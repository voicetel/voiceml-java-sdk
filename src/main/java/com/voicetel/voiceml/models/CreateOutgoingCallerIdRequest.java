package com.voicetel.voiceml.models;

import java.util.LinkedHashMap;
import java.util.Map;

/** Form body for {@code POST /OutgoingCallerIds}: start validating a caller ID. */
public final class CreateOutgoingCallerIdRequest {

    private final String phoneNumber;
    private final String friendlyName;

    private CreateOutgoingCallerIdRequest(Builder b) {
        this.phoneNumber = b.phoneNumber;
        this.friendlyName = b.friendlyName;
    }

    /** @return the form fields, keyed by wire name; unset values are omitted */
    public Map<String, Object> toForm() {
        Map<String, Object> m = new LinkedHashMap<>();
        if (phoneNumber != null) m.put("PhoneNumber", phoneNumber);
        if (friendlyName != null) m.put("FriendlyName", friendlyName);
        return m;
    }

    /** @return the {@code PhoneNumber} value, or {@code null} when unset */
    public String getPhoneNumber() {
        return phoneNumber;
    }

    /** @return the {@code FriendlyName} value, or {@code null} when unset */
    public String getFriendlyName() {
        return friendlyName;
    }

    /** @return a new builder */
    public static Builder builder() {
        return new Builder();
    }

    /** Builder for {@link CreateOutgoingCallerIdRequest}. */
    public static final class Builder {
        private String phoneNumber;
        private String friendlyName;

        /** Create an empty builder; prefer {@link CreateOutgoingCallerIdRequest#builder()}. */
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
         * @param v {@code FriendlyName}
         * @return this builder
         */
        public Builder friendlyName(String v) {
            this.friendlyName = v;
            return this;
        }

        /**
         * @return the request
         */
        public CreateOutgoingCallerIdRequest build() {
            return new CreateOutgoingCallerIdRequest(this);
        }
    }
}
