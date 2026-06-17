package com.voicetel.voiceml.models;

import java.util.LinkedHashMap;
import java.util.Map;

/** Form body for {@code POST /SIP/CredentialLists}. */
public final class CreateSipCredentialListRequest {

    private final String friendlyName;

    private CreateSipCredentialListRequest(Builder b) { this.friendlyName = b.friendlyName; }

    public Map<String, Object> toForm() {
        Map<String, Object> f = new LinkedHashMap<>();
        if (friendlyName != null) f.put("FriendlyName", friendlyName);
        return f;
    }

    public static Builder builder() { return new Builder(); }

    public static final class Builder {
        private String friendlyName;
        public Builder friendlyName(String s) { this.friendlyName = s; return this; }
        public CreateSipCredentialListRequest build() { return new CreateSipCredentialListRequest(this); }
    }
}
