package com.voicetel.voiceml.models;

import java.util.LinkedHashMap;
import java.util.Map;

/** Query-string parameters for {@code GET /Recordings} (account-scoped). */
public final class ListRecordingsParams {

    private final String dateCreated;
    private final String dateCreatedLt;
    private final String dateCreatedGt;
    private final String callSid;
    private final String conferenceSid;
    private final Integer page;
    private final Integer pageSize;

    private ListRecordingsParams(Builder b) {
        this.dateCreated = b.dateCreated;
        this.dateCreatedLt = b.dateCreatedLt;
        this.dateCreatedGt = b.dateCreatedGt;
        this.callSid = b.callSid;
        this.conferenceSid = b.conferenceSid;
        this.page = b.page;
        this.pageSize = b.pageSize;
    }

    public Map<String, Object> toQuery() {
        Map<String, Object> q = new LinkedHashMap<>();
        if (dateCreated != null) q.put("DateCreated", dateCreated);
        if (dateCreatedLt != null) q.put("DateCreated<", dateCreatedLt);
        if (dateCreatedGt != null) q.put("DateCreated>", dateCreatedGt);
        if (callSid != null) q.put("CallSid", callSid);
        if (conferenceSid != null) q.put("ConferenceSid", conferenceSid);
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
        private String callSid;
        private String conferenceSid;
        private Integer page;
        private Integer pageSize;

        public Builder dateCreated(String s) { this.dateCreated = s; return this; }
        public Builder dateCreatedLt(String s) { this.dateCreatedLt = s; return this; }
        public Builder dateCreatedGt(String s) { this.dateCreatedGt = s; return this; }
        public Builder callSid(String s) { this.callSid = s; return this; }
        public Builder conferenceSid(String s) { this.conferenceSid = s; return this; }
        public Builder page(Integer v) { this.page = v; return this; }
        public Builder pageSize(Integer v) { this.pageSize = v; return this; }

        public ListRecordingsParams build() {
            return new ListRecordingsParams(this);
        }
    }
}
