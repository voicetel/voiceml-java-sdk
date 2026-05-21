package com.voicetel.voiceml.models;

import java.util.LinkedHashMap;
import java.util.Map;

/** Query-string parameters for {@code GET /Conferences/{sid}/Participants}. */
public final class ListParticipantsParams {

    private final Boolean muted;
    private final Boolean hold;
    private final Boolean coaching;
    private final Integer page;
    private final Integer pageSize;

    private ListParticipantsParams(Builder b) {
        this.muted = b.muted;
        this.hold = b.hold;
        this.coaching = b.coaching;
        this.page = b.page;
        this.pageSize = b.pageSize;
    }

    public Map<String, Object> toQuery() {
        Map<String, Object> q = new LinkedHashMap<>();
        if (muted != null) q.put("Muted", muted);
        if (hold != null) q.put("Hold", hold);
        if (coaching != null) q.put("Coaching", coaching);
        if (page != null) q.put("Page", page);
        if (pageSize != null) q.put("PageSize", pageSize);
        return q;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder {
        private Boolean muted;
        private Boolean hold;
        private Boolean coaching;
        private Integer page;
        private Integer pageSize;

        public Builder muted(Boolean v) { this.muted = v; return this; }
        public Builder hold(Boolean v) { this.hold = v; return this; }
        public Builder coaching(Boolean v) { this.coaching = v; return this; }
        public Builder page(Integer v) { this.page = v; return this; }
        public Builder pageSize(Integer v) { this.pageSize = v; return this; }

        public ListParticipantsParams build() {
            return new ListParticipantsParams(this);
        }
    }
}
