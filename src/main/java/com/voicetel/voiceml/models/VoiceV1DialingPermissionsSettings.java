package com.voicetel.voiceml.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/** Twilio Voice v1 DialingPermissions Settings (subaccount inheritance flag). */
@JsonIgnoreProperties(ignoreUnknown = true)
public class VoiceV1DialingPermissionsSettings {

    @JsonProperty("dialing_permissions_inheritance") private Boolean dialingPermissionsInheritance;
    @JsonProperty("url") private String url;

    public Boolean getDialingPermissionsInheritance() { return dialingPermissionsInheritance; }
    public String getUrl() { return url; }

    public void setDialingPermissionsInheritance(Boolean v) { this.dialingPermissionsInheritance = v; }
    public void setUrl(String v) { this.url = v; }
}
