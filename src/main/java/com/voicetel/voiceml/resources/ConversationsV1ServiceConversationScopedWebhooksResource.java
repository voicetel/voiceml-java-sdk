package com.voicetel.voiceml.resources;

import com.voicetel.voiceml.Transport;
import com.voicetel.voiceml.models.ConversationsV1ServiceConversationScopedWebhook;
import com.voicetel.voiceml.models.ConversationsV1ServiceConversationScopedWebhookList;
import com.voicetel.voiceml.models.CreateConversationsV1ServiceConversationScopedWebhookRequest;
import com.voicetel.voiceml.models.ListPageParams;
import com.voicetel.voiceml.models.UpdateConversationsV1ServiceConversationScopedWebhookRequest;

import java.util.Map;

/** Operations on {@code /v1/Services/{ChatServiceSid}/Conversations/{ConversationSid}/Webhooks[/{WebhookSid}]}. */
public final class ConversationsV1ServiceConversationScopedWebhooksResource extends BaseResource {

    private final String chatServiceSid;
    private final String conversationSid;

    public ConversationsV1ServiceConversationScopedWebhooksResource(Transport transport,
                                                                    String chatServiceSid,
                                                                    String conversationSid) {
        super(transport);
        this.chatServiceSid = chatServiceSid;
        this.conversationSid = conversationSid;
    }

    private String base() {
        return "/v1/Services/" + chatServiceSid + "/Conversations/" + conversationSid + "/Webhooks";
    }

    public ConversationsV1ServiceConversationScopedWebhook create(CreateConversationsV1ServiceConversationScopedWebhookRequest body) {
        Map<String, Object> form = body != null ? body.toForm() : null;
        return decode(transport.request("POST", base(), null, form),
                ConversationsV1ServiceConversationScopedWebhook.class);
    }

    public ConversationsV1ServiceConversationScopedWebhookList list(ListPageParams params) {
        Map<String, Object> q = params != null ? params.toQuery() : null;
        return decode(transport.request("GET", base(), q, null),
                ConversationsV1ServiceConversationScopedWebhookList.class);
    }

    public ConversationsV1ServiceConversationScopedWebhookList list() { return list(null); }

    public ConversationsV1ServiceConversationScopedWebhook fetch(String webhookSid) {
        return decode(transport.request("GET", base() + "/" + webhookSid, null, null),
                ConversationsV1ServiceConversationScopedWebhook.class);
    }

    public ConversationsV1ServiceConversationScopedWebhook update(String webhookSid,
                                                                  UpdateConversationsV1ServiceConversationScopedWebhookRequest body) {
        Map<String, Object> form = body != null ? body.toForm() : null;
        return decode(transport.request("POST", base() + "/" + webhookSid, null, form),
                ConversationsV1ServiceConversationScopedWebhook.class);
    }

    public void delete(String webhookSid) {
        transport.request("DELETE", base() + "/" + webhookSid, null, null);
    }
}
