package com.voicetel.voiceml.models;

import java.util.LinkedHashMap;
import java.util.Map;

/** Form body for {@code POST /v1/ByocTrunks/{Sid}}. All fields optional. */
public final class UpdateVoiceV1ByocTrunkRequest {

    private final String friendlyName;
    private final String voiceUrl;
    private final String voiceMethod;
    private final String voiceFallbackUrl;
    private final String voiceFallbackMethod;
    private final String statusCallbackUrl;
    private final String statusCallbackMethod;
    private final Boolean cnamLookupEnabled;
    private final String connectionPolicySid;
    private final String fromDomainSid;

    private UpdateVoiceV1ByocTrunkRequest(Builder b) {
        this.friendlyName = b.friendlyName;
        this.voiceUrl = b.voiceUrl;
        this.voiceMethod = b.voiceMethod;
        this.voiceFallbackUrl = b.voiceFallbackUrl;
        this.voiceFallbackMethod = b.voiceFallbackMethod;
        this.statusCallbackUrl = b.statusCallbackUrl;
        this.statusCallbackMethod = b.statusCallbackMethod;
        this.cnamLookupEnabled = b.cnamLookupEnabled;
        this.connectionPolicySid = b.connectionPolicySid;
        this.fromDomainSid = b.fromDomainSid;
    }

    public Map<String, Object> toForm() {
        Map<String, Object> f = new LinkedHashMap<>();
        if (friendlyName != null) f.put("FriendlyName", friendlyName);
        if (voiceUrl != null) f.put("VoiceUrl", voiceUrl);
        if (voiceMethod != null) f.put("VoiceMethod", voiceMethod);
        if (voiceFallbackUrl != null) f.put("VoiceFallbackUrl", voiceFallbackUrl);
        if (voiceFallbackMethod != null) f.put("VoiceFallbackMethod", voiceFallbackMethod);
        if (statusCallbackUrl != null) f.put("StatusCallbackUrl", statusCallbackUrl);
        if (statusCallbackMethod != null) f.put("StatusCallbackMethod", statusCallbackMethod);
        if (cnamLookupEnabled != null) f.put("CnamLookupEnabled", cnamLookupEnabled);
        if (connectionPolicySid != null) f.put("ConnectionPolicySid", connectionPolicySid);
        if (fromDomainSid != null) f.put("FromDomainSid", fromDomainSid);
        return f;
    }

    public static Builder builder() { return new Builder(); }

    public static final class Builder {
        private String friendlyName;
        private String voiceUrl;
        private String voiceMethod;
        private String voiceFallbackUrl;
        private String voiceFallbackMethod;
        private String statusCallbackUrl;
        private String statusCallbackMethod;
        private Boolean cnamLookupEnabled;
        private String connectionPolicySid;
        private String fromDomainSid;

        public Builder friendlyName(String s) { this.friendlyName = s; return this; }
        public Builder voiceUrl(String s) { this.voiceUrl = s; return this; }
        public Builder voiceMethod(String s) { this.voiceMethod = s; return this; }
        public Builder voiceFallbackUrl(String s) { this.voiceFallbackUrl = s; return this; }
        public Builder voiceFallbackMethod(String s) { this.voiceFallbackMethod = s; return this; }
        public Builder statusCallbackUrl(String s) { this.statusCallbackUrl = s; return this; }
        public Builder statusCallbackMethod(String s) { this.statusCallbackMethod = s; return this; }
        public Builder cnamLookupEnabled(Boolean b) { this.cnamLookupEnabled = b; return this; }
        public Builder connectionPolicySid(String s) { this.connectionPolicySid = s; return this; }
        public Builder fromDomainSid(String s) { this.fromDomainSid = s; return this; }

        public UpdateVoiceV1ByocTrunkRequest build() { return new UpdateVoiceV1ByocTrunkRequest(this); }
    }
}
