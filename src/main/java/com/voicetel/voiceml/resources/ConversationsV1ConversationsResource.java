package com.voicetel.voiceml.resources;

import com.voicetel.voiceml.Transport;
import com.voicetel.voiceml.models.ConversationsV1Conversation;
import com.voicetel.voiceml.models.ConversationsV1ConversationList;
import com.voicetel.voiceml.models.CreateConversationsV1ConversationRequest;
import com.voicetel.voiceml.models.ListPageParams;
import com.voicetel.voiceml.models.UpdateConversationsV1ConversationRequest;

import java.util.Map;

/** Operations on {@code /v1/Conversations[/{ConversationSid}]} and its sub-resources. */
public final class ConversationsV1ConversationsResource extends BaseResource {

    public ConversationsV1ConversationsResource(Transport transport) {
        super(transport);
    }

    public ConversationsV1Conversation create(CreateConversationsV1ConversationRequest body) {
        Map<String, Object> form = body != null ? body.toForm() : null;
        return decode(transport.request("POST", "/v1/Conversations", null, form),
                ConversationsV1Conversation.class);
    }

    public ConversationsV1ConversationList list(ListPageParams params) {
        Map<String, Object> q = params != null ? params.toQuery() : null;
        return decode(transport.request("GET", "/v1/Conversations", q, null),
                ConversationsV1ConversationList.class);
    }

    public ConversationsV1ConversationList list() { return list(null); }

    public ConversationsV1Conversation fetch(String conversationSid) {
        return decode(transport.request("GET", "/v1/Conversations/" + conversationSid, null, null),
                ConversationsV1Conversation.class);
    }

    public ConversationsV1Conversation update(String conversationSid,
                                              UpdateConversationsV1ConversationRequest body) {
        Map<String, Object> form = body != null ? body.toForm() : null;
        return decode(transport.request("POST", "/v1/Conversations/" + conversationSid, null, form),
                ConversationsV1Conversation.class);
    }

    public void delete(String conversationSid) {
        transport.request("DELETE", "/v1/Conversations/" + conversationSid, null, null);
    }

    /** Sub-resource handle for {@code .../Messages}. */
    public ConversationsV1ConversationMessagesResource messages(String conversationSid) {
        return new ConversationsV1ConversationMessagesResource(transport, conversationSid);
    }

    /** Sub-resource handle for {@code .../Participants}. */
    public ConversationsV1ConversationParticipantsResource participants(String conversationSid) {
        return new ConversationsV1ConversationParticipantsResource(transport, conversationSid);
    }

    /** Sub-resource handle for {@code .../Webhooks}. */
    public ConversationsV1ConversationScopedWebhooksResource webhooks(String conversationSid) {
        return new ConversationsV1ConversationScopedWebhooksResource(transport, conversationSid);
    }
}
