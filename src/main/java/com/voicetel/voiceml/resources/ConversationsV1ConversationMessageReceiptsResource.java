package com.voicetel.voiceml.resources;

import com.voicetel.voiceml.Transport;
import com.voicetel.voiceml.models.ConversationsV1ConversationMessageReceipt;
import com.voicetel.voiceml.models.ConversationsV1ConversationMessageReceiptList;
import com.voicetel.voiceml.models.ListPageParams;

import java.util.Map;

/** Read-only ops on {@code /v1/Conversations/{ConversationSid}/Messages/{MessageSid}/Receipts}. */
public final class ConversationsV1ConversationMessageReceiptsResource extends BaseResource {

    private final String conversationSid;
    private final String messageSid;

    public ConversationsV1ConversationMessageReceiptsResource(Transport transport,
                                                              String conversationSid,
                                                              String messageSid) {
        super(transport);
        this.conversationSid = conversationSid;
        this.messageSid = messageSid;
    }

    private String base() {
        return "/v1/Conversations/" + conversationSid + "/Messages/" + messageSid + "/Receipts";
    }

    public ConversationsV1ConversationMessageReceiptList list(ListPageParams params) {
        Map<String, Object> q = params != null ? params.toQuery() : null;
        return decode(transport.request("GET", base(), q, null),
                ConversationsV1ConversationMessageReceiptList.class);
    }

    public ConversationsV1ConversationMessageReceiptList list() { return list(null); }

    public ConversationsV1ConversationMessageReceipt fetch(String sid) {
        return decode(transport.request("GET", base() + "/" + sid, null, null),
                ConversationsV1ConversationMessageReceipt.class);
    }
}
