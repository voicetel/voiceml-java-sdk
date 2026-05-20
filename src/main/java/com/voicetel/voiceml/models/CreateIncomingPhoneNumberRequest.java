package com.voicetel.voiceml.models;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Form body for {@code POST /IncomingPhoneNumbers}.
 *
 * <p>Idempotent on the same tenant — re-POSTing the same {@code PhoneNumber} rebinds voice routing
 * without erroring; the server returns {@code 409} when the number is already assigned to a
 * different account.
 */
public final class CreateIncomingPhoneNumberRequest {

    private final String phoneNumber;
    private final String voiceUrl;
    private final String voiceMethod;
    private final String voiceFallbackUrl;
    private final String voiceFallbackMethod;

    private CreateIncomingPhoneNumberRequest(Builder b) {
        this.phoneNumber = b.phoneNumber;
        this.voiceUrl = b.voiceUrl;
        this.voiceMethod = b.voiceMethod;
        this.voiceFallbackUrl = b.voiceFallbackUrl;
        this.voiceFallbackMethod = b.voiceFallbackMethod;
    }

    public Map<String, Object> toForm() {
        Map<String, Object> form = new LinkedHashMap<>();
        if (phoneNumber != null) form.put("PhoneNumber", phoneNumber);
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
        private String phoneNumber;
        private String voiceUrl;
        private String voiceMethod;
        private String voiceFallbackUrl;
        private String voiceFallbackMethod;

        public Builder phoneNumber(String s) { this.phoneNumber = s; return this; }
        public Builder voiceUrl(String s) { this.voiceUrl = s; return this; }
        public Builder voiceMethod(String s) { this.voiceMethod = s; return this; }
        public Builder voiceFallbackUrl(String s) { this.voiceFallbackUrl = s; return this; }
        public Builder voiceFallbackMethod(String s) { this.voiceFallbackMethod = s; return this; }

        public CreateIncomingPhoneNumberRequest build() {
            return new CreateIncomingPhoneNumberRequest(this);
        }
    }
}
