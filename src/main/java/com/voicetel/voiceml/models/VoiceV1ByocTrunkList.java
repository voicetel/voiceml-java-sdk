package com.voicetel.voiceml.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;

/** Paginated {@code /v1/ByocTrunks} response. */
@JsonIgnoreProperties(ignoreUnknown = true)
public class VoiceV1ByocTrunkList {

    @JsonProperty("byoc_trunks")
    private List<VoiceV1ByocTrunk> byocTrunks = new ArrayList<>();

    @JsonProperty("meta")
    private VoiceV1Meta meta;

    public List<VoiceV1ByocTrunk> getByocTrunks() { return byocTrunks; }
    public VoiceV1Meta getMeta() { return meta; }

    public void setByocTrunks(List<VoiceV1ByocTrunk> v) {
        this.byocTrunks = v != null ? v : new ArrayList<>();
    }
    public void setMeta(VoiceV1Meta v) { this.meta = v; }
}
