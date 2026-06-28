package com.voicetel.voiceml.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Map;

/** Twilio Conversations v1 User. SID is {@code US…}. */
@JsonIgnoreProperties(ignoreUnknown = true)
public class ConversationsV1User {

    @JsonProperty("sid") private String sid;
    @JsonProperty("account_sid") private String accountSid;
    @JsonProperty("chat_service_sid") private String chatServiceSid;
    @JsonProperty("role_sid") private String roleSid;
    @JsonProperty("identity") private String identity;
    @JsonProperty("friendly_name") private String friendlyName;
    @JsonProperty("attributes") private String attributes;
    @JsonProperty("is_online") private Boolean isOnline;
    @JsonProperty("is_notifiable") private Boolean isNotifiable;
    @JsonProperty("date_created") private String dateCreated;
    @JsonProperty("date_updated") private String dateUpdated;
    @JsonProperty("url") private String url;
    @JsonProperty("links") private Map<String, Object> links;

    public String getSid() { return sid; }
    public String getAccountSid() { return accountSid; }
    public String getChatServiceSid() { return chatServiceSid; }
    public String getRoleSid() { return roleSid; }
    public String getIdentity() { return identity; }
    public String getFriendlyName() { return friendlyName; }
    public String getAttributes() { return attributes; }
    public Boolean getIsOnline() { return isOnline; }
    public Boolean getIsNotifiable() { return isNotifiable; }
    public String getDateCreated() { return dateCreated; }
    public String getDateUpdated() { return dateUpdated; }
    public String getUrl() { return url; }
    public Map<String, Object> getLinks() { return links; }

    public void setSid(String v) { this.sid = v; }
    public void setAccountSid(String v) { this.accountSid = v; }
    public void setChatServiceSid(String v) { this.chatServiceSid = v; }
    public void setRoleSid(String v) { this.roleSid = v; }
    public void setIdentity(String v) { this.identity = v; }
    public void setFriendlyName(String v) { this.friendlyName = v; }
    public void setAttributes(String v) { this.attributes = v; }
    public void setIsOnline(Boolean v) { this.isOnline = v; }
    public void setIsNotifiable(Boolean v) { this.isNotifiable = v; }
    public void setDateCreated(String v) { this.dateCreated = v; }
    public void setDateUpdated(String v) { this.dateUpdated = v; }
    public void setUrl(String v) { this.url = v; }
    public void setLinks(Map<String, Object> v) { this.links = v; }
}
