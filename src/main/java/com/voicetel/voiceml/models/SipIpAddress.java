package com.voicetel.voiceml.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/** A single CIDR-bound entry in an IpAccessControlList — {@code IP…}. */
@JsonIgnoreProperties(ignoreUnknown = true)
public class SipIpAddress {

    @JsonProperty("sid") private String sid;
    @JsonProperty("account_sid") private String accountSid;
    @JsonProperty("ip_access_control_list_sid") private String ipAccessControlListSid;
    @JsonProperty("friendly_name") private String friendlyName;
    @JsonProperty("ip_address") private String ipAddress;
    @JsonProperty("cidr_prefix_length") private Integer cidrPrefixLength;
    @JsonProperty("date_created") private String dateCreated;
    @JsonProperty("date_updated") private String dateUpdated;
    @JsonProperty("uri") private String uri;

    public String getSid() { return sid; }
    public String getAccountSid() { return accountSid; }
    public String getIpAccessControlListSid() { return ipAccessControlListSid; }
    public String getFriendlyName() { return friendlyName; }
    public String getIpAddress() { return ipAddress; }
    public Integer getCidrPrefixLength() { return cidrPrefixLength; }
    public String getDateCreated() { return dateCreated; }
    public String getDateUpdated() { return dateUpdated; }
    public String getUri() { return uri; }

    public void setSid(String v) { this.sid = v; }
    public void setAccountSid(String v) { this.accountSid = v; }
    public void setIpAccessControlListSid(String v) { this.ipAccessControlListSid = v; }
    public void setFriendlyName(String v) { this.friendlyName = v; }
    public void setIpAddress(String v) { this.ipAddress = v; }
    public void setCidrPrefixLength(Integer v) { this.cidrPrefixLength = v; }
    public void setDateCreated(String v) { this.dateCreated = v; }
    public void setDateUpdated(String v) { this.dateUpdated = v; }
    public void setUri(String v) { this.uri = v; }
}
