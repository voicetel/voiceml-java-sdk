package com.voicetel.voiceml.models;

import java.util.LinkedHashMap;
import java.util.Map;

/** Form body for {@code POST /OutgoingCallerIds/{Sid}}. */
public final class UpdateOutgoingCallerIdRequest {

    private final String friendlyName;

    private UpdateOutgoingCallerIdRequest(Builder b) {
        this.friendlyName = b.friendlyName;
    }

    /** @return the form fields, keyed by wire name; unset values are omitted */
    public Map<String, Object> toForm() {
        Map<String, Object> m = new LinkedHashMap<>();
        if (friendlyName != null) m.put("FriendlyName", friendlyName);
        return m;
    }

    /** @return the {@code FriendlyName} value, or {@code null} when unset */
    public String getFriendlyName() {
        return friendlyName;
    }

    /** @return a new builder */
    public static Builder builder() {
        return new Builder();
    }

    /** Builder for {@link UpdateOutgoingCallerIdRequest}. */
    public static final class Builder {
        private String friendlyName;

        /** Create an empty builder; prefer {@link UpdateOutgoingCallerIdRequest#builder()}. */
        public Builder() {
            // all fields start unset
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
        public UpdateOutgoingCallerIdRequest build() {
            return new UpdateOutgoingCallerIdRequest(this);
        }
    }
}
