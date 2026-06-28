package com.voicetel.voiceml.resources;

import com.voicetel.voiceml.Transport;
import com.voicetel.voiceml.models.AssistantsV1Tool;
import com.voicetel.voiceml.models.AssistantsV1ToolList;
import com.voicetel.voiceml.models.AssistantsV1ToolWithPolicies;
import com.voicetel.voiceml.models.CreateAssistantsV1ToolRequest;
import com.voicetel.voiceml.models.ListAssistantsV1ToolsParams;
import com.voicetel.voiceml.models.UpdateAssistantsV1ToolRequest;

import java.util.Map;

/** Operations on {@code /v1/Tools[/{id}]}. JSON request bodies. */
public final class AssistantsV1ToolsResource extends BaseResource {

    public AssistantsV1ToolsResource(Transport transport) {
        super(transport);
    }

    public AssistantsV1Tool create(CreateAssistantsV1ToolRequest body) {
        Map<String, Object> json = body != null ? body.toJsonBody() : null;
        return decode(transport.requestJson("POST", "/v1/Tools", null, json),
                AssistantsV1Tool.class);
    }

    public AssistantsV1ToolList list(ListAssistantsV1ToolsParams params) {
        Map<String, Object> q = params != null ? params.toQuery() : null;
        return decode(transport.requestJson("GET", "/v1/Tools", q, null),
                AssistantsV1ToolList.class);
    }

    public AssistantsV1ToolList list() { return list(null); }

    /** Returns the Tool inlined with its attached {@code policies}. */
    public AssistantsV1ToolWithPolicies fetch(String toolId) {
        return decode(transport.requestJson("GET", "/v1/Tools/" + toolId, null, null),
                AssistantsV1ToolWithPolicies.class);
    }

    public AssistantsV1Tool update(String toolId, UpdateAssistantsV1ToolRequest body) {
        Map<String, Object> json = body != null ? body.toJsonBody() : null;
        return decode(transport.requestJson("PUT", "/v1/Tools/" + toolId, null, json),
                AssistantsV1Tool.class);
    }

    public void delete(String toolId) {
        transport.requestJson("DELETE", "/v1/Tools/" + toolId, null, null);
    }
}
