package com.voicetel.voiceml.resources;

import com.voicetel.voiceml.Transport;
import com.voicetel.voiceml.models.ConversationsV1ConversationMessage;
import com.voicetel.voiceml.models.ConversationsV1ConversationMessageList;
import com.voicetel.voiceml.models.CreateConversationsV1ConversationMessageRequest;
import com.voicetel.voiceml.models.ListPageParams;
import com.voicetel.voiceml.models.UpdateConversationsV1ConversationMessageRequest;

import java.util.Map;

/** Operations on {@code /v1/Conversations/{ConversationSid}/Messages[/{MessageSid}]}. */
public final class ConversationsV1ConversationMessagesResource extends BaseResource {

    private final String conversationSid;

    public ConversationsV1ConversationMessagesResource(Transport transport, String conversationSid) {
        super(transport);
        this.conversationSid = conversationSid;
    }

    private String base() {
        return "/v1/Conversations/" + conversationSid + "/Messages";
    }

    public ConversationsV1ConversationMessage create(CreateConversationsV1ConversationMessageRequest body) {
        Map<String, Object> form = body != null ? body.toForm() : null;
        return decode(transport.request("POST", base(), null, form),
                ConversationsV1ConversationMessage.class);
    }

    public ConversationsV1ConversationMessageList list(ListPageParams params) {
        Map<String, Object> q = params != null ? params.toQuery() : null;
        return decode(transport.request("GET", base(), q, null),
                ConversationsV1ConversationMessageList.class);
    }

    public ConversationsV1ConversationMessageList list() { return list(null); }

    public ConversationsV1ConversationMessage fetch(String messageSid) {
        return decode(transport.request("GET", base() + "/" + messageSid, null, null),
                ConversationsV1ConversationMessage.class);
    }

    public ConversationsV1ConversationMessage update(String messageSid,
                                                     UpdateConversationsV1ConversationMessageRequest body) {
        Map<String, Object> form = body != null ? body.toForm() : null;
        return decode(transport.request("POST", base() + "/" + messageSid, null, form),
                ConversationsV1ConversationMessage.class);
    }

    public void delete(String messageSid) {
        transport.request("DELETE", base() + "/" + messageSid, null, null);
    }

    /** Sub-resource handle for {@code .../Messages/{MessageSid}/Receipts}. */
    public ConversationsV1ConversationMessageReceiptsResource receipts(String messageSid) {
        return new ConversationsV1ConversationMessageReceiptsResource(
                transport, conversationSid, messageSid);
    }
}
