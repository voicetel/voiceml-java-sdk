package com.voicetel.voiceml.resources;

import com.voicetel.voiceml.Transport;
import com.voicetel.voiceml.models.ConversationsV1ServiceRole;
import com.voicetel.voiceml.models.ConversationsV1ServiceRoleList;
import com.voicetel.voiceml.models.CreateConversationsV1ServiceRoleRequest;
import com.voicetel.voiceml.models.ListPageParams;
import com.voicetel.voiceml.models.UpdateConversationsV1ServiceRoleRequest;

import java.util.Map;

/** Operations on {@code /v1/Services/{ChatServiceSid}/Roles[/{Sid}]}. */
public final class ConversationsV1ServiceRolesResource extends BaseResource {

    private final String chatServiceSid;

    public ConversationsV1ServiceRolesResource(Transport transport, String chatServiceSid) {
        super(transport);
        this.chatServiceSid = chatServiceSid;
    }

    private String base() { return "/v1/Services/" + chatServiceSid + "/Roles"; }

    public ConversationsV1ServiceRole create(CreateConversationsV1ServiceRoleRequest body) {
        Map<String, Object> form = body != null ? body.toForm() : null;
        return decode(transport.request("POST", base(), null, form),
                ConversationsV1ServiceRole.class);
    }

    public ConversationsV1ServiceRoleList list(ListPageParams params) {
        Map<String, Object> q = params != null ? params.toQuery() : null;
        return decode(transport.request("GET", base(), q, null),
                ConversationsV1ServiceRoleList.class);
    }

    public ConversationsV1ServiceRoleList list() { return list(null); }

    public ConversationsV1ServiceRole fetch(String sid) {
        return decode(transport.request("GET", base() + "/" + sid, null, null),
                ConversationsV1ServiceRole.class);
    }

    public ConversationsV1ServiceRole update(String sid, UpdateConversationsV1ServiceRoleRequest body) {
        Map<String, Object> form = body != null ? body.toForm() : null;
        return decode(transport.request("POST", base() + "/" + sid, null, form),
                ConversationsV1ServiceRole.class);
    }

    public void delete(String sid) {
        transport.request("DELETE", base() + "/" + sid, null, null);
    }
}
