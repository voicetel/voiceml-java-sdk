package com.voicetel.voiceml.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Map;

/**
 * A SIP ingress domain — Twilio-compatible {@code SD…} resource. Bind a
 * CredentialList and/or IpAccessControlList via the mapping sub-resources to
 * authenticate inbound SIP traffic.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class SipDomain {

    @JsonProperty("sid") private String sid;
    @JsonProperty("account_sid") private String accountSid;
    @JsonProperty("domain_name") private String domainName;
    @JsonProperty("api_version") private String apiVersion;
    @JsonProperty("friendly_name") private String friendlyName;
    @JsonProperty("auth_type") private String authType;
    @JsonProperty("voice_url") private String voiceUrl;
    @JsonProperty("voice_method") private String voiceMethod;
    @JsonProperty("voice_fallback_url") private String voiceFallbackUrl;
    @JsonProperty("voice_fallback_method") private String voiceFallbackMethod;
    @JsonProperty("voice_status_callback_url") private String voiceStatusCallbackUrl;
    @JsonProperty("voice_status_callback_method") private String voiceStatusCallbackMethod;
    @JsonProperty("sip_registration") private Boolean sipRegistration;
    @JsonProperty("emergency_calling_enabled") private Boolean emergencyCallingEnabled;
    @JsonProperty("secure") private Boolean secure;
    @JsonProperty("byoc_trunk_sid") private String byocTrunkSid;
    @JsonProperty("emergency_caller_sid") private String emergencyCallerSid;
    @JsonProperty("date_created") private String dateCreated;
    @JsonProperty("date_updated") private String dateUpdated;
    @JsonProperty("uri") private String uri;
    @JsonProperty("subresource_uris") private Map<String, String> subresourceUris;

    public String getSid() { return sid; }
    public String getAccountSid() { return accountSid; }
    public String getDomainName() { return domainName; }
    public String getApiVersion() { return apiVersion; }
    public String getFriendlyName() { return friendlyName; }
    public String getAuthType() { return authType; }
    public String getVoiceUrl() { return voiceUrl; }
    public String getVoiceMethod() { return voiceMethod; }
    public String getVoiceFallbackUrl() { return voiceFallbackUrl; }
    public String getVoiceFallbackMethod() { return voiceFallbackMethod; }
    public String getVoiceStatusCallbackUrl() { return voiceStatusCallbackUrl; }
    public String getVoiceStatusCallbackMethod() { return voiceStatusCallbackMethod; }
    public Boolean getSipRegistration() { return sipRegistration; }
    public Boolean getEmergencyCallingEnabled() { return emergencyCallingEnabled; }
    public Boolean getSecure() { return secure; }
    public String getByocTrunkSid() { return byocTrunkSid; }
    public String getEmergencyCallerSid() { return emergencyCallerSid; }
    public String getDateCreated() { return dateCreated; }
    public String getDateUpdated() { return dateUpdated; }
    public String getUri() { return uri; }
    public Map<String, String> getSubresourceUris() { return subresourceUris; }

    public void setSid(String v) { this.sid = v; }
    public void setAccountSid(String v) { this.accountSid = v; }
    public void setDomainName(String v) { this.domainName = v; }
    public void setApiVersion(String v) { this.apiVersion = v; }
    public void setFriendlyName(String v) { this.friendlyName = v; }
    public void setAuthType(String v) { this.authType = v; }
    public void setVoiceUrl(String v) { this.voiceUrl = v; }
    public void setVoiceMethod(String v) { this.voiceMethod = v; }
    public void setVoiceFallbackUrl(String v) { this.voiceFallbackUrl = v; }
    public void setVoiceFallbackMethod(String v) { this.voiceFallbackMethod = v; }
    public void setVoiceStatusCallbackUrl(String v) { this.voiceStatusCallbackUrl = v; }
    public void setVoiceStatusCallbackMethod(String v) { this.voiceStatusCallbackMethod = v; }
    public void setSipRegistration(Boolean v) { this.sipRegistration = v; }
    public void setEmergencyCallingEnabled(Boolean v) { this.emergencyCallingEnabled = v; }
    public void setSecure(Boolean v) { this.secure = v; }
    public void setByocTrunkSid(String v) { this.byocTrunkSid = v; }
    public void setEmergencyCallerSid(String v) { this.emergencyCallerSid = v; }
    public void setDateCreated(String v) { this.dateCreated = v; }
    public void setDateUpdated(String v) { this.dateUpdated = v; }
    public void setUri(String v) { this.uri = v; }
    public void setSubresourceUris(Map<String, String> v) { this.subresourceUris = v; }
}
