package com.voicetel.voiceml.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;

/** Paginated {@code /v1/Users/{Sid}/Conversations} response. */
@JsonIgnoreProperties(ignoreUnknown = true)
public class ConversationsV1UserConversationList {

    @JsonProperty("conversations")
    private List<ConversationsV1UserConversation> conversations = new ArrayList<>();

    @JsonProperty("meta")
    private VoiceV1Meta meta;

    public List<ConversationsV1UserConversation> getConversations() { return conversations; }
    public VoiceV1Meta getMeta() { return meta; }

    public void setConversations(List<ConversationsV1UserConversation> v) {
        this.conversations = v != null ? v : new ArrayList<>();
    }
    public void setMeta(VoiceV1Meta v) { this.meta = v; }
}
