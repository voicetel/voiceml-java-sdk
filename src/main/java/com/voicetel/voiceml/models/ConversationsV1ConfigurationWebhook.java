package com.voicetel.voiceml.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

/** Account-global Conversations webhook config. */
@JsonIgnoreProperties(ignoreUnknown = true)
public class ConversationsV1ConfigurationWebhook {

    @JsonProperty("account_sid") private String accountSid;
    @JsonProperty("method") private String method;
    @JsonProperty("filters") private List<String> filters;
    @JsonProperty("pre_webhook_url") private String preWebhookUrl;
    @JsonProperty("post_webhook_url") private String postWebhookUrl;
    @JsonProperty("target") private String target;
    @JsonProperty("url") private String url;

    public String getAccountSid() { return accountSid; }
    public String getMethod() { return method; }
    public List<String> getFilters() { return filters; }
    public String getPreWebhookUrl() { return preWebhookUrl; }
    public String getPostWebhookUrl() { return postWebhookUrl; }
    public String getTarget() { return target; }
    public String getUrl() { return url; }

    public void setAccountSid(String v) { this.accountSid = v; }
    public void setMethod(String v) { this.method = v; }
    public void setFilters(List<String> v) { this.filters = v; }
    public void setPreWebhookUrl(String v) { this.preWebhookUrl = v; }
    public void setPostWebhookUrl(String v) { this.postWebhookUrl = v; }
    public void setTarget(String v) { this.target = v; }
    public void setUrl(String v) { this.url = v; }
}
