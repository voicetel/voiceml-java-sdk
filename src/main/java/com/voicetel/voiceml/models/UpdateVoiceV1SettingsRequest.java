package com.voicetel.voiceml.models;

import java.util.LinkedHashMap;
import java.util.Map;

/** Form body for {@code POST /v1/Settings}. */
public final class UpdateVoiceV1SettingsRequest {

    private final Boolean dialingPermissionsInheritance;

    private UpdateVoiceV1SettingsRequest(Builder b) {
        this.dialingPermissionsInheritance = b.dialingPermissionsInheritance;
    }

    public Map<String, Object> toForm() {
        Map<String, Object> f = new LinkedHashMap<>();
        if (dialingPermissionsInheritance != null) {
            f.put("DialingPermissionsInheritance", dialingPermissionsInheritance);
        }
        return f;
    }

    public static Builder builder() { return new Builder(); }

    public static final class Builder {
        private Boolean dialingPermissionsInheritance;
        public Builder dialingPermissionsInheritance(Boolean v) {
            this.dialingPermissionsInheritance = v;
            return this;
        }
        public UpdateVoiceV1SettingsRequest build() { return new UpdateVoiceV1SettingsRequest(this); }
    }
}
