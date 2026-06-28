package com.voicetel.voiceml.models;

import java.util.LinkedHashMap;
import java.util.Map;

/** Form body for {@code POST /v1/Users/{Sid}/Conversations/{ConversationSid}}. */
public final class UpdateConversationsV1UserConversationRequest {

    private final String notificationLevel;
    private final Integer lastReadMessageIndex;
    private final String lastReadTimestamp;

    private UpdateConversationsV1UserConversationRequest(Builder b) {
        this.notificationLevel = b.notificationLevel;
        this.lastReadMessageIndex = b.lastReadMessageIndex;
        this.lastReadTimestamp = b.lastReadTimestamp;
    }

    public Map<String, Object> toForm() {
        Map<String, Object> f = new LinkedHashMap<>();
        if (notificationLevel != null) f.put("NotificationLevel", notificationLevel);
        if (lastReadMessageIndex != null) f.put("LastReadMessageIndex", lastReadMessageIndex);
        if (lastReadTimestamp != null) f.put("LastReadTimestamp", lastReadTimestamp);
        return f;
    }

    public static Builder builder() { return new Builder(); }

    public static final class Builder {
        private String notificationLevel;
        private Integer lastReadMessageIndex;
        private String lastReadTimestamp;

        public Builder notificationLevel(String s) { this.notificationLevel = s; return this; }
        public Builder lastReadMessageIndex(Integer v) { this.lastReadMessageIndex = v; return this; }
        public Builder lastReadTimestamp(String s) { this.lastReadTimestamp = s; return this; }

        public UpdateConversationsV1UserConversationRequest build() {
            return new UpdateConversationsV1UserConversationRequest(this);
        }
    }
}
