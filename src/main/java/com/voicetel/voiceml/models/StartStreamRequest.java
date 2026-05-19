package com.voicetel.voiceml.models;

import java.util.LinkedHashMap;
import java.util.Map;

/** Form body for {@code POST /Calls/{sid}/Streams}. The {@code url} field is the {@code wss://} endpoint. */
public final class StartStreamRequest {

    private final String url;
    private final String track;
    private final String name;
    private final String statusCallback;
    private final String statusCallbackMethod;

    private StartStreamRequest(Builder b) {
        this.url = b.url;
        this.track = b.track;
        this.name = b.name;
        this.statusCallback = b.statusCallback;
        this.statusCallbackMethod = b.statusCallbackMethod;
    }

    public Map<String, Object> toForm() {
        Map<String, Object> form = new LinkedHashMap<>();
        if (url != null) form.put("Url", url);
        if (track != null) form.put("Track", track);
        if (name != null) form.put("Name", name);
        if (statusCallback != null) form.put("StatusCallback", statusCallback);
        if (statusCallbackMethod != null) form.put("StatusCallbackMethod", statusCallbackMethod);
        return form;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder {
        private String url;
        private String track;
        private String name;
        private String statusCallback;
        private String statusCallbackMethod;

        public Builder url(String s) { this.url = s; return this; }
        public Builder track(String s) { this.track = s; return this; }
        public Builder name(String s) { this.name = s; return this; }
        public Builder statusCallback(String s) { this.statusCallback = s; return this; }
        public Builder statusCallbackMethod(String s) { this.statusCallbackMethod = s; return this; }

        public StartStreamRequest build() {
            return new StartStreamRequest(this);
        }
    }
}
