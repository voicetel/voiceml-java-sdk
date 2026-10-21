package com.voicetel.voiceml.models;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Form body for {@code POST /IncomingPhoneNumbers}.
 *
 * <p>Idempotent on the same account: re-POSTing the same {@code PhoneNumber} rebinds routing
 * without erroring; the server returns {@code 409} when the number is already assigned to a
 * different account. {@code SmsApplicationSid} ({@code AP} + 32 hex) routes inbound SMS to an
 * Application.
 */
public final class CreateIncomingPhoneNumberRequest {

    private final String phoneNumber;
    private final String voiceUrl;
    private final String voiceMethod;
    private final String voiceFallbackUrl;
    private final String voiceFallbackMethod;
    private final String smsUrl;
    private final String smsMethod;
    private final String smsFallbackUrl;
    private final String smsFallbackMethod;
    private final String smsApplicationSid;
    private final String voiceApplicationSid;
    private final String statusCallback;
    private final String statusCallbackMethod;

    private CreateIncomingPhoneNumberRequest(Builder b) {
        this.phoneNumber = b.phoneNumber;
        this.voiceUrl = b.voiceUrl;
        this.voiceMethod = b.voiceMethod;
        this.voiceFallbackUrl = b.voiceFallbackUrl;
        this.voiceFallbackMethod = b.voiceFallbackMethod;
        this.smsUrl = b.smsUrl;
        this.smsMethod = b.smsMethod;
        this.smsFallbackUrl = b.smsFallbackUrl;
        this.smsFallbackMethod = b.smsFallbackMethod;
        this.smsApplicationSid = b.smsApplicationSid;
        this.voiceApplicationSid = b.voiceApplicationSid;
        this.statusCallback = b.statusCallback;
        this.statusCallbackMethod = b.statusCallbackMethod;
    }

    /** @return the form fields, keyed by wire name; unset values are omitted */
    public Map<String, Object> toForm() {
        Map<String, Object> m = new LinkedHashMap<>();
        if (phoneNumber != null) m.put("PhoneNumber", phoneNumber);
        if (voiceUrl != null) m.put("VoiceUrl", voiceUrl);
        if (voiceMethod != null) m.put("VoiceMethod", voiceMethod);
        if (voiceFallbackUrl != null) m.put("VoiceFallbackUrl", voiceFallbackUrl);
        if (voiceFallbackMethod != null) m.put("VoiceFallbackMethod", voiceFallbackMethod);
        if (smsUrl != null) m.put("SmsUrl", smsUrl);
        if (smsMethod != null) m.put("SmsMethod", smsMethod);
        if (smsFallbackUrl != null) m.put("SmsFallbackUrl", smsFallbackUrl);
        if (smsFallbackMethod != null) m.put("SmsFallbackMethod", smsFallbackMethod);
        if (smsApplicationSid != null) m.put("SmsApplicationSid", smsApplicationSid);
        if (voiceApplicationSid != null) m.put("VoiceApplicationSid", voiceApplicationSid);
        if (statusCallback != null) m.put("StatusCallback", statusCallback);
        if (statusCallbackMethod != null) m.put("StatusCallbackMethod", statusCallbackMethod);
        return m;
    }

    /** @return the {@code PhoneNumber} value, or {@code null} when unset */
    public String getPhoneNumber() {
        return phoneNumber;
    }

    /** @return the {@code VoiceUrl} value, or {@code null} when unset */
    public String getVoiceUrl() {
        return voiceUrl;
    }

    /** @return the {@code VoiceMethod} value, or {@code null} when unset */
    public String getVoiceMethod() {
        return voiceMethod;
    }

    /** @return the {@code VoiceFallbackUrl} value, or {@code null} when unset */
    public String getVoiceFallbackUrl() {
        return voiceFallbackUrl;
    }

    /** @return the {@code VoiceFallbackMethod} value, or {@code null} when unset */
    public String getVoiceFallbackMethod() {
        return voiceFallbackMethod;
    }

    /** @return the {@code SmsUrl} value, or {@code null} when unset */
    public String getSmsUrl() {
        return smsUrl;
    }

    /** @return the {@code SmsMethod} value, or {@code null} when unset */
    public String getSmsMethod() {
        return smsMethod;
    }

    /** @return the {@code SmsFallbackUrl} value, or {@code null} when unset */
    public String getSmsFallbackUrl() {
        return smsFallbackUrl;
    }

