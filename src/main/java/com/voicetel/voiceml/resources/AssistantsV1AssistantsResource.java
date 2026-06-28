package com.voicetel.voiceml.resources;

import com.voicetel.voiceml.Transport;
import com.voicetel.voiceml.models.AssistantsV1Assistant;
import com.voicetel.voiceml.models.AssistantsV1AssistantList;
import com.voicetel.voiceml.models.AssistantsV1AssistantWithToolsAndKnowledge;
import com.voicetel.voiceml.models.CreateAssistantsV1AssistantRequest;
import com.voicetel.voiceml.models.ListPageParams;
import com.voicetel.voiceml.models.UpdateAssistantsV1AssistantRequest;

import java.util.Map;

/** Operations on {@code /v1/Assistants[/{id}]}. JSON request bodies. */
public final class AssistantsV1AssistantsResource extends BaseResource {

    public AssistantsV1AssistantsResource(Transport transport) {
        super(transport);
    }

    public AssistantsV1Assistant create(CreateAssistantsV1AssistantRequest body) {
        Map<String, Object> json = body != null ? body.toJsonBody() : null;
        return decode(transport.requestJson("POST", "/v1/Assistants", null, json),
                AssistantsV1Assistant.class);
    }

    public AssistantsV1AssistantList list(ListPageParams params) {
        Map<String, Object> q = params != null ? params.toQuery() : null;
        return decode(transport.requestJson("GET", "/v1/Assistants", q, null),
                AssistantsV1AssistantList.class);
    }

    public AssistantsV1AssistantList list() { return list(null); }

    /** Returns the Assistant inlined with its attached {@code tools} and {@code knowledge}. */
    public AssistantsV1AssistantWithToolsAndKnowledge fetch(String assistantId) {
        return decode(transport.requestJson("GET", "/v1/Assistants/" + assistantId, null, null),
                AssistantsV1AssistantWithToolsAndKnowledge.class);
    }

    public AssistantsV1Assistant update(String assistantId, UpdateAssistantsV1AssistantRequest body) {
        Map<String, Object> json = body != null ? body.toJsonBody() : null;
        return decode(transport.requestJson("PUT", "/v1/Assistants/" + assistantId, null, json),
                AssistantsV1Assistant.class);
    }

    public void delete(String assistantId) {
        transport.requestJson("DELETE", "/v1/Assistants/" + assistantId, null, null);
    }
}
