package com.voicetel.voiceml.resources;

import com.voicetel.voiceml.Transport;
import com.voicetel.voiceml.models.AssistantsV1Session;
import com.voicetel.voiceml.models.AssistantsV1SessionList;
import com.voicetel.voiceml.models.ListPageParams;

import java.util.Map;

/** Operations on {@code /v1/Sessions[/{id}]} — read-only list + fetch. */
public final class AssistantsV1SessionsResource extends BaseResource {

    public AssistantsV1SessionsResource(Transport transport) {
        super(transport);
    }

    public AssistantsV1SessionList list(ListPageParams params) {
        Map<String, Object> q = params != null ? params.toQuery() : null;
        return decode(transport.requestJson("GET", "/v1/Sessions", q, null),
                AssistantsV1SessionList.class);
    }

    public AssistantsV1SessionList list() { return list(null); }

    public AssistantsV1Session fetch(String sessionId) {
        return decode(transport.requestJson("GET", "/v1/Sessions/" + sessionId, null, null),
                AssistantsV1Session.class);
    }
}
