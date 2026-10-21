package com.voicetel.voiceml.models;

import java.util.LinkedHashMap;
import java.util.Map;

/** Form body for {@code POST /OutgoingCallerIds}: start validating a caller ID. */
public final class CreateOutgoingCallerIdRequest {

    private final String phoneNumber;
    private final String friendlyName;
    private final Integer callDelay;
    private final String extension;
    private final String statusCallback;
    private final String statusCallbackMethod;

    private CreateOutgoingCallerIdRequest(Builder b) {
        this.phoneNumber = b.phoneNumber;
        this.friendlyName = b.friendlyName;
        this.callDelay = b.callDelay;
        this.extension = b.extension;
        this.statusCallback = b.statusCallback;
        this.statusCallbackMethod = b.statusCallbackMethod;
    }

    /** @return the form fields, keyed by wire name; unset values are omitted */
    public Map<String, Object> toForm() {
        Map<String, Object> m = new LinkedHashMap<>();
        if (phoneNumber != null) m.put("PhoneNumber", phoneNumber);
        if (friendlyName != null) m.put("FriendlyName", friendlyName);
        if (callDelay != null) m.put("CallDelay", callDelay);
        if (extension != null) m.put("Extension", extension);
        if (statusCallback != null) m.put("StatusCallback", statusCallback);
        if (statusCallbackMethod != null) m.put("StatusCallbackMethod", statusCallbackMethod);
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

    /** @return the {@code CallDelay} value, or {@code null} when unset */
    public Integer getCallDelay() {
        return callDelay;
    }

    /** @return the {@code Extension} value, or {@code null} when unset */
    public String getExtension() {
        return extension;
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

    /** Builder for {@link CreateOutgoingCallerIdRequest}. */
    public static final class Builder {
        private String phoneNumber;
        private String friendlyName;
        private Integer callDelay;
        private String extension;
        private String statusCallback;
        private String statusCallbackMethod;

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
         * @param v {@code CallDelay}: seconds to wait before placing the validation call; the
         *     server enforces the {@code 0}–{@code 60} range
         * @return this builder
         */
        public Builder callDelay(Integer v) {
            this.callDelay = v;
            return this;
        }

        /**
         * @param v {@code Extension}: extension digits to dial after the validation call connects
         * @return this builder
         */
        public Builder extension(String v) {
            this.extension = v;
            return this;
        }

        /**
         * @param v {@code StatusCallback}: validation-status webhook URL
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
        public CreateOutgoingCallerIdRequest build() {
            return new CreateOutgoingCallerIdRequest(this);
        }
    }
}
