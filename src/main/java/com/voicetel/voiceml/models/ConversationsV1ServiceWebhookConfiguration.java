package com.voicetel.voiceml.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

/** Per-service Webhook configuration. */
@JsonIgnoreProperties(ignoreUnknown = true)
public class ConversationsV1ServiceWebhookConfiguration {

    @JsonProperty("account_sid") private String accountSid;
    @JsonProperty("chat_service_sid") private String chatServiceSid;
    @JsonProperty("pre_webhook_url") private String preWebhookUrl;
    @JsonProperty("post_webhook_url") private String postWebhookUrl;
    @JsonProperty("filters") private List<String> filters;
    @JsonProperty("method") private String method;
    @JsonProperty("url") private String url;

    public String getAccountSid() { return accountSid; }
    public String getChatServiceSid() { return chatServiceSid; }
    public String getPreWebhookUrl() { return preWebhookUrl; }
    public String getPostWebhookUrl() { return postWebhookUrl; }
    public List<String> getFilters() { return filters; }
    public String getMethod() { return method; }
    public String getUrl() { return url; }

    public void setAccountSid(String v) { this.accountSid = v; }
    public void setChatServiceSid(String v) { this.chatServiceSid = v; }
    public void setPreWebhookUrl(String v) { this.preWebhookUrl = v; }
    public void setPostWebhookUrl(String v) { this.postWebhookUrl = v; }
    public void setFilters(List<String> v) { this.filters = v; }
    public void setMethod(String v) { this.method = v; }
    public void setUrl(String v) { this.url = v; }
}
