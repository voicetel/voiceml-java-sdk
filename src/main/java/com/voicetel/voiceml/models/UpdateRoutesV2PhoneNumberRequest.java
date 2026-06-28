package com.voicetel.voiceml.models;

import java.util.LinkedHashMap;
import java.util.Map;

/** Form body for {@code POST /v2/PhoneNumbers/{PhoneNumber}}. All fields optional. */
public final class UpdateRoutesV2PhoneNumberRequest {

    private final String voiceRegion;
    private final String friendlyName;

    private UpdateRoutesV2PhoneNumberRequest(Builder b) {
        this.voiceRegion = b.voiceRegion;
        this.friendlyName = b.friendlyName;
    }

    public Map<String, Object> toForm() {
        Map<String, Object> f = new LinkedHashMap<>();
        if (voiceRegion != null) f.put("VoiceRegion", voiceRegion);
        if (friendlyName != null) f.put("FriendlyName", friendlyName);
        return f;
    }

    public static Builder builder() { return new Builder(); }

    public static final class Builder {
        private String voiceRegion;
        private String friendlyName;
        public Builder voiceRegion(String s) { this.voiceRegion = s; return this; }
        public Builder friendlyName(String s) { this.friendlyName = s; return this; }
        public UpdateRoutesV2PhoneNumberRequest build() { return new UpdateRoutesV2PhoneNumberRequest(this); }
    }
}
