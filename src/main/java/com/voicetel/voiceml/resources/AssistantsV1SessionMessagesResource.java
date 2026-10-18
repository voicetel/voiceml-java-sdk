package com.voicetel.voiceml.resources;

import com.voicetel.voiceml.Transport;
import com.voicetel.voiceml.models.AssistantsV1MessageList;
import com.voicetel.voiceml.models.ListPageParams;

import java.util.Map;

/** {@code GET /v1/Sessions/{id}/Messages} — list a session's messages. */
public final class AssistantsV1SessionMessagesResource extends BaseResource {

    private final String sessionId;

    public AssistantsV1SessionMessagesResource(Transport transport, String sessionId) {
        super(transport);
        this.sessionId = sessionId;
    }

    public AssistantsV1MessageList list(ListPageParams params) {
        Map<String, Object> q = params != null ? params.toPageSizeQuery() : null;
        return decode(transport.requestJson("GET",
                "/v1/Sessions/" + seg(sessionId) + "/Messages", q, null),
                AssistantsV1MessageList.class);
    }

    public AssistantsV1MessageList list() { return list(null); }
}
