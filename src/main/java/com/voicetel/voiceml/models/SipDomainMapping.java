package com.voicetel.voiceml.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Round-trip shape for every domain mapping sub-resource (Calls /
 * Registrations × CredentialList / IpAccessControlList). The sid echoes the
 * sid of the bound resource ({@code CL…} for credential mappings,
 * {@code AL…} for IP-ACL mappings); domainSid records which domain the
 * binding is attached to.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class SipDomainMapping {

    @JsonProperty("sid") private String sid;
    @JsonProperty("account_sid") private String accountSid;
    @JsonProperty("friendly_name") private String friendlyName;
    @JsonProperty("domain_sid") private String domainSid;
    @JsonProperty("date_created") private String dateCreated;
    @JsonProperty("date_updated") private String dateUpdated;
    @JsonProperty("uri") private String uri;

    public String getSid() { return sid; }
    public String getAccountSid() { return accountSid; }
    public String getFriendlyName() { return friendlyName; }
    public String getDomainSid() { return domainSid; }
    public String getDateCreated() { return dateCreated; }
    public String getDateUpdated() { return dateUpdated; }
    public String getUri() { return uri; }

    public void setSid(String v) { this.sid = v; }
    public void setAccountSid(String v) { this.accountSid = v; }
    public void setFriendlyName(String v) { this.friendlyName = v; }
    public void setDomainSid(String v) { this.domainSid = v; }
    public void setDateCreated(String v) { this.dateCreated = v; }
    public void setDateUpdated(String v) { this.dateUpdated = v; }
    public void setUri(String v) { this.uri = v; }
}
