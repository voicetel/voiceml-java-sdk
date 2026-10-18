package com.voicetel.voiceml.models;

import java.time.LocalDate;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Query parameters for {@code GET /Usage/Records} and its time windows. {@code StartDate} and
 * {@code EndDate} are dates ({@code YYYY-MM-DD}).
 */
public final class ListUsageRecordsParams {

    private final String category;
    private final String startDate;
    private final String endDate;
    private final Boolean includeSubaccounts;
    private final Integer page;
    private final Integer pageSize;
    private final String pageToken;

    private ListUsageRecordsParams(Builder b) {
        this.category = b.category;
        this.startDate = b.startDate;
        this.endDate = b.endDate;
        this.includeSubaccounts = b.includeSubaccounts;
        this.page = b.page;
        this.pageSize = b.pageSize;
        this.pageToken = b.pageToken;
    }

    /** @return the query parameters, keyed by wire name; unset values are omitted */
    public Map<String, Object> toQuery() {
        Map<String, Object> m = new LinkedHashMap<>();
        if (category != null) m.put("Category", category);
        if (startDate != null) m.put("StartDate", startDate);
        if (endDate != null) m.put("EndDate", endDate);
        if (includeSubaccounts != null) m.put("IncludeSubaccounts", includeSubaccounts);
        if (page != null) m.put("Page", page);
        if (pageSize != null) m.put("PageSize", pageSize);
        if (pageToken != null) m.put("PageToken", pageToken);
        return m;
    }

    /** @return the {@code Category} value, or {@code null} when unset */
    public String getCategory() {
        return category;
    }

    /** @return the {@code StartDate} value, or {@code null} when unset */
    public String getStartDate() {
        return startDate;
    }

    /** @return the {@code EndDate} value, or {@code null} when unset */
    public String getEndDate() {
        return endDate;
    }

    /** @return the {@code IncludeSubaccounts} value, or {@code null} when unset */
    public Boolean getIncludeSubaccounts() {
        return includeSubaccounts;
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

    /** Builder for {@link ListUsageRecordsParams}. */
    public static final class Builder {
        private String category;
        private String startDate;
        private String endDate;
        private Boolean includeSubaccounts;
        private Integer page;
        private Integer pageSize;
        private String pageToken;

        /** Create an empty builder; prefer {@link ListUsageRecordsParams#builder()}. */
        public Builder() {
            // all fields start unset
        }

        /**
         * @param v {@code Category}
         * @return this builder
         */
        public Builder category(String v) {
            this.category = v;
            return this;
        }

        /**
         * @param v {@code StartDate}
         * @return this builder
         */
        public Builder startDate(String v) {
            this.startDate = v;
            return this;
        }

        /**
         * @param v {@code StartDate} as a date ({@code YYYY-MM-DD} on the wire)
         * @return this builder
         */
        public Builder startDate(LocalDate v) {
            this.startDate = v == null ? null : v.toString();
            return this;
        }

        /**
         * @param v {@code EndDate}
         * @return this builder
         */
        public Builder endDate(String v) {
            this.endDate = v;
            return this;
        }

        /**
         * @param v {@code EndDate} as a date ({@code YYYY-MM-DD} on the wire)
         * @return this builder
         */
        public Builder endDate(LocalDate v) {
            this.endDate = v == null ? null : v.toString();
            return this;
        }

        /**
         * @param v {@code IncludeSubaccounts}
         * @return this builder
         */
        public Builder includeSubaccounts(Boolean v) {
            this.includeSubaccounts = v;
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
        public ListUsageRecordsParams build() {
            return new ListUsageRecordsParams(this);
        }
    }
}
