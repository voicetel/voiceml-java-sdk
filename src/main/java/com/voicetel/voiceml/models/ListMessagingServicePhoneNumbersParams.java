package com.voicetel.voiceml.models;

import java.util.LinkedHashMap;
import java.util.Map;

/** Query parameters for {@code GET /v1/Services/{Sid}/PhoneNumbers}. */
public final class ListMessagingServicePhoneNumbersParams {

    private final Integer page;
    private final Integer pageSize;

    private ListMessagingServicePhoneNumbersParams(Builder b) {
        this.page = b.page;
        this.pageSize = b.pageSize;
    }

    /** @return the query parameters, keyed by wire name; unset values are omitted */
    public Map<String, Object> toQuery() {
        Map<String, Object> m = new LinkedHashMap<>();
        if (page != null) m.put("Page", page);
        if (pageSize != null) m.put("PageSize", pageSize);
        return m;
    }

    /** @return the {@code Page} value, or {@code null} when unset */
    public Integer getPage() {
        return page;
    }

    /** @return the {@code PageSize} value, or {@code null} when unset */
    public Integer getPageSize() {
        return pageSize;
    }

    /** @return a new builder */
    public static Builder builder() {
        return new Builder();
    }

    /** Builder for {@link ListMessagingServicePhoneNumbersParams}. */
    public static final class Builder {
        private Integer page;
        private Integer pageSize;

        /** Create an empty builder; prefer {@link ListMessagingServicePhoneNumbersParams#builder()}. */
        public Builder() {
            // all fields start unset
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
         * @return the request
         */
        public ListMessagingServicePhoneNumbersParams build() {
            return new ListMessagingServicePhoneNumbersParams(this);
        }
    }
}
