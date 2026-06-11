package com.voicetel.voiceml.models;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Form body for {@code POST /Calls/{CallSid}/Payments/{PaymentSid}}.
 *
 * <p>Either advance the session ({@code capture=...}) or terminate it
 * ({@code status=complete} captures, {@code status=cancel} aborts).
 */
public final class UpdatePaymentRequest {

    private final String idempotencyKey;
    private final String statusCallback;
    private final PaymentCapture capture;
    private final PaymentSessionStatus status;

    private UpdatePaymentRequest(Builder b) {
        this.idempotencyKey = b.idempotencyKey;
        this.statusCallback = b.statusCallback;
        this.capture = b.capture;
        this.status = b.status;
    }

    public Map<String, Object> toForm() {
        Map<String, Object> form = new LinkedHashMap<>();
        if (idempotencyKey != null) form.put("IdempotencyKey", idempotencyKey);
        if (statusCallback != null) form.put("StatusCallback", statusCallback);
        if (capture != null) form.put("Capture", capture.getValue());
        if (status != null) form.put("Status", status.getValue());
        return form;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder {
        private String idempotencyKey;
        private String statusCallback;
        private PaymentCapture capture;
        private PaymentSessionStatus status;

        public Builder idempotencyKey(String s) { this.idempotencyKey = s; return this; }
        public Builder statusCallback(String s) { this.statusCallback = s; return this; }
        public Builder capture(PaymentCapture v) { this.capture = v; return this; }
        public Builder status(PaymentSessionStatus v) { this.status = v; return this; }

        public UpdatePaymentRequest build() {
            return new UpdatePaymentRequest(this);
        }
    }
}
