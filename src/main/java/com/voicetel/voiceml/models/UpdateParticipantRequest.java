package com.voicetel.voiceml.models;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Form body for {@code POST /Conferences/{sid}/Participants/{CallSid}}.
 * At least one of {@code muted} / {@code hold} must be set.
 */
public final class UpdateParticipantRequest {

    private final Boolean muted;
    private final Boolean hold;

    private UpdateParticipantRequest(Builder b) {
        this.muted = b.muted;
        this.hold = b.hold;
    }

    public Map<String, Object> toForm() {
        Map<String, Object> form = new LinkedHashMap<>();
        if (muted != null) form.put("Muted", muted);
        if (hold != null) form.put("Hold", hold);
        return form;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder {
        private Boolean muted;
        private Boolean hold;

        public Builder muted(Boolean v) { this.muted = v; return this; }
        public Builder hold(Boolean v) { this.hold = v; return this; }

        public UpdateParticipantRequest build() {
            return new UpdateParticipantRequest(this);
        }
    }
}
