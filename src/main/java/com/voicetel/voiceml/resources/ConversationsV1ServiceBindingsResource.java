package com.voicetel.voiceml.resources;

import com.voicetel.voiceml.Transport;
import com.voicetel.voiceml.models.ConversationsV1ServiceBinding;
import com.voicetel.voiceml.models.ConversationsV1ServiceBindingList;
import com.voicetel.voiceml.models.ListConversationsV1ServiceBindingsParams;

import java.util.Map;

/** Operations on {@code /v1/Services/{ChatServiceSid}/Bindings[/{Sid}]}. */
public final class ConversationsV1ServiceBindingsResource extends BaseResource {

    private final String chatServiceSid;

    public ConversationsV1ServiceBindingsResource(Transport transport, String chatServiceSid) {
        super(transport);
        this.chatServiceSid = chatServiceSid;
    }

    private String base() { return "/v1/Services/" + chatServiceSid + "/Bindings"; }

    public ConversationsV1ServiceBindingList list(ListConversationsV1ServiceBindingsParams params) {
        Map<String, Object> q = params != null ? params.toQuery() : null;
        return decode(transport.request("GET", base(), q, null),
                ConversationsV1ServiceBindingList.class);
    }

    public ConversationsV1ServiceBindingList list() { return list(null); }

    public ConversationsV1ServiceBinding fetch(String sid) {
        return decode(transport.request("GET", base() + "/" + sid, null, null),
                ConversationsV1ServiceBinding.class);
    }

    public void delete(String sid) {
        transport.request("DELETE", base() + "/" + sid, null, null);
    }
}
