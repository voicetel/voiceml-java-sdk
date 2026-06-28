package com.voicetel.voiceml.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

/** Twilio Conversations v1 Role. SID is {@code RL…}. */
@JsonIgnoreProperties(ignoreUnknown = true)
public class ConversationsV1Role {

    @JsonProperty("sid") private String sid;
    @JsonProperty("account_sid") private String accountSid;
    @JsonProperty("chat_service_sid") private String chatServiceSid;
    @JsonProperty("friendly_name") private String friendlyName;
    @JsonProperty("type") private String type;
    @JsonProperty("permissions") private List<String> permissions;
    @JsonProperty("date_created") private String dateCreated;
    @JsonProperty("date_updated") private String dateUpdated;
    @JsonProperty("url") private String url;

    public String getSid() { return sid; }
    public String getAccountSid() { return accountSid; }
    public String getChatServiceSid() { return chatServiceSid; }
    public String getFriendlyName() { return friendlyName; }
    public String getType() { return type; }
    public List<String> getPermissions() { return permissions; }
    public String getDateCreated() { return dateCreated; }
    public String getDateUpdated() { return dateUpdated; }
    public String getUrl() { return url; }

    public void setSid(String v) { this.sid = v; }
    public void setAccountSid(String v) { this.accountSid = v; }
    public void setChatServiceSid(String v) { this.chatServiceSid = v; }
    public void setFriendlyName(String v) { this.friendlyName = v; }
    public void setType(String v) { this.type = v; }
    public void setPermissions(List<String> v) { this.permissions = v; }
    public void setDateCreated(String v) { this.dateCreated = v; }
    public void setDateUpdated(String v) { this.dateUpdated = v; }
    public void setUrl(String v) { this.url = v; }
}
