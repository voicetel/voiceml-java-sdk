package com.voicetel.voiceml.resources;

import com.voicetel.voiceml.Transport;
import com.voicetel.voiceml.models.AssistantsV1Knowledge;
import com.voicetel.voiceml.models.AssistantsV1KnowledgeList;
import com.voicetel.voiceml.models.CreateAssistantsV1KnowledgeRequest;
import com.voicetel.voiceml.models.ListAssistantsV1KnowledgeParams;
import com.voicetel.voiceml.models.UpdateAssistantsV1KnowledgeRequest;

import java.util.Map;

/** Operations on {@code /v1/Knowledge[/{id}]}. JSON request bodies. */
public final class AssistantsV1KnowledgeResource extends BaseResource {

    public AssistantsV1KnowledgeResource(Transport transport) {
        super(transport);
    }

    public AssistantsV1Knowledge create(CreateAssistantsV1KnowledgeRequest body) {
        Map<String, Object> json = body != null ? body.toJsonBody() : null;
        return decode(transport.requestJson("POST", "/v1/Knowledge", null, json),
                AssistantsV1Knowledge.class);
    }

    public AssistantsV1KnowledgeList list(ListAssistantsV1KnowledgeParams params) {
        Map<String, Object> q = params != null ? params.toQuery() : null;
        return decode(transport.requestJson("GET", "/v1/Knowledge", q, null),
                AssistantsV1KnowledgeList.class);
    }

    public AssistantsV1KnowledgeList list() { return list(null); }

    public AssistantsV1Knowledge fetch(String knowledgeId) {
        return decode(transport.requestJson("GET", "/v1/Knowledge/" + knowledgeId, null, null),
                AssistantsV1Knowledge.class);
    }

    public AssistantsV1Knowledge update(String knowledgeId, UpdateAssistantsV1KnowledgeRequest body) {
        Map<String, Object> json = body != null ? body.toJsonBody() : null;
        return decode(transport.requestJson("PUT", "/v1/Knowledge/" + knowledgeId, null, json),
                AssistantsV1Knowledge.class);
    }

    public void delete(String knowledgeId) {
        transport.requestJson("DELETE", "/v1/Knowledge/" + knowledgeId, null, null);
    }
}
