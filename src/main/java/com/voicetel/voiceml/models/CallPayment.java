package com.voicetel.voiceml.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * REST companion to the {@code <Pay>} TwiML verb. Twilio-compatible response shape — the
 * minimal payload mirrors Twilio's documented surface; runtime config (ChargeAmount,
 * PaymentConnector, ValidCardTypes, etc.) is captured server-side and not echoed back.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class CallPayment {

    @JsonProperty("sid")
    private String sid;

    @JsonProperty("account_sid")
    private String accountSid;

    @JsonProperty("call_sid")
    private String callSid;

    @JsonProperty("api_version")
    private String apiVersion;

    @JsonProperty("date_created")
    private String dateCreated;

    @JsonProperty("date_updated")
    private String dateUpdated;

    @JsonProperty("uri")
    private String uri;

    public String getSid() { return sid; }
    public void setSid(String sid) { this.sid = sid; }
    public String getAccountSid() { return accountSid; }
    public void setAccountSid(String accountSid) { this.accountSid = accountSid; }
    public String getCallSid() { return callSid; }
    public void setCallSid(String callSid) { this.callSid = callSid; }
    public String getApiVersion() { return apiVersion; }
    public void setApiVersion(String apiVersion) { this.apiVersion = apiVersion; }
    public String getDateCreated() { return dateCreated; }
    public void setDateCreated(String dateCreated) { this.dateCreated = dateCreated; }
    public String getDateUpdated() { return dateUpdated; }
    public void setDateUpdated(String dateUpdated) { this.dateUpdated = dateUpdated; }
    public String getUri() { return uri; }
    public void setUri(String uri) { this.uri = uri; }
}
