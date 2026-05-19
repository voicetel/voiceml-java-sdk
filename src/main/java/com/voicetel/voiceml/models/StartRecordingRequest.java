package com.voicetel.voiceml.models;

import java.util.LinkedHashMap;
import java.util.Map;

/** Form body for {@code POST /Calls/{sid}/Recordings}. */
public final class StartRecordingRequest {

    private final Integer recordingMaxDuration;
    private final String recordingChannels;
    private final Boolean playBeep;
    private final String recordingStatusCallback;
    private final String recordingStatusCallbackMethod;
    private final String recordingStatusCallbackEvent;

    private StartRecordingRequest(Builder b) {
        this.recordingMaxDuration = b.recordingMaxDuration;
        this.recordingChannels = b.recordingChannels;
        this.playBeep = b.playBeep;
        this.recordingStatusCallback = b.recordingStatusCallback;
        this.recordingStatusCallbackMethod = b.recordingStatusCallbackMethod;
        this.recordingStatusCallbackEvent = b.recordingStatusCallbackEvent;
    }

    public Map<String, Object> toForm() {
        Map<String, Object> form = new LinkedHashMap<>();
        if (recordingMaxDuration != null) form.put("RecordingMaxDuration", recordingMaxDuration);
        if (recordingChannels != null) form.put("RecordingChannels", recordingChannels);
        if (playBeep != null) form.put("PlayBeep", playBeep);
        if (recordingStatusCallback != null) form.put("RecordingStatusCallback", recordingStatusCallback);
        if (recordingStatusCallbackMethod != null) form.put("RecordingStatusCallbackMethod", recordingStatusCallbackMethod);
        if (recordingStatusCallbackEvent != null) form.put("RecordingStatusCallbackEvent", recordingStatusCallbackEvent);
        return form;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder {
        private Integer recordingMaxDuration;
        private String recordingChannels;
        private Boolean playBeep;
        private String recordingStatusCallback;
        private String recordingStatusCallbackMethod;
        private String recordingStatusCallbackEvent;

        public Builder recordingMaxDuration(Integer v) { this.recordingMaxDuration = v; return this; }
        public Builder recordingChannels(String s) { this.recordingChannels = s; return this; }
        public Builder playBeep(Boolean v) { this.playBeep = v; return this; }
        public Builder recordingStatusCallback(String s) { this.recordingStatusCallback = s; return this; }
        public Builder recordingStatusCallbackMethod(String s) { this.recordingStatusCallbackMethod = s; return this; }
        public Builder recordingStatusCallbackEvent(String s) { this.recordingStatusCallbackEvent = s; return this; }

        public StartRecordingRequest build() {
            return new StartRecordingRequest(this);
        }
    }
}
