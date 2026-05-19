package com.voicetel.voiceml.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Map;

/** A Twilio-shape Call resource. Returned by the {@code /Calls} endpoints. */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Call {

    @JsonProperty("sid")
    private String sid;

    @JsonProperty("account_sid")
    private String accountSid;

    @JsonProperty("api_version")
    private String apiVersion;

    @JsonProperty("to")
    private String to;

    @JsonProperty("to_formatted")
    private String toFormatted;

    @JsonProperty("from")
    private String from;

    @JsonProperty("from_formatted")
    private String fromFormatted;

    @JsonProperty("parent_call_sid")
    private String parentCallSid;

    @JsonProperty("caller_name")
    private String callerName;

    @JsonProperty("forwarded_from")
    private String forwardedFrom;

    @JsonProperty("status")
    private String status;

    @JsonProperty("direction")
    private String direction;

    @JsonProperty("answered_by")
    private String answeredBy;

    @JsonProperty("start_time")
    private String startTime;

    @JsonProperty("end_time")
    private String endTime;

    @JsonProperty("duration")
    private String duration;

    @JsonProperty("price")
    private String price;

    @JsonProperty("price_unit")
    private String priceUnit;

    @JsonProperty("phone_number_sid")
    private String phoneNumberSid;

    @JsonProperty("annotation")
    private String annotation;

    @JsonProperty("group_sid")
    private String groupSid;

    @JsonProperty("queue_time")
    private String queueTime;

    @JsonProperty("trunk_sid")
    private String trunkSid;

    @JsonProperty("date_created")
    private String dateCreated;

    @JsonProperty("date_updated")
    private String dateUpdated;

    @JsonProperty("uri")
    private String uri;

    @JsonProperty("subresource_uris")
    private Map<String, String> subresourceUris;

    public String getSid() { return sid; }
    public void setSid(String sid) { this.sid = sid; }
    public String getAccountSid() { return accountSid; }
    public void setAccountSid(String accountSid) { this.accountSid = accountSid; }
    public String getApiVersion() { return apiVersion; }
    public void setApiVersion(String apiVersion) { this.apiVersion = apiVersion; }
    public String getTo() { return to; }
    public void setTo(String to) { this.to = to; }
    public String getToFormatted() { return toFormatted; }
    public void setToFormatted(String toFormatted) { this.toFormatted = toFormatted; }
    public String getFrom() { return from; }
    public void setFrom(String from) { this.from = from; }
    public String getFromFormatted() { return fromFormatted; }
    public void setFromFormatted(String fromFormatted) { this.fromFormatted = fromFormatted; }
    public String getParentCallSid() { return parentCallSid; }
    public void setParentCallSid(String parentCallSid) { this.parentCallSid = parentCallSid; }
    public String getCallerName() { return callerName; }
    public void setCallerName(String callerName) { this.callerName = callerName; }
    public String getForwardedFrom() { return forwardedFrom; }
    public void setForwardedFrom(String forwardedFrom) { this.forwardedFrom = forwardedFrom; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    public String getDirection() { return direction; }
    public void setDirection(String direction) { this.direction = direction; }
    public String getAnsweredBy() { return answeredBy; }
    public void setAnsweredBy(String answeredBy) { this.answeredBy = answeredBy; }
    public String getStartTime() { return startTime; }
    public void setStartTime(String startTime) { this.startTime = startTime; }
    public String getEndTime() { return endTime; }
    public void setEndTime(String endTime) { this.endTime = endTime; }
    public String getDuration() { return duration; }
    public void setDuration(String duration) { this.duration = duration; }
    public String getPrice() { return price; }
    public void setPrice(String price) { this.price = price; }
    public String getPriceUnit() { return priceUnit; }
    public void setPriceUnit(String priceUnit) { this.priceUnit = priceUnit; }
    public String getPhoneNumberSid() { return phoneNumberSid; }
    public void setPhoneNumberSid(String phoneNumberSid) { this.phoneNumberSid = phoneNumberSid; }
    public String getAnnotation() { return annotation; }
    public void setAnnotation(String annotation) { this.annotation = annotation; }
    public String getGroupSid() { return groupSid; }
    public void setGroupSid(String groupSid) { this.groupSid = groupSid; }
    public String getQueueTime() { return queueTime; }
    public void setQueueTime(String queueTime) { this.queueTime = queueTime; }
    public String getTrunkSid() { return trunkSid; }
    public void setTrunkSid(String trunkSid) { this.trunkSid = trunkSid; }
    public String getDateCreated() { return dateCreated; }
    public void setDateCreated(String dateCreated) { this.dateCreated = dateCreated; }
    public String getDateUpdated() { return dateUpdated; }
    public void setDateUpdated(String dateUpdated) { this.dateUpdated = dateUpdated; }
    public String getUri() { return uri; }
    public void setUri(String uri) { this.uri = uri; }
    public Map<String, String> getSubresourceUris() { return subresourceUris; }
    public void setSubresourceUris(Map<String, String> subresourceUris) { this.subresourceUris = subresourceUris; }
}
