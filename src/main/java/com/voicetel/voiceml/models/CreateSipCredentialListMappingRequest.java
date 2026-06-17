package com.voicetel.voiceml.models;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Form body for any {@code .../CredentialListMappings} POST — historical,
 * Auth/Calls, and Auth/Registrations namespaces all round-trip the same shape.
 */
public final class CreateSipCredentialListMappingRequest {

    private final String credentialListSid;

    private CreateSipCredentialListMappingRequest(Builder b) { this.credentialListSid = b.credentialListSid; }

    public Map<String, Object> toForm() {
        Map<String, Object> f = new LinkedHashMap<>();
        if (credentialListSid != null) f.put("CredentialListSid", credentialListSid);
        return f;
    }

    public static Builder builder() { return new Builder(); }

    public static final class Builder {
        private String credentialListSid;
        public Builder credentialListSid(String s) { this.credentialListSid = s; return this; }
        public CreateSipCredentialListMappingRequest build() { return new CreateSipCredentialListMappingRequest(this); }
    }
}
