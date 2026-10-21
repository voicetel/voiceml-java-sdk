package com.voicetel.voiceml.models;

import java.util.LinkedHashMap;
import java.util.Map;

/** Form body for {@code POST /Applications} and {@code POST /Applications/{Sid}}. */
public final class ApplicationBody {

    private final String friendlyName;
    private final String voiceUrl;
    private final String voiceMethod;
    private final String voiceFallbackUrl;
    private final String voiceFallbackMethod;
    private final Boolean voiceCallerIdLookup;
    private final String statusCallback;
    private final String statusCallbackMethod;
    private final String statusCallbackEvent;
    private final String messageStatusCallback;
    private final String smsStatusCallback;
    private final String smsUrl;
    private final String smsMethod;
    private final String smsFallbackUrl;
    private final String smsFallbackMethod;
    private final Boolean publicApplicationConnectEnabled;

    private ApplicationBody(Builder b) {
        this.friendlyName = b.friendlyName;
        this.voiceUrl = b.voiceUrl;
        this.voiceMethod = b.voiceMethod;
        this.voiceFallbackUrl = b.voiceFallbackUrl;
        this.voiceFallbackMethod = b.voiceFallbackMethod;
        this.voiceCallerIdLookup = b.voiceCallerIdLookup;
        this.statusCallback = b.statusCallback;
        this.statusCallbackMethod = b.statusCallbackMethod;
        this.statusCallbackEvent = b.statusCallbackEvent;
        this.messageStatusCallback = b.messageStatusCallback;
        this.smsStatusCallback = b.smsStatusCallback;
        this.smsUrl = b.smsUrl;
        this.smsMethod = b.smsMethod;
        this.smsFallbackUrl = b.smsFallbackUrl;
        this.smsFallbackMethod = b.smsFallbackMethod;
        this.publicApplicationConnectEnabled = b.publicApplicationConnectEnabled;
    }

    /** @return the form fields, keyed by wire name; unset values are omitted */
    public Map<String, Object> toForm() {
        Map<String, Object> m = new LinkedHashMap<>();
        if (friendlyName != null) m.put("FriendlyName", friendlyName);
        if (voiceUrl != null) m.put("VoiceUrl", voiceUrl);
        if (voiceMethod != null) m.put("VoiceMethod", voiceMethod);
        if (voiceFallbackUrl != null) m.put("VoiceFallbackUrl", voiceFallbackUrl);
        if (voiceFallbackMethod != null) m.put("VoiceFallbackMethod", voiceFallbackMethod);
        if (voiceCallerIdLookup != null) m.put("VoiceCallerIdLookup", voiceCallerIdLookup);
        if (statusCallback != null) m.put("StatusCallback", statusCallback);
        if (statusCallbackMethod != null) m.put("StatusCallbackMethod", statusCallbackMethod);
        if (statusCallbackEvent != null) m.put("StatusCallbackEvent", statusCallbackEvent);
        if (messageStatusCallback != null) m.put("MessageStatusCallback", messageStatusCallback);
        if (smsStatusCallback != null) m.put("SmsStatusCallback", smsStatusCallback);
        if (smsUrl != null) m.put("SmsUrl", smsUrl);
        if (smsMethod != null) m.put("SmsMethod", smsMethod);
        if (smsFallbackUrl != null) m.put("SmsFallbackUrl", smsFallbackUrl);
        if (smsFallbackMethod != null) m.put("SmsFallbackMethod", smsFallbackMethod);
        if (publicApplicationConnectEnabled != null) m.put("PublicApplicationConnectEnabled", publicApplicationConnectEnabled);
        return m;
    }

    /** @return the {@code FriendlyName} value, or {@code null} when unset */
    public String getFriendlyName() {
        return friendlyName;
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

    /** @return the {@code VoiceCallerIdLookup} value, or {@code null} when unset */
    public Boolean getVoiceCallerIdLookup() {
        return voiceCallerIdLookup;
    }

    /** @return the {@code StatusCallback} value, or {@code null} when unset */
    public String getStatusCallback() {
        return statusCallback;
    }

    /** @return the {@code StatusCallbackMethod} value, or {@code null} when unset */
    public String getStatusCallbackMethod() {
        return statusCallbackMethod;
    }

    /** @return the {@code StatusCallbackEvent} value, or {@code null} when unset */
    public String getStatusCallbackEvent() {
        return statusCallbackEvent;
    }

    /** @return the {@code MessageStatusCallback} value, or {@code null} when unset */
    public String getMessageStatusCallback() {
        return messageStatusCallback;
    }

    /** @return the {@code SmsStatusCallback} value, or {@code null} when unset */
    public String getSmsStatusCallback() {
        return smsStatusCallback;
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

    /** @return the {@code PublicApplicationConnectEnabled} value, or {@code null} when unset */
    public Boolean getPublicApplicationConnectEnabled() {
        return publicApplicationConnectEnabled;
    }

    /** @return a new builder */
    public static Builder builder() {
        return new Builder();
    }

    /** Builder for {@link ApplicationBody}. */
    public static final class Builder {
        private String friendlyName;
        private String voiceUrl;
        private String voiceMethod;
        private String voiceFallbackUrl;
        private String voiceFallbackMethod;
        private Boolean voiceCallerIdLookup;
        private String statusCallback;
        private String statusCallbackMethod;
        private String statusCallbackEvent;
        private String messageStatusCallback;
        private String smsStatusCallback;
        private String smsUrl;
        private String smsMethod;
        private String smsFallbackUrl;
        private String smsFallbackMethod;
        private Boolean publicApplicationConnectEnabled;

        /** Create an empty builder; prefer {@link ApplicationBody#builder()}. */
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
         * @param v {@code VoiceCallerIdLookup}
         * @return this builder
         */
        public Builder voiceCallerIdLookup(Boolean v) {
            this.voiceCallerIdLookup = v;
            return this;
        }

        /**
         * @param v {@code StatusCallback}
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
         * @param v {@code StatusCallbackEvent}
         * @return this builder
         */
        public Builder statusCallbackEvent(String v) {
            this.statusCallbackEvent = v;
            return this;
        }

        /**
         * @param v {@code MessageStatusCallback}
         * @return this builder
         */
        public Builder messageStatusCallback(String v) {
            this.messageStatusCallback = v;
            return this;
        }

        /**
         * @param v {@code SmsStatusCallback}
         * @return this builder
         */
        public Builder smsStatusCallback(String v) {
            this.smsStatusCallback = v;
            return this;
        }

        /**
         * @param v {@code SmsUrl}: inbound-SMS webhook URL for the Application
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
         * @param v {@code SmsFallbackUrl}: called when requesting or executing the TwiML at
         *     {@code SmsUrl} fails
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
         * @param v {@code PublicApplicationConnectEnabled}
         * @return this builder
         */
        public Builder publicApplicationConnectEnabled(Boolean v) {
            this.publicApplicationConnectEnabled = v;
            return this;
        }

        /**
         * @return the request
         */
        public ApplicationBody build() {
            return new ApplicationBody(this);
        }
    }
}
