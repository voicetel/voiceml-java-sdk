package com.voicetel.voiceml.resources;

import com.voicetel.voiceml.Transport;
import com.voicetel.voiceml.models.AssistantsV1KnowledgeChunkList;
import com.voicetel.voiceml.models.ListPageParams;

import java.util.Map;

/** {@code GET /v1/Knowledge/{id}/Chunks} — paginated list of chunks. */
public final class AssistantsV1KnowledgeChunksResource extends BaseResource {

    private final String knowledgeId;

    public AssistantsV1KnowledgeChunksResource(Transport transport, String knowledgeId) {
        super(transport);
        this.knowledgeId = knowledgeId;
    }

    public AssistantsV1KnowledgeChunkList list(ListPageParams params) {
        Map<String, Object> q = params != null ? params.toQuery() : null;
        return decode(transport.requestJson("GET",
                "/v1/Knowledge/" + knowledgeId + "/Chunks", q, null),
                AssistantsV1KnowledgeChunkList.class);
    }

    public AssistantsV1KnowledgeChunkList list() { return list(null); }
}
