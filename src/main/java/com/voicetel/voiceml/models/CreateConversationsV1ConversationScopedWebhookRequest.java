package com.voicetel.voiceml.models;

import java.util.LinkedHashMap;
import java.util.Map;

/** Form body for {@code POST /v1/Conversations/{ConversationSid}/Webhooks}. {@code target} required. */
public final class CreateConversationsV1ConversationScopedWebhookRequest {

    private final String target;
    private final String configurationUrl;
    private final String configurationMethod;
    private final String configurationFlowSid;
    private final Integer configurationReplayAfter;

    private CreateConversationsV1ConversationScopedWebhookRequest(Builder b) {
        this.target = b.target;
        this.configurationUrl = b.configurationUrl;
        this.configurationMethod = b.configurationMethod;
        this.configurationFlowSid = b.configurationFlowSid;
        this.configurationReplayAfter = b.configurationReplayAfter;
    }

    public Map<String, Object> toForm() {
        Map<String, Object> f = new LinkedHashMap<>();
        if (target != null) f.put("Target", target);
        if (configurationUrl != null) f.put("Configuration.Url", configurationUrl);
        if (configurationMethod != null) f.put("Configuration.Method", configurationMethod);
        if (configurationFlowSid != null) f.put("Configuration.FlowSid", configurationFlowSid);
        if (configurationReplayAfter != null) f.put("Configuration.ReplayAfter", configurationReplayAfter);
        return f;
    }

    public static Builder builder() { return new Builder(); }

    public static final class Builder {
        private String target;
        private String configurationUrl;
        private String configurationMethod;
        private String configurationFlowSid;
        private Integer configurationReplayAfter;

        public Builder target(String s) { this.target = s; return this; }
        public Builder configurationUrl(String s) { this.configurationUrl = s; return this; }
        public Builder configurationMethod(String s) { this.configurationMethod = s; return this; }
        public Builder configurationFlowSid(String s) { this.configurationFlowSid = s; return this; }
        public Builder configurationReplayAfter(Integer v) { this.configurationReplayAfter = v; return this; }

        public CreateConversationsV1ConversationScopedWebhookRequest build() {
            return new CreateConversationsV1ConversationScopedWebhookRequest(this);
        }
    }
}
