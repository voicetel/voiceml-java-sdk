package com.voicetel.voiceml.models;

import java.time.LocalDate;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Query parameters for {@code GET /v1/Alerts}. The list serves a single page whose {@code PageSize}
 * (default 50, capped at 1000) limits the alerts returned; there is no continuation link.
 */
public final class ListMonitorAlertsParams {

    private final String logLevel;
    private final String startDate;
    private final String endDate;
    private final Integer pageSize;

    private ListMonitorAlertsParams(Builder b) {
        this.logLevel = b.logLevel;
        this.startDate = b.startDate;
        this.endDate = b.endDate;
        this.pageSize = b.pageSize;
    }

    /** @return {@code LogLevel}, {@code StartDate}, {@code EndDate} and {@code PageSize}, when set */
    public Map<String, Object> toQuery() {
        Map<String, Object> q = new LinkedHashMap<>();
        if (logLevel != null) q.put("LogLevel", logLevel);
        if (startDate != null) q.put("StartDate", startDate);
        if (endDate != null) q.put("EndDate", endDate);
        if (pageSize != null) q.put("PageSize", pageSize);
        return q;
    }

    /** @return the {@code LogLevel} value, or {@code null} */
    public String getLogLevel() {
        return logLevel;
    }

    /** @return the {@code StartDate} value, or {@code null} */
    public String getStartDate() {
        return startDate;
    }

    /** @return the {@code EndDate} value, or {@code null} */
    public String getEndDate() {
        return endDate;
    }

    /** @return the {@code PageSize} value, or {@code null} */
    public Integer getPageSize() {
        return pageSize;
    }

    /** @return a new builder */
    public static Builder builder() {
        return new Builder();
    }

    /** Builder for {@link ListMonitorAlertsParams}. */
    public static final class Builder {
        private String logLevel;
        private String startDate;
        private String endDate;
        private Integer pageSize;

        /** Create an empty builder; prefer {@link ListMonitorAlertsParams#builder()}. */
        public Builder() {
            // all fields start unset
        }

        /**
         * @param v {@code LogLevel}: {@code error} or {@code warning}
         * @return this builder
         */
        public Builder logLevel(String v) { this.logLevel = v; return this; }

        /**
         * @param v {@code StartDate}: {@code YYYY-MM-DD} or a full ISO-8601 timestamp
         * @return this builder
         */
        public Builder startDate(String v) { this.startDate = v; return this; }

        /**
         * @param v {@code StartDate} as a date ({@code YYYY-MM-DD})
         * @return this builder
         */
        public Builder startDate(LocalDate v) { this.startDate = v == null ? null : v.toString(); return this; }

        /**
         * @param v {@code EndDate}: {@code YYYY-MM-DD} or a full ISO-8601 timestamp
         * @return this builder
         */
        public Builder endDate(String v) { this.endDate = v; return this; }

        /**
         * @param v {@code EndDate} as a date ({@code YYYY-MM-DD})
         * @return this builder
         */
        public Builder endDate(LocalDate v) { this.endDate = v == null ? null : v.toString(); return this; }

        /**
         * @param v {@code PageSize} (1-1000; the alerts page is limited by it)
         * @return this builder
         */
        public Builder pageSize(Integer v) { this.pageSize = v; return this; }

        /** @return the parameters */
        public ListMonitorAlertsParams build() {
            return new ListMonitorAlertsParams(this);
        }
    }
}
