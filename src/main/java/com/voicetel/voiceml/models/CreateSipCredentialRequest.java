package com.voicetel.voiceml.models;

import java.util.LinkedHashMap;
import java.util.Map;

/** Form body for {@code POST /SIP/CredentialLists/{Sid}/Credentials}. */
public final class CreateSipCredentialRequest {

    private final String username;
    private final String password;

    private CreateSipCredentialRequest(Builder b) {
        this.username = b.username;
        this.password = b.password;
    }

    public Map<String, Object> toForm() {
        Map<String, Object> f = new LinkedHashMap<>();
        if (username != null) f.put("Username", username);
        if (password != null) f.put("Password", password);
        return f;
    }

    public static Builder builder() { return new Builder(); }

    public static final class Builder {
        private String username;
        private String password;
        public Builder username(String s) { this.username = s; return this; }
        public Builder password(String s) { this.password = s; return this; }
        public CreateSipCredentialRequest build() { return new CreateSipCredentialRequest(this); }
    }
}
