package com.voicetel.voiceml.models;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Shared form body for {@code POST /Applications} (create) and {@code POST /Applications/{sid}}
 * (update). All fields optional — updates are partial.
 */
public final class ApplicationBody {

    private final String friendlyName;
    private final String voiceUrl;
    private final String voiceMethod;
    private final String voiceFallbackUrl;
    private final String voiceFallbackMethod;
    private final Boolean voiceCallerIdLookup;
    private final String statusCallback;
    private final String statusCallbackMethod;
    private final String statusCallbackEvent;

    private ApplicationBody(Builder b) {
        this.friendlyName = b.friendlyName;
        this.voiceUrl = b.voiceUrl;
        this.voiceMethod = b.voiceMethod;
        this.voiceFallbackUrl = b.voiceFallbackUrl;
        this.voiceFallbackMethod = b.voiceFallbackMethod;
        this.voiceCallerIdLookup = b.voiceCallerIdLookup;
        this.statusCallback = b.statusCallback;
        this.statusCallbackMethod = b.statusCallbackMethod;
        this.statusCallbackEvent = b.statusCallbackEvent;
    }

    public Map<String, Object> toForm() {
        Map<String, Object> form = new LinkedHashMap<>();
        if (friendlyName != null) form.put("FriendlyName", friendlyName);
        if (voiceUrl != null) form.put("VoiceUrl", voiceUrl);
        if (voiceMethod != null) form.put("VoiceMethod", voiceMethod);
        if (voiceFallbackUrl != null) form.put("VoiceFallbackUrl", voiceFallbackUrl);
        if (voiceFallbackMethod != null) form.put("VoiceFallbackMethod", voiceFallbackMethod);
        if (voiceCallerIdLookup != null) form.put("VoiceCallerIdLookup", voiceCallerIdLookup);
        if (statusCallback != null) form.put("StatusCallback", statusCallback);
        if (statusCallbackMethod != null) form.put("StatusCallbackMethod", statusCallbackMethod);
        if (statusCallbackEvent != null) form.put("StatusCallbackEvent", statusCallbackEvent);
        return form;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder {
        private String friendlyName;
        private String voiceUrl;
        private String voiceMethod;
        private String voiceFallbackUrl;
        private String voiceFallbackMethod;
        private Boolean voiceCallerIdLookup;
        private String statusCallback;
        private String statusCallbackMethod;
        private String statusCallbackEvent;

        public Builder friendlyName(String s) { this.friendlyName = s; return this; }
        public Builder voiceUrl(String s) { this.voiceUrl = s; return this; }
        public Builder voiceMethod(String s) { this.voiceMethod = s; return this; }
        public Builder voiceFallbackUrl(String s) { this.voiceFallbackUrl = s; return this; }
        public Builder voiceFallbackMethod(String s) { this.voiceFallbackMethod = s; return this; }
        public Builder voiceCallerIdLookup(Boolean v) { this.voiceCallerIdLookup = v; return this; }
        public Builder statusCallback(String s) { this.statusCallback = s; return this; }
        public Builder statusCallbackMethod(String s) { this.statusCallbackMethod = s; return this; }
        public Builder statusCallbackEvent(String s) { this.statusCallbackEvent = s; return this; }

        public ApplicationBody build() {
            return new ApplicationBody(this);
        }
    }
}
