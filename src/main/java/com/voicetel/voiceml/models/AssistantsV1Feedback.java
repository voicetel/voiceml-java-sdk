package com.voicetel.voiceml.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/** Twilio Assistants v1 Feedback. Prefixed ID is {@code aia_fdbk_…}. */
@JsonIgnoreProperties(ignoreUnknown = true)
public class AssistantsV1Feedback {

    @JsonProperty("assistant_id") private String assistantId;
    @JsonProperty("id") private String id;
    @JsonProperty("account_sid") private String accountSid;
    @JsonProperty("user_sid") private String userSid;
    @JsonProperty("message_id") private String messageId;
    @JsonProperty("score") private Float score;
    @JsonProperty("session_id") private String sessionId;
    @JsonProperty("text") private String text;
    @JsonProperty("date_created") private String dateCreated;
    @JsonProperty("date_updated") private String dateUpdated;

    public String getAssistantId() { return assistantId; }
    public String getId() { return id; }
    public String getAccountSid() { return accountSid; }
    public String getUserSid() { return userSid; }
    public String getMessageId() { return messageId; }
    public Float getScore() { return score; }
    public String getSessionId() { return sessionId; }
    public String getText() { return text; }
    public String getDateCreated() { return dateCreated; }
    public String getDateUpdated() { return dateUpdated; }

    public void setAssistantId(String v) { this.assistantId = v; }
    public void setId(String v) { this.id = v; }
    public void setAccountSid(String v) { this.accountSid = v; }
    public void setUserSid(String v) { this.userSid = v; }
    public void setMessageId(String v) { this.messageId = v; }
    public void setScore(Float v) { this.score = v; }
    public void setSessionId(String v) { this.sessionId = v; }
    public void setText(String v) { this.text = v; }
    public void setDateCreated(String v) { this.dateCreated = v; }
    public void setDateUpdated(String v) { this.dateUpdated = v; }
}
