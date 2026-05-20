package com.voicetel.voiceml.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Tenant-self-serve view of an assigned DID.
 *
 * <p>Twilio-shape: {@code sid} is the canonical {@code PN}-prefixed opaque identifier (34 chars);
 * {@code phoneNumber} carries the E.164 form. Tenant-scoped — only DIDs assigned to the
 * authenticated account are visible.
 *
 * <p>Fields VoiceML does not track ({@code voiceApplicationSid}, SMS, emergency, trunking) emit a
 * Twilio-compat default (empty string, {@code null}, or the documented enum default) so strict
 * Jackson deserialization in twilio-java-shape code paths does not throw.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class IncomingPhoneNumber {

    @JsonProperty("sid")
    private String sid;

    @JsonProperty("account_sid")
    private String accountSid;

    @JsonProperty("phone_number")
    private String phoneNumber;

    @JsonProperty("friendly_name")
    private String friendlyName;

    @JsonProperty("api_version")
    private String apiVersion;

    @JsonProperty("uri")
    private String uri;

    @JsonProperty("voice_url")
    private String voiceUrl;

    @JsonProperty("voice_method")
    private String voiceMethod;

    @JsonProperty("voice_fallback_url")
    private String voiceFallbackUrl;

    @JsonProperty("voice_fallback_method")
    private String voiceFallbackMethod;

    @JsonProperty("date_created")
    private String dateCreated;

    @JsonProperty("date_updated")
    private String dateUpdated;

    @JsonProperty("capabilities")
    private Capabilities capabilities;

    public String getSid() { return sid; }
    public void setSid(String sid) { this.sid = sid; }

    public String getAccountSid() { return accountSid; }
    public void setAccountSid(String accountSid) { this.accountSid = accountSid; }

    public String getPhoneNumber() { return phoneNumber; }
    public void setPhoneNumber(String phoneNumber) { this.phoneNumber = phoneNumber; }

    public String getFriendlyName() { return friendlyName; }
    public void setFriendlyName(String friendlyName) { this.friendlyName = friendlyName; }

    public String getApiVersion() { return apiVersion; }
    public void setApiVersion(String apiVersion) { this.apiVersion = apiVersion; }

    public String getUri() { return uri; }
    public void setUri(String uri) { this.uri = uri; }

    public String getVoiceUrl() { return voiceUrl; }
    public void setVoiceUrl(String voiceUrl) { this.voiceUrl = voiceUrl; }

    public String getVoiceMethod() { return voiceMethod; }
    public void setVoiceMethod(String voiceMethod) { this.voiceMethod = voiceMethod; }

    public String getVoiceFallbackUrl() { return voiceFallbackUrl; }
    public void setVoiceFallbackUrl(String voiceFallbackUrl) { this.voiceFallbackUrl = voiceFallbackUrl; }

    public String getVoiceFallbackMethod() { return voiceFallbackMethod; }
    public void setVoiceFallbackMethod(String voiceFallbackMethod) { this.voiceFallbackMethod = voiceFallbackMethod; }

    public String getDateCreated() { return dateCreated; }
    public void setDateCreated(String dateCreated) { this.dateCreated = dateCreated; }

    public String getDateUpdated() { return dateUpdated; }
    public void setDateUpdated(String dateUpdated) { this.dateUpdated = dateUpdated; }

    public Capabilities getCapabilities() { return capabilities; }
    public void setCapabilities(Capabilities capabilities) { this.capabilities = capabilities; }

    /**
     * Twilio-compat channel-type capabilities envelope. VoiceML is voice-only — {@code voice=true},
     * the rest {@code false}.
     */
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Capabilities {

        @JsonProperty("voice")
        private Boolean voice;

        @JsonProperty("sms")
        private Boolean sms;

        @JsonProperty("mms")
        private Boolean mms;

        @JsonProperty("fax")
        private Boolean fax;

        public Boolean getVoice() { return voice; }
        public void setVoice(Boolean voice) { this.voice = voice; }
        public Boolean getSms() { return sms; }
        public void setSms(Boolean sms) { this.sms = sms; }
        public Boolean getMms() { return mms; }
        public void setMms(Boolean mms) { this.mms = mms; }
        public Boolean getFax() { return fax; }
        public void setFax(Boolean fax) { this.fax = fax; }
    }
}
