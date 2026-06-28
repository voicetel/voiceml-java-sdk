package com.voicetel.voiceml.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;

/** Paginated service-scoped Participants response. */
@JsonIgnoreProperties(ignoreUnknown = true)
public class ConversationsV1ServiceConversationParticipantList {

    @JsonProperty("participants")
    private List<ConversationsV1ServiceConversationParticipant> participants = new ArrayList<>();

    @JsonProperty("meta")
    private VoiceV1Meta meta;

    public List<ConversationsV1ServiceConversationParticipant> getParticipants() { return participants; }
    public VoiceV1Meta getMeta() { return meta; }

    public void setParticipants(List<ConversationsV1ServiceConversationParticipant> v) {
        this.participants = v != null ? v : new ArrayList<>();
    }
    public void setMeta(VoiceV1Meta v) { this.meta = v; }
}
