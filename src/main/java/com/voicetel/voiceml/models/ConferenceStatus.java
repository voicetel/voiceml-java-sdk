package com.voicetel.voiceml.models;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

/** Status of a Conference resource on the wire. */
public enum ConferenceStatus {

    INIT("init"),
    IN_PROGRESS("in-progress"),
    COMPLETED("completed");

    private final String value;

    ConferenceStatus(String value) {
        this.value = value;
    }

    @JsonValue
    public String getValue() {
        return value;
    }

    @JsonCreator
    public static ConferenceStatus fromString(String text) {
        if (text == null) {
            return null;
        }
        for (ConferenceStatus s : values()) {
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
