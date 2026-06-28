package com.voicetel.voiceml.models;

import java.util.LinkedHashMap;
import java.util.Map;

/** JSON body for {@code POST /v1/Assistants/{id}/Feedbacks}. {@code session_id} is required. */
public final class CreateAssistantsV1FeedbackRequest {

    private final String sessionId;
    private final String messageId;
    private final Float score;
    private final String text;

    private CreateAssistantsV1FeedbackRequest(Builder b) {
        this.sessionId = b.sessionId;
        this.messageId = b.messageId;
        this.score = b.score;
        this.text = b.text;
    }

    public Map<String, Object> toJsonBody() {
        Map<String, Object> m = new LinkedHashMap<>();
        if (sessionId != null) m.put("session_id", sessionId);
        if (messageId != null) m.put("message_id", messageId);
        if (score != null) m.put("score", score);
        if (text != null) m.put("text", text);
        return m;
    }

    public static Builder builder() { return new Builder(); }

    public static final class Builder {
        private String sessionId;
        private String messageId;
        private Float score;
        private String text;

        public Builder sessionId(String s) { this.sessionId = s; return this; }
        public Builder messageId(String s) { this.messageId = s; return this; }
        public Builder score(Float v) { this.score = v; return this; }
        public Builder text(String s) { this.text = s; return this; }

        public CreateAssistantsV1FeedbackRequest build() {
            return new CreateAssistantsV1FeedbackRequest(this);
        }
    }
}
