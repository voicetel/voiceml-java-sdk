package com.voicetel.voiceml.models;

import java.util.LinkedHashMap;
import java.util.Map;

/** Form body for {@code POST /v1/SourceIpMappings/{Sid}}. Only {@code SipDomainSid} is mutable. */
public final class UpdateVoiceV1SourceIpMappingRequest {

    private final String sipDomainSid;

    private UpdateVoiceV1SourceIpMappingRequest(Builder b) {
        this.sipDomainSid = b.sipDomainSid;
    }

    public Map<String, Object> toForm() {
        Map<String, Object> f = new LinkedHashMap<>();
        if (sipDomainSid != null) f.put("SipDomainSid", sipDomainSid);
        return f;
    }

    public static Builder builder() { return new Builder(); }

    public static final class Builder {
        private String sipDomainSid;
        public Builder sipDomainSid(String s) { this.sipDomainSid = s; return this; }
        public UpdateVoiceV1SourceIpMappingRequest build() { return new UpdateVoiceV1SourceIpMappingRequest(this); }
    }
}
