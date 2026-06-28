package com.voicetel.voiceml.resources;

import com.voicetel.voiceml.Transport;
import com.voicetel.voiceml.models.ConversationsV1ServiceConversationMessageReceipt;
import com.voicetel.voiceml.models.ConversationsV1ServiceConversationMessageReceiptList;
import com.voicetel.voiceml.models.ListPageParams;

import java.util.Map;

/** Read-only ops on {@code /v1/Services/{ChatServiceSid}/Conversations/{ConversationSid}/Messages/{MessageSid}/Receipts}. */
public final class ConversationsV1ServiceConversationMessageReceiptsResource extends BaseResource {

    private final String chatServiceSid;
    private final String conversationSid;
    private final String messageSid;

    public ConversationsV1ServiceConversationMessageReceiptsResource(Transport transport,
                                                                     String chatServiceSid,
                                                                     String conversationSid,
                                                                     String messageSid) {
        super(transport);
        this.chatServiceSid = chatServiceSid;
        this.conversationSid = conversationSid;
        this.messageSid = messageSid;
    }

    private String base() {
        return "/v1/Services/" + chatServiceSid
                + "/Conversations/" + conversationSid
                + "/Messages/" + messageSid + "/Receipts";
    }

    public ConversationsV1ServiceConversationMessageReceiptList list(ListPageParams params) {
        Map<String, Object> q = params != null ? params.toQuery() : null;
        return decode(transport.request("GET", base(), q, null),
                ConversationsV1ServiceConversationMessageReceiptList.class);
    }

    public ConversationsV1ServiceConversationMessageReceiptList list() { return list(null); }

    public ConversationsV1ServiceConversationMessageReceipt fetch(String sid) {
        return decode(transport.request("GET", base() + "/" + sid, null, null),
                ConversationsV1ServiceConversationMessageReceipt.class);
    }
}
