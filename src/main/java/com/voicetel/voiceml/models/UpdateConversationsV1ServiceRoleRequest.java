package com.voicetel.voiceml.models;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/** Form body for {@code POST /v1/Services/{ChatServiceSid}/Roles/{Sid}}. */
public final class UpdateConversationsV1ServiceRoleRequest {

    private final List<String> permission;

    private UpdateConversationsV1ServiceRoleRequest(Builder b) {
        this.permission = b.permission;
    }

    public Map<String, Object> toForm() {
        Map<String, Object> f = new LinkedHashMap<>();
        if (permission != null && !permission.isEmpty()) f.put("Permission", permission);
        return f;
    }

    public static Builder builder() { return new Builder(); }

    public static final class Builder {
        private List<String> permission = new ArrayList<>();

        public Builder permission(List<String> v) {
            this.permission = v != null ? new ArrayList<>(v) : new ArrayList<>();
            return this;
        }
        public Builder addPermission(String s) {
            if (this.permission == null) this.permission = new ArrayList<>();
            this.permission.add(s);
            return this;
        }
        public UpdateConversationsV1ServiceRoleRequest build() {
            return new UpdateConversationsV1ServiceRoleRequest(this);
        }
    }
}
