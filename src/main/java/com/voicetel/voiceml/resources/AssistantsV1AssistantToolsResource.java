package com.voicetel.voiceml.resources;

import com.voicetel.voiceml.Transport;
import com.voicetel.voiceml.models.AssistantsV1ToolList;
import com.voicetel.voiceml.models.ListPageParams;

import java.util.Map;

/** Operations on {@code /v1/Assistants/{id}/Tools[/{toolId}]}. */
public final class AssistantsV1AssistantToolsResource extends BaseResource {

    private final String assistantId;

    public AssistantsV1AssistantToolsResource(Transport transport, String assistantId) {
        super(transport);
        this.assistantId = assistantId;
    }

    /** List Tools attached to this Assistant. */
    public AssistantsV1ToolList list(ListPageParams params) {
        Map<String, Object> q = params != null ? params.toQuery() : null;
        return decode(transport.requestJson("GET", "/v1/Assistants/" + assistantId + "/Tools", q, null),
                AssistantsV1ToolList.class);
    }

    public AssistantsV1ToolList list() { return list(null); }

    /** Attach a Tool to this Assistant ({@code POST} returns {@code 204}). */
    public void attach(String toolId) {
        transport.requestJson("POST", "/v1/Assistants/" + assistantId + "/Tools/" + toolId, null, null);
    }

    /** Detach a Tool from this Assistant ({@code DELETE} returns {@code 204}). */
    public void detach(String toolId) {
        transport.requestJson("DELETE", "/v1/Assistants/" + assistantId + "/Tools/" + toolId, null, null);
    }
}
