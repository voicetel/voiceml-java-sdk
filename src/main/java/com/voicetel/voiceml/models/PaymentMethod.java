package com.voicetel.voiceml.models;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

/** Narrows the {@code PaymentMethod} field on a Pay session. */
public enum PaymentMethod {

    CREDIT_CARD("credit-card"),
    ACH_DEBIT("ach-debit");

    private final String value;

    PaymentMethod(String value) {
        this.value = value;
    }

    @JsonValue
    public String getValue() {
        return value;
    }

    @JsonCreator
    public static PaymentMethod fromString(String text) {
        if (text == null) {
            return null;
        }
        for (PaymentMethod s : values()) {
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
