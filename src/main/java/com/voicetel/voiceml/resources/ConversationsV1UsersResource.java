package com.voicetel.voiceml.resources;

import com.voicetel.voiceml.Transport;
import com.voicetel.voiceml.models.ConversationsV1User;
import com.voicetel.voiceml.models.ConversationsV1UserList;
import com.voicetel.voiceml.models.CreateConversationsV1UserRequest;
import com.voicetel.voiceml.models.ListPageParams;
import com.voicetel.voiceml.models.UpdateConversationsV1UserRequest;

import java.util.Map;

/** Operations on {@code /v1/Users[/{Sid}]} and the nested per-user conversations sub-resource. */
public final class ConversationsV1UsersResource extends BaseResource {

    public ConversationsV1UsersResource(Transport transport) {
        super(transport);
    }

    public ConversationsV1User create(CreateConversationsV1UserRequest body) {
        Map<String, Object> form = body != null ? body.toForm() : null;
        return decode(transport.request("POST", "/v1/Users", null, form), ConversationsV1User.class);
    }

    public ConversationsV1UserList list(ListPageParams params) {
        Map<String, Object> q = params != null ? params.toQuery() : null;
        return decode(transport.request("GET", "/v1/Users", q, null), ConversationsV1UserList.class);
    }

    public ConversationsV1UserList list() { return list(null); }

    public ConversationsV1User fetch(String sid) {
        return decode(transport.request("GET", "/v1/Users/" + sid, null, null), ConversationsV1User.class);
    }

    public ConversationsV1User update(String sid, UpdateConversationsV1UserRequest body) {
        Map<String, Object> form = body != null ? body.toForm() : null;
        return decode(transport.request("POST", "/v1/Users/" + sid, null, form), ConversationsV1User.class);
    }

    public void delete(String sid) {
        transport.request("DELETE", "/v1/Users/" + sid, null, null);
    }

    /** Sub-resource handle for {@code /v1/Users/{Sid}/Conversations}. */
    public ConversationsV1UserConversationsResource conversations(String userSid) {
        return new ConversationsV1UserConversationsResource(transport, userSid);
    }
}
