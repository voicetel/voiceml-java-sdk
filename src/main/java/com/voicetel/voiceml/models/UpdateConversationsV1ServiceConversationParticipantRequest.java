package com.voicetel.voiceml.models;

import java.util.LinkedHashMap;
import java.util.Map;

/** Form body for {@code POST /v1/Services/{ChatServiceSid}/Conversations/{ConversationSid}/Participants/{ParticipantSid}}. */
public final class UpdateConversationsV1ServiceConversationParticipantRequest {

    private final String attributes;
    private final String roleSid;

    private UpdateConversationsV1ServiceConversationParticipantRequest(Builder b) {
        this.attributes = b.attributes;
        this.roleSid = b.roleSid;
    }

    public Map<String, Object> toForm() {
        Map<String, Object> f = new LinkedHashMap<>();
        if (attributes != null) f.put("Attributes", attributes);
        if (roleSid != null) f.put("RoleSid", roleSid);
        return f;
    }

    public static Builder builder() { return new Builder(); }

    public static final class Builder {
        private String attributes;
        private String roleSid;

        public Builder attributes(String s) { this.attributes = s; return this; }
        public Builder roleSid(String s) { this.roleSid = s; return this; }

        public UpdateConversationsV1ServiceConversationParticipantRequest build() {
            return new UpdateConversationsV1ServiceConversationParticipantRequest(this);
        }
    }
}
