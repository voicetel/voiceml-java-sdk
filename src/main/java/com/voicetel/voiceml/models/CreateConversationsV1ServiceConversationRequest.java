package com.voicetel.voiceml.models;

import java.util.LinkedHashMap;
import java.util.Map;

/** Form body for {@code POST /v1/Services/{ChatServiceSid}/Conversations}. */
public final class CreateConversationsV1ServiceConversationRequest {

    private final String friendlyName;
    private final String uniqueName;
    private final String messagingServiceSid;
    private final String attributes;
    private final String state;
    private final String timersInactive;
    private final String timersClosed;

    private CreateConversationsV1ServiceConversationRequest(Builder b) {
        this.friendlyName = b.friendlyName;
        this.uniqueName = b.uniqueName;
        this.messagingServiceSid = b.messagingServiceSid;
        this.attributes = b.attributes;
        this.state = b.state;
        this.timersInactive = b.timersInactive;
        this.timersClosed = b.timersClosed;
    }

    public Map<String, Object> toForm() {
        Map<String, Object> f = new LinkedHashMap<>();
        if (friendlyName != null) f.put("FriendlyName", friendlyName);
        if (uniqueName != null) f.put("UniqueName", uniqueName);
        if (messagingServiceSid != null) f.put("MessagingServiceSid", messagingServiceSid);
        if (attributes != null) f.put("Attributes", attributes);
        if (state != null) f.put("State", state);
        if (timersInactive != null) f.put("Timers.Inactive", timersInactive);
        if (timersClosed != null) f.put("Timers.Closed", timersClosed);
        return f;
    }

    public static Builder builder() { return new Builder(); }

    public static final class Builder {
        private String friendlyName;
        private String uniqueName;
        private String messagingServiceSid;
        private String attributes;
        private String state;
        private String timersInactive;
        private String timersClosed;

        public Builder friendlyName(String s) { this.friendlyName = s; return this; }
        public Builder uniqueName(String s) { this.uniqueName = s; return this; }
        public Builder messagingServiceSid(String s) { this.messagingServiceSid = s; return this; }
        public Builder attributes(String s) { this.attributes = s; return this; }
        public Builder state(String s) { this.state = s; return this; }
        public Builder timersInactive(String s) { this.timersInactive = s; return this; }
        public Builder timersClosed(String s) { this.timersClosed = s; return this; }

        public CreateConversationsV1ServiceConversationRequest build() {
            return new CreateConversationsV1ServiceConversationRequest(this);
        }
    }
}
