package com.voicetel.voiceml.models;

import java.util.LinkedHashMap;
import java.util.Map;

/** Form body for {@code POST /v1/Credentials/{Sid}}. */
public final class UpdateConversationsV1CredentialRequest {

    private final String type;
    private final String friendlyName;
    private final String certificate;
    private final String privateKey;
    private final Boolean sandbox;
    private final String apiKey;
    private final String secret;

    private UpdateConversationsV1CredentialRequest(Builder b) {
        this.type = b.type;
        this.friendlyName = b.friendlyName;
        this.certificate = b.certificate;
        this.privateKey = b.privateKey;
        this.sandbox = b.sandbox;
        this.apiKey = b.apiKey;
        this.secret = b.secret;
    }

    public Map<String, Object> toForm() {
        Map<String, Object> f = new LinkedHashMap<>();
        if (type != null) f.put("Type", type);
        if (friendlyName != null) f.put("FriendlyName", friendlyName);
        if (certificate != null) f.put("Certificate", certificate);
        if (privateKey != null) f.put("PrivateKey", privateKey);
        if (sandbox != null) f.put("Sandbox", sandbox);
        if (apiKey != null) f.put("ApiKey", apiKey);
        if (secret != null) f.put("Secret", secret);
        return f;
    }

    public static Builder builder() { return new Builder(); }

    public static final class Builder {
        private String type;
        private String friendlyName;
        private String certificate;
        private String privateKey;
        private Boolean sandbox;
        private String apiKey;
        private String secret;

        public Builder type(String s) { this.type = s; return this; }
        public Builder friendlyName(String s) { this.friendlyName = s; return this; }
        public Builder certificate(String s) { this.certificate = s; return this; }
        public Builder privateKey(String s) { this.privateKey = s; return this; }
        public Builder sandbox(Boolean v) { this.sandbox = v; return this; }
        public Builder apiKey(String s) { this.apiKey = s; return this; }
        public Builder secret(String s) { this.secret = s; return this; }

        public UpdateConversationsV1CredentialRequest build() {
            return new UpdateConversationsV1CredentialRequest(this);
        }
    }
}
