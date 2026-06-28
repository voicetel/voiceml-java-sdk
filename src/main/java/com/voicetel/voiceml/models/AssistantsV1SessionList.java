package com.voicetel.voiceml.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;

/** Paginated {@code /v1/Sessions} response. */
@JsonIgnoreProperties(ignoreUnknown = true)
public class AssistantsV1SessionList {

    @JsonProperty("sessions")
    private List<AssistantsV1Session> sessions = new ArrayList<>();

    @JsonProperty("meta")
    private VoiceV1Meta meta;

    public List<AssistantsV1Session> getSessions() { return sessions; }
    public VoiceV1Meta getMeta() { return meta; }

    public void setSessions(List<AssistantsV1Session> v) {
        this.sessions = v != null ? v : new ArrayList<>();
    }
    public void setMeta(VoiceV1Meta v) { this.meta = v; }
}
