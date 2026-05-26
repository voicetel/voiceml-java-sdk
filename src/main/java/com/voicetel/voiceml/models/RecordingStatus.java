package com.voicetel.voiceml.models;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

/** Status of a Recording resource on the wire. */
public enum RecordingStatus {

    IN_PROGRESS("in-progress"),
    PAUSED("paused"),
    STOPPED("stopped"),
    PROCESSING("processing"),
    COMPLETED("completed"),
    ABSENT("absent"),
    DELETED("deleted");

    private final String value;

    RecordingStatus(String value) {
        this.value = value;
    }

    @JsonValue
    public String getValue() {
        return value;
    }

    @JsonCreator
    public static RecordingStatus fromString(String text) {
        if (text == null) {
            return null;
        }
        for (RecordingStatus s : values()) {
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
