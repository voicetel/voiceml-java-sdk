package com.voicetel.voiceml.models;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

/**
 * Narrows the {@code Capture} field on Pay-session updates — tells the runtime which input the
 * user is about to type next.
 */
public enum PaymentCapture {

    PAYMENT_CARD_NUMBER("payment-card-number"),
    EXPIRATION_DATE("expiration-date"),
    SECURITY_CODE("security-code"),
    POSTAL_CODE("postal-code"),
    BANK_ROUTING_NUMBER("bank-routing-number"),
    BANK_ACCOUNT_NUMBER("bank-account-number"),
    PAYMENT_CARD_NUMBER_MATCHER("payment-card-number-matcher"),
    EXPIRATION_DATE_MATCHER("expiration-date-matcher"),
    SECURITY_CODE_MATCHER("security-code-matcher"),
    POSTAL_CODE_MATCHER("postal-code-matcher");

    private final String value;

    PaymentCapture(String value) {
        this.value = value;
    }

    @JsonValue
    public String getValue() {
        return value;
    }

    @JsonCreator
    public static PaymentCapture fromString(String text) {
        if (text == null) {
            return null;
        }
        for (PaymentCapture s : values()) {
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
