package com.voicetel.voiceml.models;

import java.util.LinkedHashMap;
import java.util.Map;

/** Query parameters for {@code GET /v1/Policies}. */
public final class ListAssistantsV1PoliciesParams {

    private final String toolId;
    private final String knowledgeId;
    private final Integer pageSize;

    private ListAssistantsV1PoliciesParams(Builder b) {
        this.toolId = b.toolId;
        this.knowledgeId = b.knowledgeId;
        this.pageSize = b.pageSize;
    }

    public Map<String, Object> toQuery() {
        Map<String, Object> q = new LinkedHashMap<>();
        if (toolId != null) q.put("ToolId", toolId);
        if (knowledgeId != null) q.put("KnowledgeId", knowledgeId);
        if (pageSize != null) q.put("PageSize", pageSize);
        return q;
    }

    public static Builder builder() { return new Builder(); }

    public static final class Builder {
        private String toolId;
        private String knowledgeId;
        private Integer pageSize;

        public Builder toolId(String s) { this.toolId = s; return this; }
        public Builder knowledgeId(String s) { this.knowledgeId = s; return this; }
        public Builder pageSize(Integer v) { this.pageSize = v; return this; }

        public ListAssistantsV1PoliciesParams build() {
            return new ListAssistantsV1PoliciesParams(this);
        }
    }
}
