package com.voicetel.voiceml.models;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Form body for {@code POST /v1/Roles}. {@code FriendlyName}, {@code Type},
 * and {@code Permission} (repeated) are required.
 */
public final class CreateConversationsV1RoleRequest {

    private final String friendlyName;
    private final String type;
    private final List<String> permission;

    private CreateConversationsV1RoleRequest(Builder b) {
        this.friendlyName = b.friendlyName;
        this.type = b.type;
        this.permission = b.permission;
    }

    public Map<String, Object> toForm() {
        Map<String, Object> f = new LinkedHashMap<>();
        if (friendlyName != null) f.put("FriendlyName", friendlyName);
        if (type != null) f.put("Type", type);
        if (permission != null && !permission.isEmpty()) f.put("Permission", permission);
        return f;
    }

    public static Builder builder() { return new Builder(); }

    public static final class Builder {
        private String friendlyName;
        private String type;
        private List<String> permission = new ArrayList<>();

        public Builder friendlyName(String s) { this.friendlyName = s; return this; }
        public Builder type(String s) { this.type = s; return this; }
        public Builder permission(List<String> v) {
            this.permission = v != null ? new ArrayList<>(v) : new ArrayList<>();
            return this;
        }
        public Builder addPermission(String s) {
            if (this.permission == null) this.permission = new ArrayList<>();
            this.permission.add(s);
            return this;
        }
        public CreateConversationsV1RoleRequest build() { return new CreateConversationsV1RoleRequest(this); }
    }
}
