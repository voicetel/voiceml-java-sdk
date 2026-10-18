package com.voicetel.voiceml;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.voicetel.voiceml.exceptions.ApiException;
import com.voicetel.voiceml.exceptions.AuthenticationException;
import com.voicetel.voiceml.exceptions.BadRequestException;
import com.voicetel.voiceml.exceptions.ConfigurationException;
import com.voicetel.voiceml.exceptions.ConflictException;
import com.voicetel.voiceml.exceptions.GoneException;
import com.voicetel.voiceml.exceptions.MethodNotAllowedException;
import com.voicetel.voiceml.exceptions.NotFoundException;
import com.voicetel.voiceml.exceptions.NotImplementedException;
import com.voicetel.voiceml.exceptions.PaginationException;
import com.voicetel.voiceml.exceptions.PermissionDeniedException;
import com.voicetel.voiceml.exceptions.RateLimitException;
import com.voicetel.voiceml.exceptions.RequestTimeoutException;
import com.voicetel.voiceml.exceptions.ServerException;
import com.voicetel.voiceml.exceptions.TransportException;
import com.voicetel.voiceml.exceptions.ValidationException;

import java.io.IOException;
import java.net.ConnectException;
import java.net.NoRouteToHostException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URLEncoder;
import java.net.UnknownHostException;
import java.net.http.HttpClient;
import java.net.http.HttpConnectTimeoutException;
import java.net.http.HttpHeaders;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpTimeoutException;
import java.nio.channels.UnresolvedAddressException;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.time.Instant;
import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.temporal.TemporalAccessor;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;
import javax.net.ssl.SSLHandshakeException;

/**
 * HTTP transport for the VoiceML SDK.
 *
 * <p>Owns the JDK {@link HttpClient}, builds {@code application/x-www-form-urlencoded} bodies
 * (booleans as {@code true}/{@code false}, lists as repeated keys, dates as ISO-8601), attaches
 * HTTP Basic auth from {@link ClientOptions}, and translates non-2xx responses into the right
 * {@link ApiException} subclass.
 *
 * <p><b>Retry policy.</b> Up to {@code maxRetries} retries, with exponential backoff (0.5 s base,
 * 8 s maximum, full jitter):
 * <ul>
 *   <li>{@code GET}, {@code HEAD}, {@code PUT} and {@code DELETE} retry on {@code 429},
 *       {@code 500}, {@code 502}, {@code 503}, {@code 504} and on network errors.</li>
 *   <li>{@code POST} and {@code PATCH} retry only on {@code 429} and on connection failures where
 *       the request provably was not sent (DNS failure, connection refused, connect timeout, TLS
 *       handshake failure). They are never retried on a {@code 5xx}, a read timeout or a reset
 *       connection, so a call or message is never sent twice by the SDK.</li>
 *   <li>A {@code Retry-After} header (delta-seconds or HTTP-date) replaces the backoff delay. When
 *       it exceeds {@link #MAX_RETRY_AFTER} the SDK does not sleep: it raises immediately, with the
 *       requested delay on {@link ApiException#getRetryAfter()}.</li>
 * </ul>
 *
 * <p>Paths passed to {@link #request} and {@link #requestJson} are sent as given: callers supply
 * the full, already percent-encoded path including any {@code .json} suffix.
 */
public final class Transport {

    /** Longest {@code Retry-After} the SDK sleeps for; a longer request raises immediately. */
    public static final Duration MAX_RETRY_AFTER = Duration.ofSeconds(60);

    static final long BACKOFF_BASE_MS = 500L;
    static final long BACKOFF_MAX_MS = 8000L;
    static final int MAX_REDIRECTS = 5;

    private static final Set<String> IDEMPOTENT_METHODS = Set.of("GET", "HEAD", "PUT", "DELETE", "OPTIONS");
    private static final Set<Integer> RETRYABLE_STATUSES = Set.of(429, 500, 502, 503, 504);
    private static final String FORM_CONTENT_TYPE = "application/x-www-form-urlencoded";
    private static final String JSON_CONTENT_TYPE = "application/json";

