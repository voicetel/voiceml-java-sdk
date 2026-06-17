package com.voicetel.voiceml.models;

import java.util.LinkedHashMap;
import java.util.Map;

/** Form body for {@code POST /SIP/CredentialLists/{Sid}/Credentials/{Sid}}. Only password is mutable. */
public final class UpdateSipCredentialRequest {

    private final String password;

    private UpdateSipCredentialRequest(Builder b) { this.password = b.password; }

    public Map<String, Object> toForm() {
        Map<String, Object> f = new LinkedHashMap<>();
        if (password != null) f.put("Password", password);
        return f;
    }

    public static Builder builder() { return new Builder(); }

    public static final class Builder {
        private String password;
        public Builder password(String s) { this.password = s; return this; }
        public UpdateSipCredentialRequest build() { return new UpdateSipCredentialRequest(this); }
    }
}
