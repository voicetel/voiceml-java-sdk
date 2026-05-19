package com.voicetel.voiceml.models;

import java.util.LinkedHashMap;
import java.util.Map;

/** Form body for {@code POST /Calls/{sid}/Siprec}. All fields optional. */
public final class StartSiprecRequest {

    private final String name;
    private final String connectorName;
    private final String track;
    private final String statusCallback;
    private final String statusCallbackMethod;

    private StartSiprecRequest(Builder b) {
        this.name = b.name;
        this.connectorName = b.connectorName;
        this.track = b.track;
        this.statusCallback = b.statusCallback;
        this.statusCallbackMethod = b.statusCallbackMethod;
    }

    public Map<String, Object> toForm() {
        Map<String, Object> form = new LinkedHashMap<>();
        if (name != null) form.put("Name", name);
        if (connectorName != null) form.put("ConnectorName", connectorName);
        if (track != null) form.put("Track", track);
        if (statusCallback != null) form.put("StatusCallback", statusCallback);
        if (statusCallbackMethod != null) form.put("StatusCallbackMethod", statusCallbackMethod);
        return form;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder {
        private String name;
        private String connectorName;
        private String track;
        private String statusCallback;
        private String statusCallbackMethod;

        public Builder name(String s) { this.name = s; return this; }
        public Builder connectorName(String s) { this.connectorName = s; return this; }
        public Builder track(String s) { this.track = s; return this; }
        public Builder statusCallback(String s) { this.statusCallback = s; return this; }
        public Builder statusCallbackMethod(String s) { this.statusCallbackMethod = s; return this; }

        public StartSiprecRequest build() {
            return new StartSiprecRequest(this);
        }
    }
}
