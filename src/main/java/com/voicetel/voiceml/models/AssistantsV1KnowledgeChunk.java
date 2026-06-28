package com.voicetel.voiceml.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Map;

/** Single chunk emitted by {@code /v1/Knowledge/{id}/Chunks}. */
@JsonIgnoreProperties(ignoreUnknown = true)
public class AssistantsV1KnowledgeChunk {

    @JsonProperty("account_sid") private String accountSid;
    @JsonProperty("content") private String content;
    @JsonProperty("metadata") private Map<String, Object> metadata;
    @JsonProperty("date_created") private String dateCreated;
    @JsonProperty("date_updated") private String dateUpdated;

    public String getAccountSid() { return accountSid; }
    public String getContent() { return content; }
    public Map<String, Object> getMetadata() { return metadata; }
    public String getDateCreated() { return dateCreated; }
    public String getDateUpdated() { return dateUpdated; }

    public void setAccountSid(String v) { this.accountSid = v; }
    public void setContent(String v) { this.content = v; }
    public void setMetadata(Map<String, Object> v) { this.metadata = v; }
    public void setDateCreated(String v) { this.dateCreated = v; }
    public void setDateUpdated(String v) { this.dateUpdated = v; }
}
