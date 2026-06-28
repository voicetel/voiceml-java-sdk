package com.voicetel.voiceml.models;

import java.util.LinkedHashMap;
import java.util.Map;

/** Form body for {@code POST /v1/Conversations/{ConversationSid}/Participants/{ParticipantSid}}. */
public final class UpdateConversationsV1ConversationParticipantRequest {

    private final String identity;
    private final String attributes;
    private final String roleSid;
    private final Integer lastReadMessageIndex;
    private final String lastReadTimestamp;

    private UpdateConversationsV1ConversationParticipantRequest(Builder b) {
        this.identity = b.identity;
        this.attributes = b.attributes;
        this.roleSid = b.roleSid;
        this.lastReadMessageIndex = b.lastReadMessageIndex;
        this.lastReadTimestamp = b.lastReadTimestamp;
    }

    public Map<String, Object> toForm() {
        Map<String, Object> f = new LinkedHashMap<>();
        if (identity != null) f.put("Identity", identity);
        if (attributes != null) f.put("Attributes", attributes);
        if (roleSid != null) f.put("RoleSid", roleSid);
        if (lastReadMessageIndex != null) f.put("LastReadMessageIndex", lastReadMessageIndex);
        if (lastReadTimestamp != null) f.put("LastReadTimestamp", lastReadTimestamp);
        return f;
    }

    public static Builder builder() { return new Builder(); }

    public static final class Builder {
        private String identity;
        private String attributes;
        private String roleSid;
        private Integer lastReadMessageIndex;
        private String lastReadTimestamp;

        public Builder identity(String s) { this.identity = s; return this; }
        public Builder attributes(String s) { this.attributes = s; return this; }
        public Builder roleSid(String s) { this.roleSid = s; return this; }
        public Builder lastReadMessageIndex(Integer v) { this.lastReadMessageIndex = v; return this; }
        public Builder lastReadTimestamp(String s) { this.lastReadTimestamp = s; return this; }

        public UpdateConversationsV1ConversationParticipantRequest build() {
            return new UpdateConversationsV1ConversationParticipantRequest(this);
        }
    }
}
