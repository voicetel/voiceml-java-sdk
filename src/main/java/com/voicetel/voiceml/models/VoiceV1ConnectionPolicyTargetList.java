package com.voicetel.voiceml.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;

/** Paginated {@code /v1/ConnectionPolicies/{Sid}/Targets} response. */
@JsonIgnoreProperties(ignoreUnknown = true)
public class VoiceV1ConnectionPolicyTargetList {

    @JsonProperty("targets")
    private List<VoiceV1ConnectionPolicyTarget> targets = new ArrayList<>();

    @JsonProperty("meta")
    private VoiceV1Meta meta;

    public List<VoiceV1ConnectionPolicyTarget> getTargets() { return targets; }
    public VoiceV1Meta getMeta() { return meta; }

    public void setTargets(List<VoiceV1ConnectionPolicyTarget> v) {
        this.targets = v != null ? v : new ArrayList<>();
    }
    public void setMeta(VoiceV1Meta v) { this.meta = v; }
}
