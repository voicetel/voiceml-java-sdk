package com.voicetel.voiceml.models;

import java.util.LinkedHashMap;
import java.util.Map;

/** Form body for {@code POST /v1/Services/{ChatServiceSid}/Configuration/Notifications}. */
public final class UpdateConversationsV1ServiceNotificationRequest {

    private final Boolean logEnabled;
    private final Boolean newMessageEnabled;
    private final String newMessageTemplate;
    private final String newMessageSound;
    private final Boolean newMessageBadgeCountEnabled;
    private final Boolean newMessageWithMediaEnabled;
    private final String newMessageWithMediaTemplate;
    private final Boolean addedToConversationEnabled;
    private final String addedToConversationTemplate;
    private final String addedToConversationSound;
    private final Boolean removedFromConversationEnabled;
    private final String removedFromConversationTemplate;
    private final String removedFromConversationSound;

    private UpdateConversationsV1ServiceNotificationRequest(Builder b) {
        this.logEnabled = b.logEnabled;
        this.newMessageEnabled = b.newMessageEnabled;
        this.newMessageTemplate = b.newMessageTemplate;
        this.newMessageSound = b.newMessageSound;
        this.newMessageBadgeCountEnabled = b.newMessageBadgeCountEnabled;
        this.newMessageWithMediaEnabled = b.newMessageWithMediaEnabled;
        this.newMessageWithMediaTemplate = b.newMessageWithMediaTemplate;
        this.addedToConversationEnabled = b.addedToConversationEnabled;
        this.addedToConversationTemplate = b.addedToConversationTemplate;
        this.addedToConversationSound = b.addedToConversationSound;
        this.removedFromConversationEnabled = b.removedFromConversationEnabled;
        this.removedFromConversationTemplate = b.removedFromConversationTemplate;
        this.removedFromConversationSound = b.removedFromConversationSound;
    }

    public Map<String, Object> toForm() {
        Map<String, Object> f = new LinkedHashMap<>();
        if (logEnabled != null) f.put("LogEnabled", logEnabled);
        if (newMessageEnabled != null) f.put("NewMessage.Enabled", newMessageEnabled);
        if (newMessageTemplate != null) f.put("NewMessage.Template", newMessageTemplate);
        if (newMessageSound != null) f.put("NewMessage.Sound", newMessageSound);
        if (newMessageBadgeCountEnabled != null) f.put("NewMessage.BadgeCountEnabled", newMessageBadgeCountEnabled);
        if (newMessageWithMediaEnabled != null) f.put("NewMessage.WithMedia.Enabled", newMessageWithMediaEnabled);
        if (newMessageWithMediaTemplate != null) f.put("NewMessage.WithMedia.Template", newMessageWithMediaTemplate);
        if (addedToConversationEnabled != null) f.put("AddedToConversation.Enabled", addedToConversationEnabled);
        if (addedToConversationTemplate != null) f.put("AddedToConversation.Template", addedToConversationTemplate);
        if (addedToConversationSound != null) f.put("AddedToConversation.Sound", addedToConversationSound);
        if (removedFromConversationEnabled != null) f.put("RemovedFromConversation.Enabled", removedFromConversationEnabled);
        if (removedFromConversationTemplate != null) f.put("RemovedFromConversation.Template", removedFromConversationTemplate);
        if (removedFromConversationSound != null) f.put("RemovedFromConversation.Sound", removedFromConversationSound);
        return f;
    }

    public static Builder builder() { return new Builder(); }

    public static final class Builder {
        private Boolean logEnabled;
        private Boolean newMessageEnabled;
        private String newMessageTemplate;
        private String newMessageSound;
        private Boolean newMessageBadgeCountEnabled;
        private Boolean newMessageWithMediaEnabled;
        private String newMessageWithMediaTemplate;
        private Boolean addedToConversationEnabled;
        private String addedToConversationTemplate;
        private String addedToConversationSound;
        private Boolean removedFromConversationEnabled;
        private String removedFromConversationTemplate;
        private String removedFromConversationSound;

        public Builder logEnabled(Boolean v) { this.logEnabled = v; return this; }
        public Builder newMessageEnabled(Boolean v) { this.newMessageEnabled = v; return this; }
        public Builder newMessageTemplate(String s) { this.newMessageTemplate = s; return this; }
        public Builder newMessageSound(String s) { this.newMessageSound = s; return this; }
        public Builder newMessageBadgeCountEnabled(Boolean v) { this.newMessageBadgeCountEnabled = v; return this; }
        public Builder newMessageWithMediaEnabled(Boolean v) { this.newMessageWithMediaEnabled = v; return this; }
        public Builder newMessageWithMediaTemplate(String s) { this.newMessageWithMediaTemplate = s; return this; }
        public Builder addedToConversationEnabled(Boolean v) { this.addedToConversationEnabled = v; return this; }
        public Builder addedToConversationTemplate(String s) { this.addedToConversationTemplate = s; return this; }
        public Builder addedToConversationSound(String s) { this.addedToConversationSound = s; return this; }
        public Builder removedFromConversationEnabled(Boolean v) { this.removedFromConversationEnabled = v; return this; }
        public Builder removedFromConversationTemplate(String s) { this.removedFromConversationTemplate = s; return this; }
        public Builder removedFromConversationSound(String s) { this.removedFromConversationSound = s; return this; }

        public UpdateConversationsV1ServiceNotificationRequest build() {
            return new UpdateConversationsV1ServiceNotificationRequest(this);
        }
    }
}
