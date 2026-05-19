package com.voicetel.voiceml.exceptions;

/**
 * HTTP 401 — Basic auth missing, account unknown, key wrong, or source IP not allowed.
 *
 * <p>The server intentionally returns an identical 401 for all four failure modes — see the
 * Twilio-compat spec's "Unauthorized" response description.
 */
public class AuthenticationException extends ApiException {

    private static final long serialVersionUID = 1L;

    public AuthenticationException(String message, int statusCode, Integer code, String body) {
        super(message, statusCode, code, body);
    }
}
