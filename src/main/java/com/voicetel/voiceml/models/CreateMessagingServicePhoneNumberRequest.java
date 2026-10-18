package com.voicetel.voiceml.models;

import java.util.LinkedHashMap;
import java.util.Map;

/** Form body for {@code POST /v1/Services/{Sid}/PhoneNumbers}: add a number to the sender pool. */
public final class CreateMessagingServicePhoneNumberRequest {

    private final String phoneNumberSid;

    private CreateMessagingServicePhoneNumberRequest(Builder b) {
        this.phoneNumberSid = b.phoneNumberSid;
    }

    /** @return the form fields, keyed by wire name; unset values are omitted */
    public Map<String, Object> toForm() {
        Map<String, Object> m = new LinkedHashMap<>();
        if (phoneNumberSid != null) m.put("PhoneNumberSid", phoneNumberSid);
        return m;
    }

    /** @return the {@code PhoneNumberSid} value, or {@code null} when unset */
    public String getPhoneNumberSid() {
        return phoneNumberSid;
    }

    /** @return a new builder */
    public static Builder builder() {
        return new Builder();
    }

    /** Builder for {@link CreateMessagingServicePhoneNumberRequest}. */
    public static final class Builder {
        private String phoneNumberSid;

        /** Create an empty builder; prefer {@link CreateMessagingServicePhoneNumberRequest#builder()}. */
        public Builder() {
            // all fields start unset
        }

        /**
         * @param v {@code PhoneNumberSid} (required)
         * @return this builder
         */
        public Builder phoneNumberSid(String v) {
            this.phoneNumberSid = v;
            return this;
        }

        /**
         * @return the request
         */
        public CreateMessagingServicePhoneNumberRequest build() {
            return new CreateMessagingServicePhoneNumberRequest(this);
        }
    }
}
