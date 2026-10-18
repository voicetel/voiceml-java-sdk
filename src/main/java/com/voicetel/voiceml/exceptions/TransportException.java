package com.voicetel.voiceml.exceptions;

/**
 * A request failed before an HTTP response was received: DNS failure, connection refused or
 * reset, TLS failure, an unsafe redirect, or an interrupted request.
 *
 * <p>{@link #getStatusCode()} is {@code 0}; the underlying {@link java.io.IOException} (or
 * {@link InterruptedException}) is preserved on {@link #getCause()}.
 */
public class TransportException extends ApiException {

    private static final long serialVersionUID = 1L;

    /**
     * Create the exception.
     *
     * @param message human-readable message
     * @param cause underlying cause, or {@code null}
     */
    public TransportException(String message, Throwable cause) {
        super(message, 0, null, null, cause);
    }
}
