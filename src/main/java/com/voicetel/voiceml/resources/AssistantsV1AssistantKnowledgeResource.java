package com.voicetel.voiceml.resources;

import com.voicetel.voiceml.Transport;
import com.voicetel.voiceml.models.AssistantsV1KnowledgeList;
import com.voicetel.voiceml.models.ListPageParams;

import java.util.Map;

/** Operations on {@code /v1/Assistants/{id}/Knowledge[/{knowledgeId}]}. */
public final class AssistantsV1AssistantKnowledgeResource extends BaseResource {

    private final String assistantId;

    public AssistantsV1AssistantKnowledgeResource(Transport transport, String assistantId) {
        super(transport);
        this.assistantId = assistantId;
    }

    /** List Knowledge attached to this Assistant. */
    public AssistantsV1KnowledgeList list(ListPageParams params) {
        Map<String, Object> q = params != null ? params.toQuery() : null;
        return decode(transport.requestJson("GET", "/v1/Assistants/" + assistantId + "/Knowledge", q, null),
                AssistantsV1KnowledgeList.class);
    }

    public AssistantsV1KnowledgeList list() { return list(null); }

    /** Attach Knowledge to this Assistant ({@code POST} returns {@code 204}). */
    public void attach(String knowledgeId) {
        transport.requestJson("POST",
                "/v1/Assistants/" + assistantId + "/Knowledge/" + knowledgeId, null, null);
    }

    /** Detach Knowledge from this Assistant ({@code DELETE} returns {@code 204}). */
    public void detach(String knowledgeId) {
        transport.requestJson("DELETE",
                "/v1/Assistants/" + assistantId + "/Knowledge/" + knowledgeId, null, null);
    }
}
