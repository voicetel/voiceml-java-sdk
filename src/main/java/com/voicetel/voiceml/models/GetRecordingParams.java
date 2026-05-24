package com.voicetel.voiceml.models;

import java.util.LinkedHashMap;
import java.util.Map;

/** Optional query params for {@code GET /Recordings/{sid}. */
public final class GetRecordingParams {

    private final Boolean includeSoftDeleted;

    private GetRecordingParams(Builder b) {
        this.includeSoftDeleted = b.includeSoftDeleted;
    }

    public Map<String, Object> toQuery() {
        Map<String, Object> q = new LinkedHashMap<>();
        if (includeSoftDeleted != null) q.put("IncludeSoftDeleted", includeSoftDeleted);
        return q;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder {
        private Boolean includeSoftDeleted;

        public Builder includeSoftDeleted(Boolean v) { this.includeSoftDeleted = v; return this; }

        public GetRecordingParams build() {
            return new GetRecordingParams(this);
        }
    }
}
