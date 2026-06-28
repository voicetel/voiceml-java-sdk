package com.voicetel.voiceml.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * A weighted SIP URI target attached to a {@link VoiceV1ConnectionPolicy}.
 * SID is {@code NE…}.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class VoiceV1ConnectionPolicyTarget {

    @JsonProperty("account_sid") private String accountSid;
    @JsonProperty("connection_policy_sid") private String connectionPolicySid;
    @JsonProperty("sid") private String sid;
    @JsonProperty("friendly_name") private String friendlyName;
    @JsonProperty("target") private String target;
    @JsonProperty("priority") private Integer priority;
    @JsonProperty("weight") private Integer weight;
    @JsonProperty("enabled") private Boolean enabled;
    @JsonProperty("date_created") private String dateCreated;
    @JsonProperty("date_updated") private String dateUpdated;
    @JsonProperty("url") private String url;

    public String getAccountSid() { return accountSid; }
    public String getConnectionPolicySid() { return connectionPolicySid; }
    public String getSid() { return sid; }
    public String getFriendlyName() { return friendlyName; }
    public String getTarget() { return target; }
    public Integer getPriority() { return priority; }
    public Integer getWeight() { return weight; }
    public Boolean getEnabled() { return enabled; }
    public String getDateCreated() { return dateCreated; }
    public String getDateUpdated() { return dateUpdated; }
    public String getUrl() { return url; }

    public void setAccountSid(String v) { this.accountSid = v; }
    public void setConnectionPolicySid(String v) { this.connectionPolicySid = v; }
    public void setSid(String v) { this.sid = v; }
    public void setFriendlyName(String v) { this.friendlyName = v; }
    public void setTarget(String v) { this.target = v; }
    public void setPriority(Integer v) { this.priority = v; }
    public void setWeight(Integer v) { this.weight = v; }
    public void setEnabled(Boolean v) { this.enabled = v; }
    public void setDateCreated(String v) { this.dateCreated = v; }
    public void setDateUpdated(String v) { this.dateUpdated = v; }
    public void setUrl(String v) { this.url = v; }
}
