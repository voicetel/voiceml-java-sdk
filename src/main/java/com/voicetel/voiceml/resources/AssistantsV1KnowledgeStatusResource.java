package com.voicetel.voiceml.resources;

import com.voicetel.voiceml.Transport;
import com.voicetel.voiceml.models.AssistantsV1KnowledgeStatus;

/** {@code GET /v1/Knowledge/{id}/Status} — read-only ingestion status snapshot. */
public final class AssistantsV1KnowledgeStatusResource extends BaseResource {

    private final String knowledgeId;

    public AssistantsV1KnowledgeStatusResource(Transport transport, String knowledgeId) {
        super(transport);
        this.knowledgeId = knowledgeId;
    }

    public AssistantsV1KnowledgeStatus fetch() {
        return decode(transport.requestJson("GET",
                "/v1/Knowledge/" + knowledgeId + "/Status", null, null),
                AssistantsV1KnowledgeStatus.class);
    }
}
