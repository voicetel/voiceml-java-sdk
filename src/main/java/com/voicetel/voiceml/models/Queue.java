package com.voicetel.voiceml.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/** A Twilio-shape Queue resource. */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Queue {

    @JsonProperty("sid")
    private String sid;

    @JsonProperty("account_sid")
    private String accountSid;

    @JsonProperty("friendly_name")
    private String friendlyName;

    @JsonProperty("current_size")
    private Integer currentSize;

    @JsonProperty("max_size")
    private Integer maxSize;

    @JsonProperty("average_wait_time")
    private Integer averageWaitTime;

    @JsonProperty("date_created")
    private String dateCreated;

    @JsonProperty("date_updated")
    private String dateUpdated;

    @JsonProperty("uri")
    private String uri;

    public String getSid() { return sid; }
    public void setSid(String sid) { this.sid = sid; }
    public String getAccountSid() { return accountSid; }
    public void setAccountSid(String accountSid) { this.accountSid = accountSid; }
    public String getFriendlyName() { return friendlyName; }
    public void setFriendlyName(String friendlyName) { this.friendlyName = friendlyName; }
    public Integer getCurrentSize() { return currentSize; }
    public void setCurrentSize(Integer currentSize) { this.currentSize = currentSize; }
    public Integer getMaxSize() { return maxSize; }
    public void setMaxSize(Integer maxSize) { this.maxSize = maxSize; }
    public Integer getAverageWaitTime() { return averageWaitTime; }
    public void setAverageWaitTime(Integer averageWaitTime) { this.averageWaitTime = averageWaitTime; }
    public String getDateCreated() { return dateCreated; }
    public void setDateCreated(String dateCreated) { this.dateCreated = dateCreated; }
    public String getDateUpdated() { return dateUpdated; }
    public void setDateUpdated(String dateUpdated) { this.dateUpdated = dateUpdated; }
    public String getUri() { return uri; }
    public void setUri(String uri) { this.uri = uri; }
}
