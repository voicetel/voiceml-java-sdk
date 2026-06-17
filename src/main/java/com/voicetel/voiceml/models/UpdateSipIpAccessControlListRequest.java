package com.voicetel.voiceml.models;

import java.util.LinkedHashMap;
import java.util.Map;

/** Form body for {@code POST /SIP/IpAccessControlLists/{Sid}}. */
public final class UpdateSipIpAccessControlListRequest {

    private final String friendlyName;

    private UpdateSipIpAccessControlListRequest(Builder b) { this.friendlyName = b.friendlyName; }

    public Map<String, Object> toForm() {
        Map<String, Object> f = new LinkedHashMap<>();
        if (friendlyName != null) f.put("FriendlyName", friendlyName);
        return f;
    }

    public static Builder builder() { return new Builder(); }

    public static final class Builder {
        private String friendlyName;
        public Builder friendlyName(String s) { this.friendlyName = s; return this; }
        public UpdateSipIpAccessControlListRequest build() { return new UpdateSipIpAccessControlListRequest(this); }
    }
}
