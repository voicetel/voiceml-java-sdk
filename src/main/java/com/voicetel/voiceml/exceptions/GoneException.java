package com.voicetel.voiceml.exceptions;

/** HTTP 410 — recording audio is no longer available (no local file, no S3 key). */
public class GoneException extends ApiException {

    private static final long serialVersionUID = 1L;

    public GoneException(String message, int statusCode, Integer code, String body) {
        super(message, statusCode, code, body);
    }
}
