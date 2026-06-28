package com.voicetel.voiceml.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;
import java.util.Map;

/** Service-scoped Conversation Message. SID is {@code IM…}. */
@JsonIgnoreProperties(ignoreUnknown = true)
public class ConversationsV1ServiceConversationMessage {

    @JsonProperty("account_sid") private String accountSid;
    @JsonProperty("chat_service_sid") private String chatServiceSid;
    @JsonProperty("conversation_sid") private String conversationSid;
    @JsonProperty("sid") private String sid;
    @JsonProperty("index") private Integer index;
    @JsonProperty("author") private String author;
    @JsonProperty("body") private String body;
    @JsonProperty("media") private List<Map<String, Object>> media;
    @JsonProperty("attributes") private String attributes;
    @JsonProperty("participant_sid") private String participantSid;
    @JsonProperty("date_created") private String dateCreated;
    @JsonProperty("date_updated") private String dateUpdated;
    @JsonProperty("url") private String url;
    @JsonProperty("delivery") private Map<String, Object> delivery;
    @JsonProperty("links") private Map<String, Object> links;
    @JsonProperty("content_sid") private String contentSid;

    public String getAccountSid() { return accountSid; }
    public String getChatServiceSid() { return chatServiceSid; }
    public String getConversationSid() { return conversationSid; }
    public String getSid() { return sid; }
    public Integer getIndex() { return index; }
    public String getAuthor() { return author; }
    public String getBody() { return body; }
    public List<Map<String, Object>> getMedia() { return media; }
    public String getAttributes() { return attributes; }
    public String getParticipantSid() { return participantSid; }
    public String getDateCreated() { return dateCreated; }
    public String getDateUpdated() { return dateUpdated; }
    public String getUrl() { return url; }
    public Map<String, Object> getDelivery() { return delivery; }
    public Map<String, Object> getLinks() { return links; }
    public String getContentSid() { return contentSid; }

    public void setAccountSid(String v) { this.accountSid = v; }
    public void setChatServiceSid(String v) { this.chatServiceSid = v; }
    public void setConversationSid(String v) { this.conversationSid = v; }
    public void setSid(String v) { this.sid = v; }
    public void setIndex(Integer v) { this.index = v; }
    public void setAuthor(String v) { this.author = v; }
    public void setBody(String v) { this.body = v; }
    public void setMedia(List<Map<String, Object>> v) { this.media = v; }
    public void setAttributes(String v) { this.attributes = v; }
    public void setParticipantSid(String v) { this.participantSid = v; }
    public void setDateCreated(String v) { this.dateCreated = v; }
    public void setDateUpdated(String v) { this.dateUpdated = v; }
    public void setUrl(String v) { this.url = v; }
    public void setDelivery(Map<String, Object> v) { this.delivery = v; }
    public void setLinks(Map<String, Object> v) { this.links = v; }
    public void setContentSid(String v) { this.contentSid = v; }
}
