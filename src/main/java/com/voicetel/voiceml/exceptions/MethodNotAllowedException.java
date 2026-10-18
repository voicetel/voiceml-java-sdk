package com.voicetel.voiceml.exceptions;

import java.time.Duration;

/**
 * HTTP 405 — the path exists but does not accept this method. The server answers removed
 * endpoints this way (code {@code 20004}).
 */
public class MethodNotAllowedException extends ApiException {

    private static final long serialVersionUID = 1L;

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
    public MethodNotAllowedException(String message, int statusCode, Integer code, String body,
            String moreInfo, Duration retryAfter) {
        super(message, statusCode, code, body, moreInfo, retryAfter, null);
    }
}
