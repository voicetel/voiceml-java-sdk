package com.voicetel.voiceml.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/** Conversations push Credential (APN/GCM/FCM). SID is {@code CR…}. */
@JsonIgnoreProperties(ignoreUnknown = true)
public class ConversationsV1Credential {

    @JsonProperty("sid") private String sid;
    @JsonProperty("account_sid") private String accountSid;
    @JsonProperty("friendly_name") private String friendlyName;
    @JsonProperty("type") private String type;
    @JsonProperty("sandbox") private String sandbox;
    @JsonProperty("date_created") private String dateCreated;
    @JsonProperty("date_updated") private String dateUpdated;
    @JsonProperty("url") private String url;

    public String getSid() { return sid; }
    public String getAccountSid() { return accountSid; }
    public String getFriendlyName() { return friendlyName; }
    public String getType() { return type; }
    public String getSandbox() { return sandbox; }
    public String getDateCreated() { return dateCreated; }
    public String getDateUpdated() { return dateUpdated; }
    public String getUrl() { return url; }

    public void setSid(String v) { this.sid = v; }
    public void setAccountSid(String v) { this.accountSid = v; }
    public void setFriendlyName(String v) { this.friendlyName = v; }
    public void setType(String v) { this.type = v; }
    public void setSandbox(String v) { this.sandbox = v; }
    public void setDateCreated(String v) { this.dateCreated = v; }
    public void setDateUpdated(String v) { this.dateUpdated = v; }
    public void setUrl(String v) { this.url = v; }
}
