package com.voicetel.voiceml.exceptions;

/** HTTP 5xx — the server hit an error processing the request. */
public class ServerException extends ApiException {

    private static final long serialVersionUID = 1L;

    public ServerException(String message, int statusCode, Integer code, String body) {
        super(message, statusCode, code, body);
    }
}
