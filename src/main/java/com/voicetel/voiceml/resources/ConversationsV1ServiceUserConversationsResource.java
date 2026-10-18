package com.voicetel.voiceml.resources;

import com.voicetel.voiceml.Transport;
import com.voicetel.voiceml.models.ConversationsV1ServiceUserConversationList;
import com.voicetel.voiceml.models.ListPageParams;

import java.util.Map;

/** List-only ops on {@code /v1/Services/{ChatServiceSid}/Users/{UserSid}/Conversations}. */
public final class ConversationsV1ServiceUserConversationsResource extends BaseResource {

    private final String chatServiceSid;
    private final String userSid;

    public ConversationsV1ServiceUserConversationsResource(Transport transport,
                                                           String chatServiceSid,
                                                           String userSid) {
        super(transport);
        this.chatServiceSid = chatServiceSid;
        this.userSid = userSid;
    }

    public ConversationsV1ServiceUserConversationList list(ListPageParams params) {
        Map<String, Object> q = params != null ? params.toPageSizeQuery() : null;
        return decode(transport.request("GET",
                        "/v1/Services/" + seg(chatServiceSid) + "/Users/" + seg(userSid) + "/Conversations",
                        q, null),
                ConversationsV1ServiceUserConversationList.class);
    }

    public ConversationsV1ServiceUserConversationList list() { return list(null); }
}
