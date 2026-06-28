package com.voicetel.voiceml.resources;

import com.voicetel.voiceml.Transport;
import com.voicetel.voiceml.models.UpdateVoiceV1SettingsRequest;
import com.voicetel.voiceml.models.VoiceV1DialingPermissionsSettings;

import java.util.Map;

/** Operations on {@code /v1/Settings} (DialingPermissions inheritance). */
public final class VoiceV1SettingsResource extends BaseResource {

    public VoiceV1SettingsResource(Transport transport) {
        super(transport);
    }

    public VoiceV1DialingPermissionsSettings fetch() {
        return decode(transport.request("GET", "/v1/Settings", null, null),
                VoiceV1DialingPermissionsSettings.class);
    }

    public VoiceV1DialingPermissionsSettings update(UpdateVoiceV1SettingsRequest body) {
        Map<String, Object> form = body != null ? body.toForm() : null;
        return decode(transport.request("POST", "/v1/Settings", null, form),
                VoiceV1DialingPermissionsSettings.class);
    }
}
