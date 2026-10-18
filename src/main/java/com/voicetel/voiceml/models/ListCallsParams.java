package com.voicetel.voiceml.models;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Query parameters for {@code GET /Calls}.
 *
 * <p>The wire names {@code StartTime<} / {@code StartTime>} (and the {@code EndTime} pair) cannot be
 * Java identifiers, so the builder methods are {@code startTimeLt} / {@code startTimeGt};
 * {@link #toQuery()} sends the wire names.
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
        this.page = b.page;
        this.pageSize = b.pageSize;
        this.pageToken = b.pageToken;
    }

    /** @return the query parameters, keyed by wire name; unset values are omitted */
    public Map<String, Object> toQuery() {
        Map<String, Object> m = new LinkedHashMap<>();
        if (to != null) m.put("To", to);
        if (from != null) m.put("From", from);
        if (status != null) m.put("Status", status);
        if (parentCallSid != null) m.put("ParentCallSid", parentCallSid);
        if (startTime != null) m.put("StartTime", startTime);
        if (startTimeLt != null) m.put("StartTime<", startTimeLt);
        if (startTimeGt != null) m.put("StartTime>", startTimeGt);
        if (endTime != null) m.put("EndTime", endTime);
        if (endTimeLt != null) m.put("EndTime<", endTimeLt);
        if (endTimeGt != null) m.put("EndTime>", endTimeGt);
        if (page != null) m.put("Page", page);
        if (pageSize != null) m.put("PageSize", pageSize);
        if (pageToken != null) m.put("PageToken", pageToken);
        return m;
    }

    /** @return the {@code To} value, or {@code null} when unset */
    public String getTo() {
        return to;
    }

    /** @return the {@code From} value, or {@code null} when unset */
    public String getFrom() {
        return from;
    }

    /** @return the {@code Status} value, or {@code null} when unset */
    public String getStatus() {
        return status;
    }

    /** @return the {@code ParentCallSid} value, or {@code null} when unset */
    public String getParentCallSid() {
        return parentCallSid;
    }

    /** @return the {@code StartTime} value, or {@code null} when unset */
    public String getStartTime() {
        return startTime;
    }

    /** @return the {@code StartTime<} value, or {@code null} when unset */
    public String getStartTimeLt() {
        return startTimeLt;
    }

    /** @return the {@code StartTime>} value, or {@code null} when unset */
    public String getStartTimeGt() {
        return startTimeGt;
    }

    /** @return the {@code EndTime} value, or {@code null} when unset */
    public String getEndTime() {
        return endTime;
    }

    /** @return the {@code EndTime<} value, or {@code null} when unset */
    public String getEndTimeLt() {
        return endTimeLt;
    }

    /** @return the {@code EndTime>} value, or {@code null} when unset */
    public String getEndTimeGt() {
        return endTimeGt;
    }

    /** @return the {@code Page} value, or {@code null} when unset */
    public Integer getPage() {
        return page;
    }

    /** @return the {@code PageSize} value, or {@code null} when unset */
    public Integer getPageSize() {
        return pageSize;
    }

    /** @return the {@code PageToken} value, or {@code null} when unset */
    public String getPageToken() {
        return pageToken;
    }

    /** @return a new builder */
    public static Builder builder() {
        return new Builder();
    }

    /** Builder for {@link ListCallsParams}. */
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
        private Integer page;
        private Integer pageSize;
        private String pageToken;

        /** Create an empty builder; prefer {@link ListCallsParams#builder()}. */
        public Builder() {
            // all fields start unset
        }

        /**
         * @param v {@code To}
         * @return this builder
         */
        public Builder to(String v) {
            this.to = v;
            return this;
        }

        /**
         * @param v {@code From}
         * @return this builder
         */
        public Builder from(String v) {
            this.from = v;
            return this;
        }

        /**
         * @param v {@code Status}
         * @return this builder
         */
        public Builder status(String v) {
            this.status = v;
            return this;
        }

        /**
         * @param v {@code ParentCallSid}
         * @return this builder
         */
        public Builder parentCallSid(String v) {
            this.parentCallSid = v;
            return this;
        }

        /**
         * @param v {@code StartTime}
         * @return this builder
         */
        public Builder startTime(String v) {
            this.startTime = v;
            return this;
        }

        /**
         * @param v {@code StartTime<}
         * @return this builder
         */
        public Builder startTimeLt(String v) {
            this.startTimeLt = v;
            return this;
        }

        /**
         * @param v {@code StartTime>}
         * @return this builder
         */
        public Builder startTimeGt(String v) {
            this.startTimeGt = v;
            return this;
        }

        /**
         * @param v {@code EndTime}
         * @return this builder
         */
        public Builder endTime(String v) {
            this.endTime = v;
            return this;
        }

        /**
         * @param v {@code EndTime<}
         * @return this builder
         */
        public Builder endTimeLt(String v) {
            this.endTimeLt = v;
            return this;
        }

        /**
         * @param v {@code EndTime>}
         * @return this builder
         */
        public Builder endTimeGt(String v) {
            this.endTimeGt = v;
            return this;
        }

        /**
         * @param v {@code Page}
         * @return this builder
         */
        public Builder page(Integer v) {
            this.page = v;
            return this;
        }

        /**
         * @param v {@code PageSize}
         * @return this builder
         */
        public Builder pageSize(Integer v) {
            this.pageSize = v;
            return this;
        }

        /**
         * @param v {@code PageToken}
         * @return this builder
         */
        public Builder pageToken(String v) {
            this.pageToken = v;
            return this;
        }

        /**
         * @return the request
         */
        public ListCallsParams build() {
            return new ListCallsParams(this);
        }
    }
}
