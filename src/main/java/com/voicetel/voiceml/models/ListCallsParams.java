package com.voicetel.voiceml.models;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Query-string parameters for {@code GET /Calls}.
 *
 * <p>The Twilio wire names {@code StartTime>=} and {@code StartTime<=} are the literal query keys
 * the server expects. In Java those characters can't be field identifiers, so the field names here
 * are {@code startTimeGte} / {@code startTimeLte}; {@link #toQuery()} translates back to the wire
 * shape.
 */
public final class ListCallsParams {

    private final String to;
    private final String from;
    private final String status;
    private final String parentCallSid;
    private final String startTimeGte;
    private final String startTimeLte;
    private final Integer page;
    private final Integer pageSize;

    private ListCallsParams(Builder b) {
        this.to = b.to;
        this.from = b.from;
        this.status = b.status;
        this.parentCallSid = b.parentCallSid;
        this.startTimeGte = b.startTimeGte;
        this.startTimeLte = b.startTimeLte;
        this.page = b.page;
        this.pageSize = b.pageSize;
    }

    public Map<String, Object> toQuery() {
        Map<String, Object> q = new LinkedHashMap<>();
        if (to != null) q.put("To", to);
        if (from != null) q.put("From", from);
        if (status != null) q.put("Status", status);
        if (parentCallSid != null) q.put("ParentCallSid", parentCallSid);
        if (startTimeGte != null) q.put("StartTime>=", startTimeGte);
        if (startTimeLte != null) q.put("StartTime<=", startTimeLte);
        if (page != null) q.put("Page", page);
        if (pageSize != null) q.put("PageSize", pageSize);
        return q;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder {
        private String to;
        private String from;
        private String status;
        private String parentCallSid;
        private String startTimeGte;
        private String startTimeLte;
        private Integer page;
        private Integer pageSize;

        public Builder to(String s) { this.to = s; return this; }
        public Builder from(String s) { this.from = s; return this; }
        public Builder status(String s) { this.status = s; return this; }
        public Builder parentCallSid(String s) { this.parentCallSid = s; return this; }
        public Builder startTimeGte(String s) { this.startTimeGte = s; return this; }
        public Builder startTimeLte(String s) { this.startTimeLte = s; return this; }
        public Builder page(Integer v) { this.page = v; return this; }
        public Builder pageSize(Integer v) { this.pageSize = v; return this; }

        public ListCallsParams build() {
            return new ListCallsParams(this);
        }
    }
}
