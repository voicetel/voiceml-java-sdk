package com.voicetel.voiceml.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/** A participant in a conference. */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Participant {

    @JsonProperty("call_sid")
    private String callSid;

    @JsonProperty("conference_sid")
    private String conferenceSid;

    @JsonProperty("account_sid")
    private String accountSid;

    @JsonProperty("muted")
    private Boolean muted;

    @JsonProperty("hold")
    private Boolean hold;

    @JsonProperty("start_conference_on_enter")
    private Boolean startConferenceOnEnter;

    @JsonProperty("end_conference_on_exit")
    private Boolean endConferenceOnExit;

    @JsonProperty("status")
    private String status;

    @JsonProperty("label")
    private String label;

    @JsonProperty("api_version")
    private String apiVersion;

    @JsonProperty("uri")
    private String uri;

    @JsonProperty("date_created")
    private String dateCreated;

    @JsonProperty("date_updated")
    private String dateUpdated;

    public String getCallSid() { return callSid; }
    public void setCallSid(String callSid) { this.callSid = callSid; }
    public String getConferenceSid() { return conferenceSid; }
    public void setConferenceSid(String conferenceSid) { this.conferenceSid = conferenceSid; }
    public String getAccountSid() { return accountSid; }
    public void setAccountSid(String accountSid) { this.accountSid = accountSid; }
    public Boolean getMuted() { return muted; }
    public void setMuted(Boolean muted) { this.muted = muted; }
    public Boolean getHold() { return hold; }
    public void setHold(Boolean hold) { this.hold = hold; }
    public Boolean getStartConferenceOnEnter() { return startConferenceOnEnter; }
    public void setStartConferenceOnEnter(Boolean startConferenceOnEnter) { this.startConferenceOnEnter = startConferenceOnEnter; }
    public Boolean getEndConferenceOnExit() { return endConferenceOnExit; }
    public void setEndConferenceOnExit(Boolean endConferenceOnExit) { this.endConferenceOnExit = endConferenceOnExit; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    public String getLabel() { return label; }
    public void setLabel(String label) { this.label = label; }
    public String getApiVersion() { return apiVersion; }
    public void setApiVersion(String apiVersion) { this.apiVersion = apiVersion; }
    public String getUri() { return uri; }
    public void setUri(String uri) { this.uri = uri; }
    public String getDateCreated() { return dateCreated; }
    public void setDateCreated(String dateCreated) { this.dateCreated = dateCreated; }
    public String getDateUpdated() { return dateUpdated; }
    public void setDateUpdated(String dateUpdated) { this.dateUpdated = dateUpdated; }
}
