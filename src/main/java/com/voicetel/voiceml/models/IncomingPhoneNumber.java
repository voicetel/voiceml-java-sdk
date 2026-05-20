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
 * <p>The full Twilio outer field set is bound here so strict-binding code paths (Jackson against
 * a twilio-java-shape model) deserialize without throwing. Fields VoiceML does not track
 * ({@code voiceApplicationSid}, SMS, emergency, trunking, regulatory) come back as a Twilio-compat
 * default — empty string, {@code null}, or the documented enum default. Customer code that reads
 * e.g. {@code number.getCapabilities().getVoice()} therefore behaves identically against VoiceML
 * and Twilio.
 *
 * <p>Enum-shaped properties are typed as {@link String} on purpose (defensive deserialization —
 * matches the twilio-java pattern): if the server starts emitting a new enum value the SDK does
 * not throw on bind, and {@link JsonIgnoreProperties} keeps forward-compat extras from breaking.
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

    @JsonProperty("origin")
    private String origin;

    @JsonProperty("beta")
    private Boolean beta;

    @JsonProperty("type")
    private String type;

    @JsonProperty("voice_url")
    private String voiceUrl;

    @JsonProperty("voice_method")
    private String voiceMethod;

    @JsonProperty("voice_fallback_url")
    private String voiceFallbackUrl;

    @JsonProperty("voice_fallback_method")
    private String voiceFallbackMethod;

    @JsonProperty("voice_application_sid")
    private String voiceApplicationSid;

    @JsonProperty("voice_caller_id_lookup")
    private Boolean voiceCallerIdLookup;

    @JsonProperty("voice_receive_mode")
    private String voiceReceiveMode;

    @JsonProperty("sms_url")
    private String smsUrl;

    @JsonProperty("sms_method")
    private String smsMethod;

    @JsonProperty("sms_fallback_url")
    private String smsFallbackUrl;

    @JsonProperty("sms_fallback_method")
    private String smsFallbackMethod;

    @JsonProperty("sms_application_sid")
    private String smsApplicationSid;

    @JsonProperty("status_callback")
    private String statusCallback;

    @JsonProperty("status_callback_method")
    private String statusCallbackMethod;

    @JsonProperty("trunk_sid")
    private String trunkSid;

    @JsonProperty("address_sid")
    private String addressSid;

    @JsonProperty("address_requirements")
    private String addressRequirements;

    @JsonProperty("identity_sid")
    private String identitySid;

    @JsonProperty("bundle_sid")
    private String bundleSid;

    @JsonProperty("emergency_status")
    private String emergencyStatus;

    @JsonProperty("emergency_address_sid")
    private String emergencyAddressSid;

    @JsonProperty("emergency_address_status")
    private String emergencyAddressStatus;

    @JsonProperty("status")
    private String status;

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

    public String getOrigin() { return origin; }
    public void setOrigin(String origin) { this.origin = origin; }

    public Boolean getBeta() { return beta; }
    public void setBeta(Boolean beta) { this.beta = beta; }

    public String getType() { return type; }
    public void setType(String type) { this.type = type; }

    public String getVoiceUrl() { return voiceUrl; }
    public void setVoiceUrl(String voiceUrl) { this.voiceUrl = voiceUrl; }

    public String getVoiceMethod() { return voiceMethod; }
    public void setVoiceMethod(String voiceMethod) { this.voiceMethod = voiceMethod; }

    public String getVoiceFallbackUrl() { return voiceFallbackUrl; }
    public void setVoiceFallbackUrl(String voiceFallbackUrl) { this.voiceFallbackUrl = voiceFallbackUrl; }

    public String getVoiceFallbackMethod() { return voiceFallbackMethod; }
    public void setVoiceFallbackMethod(String voiceFallbackMethod) { this.voiceFallbackMethod = voiceFallbackMethod; }

    public String getVoiceApplicationSid() { return voiceApplicationSid; }
    public void setVoiceApplicationSid(String voiceApplicationSid) { this.voiceApplicationSid = voiceApplicationSid; }

    public Boolean getVoiceCallerIdLookup() { return voiceCallerIdLookup; }
    public void setVoiceCallerIdLookup(Boolean voiceCallerIdLookup) { this.voiceCallerIdLookup = voiceCallerIdLookup; }

    public String getVoiceReceiveMode() { return voiceReceiveMode; }
    public void setVoiceReceiveMode(String voiceReceiveMode) { this.voiceReceiveMode = voiceReceiveMode; }

    public String getSmsUrl() { return smsUrl; }
    public void setSmsUrl(String smsUrl) { this.smsUrl = smsUrl; }

    public String getSmsMethod() { return smsMethod; }
    public void setSmsMethod(String smsMethod) { this.smsMethod = smsMethod; }

    public String getSmsFallbackUrl() { return smsFallbackUrl; }
    public void setSmsFallbackUrl(String smsFallbackUrl) { this.smsFallbackUrl = smsFallbackUrl; }

    public String getSmsFallbackMethod() { return smsFallbackMethod; }
    public void setSmsFallbackMethod(String smsFallbackMethod) { this.smsFallbackMethod = smsFallbackMethod; }

    public String getSmsApplicationSid() { return smsApplicationSid; }
    public void setSmsApplicationSid(String smsApplicationSid) { this.smsApplicationSid = smsApplicationSid; }

    public String getStatusCallback() { return statusCallback; }
    public void setStatusCallback(String statusCallback) { this.statusCallback = statusCallback; }

    public String getStatusCallbackMethod() { return statusCallbackMethod; }
    public void setStatusCallbackMethod(String statusCallbackMethod) { this.statusCallbackMethod = statusCallbackMethod; }

    public String getTrunkSid() { return trunkSid; }
    public void setTrunkSid(String trunkSid) { this.trunkSid = trunkSid; }

    public String getAddressSid() { return addressSid; }
    public void setAddressSid(String addressSid) { this.addressSid = addressSid; }

    public String getAddressRequirements() { return addressRequirements; }
    public void setAddressRequirements(String addressRequirements) { this.addressRequirements = addressRequirements; }

    public String getIdentitySid() { return identitySid; }
    public void setIdentitySid(String identitySid) { this.identitySid = identitySid; }

    public String getBundleSid() { return bundleSid; }
    public void setBundleSid(String bundleSid) { this.bundleSid = bundleSid; }

    public String getEmergencyStatus() { return emergencyStatus; }
    public void setEmergencyStatus(String emergencyStatus) { this.emergencyStatus = emergencyStatus; }

    public String getEmergencyAddressSid() { return emergencyAddressSid; }
    public void setEmergencyAddressSid(String emergencyAddressSid) { this.emergencyAddressSid = emergencyAddressSid; }

    public String getEmergencyAddressStatus() { return emergencyAddressStatus; }
    public void setEmergencyAddressStatus(String emergencyAddressStatus) { this.emergencyAddressStatus = emergencyAddressStatus; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

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
