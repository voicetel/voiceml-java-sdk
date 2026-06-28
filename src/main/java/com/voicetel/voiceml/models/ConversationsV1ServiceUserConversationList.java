package com.voicetel.voiceml.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;

/** Paginated service-scoped UserConversations response. */
@JsonIgnoreProperties(ignoreUnknown = true)
public class ConversationsV1ServiceUserConversationList {

    @JsonProperty("conversations")
    private List<ConversationsV1ServiceUserConversation> conversations = new ArrayList<>();

    @JsonProperty("meta")
    private VoiceV1Meta meta;

    public List<ConversationsV1ServiceUserConversation> getConversations() { return conversations; }
    public VoiceV1Meta getMeta() { return meta; }

    public void setConversations(List<ConversationsV1ServiceUserConversation> v) {
        this.conversations = v != null ? v : new ArrayList<>();
    }
    public void setMeta(VoiceV1Meta v) { this.meta = v; }
}
