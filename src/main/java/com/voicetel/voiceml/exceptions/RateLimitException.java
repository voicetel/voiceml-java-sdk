package com.voicetel.voiceml.exceptions;

import java.time.Duration;

/**
 * HTTP 429 — per-account rate limit exceeded.
 *
 * <p>The transport retries a 429 (up to {@code maxRetries}) and honours {@code Retry-After} in
 * both delta-seconds and HTTP-date form. When the server asks for a delay longer than the 60-second
 * cap, the SDK does not sleep: it raises this exception immediately, and
 * {@link #getRetryAfter()} carries the requested delay so the caller can schedule its own retry.
 */
public class RateLimitException extends ApiException {

    private static final long serialVersionUID = 1L;

    /**
     * Create the exception.
     *
     * @param message human-readable message
     * @param statusCode HTTP status
     * @param code VoiceML error code from the response body, or {@code null}
     * @param body raw response body, or {@code null}
     */
    public RateLimitException(String message, int statusCode, Integer code, String body) {
        super(message, statusCode, code, body);
    }

    /**
     * Create the exception with every detail parsed from the error response.
     *
     * @param message human-readable message
     * @param statusCode HTTP status
     * @param code VoiceML error code from the response body, or {@code null}
     * @param body raw response body, or {@code null}
     * @param moreInfo {@code more_info} URL from the error envelope, or {@code null}
     * @param retryAfter parsed {@code Retry-After} header, or {@code null}
     */
    public RateLimitException(String message, int statusCode, Integer code, String body,
            String moreInfo, Duration retryAfter) {
        super(message, statusCode, code, body, moreInfo, retryAfter, null);
    }
}
