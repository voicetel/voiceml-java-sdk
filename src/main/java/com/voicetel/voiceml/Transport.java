package com.voicetel.voiceml;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.voicetel.voiceml.exceptions.ApiException;
import com.voicetel.voiceml.exceptions.AuthenticationException;
import com.voicetel.voiceml.exceptions.BadRequestException;
import com.voicetel.voiceml.exceptions.ConflictException;
import com.voicetel.voiceml.exceptions.GoneException;
import com.voicetel.voiceml.exceptions.NotFoundException;
import com.voicetel.voiceml.exceptions.NotImplementedException;
import com.voicetel.voiceml.exceptions.PermissionDeniedException;
import com.voicetel.voiceml.exceptions.RateLimitException;
import com.voicetel.voiceml.exceptions.ServerException;

import java.io.IOException;
import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * HTTP transport for the VoiceML SDK.
 *
 * <p>Owns the JDK {@link HttpClient}, builds {@code application/x-www-form-urlencoded} bodies the
 * way Twilio's wire shape expects (booleans → {@code "true"}/{@code "false"}, lists → repeated
 * keys), attaches HTTP Basic auth from {@link ClientOptions}, and translates non-2xx responses
 * into the right {@link ApiException} subclass.
 *
 * <p>Transient failures ({@code 429}, {@code 5xx}, transport errors) are retried up to
 * {@code maxRetries} times with exponential backoff. The {@code Retry-After} header, when present
 * on a {@code 429} or {@code 503}, overrides backoff.
 */
public final class Transport {

    private static final Set<Integer> RETRYABLE_STATUSES = new HashSet<>();

    static {
        RETRYABLE_STATUSES.add(429);
        RETRYABLE_STATUSES.add(500);
        RETRYABLE_STATUSES.add(502);
        RETRYABLE_STATUSES.add(503);
        RETRYABLE_STATUSES.add(504);
    }

    private static final ObjectMapper MAPPER = new ObjectMapper();

    private final ClientOptions options;
    private final HttpClient http;

    public Transport(ClientOptions options) {
        this.options = options;
        this.http = options.getHttpClient() != null
                ? options.getHttpClient()
                : HttpClient.newBuilder()
                        .connectTimeout(Duration.ofSeconds(10))
                        .build();
    }

    public ClientOptions options() {
        return options;
    }

    public String accountSid() {
        return options.getAccountSid();
    }

    public String baseUrl() {
        return options.getBaseUrl();
    }

    public ObjectMapper mapper() {
        return MAPPER;
    }

    /**
     * Issue an HTTP request and parse the JSON response into a {@link JsonNode}.
     *
     * @param method HTTP method.
     * @param path  Path under the server base URL (must start with {@code /}).
     * @param query Query-string parameters; {@code null} values dropped, lists become repeated keys.
     * @param form  Form body parameters; same encoding rules as query.
     * @return The parsed JSON response, or {@code null} for empty 2xx bodies.
     */
    public JsonNode request(String method, String path, Map<String, Object> query, Map<String, Object> form) {
        int attempt = 0;
        while (true) {
            HttpRequest req = buildRequest(method, path, query, form, null, null);
            HttpResponse<String> resp;
            try {
                resp = http.send(req, HttpResponse.BodyHandlers.ofString(StandardCharsets.UTF_8));
            } catch (IOException ex) {
                if (attempt >= options.getMaxRetries()) {
                    throw new ApiException(
                            "transport error after " + (attempt + 1) + " attempts: " + ex.getMessage(),
                            0,
                            null,
                            null,
                            ex);
                }
                sleepBackoff(attempt, null);
                attempt++;
                continue;
            } catch (InterruptedException ex) {
                Thread.currentThread().interrupt();
                throw new ApiException(
                        "request interrupted: " + ex.getMessage(), 0, null, null, ex);
            }

            int status = resp.statusCode();
            if (RETRYABLE_STATUSES.contains(status) && attempt < options.getMaxRetries()) {
                sleepBackoff(attempt, resp);
                attempt++;
                continue;
            }
            return parse(resp);
        }
    }

