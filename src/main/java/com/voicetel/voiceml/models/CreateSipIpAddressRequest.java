package com.voicetel.voiceml.models;

import java.util.LinkedHashMap;
import java.util.Map;

/** Form body for {@code POST /SIP/IpAccessControlLists/{Sid}/IpAddresses}. */
public final class CreateSipIpAddressRequest {

    private final String friendlyName;
    private final String ipAddress;
    private final Integer cidrPrefixLength;

    private CreateSipIpAddressRequest(Builder b) {
        this.friendlyName = b.friendlyName;
        this.ipAddress = b.ipAddress;
        this.cidrPrefixLength = b.cidrPrefixLength;
    }

    public Map<String, Object> toForm() {
        Map<String, Object> f = new LinkedHashMap<>();
        if (friendlyName != null) f.put("FriendlyName", friendlyName);
        if (ipAddress != null) f.put("IpAddress", ipAddress);
        if (cidrPrefixLength != null) f.put("CidrPrefixLength", cidrPrefixLength.toString());
        return f;
    }

    public static Builder builder() { return new Builder(); }

    public static final class Builder {
        private String friendlyName;
        private String ipAddress;
        private Integer cidrPrefixLength;
        public Builder friendlyName(String s) { this.friendlyName = s; return this; }
        public Builder ipAddress(String s) { this.ipAddress = s; return this; }
        public Builder cidrPrefixLength(Integer i) { this.cidrPrefixLength = i; return this; }
        public CreateSipIpAddressRequest build() { return new CreateSipIpAddressRequest(this); }
    }
}
