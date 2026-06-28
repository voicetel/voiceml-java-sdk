package com.voicetel.voiceml.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/** Twilio Voice v1 bring-your-own-carrier (BYOC) trunk. SID is {@code BY…}. */
@JsonIgnoreProperties(ignoreUnknown = true)
public class VoiceV1ByocTrunk {

    @JsonProperty("account_sid") private String accountSid;
    @JsonProperty("sid") private String sid;
    @JsonProperty("friendly_name") private String friendlyName;
    @JsonProperty("voice_url") private String voiceUrl;
    @JsonProperty("voice_method") private String voiceMethod;
    @JsonProperty("voice_fallback_url") private String voiceFallbackUrl;
    @JsonProperty("voice_fallback_method") private String voiceFallbackMethod;
    @JsonProperty("status_callback_url") private String statusCallbackUrl;
    @JsonProperty("status_callback_method") private String statusCallbackMethod;
    @JsonProperty("cnam_lookup_enabled") private Boolean cnamLookupEnabled;
    @JsonProperty("connection_policy_sid") private String connectionPolicySid;
    @JsonProperty("from_domain_sid") private String fromDomainSid;
    @JsonProperty("date_created") private String dateCreated;
    @JsonProperty("date_updated") private String dateUpdated;
    @JsonProperty("url") private String url;

    public String getAccountSid() { return accountSid; }
    public String getSid() { return sid; }
    public String getFriendlyName() { return friendlyName; }
    public String getVoiceUrl() { return voiceUrl; }
    public String getVoiceMethod() { return voiceMethod; }
    public String getVoiceFallbackUrl() { return voiceFallbackUrl; }
    public String getVoiceFallbackMethod() { return voiceFallbackMethod; }
    public String getStatusCallbackUrl() { return statusCallbackUrl; }
    public String getStatusCallbackMethod() { return statusCallbackMethod; }
    public Boolean getCnamLookupEnabled() { return cnamLookupEnabled; }
    public String getConnectionPolicySid() { return connectionPolicySid; }
    public String getFromDomainSid() { return fromDomainSid; }
    public String getDateCreated() { return dateCreated; }
    public String getDateUpdated() { return dateUpdated; }
    public String getUrl() { return url; }

    public void setAccountSid(String v) { this.accountSid = v; }
    public void setSid(String v) { this.sid = v; }
    public void setFriendlyName(String v) { this.friendlyName = v; }
    public void setVoiceUrl(String v) { this.voiceUrl = v; }
    public void setVoiceMethod(String v) { this.voiceMethod = v; }
    public void setVoiceFallbackUrl(String v) { this.voiceFallbackUrl = v; }
    public void setVoiceFallbackMethod(String v) { this.voiceFallbackMethod = v; }
    public void setStatusCallbackUrl(String v) { this.statusCallbackUrl = v; }
    public void setStatusCallbackMethod(String v) { this.statusCallbackMethod = v; }
    public void setCnamLookupEnabled(Boolean v) { this.cnamLookupEnabled = v; }
    public void setConnectionPolicySid(String v) { this.connectionPolicySid = v; }
    public void setFromDomainSid(String v) { this.fromDomainSid = v; }
    public void setDateCreated(String v) { this.dateCreated = v; }
    public void setDateUpdated(String v) { this.dateUpdated = v; }
    public void setUrl(String v) { this.url = v; }
}
