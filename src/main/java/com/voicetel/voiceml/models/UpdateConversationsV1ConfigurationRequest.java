package com.voicetel.voiceml.models;

import java.util.LinkedHashMap;
import java.util.Map;

/** Form body for {@code POST /v1/Configuration}. */
public final class UpdateConversationsV1ConfigurationRequest {

    private final String defaultChatServiceSid;
    private final String defaultMessagingServiceSid;
    private final String defaultInactiveTimer;
    private final String defaultClosedTimer;

    private UpdateConversationsV1ConfigurationRequest(Builder b) {
        this.defaultChatServiceSid = b.defaultChatServiceSid;
        this.defaultMessagingServiceSid = b.defaultMessagingServiceSid;
        this.defaultInactiveTimer = b.defaultInactiveTimer;
        this.defaultClosedTimer = b.defaultClosedTimer;
    }

    public Map<String, Object> toForm() {
        Map<String, Object> f = new LinkedHashMap<>();
        if (defaultChatServiceSid != null) f.put("DefaultChatServiceSid", defaultChatServiceSid);
        if (defaultMessagingServiceSid != null) f.put("DefaultMessagingServiceSid", defaultMessagingServiceSid);
        if (defaultInactiveTimer != null) f.put("DefaultInactiveTimer", defaultInactiveTimer);
        if (defaultClosedTimer != null) f.put("DefaultClosedTimer", defaultClosedTimer);
        return f;
    }

    public static Builder builder() { return new Builder(); }

    public static final class Builder {
        private String defaultChatServiceSid;
        private String defaultMessagingServiceSid;
        private String defaultInactiveTimer;
        private String defaultClosedTimer;

        public Builder defaultChatServiceSid(String s) { this.defaultChatServiceSid = s; return this; }
        public Builder defaultMessagingServiceSid(String s) { this.defaultMessagingServiceSid = s; return this; }
        public Builder defaultInactiveTimer(String s) { this.defaultInactiveTimer = s; return this; }
        public Builder defaultClosedTimer(String s) { this.defaultClosedTimer = s; return this; }

        public UpdateConversationsV1ConfigurationRequest build() {
            return new UpdateConversationsV1ConfigurationRequest(this);
        }
    }
}
