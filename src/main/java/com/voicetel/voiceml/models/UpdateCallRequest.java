package com.voicetel.voiceml.models;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Form body for {@code POST /Calls/{sid}}.
 *
 * <p>Three flows on the same endpoint (mirrors Twilio):
 * <ul>
 *   <li>{@code status="completed"|"canceled"} — terminate the call. Wins over any TwiML source.</li>
 *   <li>{@code twiml=<inline>} — execute inline TwiML on the live call (wins over {@code url}).</li>
 *   <li>{@code url=…} — fetch new TwiML and execute it on the live call.</li>
 * </ul>
 * StatusCallback fields apply independently — including on the terminate path.
 */
public final class UpdateCallRequest {

    private final String status;
    private final String twiml;
    private final String url;
    private final String method;
    private final String fallbackUrl;
    private final String fallbackMethod;
    private final String statusCallback;
    private final String statusCallbackMethod;
    private final List<String> statusCallbackEvent;

    private UpdateCallRequest(Builder b) {
        this.status = b.status;
        this.twiml = b.twiml;
        this.url = b.url;
        this.method = b.method;
        this.fallbackUrl = b.fallbackUrl;
        this.fallbackMethod = b.fallbackMethod;
        this.statusCallback = b.statusCallback;
        this.statusCallbackMethod = b.statusCallbackMethod;
        this.statusCallbackEvent = b.statusCallbackEvent;
    }

    public Map<String, Object> toForm() {
        Map<String, Object> form = new LinkedHashMap<>();
        if (status != null) form.put("Status", status);
        if (twiml != null) form.put("Twiml", twiml);
        if (url != null) form.put("Url", url);
        if (method != null) form.put("Method", method);
        if (fallbackUrl != null) form.put("FallbackUrl", fallbackUrl);
        if (fallbackMethod != null) form.put("FallbackMethod", fallbackMethod);
        if (statusCallback != null) form.put("StatusCallback", statusCallback);
        if (statusCallbackMethod != null) form.put("StatusCallbackMethod", statusCallbackMethod);
        if (statusCallbackEvent != null) form.put("StatusCallbackEvent", statusCallbackEvent);
        return form;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder {
        private String status;
        private String twiml;
        private String url;
        private String method;
        private String fallbackUrl;
        private String fallbackMethod;
        private String statusCallback;
        private String statusCallbackMethod;
        private List<String> statusCallbackEvent;

        public Builder status(String s) { this.status = s; return this; }
        public Builder twiml(String s) { this.twiml = s; return this; }
        public Builder url(String s) { this.url = s; return this; }
        public Builder method(String s) { this.method = s; return this; }
        public Builder fallbackUrl(String s) { this.fallbackUrl = s; return this; }
        public Builder fallbackMethod(String s) { this.fallbackMethod = s; return this; }
        public Builder statusCallback(String s) { this.statusCallback = s; return this; }
        public Builder statusCallbackMethod(String s) { this.statusCallbackMethod = s; return this; }
        public Builder statusCallbackEvent(List<String> v) { this.statusCallbackEvent = v; return this; }

        public UpdateCallRequest build() {
            return new UpdateCallRequest(this);
        }
    }
}
