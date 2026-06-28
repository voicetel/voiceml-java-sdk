package com.voicetel.voiceml.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;

/** Paginated {@code /v1/ParticipantConversations} response. */
@JsonIgnoreProperties(ignoreUnknown = true)
public class ConversationsV1ParticipantConversationList {

    @JsonProperty("conversations")
    private List<ConversationsV1ParticipantConversation> conversations = new ArrayList<>();

    @JsonProperty("meta")
    private VoiceV1Meta meta;

    public List<ConversationsV1ParticipantConversation> getConversations() { return conversations; }
    public VoiceV1Meta getMeta() { return meta; }

    public void setConversations(List<ConversationsV1ParticipantConversation> v) {
        this.conversations = v != null ? v : new ArrayList<>();
    }
    public void setMeta(VoiceV1Meta v) { this.meta = v; }
}
