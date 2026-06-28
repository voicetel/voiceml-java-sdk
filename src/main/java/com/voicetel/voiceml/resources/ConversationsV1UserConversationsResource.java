package com.voicetel.voiceml.resources;

import com.voicetel.voiceml.Transport;
import com.voicetel.voiceml.models.ConversationsV1UserConversation;
import com.voicetel.voiceml.models.ConversationsV1UserConversationList;
import com.voicetel.voiceml.models.ListPageParams;
import com.voicetel.voiceml.models.UpdateConversationsV1UserConversationRequest;

import java.util.Map;

/** Operations on {@code /v1/Users/{Sid}/Conversations[/{ConversationSid}]}. */
public final class ConversationsV1UserConversationsResource extends BaseResource {

    private final String userSid;

    public ConversationsV1UserConversationsResource(Transport transport, String userSid) {
        super(transport);
        this.userSid = userSid;
    }

    private String base() {
        return "/v1/Users/" + userSid + "/Conversations";
    }

    public ConversationsV1UserConversationList list(ListPageParams params) {
        Map<String, Object> q = params != null ? params.toQuery() : null;
        return decode(transport.request("GET", base(), q, null),
                ConversationsV1UserConversationList.class);
    }

    public ConversationsV1UserConversationList list() { return list(null); }

    public ConversationsV1UserConversation fetch(String conversationSid) {
        return decode(transport.request("GET", base() + "/" + conversationSid, null, null),
                ConversationsV1UserConversation.class);
    }

    public ConversationsV1UserConversation update(String conversationSid,
                                                  UpdateConversationsV1UserConversationRequest body) {
        Map<String, Object> form = body != null ? body.toForm() : null;
        return decode(transport.request("POST", base() + "/" + conversationSid, null, form),
                ConversationsV1UserConversation.class);
    }

    public void delete(String conversationSid) {
        transport.request("DELETE", base() + "/" + conversationSid, null, null);
    }
}
