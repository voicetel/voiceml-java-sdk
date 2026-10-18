package com.voicetel.voiceml.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Map;

/** Your VoiceML account ({@code GET /2010-04-01/Accounts/{Sid}}). */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Account {

    @JsonProperty("sid")
    private String sid;

    @JsonProperty("friendly_name")
    private String friendlyName;

    @JsonProperty("status")
    private String status;

    @JsonProperty("type")
    private String type;

    @JsonProperty("owner_account_sid")
    private String ownerAccountSid;

    @JsonProperty("date_created")
    private String dateCreated;

    @JsonProperty("date_updated")
    private String dateUpdated;

    @JsonProperty("uri")
    private String uri;

    @JsonProperty("subresource_uris")
    private Map<String, String> subresourceUris;

    /** @return the {@code sid} field */
    public String getSid() {
        return sid;
    }

    /** @param sid the {@code sid} field */
    public void setSid(String sid) {
        this.sid = sid;
    }

    /** @return the {@code friendly_name} field */
    public String getFriendlyName() {
        return friendlyName;
    }

    /** @param friendlyName the {@code friendly_name} field */
    public void setFriendlyName(String friendlyName) {
        this.friendlyName = friendlyName;
    }

    /** @return the {@code status} field */
    public String getStatus() {
        return status;
    }

    /** @param status the {@code status} field */
    public void setStatus(String status) {
        this.status = status;
    }

    /** @return the {@code type} field */
    public String getType() {
        return type;
    }

    /** @param type the {@code type} field */
    public void setType(String type) {
        this.type = type;
    }

    /** @return the {@code owner_account_sid} field */
    public String getOwnerAccountSid() {
        return ownerAccountSid;
    }

    /** @param ownerAccountSid the {@code owner_account_sid} field */
    public void setOwnerAccountSid(String ownerAccountSid) {
        this.ownerAccountSid = ownerAccountSid;
    }

    /** @return the {@code date_created} field */
    public String getDateCreated() {
        return dateCreated;
    }

    /** @param dateCreated the {@code date_created} field */
    public void setDateCreated(String dateCreated) {
        this.dateCreated = dateCreated;
    }

    /** @return the {@code date_updated} field */
    public String getDateUpdated() {
        return dateUpdated;
    }

    /** @param dateUpdated the {@code date_updated} field */
    public void setDateUpdated(String dateUpdated) {
        this.dateUpdated = dateUpdated;
    }

    /** @return the {@code uri} field */
    public String getUri() {
        return uri;
    }

    /** @param uri the {@code uri} field */
    public void setUri(String uri) {
        this.uri = uri;
    }

    /** @return the {@code subresource_uris} field */
    public Map<String, String> getSubresourceUris() {
        return subresourceUris;
    }

    /** @param subresourceUris the {@code subresource_uris} field */
    public void setSubresourceUris(Map<String, String> subresourceUris) {
        this.subresourceUris = subresourceUris;
    }
}
