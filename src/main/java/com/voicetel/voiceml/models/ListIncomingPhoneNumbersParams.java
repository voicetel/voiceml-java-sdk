package com.voicetel.voiceml.models;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Query-string parameters for {@code GET /IncomingPhoneNumbers}.
 *
 * <p>Twilio's canonical sid-from-E.164 lookup is
 * {@code GET /IncomingPhoneNumbers?PhoneNumber=+1...} returning a 0-or-1-row envelope; supply
 * {@link Builder#phoneNumber(String)} to drive it.
 */
public final class ListIncomingPhoneNumbersParams {

    private final String phoneNumber;
    private final Integer page;
    private final Integer pageSize;

    private ListIncomingPhoneNumbersParams(Builder b) {
        this.phoneNumber = b.phoneNumber;
        this.page = b.page;
        this.pageSize = b.pageSize;
    }

    public Map<String, Object> toQuery() {
        Map<String, Object> q = new LinkedHashMap<>();
        if (phoneNumber != null) q.put("PhoneNumber", phoneNumber);
        if (page != null) q.put("Page", page);
        if (pageSize != null) q.put("PageSize", pageSize);
        return q;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder {
        private String phoneNumber;
        private Integer page;
        private Integer pageSize;

        public Builder phoneNumber(String s) { this.phoneNumber = s; return this; }
        public Builder page(Integer v) { this.page = v; return this; }
        public Builder pageSize(Integer v) { this.pageSize = v; return this; }

        public ListIncomingPhoneNumbersParams build() {
            return new ListIncomingPhoneNumbersParams(this);
        }
    }
}