    /** @return the {@code SmsFallbackMethod} value, or {@code null} when unset */
    public String getSmsFallbackMethod() {
        return smsFallbackMethod;
    }

    /** @return the {@code SmsApplicationSid} value, or {@code null} when unset */
    public String getSmsApplicationSid() {
        return smsApplicationSid;
    }

    /** @return the {@code VoiceApplicationSid} value, or {@code null} when unset */
    public String getVoiceApplicationSid() {
        return voiceApplicationSid;
    }

    /** @return the {@code StatusCallback} value, or {@code null} when unset */
    public String getStatusCallback() {
        return statusCallback;
    }

    /** @return the {@code StatusCallbackMethod} value, or {@code null} when unset */
    public String getStatusCallbackMethod() {
        return statusCallbackMethod;
    }

    /** @return a new builder */
    public static Builder builder() {
        return new Builder();
    }

    /** Builder for {@link CreateIncomingPhoneNumberRequest}. */
    public static final class Builder {
        private String phoneNumber;
        private String voiceUrl;
        private String voiceMethod;
        private String voiceFallbackUrl;
        private String voiceFallbackMethod;
        private String smsUrl;
        private String smsMethod;
        private String smsFallbackUrl;
        private String smsFallbackMethod;
        private String smsApplicationSid;
        private String voiceApplicationSid;
        private String statusCallback;
        private String statusCallbackMethod;

        /** Create an empty builder; prefer {@link CreateIncomingPhoneNumberRequest#builder()}. */
        public Builder() {
            // all fields start unset
        }

        /**
         * @param v {@code PhoneNumber} (required)
         * @return this builder
         */
        public Builder phoneNumber(String v) {
            this.phoneNumber = v;
            return this;
        }

        /**
         * @param v {@code VoiceUrl}
         * @return this builder
         */
        public Builder voiceUrl(String v) {
            this.voiceUrl = v;
            return this;
        }

        /**
         * @param v {@code VoiceMethod}: one of {@code GET}, {@code POST}
         * @return this builder
         */
        public Builder voiceMethod(String v) {
            this.voiceMethod = v;
            return this;
        }

        /**
         * @param v {@code VoiceFallbackUrl}
         * @return this builder
         */
        public Builder voiceFallbackUrl(String v) {
            this.voiceFallbackUrl = v;
            return this;
        }

        /**
         * @param v {@code VoiceFallbackMethod}: one of {@code GET}, {@code POST}
         * @return this builder
         */
        public Builder voiceFallbackMethod(String v) {
            this.voiceFallbackMethod = v;
            return this;
        }

        /**
         * @param v {@code SmsUrl}
         * @return this builder
         */
        public Builder smsUrl(String v) {
            this.smsUrl = v;
            return this;
        }

        /**
         * @param v {@code SmsMethod}: one of {@code GET}, {@code POST}
         * @return this builder
         */
        public Builder smsMethod(String v) {
            this.smsMethod = v;
            return this;
        }

        /**
         * @param v {@code SmsFallbackUrl}
         * @return this builder
         */
        public Builder smsFallbackUrl(String v) {
            this.smsFallbackUrl = v;
            return this;
        }

        /**
         * @param v {@code SmsFallbackMethod}: one of {@code GET}, {@code POST}
         * @return this builder
         */
        public Builder smsFallbackMethod(String v) {
            this.smsFallbackMethod = v;
            return this;
        }

        /**
         * @param v {@code SmsApplicationSid}
         * @return this builder
         */
        public Builder smsApplicationSid(String v) {
            this.smsApplicationSid = v;
            return this;
        }

        /**
         * @param v {@code VoiceApplicationSid}: an {@code AP} + 32 hex Application sid that
         *     routes inbound voice calls to the Application
         * @return this builder
         */
        public Builder voiceApplicationSid(String v) {
            this.voiceApplicationSid = v;
            return this;
        }

        /**
         * @param v {@code StatusCallback}: number-level lifecycle webhook URL for inbound calls
         * @return this builder
         */
        public Builder statusCallback(String v) {
            this.statusCallback = v;
            return this;
        }

        /**
         * @param v {@code StatusCallbackMethod}: one of {@code GET}, {@code POST}
         * @return this builder
         */
        public Builder statusCallbackMethod(String v) {
            this.statusCallbackMethod = v;
            return this;
        }

        /**
         * @return the request
         */
        public CreateIncomingPhoneNumberRequest build() {
            return new CreateIncomingPhoneNumberRequest(this);
        }
    }
}
