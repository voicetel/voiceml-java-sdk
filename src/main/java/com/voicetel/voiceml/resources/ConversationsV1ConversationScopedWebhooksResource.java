package com.voicetel.voiceml.resources;

import com.voicetel.voiceml.Transport;
import com.voicetel.voiceml.models.ConversationsV1ConversationScopedWebhook;
import com.voicetel.voiceml.models.ConversationsV1ConversationScopedWebhookList;
import com.voicetel.voiceml.models.CreateConversationsV1ConversationScopedWebhookRequest;
import com.voicetel.voiceml.models.ListPageParams;
import com.voicetel.voiceml.models.UpdateConversationsV1ConversationScopedWebhookRequest;

import java.util.Map;

/** Operations on {@code /v1/Conversations/{ConversationSid}/Webhooks[/{WebhookSid}]}. */
public final class ConversationsV1ConversationScopedWebhooksResource extends BaseResource {

    private final String conversationSid;

    public ConversationsV1ConversationScopedWebhooksResource(Transport transport, String conversationSid) {
        super(transport);
        this.conversationSid = conversationSid;
    }

    private String base() {
        return "/v1/Conversations/" + conversationSid + "/Webhooks";
    }

    public ConversationsV1ConversationScopedWebhook create(CreateConversationsV1ConversationScopedWebhookRequest body) {
        Map<String, Object> form = body != null ? body.toForm() : null;
        return decode(transport.request("POST", base(), null, form),
                ConversationsV1ConversationScopedWebhook.class);
    }

    public ConversationsV1ConversationScopedWebhookList list(ListPageParams params) {
        Map<String, Object> q = params != null ? params.toQuery() : null;
        return decode(transport.request("GET", base(), q, null),
                ConversationsV1ConversationScopedWebhookList.class);
    }

    public ConversationsV1ConversationScopedWebhookList list() { return list(null); }

    public ConversationsV1ConversationScopedWebhook fetch(String webhookSid) {
        return decode(transport.request("GET", base() + "/" + webhookSid, null, null),
                ConversationsV1ConversationScopedWebhook.class);
    }

    public ConversationsV1ConversationScopedWebhook update(String webhookSid,
                                                           UpdateConversationsV1ConversationScopedWebhookRequest body) {
        Map<String, Object> form = body != null ? body.toForm() : null;
        return decode(transport.request("POST", base() + "/" + webhookSid, null, form),
                ConversationsV1ConversationScopedWebhook.class);
    }

    public void delete(String webhookSid) {
        transport.request("DELETE", base() + "/" + webhookSid, null, null);
    }
}
