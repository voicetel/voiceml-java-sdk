package com.voicetel.voiceml.models;

import java.util.LinkedHashMap;
import java.util.Map;

/** Form body for {@code POST /v1/Services/{ChatServiceSid}/Configuration}. */
public final class UpdateConversationsV1ServiceConfigurationRequest {

    private final String defaultChatServiceRoleSid;
    private final String defaultConversationCreatorRoleSid;
    private final String defaultConversationRoleSid;
    private final Boolean reachabilityEnabled;

    private UpdateConversationsV1ServiceConfigurationRequest(Builder b) {
        this.defaultChatServiceRoleSid = b.defaultChatServiceRoleSid;
        this.defaultConversationCreatorRoleSid = b.defaultConversationCreatorRoleSid;
        this.defaultConversationRoleSid = b.defaultConversationRoleSid;
        this.reachabilityEnabled = b.reachabilityEnabled;
    }

    public Map<String, Object> toForm() {
        Map<String, Object> f = new LinkedHashMap<>();
        if (defaultChatServiceRoleSid != null) f.put("DefaultChatServiceRoleSid", defaultChatServiceRoleSid);
        if (defaultConversationCreatorRoleSid != null) f.put("DefaultConversationCreatorRoleSid", defaultConversationCreatorRoleSid);
        if (defaultConversationRoleSid != null) f.put("DefaultConversationRoleSid", defaultConversationRoleSid);
        if (reachabilityEnabled != null) f.put("ReachabilityEnabled", reachabilityEnabled);
        return f;
    }

    public static Builder builder() { return new Builder(); }

    public static final class Builder {
        private String defaultChatServiceRoleSid;
        private String defaultConversationCreatorRoleSid;
        private String defaultConversationRoleSid;
        private Boolean reachabilityEnabled;

        public Builder defaultChatServiceRoleSid(String s) { this.defaultChatServiceRoleSid = s; return this; }
        public Builder defaultConversationCreatorRoleSid(String s) { this.defaultConversationCreatorRoleSid = s; return this; }
        public Builder defaultConversationRoleSid(String s) { this.defaultConversationRoleSid = s; return this; }
        public Builder reachabilityEnabled(Boolean v) { this.reachabilityEnabled = v; return this; }

        public UpdateConversationsV1ServiceConfigurationRequest build() {
            return new UpdateConversationsV1ServiceConfigurationRequest(this);
        }
    }
}
