package com.voicetel.voiceml.exceptions;

/** Raised when the client is constructed with missing or conflicting configuration. */
public class ConfigurationException extends VoiceMLException {

    private static final long serialVersionUID = 1L;

    /**
     * Create the exception.
     *
     * @param message what was wrong with the configuration
     */
    public ConfigurationException(String message) {
        super(message);
    }
}
