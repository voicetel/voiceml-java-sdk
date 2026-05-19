package com.voicetel.voiceml.models;

import java.util.LinkedHashMap;
import java.util.Map;

/** Form body for {@code POST /Queues/{sid}/Members/Front} and {@code .../Members/{CallSid}}. */
public final class DequeueRequest {

    private final String url;
    private final String method;

    private DequeueRequest(Builder b) {
        this.url = b.url;
        this.method = b.method;
    }

    public Map<String, Object> toForm() {
        Map<String, Object> form = new LinkedHashMap<>();
        if (url != null) form.put("Url", url);
        if (method != null) form.put("Method", method);
        return form;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder {
        private String url;
        private String method;

        public Builder url(String s) { this.url = s; return this; }
        public Builder method(String s) { this.method = s; return this; }

        public DequeueRequest build() {
            return new DequeueRequest(this);
        }
    }
}
