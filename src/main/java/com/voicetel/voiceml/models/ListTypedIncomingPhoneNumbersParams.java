package com.voicetel.voiceml.models;

import java.util.LinkedHashMap;
import java.util.Map;

/** Query params for type-specific {@code /IncomingPhoneNumbers/{Local,Mobile,TollFree}} list endpoints. */
public final class ListTypedIncomingPhoneNumbersParams {

    private final String phoneNumber;
    private final String friendlyName;
    private final Boolean beta;
    private final String origin;
    private final Integer page;
    private final Integer pageSize;
    private final String pageToken;

    private ListTypedIncomingPhoneNumbersParams(Builder b) {
        this.phoneNumber = b.phoneNumber;
        this.friendlyName = b.friendlyName;
        this.beta = b.beta;
        this.origin = b.origin;
        this.page = b.page;
        this.pageSize = b.pageSize;
        this.pageToken = b.pageToken;
    }

    public Map<String, Object> toQuery() {
        Map<String, Object> q = new LinkedHashMap<>();
        if (phoneNumber != null) q.put("PhoneNumber", phoneNumber);
        if (friendlyName != null) q.put("FriendlyName", friendlyName);
        if (beta != null) q.put("Beta", beta);
        if (origin != null) q.put("Origin", origin);
        if (page != null) q.put("Page", page);
        if (pageSize != null) q.put("PageSize", pageSize);
        if (pageToken != null) q.put("PageToken", pageToken);
        return q;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder {
        private String phoneNumber;
        private String friendlyName;
        private Boolean beta;
        private String origin;
        private Integer page;
        private Integer pageSize;
        private String pageToken;

        public Builder phoneNumber(String v) { this.phoneNumber = v; return this; }
        public Builder friendlyName(String v) { this.friendlyName = v; return this; }
        public Builder beta(Boolean v) { this.beta = v; return this; }
        public Builder origin(String v) { this.origin = v; return this; }
        public Builder page(Integer v) { this.page = v; return this; }
        public Builder pageSize(Integer v) { this.pageSize = v; return this; }
        public Builder pageToken(String v) { this.pageToken = v; return this; }

        public ListTypedIncomingPhoneNumbersParams build() {
            return new ListTypedIncomingPhoneNumbersParams(this);
        }
    }
}
