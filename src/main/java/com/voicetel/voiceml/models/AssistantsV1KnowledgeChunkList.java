package com.voicetel.voiceml.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;

/** Paginated {@code /v1/Knowledge/{id}/Chunks} response. */
@JsonIgnoreProperties(ignoreUnknown = true)
public class AssistantsV1KnowledgeChunkList {

    @JsonProperty("chunks")
    private List<AssistantsV1KnowledgeChunk> chunks = new ArrayList<>();

    @JsonProperty("meta")
    private VoiceV1Meta meta;

    public List<AssistantsV1KnowledgeChunk> getChunks() { return chunks; }
    public VoiceV1Meta getMeta() { return meta; }

    public void setChunks(List<AssistantsV1KnowledgeChunk> v) {
        this.chunks = v != null ? v : new ArrayList<>();
    }
    public void setMeta(VoiceV1Meta v) { this.meta = v; }
}
