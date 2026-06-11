package com.voicetel.voiceml.models;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Form body for {@code POST /Messages}.
 *
 * <p>{@code to} and {@code body} are required by the server. {@code from} falls back to the
 * tenant's configured default sender when omitted.
 */
public final class CreateMessageRequest {

    private final String to;
    private final String from;
    private final String body;
    private final String messagingServiceSid;
    private final String statusCallback;

    private CreateMessageRequest(Builder b) {
        this.to = b.to;
        this.from = b.from;
        this.body = b.body;
        this.messagingServiceSid = b.messagingServiceSid;
        this.statusCallback = b.statusCallback;
    }

    public Map<String, Object> toForm() {
        Map<String, Object> form = new LinkedHashMap<>();
        if (to != null) form.put("To", to);
        if (from != null) form.put("From", from);
        if (body != null) form.put("Body", body);
        if (messagingServiceSid != null) form.put("MessagingServiceSid", messagingServiceSid);
        if (statusCallback != null) form.put("StatusCallback", statusCallback);
        return form;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder {
        private String to;
        private String from;
        private String body;
        private String messagingServiceSid;
        private String statusCallback;

        public Builder to(String s) { this.to = s; return this; }
        public Builder from(String s) { this.from = s; return this; }
        public Builder body(String s) { this.body = s; return this; }
        public Builder messagingServiceSid(String s) { this.messagingServiceSid = s; return this; }
        public Builder statusCallback(String s) { this.statusCallback = s; return this; }

        public CreateMessageRequest build() {
            return new CreateMessageRequest(this);
        }
    }
}
