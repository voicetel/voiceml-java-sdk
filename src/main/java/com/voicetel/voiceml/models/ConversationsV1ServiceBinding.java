package com.voicetel.voiceml.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

/** Service-scoped push Binding. SID is {@code BS…}. */
@JsonIgnoreProperties(ignoreUnknown = true)
public class ConversationsV1ServiceBinding {

    @JsonProperty("sid") private String sid;
    @JsonProperty("account_sid") private String accountSid;
    @JsonProperty("chat_service_sid") private String chatServiceSid;
    @JsonProperty("credential_sid") private String credentialSid;
    @JsonProperty("date_created") private String dateCreated;
    @JsonProperty("date_updated") private String dateUpdated;
    @JsonProperty("endpoint") private String endpoint;
    @JsonProperty("identity") private String identity;
    @JsonProperty("binding_type") private String bindingType;
    @JsonProperty("message_types") private List<String> messageTypes;
    @JsonProperty("url") private String url;

    public String getSid() { return sid; }
    public String getAccountSid() { return accountSid; }
    public String getChatServiceSid() { return chatServiceSid; }
    public String getCredentialSid() { return credentialSid; }
    public String getDateCreated() { return dateCreated; }
    public String getDateUpdated() { return dateUpdated; }
    public String getEndpoint() { return endpoint; }
    public String getIdentity() { return identity; }
    public String getBindingType() { return bindingType; }
    public List<String> getMessageTypes() { return messageTypes; }
    public String getUrl() { return url; }

    public void setSid(String v) { this.sid = v; }
    public void setAccountSid(String v) { this.accountSid = v; }
    public void setChatServiceSid(String v) { this.chatServiceSid = v; }
    public void setCredentialSid(String v) { this.credentialSid = v; }
    public void setDateCreated(String v) { this.dateCreated = v; }
    public void setDateUpdated(String v) { this.dateUpdated = v; }
    public void setEndpoint(String v) { this.endpoint = v; }
    public void setIdentity(String v) { this.identity = v; }
    public void setBindingType(String v) { this.bindingType = v; }
    public void setMessageTypes(List<String> v) { this.messageTypes = v; }
    public void setUrl(String v) { this.url = v; }
}
