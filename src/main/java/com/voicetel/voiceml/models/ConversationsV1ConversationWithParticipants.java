package com.voicetel.voiceml.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Map;

/**
 * Response shape for {@code POST /v1/ConversationWithParticipants} — same
 * field set as {@link ConversationsV1Conversation} (separate type so the
 * server can drift independently).
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class ConversationsV1ConversationWithParticipants {

    @JsonProperty("account_sid") private String accountSid;
    @JsonProperty("chat_service_sid") private String chatServiceSid;
    @JsonProperty("messaging_service_sid") private String messagingServiceSid;
    @JsonProperty("sid") private String sid;
    @JsonProperty("friendly_name") private String friendlyName;
    @JsonProperty("unique_name") private String uniqueName;
    @JsonProperty("attributes") private String attributes;
    @JsonProperty("state") private String state;
    @JsonProperty("date_created") private String dateCreated;
    @JsonProperty("date_updated") private String dateUpdated;
    @JsonProperty("timers") private Map<String, Object> timers;
    @JsonProperty("links") private Map<String, Object> links;
    @JsonProperty("bindings") private Map<String, Object> bindings;
    @JsonProperty("url") private String url;

    public String getAccountSid() { return accountSid; }
    public String getChatServiceSid() { return chatServiceSid; }
    public String getMessagingServiceSid() { return messagingServiceSid; }
    public String getSid() { return sid; }
    public String getFriendlyName() { return friendlyName; }
    public String getUniqueName() { return uniqueName; }
    public String getAttributes() { return attributes; }
    public String getState() { return state; }
    public String getDateCreated() { return dateCreated; }
    public String getDateUpdated() { return dateUpdated; }
    public Map<String, Object> getTimers() { return timers; }
    public Map<String, Object> getLinks() { return links; }
    public Map<String, Object> getBindings() { return bindings; }
    public String getUrl() { return url; }

    public void setAccountSid(String v) { this.accountSid = v; }
    public void setChatServiceSid(String v) { this.chatServiceSid = v; }
    public void setMessagingServiceSid(String v) { this.messagingServiceSid = v; }
    public void setSid(String v) { this.sid = v; }
    public void setFriendlyName(String v) { this.friendlyName = v; }
    public void setUniqueName(String v) { this.uniqueName = v; }
    public void setAttributes(String v) { this.attributes = v; }
    public void setState(String v) { this.state = v; }
    public void setDateCreated(String v) { this.dateCreated = v; }
    public void setDateUpdated(String v) { this.dateUpdated = v; }
    public void setTimers(Map<String, Object> v) { this.timers = v; }
    public void setLinks(Map<String, Object> v) { this.links = v; }
    public void setBindings(Map<String, Object> v) { this.bindings = v; }
    public void setUrl(String v) { this.url = v; }
}
