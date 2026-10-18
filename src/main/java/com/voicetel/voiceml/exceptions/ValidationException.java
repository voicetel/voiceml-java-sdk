package com.voicetel.voiceml.exceptions;

/**
 * An argument was rejected before any request was sent, for example an empty path identifier or
 * a message with neither {@code Body} nor {@code MediaUrl}.
 */
public class ValidationException extends VoiceMLException {

    private static final long serialVersionUID = 1L;

    /**
     * Create the exception.
     *
     * @param message what was wrong with the argument
     */
    public ValidationException(String message) {
        super(message);
    }
}
