package com.voicetel.voiceml.resources;

import com.voicetel.voiceml.Transport;

/**
 * Per-Knowledge scope. Returned by
 * {@link AssistantsV1Resource#knowledge(String)}, exposes the two read-only
 * Knowledge-scoped sub-resources ({@code /Status}, {@code /Chunks}).
 */
public final class AssistantsV1KnowledgeScope {

    private final AssistantsV1KnowledgeStatusResource status;
    private final AssistantsV1KnowledgeChunksResource chunks;

    public AssistantsV1KnowledgeScope(Transport transport, String knowledgeId) {
        this.status = new AssistantsV1KnowledgeStatusResource(transport, knowledgeId);
        this.chunks = new AssistantsV1KnowledgeChunksResource(transport, knowledgeId);
    }

    public AssistantsV1KnowledgeStatusResource status() { return status; }
    public AssistantsV1KnowledgeChunksResource chunks() { return chunks; }
}
