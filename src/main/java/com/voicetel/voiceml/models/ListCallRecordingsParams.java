package com.voicetel.voiceml.models;

import java.util.LinkedHashMap;
import java.util.Map;

/** Query-string parameters for {@code GET /Calls/{sid}/Recordings}. */
public final class ListCallRecordingsParams {

    private final String dateCreated;
    private final String dateCreatedLt;
    private final String dateCreatedGt;
    private final Integer page;
    private final Integer pageSize;

    private ListCallRecordingsParams(Builder b) {
        this.dateCreated = b.dateCreated;
        this.dateCreatedLt = b.dateCreatedLt;
        this.dateCreatedGt = b.dateCreatedGt;
        this.page = b.page;
        this.pageSize = b.pageSize;
    }

    public Map<String, Object> toQuery() {
        Map<String, Object> q = new LinkedHashMap<>();
        if (dateCreated != null) q.put("DateCreated", dateCreated);
        if (dateCreatedLt != null) q.put("DateCreated<", dateCreatedLt);
        if (dateCreatedGt != null) q.put("DateCreated>", dateCreatedGt);
        if (page != null) q.put("Page", page);
        if (pageSize != null) q.put("PageSize", pageSize);
        return q;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder {
        private String dateCreated;
        private String dateCreatedLt;
        private String dateCreatedGt;
        private Integer page;
        private Integer pageSize;

        public Builder dateCreated(String s) { this.dateCreated = s; return this; }
        public Builder dateCreatedLt(String s) { this.dateCreatedLt = s; return this; }
        public Builder dateCreatedGt(String s) { this.dateCreatedGt = s; return this; }
        public Builder page(Integer v) { this.page = v; return this; }
        public Builder pageSize(Integer v) { this.pageSize = v; return this; }

        public ListCallRecordingsParams build() {
            return new ListCallRecordingsParams(this);
        }
    }
}
