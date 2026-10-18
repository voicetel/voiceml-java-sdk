package com.voicetel.voiceml.models;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Form body for {@code POST /Calls/{CallSid}/Recordings/{Sid}}: stop, pause or resume, with an
 * optional {@code PauseBehavior} ({@code skip} or {@code silence}).
 */
public final class UpdateCallRecordingRequest {

    private final String status;
    private final String pauseBehavior;

    private UpdateCallRecordingRequest(Builder b) {
        this.status = b.status;
        this.pauseBehavior = b.pauseBehavior;
    }

    /** @return the form fields, keyed by wire name; unset values are omitted */
    public Map<String, Object> toForm() {
        Map<String, Object> m = new LinkedHashMap<>();
        if (status != null) m.put("Status", status);
        if (pauseBehavior != null) m.put("PauseBehavior", pauseBehavior);
        return m;
    }

    /** @return the {@code Status} value, or {@code null} when unset */
    public String getStatus() {
        return status;
    }

    /** @return the {@code PauseBehavior} value, or {@code null} when unset */
    public String getPauseBehavior() {
        return pauseBehavior;
    }

    /** @return a new builder */
    public static Builder builder() {
        return new Builder();
    }

    /** Builder for {@link UpdateCallRecordingRequest}. */
    public static final class Builder {
        private String status;
        private String pauseBehavior;

        /** Create an empty builder; prefer {@link UpdateCallRecordingRequest#builder()}. */
        public Builder() {
            // all fields start unset
        }

        /**
         * @param v {@code Status} (required): one of {@code stopped}, {@code paused}, {@code in-progress}
         * @return this builder
         */
        public Builder status(String v) {
            this.status = v;
            return this;
        }

        /**
         * @param v {@code PauseBehavior}
         * @return this builder
         */
        public Builder pauseBehavior(String v) {
            this.pauseBehavior = v;
            return this;
        }

        /**
         * @return the request
         */
        public UpdateCallRecordingRequest build() {
            return new UpdateCallRecordingRequest(this);
        }
    }
}
