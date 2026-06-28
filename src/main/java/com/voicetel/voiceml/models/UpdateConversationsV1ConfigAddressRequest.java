package com.voicetel.voiceml.models;

import java.util.LinkedHashMap;
import java.util.Map;

/** Form body for {@code POST /v1/Configuration/Addresses/{Sid}}. */
public final class UpdateConversationsV1ConfigAddressRequest {

    private final String friendlyName;
    private final Boolean autoCreationEnabled;
    private final String autoCreationType;
    private final String autoCreationWebhookUrl;

    private UpdateConversationsV1ConfigAddressRequest(Builder b) {
        this.friendlyName = b.friendlyName;
        this.autoCreationEnabled = b.autoCreationEnabled;
        this.autoCreationType = b.autoCreationType;
        this.autoCreationWebhookUrl = b.autoCreationWebhookUrl;
    }

    public Map<String, Object> toForm() {
        Map<String, Object> f = new LinkedHashMap<>();
        if (friendlyName != null) f.put("FriendlyName", friendlyName);
        if (autoCreationEnabled != null) f.put("AutoCreation.Enabled", autoCreationEnabled);
        if (autoCreationType != null) f.put("AutoCreation.Type", autoCreationType);
        if (autoCreationWebhookUrl != null) f.put("AutoCreation.WebhookUrl", autoCreationWebhookUrl);
        return f;
    }

    public static Builder builder() { return new Builder(); }

    public static final class Builder {
        private String friendlyName;
        private Boolean autoCreationEnabled;
        private String autoCreationType;
        private String autoCreationWebhookUrl;

        public Builder friendlyName(String s) { this.friendlyName = s; return this; }
        public Builder autoCreationEnabled(Boolean v) { this.autoCreationEnabled = v; return this; }
        public Builder autoCreationType(String s) { this.autoCreationType = s; return this; }
        public Builder autoCreationWebhookUrl(String s) { this.autoCreationWebhookUrl = s; return this; }

        public UpdateConversationsV1ConfigAddressRequest build() {
            return new UpdateConversationsV1ConfigAddressRequest(this);
        }
    }
}
