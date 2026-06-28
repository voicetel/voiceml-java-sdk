package com.voicetel.voiceml.resources;

import com.voicetel.voiceml.Transport;
import com.voicetel.voiceml.models.AssistantsV1PolicyList;
import com.voicetel.voiceml.models.ListAssistantsV1PoliciesParams;

import java.util.Map;

/** {@code GET /v1/Policies} — list-only Policies resource. */
public final class AssistantsV1PoliciesResource extends BaseResource {

    public AssistantsV1PoliciesResource(Transport transport) {
        super(transport);
    }

    public AssistantsV1PolicyList list(ListAssistantsV1PoliciesParams params) {
        Map<String, Object> q = params != null ? params.toQuery() : null;
        return decode(transport.requestJson("GET", "/v1/Policies", q, null),
                AssistantsV1PolicyList.class);
    }

    public AssistantsV1PolicyList list() { return list(null); }
}