    /**
     * GET a binary payload (used for {@code .wav} recording audio).
     *
     * <p>Follows the single {@code 302}→presigned-S3 redirect the recordings endpoint may emit.
     */
    public BinaryResponse fetchBytes(String path) {
        HttpRequest req = HttpRequest.newBuilder()
                .uri(URI.create(baseUrl() + path))
                .timeout(options.getTimeout())
                .header("Authorization", basicAuth())
                .header("User-Agent", options.getUserAgent())
                .header("Accept", "*/*")
                .GET()
                .build();

        HttpClient redirecting = HttpClient.newBuilder()
                .followRedirects(HttpClient.Redirect.ALWAYS)
                .connectTimeout(Duration.ofSeconds(10))
                .build();

        HttpResponse<byte[]> resp;
        try {
            resp = redirecting.send(req, HttpResponse.BodyHandlers.ofByteArray());
        } catch (IOException ex) {
            throw new ApiException("transport error: " + ex.getMessage(), 0, null, null, ex);
        } catch (InterruptedException ex) {
            Thread.currentThread().interrupt();
            throw new ApiException("request interrupted: " + ex.getMessage(), 0, null, null, ex);
        }

        int status = resp.statusCode();
        if (status < 200 || status >= 300) {
            String body = new String(resp.body(), StandardCharsets.UTF_8);
            throw mapError(status, body);
        }
        String contentType = resp.headers().firstValue("content-type").orElse("application/octet-stream");
        boolean viaRedirect = resp.previousResponse().isPresent();
        return new BinaryResponse(resp.body(), contentType, status, viaRedirect);
    }

    /**
     * Issue an HTTP request with a JSON body and parse the JSON response into a {@link JsonNode}.
     *
     * <p>Used by surfaces (notably Assistants v1) whose OpenAPI spec declares
     * {@code application/json} request bodies rather than the Twilio-traditional
     * {@code application/x-www-form-urlencoded}. {@code jsonBody} is serialised with the shared
     * {@link ObjectMapper}; pass {@code null} for a bodyless request.
     */
    public JsonNode requestJson(String method, String path, Map<String, Object> query, Object jsonBody) {
        String bodyStr = null;
        if (jsonBody != null) {
            try {
                bodyStr = MAPPER.writeValueAsString(jsonBody);
            } catch (JsonProcessingException ex) {
                throw new ApiException(
                        "failed to serialise JSON body: " + ex.getMessage(), 0, null, null, ex);
            }
        }
        int attempt = 0;
        while (true) {
            HttpRequest req = buildRequest(method, path, query, null, bodyStr, "application/json");
            HttpResponse<String> resp;
            try {
                resp = http.send(req, HttpResponse.BodyHandlers.ofString(StandardCharsets.UTF_8));
            } catch (IOException ex) {
                if (attempt >= options.getMaxRetries()) {
                    throw new ApiException(
                            "transport error after " + (attempt + 1) + " attempts: " + ex.getMessage(),
                            0,
                            null,
                            null,
                            ex);
                }
                sleepBackoff(attempt, null);
                attempt++;
                continue;
            } catch (InterruptedException ex) {
                Thread.currentThread().interrupt();
                throw new ApiException(
                        "request interrupted: " + ex.getMessage(), 0, null, null, ex);
            }

            int status = resp.statusCode();
            if (RETRYABLE_STATUSES.contains(status) && attempt < options.getMaxRetries()) {
                sleepBackoff(attempt, resp);
                attempt++;
                continue;
            }
            return parse(resp);
        }
    }

    private HttpRequest buildRequest(String method, String path,
                                     Map<String, Object> query, Map<String, Object> form,
                                     String rawBody, String rawContentType) {
        String url = baseUrl() + withJsonSuffix(path) + buildQuery(query);
        HttpRequest.Builder b = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .timeout(options.getTimeout())
                .header("Authorization", basicAuth())
                .header("User-Agent", options.getUserAgent())
                .header("Accept", "application/json");

        String body;
        String contentType;
        if (rawBody != null) {
            body = rawBody;
            contentType = rawContentType != null ? rawContentType : "application/json";
        } else {
            body = encodeForm(form);
            contentType = "application/x-www-form-urlencoded";
        }
        HttpRequest.BodyPublisher publisher;
        if (body != null) {
            publisher = HttpRequest.BodyPublishers.ofString(body, StandardCharsets.UTF_8);
            b.header("Content-Type", contentType);
        } else {
            publisher = HttpRequest.BodyPublishers.noBody();
        }

        switch (method.toUpperCase()) {
            case "GET":
                b.GET();
                break;
            case "POST":
                b.POST(publisher);
                break;
            case "PUT":
                b.PUT(publisher);
                break;
            case "DELETE":
                b.DELETE();
                break;
            default:
                b.method(method.toUpperCase(), publisher);
        }
        return b.build();
    }

