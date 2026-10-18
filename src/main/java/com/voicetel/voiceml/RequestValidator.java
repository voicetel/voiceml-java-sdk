package com.voicetel.voiceml;

import java.net.URI;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.TreeMap;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

/**
 * Validates the {@code X-Twilio-Signature} header VoiceML puts on every webhook it sends.
 *
 * <p>The signature is HMAC-SHA1, keyed with your VoiceML API key, over the full webhook URL
 * (without any {@code #fragment}) followed, for a form-encoded {@code POST}, by every parameter
 * as {@code key + value}, with keys sorted in byte order and repeated keys kept in the order they
 * were received. It is sent Base64-encoded. For a {@code GET} only the URL is signed. Validation
 * compares in constant time and, like Twilio's validators, also accepts the URL with the default
 * port ({@code :443} for https, {@code :80} for http) added or removed, since proxies differ on
 * whether they keep it.
 *
 * <pre>{@code
 * RequestValidator validator = new RequestValidator(apiKey);
 * boolean ok = validator.validate(
 *         "https://example.com/voice/answer",       // the URL VoiceML called, as configured
 *         formParams,                                // Map<String, List<String>> of the POST body
 *         request.getHeader("X-Twilio-Signature"));
 * }</pre>
 *
 * <p>{@link #toString()} never includes the key. Instances are immutable and thread-safe.
 */
public final class RequestValidator {

    /** The request header that carries the signature. */
    public static final String SIGNATURE_HEADER = "X-Twilio-Signature";

    private static final String HMAC = "HmacSHA1";

    private final SecretKeySpec key;

    /**
     * Create a validator.
     *
     * @param apiKey your VoiceML API key (the HTTP Basic password the SDK authenticates with)
     */
    public RequestValidator(String apiKey) {
        Objects.requireNonNull(apiKey, "apiKey");
        if (apiKey.isEmpty()) {
            throw new IllegalArgumentException("apiKey must not be empty");
        }
        this.key = new SecretKeySpec(apiKey.getBytes(StandardCharsets.UTF_8), HMAC);
    }

    /**
     * Validate a webhook.
     *
     * @param url the full URL VoiceML requested, including the query string
     * @param params the form parameters of a {@code POST} (each key's values in received order),
     *     or {@code null} / empty for a {@code GET}
     * @param signature the {@code X-Twilio-Signature} header value
     * @return whether the signature matches
     */
    public boolean validate(String url, Map<String, ? extends Collection<String>> params, String signature) {
        if (url == null || signature == null || signature.isEmpty()) {
            return false;
        }
        byte[] expected = signature.getBytes(StandardCharsets.UTF_8);
        boolean ok = false;
        // Evaluate every candidate so the time taken does not depend on which one matched.
        for (String candidate : urlVariants(url)) {
            byte[] actual = computeSignature(candidate, params).getBytes(StandardCharsets.UTF_8);
            ok |= MessageDigest.isEqual(actual, expected);
        }
        return ok;
    }

    /**
     * Validate a webhook whose form parameters are single-valued.
     *
     * @param url the full URL VoiceML requested
     * @param params the form parameters of a {@code POST}, or {@code null} / empty for a {@code GET}
     * @param signature the {@code X-Twilio-Signature} header value
     * @return whether the signature matches
     */
    public boolean validateSingle(String url, Map<String, String> params, String signature) {
        return validate(url, single(params), signature);
    }

    /**
     * Compute the signature VoiceML would send for a request (useful in tests of your webhook).
     *
     * @param url the full URL; a {@code #fragment} is ignored
     * @param params the form parameters of a {@code POST}, or {@code null} / empty for a {@code GET}
     * @return the Base64 signature
     */
    public String computeSignature(String url, Map<String, ? extends Collection<String>> params) {
        StringBuilder data = new StringBuilder(stripFragment(url));
        if (params != null && !params.isEmpty()) {
            TreeMap<byte[], String> sorted = new TreeMap<>(RequestValidator::compareBytes);
            for (String k : params.keySet()) {
                sorted.put(k.getBytes(StandardCharsets.UTF_8), k);
            }
            for (String k : sorted.values()) {
                Collection<String> values = params.get(k);
                if (values == null) {
                    continue;
                }
                for (String v : values) {
                    data.append(k).append(v == null ? "" : v);
                }
            }
        }
        Mac mac;
        try {
            mac = Mac.getInstance(HMAC);
            mac.init(key);
        } catch (NoSuchAlgorithmException | InvalidKeyException ex) {
            throw new IllegalStateException("HmacSHA1 is unavailable", ex);
        }
        byte[] digest = mac.doFinal(data.toString().getBytes(StandardCharsets.UTF_8));
        return Base64.getEncoder().encodeToString(digest);
    }

    /**
     * Compute the signature for single-valued form parameters.
     *
     * @param url the full URL
     * @param params the form parameters, or {@code null} for a {@code GET}
     * @return the Base64 signature
     */
    public String computeSignatureSingle(String url, Map<String, String> params) {
        return computeSignature(url, single(params));
    }

    @Override
    public String toString() {
        return "RequestValidator([key redacted])";
    }

    private static Map<String, List<String>> single(Map<String, String> params) {
        if (params == null) {
            return null;
        }
        Map<String, List<String>> out = new TreeMap<>();
        for (Map.Entry<String, String> e : params.entrySet()) {
            out.put(e.getKey(), Collections.singletonList(e.getValue()));
        }
        return out;
    }

    private static int compareBytes(byte[] a, byte[] b) {
        int n = Math.min(a.length, b.length);
        for (int i = 0; i < n; i++) {
            int c = Integer.compare(a[i] & 0xFF, b[i] & 0xFF);
            if (c != 0) {
                return c;
            }
        }
        return Integer.compare(a.length, b.length);
    }

    private static String stripFragment(String url) {
        int hash = url.indexOf('#');
        return hash >= 0 ? url.substring(0, hash) : url;
    }

    /** The URL as given, plus the variant with the scheme's default port added or removed. */
    static List<String> urlVariants(String url) {
        String base = stripFragment(url);
        List<String> out = new ArrayList<>(2);
        out.add(base);
        URI u;
        try {
            u = new URI(base);
        } catch (URISyntaxException ex) {
            return out;
        }
        String scheme = u.getScheme();
        if (scheme == null || u.getRawAuthority() == null || u.getHost() == null) {
            return out;
        }
        int defaultPort = scheme.equalsIgnoreCase("https") ? 443 : scheme.equalsIgnoreCase("http") ? 80 : -1;
        if (defaultPort < 0) {
            return out;
        }
        String authority = u.getRawAuthority();
        int at = authority.lastIndexOf('@');
        String userInfo = at >= 0 ? authority.substring(0, at + 1) : "";
        String hostPort = at >= 0 ? authority.substring(at + 1) : authority;
        String newHostPort;
        if (u.getPort() == defaultPort) {
            newHostPort = hostPort.substring(0, hostPort.lastIndexOf(':'));
        } else if (u.getPort() < 0) {
            newHostPort = hostPort + ":" + defaultPort;
        } else {
            return out;
        }
        int authStart = base.indexOf(authority, scheme.length());
        out.add(base.substring(0, authStart) + userInfo + newHostPort + base.substring(authStart + authority.length()));
        return out;
    }
}
