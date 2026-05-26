package com.voicetel.voiceml.models;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

/** Status of a Call resource on the wire. */
public enum CallStatus {

    QUEUED("queued"),
    RINGING("ringing"),
    IN_PROGRESS("in-progress"),
    COMPLETED("completed"),
    BUSY("busy"),
    NO_ANSWER("no-answer"),
    CANCELED("canceled"),
    FAILED("failed");

    private final String value;

    CallStatus(String value) {
        this.value = value;
    }

    @JsonValue
    public String getValue() {
        return value;
    }

    @JsonCreator
    public static CallStatus fromString(String text) {
        if (text == null) {
            return null;
        }
        for (CallStatus s : values()) {
            if (s.value.equals(text)) {
                return s;
            }
        }
        return null;
    }

    @Override
    public String toString() {
        return value;
    }
}
