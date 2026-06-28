package com.voicetel.voiceml.models;

import java.util.LinkedHashMap;
import java.util.Map;

/** Form body for {@code POST /v1/Configuration/Addresses}. {@code Type} and {@code Address} required. */
public final class CreateConversationsV1ConfigAddressRequest {

    private final String type;
    private final String address;
    private final String friendlyName;
    private final Boolean autoCreationEnabled;
    private final String autoCreationType;
    private final String autoCreationWebhookUrl;
    private final String addressCountry;

    private CreateConversationsV1ConfigAddressRequest(Builder b) {
        this.type = b.type;
        this.address = b.address;
        this.friendlyName = b.friendlyName;
        this.autoCreationEnabled = b.autoCreationEnabled;
        this.autoCreationType = b.autoCreationType;
        this.autoCreationWebhookUrl = b.autoCreationWebhookUrl;
        this.addressCountry = b.addressCountry;
    }

    public Map<String, Object> toForm() {
        Map<String, Object> f = new LinkedHashMap<>();
        if (type != null) f.put("Type", type);
        if (address != null) f.put("Address", address);
        if (friendlyName != null) f.put("FriendlyName", friendlyName);
        if (autoCreationEnabled != null) f.put("AutoCreation.Enabled", autoCreationEnabled);
        if (autoCreationType != null) f.put("AutoCreation.Type", autoCreationType);
        if (autoCreationWebhookUrl != null) f.put("AutoCreation.WebhookUrl", autoCreationWebhookUrl);
        if (addressCountry != null) f.put("AddressCountry", addressCountry);
        return f;
    }

    public static Builder builder() { return new Builder(); }

    public static final class Builder {
        private String type;
        private String address;
        private String friendlyName;
        private Boolean autoCreationEnabled;
        private String autoCreationType;
        private String autoCreationWebhookUrl;
        private String addressCountry;

        public Builder type(String s) { this.type = s; return this; }
        public Builder address(String s) { this.address = s; return this; }
        public Builder friendlyName(String s) { this.friendlyName = s; return this; }
        public Builder autoCreationEnabled(Boolean v) { this.autoCreationEnabled = v; return this; }
        public Builder autoCreationType(String s) { this.autoCreationType = s; return this; }
        public Builder autoCreationWebhookUrl(String s) { this.autoCreationWebhookUrl = s; return this; }
        public Builder addressCountry(String s) { this.addressCountry = s; return this; }

        public CreateConversationsV1ConfigAddressRequest build() {
            return new CreateConversationsV1ConfigAddressRequest(this);
        }
    }
}