    /**
     * Append the {@code .json} extension that the {@code /2010-04-01/} Twilio-compatible resources
     * expect on JSON requests.
     *
     * <p>Skipped for paths that already carry a recognised extension ({@code .json}, {@code .wav},
     * {@code .yaml}, {@code .yml}) and for the diagnostic root paths ({@code /health},
     * {@code /openapi.*}). The .wav path is constructed directly by
     * {@code fetchBytes}, but we still guard here so this helper is safe to call from any future
     * code path.
     */
    static String withJsonSuffix(String path) {
        if (path == null || path.isEmpty()) {
            return path;
        }
        if (path.equals("/health") || path.startsWith("/openapi.")
                || path.startsWith("/v1/") || path.startsWith("/v2/")) {
            return path;
        }
        int lastSlash = path.lastIndexOf('/');
        String tail = lastSlash >= 0 ? path.substring(lastSlash + 1) : path;
        int dot = tail.lastIndexOf('.');
        if (dot >= 0) {
            String ext = tail.substring(dot + 1).toLowerCase();
            if (ext.equals("json") || ext.equals("wav") || ext.equals("yaml") || ext.equals("yml")) {
                return path;
            }
        }
        return path + ".json";
    }

    private String basicAuth() {
        String token = options.getAccountSid() + ":" + options.getApiKey();
        return "Basic " + Base64.getEncoder()
                .encodeToString(token.getBytes(StandardCharsets.UTF_8));
    }

    /**
     * Build a URL-encoded query string with leading {@code ?}, or empty when {@code params} is null/empty.
     * Drops {@code null} values; renders lists as repeated keys.
     */
    static String buildQuery(Map<String, Object> params) {
        if (params == null || params.isEmpty()) {
            return "";
        }
        List<String> pairs = new ArrayList<>();
        for (Map.Entry<String, Object> e : params.entrySet()) {
            if (e.getValue() == null) {
                continue;
            }
            String key = e.getKey();
            for (String value : flatten(e.getValue())) {
                pairs.add(encode(key) + "=" + encode(value));
            }
        }
        if (pairs.isEmpty()) {
            return "";
        }
        return "?" + String.join("&", pairs);
    }

    /**
     * Encode a map as {@code application/x-www-form-urlencoded}, returning {@code null} when no
     * usable values are present (so the caller can send a bodyless POST instead of {@code &}).
     */
    static String encodeForm(Map<String, Object> params) {
        if (params == null || params.isEmpty()) {
            return null;
        }
        List<String> pairs = new ArrayList<>();
        for (Map.Entry<String, Object> e : params.entrySet()) {
            if (e.getValue() == null) {
                continue;
            }
            String key = e.getKey();
            for (String value : flatten(e.getValue())) {
                pairs.add(encode(key) + "=" + encode(value));
            }
        }
        if (pairs.isEmpty()) {
            return null;
        }
        return String.join("&", pairs);
    }

    /**
     * Coerce a value into one or more string representations for URL/form encoding.
     * Booleans always become {@code "true"}/{@code "false"} (Twilio's documented convention).
     */
    private static List<String> flatten(Object value) {
        List<String> out = new ArrayList<>();
        if (value instanceof Collection<?>) {
            for (Object v : (Collection<?>) value) {
                if (v == null) {
                    continue;
                }
                out.add(stringify(v));
            }
        } else if (value.getClass().isArray()) {
            Object[] arr = (Object[]) value;
            for (Object v : arr) {
                if (v == null) {
                    continue;
                }
                out.add(stringify(v));
            }
        } else {
            out.add(stringify(value));
        }
        return out;
    }

    private static String stringify(Object v) {
        if (v instanceof Boolean) {
            return ((Boolean) v) ? "true" : "false";
        }
        return v.toString();
    }

    private static String encode(String s) {
        return URLEncoder.encode(s, StandardCharsets.UTF_8);
    }

