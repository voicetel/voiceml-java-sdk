package com.voicetel.voiceml.models;

import java.util.LinkedHashMap;
import java.util.Map;

/** Query parameters for {@code GET /v1/Knowledge}. */
public final class ListAssistantsV1KnowledgeParams {

    private final String assistantId;
    private final Integer pageSize;

    private ListAssistantsV1KnowledgeParams(Builder b) {
        this.assistantId = b.assistantId;
        this.pageSize = b.pageSize;
    }

    public Map<String, Object> toQuery() {
        Map<String, Object> q = new LinkedHashMap<>();
        if (assistantId != null) q.put("AssistantId", assistantId);
        if (pageSize != null) q.put("PageSize", pageSize);
        return q;
    }

    public static Builder builder() { return new Builder(); }

    public static final class Builder {
        private String assistantId;
        private Integer pageSize;

        public Builder assistantId(String s) { this.assistantId = s; return this; }
        public Builder pageSize(Integer v) { this.pageSize = v; return this; }

        public ListAssistantsV1KnowledgeParams build() {
            return new ListAssistantsV1KnowledgeParams(this);
        }
    }
}
