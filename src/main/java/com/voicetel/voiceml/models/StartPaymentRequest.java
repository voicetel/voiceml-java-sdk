package com.voicetel.voiceml.models;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Form body for {@code POST /Calls/{CallSid}/Payments}.
 *
 * <p>Every attribute the {@code <Pay>} TwiML verb accepts has a counterpart here.
 * {@code idempotencyKey} is persisted on the row for diagnostic visibility — replay-dedup is
 * not enforced today.
 */
public final class StartPaymentRequest {

    private final String idempotencyKey;
    private final String statusCallback;
    private final PaymentBankAccountType bankAccountType;
    private final String chargeAmount;
    private final String currency;
    private final String description;
    private final PaymentInput input;
    private final Integer minPostalCodeLength;
    private final String parameter;
    private final String paymentConnector;
    private final PaymentMethod paymentMethod;
    private final Boolean postalCode;
    private final Boolean securityCode;
    private final Integer timeout;
    private final PaymentTokenType tokenType;
    private final String validCardTypes;
    private final String requireMatchingInputs;
    private final Boolean confirmation;

    private StartPaymentRequest(Builder b) {
        this.idempotencyKey = b.idempotencyKey;
        this.statusCallback = b.statusCallback;
        this.bankAccountType = b.bankAccountType;
        this.chargeAmount = b.chargeAmount;
        this.currency = b.currency;
        this.description = b.description;
        this.input = b.input;
        this.minPostalCodeLength = b.minPostalCodeLength;
        this.parameter = b.parameter;
        this.paymentConnector = b.paymentConnector;
        this.paymentMethod = b.paymentMethod;
        this.postalCode = b.postalCode;
        this.securityCode = b.securityCode;
        this.timeout = b.timeout;
        this.tokenType = b.tokenType;
        this.validCardTypes = b.validCardTypes;
        this.requireMatchingInputs = b.requireMatchingInputs;
        this.confirmation = b.confirmation;
    }

    public Map<String, Object> toForm() {
        Map<String, Object> form = new LinkedHashMap<>();
        if (idempotencyKey != null) form.put("IdempotencyKey", idempotencyKey);
        if (statusCallback != null) form.put("StatusCallback", statusCallback);
        if (bankAccountType != null) form.put("BankAccountType", bankAccountType.getValue());
        if (chargeAmount != null) form.put("ChargeAmount", chargeAmount);
        if (currency != null) form.put("Currency", currency);
        if (description != null) form.put("Description", description);
        if (input != null) form.put("Input", input.getValue());
        if (minPostalCodeLength != null) form.put("MinPostalCodeLength", minPostalCodeLength);
        if (parameter != null) form.put("Parameter", parameter);
        if (paymentConnector != null) form.put("PaymentConnector", paymentConnector);
        if (paymentMethod != null) form.put("PaymentMethod", paymentMethod.getValue());
        if (postalCode != null) form.put("PostalCode", postalCode);
        if (securityCode != null) form.put("SecurityCode", securityCode);
        if (timeout != null) form.put("Timeout", timeout);
        if (tokenType != null) form.put("TokenType", tokenType.getValue());
        if (validCardTypes != null) form.put("ValidCardTypes", validCardTypes);
        if (requireMatchingInputs != null) form.put("RequireMatchingInputs", requireMatchingInputs);
        if (confirmation != null) form.put("Confirmation", confirmation);
        return form;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder {
        private String idempotencyKey;
        private String statusCallback;
        private PaymentBankAccountType bankAccountType;
        private String chargeAmount;
        private String currency;
        private String description;
        private PaymentInput input;
        private Integer minPostalCodeLength;
        private String parameter;
        private String paymentConnector;
        private PaymentMethod paymentMethod;
        private Boolean postalCode;
        private Boolean securityCode;
        private Integer timeout;
        private PaymentTokenType tokenType;
        private String validCardTypes;
        private String requireMatchingInputs;
        private Boolean confirmation;

        public Builder idempotencyKey(String s) { this.idempotencyKey = s; return this; }
        public Builder statusCallback(String s) { this.statusCallback = s; return this; }
        public Builder bankAccountType(PaymentBankAccountType v) { this.bankAccountType = v; return this; }
        public Builder chargeAmount(String s) { this.chargeAmount = s; return this; }
        public Builder currency(String s) { this.currency = s; return this; }
        public Builder description(String s) { this.description = s; return this; }
        public Builder input(PaymentInput v) { this.input = v; return this; }
        public Builder minPostalCodeLength(Integer v) { this.minPostalCodeLength = v; return this; }
        public Builder parameter(String s) { this.parameter = s; return this; }
        public Builder paymentConnector(String s) { this.paymentConnector = s; return this; }
        public Builder paymentMethod(PaymentMethod v) { this.paymentMethod = v; return this; }
        public Builder postalCode(Boolean v) { this.postalCode = v; return this; }
        public Builder securityCode(Boolean v) { this.securityCode = v; return this; }
        public Builder timeout(Integer v) { this.timeout = v; return this; }
        public Builder tokenType(PaymentTokenType v) { this.tokenType = v; return this; }
        public Builder validCardTypes(String s) { this.validCardTypes = s; return this; }
        public Builder requireMatchingInputs(String s) { this.requireMatchingInputs = s; return this; }
        public Builder confirmation(Boolean v) { this.confirmation = v; return this; }

        public StartPaymentRequest build() {
            return new StartPaymentRequest(this);
        }
    }
}
