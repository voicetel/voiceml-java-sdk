package com.voicetel.voiceml.models;

import java.util.LinkedHashMap;
import java.util.Map;

/** Form body for {@code POST /v1/SourceIpMappings}. Both fields required. */
public final class CreateVoiceV1SourceIpMappingRequest {

    private final String ipRecordSid;
    private final String sipDomainSid;

    private CreateVoiceV1SourceIpMappingRequest(Builder b) {
        this.ipRecordSid = b.ipRecordSid;
        this.sipDomainSid = b.sipDomainSid;
    }

    public Map<String, Object> toForm() {
        Map<String, Object> f = new LinkedHashMap<>();
        if (ipRecordSid != null) f.put("IpRecordSid", ipRecordSid);
        if (sipDomainSid != null) f.put("SipDomainSid", sipDomainSid);
        return f;
    }

    public static Builder builder() { return new Builder(); }

    public static final class Builder {
        private String ipRecordSid;
        private String sipDomainSid;
        public Builder ipRecordSid(String s) { this.ipRecordSid = s; return this; }
        public Builder sipDomainSid(String s) { this.sipDomainSid = s; return this; }
        public CreateVoiceV1SourceIpMappingRequest build() { return new CreateVoiceV1SourceIpMappingRequest(this); }
    }
}
