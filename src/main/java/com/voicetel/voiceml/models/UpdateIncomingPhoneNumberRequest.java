package com.voicetel.voiceml.models;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Form body for {@code POST /IncomingPhoneNumbers/{PhoneNumberSid}}.
 *
 * <p>Only-set-fields-touched semantics — unset fields are left as-is on the server.
 */
public final class UpdateIncomingPhoneNumberRequest {

    private final String voiceUrl;
    private final String voiceMethod;
    private final String voiceFallbackUrl;
    private final String voiceFallbackMethod;

    private UpdateIncomingPhoneNumberRequest(Builder b) {
        this.voiceUrl = b.voiceUrl;
        this.voiceMethod = b.voiceMethod;
        this.voiceFallbackUrl = b.voiceFallbackUrl;
        this.voiceFallbackMethod = b.voiceFallbackMethod;
    }

    public Map<String, Object> toForm() {
        Map<String, Object> form = new LinkedHashMap<>();
        if (voiceUrl != null) form.put("VoiceUrl", voiceUrl);
        if (voiceMethod != null) form.put("VoiceMethod", voiceMethod);
        if (voiceFallbackUrl != null) form.put("VoiceFallbackUrl", voiceFallbackUrl);
        if (voiceFallbackMethod != null) form.put("VoiceFallbackMethod", voiceFallbackMethod);
        return form;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder {
        private String voiceUrl;
        private String voiceMethod;
        private String voiceFallbackUrl;
        private String voiceFallbackMethod;

        public Builder voiceUrl(String s) { this.voiceUrl = s; return this; }
        public Builder voiceMethod(String s) { this.voiceMethod = s; return this; }
        public Builder voiceFallbackUrl(String s) { this.voiceFallbackUrl = s; return this; }
        public Builder voiceFallbackMethod(String s) { this.voiceFallbackMethod = s; return this; }

        public UpdateIncomingPhoneNumberRequest build() {
            return new UpdateIncomingPhoneNumberRequest(this);
        }
    }
}
