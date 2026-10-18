package com.voicetel.voiceml.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Delivery-quality feedback echoed back for a Message
 * ({@code POST /2010-04-01/Accounts/{AccountSid}/Messages/{Sid}/Feedback.json}).
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class MessageFeedback {

    @JsonProperty("account_sid")
    private String accountSid;

    @JsonProperty("message_sid")
    private String messageSid;

    @JsonProperty("outcome")
    private String outcome;

    @JsonProperty("date_created")
    private String dateCreated;

    @JsonProperty("date_updated")
    private String dateUpdated;

    @JsonProperty("uri")
    private String uri;

    /** @return the {@code account_sid} field */
    public String getAccountSid() {
        return accountSid;
    }

    /** @param accountSid the {@code account_sid} field */
    public void setAccountSid(String accountSid) {
        this.accountSid = accountSid;
    }

    /** @return the {@code message_sid} field */
    public String getMessageSid() {
        return messageSid;
    }

    /** @param messageSid the {@code message_sid} field */
    public void setMessageSid(String messageSid) {
        this.messageSid = messageSid;
    }

    /** @return the {@code outcome} field ({@code confirmed} or {@code unconfirmed}) */
    public String getOutcome() {
        return outcome;
    }

    /** @param outcome the {@code outcome} field */
    public void setOutcome(String outcome) {
        this.outcome = outcome;
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
