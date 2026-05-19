package com.voicetel.voiceml.exceptions;

/**
 * HTTP 409 — request conflicts with current resource state.
 *
 * <p>Typical case: deleting a queue that still has waiting members.
 */
public class ConflictException extends ApiException {

    private static final long serialVersionUID = 1L;

    public ConflictException(String message, int statusCode, Integer code, String body) {
        super(message, statusCode, code, body);
    }
}
