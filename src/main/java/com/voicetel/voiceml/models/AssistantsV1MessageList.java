package com.voicetel.voiceml.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;

/** Paginated {@code /v1/Sessions/{id}/Messages} response. */
@JsonIgnoreProperties(ignoreUnknown = true)
public class AssistantsV1MessageList {

    @JsonProperty("messages")
    private List<AssistantsV1Message> messages = new ArrayList<>();

    @JsonProperty("meta")
    private VoiceV1Meta meta;

    public List<AssistantsV1Message> getMessages() { return messages; }
    public VoiceV1Meta getMeta() { return meta; }

    public void setMessages(List<AssistantsV1Message> v) {
        this.messages = v != null ? v : new ArrayList<>();
    }
    public void setMeta(VoiceV1Meta v) { this.meta = v; }
}
