package com.voicetel.voiceml.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * A single SIP-digest username + (write-only) password — {@code CR…}. The
 * password is never round-tripped on the response — use update with a new
 * password to rotate.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class SipCredential {

    @JsonProperty("sid") private String sid;
    @JsonProperty("account_sid") private String accountSid;
    @JsonProperty("credential_list_sid") private String credentialListSid;
    @JsonProperty("username") private String username;
    @JsonProperty("date_created") private String dateCreated;
    @JsonProperty("date_updated") private String dateUpdated;
    @JsonProperty("uri") private String uri;

    public String getSid() { return sid; }
    public String getAccountSid() { return accountSid; }
    public String getCredentialListSid() { return credentialListSid; }
    public String getUsername() { return username; }
    public String getDateCreated() { return dateCreated; }
    public String getDateUpdated() { return dateUpdated; }
    public String getUri() { return uri; }

    public void setSid(String v) { this.sid = v; }
    public void setAccountSid(String v) { this.accountSid = v; }
    public void setCredentialListSid(String v) { this.credentialListSid = v; }
    public void setUsername(String v) { this.username = v; }
    public void setDateCreated(String v) { this.dateCreated = v; }
    public void setDateUpdated(String v) { this.dateUpdated = v; }
    public void setUri(String v) { this.uri = v; }
}
