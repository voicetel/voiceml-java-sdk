package com.voicetel.voiceml.models;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

/** Narrows the {@code BankAccountType} field on a Pay session. */
public enum PaymentBankAccountType {

    CONSUMER_CHECKING("consumer-checking"),
    CONSUMER_SAVINGS("consumer-savings"),
    COMMERCIAL_CHECKING("commercial-checking");

    private final String value;

    PaymentBankAccountType(String value) {
        this.value = value;
    }

    @JsonValue
    public String getValue() {
        return value;
    }

    @JsonCreator
    public static PaymentBankAccountType fromString(String text) {
        if (text == null) {
            return null;
        }
        for (PaymentBankAccountType s : values()) {
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