    private static final ObjectMapper MAPPER = JsonMapper.builder()
            .disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES)
            .enable(DeserializationFeature.READ_UNKNOWN_ENUM_VALUES_USING_DEFAULT_VALUE)
            .build();

    private final ClientOptions options;
    private final HttpClient http;
    private final String baseUrlOverride;

    /**
     * Create a transport for the given options.
     *
     * @param options validated client options
     */
    public Transport(ClientOptions options) {
        this(options, null, null);
    }

    private Transport(ClientOptions options, HttpClient sharedHttp, String baseUrlOverride) {
        this.options = options;
        this.http = sharedHttp != null
                ? sharedHttp
                : (options.getHttpClient() != null
                        ? options.getHttpClient()
                        : HttpClient.newBuilder()
                                .connectTimeout(Duration.ofSeconds(10))
                                .followRedirects(HttpClient.Redirect.NEVER)
                                .build());
        this.baseUrlOverride = baseUrlOverride;
    }

    /**
     * Return a transport view pinned to a product base URL, sharing this transport's underlying
     * {@link HttpClient}, auth, and retry policy.
     *
     * <p>Used to route an entire resource group at its product subdomain
     * ({@code conversations.voicetel.com} / {@code messaging.voicetel.com}) without each resource
     * needing to know its own host: the returned transport answers {@link #baseUrl()} with the
     * scoped host, and every request is issued against it.
     *
     * @param baseUrl the product base URL
     * @return a transport bound to that URL
     */
    public Transport withBaseUrl(String baseUrl) {
        return new Transport(options, this.http, Hosts.stripTrailingSlash(baseUrl));
    }

    /**
     * The options this transport was built with.
     *
     * @return the options
     */
    public ClientOptions options() {
        return options;
    }

    /**
     * The configured AccountSid.
     *
     * @return the AccountSid, or {@code null} for a diagnostics-only client
     */
    public String accountSid() {
        return options.getAccountSid();
    }

    /**
     * The base URL every request of this transport is sent to.
     *
     * @return the URL, without a trailing slash
     */
    public String baseUrl() {
        return baseUrlOverride != null ? baseUrlOverride : options.getBaseUrl();
    }

    /**
     * Decode a JSON tree into a model type with the SDK's (private) mapper configuration: unknown
     * properties are ignored and unknown enum values decode to {@code UNKNOWN}.
     *
     * @param node the JSON tree, may be {@code null}
     * @param type target type
     * @param <T> target type
     * @return the decoded value, or {@code null} for a {@code null} node
     * @throws ApiException when the JSON does not fit the type
     */
    public <T> T decode(JsonNode node, Class<T> type) {
        if (node == null) {
            return null;
        }
        try {
            return MAPPER.treeToValue(node, type);
        } catch (JsonProcessingException | IllegalArgumentException ex) {
            throw new ApiException("could not decode response into " + type.getSimpleName()
                    + ": " + ex.getMessage(), 0, null, null, ex);
        }
    }

    /**
     * Decode a JSON tree into a generic type (for example {@code List<…>}).
     *
     * @param node the JSON tree, may be {@code null}
     * @param type target type
     * @param <T> target type
     * @return the decoded value, or {@code null} for a {@code null} node
     * @throws ApiException when the JSON does not fit the type
     */
    public <T> T decode(JsonNode node, TypeReference<T> type) {
        if (node == null) {
            return null;
        }
        try {
            return MAPPER.readValue(MAPPER.treeAsTokens(node), type);
        } catch (IOException | IllegalArgumentException ex) {
            throw new ApiException("could not decode response: " + ex.getMessage(), 0, null, null, ex);
        }
    }

    /**
     * Issue an authenticated HTTP request with an optional form body and parse the JSON response.
     *
     * @param method HTTP method.
     * @param path  Full, percent-encoded path under the base URL (must start with {@code /}).
     * @param query Query-string parameters; {@code null} values dropped, lists become repeated keys.
     * @param form  Form body parameters; same encoding rules as query.
     * @return The parsed JSON response, or {@code null} for empty 2xx bodies.
     */
    public JsonNode request(String method, String path, Map<String, Object> query, Map<String, Object> form) {
        String body = encodeForm(form);
        HttpRequest req = newRequest(method, resolvePath(path, query), JSON_CONTENT_TYPE, true,
                body, body != null ? FORM_CONTENT_TYPE : null);
        return parse(send(req, stringHandler(), options.getMaxRetries()));
    }

    /**
     * Issue an authenticated HTTP request with a JSON body and parse the JSON response.
     *
     * <p>Used by surfaces (notably Assistants v1 and the account update) whose OpenAPI spec
     * declares {@code application/json} request bodies. Pass {@code null} for a bodyless request.
     *
     * @param method HTTP method.
     * @param path  Full, percent-encoded path under the base URL.
     * @param query Query-string parameters.
     * @param jsonBody Value serialised as the JSON body, or {@code null}.
     * @return The parsed JSON response, or {@code null} for empty 2xx bodies.
     */
    public JsonNode requestJson(String method, String path, Map<String, Object> query, Object jsonBody) {
        String bodyStr = null;
        if (jsonBody != null) {
            try {
                bodyStr = MAPPER.writeValueAsString(jsonBody);
            } catch (JsonProcessingException ex) {
                throw new ValidationException("could not serialise JSON body: " + ex.getMessage());
            }
        }
        HttpRequest req = newRequest(method, resolvePath(path, query), JSON_CONTENT_TYPE, true,
                bodyStr, bodyStr != null ? JSON_CONTENT_TYPE : null);
        return parse(send(req, stringHandler(), options.getMaxRetries()));
    }

    /**
     * Follow a pagination link with an authenticated {@code GET}.
     *
     * <p>A relative link ({@code next_page_uri} on {@code /2010-04-01} lists) is resolved against
     * the scheme and host of this transport. An absolute link ({@code meta.next_page_url} on
     * {@code /v1} lists) is followed only when its scheme, host and port equal this transport's;
     * anything else raises {@link PaginationException} without sending a request.
     *
     * @param link the {@code next_page_uri} / {@code next_page_url} value
     * @return the parsed JSON page
     */
    public JsonNode getLink(String link) {
        HttpRequest req = newRequest("GET", resolveLink(link), JSON_CONTENT_TYPE, true, null, null);
        return parse(send(req, stringHandler(), options.getMaxRetries()));
    }

    /**
     * Authenticated {@code GET} of a binary payload (recording audio) with
     * {@code Accept: audio/*, *}{@code /*}. See {@link #fetchBinary(String, String)}.
     *
     * @param path full, percent-encoded path
     * @return the payload
     */
    public BinaryResponse fetchBytes(String path) {
        return fetchBinary(path, "audio/*, */*");
    }

    /**
     * Authenticated {@code GET} of a binary payload, following redirects manually.
     *
     * <p>Up to {@value #MAX_REDIRECTS} redirects are followed. {@code Authorization} is sent only
     * while the target keeps the original scheme, host and port; once a redirect leaves that origin
     * (for example to a presigned storage URL) the remaining hops are unauthenticated. A redirect
     * from {@code https} to {@code http} is refused with {@link TransportException}.
     *
     * @param path full, percent-encoded path
     * @param accept the {@code Accept} header
     * @return the payload
     */
    public BinaryResponse fetchBinary(String path, String accept) {
        URI origin = resolvePath(path, null);
        URI current = origin;
        boolean authenticated = true;
        for (int hop = 0; ; hop++) {
            HttpRequest req = newRequest("GET", current, accept, authenticated, null, null);
            HttpResponse<byte[]> resp = send(req, binaryHandler(), options.getMaxRetries());
            int status = resp.statusCode();
            if (isRedirect(status)) {
                if (hop >= MAX_REDIRECTS) {
                    throw new TransportException("too many redirects fetching " + path, null);
                }
                Optional<String> location = resp.headers().firstValue("location");
                if (location.isEmpty() || location.get().isEmpty()) {
                    throw mapError(status, "redirect without a Location header", resp.headers());
                }
                URI next;
                try {
                    next = current.resolve(new URI(location.get()));
                } catch (URISyntaxException | IllegalArgumentException ex) {
                    throw new TransportException("invalid redirect Location: " + location.get(), ex);
                }
                String scheme = next.getScheme() == null ? "" : next.getScheme().toLowerCase(Locale.ROOT);
                if (!scheme.equals("https") && !scheme.equals("http")) {
                    throw new TransportException("refusing redirect to unsupported scheme: " + next, null);
                }
                if ("https".equalsIgnoreCase(current.getScheme()) && scheme.equals("http")) {
                    throw new TransportException("refusing redirect from https to http: " + next, null);
                }
                if (!sameOrigin(origin, next)) {
                    authenticated = false;
                }
                current = next;
                continue;
            }
            if (status < 200 || status >= 300) {
                throw mapError(status, new String(resp.body(), StandardCharsets.UTF_8), resp.headers());
            }
            String contentType = resp.headers().firstValue("content-type").orElse("application/octet-stream");
            return new BinaryResponse(resp.body(), contentType, status, hop > 0);
        }
    }

    /**
     * Unauthenticated {@code GET} of a JSON document (the {@code /health} and {@code /openapi.json}
     * diagnostics, which the spec marks {@code security: []}).
     *
     * @param path full path
     * @param retry whether the usual retry policy applies ({@code false} makes a single attempt)
     * @return the parsed JSON
     */
    public JsonNode requestUnauthenticated(String path, boolean retry) {
        HttpRequest req = newRequest("GET", resolvePath(path, null), JSON_CONTENT_TYPE, false, null, null);
        return parse(send(req, stringHandler(), retry ? options.getMaxRetries() : 0));
    }

    /**
     * Unauthenticated {@code GET} of a text document (the YAML OpenAPI diagnostics).
     *
     * @param path full path
     * @param accept the {@code Accept} header
     * @return the response body
     */
    public String requestTextUnauthenticated(String path, String accept) {
        HttpRequest req = newRequest("GET", resolvePath(path, null), accept, false, null, null);
        HttpResponse<String> resp = send(req, stringHandler(), options.getMaxRetries());
        int status = resp.statusCode();
        if (status < 200 || status >= 300) {
            throw mapError(status, resp.body(), resp.headers());
        }
        return resp.body() == null ? "" : resp.body();
    }

    // --- request construction ------------------------------------------------------------------

    private URI resolvePath(String path, Map<String, Object> query) {
        if (path == null || !path.startsWith("/")) {
            throw new ValidationException("path must start with '/': " + path);
        }
        String url = baseUrl() + path + buildQuery(query);
        try {
            return new URI(url);
        } catch (URISyntaxException ex) {
            throw new ValidationException("invalid request URL " + url + ": " + ex.getMessage());
        }
    }

    URI resolveLink(String link) {
        if (link == null || link.isEmpty()) {
            throw new PaginationException("no next page");
        }
        URI base = URI.create(baseUrl());
        URI target;
        try {
            if (link.startsWith("/") && !link.startsWith("//")) {
                target = new URI(base.getScheme() + "://" + base.getRawAuthority() + link);
            } else {
                target = new URI(link);
            }
        } catch (URISyntaxException ex) {
            throw new PaginationException("invalid pagination link: " + link);
        }
        if (!target.isAbsolute() || target.getHost() == null) {
            throw new PaginationException("pagination link is neither a path nor an absolute URL: " + link);
        }
        if (!sameOrigin(base, target)) {
            throw new PaginationException("refusing to follow a pagination link to " + target.getScheme()
                    + "://" + target.getRawAuthority() + ": it is not the configured host "
                    + base.getScheme() + "://" + base.getRawAuthority());
        }
        return target;
    }

    private HttpRequest newRequest(String method, URI uri, String accept, boolean authenticated,
                                   String body, String contentType) {
        HttpRequest.Builder b = HttpRequest.newBuilder()
                .uri(uri)
                .timeout(options.getTimeout())
                .header("User-Agent", options.getUserAgent())
                .header("Accept", accept);
        if (authenticated) {
            b.header("Authorization", basicAuth());
        }
        HttpRequest.BodyPublisher publisher;
        if (body != null) {
            publisher = HttpRequest.BodyPublishers.ofString(body, StandardCharsets.UTF_8);
            b.header("Content-Type", contentType);
        } else {
            publisher = HttpRequest.BodyPublishers.noBody();
        }
        String m = method.toUpperCase(Locale.ROOT);
        switch (m) {
            case "GET":
                b.GET();
                break;
            case "DELETE":
                b.DELETE();
                break;
            default:
                b.method(m, publisher);
        }
        return b.build();
    }

    private String basicAuth() {
        if (!options.hasCredentials()) {
            throw new ConfigurationException(
                    "this client was built without credentials; only diagnostics are available");
        }
        String token = options.getAccountSid() + ":" + options.getApiKey();
        return "Basic " + Base64.getEncoder()
                .encodeToString(token.getBytes(StandardCharsets.UTF_8));
    }

    // --- retry loop ----------------------------------------------------------------------------

    private <T> HttpResponse<T> send(HttpRequest request, HttpResponse.BodyHandler<T> handler, int maxRetries) {
        boolean idempotent = IDEMPOTENT_METHODS.contains(request.method());
        int attempt = 0;
        while (true) {
            HttpResponse<T> resp;
            try {
                resp = http.send(request, handler);
            } catch (InterruptedException ex) {
                Thread.currentThread().interrupt();
                throw new TransportException("request interrupted: " + request.method() + " "
                        + request.uri().getRawPath(), ex);
            } catch (IOException ex) {
                boolean retry = attempt < maxRetries && (idempotent || provablyNotSent(ex));
                if (!retry) {
                    throw transportFailure(request, ex, attempt + 1);
                }
                sleep(backoffDelayMs(attempt));
                attempt++;
                continue;
            }
            int status = resp.statusCode();
            if (attempt < maxRetries && retryableStatus(status, idempotent)) {
                Duration retryAfter = parseRetryAfter(
                        resp.headers().firstValue("retry-after").orElse(null), Instant.now());
                if (retryAfter != null && retryAfter.compareTo(MAX_RETRY_AFTER) > 0) {
                    return resp;
                }
                sleep(retryAfter != null ? retryAfter.toMillis() : backoffDelayMs(attempt));
                attempt++;
                continue;
            }
            return resp;
        }
    }

    static boolean retryableStatus(int status, boolean idempotent) {
        return idempotent ? RETRYABLE_STATUSES.contains(status) : status == 429;
    }

    /**
     * Whether the failure happened while establishing the connection, so the request bytes were
     * never written and a {@code POST} can be retried without risking a duplicate.
     */
    static boolean provablyNotSent(Throwable ex) {
        for (Throwable t = ex; t != null; t = t.getCause()) {
            if (t instanceof HttpConnectTimeoutException
                    || t instanceof ConnectException
                    || t instanceof NoRouteToHostException
                    || t instanceof UnknownHostException
                    || t instanceof UnresolvedAddressException
                    || t instanceof SSLHandshakeException) {
                return true;
            }
            if (t.getCause() == t) {
                break;
            }
        }
        return false;
    }

    private static TransportException transportFailure(HttpRequest request, IOException ex, int attempts) {
        String what = request.method() + " " + request.uri().getScheme() + "://"
                + request.uri().getRawAuthority() + request.uri().getRawPath();
        String suffix = " (" + attempts + (attempts == 1 ? " attempt): " : " attempts): ") + ex;
        if (ex instanceof HttpTimeoutException) {
            return new RequestTimeoutException("request timed out: " + what + suffix, ex);
        }
        return new TransportException("transport error: " + what + suffix, ex);
    }

    private static void sleep(long millis) {
        if (millis <= 0) {
            return;
        }
        try {
            Thread.sleep(millis);
        } catch (InterruptedException ex) {
            Thread.currentThread().interrupt();
            throw new TransportException("interrupted while waiting to retry", ex);
        }
    }

    /** Full-jitter exponential backoff: uniform in {@code [0, min(8 s, 0.5 s * 2^attempt)]}. */
    static long backoffDelayMs(int attempt) {
        long cap = Math.min(BACKOFF_MAX_MS, BACKOFF_BASE_MS << Math.min(attempt, 4));
        return ThreadLocalRandom.current().nextLong(cap + 1);
    }

    /**
     * Parse a {@code Retry-After} value: delta-seconds (digits only) or an HTTP-date (RFC 1123).
     *
     * @return the delay (never negative), or {@code null} when absent or unparseable
     */
    static Duration parseRetryAfter(String value, Instant now) {
        if (value == null) {
            return null;
        }
        String v = value.trim();
        if (v.isEmpty()) {
            return null;
        }
        if (v.chars().allMatch(c -> c >= '0' && c <= '9')) {
            if (v.length() > 9) {
                return Duration.ofSeconds(999_999_999L);
            }
            return Duration.ofSeconds(Long.parseLong(v));
        }
        try {
            TemporalAccessor parsed = DateTimeFormatter.RFC_1123_DATE_TIME.parse(v);
            Instant when = ZonedDateTime.from(parsed).toInstant();
            Duration d = Duration.between(now, when);
            return d.isNegative() ? Duration.ZERO : d;
        } catch (DateTimeParseException ex) {
            return null;
        }
    }

    // --- responses -----------------------------------------------------------------------------

    private static HttpResponse.BodyHandler<String> stringHandler() {
        return HttpResponse.BodyHandlers.ofString(StandardCharsets.UTF_8);
    }

    /** Reads 2xx/4xx/5xx bodies; discards the (unused) body of a redirect. */
    private static HttpResponse.BodyHandler<byte[]> binaryHandler() {
        return info -> isRedirect(info.statusCode())
                ? HttpResponse.BodySubscribers.replacing(new byte[0])
                : HttpResponse.BodySubscribers.ofByteArray();
    }

    private static boolean isRedirect(int status) {
        return status == 301 || status == 302 || status == 303 || status == 307 || status == 308;
    }

    private static boolean sameOrigin(URI a, URI b) {
        String sa = a.getScheme() == null ? "" : a.getScheme().toLowerCase(Locale.ROOT);
        String sb = b.getScheme() == null ? "" : b.getScheme().toLowerCase(Locale.ROOT);
        String ha = a.getHost() == null ? "" : a.getHost().toLowerCase(Locale.ROOT);
        String hb = b.getHost() == null ? "" : b.getHost().toLowerCase(Locale.ROOT);
        return sa.equals(sb) && ha.equals(hb) && effectivePort(a) == effectivePort(b);
    }

    private static int effectivePort(URI u) {
        if (u.getPort() >= 0) {
            return u.getPort();
        }
        return "https".equalsIgnoreCase(u.getScheme()) ? 443 : 80;
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
                        "could not decode HTTP " + status + " response: " + ex.getOriginalMessage(),
                        status,
                        null,
                        body,
                        ex);
            }
        }
        throw mapError(status, body, resp.headers());
    }

    /**
     * Map an HTTP status and body to the most specific {@link ApiException} subclass, parsing the
     * Twilio-compatible error envelope ({@code code}, {@code message}, {@code more_info}) when the
     * body is JSON.
     *
     * @param status HTTP status
     * @param body raw response body, may be {@code null}
     * @return the exception (not thrown)
     */
    public static ApiException mapError(int status, String body) {
        return mapError(status, body, null);
    }

    static ApiException mapError(int status, String body, HttpHeaders headers) {
        Integer code = null;
        String message = "HTTP " + status;
        String moreInfo = null;
        if (body != null && !body.isEmpty()) {
            try {
                JsonNode node = MAPPER.readTree(body);
                if (node != null && node.isObject()) {
                    JsonNode codeNode = node.get("code");
                    if (codeNode != null && codeNode.canConvertToInt()) {
                        code = codeNode.intValue();
                    } else if (codeNode != null && codeNode.isTextual()) {
                        try {
                            code = Integer.parseInt(codeNode.asText().trim());
                        } catch (NumberFormatException ignored) {
                            // non-numeric code: leave null
                        }
                    }
                    JsonNode msgNode = node.get("message");
                    if (msgNode != null && msgNode.isTextual() && !msgNode.asText().isEmpty()) {
                        message = msgNode.asText();
                    }
                    JsonNode moreInfoNode = node.get("more_info");
                    if (moreInfoNode != null && moreInfoNode.isTextual()) {
                        moreInfo = moreInfoNode.asText();
                    }
                }
            } catch (JsonProcessingException ignored) {
                // body wasn't JSON: keep the default message, raw text stays on getBody()
            }
        }
        Duration retryAfter = headers == null ? null
                : parseRetryAfter(headers.firstValue("retry-after").orElse(null), Instant.now());
        switch (status) {
            case 400:
                return new BadRequestException(message, status, code, body, moreInfo, retryAfter);
            case 401:
                return new AuthenticationException(message, status, code, body, moreInfo, retryAfter);
            case 403:
                return new PermissionDeniedException(message, status, code, body, moreInfo, retryAfter);
            case 404:
                return new NotFoundException(message, status, code, body, moreInfo, retryAfter);
            case 405:
                return new MethodNotAllowedException(message, status, code, body, moreInfo, retryAfter);
            case 409:
                return new ConflictException(message, status, code, body, moreInfo, retryAfter);
            case 410:
                return new GoneException(message, status, code, body, moreInfo, retryAfter);
            case 429:
                return new RateLimitException(message, status, code, body, moreInfo, retryAfter);
            case 501:
                return new NotImplementedException(message, status, code, body, moreInfo, retryAfter);
            default:
                if (status >= 500 && status < 600) {
                    return new ServerException(message, status, code, body, moreInfo, retryAfter);
                }
                return new ApiException(message, status, code, body, moreInfo, retryAfter, null);
        }
    }

    // --- encoding ------------------------------------------------------------------------------

    /**
     * Build a URL-encoded query string with leading {@code ?}, or empty when {@code params} is
     * null/empty. Drops {@code null} values; renders lists as repeated keys.
     */
    static String buildQuery(Map<String, Object> params) {
        String encoded = encodePairs(params);
        return encoded == null ? "" : "?" + encoded;
    }

    /**
     * Encode a map as {@code application/x-www-form-urlencoded}, returning {@code null} when no
     * usable values are present (so the caller can send a bodyless POST instead of {@code &}).
     */
    static String encodeForm(Map<String, Object> params) {
        return encodePairs(params);
    }

    private static String encodePairs(Map<String, Object> params) {
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
        return pairs.isEmpty() ? null : String.join("&", pairs);
    }

    /**
     * Coerce a value into one or more strings: collections and arrays become repeated keys,
     * booleans {@code true}/{@code false}, date-times ISO-8601 UTC, dates {@code YYYY-MM-DD}, and
     * maps / JSON trees compact JSON.
     */
    private static List<String> flatten(Object value) {
        List<String> out = new ArrayList<>();
        if (value instanceof Collection<?>) {
            for (Object v : (Collection<?>) value) {
                if (v != null) {
                    out.add(stringify(v));
                }
            }
        } else if (value instanceof Object[]) {
            for (Object v : (Object[]) value) {
                if (v != null) {
                    out.add(stringify(v));
                }
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
        if (v instanceof Instant) {
            return DateTimeFormatter.ISO_INSTANT.format((Instant) v);
        }
        if (v instanceof OffsetDateTime) {
            return DateTimeFormatter.ISO_INSTANT.format(((OffsetDateTime) v).withOffsetSameInstant(ZoneOffset.UTC));
        }
        if (v instanceof ZonedDateTime) {
            return DateTimeFormatter.ISO_INSTANT.format(((ZonedDateTime) v).toInstant());
        }
        if (v instanceof LocalDate) {
            return DateTimeFormatter.ISO_LOCAL_DATE.format((LocalDate) v);
        }
        if (v instanceof Enum<?> && ((Enum<?>) v).name().equals("UNKNOWN")) {
            throw new ValidationException(
                    v.getClass().getSimpleName() + ".UNKNOWN is a decode-only placeholder and cannot be sent");
        }
        if (v instanceof Map<?, ?> || v instanceof JsonNode) {
            try {
                return MAPPER.writeValueAsString(v);
            } catch (JsonProcessingException ex) {
                throw new ValidationException("could not serialise JSON field: " + ex.getMessage());
            }
        }
        return v.toString();
    }

    private static String encode(String s) {
        return URLEncoder.encode(s, StandardCharsets.UTF_8);
    }

    /** Holder for binary responses (recording audio, exports). */
    public static final class BinaryResponse {
        private final byte[] content;
        private final String contentType;
        private final int statusCode;
        private final boolean viaRedirect;

        /**
         * Create a binary response.
         *
         * @param content the payload
         * @param contentType the {@code Content-Type} of the final response
         * @param statusCode the status of the final response
         * @param viaRedirect whether at least one redirect was followed
         */
        public BinaryResponse(byte[] content, String contentType, int statusCode, boolean viaRedirect) {
            this.content = content;
            this.contentType = contentType;
            this.statusCode = statusCode;
            this.viaRedirect = viaRedirect;
        }

        /**
         * The payload.
         *
         * @return the bytes
         */
        public byte[] getContent() {
            return content;
        }

        /**
         * The {@code Content-Type} of the final response.
         *
         * @return the media type
         */
        public String getContentType() {
            return contentType;
        }

        /**
         * The status of the final response.
         *
         * @return the HTTP status
         */
        public int getStatusCode() {
            return statusCode;
        }

        /**
         * Whether at least one redirect was followed.
         *
         * @return {@code true} when the payload came from a redirect target
         */
        public boolean isViaRedirect() {
            return viaRedirect;
        }
    }

    /**
     * Construct an ordered parameter map (insertion order is preserved on the wire).
     *
     * @return a new mutable map
     */
    public static Map<String, Object> params() {
        return new LinkedHashMap<>();
    }
}
