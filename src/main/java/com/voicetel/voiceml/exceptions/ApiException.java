package com.voicetel.voiceml.exceptions;

import java.time.Duration;

/**
 * Raised when a request to the VoiceML API fails: a non-2xx response, a response that could not
 * be decoded, or (via {@link TransportException}) a network-level failure.
 *
 * <p>Subclasses cover specific status families; catch {@link ApiException} to handle them all.
 * The Twilio-compatible error body ({@code {code, message, more_info, status}}) is parsed into
 * {@link #getCode()} / {@link #getMessage()} / {@link #getMoreInfo()} when present, with the raw
 * payload exposed on {@link #getBody()}. A non-JSON error body still produces an
 * {@code ApiException}, with the HTTP status as the message and the raw text on {@link #getBody()}.
 *
 * <p>Instances are immutable.
 */
public class ApiException extends VoiceMLException {

    private static final long serialVersionUID = 2L;

    private final int statusCode;
    private final Integer code;
    private final String body;
    private final String moreInfo;
    private final Duration retryAfter;

    /**
     * Create an exception without {@code more_info} or {@code Retry-After} details.
     *
     * @param message human-readable message
     * @param statusCode HTTP status, or {@code 0} for failures before a response was received
     * @param code VoiceML error code from the response body, or {@code null}
     * @param body raw response body, or {@code null}
     */
    public ApiException(String message, int statusCode, Integer code, String body) {
        this(message, statusCode, code, body, null, null, null);
    }

    /**
     * Create an exception with an underlying cause.
     *
     * @param message human-readable message
     * @param statusCode HTTP status, or {@code 0} for failures before a response was received
     * @param code VoiceML error code from the response body, or {@code null}
     * @param body raw response body, or {@code null}
     * @param cause underlying cause (kept on {@link #getCause()})
     */
    public ApiException(String message, int statusCode, Integer code, String body, Throwable cause) {
        this(message, statusCode, code, body, null, null, cause);
    }

    /**
     * Create an exception carrying every detail parsed from an error response.
     *
     * @param message human-readable message
     * @param statusCode HTTP status, or {@code 0} for failures before a response was received
     * @param code VoiceML error code from the response body, or {@code null}
     * @param body raw response body, or {@code null}
     * @param moreInfo {@code more_info} URL from the error envelope, or {@code null}
     * @param retryAfter parsed {@code Retry-After} response header, or {@code null}
     * @param cause underlying cause, or {@code null}
     */
    public ApiException(String message, int statusCode, Integer code, String body,
                        String moreInfo, Duration retryAfter, Throwable cause) {
        super(message, cause);
        this.statusCode = statusCode;
        this.code = code;
        this.body = body;
        this.moreInfo = moreInfo;
        this.retryAfter = retryAfter;
    }

    /**
     * The HTTP status code from the response.
     *
     * @return the status, or {@code 0} when the failure happened before a response was received
     */
    public int getStatusCode() {
        return statusCode;
    }

    /**
     * The numeric VoiceML error code from the response body.
     *
     * @return the code, or {@code null} when absent
     */
    public Integer getCode() {
        return code;
    }

    /**
     * The raw response body.
     *
     * @return the body decoded as UTF-8, or {@code null} when no body was returned
     */
    public String getBody() {
        return body;
    }

    /**
     * The {@code more_info} URL parsed from the error envelope. Same accessor name as
     * twilio-java's {@code ApiException.getMoreInfo()}, so error-handling code ports directly.
     *
     * @return the URL, or {@code null} when the response did not include one
     */
    public String getMoreInfo() {
        return moreInfo;
    }

    /**
     * The delay the server asked for in its {@code Retry-After} header, parsed from either
     * delta-seconds or an HTTP-date.
     *
     * @return the delay, or {@code null} when the response had no (parseable) header
     */
    public Duration getRetryAfter() {
        return retryAfter;
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
