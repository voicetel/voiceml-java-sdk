package com.voicetel.voiceml.models;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

/** Narrows the {@code Input} field on a Pay session. DTMF is the only supported value today. */
public enum PaymentInput {

    DTMF("dtmf");

    private final String value;

    PaymentInput(String value) {
        this.value = value;
    }

    @JsonValue
    public String getValue() {
        return value;
    }

    @JsonCreator
    public static PaymentInput fromString(String text) {
        if (text == null) {
            return null;
        }
        for (PaymentInput s : values()) {
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
