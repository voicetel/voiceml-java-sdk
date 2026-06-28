package com.voicetel.voiceml.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;

/** Paginated {@code /v1/Conversations/{ConversationSid}/Webhooks} response. */
@JsonIgnoreProperties(ignoreUnknown = true)
public class ConversationsV1ConversationScopedWebhookList {

    @JsonProperty("webhooks")
    private List<ConversationsV1ConversationScopedWebhook> webhooks = new ArrayList<>();

    @JsonProperty("meta")
    private VoiceV1Meta meta;

    public List<ConversationsV1ConversationScopedWebhook> getWebhooks() { return webhooks; }
    public VoiceV1Meta getMeta() { return meta; }

    public void setWebhooks(List<ConversationsV1ConversationScopedWebhook> v) {
        this.webhooks = v != null ? v : new ArrayList<>();
    }
    public void setMeta(VoiceV1Meta v) { this.meta = v; }
}
