package com.voicetel.voiceml.models;

import java.util.LinkedHashMap;
import java.util.Map;

/** Query parameters for {@code GET /Transcriptions} and {@code GET /Recordings/{RecordingSid}/Transcriptions}. */
public final class ListTranscriptionsParams {

    private final String dateCreated;
    private final String dateCreatedLt;
    private final String dateCreatedGt;
    private final Integer page;
    private final Integer pageSize;
    private final String pageToken;

    private ListTranscriptionsParams(Builder b) {
        this.dateCreated = b.dateCreated;
        this.dateCreatedLt = b.dateCreatedLt;
        this.dateCreatedGt = b.dateCreatedGt;
        this.page = b.page;
        this.pageSize = b.pageSize;
        this.pageToken = b.pageToken;
    }

    /** @return the query parameters, keyed by wire name; unset values are omitted */
    public Map<String, Object> toQuery() {
        Map<String, Object> m = new LinkedHashMap<>();
        if (dateCreated != null) m.put("DateCreated", dateCreated);
        if (dateCreatedLt != null) m.put("DateCreated<", dateCreatedLt);
        if (dateCreatedGt != null) m.put("DateCreated>", dateCreatedGt);
        if (page != null) m.put("Page", page);
        if (pageSize != null) m.put("PageSize", pageSize);
        if (pageToken != null) m.put("PageToken", pageToken);
        return m;
    }

    /** @return the {@code DateCreated} value, or {@code null} when unset */
    public String getDateCreated() {
        return dateCreated;
    }

    /** @return the {@code DateCreated<} value, or {@code null} when unset */
    public String getDateCreatedLt() {
        return dateCreatedLt;
    }

    /** @return the {@code DateCreated>} value, or {@code null} when unset */
    public String getDateCreatedGt() {
        return dateCreatedGt;
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

    /** Builder for {@link ListTranscriptionsParams}. */
    public static final class Builder {
        private String dateCreated;
        private String dateCreatedLt;
        private String dateCreatedGt;
        private Integer page;
        private Integer pageSize;
        private String pageToken;

        /** Create an empty builder; prefer {@link ListTranscriptionsParams#builder()}. */
        public Builder() {
            // all fields start unset
        }

        /**
         * @param v {@code DateCreated}
         * @return this builder
         */
        public Builder dateCreated(String v) {
            this.dateCreated = v;
            return this;
        }

        /**
         * @param v {@code DateCreated<}
         * @return this builder
         */
        public Builder dateCreatedLt(String v) {
            this.dateCreatedLt = v;
            return this;
        }

        /**
         * @param v {@code DateCreated>}
         * @return this builder
         */
        public Builder dateCreatedGt(String v) {
            this.dateCreatedGt = v;
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
        public ListTranscriptionsParams build() {
            return new ListTranscriptionsParams(this);
        }
    }
}
