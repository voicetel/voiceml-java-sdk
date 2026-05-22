package com.voicetel.voiceml.models;

import java.util.LinkedHashMap;
import java.util.Map;

/** Query-string parameters for {@code GET /Conferences}. */
public final class ListConferencesParams {

    private final String friendlyName;
    private final String status;
    private final Integer page;
    private final Integer pageSize;
    private final String pageToken;

    private ListConferencesParams(Builder b) {
        this.friendlyName = b.friendlyName;
        this.status = b.status;
        this.page = b.page;
        this.pageSize = b.pageSize;
        this.pageToken = b.pageToken;
    }

    public Map<String, Object> toQuery() {
        Map<String, Object> q = new LinkedHashMap<>();
        if (friendlyName != null) q.put("FriendlyName", friendlyName);
        if (status != null) q.put("Status", status);
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
        private Integer page;
        private Integer pageSize;
        private String pageToken;

        public Builder friendlyName(String s) { this.friendlyName = s; return this; }
        public Builder status(String s) { this.status = s; return this; }
        public Builder page(Integer v) { this.page = v; return this; }
        public Builder pageSize(Integer v) { this.pageSize = v; return this; }
        public Builder pageToken(String s) { this.pageToken = s; return this; }

        public ListConferencesParams build() {
            return new ListConferencesParams(this);
        }
    }
}
