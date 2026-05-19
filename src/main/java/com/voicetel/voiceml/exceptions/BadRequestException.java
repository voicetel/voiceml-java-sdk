package com.voicetel.voiceml.exceptions;

/** HTTP 400 — the request was malformed or failed server-side validation. */
public class BadRequestException extends ApiException {

    private static final long serialVersionUID = 1L;

    public BadRequestException(String message, int statusCode, Integer code, String body) {
        super(message, statusCode, code, body);
    }
}
