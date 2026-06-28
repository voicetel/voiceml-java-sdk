package com.voicetel.voiceml.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Map;

/** A configuration-address (inbound channel binding). SID is {@code IG…}. */
@JsonIgnoreProperties(ignoreUnknown = true)
public class ConversationsV1ConfigAddress {

    @JsonProperty("sid") private String sid;
    @JsonProperty("account_sid") private String accountSid;
    @JsonProperty("type") private String type;
    @JsonProperty("address") private String address;
    @JsonProperty("friendly_name") private String friendlyName;
    @JsonProperty("auto_creation") private Map<String, Object> autoCreation;
    @JsonProperty("date_created") private String dateCreated;
    @JsonProperty("date_updated") private String dateUpdated;
    @JsonProperty("url") private String url;
    @JsonProperty("address_country") private String addressCountry;

    public String getSid() { return sid; }
    public String getAccountSid() { return accountSid; }
    public String getType() { return type; }
    public String getAddress() { return address; }
    public String getFriendlyName() { return friendlyName; }
    public Map<String, Object> getAutoCreation() { return autoCreation; }
    public String getDateCreated() { return dateCreated; }
    public String getDateUpdated() { return dateUpdated; }
    public String getUrl() { return url; }
    public String getAddressCountry() { return addressCountry; }

    public void setSid(String v) { this.sid = v; }
    public void setAccountSid(String v) { this.accountSid = v; }
    public void setType(String v) { this.type = v; }
    public void setAddress(String v) { this.address = v; }
    public void setFriendlyName(String v) { this.friendlyName = v; }
    public void setAutoCreation(Map<String, Object> v) { this.autoCreation = v; }
    public void setDateCreated(String v) { this.dateCreated = v; }
    public void setDateUpdated(String v) { this.dateUpdated = v; }
    public void setUrl(String v) { this.url = v; }
    public void setAddressCountry(String v) { this.addressCountry = v; }
}
