package com.voicetel.voiceml.models;

import java.util.LinkedHashMap;
import java.util.Map;

/** Query-string parameters for {@code GET /Conferences}. */
public final class ListConferencesParams {

    private final String friendlyName;
    private final String status;
    private final String dateCreated;
    private final String dateCreatedLt;
    private final String dateCreatedGt;
    private final String dateUpdated;
    private final String dateUpdatedLt;
    private final String dateUpdatedGt;
    private final Integer page;
    private final Integer pageSize;
    private final String pageToken;

    private ListConferencesParams(Builder b) {
        this.friendlyName = b.friendlyName;
        this.status = b.status;
        this.dateCreated = b.dateCreated;
        this.dateCreatedLt = b.dateCreatedLt;
        this.dateCreatedGt = b.dateCreatedGt;
        this.dateUpdated = b.dateUpdated;
        this.dateUpdatedLt = b.dateUpdatedLt;
        this.dateUpdatedGt = b.dateUpdatedGt;
        this.page = b.page;
        this.pageSize = b.pageSize;
        this.pageToken = b.pageToken;
    }

    public Map<String, Object> toQuery() {
        Map<String, Object> q = new LinkedHashMap<>();
        if (friendlyName != null) q.put("FriendlyName", friendlyName);
        if (status != null) q.put("Status", status);
        if (dateCreated != null) q.put("DateCreated", dateCreated);
        if (dateCreatedLt != null) q.put("DateCreated<", dateCreatedLt);
        if (dateCreatedGt != null) q.put("DateCreated>", dateCreatedGt);
        if (dateUpdated != null) q.put("DateUpdated", dateUpdated);
        if (dateUpdatedLt != null) q.put("DateUpdated<", dateUpdatedLt);
        if (dateUpdatedGt != null) q.put("DateUpdated>", dateUpdatedGt);
        if (page != null) q.put("Page", page);
        if (pageSize != null) q.put("PageSize", pageSize);
        if (pageToken != null) q.put("PageToken", pageToken);
        return q;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder {
        private String friendlyName;
        private String status;
        private String dateCreated;
        private String dateCreatedLt;
        private String dateCreatedGt;
        private String dateUpdated;
        private String dateUpdatedLt;
        private String dateUpdatedGt;
        private Integer page;
        private Integer pageSize;
        private String pageToken;

        public Builder friendlyName(String s) { this.friendlyName = s; return this; }
        public Builder status(String s) { this.status = s; return this; }
        public Builder dateCreated(String s) { this.dateCreated = s; return this; }
        public Builder dateCreatedLt(String s) { this.dateCreatedLt = s; return this; }
        public Builder dateCreatedGt(String s) { this.dateCreatedGt = s; return this; }
        public Builder dateUpdated(String s) { this.dateUpdated = s; return this; }
        public Builder dateUpdatedLt(String s) { this.dateUpdatedLt = s; return this; }
        public Builder dateUpdatedGt(String s) { this.dateUpdatedGt = s; return this; }
        public Builder page(Integer v) { this.page = v; return this; }
        public Builder pageSize(Integer v) { this.pageSize = v; return this; }
        public Builder pageToken(String s) { this.pageToken = s; return this; }

        public ListConferencesParams build() {
            return new ListConferencesParams(this);
        }
    }
}
