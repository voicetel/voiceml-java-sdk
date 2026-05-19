package com.voicetel.voiceml.exceptions;

/** Raised when the client is constructed with missing or conflicting configuration. */
public class ConfigurationException extends VoiceMLException {

    private static final long serialVersionUID = 1L;

    public ConfigurationException(String message) {
        super(message);
    }
}
