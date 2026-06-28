package com.voicetel.voiceml.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/** Per-channel delivery receipt for a Conversation Message. SID is {@code DY…}. */
@JsonIgnoreProperties(ignoreUnknown = true)
public class ConversationsV1ConversationMessageReceipt {

    @JsonProperty("account_sid") private String accountSid;
    @JsonProperty("conversation_sid") private String conversationSid;
    @JsonProperty("sid") private String sid;
    @JsonProperty("message_sid") private String messageSid;
    @JsonProperty("channel_message_sid") private String channelMessageSid;
    @JsonProperty("participant_sid") private String participantSid;
    @JsonProperty("status") private String status;
    @JsonProperty("error_code") private Integer errorCode;
    @JsonProperty("date_created") private String dateCreated;
    @JsonProperty("date_updated") private String dateUpdated;
    @JsonProperty("url") private String url;

    public String getAccountSid() { return accountSid; }
    public String getConversationSid() { return conversationSid; }
    public String getSid() { return sid; }
    public String getMessageSid() { return messageSid; }
    public String getChannelMessageSid() { return channelMessageSid; }
    public String getParticipantSid() { return participantSid; }
    public String getStatus() { return status; }
    public Integer getErrorCode() { return errorCode; }
    public String getDateCreated() { return dateCreated; }
    public String getDateUpdated() { return dateUpdated; }
    public String getUrl() { return url; }

    public void setAccountSid(String v) { this.accountSid = v; }
    public void setConversationSid(String v) { this.conversationSid = v; }
    public void setSid(String v) { this.sid = v; }
    public void setMessageSid(String v) { this.messageSid = v; }
    public void setChannelMessageSid(String v) { this.channelMessageSid = v; }
    public void setParticipantSid(String v) { this.participantSid = v; }
    public void setStatus(String v) { this.status = v; }
    public void setErrorCode(Integer v) { this.errorCode = v; }
    public void setDateCreated(String v) { this.dateCreated = v; }
    public void setDateUpdated(String v) { this.dateUpdated = v; }
    public void setUrl(String v) { this.url = v; }
}
