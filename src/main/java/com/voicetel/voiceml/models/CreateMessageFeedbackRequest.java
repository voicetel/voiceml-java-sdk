package com.voicetel.voiceml.models;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Form body for {@code POST …/Messages/{Sid}/Feedback.json}. Only the properties you set are sent;
 * an omitted {@code Outcome} is reported as {@code unconfirmed}.
 */
public final class CreateMessageFeedbackRequest {

    private final String outcome;

    private CreateMessageFeedbackRequest(Builder b) {
        this.outcome = b.outcome;
    }

    /** @return the form properties, keyed by wire name; unset values are omitted */
    public Map<String, Object> toForm() {
        Map<String, Object> m = new LinkedHashMap<>();
        if (outcome != null) m.put("Outcome", outcome);
        return m;
    }

    /** @return the {@code Outcome} value, or {@code null} when unset */
    public String getOutcome() {
        return outcome;
    }

    /** @return a new builder */
    public static Builder builder() {
        return new Builder();
    }

    /** Builder for {@link CreateMessageFeedbackRequest}. */
    public static final class Builder {
        private String outcome;

        /** Create an empty builder; prefer {@link CreateMessageFeedbackRequest#builder()}. */
        public Builder() {
            // all fields start unset
        }

        /**
         * @param v {@code Outcome}: {@code confirmed} or {@code unconfirmed}; any other value is
         *     reported as {@code unconfirmed}
         * @return this builder
         */
        public Builder outcome(String v) {
            this.outcome = v;
            return this;
        }

        /** @return the request */
        public CreateMessageFeedbackRequest build() {
            return new CreateMessageFeedbackRequest(this);
        }
    }
}
