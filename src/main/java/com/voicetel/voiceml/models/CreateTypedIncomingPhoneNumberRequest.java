package com.voicetel.voiceml.models;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Form body for {@code POST /IncomingPhoneNumbers/Local}, {@code /Mobile} and {@code /TollFree},
 * which accept only {@code PhoneNumber}, {@code VoiceUrl} and {@code VoiceMethod}.
 */
public final class CreateTypedIncomingPhoneNumberRequest {

    private final String phoneNumber;
    private final String voiceUrl;
    private final String voiceMethod;

    private CreateTypedIncomingPhoneNumberRequest(Builder b) {
        this.phoneNumber = b.phoneNumber;
        this.voiceUrl = b.voiceUrl;
        this.voiceMethod = b.voiceMethod;
    }

    /** @return the form fields, keyed by wire name; unset values are omitted */
    public Map<String, Object> toForm() {
        Map<String, Object> m = new LinkedHashMap<>();
        if (phoneNumber != null) m.put("PhoneNumber", phoneNumber);
        if (voiceUrl != null) m.put("VoiceUrl", voiceUrl);
        if (voiceMethod != null) m.put("VoiceMethod", voiceMethod);
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

    /** @return a new builder */
    public static Builder builder() {
        return new Builder();
    }

    /** Builder for {@link CreateTypedIncomingPhoneNumberRequest}. */
    public static final class Builder {
        private String phoneNumber;
        private String voiceUrl;
        private String voiceMethod;

        /** Create an empty builder; prefer {@link CreateTypedIncomingPhoneNumberRequest#builder()}. */
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
         * @return the request
         */
        public CreateTypedIncomingPhoneNumberRequest build() {
            return new CreateTypedIncomingPhoneNumberRequest(this);
        }
    }
}
