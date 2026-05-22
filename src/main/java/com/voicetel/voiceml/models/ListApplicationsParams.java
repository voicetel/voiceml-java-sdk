package com.voicetel.voiceml.models;

import java.util.LinkedHashMap;
import java.util.Map;

/** Query-string parameters for {@code GET /Applications}. */
public final class ListApplicationsParams {

    private final String friendlyName;
    private final Integer page;
    private final Integer pageSize;
    private final String pageToken;

    private ListApplicationsParams(Builder b) {
        this.friendlyName = b.friendlyName;
        this.page = b.page;
        this.pageSize = b.pageSize;
        this.pageToken = b.pageToken;
    }

    public Map<String, Object> toQuery() {
        Map<String, Object> q = new LinkedHashMap<>();
        if (friendlyName != null) q.put("FriendlyName", friendlyName);
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
        private Integer page;
        private Integer pageSize;
        private String pageToken;

        public Builder friendlyName(String s) { this.friendlyName = s; return this; }
        public Builder page(Integer v) { this.page = v; return this; }
        public Builder pageSize(Integer v) { this.pageSize = v; return this; }
        public Builder pageToken(String s) { this.pageToken = s; return this; }

        public ListApplicationsParams build() {
            return new ListApplicationsParams(this);
        }
    }
}
