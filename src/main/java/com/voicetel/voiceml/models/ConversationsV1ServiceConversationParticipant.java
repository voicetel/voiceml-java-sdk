package com.voicetel.voiceml.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Map;

/** Service-scoped Participant. SID is {@code MB…}. */
@JsonIgnoreProperties(ignoreUnknown = true)
public class ConversationsV1ServiceConversationParticipant {

    @JsonProperty("account_sid") private String accountSid;
    @JsonProperty("chat_service_sid") private String chatServiceSid;
    @JsonProperty("conversation_sid") private String conversationSid;
    @JsonProperty("sid") private String sid;
    @JsonProperty("identity") private String identity;
    @JsonProperty("attributes") private String attributes;
    @JsonProperty("messaging_binding") private Map<String, Object> messagingBinding;
    @JsonProperty("role_sid") private String roleSid;
    @JsonProperty("date_created") private String dateCreated;
    @JsonProperty("date_updated") private String dateUpdated;
    @JsonProperty("url") private String url;
    @JsonProperty("last_read_message_index") private Integer lastReadMessageIndex;
    @JsonProperty("last_read_timestamp") private String lastReadTimestamp;

    public String getAccountSid() { return accountSid; }
    public String getChatServiceSid() { return chatServiceSid; }
    public String getConversationSid() { return conversationSid; }
    public String getSid() { return sid; }
    public String getIdentity() { return identity; }
    public String getAttributes() { return attributes; }
    public Map<String, Object> getMessagingBinding() { return messagingBinding; }
    public String getRoleSid() { return roleSid; }
    public String getDateCreated() { return dateCreated; }
    public String getDateUpdated() { return dateUpdated; }
    public String getUrl() { return url; }
    public Integer getLastReadMessageIndex() { return lastReadMessageIndex; }
    public String getLastReadTimestamp() { return lastReadTimestamp; }

    public void setAccountSid(String v) { this.accountSid = v; }
    public void setChatServiceSid(String v) { this.chatServiceSid = v; }
    public void setConversationSid(String v) { this.conversationSid = v; }
    public void setSid(String v) { this.sid = v; }
    public void setIdentity(String v) { this.identity = v; }
    public void setAttributes(String v) { this.attributes = v; }
    public void setMessagingBinding(Map<String, Object> v) { this.messagingBinding = v; }
    public void setRoleSid(String v) { this.roleSid = v; }
    public void setDateCreated(String v) { this.dateCreated = v; }
    public void setDateUpdated(String v) { this.dateUpdated = v; }
    public void setUrl(String v) { this.url = v; }
    public void setLastReadMessageIndex(Integer v) { this.lastReadMessageIndex = v; }
    public void setLastReadTimestamp(String v) { this.lastReadTimestamp = v; }
}
