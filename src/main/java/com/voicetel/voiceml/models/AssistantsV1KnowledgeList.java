package com.voicetel.voiceml.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;

/** Paginated {@code /v1/Knowledge} response. */
@JsonIgnoreProperties(ignoreUnknown = true)
public class AssistantsV1KnowledgeList {

    @JsonProperty("knowledge")
    private List<AssistantsV1Knowledge> knowledge = new ArrayList<>();

    @JsonProperty("meta")
    private VoiceV1Meta meta;

    public List<AssistantsV1Knowledge> getKnowledge() { return knowledge; }
    public VoiceV1Meta getMeta() { return meta; }

    public void setKnowledge(List<AssistantsV1Knowledge> v) {
        this.knowledge = v != null ? v : new ArrayList<>();
    }
    public void setMeta(VoiceV1Meta v) { this.meta = v; }
}
