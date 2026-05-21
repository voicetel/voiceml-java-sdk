package com.voicetel.voiceml.models;

import java.util.LinkedHashMap;
import java.util.Map;

/** Shared Page / PageSize query params for stub list endpoints. */
public final class ListPageParams {

    private final Integer page;
    private final Integer pageSize;

    private ListPageParams(Builder b) {
        this.page = b.page;
        this.pageSize = b.pageSize;
    }

    public Map<String, Object> toQuery() {
        Map<String, Object> q = new LinkedHashMap<>();
        if (page != null) q.put("Page", page);
        if (pageSize != null) q.put("PageSize", pageSize);
        return q;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder {
        private Integer page;
        private Integer pageSize;

        public Builder page(Integer v) { this.page = v; return this; }
        public Builder pageSize(Integer v) { this.pageSize = v; return this; }

        public ListPageParams build() {
            return new ListPageParams(this);
        }
    }
}
