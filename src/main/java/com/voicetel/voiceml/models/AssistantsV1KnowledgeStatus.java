package com.voicetel.voiceml.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/** Ingestion status snapshot for a {@code /v1/Knowledge/{id}/Status} resource. */
@JsonIgnoreProperties(ignoreUnknown = true)
public class AssistantsV1KnowledgeStatus {

    @JsonProperty("account_sid") private String accountSid;
    @JsonProperty("status") private String status;
    @JsonProperty("last_status") private String lastStatus;
    @JsonProperty("date_updated") private String dateUpdated;

    public String getAccountSid() { return accountSid; }
    public String getStatus() { return status; }
    public String getLastStatus() { return lastStatus; }
    public String getDateUpdated() { return dateUpdated; }

    public void setAccountSid(String v) { this.accountSid = v; }
    public void setStatus(String v) { this.status = v; }
    public void setLastStatus(String v) { this.lastStatus = v; }
    public void setDateUpdated(String v) { this.dateUpdated = v; }
}
