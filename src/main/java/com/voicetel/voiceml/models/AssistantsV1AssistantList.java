package com.voicetel.voiceml.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;

/** Paginated {@code /v1/Assistants} response. */
@JsonIgnoreProperties(ignoreUnknown = true)
public class AssistantsV1AssistantList {

    @JsonProperty("assistants")
    private List<AssistantsV1Assistant> assistants = new ArrayList<>();

    @JsonProperty("meta")
    private VoiceV1Meta meta;

    public List<AssistantsV1Assistant> getAssistants() { return assistants; }
    public VoiceV1Meta getMeta() { return meta; }

    public void setAssistants(List<AssistantsV1Assistant> v) {
        this.assistants = v != null ? v : new ArrayList<>();
    }
    public void setMeta(VoiceV1Meta v) { this.meta = v; }
}
