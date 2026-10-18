package com.voicetel.voiceml.models;

import java.util.LinkedHashMap;
import java.util.Map;

/** Form body for {@code POST /Conferences/{ConferenceSid}/Recordings}. */
public final class StartConferenceRecordingRequest {

    private final String recordingStatusCallback;
    private final String recordingStatusCallbackMethod;
    private final String recordingStatusCallbackEvent;

    private StartConferenceRecordingRequest(Builder b) {
        this.recordingStatusCallback = b.recordingStatusCallback;
        this.recordingStatusCallbackMethod = b.recordingStatusCallbackMethod;
        this.recordingStatusCallbackEvent = b.recordingStatusCallbackEvent;
    }

    /** @return the form fields, keyed by wire name; unset values are omitted */
    public Map<String, Object> toForm() {
        Map<String, Object> m = new LinkedHashMap<>();
        if (recordingStatusCallback != null) m.put("RecordingStatusCallback", recordingStatusCallback);
        if (recordingStatusCallbackMethod != null) m.put("RecordingStatusCallbackMethod", recordingStatusCallbackMethod);
        if (recordingStatusCallbackEvent != null) m.put("RecordingStatusCallbackEvent", recordingStatusCallbackEvent);
        return m;
    }

    /** @return the {@code RecordingStatusCallback} value, or {@code null} when unset */
    public String getRecordingStatusCallback() {
        return recordingStatusCallback;
    }

    /** @return the {@code RecordingStatusCallbackMethod} value, or {@code null} when unset */
    public String getRecordingStatusCallbackMethod() {
        return recordingStatusCallbackMethod;
    }

    /** @return the {@code RecordingStatusCallbackEvent} value, or {@code null} when unset */
    public String getRecordingStatusCallbackEvent() {
        return recordingStatusCallbackEvent;
    }

    /** @return a new builder */
    public static Builder builder() {
        return new Builder();
    }

    /** Builder for {@link StartConferenceRecordingRequest}. */
    public static final class Builder {
        private String recordingStatusCallback;
        private String recordingStatusCallbackMethod;
        private String recordingStatusCallbackEvent;

        /** Create an empty builder; prefer {@link StartConferenceRecordingRequest#builder()}. */
        public Builder() {
            // all fields start unset
        }

        /**
         * @param v {@code RecordingStatusCallback}
         * @return this builder
         */
        public Builder recordingStatusCallback(String v) {
            this.recordingStatusCallback = v;
            return this;
        }

        /**
         * @param v {@code RecordingStatusCallbackMethod}
         * @return this builder
         */
        public Builder recordingStatusCallbackMethod(String v) {
            this.recordingStatusCallbackMethod = v;
            return this;
        }

        /**
         * @param v {@code RecordingStatusCallbackEvent}
         * @return this builder
         */
        public Builder recordingStatusCallbackEvent(String v) {
            this.recordingStatusCallbackEvent = v;
            return this;
        }

        /**
         * @return the request
         */
        public StartConferenceRecordingRequest build() {
            return new StartConferenceRecordingRequest(this);
        }
    }
}
