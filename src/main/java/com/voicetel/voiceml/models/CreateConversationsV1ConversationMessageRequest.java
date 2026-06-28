package com.voicetel.voiceml.models;

import java.util.LinkedHashMap;
import java.util.Map;

/** Form body for {@code POST /v1/Conversations/{ConversationSid}/Messages}. */
public final class CreateConversationsV1ConversationMessageRequest {

    private final String author;
    private final String body;
    private final String attributes;
    private final String contentSid;

    private CreateConversationsV1ConversationMessageRequest(Builder b) {
        this.author = b.author;
        this.body = b.body;
        this.attributes = b.attributes;
        this.contentSid = b.contentSid;
    }

    public Map<String, Object> toForm() {
        Map<String, Object> f = new LinkedHashMap<>();
        if (author != null) f.put("Author", author);
        if (body != null) f.put("Body", body);
        if (attributes != null) f.put("Attributes", attributes);
        if (contentSid != null) f.put("ContentSid", contentSid);
        return f;
    }

    public static Builder builder() { return new Builder(); }

    public static final class Builder {
        private String author;
        private String body;
        private String attributes;
        private String contentSid;
        public Builder author(String s) { this.author = s; return this; }
        public Builder body(String s) { this.body = s; return this; }
        public Builder attributes(String s) { this.attributes = s; return this; }
        public Builder contentSid(String s) { this.contentSid = s; return this; }
        public CreateConversationsV1ConversationMessageRequest build() {
            return new CreateConversationsV1ConversationMessageRequest(this);
        }
    }
}
