package com.voicetel.voiceml.exceptions;

/**
 * Raised when the VoiceML API returns a non-2xx response.
 *
 * <p>Subclasses cover specific status families; catch {@link ApiException} to handle them all.
 * The Twilio-shape error body ({@code {code, message, more_info, status}}) is parsed into
 * {@link #getCode()} / {@link #getMessage()} when present, with the raw payload exposed on
 * {@link #getBody()}.
 */
public class ApiException extends VoiceMLException {

    private static final long serialVersionUID = 1L;

    private final int statusCode;
    private final Integer code;
    private final String body;

    public ApiException(String message, int statusCode, Integer code, String body) {
        super(message);
        this.statusCode = statusCode;
        this.code = code;
        this.body = body;
    }

    public ApiException(String message, int statusCode, Integer code, String body, Throwable cause) {
        super(message, cause);
        this.statusCode = statusCode;
        this.code = code;
        this.body = body;
    }

    /** The HTTP status code from the response. {@code 0} when the failure was at the transport layer. */
    public int getStatusCode() {
        return statusCode;
    }

    /** The Twilio-style numeric error code from the response body, or {@code null} when absent. */
    public Integer getCode() {
        return code;
    }

    /** The raw response body (already decoded to a string), or {@code null} when no body was returned. */
    public String getBody() {
        return body;
    }

    @Override
    public String toString() {
        return getClass().getSimpleName()
                + "(statusCode=" + statusCode
                + ", code=" + code
                + ", message=" + getMessage()
                + ")";
    }
}
