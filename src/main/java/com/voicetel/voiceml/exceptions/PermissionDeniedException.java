package com.voicetel.voiceml.exceptions;

/** HTTP 403 — authenticated, but not allowed to perform this action. */
public class PermissionDeniedException extends ApiException {

    private static final long serialVersionUID = 1L;

    public PermissionDeniedException(String message, int statusCode, Integer code, String body) {
        super(message, statusCode, code, body);
    }
}
