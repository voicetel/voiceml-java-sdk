package com.voicetel.voiceml.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Map;

/** Service-scoped user's view of a conversation. */
@JsonIgnoreProperties(ignoreUnknown = true)
public class ConversationsV1ServiceUserConversation {

    @JsonProperty("account_sid") private String accountSid;
    @JsonProperty("chat_service_sid") private String chatServiceSid;
    @JsonProperty("conversation_sid") private String conversationSid;
    @JsonProperty("unread_messages_count") private Integer unreadMessagesCount;
    @JsonProperty("last_read_message_index") private Integer lastReadMessageIndex;
    @JsonProperty("participant_sid") private String participantSid;
    @JsonProperty("user_sid") private String userSid;
    @JsonProperty("friendly_name") private String friendlyName;
    @JsonProperty("conversation_state") private String conversationState;
    @JsonProperty("timers") private Map<String, Object> timers;
    @JsonProperty("attributes") private String attributes;
    @JsonProperty("date_created") private String dateCreated;
    @JsonProperty("date_updated") private String dateUpdated;
    @JsonProperty("created_by") private String createdBy;
    @JsonProperty("notification_level") private String notificationLevel;
    @JsonProperty("unique_name") private String uniqueName;
    @JsonProperty("url") private String url;
    @JsonProperty("links") private Map<String, Object> links;

    public String getAccountSid() { return accountSid; }
    public String getChatServiceSid() { return chatServiceSid; }
    public String getConversationSid() { return conversationSid; }
    public Integer getUnreadMessagesCount() { return unreadMessagesCount; }
    public Integer getLastReadMessageIndex() { return lastReadMessageIndex; }
    public String getParticipantSid() { return participantSid; }
    public String getUserSid() { return userSid; }
    public String getFriendlyName() { return friendlyName; }
    public String getConversationState() { return conversationState; }
    public Map<String, Object> getTimers() { return timers; }
    public String getAttributes() { return attributes; }
    public String getDateCreated() { return dateCreated; }
    public String getDateUpdated() { return dateUpdated; }
    public String getCreatedBy() { return createdBy; }
    public String getNotificationLevel() { return notificationLevel; }
    public String getUniqueName() { return uniqueName; }
    public String getUrl() { return url; }
    public Map<String, Object> getLinks() { return links; }

    public void setAccountSid(String v) { this.accountSid = v; }
    public void setChatServiceSid(String v) { this.chatServiceSid = v; }
    public void setConversationSid(String v) { this.conversationSid = v; }
    public void setUnreadMessagesCount(Integer v) { this.unreadMessagesCount = v; }
    public void setLastReadMessageIndex(Integer v) { this.lastReadMessageIndex = v; }
    public void setParticipantSid(String v) { this.participantSid = v; }
    public void setUserSid(String v) { this.userSid = v; }
    public void setFriendlyName(String v) { this.friendlyName = v; }
    public void setConversationState(String v) { this.conversationState = v; }
    public void setTimers(Map<String, Object> v) { this.timers = v; }
    public void setAttributes(String v) { this.attributes = v; }
    public void setDateCreated(String v) { this.dateCreated = v; }
    public void setDateUpdated(String v) { this.dateUpdated = v; }
    public void setCreatedBy(String v) { this.createdBy = v; }
    public void setNotificationLevel(String v) { this.notificationLevel = v; }
    public void setUniqueName(String v) { this.uniqueName = v; }
    public void setUrl(String v) { this.url = v; }
    public void setLinks(Map<String, Object> v) { this.links = v; }
}
