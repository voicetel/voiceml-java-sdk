package com.voicetel.voiceml.models;

import java.util.LinkedHashMap;
import java.util.Map;

/** Form body for {@code POST /v1/Services/{ChatServiceSid}/Conversations/{ConversationSid}/Webhooks/{WebhookSid}}. */
public final class UpdateConversationsV1ServiceConversationScopedWebhookRequest {

    private final String configurationUrl;
    private final String configurationMethod;
    private final String configurationFlowSid;

    private UpdateConversationsV1ServiceConversationScopedWebhookRequest(Builder b) {
        this.configurationUrl = b.configurationUrl;
        this.configurationMethod = b.configurationMethod;
        this.configurationFlowSid = b.configurationFlowSid;
    }

    public Map<String, Object> toForm() {
        Map<String, Object> f = new LinkedHashMap<>();
        if (configurationUrl != null) f.put("Configuration.Url", configurationUrl);
        if (configurationMethod != null) f.put("Configuration.Method", configurationMethod);
        if (configurationFlowSid != null) f.put("Configuration.FlowSid", configurationFlowSid);
        return f;
    }

    public static Builder builder() { return new Builder(); }

    public static final class Builder {
        private String configurationUrl;
        private String configurationMethod;
        private String configurationFlowSid;

        public Builder configurationUrl(String s) { this.configurationUrl = s; return this; }
        public Builder configurationMethod(String s) { this.configurationMethod = s; return this; }
        public Builder configurationFlowSid(String s) { this.configurationFlowSid = s; return this; }

        public UpdateConversationsV1ServiceConversationScopedWebhookRequest build() {
            return new UpdateConversationsV1ServiceConversationScopedWebhookRequest(this);
        }
    }
}
