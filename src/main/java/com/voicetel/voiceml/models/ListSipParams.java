package com.voicetel.voiceml.models;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Query parameters for the SIP lists that accept a {@code FriendlyName} filter: Domains,
 * CredentialLists, IpAccessControlLists and IpAddresses.
 */
public final class ListSipParams {

    private final String friendlyName;
    private final Integer page;
    private final Integer pageSize;
    private final String pageToken;

    private ListSipParams(Builder b) {
        this.friendlyName = b.friendlyName;
        this.page = b.page;
        this.pageSize = b.pageSize;
        this.pageToken = b.pageToken;
    }

    /** @return the query parameters, keyed by wire name; unset values are omitted */
    public Map<String, Object> toQuery() {
        Map<String, Object> m = new LinkedHashMap<>();
        if (friendlyName != null) m.put("FriendlyName", friendlyName);
        if (page != null) m.put("Page", page);
        if (pageSize != null) m.put("PageSize", pageSize);
        if (pageToken != null) m.put("PageToken", pageToken);
        return m;
    }

    /** @return the {@code FriendlyName} value, or {@code null} when unset */
    public String getFriendlyName() {
        return friendlyName;
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

    /** Builder for {@link ListSipParams}. */
    public static final class Builder {
        private String friendlyName;
        private Integer page;
        private Integer pageSize;
        private String pageToken;

        /** Create an empty builder; prefer {@link ListSipParams#builder()}. */
        public Builder() {
            // all fields start unset
        }

        /**
         * @param v {@code FriendlyName}
         * @return this builder
         */
        public Builder friendlyName(String v) {
            this.friendlyName = v;
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
        public ListSipParams build() {
            return new ListSipParams(this);
        }
    }
}
