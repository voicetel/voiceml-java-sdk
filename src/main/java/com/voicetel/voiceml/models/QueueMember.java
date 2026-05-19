package com.voicetel.voiceml.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/** A call currently parked in a queue. */
@JsonIgnoreProperties(ignoreUnknown = true)
public class QueueMember {

    @JsonProperty("call_sid")
    private String callSid;

    @JsonProperty("queue_sid")
    private String queueSid;

    @JsonProperty("account_sid")
    private String accountSid;

    @JsonProperty("date_enqueued")
    private String dateEnqueued;

    @JsonProperty("wait_time")
    private Integer waitTime;

    @JsonProperty("position")
    private Integer position;

    @JsonProperty("uri")
    private String uri;

    public String getCallSid() { return callSid; }
    public void setCallSid(String callSid) { this.callSid = callSid; }
    public String getQueueSid() { return queueSid; }
    public void setQueueSid(String queueSid) { this.queueSid = queueSid; }
    public String getAccountSid() { return accountSid; }
    public void setAccountSid(String accountSid) { this.accountSid = accountSid; }
    public String getDateEnqueued() { return dateEnqueued; }
    public void setDateEnqueued(String dateEnqueued) { this.dateEnqueued = dateEnqueued; }
    public Integer getWaitTime() { return waitTime; }
    public void setWaitTime(Integer waitTime) { this.waitTime = waitTime; }
    public Integer getPosition() { return position; }
    public void setPosition(Integer position) { this.position = position; }
    public String getUri() { return uri; }
    public void setUri(String uri) { this.uri = uri; }
}
