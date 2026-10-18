package com.voicetel.voiceml.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/** Persistent TwiML+callback bundle dispatched by {@code <Dial><Application>}. */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Application {

    @JsonProperty("sid")
    private String sid;

    @JsonProperty("account_sid")
    private String accountSid;

    @JsonProperty("friendly_name")
    private String friendlyName;

    @JsonProperty("api_version")
    private String apiVersion;

    @JsonProperty("voice_url")
    private String voiceUrl;

    @JsonProperty("voice_method")
    private String voiceMethod;

    @JsonProperty("voice_fallback_url")
    private String voiceFallbackUrl;

    @JsonProperty("voice_fallback_method")
    private String voiceFallbackMethod;

    @JsonProperty("voice_caller_id_lookup")
    private Boolean voiceCallerIdLookup;

    @JsonProperty("status_callback")
    private String statusCallback;

    @JsonProperty("status_callback_method")
    private String statusCallbackMethod;

    @JsonProperty("date_created")
    private String dateCreated;

    @JsonProperty("date_updated")
    private String dateUpdated;

    @JsonProperty("uri")
    private String uri;

    @JsonProperty("sms_url")
    private String smsUrl;

    @JsonProperty("sms_method")
    private String smsMethod;

    @JsonProperty("sms_fallback_url")
    private String smsFallbackUrl;

    @JsonProperty("sms_fallback_method")
    private String smsFallbackMethod;

    @JsonProperty("message_status_callback")
    private String messageStatusCallback;

    @JsonProperty("sms_status_callback")
    private String smsStatusCallback;

    public String getSid() { return sid; }
    public void setSid(String sid) { this.sid = sid; }
    public String getAccountSid() { return accountSid; }
    public void setAccountSid(String accountSid) { this.accountSid = accountSid; }
    public String getFriendlyName() { return friendlyName; }
    public void setFriendlyName(String friendlyName) { this.friendlyName = friendlyName; }
    public String getApiVersion() { return apiVersion; }
    public void setApiVersion(String apiVersion) { this.apiVersion = apiVersion; }
    public String getVoiceUrl() { return voiceUrl; }
    public void setVoiceUrl(String voiceUrl) { this.voiceUrl = voiceUrl; }
    public String getVoiceMethod() { return voiceMethod; }
    public void setVoiceMethod(String voiceMethod) { this.voiceMethod = voiceMethod; }
    public String getVoiceFallbackUrl() { return voiceFallbackUrl; }
    public void setVoiceFallbackUrl(String voiceFallbackUrl) { this.voiceFallbackUrl = voiceFallbackUrl; }
    public String getVoiceFallbackMethod() { return voiceFallbackMethod; }
    public void setVoiceFallbackMethod(String voiceFallbackMethod) { this.voiceFallbackMethod = voiceFallbackMethod; }
    public Boolean getVoiceCallerIdLookup() { return voiceCallerIdLookup; }
    public void setVoiceCallerIdLookup(Boolean voiceCallerIdLookup) { this.voiceCallerIdLookup = voiceCallerIdLookup; }
    public String getStatusCallback() { return statusCallback; }
    public void setStatusCallback(String statusCallback) { this.statusCallback = statusCallback; }
    public String getStatusCallbackMethod() { return statusCallbackMethod; }
    public void setStatusCallbackMethod(String statusCallbackMethod) { this.statusCallbackMethod = statusCallbackMethod; }
    public String getDateCreated() { return dateCreated; }
    public void setDateCreated(String dateCreated) { this.dateCreated = dateCreated; }
    public String getDateUpdated() { return dateUpdated; }
    public void setDateUpdated(String dateUpdated) { this.dateUpdated = dateUpdated; }
    public String getUri() { return uri; }
    public void setUri(String uri) { this.uri = uri; }
    public String getSmsUrl() { return smsUrl; }
    public void setSmsUrl(String smsUrl) { this.smsUrl = smsUrl; }
    public String getSmsMethod() { return smsMethod; }
    public void setSmsMethod(String smsMethod) { this.smsMethod = smsMethod; }
    public String getSmsFallbackUrl() { return smsFallbackUrl; }
    public void setSmsFallbackUrl(String smsFallbackUrl) { this.smsFallbackUrl = smsFallbackUrl; }
    public String getSmsFallbackMethod() { return smsFallbackMethod; }
    public void setSmsFallbackMethod(String smsFallbackMethod) { this.smsFallbackMethod = smsFallbackMethod; }
    public String getMessageStatusCallback() { return messageStatusCallback; }
    public void setMessageStatusCallback(String messageStatusCallback) { this.messageStatusCallback = messageStatusCallback; }
    public String getSmsStatusCallback() { return smsStatusCallback; }
    public void setSmsStatusCallback(String smsStatusCallback) { this.smsStatusCallback = smsStatusCallback; }
}
