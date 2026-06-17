package com.voicetel.voiceml.models;

import java.util.LinkedHashMap;
import java.util.Map;

/** Form body for {@code POST /SIP/Domains/{Sid}}. All fields optional. */
public final class UpdateSipDomainRequest {

    private final String friendlyName;
    private final String voiceUrl;
    private final String voiceMethod;
    private final String voiceFallbackUrl;
    private final String voiceFallbackMethod;
    private final String voiceStatusCallbackUrl;
    private final String voiceStatusCallbackMethod;
    private final Boolean sipRegistration;
    private final Boolean secure;
    private final Boolean emergencyCallingEnabled;
    private final String byocTrunkSid;
    private final String emergencyCallerSid;

    private UpdateSipDomainRequest(Builder b) {
        this.friendlyName = b.friendlyName;
        this.voiceUrl = b.voiceUrl;
        this.voiceMethod = b.voiceMethod;
        this.voiceFallbackUrl = b.voiceFallbackUrl;
        this.voiceFallbackMethod = b.voiceFallbackMethod;
        this.voiceStatusCallbackUrl = b.voiceStatusCallbackUrl;
        this.voiceStatusCallbackMethod = b.voiceStatusCallbackMethod;
        this.sipRegistration = b.sipRegistration;
        this.secure = b.secure;
        this.emergencyCallingEnabled = b.emergencyCallingEnabled;
        this.byocTrunkSid = b.byocTrunkSid;
        this.emergencyCallerSid = b.emergencyCallerSid;
    }

    public Map<String, Object> toForm() {
        Map<String, Object> f = new LinkedHashMap<>();
        if (friendlyName != null) f.put("FriendlyName", friendlyName);
        if (voiceUrl != null) f.put("VoiceUrl", voiceUrl);
        if (voiceMethod != null) f.put("VoiceMethod", voiceMethod);
        if (voiceFallbackUrl != null) f.put("VoiceFallbackUrl", voiceFallbackUrl);
        if (voiceFallbackMethod != null) f.put("VoiceFallbackMethod", voiceFallbackMethod);
        if (voiceStatusCallbackUrl != null) f.put("VoiceStatusCallbackUrl", voiceStatusCallbackUrl);
        if (voiceStatusCallbackMethod != null) f.put("VoiceStatusCallbackMethod", voiceStatusCallbackMethod);
        if (sipRegistration != null) f.put("SipRegistration", sipRegistration.toString());
        if (secure != null) f.put("Secure", secure.toString());
        if (emergencyCallingEnabled != null) f.put("EmergencyCallingEnabled", emergencyCallingEnabled.toString());
        if (byocTrunkSid != null) f.put("ByocTrunkSid", byocTrunkSid);
        if (emergencyCallerSid != null) f.put("EmergencyCallerSid", emergencyCallerSid);
        return f;
    }

    public static Builder builder() { return new Builder(); }

    public static final class Builder {
        private String friendlyName;
        private String voiceUrl;
        private String voiceMethod;
        private String voiceFallbackUrl;
        private String voiceFallbackMethod;
        private String voiceStatusCallbackUrl;
        private String voiceStatusCallbackMethod;
        private Boolean sipRegistration;
        private Boolean secure;
        private Boolean emergencyCallingEnabled;
        private String byocTrunkSid;
        private String emergencyCallerSid;

        public Builder friendlyName(String s) { this.friendlyName = s; return this; }
        public Builder voiceUrl(String s) { this.voiceUrl = s; return this; }
        public Builder voiceMethod(String s) { this.voiceMethod = s; return this; }
        public Builder voiceFallbackUrl(String s) { this.voiceFallbackUrl = s; return this; }
        public Builder voiceFallbackMethod(String s) { this.voiceFallbackMethod = s; return this; }
        public Builder voiceStatusCallbackUrl(String s) { this.voiceStatusCallbackUrl = s; return this; }
        public Builder voiceStatusCallbackMethod(String s) { this.voiceStatusCallbackMethod = s; return this; }
        public Builder sipRegistration(Boolean b) { this.sipRegistration = b; return this; }
        public Builder secure(Boolean b) { this.secure = b; return this; }
        public Builder emergencyCallingEnabled(Boolean b) { this.emergencyCallingEnabled = b; return this; }
        public Builder byocTrunkSid(String s) { this.byocTrunkSid = s; return this; }
        public Builder emergencyCallerSid(String s) { this.emergencyCallerSid = s; return this; }

        public UpdateSipDomainRequest build() { return new UpdateSipDomainRequest(this); }
    }
}
