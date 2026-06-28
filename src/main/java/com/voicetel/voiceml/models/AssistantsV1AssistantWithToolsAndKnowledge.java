package com.voicetel.voiceml.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Twilio Assistants v1 Assistant returned by the fetch endpoint, with attached
 * {@code tools} and {@code knowledge} arrays inlined.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class AssistantsV1AssistantWithToolsAndKnowledge {

    @JsonProperty("account_sid") private String accountSid;
    @JsonProperty("customer_ai") private Map<String, Object> customerAi;
    @JsonProperty("id") private String id;
    @JsonProperty("model") private String model;
    @JsonProperty("name") private String name;
    @JsonProperty("owner") private String owner;
    @JsonProperty("url") private String url;
    @JsonProperty("personality_prompt") private String personalityPrompt;
    @JsonProperty("date_created") private String dateCreated;
    @JsonProperty("date_updated") private String dateUpdated;
    @JsonProperty("tools") private List<AssistantsV1Tool> tools = new ArrayList<>();
    @JsonProperty("knowledge") private List<AssistantsV1Knowledge> knowledge = new ArrayList<>();

    public String getAccountSid() { return accountSid; }
    public Map<String, Object> getCustomerAi() { return customerAi; }
    public String getId() { return id; }
    public String getModel() { return model; }
    public String getName() { return name; }
    public String getOwner() { return owner; }
    public String getUrl() { return url; }
    public String getPersonalityPrompt() { return personalityPrompt; }
    public String getDateCreated() { return dateCreated; }
    public String getDateUpdated() { return dateUpdated; }
    public List<AssistantsV1Tool> getTools() { return tools; }
    public List<AssistantsV1Knowledge> getKnowledge() { return knowledge; }

    public void setAccountSid(String v) { this.accountSid = v; }
    public void setCustomerAi(Map<String, Object> v) { this.customerAi = v; }
    public void setId(String v) { this.id = v; }
    public void setModel(String v) { this.model = v; }
    public void setName(String v) { this.name = v; }
    public void setOwner(String v) { this.owner = v; }
    public void setUrl(String v) { this.url = v; }
    public void setPersonalityPrompt(String v) { this.personalityPrompt = v; }
    public void setDateCreated(String v) { this.dateCreated = v; }
    public void setDateUpdated(String v) { this.dateUpdated = v; }
    public void setTools(List<AssistantsV1Tool> v) { this.tools = v != null ? v : new ArrayList<>(); }
    public void setKnowledge(List<AssistantsV1Knowledge> v) { this.knowledge = v != null ? v : new ArrayList<>(); }
}
