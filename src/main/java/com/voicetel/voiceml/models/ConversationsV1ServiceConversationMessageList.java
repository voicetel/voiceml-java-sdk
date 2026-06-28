package com.voicetel.voiceml.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;

/** Paginated {@code /v1/Services/{ChatServiceSid}/Conversations/{ConversationSid}/Messages} response. */
@JsonIgnoreProperties(ignoreUnknown = true)
public class ConversationsV1ServiceConversationMessageList {

    @JsonProperty("messages")
    private List<ConversationsV1ServiceConversationMessage> messages = new ArrayList<>();

    @JsonProperty("meta")
    private VoiceV1Meta meta;

    public List<ConversationsV1ServiceConversationMessage> getMessages() { return messages; }
    public VoiceV1Meta getMeta() { return meta; }

    public void setMessages(List<ConversationsV1ServiceConversationMessage> v) {
        this.messages = v != null ? v : new ArrayList<>();
    }
    public void setMeta(VoiceV1Meta v) { this.meta = v; }
}
