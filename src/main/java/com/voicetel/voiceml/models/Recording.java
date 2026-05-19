package com.voicetel.voiceml.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Map;

/** A Twilio-shape Recording resource. */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Recording {

    @JsonProperty("sid")
    private String sid;

    @JsonProperty("account_sid")
    private String accountSid;

    @JsonProperty("call_sid")
    private String callSid;

    @JsonProperty("conference_sid")
    private String conferenceSid;

    @JsonProperty("status")
    private String status;

    @JsonProperty("source")
    private String source;

    @JsonProperty("channels")
    private Integer channels;

    @JsonProperty("duration")
    private String duration;

    @JsonProperty("api_version")
    private String apiVersion;

    @JsonProperty("uri")
    private String uri;

    @JsonProperty("date_created")
    private String dateCreated;

    @JsonProperty("date_updated")
    private String dateUpdated;

    @JsonProperty("start_time")
    private String startTime;

    @JsonProperty("price")
    private String price;

    @JsonProperty("price_unit")
    private String priceUnit;

    @JsonProperty("encryption_details")
    private Map<String, Object> encryptionDetails;

    @JsonProperty("subresource_uris")
    private Map<String, Object> subresourceUris;

    public String getSid() { return sid; }
    public void setSid(String sid) { this.sid = sid; }
    public String getAccountSid() { return accountSid; }
    public void setAccountSid(String accountSid) { this.accountSid = accountSid; }
    public String getCallSid() { return callSid; }
    public void setCallSid(String callSid) { this.callSid = callSid; }
    public String getConferenceSid() { return conferenceSid; }
    public void setConferenceSid(String conferenceSid) { this.conferenceSid = conferenceSid; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    public String getSource() { return source; }
    public void setSource(String source) { this.source = source; }
    public Integer getChannels() { return channels; }
    public void setChannels(Integer channels) { this.channels = channels; }
    public String getDuration() { return duration; }
    public void setDuration(String duration) { this.duration = duration; }
    public String getApiVersion() { return apiVersion; }
    public void setApiVersion(String apiVersion) { this.apiVersion = apiVersion; }
    public String getUri() { return uri; }
    public void setUri(String uri) { this.uri = uri; }
    public String getDateCreated() { return dateCreated; }
    public void setDateCreated(String dateCreated) { this.dateCreated = dateCreated; }
    public String getDateUpdated() { return dateUpdated; }
    public void setDateUpdated(String dateUpdated) { this.dateUpdated = dateUpdated; }
    public String getStartTime() { return startTime; }
    public void setStartTime(String startTime) { this.startTime = startTime; }
    public String getPrice() { return price; }
    public void setPrice(String price) { this.price = price; }
    public String getPriceUnit() { return priceUnit; }
    public void setPriceUnit(String priceUnit) { this.priceUnit = priceUnit; }
    public Map<String, Object> getEncryptionDetails() { return encryptionDetails; }
    public void setEncryptionDetails(Map<String, Object> encryptionDetails) { this.encryptionDetails = encryptionDetails; }
    public Map<String, Object> getSubresourceUris() { return subresourceUris; }
    public void setSubresourceUris(Map<String, Object> subresourceUris) { this.subresourceUris = subresourceUris; }
}
