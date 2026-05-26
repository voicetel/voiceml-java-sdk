package com.voicetel.voiceml.models;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

/** Direction of a Call resource on the wire. */
public enum CallDirection {

    INBOUND("inbound"),
    OUTBOUND_API("outbound-api"),
    OUTBOUND_DIAL("outbound-dial");

    private final String value;

    CallDirection(String value) {
        this.value = value;
    }

    @JsonValue
    public String getValue() {
        return value;
    }

    @JsonCreator
    public static CallDirection fromString(String text) {
        if (text == null) {
            return null;
        }
        for (CallDirection d : values()) {
            if (d.value.equals(text)) {
                return d;
            }
        }
        return null;
    }

    @Override
    public String toString() {
        return value;
    }
}
