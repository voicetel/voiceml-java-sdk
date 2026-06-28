package com.voicetel.voiceml.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;

/** Paginated {@code /v1/Tools} response. */
@JsonIgnoreProperties(ignoreUnknown = true)
public class AssistantsV1ToolList {

    @JsonProperty("tools")
    private List<AssistantsV1Tool> tools = new ArrayList<>();

    @JsonProperty("meta")
    private VoiceV1Meta meta;

    public List<AssistantsV1Tool> getTools() { return tools; }
    public VoiceV1Meta getMeta() { return meta; }

    public void setTools(List<AssistantsV1Tool> v) {
        this.tools = v != null ? v : new ArrayList<>();
    }
    public void setMeta(VoiceV1Meta v) { this.meta = v; }
}
