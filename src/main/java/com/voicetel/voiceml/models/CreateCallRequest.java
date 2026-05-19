package com.voicetel.voiceml.models;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Form body for {@code POST /Calls}.
 *
 * <p>Set at most one of {@code url} / {@code twiml} / {@code applicationSid} — TwiML wins
 * server-side when multiple are set (Twilio's documented precedence).
 *
 * <p>Build via {@link #builder()}. Only fields the caller explicitly sets are emitted on the wire
 * — the form encoder skips {@code null} values.
 */
public final class CreateCallRequest {

    private final String to;
    private final String from;
    private final String url;
    private final String method;
    private final String twiml;
    private final String applicationSid;
    private final String fallbackUrl;
    private final String fallbackMethod;
    private final String statusCallback;
    private final String statusCallbackMethod;
    private final List<String> statusCallbackEvent;
    private final String machineDetection;
    private final Integer machineDetectionTimeout;
    private final Integer machineDetectionSpeechThreshold;
    private final Integer machineDetectionSpeechEndThreshold;
    private final Integer machineDetectionSilenceTimeout;
    private final String asyncAmdStatusCallback;
    private final String asyncAmdStatusCallbackMethod;
    private final Boolean record;
    private final String recordingStatusCallback;
    private final String recordingStatusCallbackMethod;
    private final String recordingStatusCallbackEvent;
    private final String recordingChannels;
    private final String recordingTrack;
    private final String trim;
    private final Integer timeout;
    private final String sendDigits;
    private final String callerId;
    private final String callReason;
    private final String sipAuthUsername;
    private final String sipAuthPassword;
    private final String byoc;
    private final Boolean asyncAmd;
    private final String callToken;

    private CreateCallRequest(Builder b) {
        this.to = b.to;
        this.from = b.from;
        this.url = b.url;
        this.method = b.method;
        this.twiml = b.twiml;
        this.applicationSid = b.applicationSid;
        this.fallbackUrl = b.fallbackUrl;
        this.fallbackMethod = b.fallbackMethod;
        this.statusCallback = b.statusCallback;
        this.statusCallbackMethod = b.statusCallbackMethod;
        this.statusCallbackEvent = b.statusCallbackEvent;
        this.machineDetection = b.machineDetection;
        this.machineDetectionTimeout = b.machineDetectionTimeout;
        this.machineDetectionSpeechThreshold = b.machineDetectionSpeechThreshold;
        this.machineDetectionSpeechEndThreshold = b.machineDetectionSpeechEndThreshold;
        this.machineDetectionSilenceTimeout = b.machineDetectionSilenceTimeout;
        this.asyncAmdStatusCallback = b.asyncAmdStatusCallback;
        this.asyncAmdStatusCallbackMethod = b.asyncAmdStatusCallbackMethod;
        this.record = b.record;
        this.recordingStatusCallback = b.recordingStatusCallback;
        this.recordingStatusCallbackMethod = b.recordingStatusCallbackMethod;
        this.recordingStatusCallbackEvent = b.recordingStatusCallbackEvent;
        this.recordingChannels = b.recordingChannels;
        this.recordingTrack = b.recordingTrack;
        this.trim = b.trim;
        this.timeout = b.timeout;
        this.sendDigits = b.sendDigits;
        this.callerId = b.callerId;
        this.callReason = b.callReason;
        this.sipAuthUsername = b.sipAuthUsername;
        this.sipAuthPassword = b.sipAuthPassword;
        this.byoc = b.byoc;
        this.asyncAmd = b.asyncAmd;
        this.callToken = b.callToken;
    }

    /**
     * Render as a form-encodable map. Only set fields are emitted; the transport handles
     * boolean → {@code "true"}/{@code "false"} conversion and list → repeated keys.
     */
    public Map<String, Object> toForm() {
        Map<String, Object> form = new LinkedHashMap<>();
        if (to != null) form.put("To", to);
        if (from != null) form.put("From", from);
        if (url != null) form.put("Url", url);
        if (method != null) form.put("Method", method);
        if (twiml != null) form.put("Twiml", twiml);
        if (applicationSid != null) form.put("ApplicationSid", applicationSid);
        if (fallbackUrl != null) form.put("FallbackUrl", fallbackUrl);
        if (fallbackMethod != null) form.put("FallbackMethod", fallbackMethod);
        if (statusCallback != null) form.put("StatusCallback", statusCallback);
        if (statusCallbackMethod != null) form.put("StatusCallbackMethod", statusCallbackMethod);
        if (statusCallbackEvent != null) form.put("StatusCallbackEvent", statusCallbackEvent);
        if (machineDetection != null) form.put("MachineDetection", machineDetection);
        if (machineDetectionTimeout != null) form.put("MachineDetectionTimeout", machineDetectionTimeout);
        if (machineDetectionSpeechThreshold != null) form.put("MachineDetectionSpeechThreshold", machineDetectionSpeechThreshold);
        if (machineDetectionSpeechEndThreshold != null) form.put("MachineDetectionSpeechEndThreshold", machineDetectionSpeechEndThreshold);
        if (machineDetectionSilenceTimeout != null) form.put("MachineDetectionSilenceTimeout", machineDetectionSilenceTimeout);
        if (asyncAmdStatusCallback != null) form.put("AsyncAmdStatusCallback", asyncAmdStatusCallback);
        if (asyncAmdStatusCallbackMethod != null) form.put("AsyncAmdStatusCallbackMethod", asyncAmdStatusCallbackMethod);
        if (record != null) form.put("Record", record);
        if (recordingStatusCallback != null) form.put("RecordingStatusCallback", recordingStatusCallback);
        if (recordingStatusCallbackMethod != null) form.put("RecordingStatusCallbackMethod", recordingStatusCallbackMethod);
        if (recordingStatusCallbackEvent != null) form.put("RecordingStatusCallbackEvent", recordingStatusCallbackEvent);
        if (recordingChannels != null) form.put("RecordingChannels", recordingChannels);
        if (recordingTrack != null) form.put("RecordingTrack", recordingTrack);
        if (trim != null) form.put("Trim", trim);
        if (timeout != null) form.put("Timeout", timeout);
        if (sendDigits != null) form.put("SendDigits", sendDigits);
        if (callerId != null) form.put("CallerId", callerId);
        if (callReason != null) form.put("CallReason", callReason);
        if (sipAuthUsername != null) form.put("SipAuthUsername", sipAuthUsername);
        if (sipAuthPassword != null) form.put("SipAuthPassword", sipAuthPassword);
        if (byoc != null) form.put("Byoc", byoc);
        if (asyncAmd != null) form.put("AsyncAmd", asyncAmd);
        if (callToken != null) form.put("CallToken", callToken);
        return form;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder {
        private String to;
        private String from;
        private String url;
        private String method;
        private String twiml;
        private String applicationSid;
        private String fallbackUrl;
        private String fallbackMethod;
        private String statusCallback;
        private String statusCallbackMethod;
        private List<String> statusCallbackEvent;
        private String machineDetection;
        private Integer machineDetectionTimeout;
        private Integer machineDetectionSpeechThreshold;
        private Integer machineDetectionSpeechEndThreshold;
        private Integer machineDetectionSilenceTimeout;
        private String asyncAmdStatusCallback;
        private String asyncAmdStatusCallbackMethod;
        private Boolean record;
        private String recordingStatusCallback;
        private String recordingStatusCallbackMethod;
        private String recordingStatusCallbackEvent;
        private String recordingChannels;
        private String recordingTrack;
        private String trim;
        private Integer timeout;
        private String sendDigits;
        private String callerId;
        private String callReason;
        private String sipAuthUsername;
        private String sipAuthPassword;
        private String byoc;
        private Boolean asyncAmd;
        private String callToken;

        public Builder to(String to) { this.to = to; return this; }
        public Builder from(String from) { this.from = from; return this; }
        public Builder url(String url) { this.url = url; return this; }
        public Builder method(String method) { this.method = method; return this; }
        public Builder twiml(String twiml) { this.twiml = twiml; return this; }
        public Builder applicationSid(String s) { this.applicationSid = s; return this; }
        public Builder fallbackUrl(String s) { this.fallbackUrl = s; return this; }
        public Builder fallbackMethod(String s) { this.fallbackMethod = s; return this; }
        public Builder statusCallback(String s) { this.statusCallback = s; return this; }
        public Builder statusCallbackMethod(String s) { this.statusCallbackMethod = s; return this; }
        public Builder statusCallbackEvent(List<String> v) { this.statusCallbackEvent = v; return this; }
        public Builder machineDetection(String s) { this.machineDetection = s; return this; }
        public Builder machineDetectionTimeout(Integer v) { this.machineDetectionTimeout = v; return this; }
        public Builder machineDetectionSpeechThreshold(Integer v) { this.machineDetectionSpeechThreshold = v; return this; }
        public Builder machineDetectionSpeechEndThreshold(Integer v) { this.machineDetectionSpeechEndThreshold = v; return this; }
        public Builder machineDetectionSilenceTimeout(Integer v) { this.machineDetectionSilenceTimeout = v; return this; }
        public Builder asyncAmdStatusCallback(String s) { this.asyncAmdStatusCallback = s; return this; }
        public Builder asyncAmdStatusCallbackMethod(String s) { this.asyncAmdStatusCallbackMethod = s; return this; }
        public Builder record(Boolean v) { this.record = v; return this; }
        public Builder recordingStatusCallback(String s) { this.recordingStatusCallback = s; return this; }
        public Builder recordingStatusCallbackMethod(String s) { this.recordingStatusCallbackMethod = s; return this; }
        public Builder recordingStatusCallbackEvent(String s) { this.recordingStatusCallbackEvent = s; return this; }
        public Builder recordingChannels(String s) { this.recordingChannels = s; return this; }
        public Builder recordingTrack(String s) { this.recordingTrack = s; return this; }
        public Builder trim(String s) { this.trim = s; return this; }
        public Builder timeout(Integer v) { this.timeout = v; return this; }
        public Builder sendDigits(String s) { this.sendDigits = s; return this; }
        public Builder callerId(String s) { this.callerId = s; return this; }
        public Builder callReason(String s) { this.callReason = s; return this; }
        public Builder sipAuthUsername(String s) { this.sipAuthUsername = s; return this; }
        public Builder sipAuthPassword(String s) { this.sipAuthPassword = s; return this; }
        public Builder byoc(String s) { this.byoc = s; return this; }
        public Builder asyncAmd(Boolean v) { this.asyncAmd = v; return this; }
        public Builder callToken(String s) { this.callToken = s; return this; }

        public CreateCallRequest build() {
            return new CreateCallRequest(this);
        }
    }
}
