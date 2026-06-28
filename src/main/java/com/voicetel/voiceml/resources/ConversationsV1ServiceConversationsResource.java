package com.voicetel.voiceml.resources;

import com.voicetel.voiceml.Transport;
import com.voicetel.voiceml.models.ConversationsV1ServiceConversation;
import com.voicetel.voiceml.models.ConversationsV1ServiceConversationList;
import com.voicetel.voiceml.models.CreateConversationsV1ServiceConversationRequest;
import com.voicetel.voiceml.models.ListPageParams;
import com.voicetel.voiceml.models.UpdateConversationsV1ServiceConversationRequest;

import java.util.Map;

/** Operations on {@code /v1/Services/{ChatServiceSid}/Conversations[/{ConversationSid}]} and its sub-resources. */
public final class ConversationsV1ServiceConversationsResource extends BaseResource {

    private final String chatServiceSid;

    public ConversationsV1ServiceConversationsResource(Transport transport, String chatServiceSid) {
        super(transport);
        this.chatServiceSid = chatServiceSid;
    }

    private String base() {
        return "/v1/Services/" + chatServiceSid + "/Conversations";
    }

    public ConversationsV1ServiceConversation create(CreateConversationsV1ServiceConversationRequest body) {
        Map<String, Object> form = body != null ? body.toForm() : null;
        return decode(transport.request("POST", base(), null, form),
                ConversationsV1ServiceConversation.class);
    }

    public ConversationsV1ServiceConversationList list(ListPageParams params) {
        Map<String, Object> q = params != null ? params.toQuery() : null;
        return decode(transport.request("GET", base(), q, null),
                ConversationsV1ServiceConversationList.class);
    }

    public ConversationsV1ServiceConversationList list() { return list(null); }

    public ConversationsV1ServiceConversation fetch(String conversationSid) {
        return decode(transport.request("GET", base() + "/" + conversationSid, null, null),
                ConversationsV1ServiceConversation.class);
    }

    public ConversationsV1ServiceConversation update(String conversationSid,
                                                     UpdateConversationsV1ServiceConversationRequest body) {
        Map<String, Object> form = body != null ? body.toForm() : null;
        return decode(transport.request("POST", base() + "/" + conversationSid, null, form),
                ConversationsV1ServiceConversation.class);
    }

    public void delete(String conversationSid) {
        transport.request("DELETE", base() + "/" + conversationSid, null, null);
    }

    /** Sub-resource handle for {@code .../Messages}. */
    public ConversationsV1ServiceConversationMessagesResource messages(String conversationSid) {
        return new ConversationsV1ServiceConversationMessagesResource(transport, chatServiceSid, conversationSid);
    }

    /** Sub-resource handle for {@code .../Participants}. */
    public ConversationsV1ServiceConversationParticipantsResource participants(String conversationSid) {
        return new ConversationsV1ServiceConversationParticipantsResource(transport, chatServiceSid, conversationSid);
    }

    /** Sub-resource handle for {@code .../Webhooks}. */
    public ConversationsV1ServiceConversationScopedWebhooksResource webhooks(String conversationSid) {
        return new ConversationsV1ServiceConversationScopedWebhooksResource(transport, chatServiceSid, conversationSid);
    }
}
