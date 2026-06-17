package com.voicetel.voiceml.models;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Form body for any {@code .../IpAccessControlListMappings} POST — historical
 * and Auth/Calls namespaces (no registrations counterpart).
 */
public final class CreateSipIpAccessControlListMappingRequest {

    private final String ipAccessControlListSid;

    private CreateSipIpAccessControlListMappingRequest(Builder b) { this.ipAccessControlListSid = b.ipAccessControlListSid; }

    public Map<String, Object> toForm() {
        Map<String, Object> f = new LinkedHashMap<>();
        if (ipAccessControlListSid != null) f.put("IpAccessControlListSid", ipAccessControlListSid);
        return f;
    }

    public static Builder builder() { return new Builder(); }

    public static final class Builder {
        private String ipAccessControlListSid;
        public Builder ipAccessControlListSid(String s) { this.ipAccessControlListSid = s; return this; }
        public CreateSipIpAccessControlListMappingRequest build() { return new CreateSipIpAccessControlListMappingRequest(this); }
    }
}
