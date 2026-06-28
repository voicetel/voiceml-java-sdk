package com.voicetel.voiceml.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Twilio routes/v2 Inbound Processing Region binding for a phone number.
 * SID is {@code QQ…}. Keyed by phone number (E.164) or PN SID; the account
 * is resolved from HTTP Basic auth.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class RoutesV2PhoneNumber {

    @JsonProperty("sid") private String sid;
    @JsonProperty("phone_number") private String phoneNumber;
    @JsonProperty("account_sid") private String accountSid;
    @JsonProperty("friendly_name") private String friendlyName;
    @JsonProperty("voice_region") private String voiceRegion;
    @JsonProperty("url") private String url;
    @JsonProperty("date_created") private String dateCreated;
    @JsonProperty("date_updated") private String dateUpdated;

    public String getSid() { return sid; }
    public String getPhoneNumber() { return phoneNumber; }
    public String getAccountSid() { return accountSid; }
    public String getFriendlyName() { return friendlyName; }
    public String getVoiceRegion() { return voiceRegion; }
    public String getUrl() { return url; }
    public String getDateCreated() { return dateCreated; }
    public String getDateUpdated() { return dateUpdated; }

    public void setSid(String v) { this.sid = v; }
    public void setPhoneNumber(String v) { this.phoneNumber = v; }
    public void setAccountSid(String v) { this.accountSid = v; }
    public void setFriendlyName(String v) { this.friendlyName = v; }
    public void setVoiceRegion(String v) { this.voiceRegion = v; }
    public void setUrl(String v) { this.url = v; }
    public void setDateCreated(String v) { this.dateCreated = v; }
    public void setDateUpdated(String v) { this.dateUpdated = v; }
}
