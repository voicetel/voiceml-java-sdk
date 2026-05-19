package com.voicetel.voiceml.exceptions;

/** HTTP 501 — endpoint is mounted as a stub (e.g. {@code UserDefinedMessages}). */
public class NotImplementedException extends ApiException {

    private static final long serialVersionUID = 1L;

    public NotImplementedException(String message, int statusCode, Integer code, String body) {
        super(message, statusCode, code, body);
    }
}
