package com.voicetel.voiceml.models;

import com.fasterxml.jackson.annotation.JsonEnumDefaultValue;
import com.fasterxml.jackson.annotation.JsonValue;

/** Status of a Recording resource on the wire. */
public enum RecordingStatus {

    IN_PROGRESS("in-progress"),
    PAUSED("paused"),
    STOPPED("stopped"),
    PROCESSING("processing"),
    COMPLETED("completed"),
    ABSENT("absent"),
    DELETED("deleted"),

    /**
     * Placeholder for a value this SDK version does not recognise. Decode-only: the server may add
     * values at any time, and they decode to {@code UNKNOWN} instead of failing. The SDK refuses to
     * send it.
     */
    @JsonEnumDefaultValue
    UNKNOWN("unknown");

    private final String value;

    RecordingStatus(String value) {
        this.value = value;
    }

    /** @return the wire value */
    @JsonValue
    public String getValue() {
        return value;
    }

    /**
     * Parse a wire value.
     *
     * @param text the wire value
     * @return the constant, {@code UNKNOWN} for an unrecognised value, or {@code null} for {@code null}
     */
    public static RecordingStatus fromString(String text) {
        if (text == null) {
            return null;
        }
        for (RecordingStatus s : values()) {
            if (s.value.equals(text)) {
                return s;
            }
        }
        return UNKNOWN;
    }

    @Override
    public String toString() {
        return value;
    }
}
