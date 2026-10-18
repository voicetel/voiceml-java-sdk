package com.voicetel.voiceml.models;

import java.util.LinkedHashMap;
import java.util.Map;

/** Form body for {@code POST /Calls/{CallSid}/Recordings}. */
public final class StartRecordingRequest {

    private final Integer recordingMaxDuration;
    private final String recordingChannels;
    private final String recordingTrack;
    private final Boolean playBeep;
    private final String recordingStatusCallback;
    private final String recordingStatusCallbackMethod;
    private final String recordingStatusCallbackEvent;

    private StartRecordingRequest(Builder b) {
        this.recordingMaxDuration = b.recordingMaxDuration;
        this.recordingChannels = b.recordingChannels;
        this.recordingTrack = b.recordingTrack;
        this.playBeep = b.playBeep;
        this.recordingStatusCallback = b.recordingStatusCallback;
        this.recordingStatusCallbackMethod = b.recordingStatusCallbackMethod;
        this.recordingStatusCallbackEvent = b.recordingStatusCallbackEvent;
    }

    /** @return the form fields, keyed by wire name; unset values are omitted */
    public Map<String, Object> toForm() {
        Map<String, Object> m = new LinkedHashMap<>();
        if (recordingMaxDuration != null) m.put("RecordingMaxDuration", recordingMaxDuration);
        if (recordingChannels != null) m.put("RecordingChannels", recordingChannels);
        if (recordingTrack != null) m.put("RecordingTrack", recordingTrack);
        if (playBeep != null) m.put("PlayBeep", playBeep);
        if (recordingStatusCallback != null) m.put("RecordingStatusCallback", recordingStatusCallback);
        if (recordingStatusCallbackMethod != null) m.put("RecordingStatusCallbackMethod", recordingStatusCallbackMethod);
        if (recordingStatusCallbackEvent != null) m.put("RecordingStatusCallbackEvent", recordingStatusCallbackEvent);
        return m;
    }

    /** @return the {@code RecordingMaxDuration} value, or {@code null} when unset */
    public Integer getRecordingMaxDuration() {
        return recordingMaxDuration;
    }

    /** @return the {@code RecordingChannels} value, or {@code null} when unset */
    public String getRecordingChannels() {
        return recordingChannels;
    }

    /** @return the {@code RecordingTrack} value, or {@code null} when unset */
    public String getRecordingTrack() {
        return recordingTrack;
    }

    /** @return the {@code PlayBeep} value, or {@code null} when unset */
    public Boolean getPlayBeep() {
        return playBeep;
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

    /** Builder for {@link StartRecordingRequest}. */
    public static final class Builder {
        private Integer recordingMaxDuration;
        private String recordingChannels;
        private String recordingTrack;
        private Boolean playBeep;
        private String recordingStatusCallback;
        private String recordingStatusCallbackMethod;
        private String recordingStatusCallbackEvent;

        /** Create an empty builder; prefer {@link StartRecordingRequest#builder()}. */
        public Builder() {
            // all fields start unset
        }

        /**
         * @param v {@code RecordingMaxDuration}
         * @return this builder
         */
        public Builder recordingMaxDuration(Integer v) {
            this.recordingMaxDuration = v;
            return this;
        }

        /**
         * @param v {@code RecordingChannels}: one of {@code mono}, {@code dual}
         * @return this builder
         */
        public Builder recordingChannels(String v) {
            this.recordingChannels = v;
            return this;
        }

        /**
         * @param v {@code RecordingTrack}: one of {@code inbound}, {@code outbound}, {@code both}
         * @return this builder
         */
        public Builder recordingTrack(String v) {
            this.recordingTrack = v;
            return this;
        }

        /**
         * @param v {@code PlayBeep}
         * @return this builder
         */
        public Builder playBeep(Boolean v) {
            this.playBeep = v;
            return this;
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
        public StartRecordingRequest build() {
            return new StartRecordingRequest(this);
        }
    }
}
