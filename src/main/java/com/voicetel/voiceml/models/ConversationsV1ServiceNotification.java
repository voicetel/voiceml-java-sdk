package com.voicetel.voiceml.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Map;

/** Per-service push Notification configuration. */
@JsonIgnoreProperties(ignoreUnknown = true)
public class ConversationsV1ServiceNotification {

    @JsonProperty("account_sid") private String accountSid;
    @JsonProperty("chat_service_sid") private String chatServiceSid;
    @JsonProperty("new_message") private Map<String, Object> newMessage;
    @JsonProperty("added_to_conversation") private Map<String, Object> addedToConversation;
    @JsonProperty("removed_from_conversation") private Map<String, Object> removedFromConversation;
    @JsonProperty("log_enabled") private Boolean logEnabled;
    @JsonProperty("url") private String url;

    public String getAccountSid() { return accountSid; }
    public String getChatServiceSid() { return chatServiceSid; }
    public Map<String, Object> getNewMessage() { return newMessage; }
    public Map<String, Object> getAddedToConversation() { return addedToConversation; }
    public Map<String, Object> getRemovedFromConversation() { return removedFromConversation; }
    public Boolean getLogEnabled() { return logEnabled; }
    public String getUrl() { return url; }

    public void setAccountSid(String v) { this.accountSid = v; }
    public void setChatServiceSid(String v) { this.chatServiceSid = v; }
    public void setNewMessage(Map<String, Object> v) { this.newMessage = v; }
    public void setAddedToConversation(Map<String, Object> v) { this.addedToConversation = v; }
    public void setRemovedFromConversation(Map<String, Object> v) { this.removedFromConversation = v; }
    public void setLogEnabled(Boolean v) { this.logEnabled = v; }
    public void setUrl(String v) { this.url = v; }
}
