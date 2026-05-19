package com.voicetel.voiceml.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Map;

/** A Twilio-shape Conference resource. */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Conference {

    @JsonProperty("sid")
    private String sid;

    @JsonProperty("account_sid")
    private String accountSid;

    @JsonProperty("friendly_name")
    private String friendlyName;

    @JsonProperty("status")
    private String status;

    @JsonProperty("region")
    private String region;

    @JsonProperty("api_version")
    private String apiVersion;

    @JsonProperty("uri")
    private String uri;

    @JsonProperty("date_created")
    private String dateCreated;

    @JsonProperty("date_updated")
    private String dateUpdated;

    @JsonProperty("reason_conference_ended")
    private String reasonConferenceEnded;

    @JsonProperty("call_sid_ending_conference")
    private String callSidEndingConference;

    @JsonProperty("subresource_uris")
    private Map<String, String> subresourceUris;

    @JsonProperty("member_count")
    private Integer memberCount;

    public String getSid() { return sid; }
    public void setSid(String sid) { this.sid = sid; }
    public String getAccountSid() { return accountSid; }
    public void setAccountSid(String accountSid) { this.accountSid = accountSid; }
    public String getFriendlyName() { return friendlyName; }
    public void setFriendlyName(String friendlyName) { this.friendlyName = friendlyName; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    public String getRegion() { return region; }
    public void setRegion(String region) { this.region = region; }
    public String getApiVersion() { return apiVersion; }
    public void setApiVersion(String apiVersion) { this.apiVersion = apiVersion; }
    public String getUri() { return uri; }
    public void setUri(String uri) { this.uri = uri; }
    public String getDateCreated() { return dateCreated; }
    public void setDateCreated(String dateCreated) { this.dateCreated = dateCreated; }
    public String getDateUpdated() { return dateUpdated; }
    public void setDateUpdated(String dateUpdated) { this.dateUpdated = dateUpdated; }
    public String getReasonConferenceEnded() { return reasonConferenceEnded; }
    public void setReasonConferenceEnded(String reasonConferenceEnded) { this.reasonConferenceEnded = reasonConferenceEnded; }
    public String getCallSidEndingConference() { return callSidEndingConference; }
    public void setCallSidEndingConference(String callSidEndingConference) { this.callSidEndingConference = callSidEndingConference; }
    public Map<String, String> getSubresourceUris() { return subresourceUris; }
    public void setSubresourceUris(Map<String, String> subresourceUris) { this.subresourceUris = subresourceUris; }
    public Integer getMemberCount() { return memberCount; }
    public void setMemberCount(Integer memberCount) { this.memberCount = memberCount; }
}
