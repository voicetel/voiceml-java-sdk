package com.voicetel.voiceml.models;

import java.util.LinkedHashMap;
import java.util.Map;

/** Query params for {@code GET /v1/Services/{ChatServiceSid}/Bindings}. */
public final class ListConversationsV1ServiceBindingsParams {

    private final String bindingType;
    private final String identity;
    private final Integer pageSize;

    private ListConversationsV1ServiceBindingsParams(Builder b) {
        this.bindingType = b.bindingType;
        this.identity = b.identity;
        this.pageSize = b.pageSize;
    }

    public Map<String, Object> toQuery() {
        Map<String, Object> q = new LinkedHashMap<>();
        if (bindingType != null) q.put("BindingType", bindingType);
        if (identity != null) q.put("Identity", identity);
        if (pageSize != null) q.put("PageSize", pageSize);
        return q;
    }

    public static Builder builder() { return new Builder(); }

    public static final class Builder {
        private String bindingType;
        private String identity;
        private Integer pageSize;
        public Builder bindingType(String s) { this.bindingType = s; return this; }
        public Builder identity(String s) { this.identity = s; return this; }
        public Builder pageSize(Integer v) { this.pageSize = v; return this; }
        public ListConversationsV1ServiceBindingsParams build() {
            return new ListConversationsV1ServiceBindingsParams(this);
        }
    }
}
