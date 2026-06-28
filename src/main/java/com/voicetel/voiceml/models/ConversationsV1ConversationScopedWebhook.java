package com.voicetel.voiceml.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Map;

/** A webhook scoped to a single conversation. SID is {@code WH…}. */
@JsonIgnoreProperties(ignoreUnknown = true)
public class ConversationsV1ConversationScopedWebhook {

    @JsonProperty("sid") private String sid;
    @JsonProperty("account_sid") private String accountSid;
    @JsonProperty("conversation_sid") private String conversationSid;
    @JsonProperty("target") private String target;
    @JsonProperty("url") private String url;
    @JsonProperty("configuration") private Map<String, Object> configuration;
    @JsonProperty("date_created") private String dateCreated;
    @JsonProperty("date_updated") private String dateUpdated;

    public String getSid() { return sid; }
    public String getAccountSid() { return accountSid; }
    public String getConversationSid() { return conversationSid; }
    public String getTarget() { return target; }
    public String getUrl() { return url; }
    public Map<String, Object> getConfiguration() { return configuration; }
    public String getDateCreated() { return dateCreated; }
    public String getDateUpdated() { return dateUpdated; }

    public void setSid(String v) { this.sid = v; }
    public void setAccountSid(String v) { this.accountSid = v; }
    public void setConversationSid(String v) { this.conversationSid = v; }
    public void setTarget(String v) { this.target = v; }
    public void setUrl(String v) { this.url = v; }
    public void setConfiguration(Map<String, Object> v) { this.configuration = v; }
    public void setDateCreated(String v) { this.dateCreated = v; }
    public void setDateUpdated(String v) { this.dateUpdated = v; }
}
