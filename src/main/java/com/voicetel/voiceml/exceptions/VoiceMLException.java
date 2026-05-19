package com.voicetel.voiceml.exceptions;

/**
 * Base class for every exception raised by the VoiceML SDK.
 *
 * <p>All SDK exceptions are unchecked ({@link RuntimeException}) — the Twilio-Java convention.
 * Catch this type to handle any SDK-originated failure regardless of cause.
 */
public class VoiceMLException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public VoiceMLException(String message) {
        super(message);
    }

    public VoiceMLException(String message, Throwable cause) {
        super(message, cause);
    }
}
