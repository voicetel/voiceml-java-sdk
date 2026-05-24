package com.voicetel.voiceml.models;

import java.util.LinkedHashMap;
import java.util.Map;

/** Form body for {@code POST /Conferences/{sid}/Participants}. {@code from} and {@code to} are required. */
public final class CreateParticipantRequest {

    private final String from;
    private final String to;
    private final String label;
    private final Boolean muted;
    private final Boolean startConferenceOnEnter;
    private final Boolean endConferenceOnExit;
    private final Integer timeout;
    private final String statusCallback;
    private final String statusCallbackMethod;
    private final String statusCallbackEvent;

    private CreateParticipantRequest(Builder b) {
        this.from = b.from;
        this.to = b.to;
        this.label = b.label;
        this.muted = b.muted;
        this.startConferenceOnEnter = b.startConferenceOnEnter;
        this.endConferenceOnExit = b.endConferenceOnExit;
        this.timeout = b.timeout;
        this.statusCallback = b.statusCallback;
        this.statusCallbackMethod = b.statusCallbackMethod;
        this.statusCallbackEvent = b.statusCallbackEvent;
    }

    public Map<String, Object> toForm() {
        Map<String, Object> form = new LinkedHashMap<>();
        form.put("From", from);
        form.put("To", to);
        if (label != null) form.put("Label", label);
        if (muted != null) form.put("Muted", muted);
        if (startConferenceOnEnter != null) form.put("StartConferenceOnEnter", startConferenceOnEnter);
        if (endConferenceOnExit != null) form.put("EndConferenceOnExit", endConferenceOnExit);
        if (timeout != null) form.put("Timeout", timeout);
        if (statusCallback != null) form.put("StatusCallback", statusCallback);
        if (statusCallbackMethod != null) form.put("StatusCallbackMethod", statusCallbackMethod);
        if (statusCallbackEvent != null) form.put("StatusCallbackEvent", statusCallbackEvent);
        return form;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder {
        private String from;
        private String to;
        private String label;
        private Boolean muted;
        private Boolean startConferenceOnEnter;
        private Boolean endConferenceOnExit;
        private Integer timeout;
        private String statusCallback;
        private String statusCallbackMethod;
        private String statusCallbackEvent;

        public Builder from(String v) { this.from = v; return this; }
        public Builder to(String v) { this.to = v; return this; }
        public Builder label(String v) { this.label = v; return this; }
        public Builder muted(Boolean v) { this.muted = v; return this; }
        public Builder startConferenceOnEnter(Boolean v) { this.startConferenceOnEnter = v; return this; }
        public Builder endConferenceOnExit(Boolean v) { this.endConferenceOnExit = v; return this; }
        public Builder timeout(Integer v) { this.timeout = v; return this; }
        public Builder statusCallback(String v) { this.statusCallback = v; return this; }
        public Builder statusCallbackMethod(String v) { this.statusCallbackMethod = v; return this; }
        public Builder statusCallbackEvent(String v) { this.statusCallbackEvent = v; return this; }

        public CreateParticipantRequest build() {
            return new CreateParticipantRequest(this);
        }
    }
}
