package com.voicetel.voiceml.models;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

/**
 * Narrows the {@code Status} field on Pay-session updates — terminate the session with
 * {@code complete} (capture) or {@code cancel} (abort).
 */
public enum PaymentSessionStatus {

    COMPLETE("complete"),
    CANCEL("cancel");

    private final String value;

    PaymentSessionStatus(String value) {
        this.value = value;
    }

    @JsonValue
    public String getValue() {
        return value;
    }

    @JsonCreator
    public static PaymentSessionStatus fromString(String text) {
        if (text == null) {
            return null;
        }
        for (PaymentSessionStatus s : values()) {
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
