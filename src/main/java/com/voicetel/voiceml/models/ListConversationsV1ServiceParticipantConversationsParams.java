package com.voicetel.voiceml.models;

import java.util.LinkedHashMap;
import java.util.Map;

/** Query params for {@code GET /v1/Services/{ChatServiceSid}/ParticipantConversations}. */
public final class ListConversationsV1ServiceParticipantConversationsParams {

    private final String identity;
    private final String address;
    private final Integer pageSize;

    private ListConversationsV1ServiceParticipantConversationsParams(Builder b) {
        this.identity = b.identity;
        this.address = b.address;
        this.pageSize = b.pageSize;
    }

    public Map<String, Object> toQuery() {
        Map<String, Object> q = new LinkedHashMap<>();
        if (identity != null) q.put("Identity", identity);
        if (address != null) q.put("Address", address);
        if (pageSize != null) q.put("PageSize", pageSize);
        return q;
    }

    public static Builder builder() { return new Builder(); }

    public static final class Builder {
        private String identity;
        private String address;
        private Integer pageSize;
        public Builder identity(String s) { this.identity = s; return this; }
        public Builder address(String s) { this.address = s; return this; }
        public Builder pageSize(Integer v) { this.pageSize = v; return this; }
        public ListConversationsV1ServiceParticipantConversationsParams build() {
            return new ListConversationsV1ServiceParticipantConversationsParams(this);
        }
    }
}
