package com.voicetel.voiceml.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/** Twilio Assistants v1 Session. */
@JsonIgnoreProperties(ignoreUnknown = true)
public class AssistantsV1Session {

    @JsonProperty("id") private String id;
    @JsonProperty("account_sid") private String accountSid;
    @JsonProperty("assistant_id") private String assistantId;
    @JsonProperty("verified") private Boolean verified;
    @JsonProperty("identity") private String identity;
    @JsonProperty("date_created") private String dateCreated;
    @JsonProperty("date_updated") private String dateUpdated;

    public String getId() { return id; }
    public String getAccountSid() { return accountSid; }
    public String getAssistantId() { return assistantId; }
    public Boolean getVerified() { return verified; }
    public String getIdentity() { return identity; }
    public String getDateCreated() { return dateCreated; }
    public String getDateUpdated() { return dateUpdated; }

    public void setId(String v) { this.id = v; }
    public void setAccountSid(String v) { this.accountSid = v; }
    public void setAssistantId(String v) { this.assistantId = v; }
    public void setVerified(Boolean v) { this.verified = v; }
    public void setIdentity(String v) { this.identity = v; }
    public void setDateCreated(String v) { this.dateCreated = v; }
    public void setDateUpdated(String v) { this.dateUpdated = v; }
}
