package com.voicetel.voiceml.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Map;

/** Twilio Assistants v1 Policy. Prefixed ID is {@code aia_plcy_…}. */
@JsonIgnoreProperties(ignoreUnknown = true)
public class AssistantsV1Policy {

    @JsonProperty("id") private String id;
    @JsonProperty("name") private String name;
    @JsonProperty("description") private String description;
    @JsonProperty("account_sid") private String accountSid;
    @JsonProperty("user_sid") private String userSid;
    @JsonProperty("type") private String type;
    @JsonProperty("policy_details") private Map<String, Object> policyDetails;
    @JsonProperty("date_created") private String dateCreated;
    @JsonProperty("date_updated") private String dateUpdated;

    public String getId() { return id; }
    public String getName() { return name; }
    public String getDescription() { return description; }
    public String getAccountSid() { return accountSid; }
    public String getUserSid() { return userSid; }
    public String getType() { return type; }
    public Map<String, Object> getPolicyDetails() { return policyDetails; }
    public String getDateCreated() { return dateCreated; }
    public String getDateUpdated() { return dateUpdated; }

    public void setId(String v) { this.id = v; }
    public void setName(String v) { this.name = v; }
    public void setDescription(String v) { this.description = v; }
    public void setAccountSid(String v) { this.accountSid = v; }
    public void setUserSid(String v) { this.userSid = v; }
    public void setType(String v) { this.type = v; }
    public void setPolicyDetails(Map<String, Object> v) { this.policyDetails = v; }
    public void setDateCreated(String v) { this.dateCreated = v; }
    public void setDateUpdated(String v) { this.dateUpdated = v; }
}
