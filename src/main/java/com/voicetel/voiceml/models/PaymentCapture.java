package com.voicetel.voiceml.models;

import com.fasterxml.jackson.annotation.JsonEnumDefaultValue;
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
    POSTAL_CODE_MATCHER("postal-code-matcher"),

    /**
     * Placeholder for a value this SDK version does not recognise. Decode-only: the server may add
     * values at any time, and they decode to {@code UNKNOWN} instead of failing. The SDK refuses to
     * send it.
     */
    @JsonEnumDefaultValue
    UNKNOWN("unknown");

    private final String value;

    PaymentCapture(String value) {
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
    public static PaymentCapture fromString(String text) {
        if (text == null) {
            return null;
        }
        for (PaymentCapture s : values()) {
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
