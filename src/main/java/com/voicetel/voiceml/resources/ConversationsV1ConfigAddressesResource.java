package com.voicetel.voiceml.resources;

import com.voicetel.voiceml.Transport;
import com.voicetel.voiceml.models.ConversationsV1ConfigAddress;
import com.voicetel.voiceml.models.ConversationsV1ConfigAddressList;
import com.voicetel.voiceml.models.CreateConversationsV1ConfigAddressRequest;
import com.voicetel.voiceml.models.ListPageParams;
import com.voicetel.voiceml.models.UpdateConversationsV1ConfigAddressRequest;

import java.util.Map;

/** Operations on {@code /v1/Configuration/Addresses[/{Sid}]}. */
public final class ConversationsV1ConfigAddressesResource extends BaseResource {

    public ConversationsV1ConfigAddressesResource(Transport transport) {
        super(transport);
    }

    public ConversationsV1ConfigAddress create(CreateConversationsV1ConfigAddressRequest body) {
        Map<String, Object> form = body != null ? body.toForm() : null;
        return decode(transport.request("POST", "/v1/Configuration/Addresses", null, form),
                ConversationsV1ConfigAddress.class);
    }

    public ConversationsV1ConfigAddressList list(ListPageParams params) {
        Map<String, Object> q = params != null ? params.toQuery() : null;
        return decode(transport.request("GET", "/v1/Configuration/Addresses", q, null),
                ConversationsV1ConfigAddressList.class);
    }

    public ConversationsV1ConfigAddressList list() { return list(null); }

    public ConversationsV1ConfigAddress fetch(String sid) {
        return decode(transport.request("GET", "/v1/Configuration/Addresses/" + sid, null, null),
                ConversationsV1ConfigAddress.class);
    }

    public ConversationsV1ConfigAddress update(String sid, UpdateConversationsV1ConfigAddressRequest body) {
        Map<String, Object> form = body != null ? body.toForm() : null;
        return decode(transport.request("POST", "/v1/Configuration/Addresses/" + sid, null, form),
                ConversationsV1ConfigAddress.class);
    }

    public void delete(String sid) {
        transport.request("DELETE", "/v1/Configuration/Addresses/" + sid, null, null);
    }
}
