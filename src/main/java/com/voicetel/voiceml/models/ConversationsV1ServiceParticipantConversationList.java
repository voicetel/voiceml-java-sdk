package com.voicetel.voiceml.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;

/** Paginated service-scoped ParticipantConversations response. */
@JsonIgnoreProperties(ignoreUnknown = true)
public class ConversationsV1ServiceParticipantConversationList {

    @JsonProperty("conversations")
    private List<ConversationsV1ServiceParticipantConversation> conversations = new ArrayList<>();

    @JsonProperty("meta")
    private VoiceV1Meta meta;

    public List<ConversationsV1ServiceParticipantConversation> getConversations() { return conversations; }
    public VoiceV1Meta getMeta() { return meta; }

    public void setConversations(List<ConversationsV1ServiceParticipantConversation> v) {
        this.conversations = v != null ? v : new ArrayList<>();
    }
    public void setMeta(VoiceV1Meta v) { this.meta = v; }
}
