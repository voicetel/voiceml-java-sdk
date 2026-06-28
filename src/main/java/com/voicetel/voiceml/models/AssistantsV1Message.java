package com.voicetel.voiceml.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Map;

/** Twilio Assistants v1 Message. Prefixed ID is {@code aia_msg_…}. */
@JsonIgnoreProperties(ignoreUnknown = true)
public class AssistantsV1Message {

    @JsonProperty("id") private String id;
    @JsonProperty("account_sid") private String accountSid;
    @JsonProperty("assistant_id") private String assistantId;
    @JsonProperty("session_id") private String sessionId;
    @JsonProperty("identity") private String identity;
    @JsonProperty("role") private String role;
    @JsonProperty("content") private Map<String, Object> content;
    @JsonProperty("meta") private Map<String, Object> meta;
    @JsonProperty("date_created") private String dateCreated;
    @JsonProperty("date_updated") private String dateUpdated;

    public String getId() { return id; }
    public String getAccountSid() { return accountSid; }
    public String getAssistantId() { return assistantId; }
    public String getSessionId() { return sessionId; }
    public String getIdentity() { return identity; }
    public String getRole() { return role; }
    public Map<String, Object> getContent() { return content; }
    public Map<String, Object> getMeta() { return meta; }
    public String getDateCreated() { return dateCreated; }
    public String getDateUpdated() { return dateUpdated; }

    public void setId(String v) { this.id = v; }
    public void setAccountSid(String v) { this.accountSid = v; }
    public void setAssistantId(String v) { this.assistantId = v; }
    public void setSessionId(String v) { this.sessionId = v; }
    public void setIdentity(String v) { this.identity = v; }
    public void setRole(String v) { this.role = v; }
    public void setContent(Map<String, Object> v) { this.content = v; }
    public void setMeta(Map<String, Object> v) { this.meta = v; }
    public void setDateCreated(String v) { this.dateCreated = v; }
    public void setDateUpdated(String v) { this.dateUpdated = v; }
}
