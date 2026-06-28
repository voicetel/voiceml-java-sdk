package com.voicetel.voiceml.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;

/** Paginated {@code /v1/ConnectionPolicies} response. */
@JsonIgnoreProperties(ignoreUnknown = true)
public class VoiceV1ConnectionPolicyList {

    @JsonProperty("connection_policies")
    private List<VoiceV1ConnectionPolicy> connectionPolicies = new ArrayList<>();

    @JsonProperty("meta")
    private VoiceV1Meta meta;

    public List<VoiceV1ConnectionPolicy> getConnectionPolicies() { return connectionPolicies; }
    public VoiceV1Meta getMeta() { return meta; }

    public void setConnectionPolicies(List<VoiceV1ConnectionPolicy> v) {
        this.connectionPolicies = v != null ? v : new ArrayList<>();
    }
    public void setMeta(VoiceV1Meta v) { this.meta = v; }
}
