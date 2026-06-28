package com.voicetel.voiceml.models;

import java.util.LinkedHashMap;
import java.util.Map;

/** Form body for {@code POST /v1/Users/{Sid}}. */
public final class UpdateConversationsV1UserRequest {

    private final String friendlyName;
    private final String attributes;
    private final String roleSid;

    private UpdateConversationsV1UserRequest(Builder b) {
        this.friendlyName = b.friendlyName;
        this.attributes = b.attributes;
        this.roleSid = b.roleSid;
    }

    public Map<String, Object> toForm() {
        Map<String, Object> f = new LinkedHashMap<>();
        if (friendlyName != null) f.put("FriendlyName", friendlyName);
        if (attributes != null) f.put("Attributes", attributes);
        if (roleSid != null) f.put("RoleSid", roleSid);
        return f;
    }

    public static Builder builder() { return new Builder(); }

    public static final class Builder {
        private String friendlyName;
        private String attributes;
        private String roleSid;
        public Builder friendlyName(String s) { this.friendlyName = s; return this; }
        public Builder attributes(String s) { this.attributes = s; return this; }
        public Builder roleSid(String s) { this.roleSid = s; return this; }
        public UpdateConversationsV1UserRequest build() {
            return new UpdateConversationsV1UserRequest(this);
        }
    }
}
