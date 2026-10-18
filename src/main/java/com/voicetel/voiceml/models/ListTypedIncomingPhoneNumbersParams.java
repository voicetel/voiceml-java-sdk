package com.voicetel.voiceml.models;

import java.util.LinkedHashMap;
import java.util.Map;

/** Query parameters for {@code GET /IncomingPhoneNumbers/Local}, {@code /Mobile} and {@code /TollFree}. */
public final class ListTypedIncomingPhoneNumbersParams {

    private final Integer page;
    private final Integer pageSize;
    private final String pageToken;
    private final String phoneNumber;
    private final String friendlyName;
    private final Boolean beta;
    private final String origin;

    private ListTypedIncomingPhoneNumbersParams(Builder b) {
        this.page = b.page;
        this.pageSize = b.pageSize;
        this.pageToken = b.pageToken;
        this.phoneNumber = b.phoneNumber;
        this.friendlyName = b.friendlyName;
        this.beta = b.beta;
        this.origin = b.origin;
    }

    /** @return the query parameters, keyed by wire name; unset values are omitted */
    public Map<String, Object> toQuery() {
        Map<String, Object> m = new LinkedHashMap<>();
        if (page != null) m.put("Page", page);
        if (pageSize != null) m.put("PageSize", pageSize);
        if (pageToken != null) m.put("PageToken", pageToken);
        if (phoneNumber != null) m.put("PhoneNumber", phoneNumber);
        if (friendlyName != null) m.put("FriendlyName", friendlyName);
        if (beta != null) m.put("Beta", beta);
        if (origin != null) m.put("Origin", origin);
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

    /** @return the {@code PageToken} value, or {@code null} when unset */
    public String getPageToken() {
        return pageToken;
    }

    /** @return the {@code PhoneNumber} value, or {@code null} when unset */
    public String getPhoneNumber() {
        return phoneNumber;
    }

    /** @return the {@code FriendlyName} value, or {@code null} when unset */
    public String getFriendlyName() {
        return friendlyName;
    }

    /** @return the {@code Beta} value, or {@code null} when unset */
    public Boolean getBeta() {
        return beta;
    }

    /** @return the {@code Origin} value, or {@code null} when unset */
    public String getOrigin() {
        return origin;
    }

    /** @return a new builder */
    public static Builder builder() {
        return new Builder();
    }

    /** Builder for {@link ListTypedIncomingPhoneNumbersParams}. */
    public static final class Builder {
        private Integer page;
        private Integer pageSize;
        private String pageToken;
        private String phoneNumber;
        private String friendlyName;
        private Boolean beta;
        private String origin;

        /** Create an empty builder; prefer {@link ListTypedIncomingPhoneNumbersParams#builder()}. */
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
         * @param v {@code PageToken}
         * @return this builder
         */
        public Builder pageToken(String v) {
            this.pageToken = v;
            return this;
        }

        /**
         * @param v {@code PhoneNumber}
         * @return this builder
         */
        public Builder phoneNumber(String v) {
            this.phoneNumber = v;
            return this;
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
         * @param v {@code Beta}
         * @return this builder
         */
        public Builder beta(Boolean v) {
            this.beta = v;
            return this;
        }

        /**
         * @param v {@code Origin}: one of {@code twilio}, {@code hosted}
         * @return this builder
         */
        public Builder origin(String v) {
            this.origin = v;
            return this;
        }

        /**
         * @return the request
         */
        public ListTypedIncomingPhoneNumbersParams build() {
            return new ListTypedIncomingPhoneNumbersParams(this);
        }
    }
}
