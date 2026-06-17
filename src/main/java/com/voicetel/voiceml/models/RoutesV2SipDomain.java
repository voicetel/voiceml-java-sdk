package com.voicetel.voiceml.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Twilio routes/v2 Inbound Processing Region binding for a SIP domain.
 * SID is {@code QQ…}. Keyed by domain name (not the SipDomain SID); the
 * account is resolved from HTTP Basic auth.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class RoutesV2SipDomain {

    @JsonProperty("sid") private String sid;
    @JsonProperty("sip_domain") private String sipDomain;
    @JsonProperty("account_sid") private String accountSid;
    @JsonProperty("friendly_name") private String friendlyName;
    @JsonProperty("voice_region") private String voiceRegion;
    @JsonProperty("url") private String url;
    @JsonProperty("date_created") private String dateCreated;
    @JsonProperty("date_updated") private String dateUpdated;

    public String getSid() { return sid; }
    public String getSipDomain() { return sipDomain; }
    public String getAccountSid() { return accountSid; }
    public String getFriendlyName() { return friendlyName; }
    public String getVoiceRegion() { return voiceRegion; }
    public String getUrl() { return url; }
    public String getDateCreated() { return dateCreated; }
    public String getDateUpdated() { return dateUpdated; }

    public void setSid(String v) { this.sid = v; }
    public void setSipDomain(String v) { this.sipDomain = v; }
    public void setAccountSid(String v) { this.accountSid = v; }
    public void setFriendlyName(String v) { this.friendlyName = v; }
    public void setVoiceRegion(String v) { this.voiceRegion = v; }
    public void setUrl(String v) { this.url = v; }
    public void setDateCreated(String v) { this.dateCreated = v; }
    public void setDateUpdated(String v) { this.dateUpdated = v; }
}
