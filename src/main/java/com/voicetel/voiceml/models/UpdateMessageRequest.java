package com.voicetel.voiceml.models;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Form body for {@code POST /Messages/{Sid}}.
 *
 * <p>Two flows on the same endpoint (mirrors Twilio):
 * <ul>
 *   <li>{@code body=""} — redact the stored Message body.</li>
 *   <li>{@code status="canceled"} — attempt to cancel a queued send. Returns 21610 on the
 *       fire-and-forget gateway.</li>
 * </ul>
 */
public final class UpdateMessageRequest {

    private final String body;
    private final String status;

    private UpdateMessageRequest(Builder b) {
        this.body = b.body;
        this.status = b.status;
    }

    public Map<String, Object> toForm() {
        Map<String, Object> form = new LinkedHashMap<>();
        if (body != null) form.put("Body", body);
        if (status != null) form.put("Status", status);
        return form;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder {
        private String body;
        private String status;

        public Builder body(String s) { this.body = s; return this; }
        public Builder status(String s) { this.status = s; return this; }

        public UpdateMessageRequest build() {
            return new UpdateMessageRequest(this);
        }
    }
}
