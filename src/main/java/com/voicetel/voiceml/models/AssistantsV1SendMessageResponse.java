package com.voicetel.voiceml.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/** Response shape for {@code POST /v1/Assistants/{id}/Messages}. */
@JsonIgnoreProperties(ignoreUnknown = true)
public class AssistantsV1SendMessageResponse {

    @JsonProperty("status") private String status;
    @JsonProperty("flagged") private Boolean flagged;
    @JsonProperty("aborted") private Boolean aborted;
    @JsonProperty("session_id") private String sessionId;
    @JsonProperty("account_sid") private String accountSid;
    @JsonProperty("body") private String body;
    @JsonProperty("error") private String error;

    public String getStatus() { return status; }
    public Boolean getFlagged() { return flagged; }
    public Boolean getAborted() { return aborted; }
    public String getSessionId() { return sessionId; }
    public String getAccountSid() { return accountSid; }
    public String getBody() { return body; }
    public String getError() { return error; }

    public void setStatus(String v) { this.status = v; }
    public void setFlagged(Boolean v) { this.flagged = v; }
    public void setAborted(Boolean v) { this.aborted = v; }
    public void setSessionId(String v) { this.sessionId = v; }
    public void setAccountSid(String v) { this.accountSid = v; }
    public void setBody(String v) { this.body = v; }
    public void setError(String v) { this.error = v; }
}
