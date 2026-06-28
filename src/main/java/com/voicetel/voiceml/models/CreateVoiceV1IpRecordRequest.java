package com.voicetel.voiceml.models;

import java.util.LinkedHashMap;
import java.util.Map;

/** Form body for {@code POST /v1/IpRecords}. {@code ipAddress} is required. */
public final class CreateVoiceV1IpRecordRequest {

    private final String ipAddress;
    private final String friendlyName;
    private final Integer cidrPrefixLength;

    private CreateVoiceV1IpRecordRequest(Builder b) {
        this.ipAddress = b.ipAddress;
        this.friendlyName = b.friendlyName;
        this.cidrPrefixLength = b.cidrPrefixLength;
    }

    public Map<String, Object> toForm() {
        Map<String, Object> f = new LinkedHashMap<>();
        if (ipAddress != null) f.put("IpAddress", ipAddress);
        if (friendlyName != null) f.put("FriendlyName", friendlyName);
        if (cidrPrefixLength != null) f.put("CidrPrefixLength", cidrPrefixLength);
        return f;
    }

    public static Builder builder() { return new Builder(); }

    public static final class Builder {
        private String ipAddress;
        private String friendlyName;
        private Integer cidrPrefixLength;
        public Builder ipAddress(String s) { this.ipAddress = s; return this; }
        public Builder friendlyName(String s) { this.friendlyName = s; return this; }
        public Builder cidrPrefixLength(Integer v) { this.cidrPrefixLength = v; return this; }
        public CreateVoiceV1IpRecordRequest build() { return new CreateVoiceV1IpRecordRequest(this); }
    }
}
