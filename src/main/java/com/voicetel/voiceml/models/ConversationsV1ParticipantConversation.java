package com.voicetel.voiceml.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Map;

/** Cross-conversation lookup row for {@code /v1/ParticipantConversations}. */
@JsonIgnoreProperties(ignoreUnknown = true)
public class ConversationsV1ParticipantConversation {

    @JsonProperty("account_sid") private String accountSid;
    @JsonProperty("chat_service_sid") private String chatServiceSid;
    @JsonProperty("participant_sid") private String participantSid;
    @JsonProperty("participant_user_sid") private String participantUserSid;
    @JsonProperty("participant_identity") private String participantIdentity;
    @JsonProperty("participant_messaging_binding") private Map<String, Object> participantMessagingBinding;
    @JsonProperty("conversation_sid") private String conversationSid;
    @JsonProperty("conversation_unique_name") private String conversationUniqueName;
    @JsonProperty("conversation_friendly_name") private String conversationFriendlyName;
    @JsonProperty("conversation_attributes") private String conversationAttributes;
    @JsonProperty("conversation_date_created") private String conversationDateCreated;
    @JsonProperty("conversation_date_updated") private String conversationDateUpdated;
    @JsonProperty("conversation_created_by") private String conversationCreatedBy;
    @JsonProperty("conversation_state") private String conversationState;
    @JsonProperty("conversation_timers") private Map<String, Object> conversationTimers;
    @JsonProperty("links") private Map<String, Object> links;

    public String getAccountSid() { return accountSid; }
    public String getChatServiceSid() { return chatServiceSid; }
    public String getParticipantSid() { return participantSid; }
    public String getParticipantUserSid() { return participantUserSid; }
    public String getParticipantIdentity() { return participantIdentity; }
    public Map<String, Object> getParticipantMessagingBinding() { return participantMessagingBinding; }
    public String getConversationSid() { return conversationSid; }
    public String getConversationUniqueName() { return conversationUniqueName; }
    public String getConversationFriendlyName() { return conversationFriendlyName; }
    public String getConversationAttributes() { return conversationAttributes; }
    public String getConversationDateCreated() { return conversationDateCreated; }
    public String getConversationDateUpdated() { return conversationDateUpdated; }
    public String getConversationCreatedBy() { return conversationCreatedBy; }
    public String getConversationState() { return conversationState; }
    public Map<String, Object> getConversationTimers() { return conversationTimers; }
    public Map<String, Object> getLinks() { return links; }

    public void setAccountSid(String v) { this.accountSid = v; }
    public void setChatServiceSid(String v) { this.chatServiceSid = v; }
    public void setParticipantSid(String v) { this.participantSid = v; }
    public void setParticipantUserSid(String v) { this.participantUserSid = v; }
    public void setParticipantIdentity(String v) { this.participantIdentity = v; }
    public void setParticipantMessagingBinding(Map<String, Object> v) { this.participantMessagingBinding = v; }
    public void setConversationSid(String v) { this.conversationSid = v; }
    public void setConversationUniqueName(String v) { this.conversationUniqueName = v; }
    public void setConversationFriendlyName(String v) { this.conversationFriendlyName = v; }
    public void setConversationAttributes(String v) { this.conversationAttributes = v; }
    public void setConversationDateCreated(String v) { this.conversationDateCreated = v; }
    public void setConversationDateUpdated(String v) { this.conversationDateUpdated = v; }
    public void setConversationCreatedBy(String v) { this.conversationCreatedBy = v; }
    public void setConversationState(String v) { this.conversationState = v; }
    public void setConversationTimers(Map<String, Object> v) { this.conversationTimers = v; }
    public void setLinks(Map<String, Object> v) { this.links = v; }
}
