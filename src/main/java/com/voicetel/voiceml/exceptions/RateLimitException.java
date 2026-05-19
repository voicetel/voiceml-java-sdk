package com.voicetel.voiceml.exceptions;

/**
 * HTTP 429 — per-account rate limit exceeded.
 *
 * <p>The {@code Retry-After} header (when present) is consumed by the transport's retry
 * loop; callers handling this exception manually should check the header on the wire if
 * they need to back off further.
 */
public class RateLimitException extends ApiException {

    private static final long serialVersionUID = 1L;

    public RateLimitException(String message, int statusCode, Integer code, String body) {
        super(message, statusCode, code, body);
    }
}
