package com.voicetel.voiceml.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;

/** Paginated {@code /v1/SourceIpMappings} response. */
@JsonIgnoreProperties(ignoreUnknown = true)
public class VoiceV1SourceIpMappingList {

    @JsonProperty("source_ip_mappings")
    private List<VoiceV1SourceIpMapping> sourceIpMappings = new ArrayList<>();

    @JsonProperty("meta")
    private VoiceV1Meta meta;

    public List<VoiceV1SourceIpMapping> getSourceIpMappings() { return sourceIpMappings; }
    public VoiceV1Meta getMeta() { return meta; }

    public void setSourceIpMappings(List<VoiceV1SourceIpMapping> v) {
        this.sourceIpMappings = v != null ? v : new ArrayList<>();
    }
    public void setMeta(VoiceV1Meta v) { this.meta = v; }
}
