package com.voicetel.voiceml.exceptions;

/**
 * A pagination link could not be followed safely: it points at a host other than the configured
 * product host (the SDK never sends credentials elsewhere), uses an unsupported scheme, or does not
 * advance past the page that produced it.
 */
public class PaginationException extends VoiceMLException {

    private static final long serialVersionUID = 1L;

    /**
     * Create the exception.
     *
     * @param message what was wrong with the link
     */
    public PaginationException(String message) {
        super(message);
    }
}
