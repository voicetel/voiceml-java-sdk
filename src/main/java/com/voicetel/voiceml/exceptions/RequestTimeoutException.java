package com.voicetel.voiceml.exceptions;

/**
 * A request exceeded the configured connect or per-request timeout.
 *
 * <p>The JDK's {@link java.net.http.HttpTimeoutException} is preserved on {@link #getCause()}.
 * For a {@code POST} that timed out while waiting for the response, the server may still have
 * processed the request, so the SDK does not retry it.
 */
public class RequestTimeoutException extends TransportException {

    private static final long serialVersionUID = 1L;

    /**
     * Create the exception.
     *
     * @param message human-readable message
     * @param cause underlying cause, or {@code null}
     */
    public RequestTimeoutException(String message, Throwable cause) {
        super(message, cause);
    }
}
