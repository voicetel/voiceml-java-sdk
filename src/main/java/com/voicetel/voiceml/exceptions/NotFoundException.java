package com.voicetel.voiceml.exceptions;

/** HTTP 404 — the resource does not exist (or belongs to a different tenant). */
public class NotFoundException extends ApiException {

    private static final long serialVersionUID = 1L;

    public NotFoundException(String message, int statusCode, Integer code, String body) {
        super(message, statusCode, code, body);
    }
}
