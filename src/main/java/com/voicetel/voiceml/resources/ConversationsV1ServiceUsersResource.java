package com.voicetel.voiceml.resources;

import com.voicetel.voiceml.Transport;
import com.voicetel.voiceml.models.ConversationsV1ServiceUser;
import com.voicetel.voiceml.models.ConversationsV1ServiceUserList;
import com.voicetel.voiceml.models.CreateConversationsV1ServiceUserRequest;
import com.voicetel.voiceml.models.ListPageParams;
import com.voicetel.voiceml.models.UpdateConversationsV1ServiceUserRequest;

import java.util.Map;

/** Operations on {@code /v1/Services/{ChatServiceSid}/Users[/{Sid}]} and the nested per-user conversations sub-resource. */
public final class ConversationsV1ServiceUsersResource extends BaseResource {

    private final String chatServiceSid;

    public ConversationsV1ServiceUsersResource(Transport transport, String chatServiceSid) {
        super(transport);
        this.chatServiceSid = chatServiceSid;
    }

    private String base() { return "/v1/Services/" + chatServiceSid + "/Users"; }

    public ConversationsV1ServiceUser create(CreateConversationsV1ServiceUserRequest body) {
        Map<String, Object> form = body != null ? body.toForm() : null;
        return decode(transport.request("POST", base(), null, form),
                ConversationsV1ServiceUser.class);
    }

    public ConversationsV1ServiceUserList list(ListPageParams params) {
        Map<String, Object> q = params != null ? params.toQuery() : null;
        return decode(transport.request("GET", base(), q, null),
                ConversationsV1ServiceUserList.class);
    }

    public ConversationsV1ServiceUserList list() { return list(null); }

    public ConversationsV1ServiceUser fetch(String sid) {
        return decode(transport.request("GET", base() + "/" + sid, null, null),
                ConversationsV1ServiceUser.class);
    }

    public ConversationsV1ServiceUser update(String sid, UpdateConversationsV1ServiceUserRequest body) {
        Map<String, Object> form = body != null ? body.toForm() : null;
        return decode(transport.request("POST", base() + "/" + sid, null, form),
                ConversationsV1ServiceUser.class);
    }

    public void delete(String sid) {
        transport.request("DELETE", base() + "/" + sid, null, null);
    }

    /** Sub-resource handle for {@code .../Users/{UserSid}/Conversations}. */
    public ConversationsV1ServiceUserConversationsResource conversations(String userSid) {
        return new ConversationsV1ServiceUserConversationsResource(transport, chatServiceSid, userSid);
    }
}
