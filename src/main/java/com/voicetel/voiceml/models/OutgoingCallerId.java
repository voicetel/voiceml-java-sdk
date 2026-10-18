package com.voicetel.voiceml.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/** A verified outgoing caller ID. */
@JsonIgnoreProperties(ignoreUnknown = true)
public class OutgoingCallerId {

    @JsonProperty("sid")
    private String sid;

    @JsonProperty("account_sid")
    private String accountSid;

    @JsonProperty("friendly_name")
    private String friendlyName;

    @JsonProperty("phone_number")
    private String phoneNumber;

    @JsonProperty("date_created")
    private String dateCreated;

    @JsonProperty("date_updated")
    private String dateUpdated;

    @JsonProperty("uri")
    private String uri;

    /** @return the {@code sid} field */
    public String getSid() {
        return sid;
    }

    /** @param sid the {@code sid} field */
    public void setSid(String sid) {
        this.sid = sid;
    }

    /** @return the {@code account_sid} field */
    public String getAccountSid() {
        return accountSid;
    }

    /** @param accountSid the {@code account_sid} field */
    public void setAccountSid(String accountSid) {
        this.accountSid = accountSid;
    }

    /** @return the {@code friendly_name} field */
    public String getFriendlyName() {
        return friendlyName;
    }

    /** @param friendlyName the {@code friendly_name} field */
    public void setFriendlyName(String friendlyName) {
        this.friendlyName = friendlyName;
    }

    /** @return the {@code phone_number} field */
    public String getPhoneNumber() {
        return phoneNumber;
    }

    /** @param phoneNumber the {@code phone_number} field */
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
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
}
