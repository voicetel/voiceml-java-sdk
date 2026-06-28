package com.voicetel.voiceml.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Map;

/** Per-service Configuration singleton. */
@JsonIgnoreProperties(ignoreUnknown = true)
public class ConversationsV1ServiceConfiguration {

    @JsonProperty("chat_service_sid") private String chatServiceSid;
    @JsonProperty("default_conversation_creator_role_sid") private String defaultConversationCreatorRoleSid;
    @JsonProperty("default_conversation_role_sid") private String defaultConversationRoleSid;
    @JsonProperty("default_chat_service_role_sid") private String defaultChatServiceRoleSid;
    @JsonProperty("url") private String url;
    @JsonProperty("links") private Map<String, Object> links;
    @JsonProperty("reachability_enabled") private Boolean reachabilityEnabled;

    public String getChatServiceSid() { return chatServiceSid; }
    public String getDefaultConversationCreatorRoleSid() { return defaultConversationCreatorRoleSid; }
    public String getDefaultConversationRoleSid() { return defaultConversationRoleSid; }
    public String getDefaultChatServiceRoleSid() { return defaultChatServiceRoleSid; }
    public String getUrl() { return url; }
    public Map<String, Object> getLinks() { return links; }
    public Boolean getReachabilityEnabled() { return reachabilityEnabled; }

    public void setChatServiceSid(String v) { this.chatServiceSid = v; }
    public void setDefaultConversationCreatorRoleSid(String v) { this.defaultConversationCreatorRoleSid = v; }
    public void setDefaultConversationRoleSid(String v) { this.defaultConversationRoleSid = v; }
    public void setDefaultChatServiceRoleSid(String v) { this.defaultChatServiceRoleSid = v; }
    public void setUrl(String v) { this.url = v; }
    public void setLinks(Map<String, Object> v) { this.links = v; }
    public void setReachabilityEnabled(Boolean v) { this.reachabilityEnabled = v; }
}
