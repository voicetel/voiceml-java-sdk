package com.voicetel.voiceml.models;

import com.fasterxml.jackson.annotation.JsonEnumDefaultValue;
import com.fasterxml.jackson.annotation.JsonValue;

/** Direction of a Call resource on the wire. */
public enum CallDirection {

    INBOUND("inbound"),
    OUTBOUND_API("outbound-api"),
    OUTBOUND_DIAL("outbound-dial"),

    /**
     * Placeholder for a value this SDK version does not recognise. Decode-only: the server may add
     * values at any time, and they decode to {@code UNKNOWN} instead of failing. The SDK refuses to
     * send it.
     */
    @JsonEnumDefaultValue
    UNKNOWN("unknown");

    private final String value;

    CallDirection(String value) {
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
    public static CallDirection fromString(String text) {
        if (text == null) {
            return null;
        }
        for (CallDirection d : values()) {
            if (d.value.equals(text)) {
                return d;
            }
        }
        return UNKNOWN;
    }

    @Override
    public String toString() {
        return value;
    }
}
