package com.voicetel.voiceml.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/** A live per-call transcription. Events stream via {@code StatusCallback}. */
@JsonIgnoreProperties(ignoreUnknown = true)
public class CallTranscription {

    @JsonProperty("sid")
    private String sid;

    @JsonProperty("account_sid")
    private String accountSid;

    @JsonProperty("call_sid")
    private String callSid;

    @JsonProperty("name")
    private String name;

    @JsonProperty("language_code")
    private String languageCode;

    @JsonProperty("transcription_engine")
    private String transcriptionEngine;

    @JsonProperty("status")
    private String status;

    @JsonProperty("api_version")
    private String apiVersion;

    @JsonProperty("uri")
    private String uri;

    @JsonProperty("date_created")
    private String dateCreated;

    @JsonProperty("date_updated")
    private String dateUpdated;

    public String getSid() { return sid; }
    public void setSid(String sid) { this.sid = sid; }
    public String getAccountSid() { return accountSid; }
    public void setAccountSid(String accountSid) { this.accountSid = accountSid; }
    public String getCallSid() { return callSid; }
    public void setCallSid(String callSid) { this.callSid = callSid; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getLanguageCode() { return languageCode; }
    public void setLanguageCode(String languageCode) { this.languageCode = languageCode; }
    public String getTranscriptionEngine() { return transcriptionEngine; }
    public void setTranscriptionEngine(String transcriptionEngine) { this.transcriptionEngine = transcriptionEngine; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    public String getApiVersion() { return apiVersion; }
    public void setApiVersion(String apiVersion) { this.apiVersion = apiVersion; }
    public String getUri() { return uri; }
    public void setUri(String uri) { this.uri = uri; }
    public String getDateCreated() { return dateCreated; }
    public void setDateCreated(String dateCreated) { this.dateCreated = dateCreated; }
    public String getDateUpdated() { return dateUpdated; }
    public void setDateUpdated(String dateUpdated) { this.dateUpdated = dateUpdated; }
}
