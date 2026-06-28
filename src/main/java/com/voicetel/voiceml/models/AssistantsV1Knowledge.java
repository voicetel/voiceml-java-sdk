package com.voicetel.voiceml.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Map;

/** Twilio Assistants v1 Knowledge. Prefixed ID is {@code aia_know_…}. */
@JsonIgnoreProperties(ignoreUnknown = true)
public class AssistantsV1Knowledge {

    @JsonProperty("description") private String description;
    @JsonProperty("id") private String id;
    @JsonProperty("account_sid") private String accountSid;
    @JsonProperty("knowledge_source_details") private Map<String, Object> knowledgeSourceDetails;
    @JsonProperty("name") private String name;
    @JsonProperty("status") private String status;
    @JsonProperty("type") private String type;
    @JsonProperty("url") private String url;
    @JsonProperty("embedding_model") private String embeddingModel;
    @JsonProperty("date_created") private String dateCreated;
    @JsonProperty("date_updated") private String dateUpdated;

    public String getDescription() { return description; }
    public String getId() { return id; }
    public String getAccountSid() { return accountSid; }
    public Map<String, Object> getKnowledgeSourceDetails() { return knowledgeSourceDetails; }
    public String getName() { return name; }
    public String getStatus() { return status; }
    public String getType() { return type; }
    public String getUrl() { return url; }
    public String getEmbeddingModel() { return embeddingModel; }
    public String getDateCreated() { return dateCreated; }
    public String getDateUpdated() { return dateUpdated; }

    public void setDescription(String v) { this.description = v; }
    public void setId(String v) { this.id = v; }
    public void setAccountSid(String v) { this.accountSid = v; }
    public void setKnowledgeSourceDetails(Map<String, Object> v) { this.knowledgeSourceDetails = v; }
    public void setName(String v) { this.name = v; }
    public void setStatus(String v) { this.status = v; }
    public void setType(String v) { this.type = v; }
    public void setUrl(String v) { this.url = v; }
    public void setEmbeddingModel(String v) { this.embeddingModel = v; }
    public void setDateCreated(String v) { this.dateCreated = v; }
    public void setDateUpdated(String v) { this.dateUpdated = v; }
}
