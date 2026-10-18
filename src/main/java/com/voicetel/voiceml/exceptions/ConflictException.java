package com.voicetel.voiceml.exceptions;

import java.time.Duration;

/**
 * HTTP 409 — request conflicts with current resource state.
 *
 * <p>Typical cases: deleting a queue that still has waiting members, or an account update that
 * lost an optimistic-lock race (code {@code 20409}).
 */
public class ConflictException extends ApiException {

    private static final long serialVersionUID = 1L;

    /**
     * Create the exception.
     *
     * @param message human-readable message
     * @param statusCode HTTP status
     * @param code VoiceML error code from the response body, or {@code null}
     * @param body raw response body, or {@code null}
     */
    public ConflictException(String message, int statusCode, Integer code, String body) {
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
    public ConflictException(String message, int statusCode, Integer code, String body,
            String moreInfo, Duration retryAfter) {
        super(message, statusCode, code, body, moreInfo, retryAfter, null);
    }
}
