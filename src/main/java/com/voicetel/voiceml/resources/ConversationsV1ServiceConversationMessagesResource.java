package com.voicetel.voiceml.resources;

import com.voicetel.voiceml.Transport;
import com.voicetel.voiceml.models.ConversationsV1ServiceConversationMessage;
import com.voicetel.voiceml.models.ConversationsV1ServiceConversationMessageList;
import com.voicetel.voiceml.models.CreateConversationsV1ServiceConversationMessageRequest;
import com.voicetel.voiceml.models.ListPageParams;
import com.voicetel.voiceml.models.UpdateConversationsV1ServiceConversationMessageRequest;

import java.util.Map;

/** Operations on {@code /v1/Services/{ChatServiceSid}/Conversations/{ConversationSid}/Messages[/{MessageSid}]}. */
public final class ConversationsV1ServiceConversationMessagesResource extends BaseResource {

    private final String chatServiceSid;
    private final String conversationSid;

    public ConversationsV1ServiceConversationMessagesResource(Transport transport,
                                                              String chatServiceSid,
                                                              String conversationSid) {
        super(transport);
        this.chatServiceSid = chatServiceSid;
        this.conversationSid = conversationSid;
    }

    private String base() {
        return "/v1/Services/" + chatServiceSid + "/Conversations/" + conversationSid + "/Messages";
    }

    public ConversationsV1ServiceConversationMessage create(CreateConversationsV1ServiceConversationMessageRequest body) {
        Map<String, Object> form = body != null ? body.toForm() : null;
        return decode(transport.request("POST", base(), null, form),
                ConversationsV1ServiceConversationMessage.class);
    }

    public ConversationsV1ServiceConversationMessageList list(ListPageParams params) {
        Map<String, Object> q = params != null ? params.toQuery() : null;
        return decode(transport.request("GET", base(), q, null),
                ConversationsV1ServiceConversationMessageList.class);
    }

    public ConversationsV1ServiceConversationMessageList list() { return list(null); }

    public ConversationsV1ServiceConversationMessage fetch(String messageSid) {
        return decode(transport.request("GET", base() + "/" + messageSid, null, null),
                ConversationsV1ServiceConversationMessage.class);
    }

    public ConversationsV1ServiceConversationMessage update(String messageSid,
                                                            UpdateConversationsV1ServiceConversationMessageRequest body) {
        Map<String, Object> form = body != null ? body.toForm() : null;
        return decode(transport.request("POST", base() + "/" + messageSid, null, form),
                ConversationsV1ServiceConversationMessage.class);
    }

    public void delete(String messageSid) {
        transport.request("DELETE", base() + "/" + messageSid, null, null);
    }

    /** Sub-resource handle for {@code .../Messages/{MessageSid}/Receipts}. */
    public ConversationsV1ServiceConversationMessageReceiptsResource receipts(String messageSid) {
        return new ConversationsV1ServiceConversationMessageReceiptsResource(
                transport, chatServiceSid, conversationSid, messageSid);
    }
}
