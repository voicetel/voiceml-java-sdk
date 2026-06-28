package com.voicetel.voiceml.models;

import java.util.LinkedHashMap;
import java.util.Map;

/** JSON body for {@code PUT /v1/Knowledge/{id}}. All fields optional. */
public final class UpdateAssistantsV1KnowledgeRequest {

    private final String name;
    private final String type;
    private final String description;
    private final String embeddingModel;
    private final Map<String, Object> knowledgeSourceDetails;

    private UpdateAssistantsV1KnowledgeRequest(Builder b) {
        this.name = b.name;
        this.type = b.type;
        this.description = b.description;
        this.embeddingModel = b.embeddingModel;
        this.knowledgeSourceDetails = b.knowledgeSourceDetails;
    }

    public Map<String, Object> toJsonBody() {
        Map<String, Object> m = new LinkedHashMap<>();
        if (name != null) m.put("name", name);
        if (type != null) m.put("type", type);
        if (description != null) m.put("description", description);
        if (embeddingModel != null) m.put("embedding_model", embeddingModel);
        if (knowledgeSourceDetails != null) m.put("knowledge_source_details", knowledgeSourceDetails);
        return m;
    }

    public static Builder builder() { return new Builder(); }

    public static final class Builder {
        private String name;
        private String type;
        private String description;
        private String embeddingModel;
        private Map<String, Object> knowledgeSourceDetails;

        public Builder name(String s) { this.name = s; return this; }
        public Builder type(String s) { this.type = s; return this; }
        public Builder description(String s) { this.description = s; return this; }
        public Builder embeddingModel(String s) { this.embeddingModel = s; return this; }
        public Builder knowledgeSourceDetails(Map<String, Object> v) { this.knowledgeSourceDetails = v; return this; }

        public UpdateAssistantsV1KnowledgeRequest build() {
            return new UpdateAssistantsV1KnowledgeRequest(this);
        }
    }
}
