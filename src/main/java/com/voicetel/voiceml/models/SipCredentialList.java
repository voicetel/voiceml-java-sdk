package com.voicetel.voiceml.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Map;

/** A named bag of SIP-digest credentials — Twilio-compatible {@code CL…}. */
@JsonIgnoreProperties(ignoreUnknown = true)
public class SipCredentialList {

    @JsonProperty("sid") private String sid;
    @JsonProperty("account_sid") private String accountSid;
    @JsonProperty("friendly_name") private String friendlyName;
    @JsonProperty("date_created") private String dateCreated;
    @JsonProperty("date_updated") private String dateUpdated;
    @JsonProperty("uri") private String uri;
    @JsonProperty("subresource_uris") private Map<String, String> subresourceUris;

    public String getSid() { return sid; }
    public String getAccountSid() { return accountSid; }
    public String getFriendlyName() { return friendlyName; }
    public String getDateCreated() { return dateCreated; }
    public String getDateUpdated() { return dateUpdated; }
    public String getUri() { return uri; }
    public Map<String, String> getSubresourceUris() { return subresourceUris; }

    public void setSid(String v) { this.sid = v; }
    public void setAccountSid(String v) { this.accountSid = v; }
    public void setFriendlyName(String v) { this.friendlyName = v; }
    public void setDateCreated(String v) { this.dateCreated = v; }
    public void setDateUpdated(String v) { this.dateUpdated = v; }
    public void setUri(String v) { this.uri = v; }
    public void setSubresourceUris(Map<String, String> v) { this.subresourceUris = v; }
}
