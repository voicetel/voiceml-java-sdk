package com.voicetel.voiceml.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Map;

/** Account-global Conversations Configuration. */
@JsonIgnoreProperties(ignoreUnknown = true)
public class ConversationsV1Configuration {

    @JsonProperty("account_sid") private String accountSid;
    @JsonProperty("default_chat_service_sid") private String defaultChatServiceSid;
    @JsonProperty("default_messaging_service_sid") private String defaultMessagingServiceSid;
    @JsonProperty("default_inactive_timer") private String defaultInactiveTimer;
    @JsonProperty("default_closed_timer") private String defaultClosedTimer;
    @JsonProperty("url") private String url;
    @JsonProperty("links") private Map<String, Object> links;

    public String getAccountSid() { return accountSid; }
    public String getDefaultChatServiceSid() { return defaultChatServiceSid; }
    public String getDefaultMessagingServiceSid() { return defaultMessagingServiceSid; }
    public String getDefaultInactiveTimer() { return defaultInactiveTimer; }
    public String getDefaultClosedTimer() { return defaultClosedTimer; }
    public String getUrl() { return url; }
    public Map<String, Object> getLinks() { return links; }

    public void setAccountSid(String v) { this.accountSid = v; }
    public void setDefaultChatServiceSid(String v) { this.defaultChatServiceSid = v; }
    public void setDefaultMessagingServiceSid(String v) { this.defaultMessagingServiceSid = v; }
    public void setDefaultInactiveTimer(String v) { this.defaultInactiveTimer = v; }
    public void setDefaultClosedTimer(String v) { this.defaultClosedTimer = v; }
    public void setUrl(String v) { this.url = v; }
    public void setLinks(Map<String, Object> v) { this.links = v; }
}