    private JsonNode parse(HttpResponse<String> resp) {
        int status = resp.statusCode();
        String body = resp.body();
        if (status >= 200 && status < 300) {
            if (body == null || body.isEmpty()) {
                return null;
            }
            try {
                return MAPPER.readTree(body);
            } catch (JsonProcessingException ex) {
                throw new ApiException(
                        "non-JSON success response: " + truncate(body),
                        status,
                        null,
                        body,
                        ex);
            }
        }
        throw mapError(status, body);
    }

    private static String truncate(String s) {
        if (s == null) {
            return "";
        }
        return s.length() > 200 ? s.substring(0, 200) : s;
    }

    private void sleepBackoff(int attempt, HttpResponse<String> resp) {
        long delayMs = backoffDelayMs(attempt, resp);
        try {
            Thread.sleep(delayMs);
        } catch (InterruptedException ex) {
            Thread.currentThread().interrupt();
        }
    }

    static long backoffDelayMs(int attempt, HttpResponse<String> resp) {
        if (resp != null) {
            var retryAfter = resp.headers().firstValue("retry-after");
            if (retryAfter.isPresent()) {
                try {
                    double seconds = Double.parseDouble(retryAfter.get());
                    return (long) Math.max(0.0, seconds * 1000.0);
                } catch (NumberFormatException ignored) {
                    // fall through to exponential backoff
                }
            }
        }
        long base = 500L * (1L << Math.min(attempt, 4));
        return Math.min(base, 8000L);
    }

    /**
     * Map an HTTP status + body to the most specific {@link ApiException} subclass.
     * Parses the Twilio-compatible error envelope when present.
     */
    public static ApiException mapError(int status, String body) {
        Integer code = null;
        String message = "HTTP " + status;
        String moreInfo = null;
        if (body != null && !body.isEmpty()) {
            try {
                JsonNode node = MAPPER.readTree(body);
                if (node != null && node.isObject()) {
                    JsonNode codeNode = node.get("code");
                    if (codeNode != null && codeNode.isNumber()) {
                        code = codeNode.intValue();
                    } else if (codeNode != null && codeNode.isTextual()) {
                        try {
                            code = Integer.parseInt(codeNode.asText());
                        } catch (NumberFormatException ignored) {
                            // non-numeric code — leave null
                        }
                    }
                    JsonNode msgNode = node.get("message");
                    if (msgNode != null && msgNode.isTextual()) {
                        message = msgNode.asText();
                    }
                    JsonNode moreInfoNode = node.get("more_info");
                    if (moreInfoNode != null && moreInfoNode.isTextual()) {
                        moreInfo = moreInfoNode.asText();
                    }
                }
            } catch (JsonProcessingException ignored) {
                // body wasn't JSON — keep the default message
            }
        }
        ApiException ex;
        switch (status) {
            case 400:
                ex = new BadRequestException(message, status, code, body);
                break;
            case 401:
                ex = new AuthenticationException(message, status, code, body);
                break;
            case 403:
                ex = new PermissionDeniedException(message, status, code, body);
                break;
            case 404:
                ex = new NotFoundException(message, status, code, body);
                break;
            case 409:
                ex = new ConflictException(message, status, code, body);
                break;
            case 410:
                ex = new GoneException(message, status, code, body);
                break;
            case 429:
                ex = new RateLimitException(message, status, code, body);
                break;
            case 501:
                ex = new NotImplementedException(message, status, code, body);
                break;
            default:
                if (status >= 500 && status < 600) {
                    ex = new ServerException(message, status, code, body);
                } else {
                    ex = new ApiException(message, status, code, body);
                }
        }
        ex.setMoreInfo(moreInfo);
        return ex;
    }

    /** Holder for binary responses (recording audio). */
    public static final class BinaryResponse {
        private final byte[] content;
        private final String contentType;
        private final int statusCode;
        private final boolean viaRedirect;

        public BinaryResponse(byte[] content, String contentType, int statusCode, boolean viaRedirect) {
            this.content = content;
            this.contentType = contentType;
            this.statusCode = statusCode;
            this.viaRedirect = viaRedirect;
        }

        public byte[] getContent() {
            return content;
        }

        public String getContentType() {
            return contentType;
        }

        public int getStatusCode() {
            return statusCode;
        }

        public boolean isViaRedirect() {
            return viaRedirect;
        }
    }

    /** Construct an ordered map (preserves insertion order, drops nulls is the caller's job). */
    public static Map<String, Object> params() {
        return new LinkedHashMap<>();
    }
}
