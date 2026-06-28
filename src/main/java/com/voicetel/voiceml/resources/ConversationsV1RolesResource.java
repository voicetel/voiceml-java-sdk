package com.voicetel.voiceml.resources;

import com.voicetel.voiceml.Transport;
import com.voicetel.voiceml.models.ConversationsV1Role;
import com.voicetel.voiceml.models.ConversationsV1RoleList;
import com.voicetel.voiceml.models.CreateConversationsV1RoleRequest;
import com.voicetel.voiceml.models.ListPageParams;
import com.voicetel.voiceml.models.UpdateConversationsV1RoleRequest;

import java.util.Map;

/** Operations on {@code /v1/Roles[/{Sid}]}. */
public final class ConversationsV1RolesResource extends BaseResource {

    public ConversationsV1RolesResource(Transport transport) {
        super(transport);
    }

    public ConversationsV1Role create(CreateConversationsV1RoleRequest body) {
        Map<String, Object> form = body != null ? body.toForm() : null;
        return decode(transport.request("POST", "/v1/Roles", null, form), ConversationsV1Role.class);
    }

    public ConversationsV1RoleList list(ListPageParams params) {
        Map<String, Object> q = params != null ? params.toQuery() : null;
        return decode(transport.request("GET", "/v1/Roles", q, null), ConversationsV1RoleList.class);
    }

    public ConversationsV1RoleList list() { return list(null); }

    public ConversationsV1Role fetch(String sid) {
        return decode(transport.request("GET", "/v1/Roles/" + sid, null, null), ConversationsV1Role.class);
    }

    public ConversationsV1Role update(String sid, UpdateConversationsV1RoleRequest body) {
        Map<String, Object> form = body != null ? body.toForm() : null;
        return decode(transport.request("POST", "/v1/Roles/" + sid, null, form), ConversationsV1Role.class);
    }

    public void delete(String sid) {
        transport.request("DELETE", "/v1/Roles/" + sid, null, null);
    }
}
