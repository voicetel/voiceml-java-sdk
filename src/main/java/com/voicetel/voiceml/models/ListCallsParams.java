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
    private final String startTime;
    private final String startTimeLt;
    private final String startTimeGt;
    private final String endTime;
    private final String endTimeLt;
    private final String endTimeGt;
    private final String startTimeGte;
    private final String startTimeLte;
    private final Integer page;
    private final Integer pageSize;
    private final String pageToken;

    private ListCallsParams(Builder b) {
        this.to = b.to;
        this.from = b.from;
        this.status = b.status;
        this.parentCallSid = b.parentCallSid;
        this.startTime = b.startTime;
        this.startTimeLt = b.startTimeLt;
        this.startTimeGt = b.startTimeGt;
        this.endTime = b.endTime;
        this.endTimeLt = b.endTimeLt;
        this.endTimeGt = b.endTimeGt;
        this.startTimeGte = b.startTimeGte;
        this.startTimeLte = b.startTimeLte;
        this.page = b.page;
        this.pageSize = b.pageSize;
        this.pageToken = b.pageToken;
    }

    public Map<String, Object> toQuery() {
        Map<String, Object> q = new LinkedHashMap<>();
        if (to != null) q.put("To", to);
        if (from != null) q.put("From", from);
        if (status != null) q.put("Status", status);
        if (parentCallSid != null) q.put("ParentCallSid", parentCallSid);
        if (startTime != null) q.put("StartTime", startTime);
        if (startTimeLt != null) q.put("StartTime<", startTimeLt);
        if (startTimeGt != null) q.put("StartTime>", startTimeGt);
        if (endTime != null) q.put("EndTime", endTime);
        if (endTimeLt != null) q.put("EndTime<", endTimeLt);
        if (endTimeGt != null) q.put("EndTime>", endTimeGt);
        if (startTimeGte != null) q.put("StartTime>=", startTimeGte);
        if (startTimeLte != null) q.put("StartTime<=", startTimeLte);
        if (page != null) q.put("Page", page);
        if (pageSize != null) q.put("PageSize", pageSize);
        if (pageToken != null) q.put("PageToken", pageToken);
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
        private String startTime;
        private String startTimeLt;
        private String startTimeGt;
        private String endTime;
        private String endTimeLt;
        private String endTimeGt;
        private String startTimeGte;
        private String startTimeLte;
        private Integer page;
        private Integer pageSize;
        private String pageToken;

        public Builder to(String s) { this.to = s; return this; }
        public Builder from(String s) { this.from = s; return this; }
        public Builder status(String s) { this.status = s; return this; }
        public Builder parentCallSid(String s) { this.parentCallSid = s; return this; }
        public Builder startTime(String s) { this.startTime = s; return this; }
        public Builder startTimeLt(String s) { this.startTimeLt = s; return this; }
        public Builder startTimeGt(String s) { this.startTimeGt = s; return this; }
        public Builder endTime(String s) { this.endTime = s; return this; }
        public Builder endTimeLt(String s) { this.endTimeLt = s; return this; }
        public Builder endTimeGt(String s) { this.endTimeGt = s; return this; }
        /** Inclusive lower bound — wire name {@code StartTime>=}. */
        public Builder startTimeGte(String s) { this.startTimeGte = s; return this; }
        /** Inclusive upper bound — wire name {@code StartTime<=}. */
        public Builder startTimeLte(String s) { this.startTimeLte = s; return this; }
        public Builder page(Integer v) { this.page = v; return this; }
        public Builder pageSize(Integer v) { this.pageSize = v; return this; }
        public Builder pageToken(String s) { this.pageToken = s; return this; }

        public ListCallsParams build() {
            return new ListCallsParams(this);
        }
    }
}
