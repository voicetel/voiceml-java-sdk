package com.voicetel.voiceml.models;

import java.util.LinkedHashMap;
import java.util.Map;

/** JSON body for {@code POST /v1/Assistants/{id}/Messages}. {@code identity} and {@code body} required. */
public final class SendAssistantsV1MessageRequest {

    private final String identity;
    private final String body;
    private final String sessionId;
    private final String webhook;
    private final String mode;

    private SendAssistantsV1MessageRequest(Builder b) {
        this.identity = b.identity;
        this.body = b.body;
        this.sessionId = b.sessionId;
        this.webhook = b.webhook;
        this.mode = b.mode;
    }

    public Map<String, Object> toJsonBody() {
        Map<String, Object> m = new LinkedHashMap<>();
        if (identity != null) m.put("identity", identity);
        if (body != null) m.put("body", body);
        if (sessionId != null) m.put("session_id", sessionId);
        if (webhook != null) m.put("webhook", webhook);
        if (mode != null) m.put("mode", mode);
        return m;
    }

    public static Builder builder() { return new Builder(); }

    public static final class Builder {
        private String identity;
        private String body;
        private String sessionId;
        private String webhook;
        private String mode;

        public Builder identity(String s) { this.identity = s; return this; }
        public Builder body(String s) { this.body = s; return this; }
        public Builder sessionId(String s) { this.sessionId = s; return this; }
        public Builder webhook(String s) { this.webhook = s; return this; }
        public Builder mode(String s) { this.mode = s; return this; }

        public SendAssistantsV1MessageRequest build() {
            return new SendAssistantsV1MessageRequest(this);
        }
    }
}
