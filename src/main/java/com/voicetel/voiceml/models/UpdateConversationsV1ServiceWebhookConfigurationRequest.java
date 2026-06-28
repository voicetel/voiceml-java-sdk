package com.voicetel.voiceml.models;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/** Form body for {@code POST /v1/Services/{ChatServiceSid}/Configuration/Webhooks}. */
public final class UpdateConversationsV1ServiceWebhookConfigurationRequest {

    private final String preWebhookUrl;
    private final String postWebhookUrl;
    private final String method;
    private final List<String> filters;

    private UpdateConversationsV1ServiceWebhookConfigurationRequest(Builder b) {
        this.preWebhookUrl = b.preWebhookUrl;
        this.postWebhookUrl = b.postWebhookUrl;
        this.method = b.method;
        this.filters = b.filters;
    }

    public Map<String, Object> toForm() {
        Map<String, Object> f = new LinkedHashMap<>();
        if (preWebhookUrl != null) f.put("PreWebhookUrl", preWebhookUrl);
        if (postWebhookUrl != null) f.put("PostWebhookUrl", postWebhookUrl);
        if (method != null) f.put("Method", method);
        if (filters != null && !filters.isEmpty()) f.put("Filters", filters);
        return f;
    }

    public static Builder builder() { return new Builder(); }

    public static final class Builder {
        private String preWebhookUrl;
        private String postWebhookUrl;
        private String method;
        private List<String> filters = new ArrayList<>();

        public Builder preWebhookUrl(String s) { this.preWebhookUrl = s; return this; }
        public Builder postWebhookUrl(String s) { this.postWebhookUrl = s; return this; }
        public Builder method(String s) { this.method = s; return this; }
        public Builder filters(List<String> v) {
            this.filters = v != null ? new ArrayList<>(v) : new ArrayList<>();
            return this;
        }
        public Builder addFilter(String s) {
            if (this.filters == null) this.filters = new ArrayList<>();
            this.filters.add(s);
            return this;
        }

        public UpdateConversationsV1ServiceWebhookConfigurationRequest build() {
            return new UpdateConversationsV1ServiceWebhookConfigurationRequest(this);
        }
    }
}
