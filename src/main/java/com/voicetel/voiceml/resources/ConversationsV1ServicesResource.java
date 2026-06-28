package com.voicetel.voiceml.resources;

import com.voicetel.voiceml.Transport;
import com.voicetel.voiceml.models.ConversationsV1Service;
import com.voicetel.voiceml.models.ConversationsV1ServiceList;
import com.voicetel.voiceml.models.CreateConversationsV1ServiceRequest;
import com.voicetel.voiceml.models.ListPageParams;

import java.util.Map;

/** Operations on {@code /v1/Services[/{ChatServiceSid}]}. No update endpoint. */
public final class ConversationsV1ServicesResource extends BaseResource {

    public ConversationsV1ServicesResource(Transport transport) {
        super(transport);
    }

    public ConversationsV1Service create(CreateConversationsV1ServiceRequest body) {
        Map<String, Object> form = body != null ? body.toForm() : null;
        return decode(transport.request("POST", "/v1/Services", null, form),
                ConversationsV1Service.class);
    }

    public ConversationsV1ServiceList list(ListPageParams params) {
        Map<String, Object> q = params != null ? params.toQuery() : null;
        return decode(transport.request("GET", "/v1/Services", q, null),
                ConversationsV1ServiceList.class);
    }

    public ConversationsV1ServiceList list() { return list(null); }

    public ConversationsV1Service fetch(String chatServiceSid) {
        return decode(transport.request("GET", "/v1/Services/" + chatServiceSid, null, null),
                ConversationsV1Service.class);
    }

    public void delete(String chatServiceSid) {
        transport.request("DELETE", "/v1/Services/" + chatServiceSid, null, null);
    }

    /** Sub-resource handle for {@code /v1/Services/{ChatServiceSid}/*} (Phase 4). */
    public ConversationsV1ServiceScopeResource scope(String chatServiceSid) {
        return new ConversationsV1ServiceScopeResource(transport, chatServiceSid);
    }
}
