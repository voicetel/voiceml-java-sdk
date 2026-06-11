package com.voicetel.voiceml.models;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

/** Narrows the {@code TokenType} field on a Pay session. */
public enum PaymentTokenType {

    ONE_TIME("one-time"),
    REUSABLE("reusable"),
    PAYMENT_METHOD("payment-method");

    private final String value;

    PaymentTokenType(String value) {
        this.value = value;
    }

    @JsonValue
    public String getValue() {
        return value;
    }

    @JsonCreator
    public static PaymentTokenType fromString(String text) {
        if (text == null) {
            return null;
        }
        for (PaymentTokenType s : values()) {
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
