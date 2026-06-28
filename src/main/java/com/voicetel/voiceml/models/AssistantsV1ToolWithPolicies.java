package com.voicetel.voiceml.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Twilio Assistants v1 Tool returned by the fetch endpoint, with attached
 * {@code policies} array inlined.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class AssistantsV1ToolWithPolicies {

    @JsonProperty("account_sid") private String accountSid;
    @JsonProperty("description") private String description;
    @JsonProperty("enabled") private Boolean enabled;
    @JsonProperty("id") private String id;
    @JsonProperty("meta") private Map<String, Object> meta;
    @JsonProperty("name") private String name;
    @JsonProperty("requires_auth") private Boolean requiresAuth;
    @JsonProperty("type") private String type;
    @JsonProperty("url") private String url;
    @JsonProperty("date_created") private String dateCreated;
    @JsonProperty("date_updated") private String dateUpdated;
    @JsonProperty("policies") private List<AssistantsV1Policy> policies = new ArrayList<>();

    public String getAccountSid() { return accountSid; }
    public String getDescription() { return description; }
    public Boolean getEnabled() { return enabled; }
    public String getId() { return id; }
    public Map<String, Object> getMeta() { return meta; }
    public String getName() { return name; }
    public Boolean getRequiresAuth() { return requiresAuth; }
    public String getType() { return type; }
    public String getUrl() { return url; }
    public String getDateCreated() { return dateCreated; }
    public String getDateUpdated() { return dateUpdated; }
    public List<AssistantsV1Policy> getPolicies() { return policies; }

    public void setAccountSid(String v) { this.accountSid = v; }
    public void setDescription(String v) { this.description = v; }
    public void setEnabled(Boolean v) { this.enabled = v; }
    public void setId(String v) { this.id = v; }
    public void setMeta(Map<String, Object> v) { this.meta = v; }
    public void setName(String v) { this.name = v; }
    public void setRequiresAuth(Boolean v) { this.requiresAuth = v; }
    public void setType(String v) { this.type = v; }
    public void setUrl(String v) { this.url = v; }
    public void setDateCreated(String v) { this.dateCreated = v; }
    public void setDateUpdated(String v) { this.dateUpdated = v; }
    public void setPolicies(List<AssistantsV1Policy> v) {
        this.policies = v != null ? v : new ArrayList<>();
    }
}
