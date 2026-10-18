package com.voicetel.voiceml.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;

/** A SIP endpoint registration ({@code GET /SIP/Registrations}). */
@JsonIgnoreProperties(ignoreUnknown = true)
public class SipRegistration {

    @JsonProperty("aor")
    private String aor;

    @JsonProperty("username")
    private String username;

    @JsonProperty("domain")
    private String domain;

    @JsonProperty("domain_sid")
    private String domainSid;

    @JsonProperty("status")
    private String status;

    @JsonProperty("caller_id")
    private String callerId;

    @JsonProperty("user_agent")
    private String userAgent;

    @JsonProperty("last_registered")
    private String lastRegistered;

    @JsonProperty("contacts")
    private List<SipRegistrationContact> contacts = new ArrayList<>();

    @JsonProperty("active_call")
    private ActiveCall activeCall;

    /** @return the {@code aor} field */
    public String getAor() {
        return aor;
    }

    /** @param aor the {@code aor} field */
    public void setAor(String aor) {
        this.aor = aor;
    }

    /** @return the {@code username} field */
    public String getUsername() {
        return username;
    }

    /** @param username the {@code username} field */
    public void setUsername(String username) {
        this.username = username;
    }

    /** @return the {@code domain} field */
    public String getDomain() {
        return domain;
    }

    /** @param domain the {@code domain} field */
    public void setDomain(String domain) {
        this.domain = domain;
    }

    /** @return the {@code domain_sid} field */
    public String getDomainSid() {
        return domainSid;
    }

    /** @param domainSid the {@code domain_sid} field */
    public void setDomainSid(String domainSid) {
        this.domainSid = domainSid;
    }

    /** @return the {@code status} field */
    public String getStatus() {
        return status;
    }

    /** @param status the {@code status} field */
    public void setStatus(String status) {
        this.status = status;
    }

    /** @return the {@code caller_id} field */
    public String getCallerId() {
        return callerId;
    }

    /** @param callerId the {@code caller_id} field */
    public void setCallerId(String callerId) {
        this.callerId = callerId;
    }

    /** @return the {@code user_agent} field */
    public String getUserAgent() {
        return userAgent;
    }

    /** @param userAgent the {@code user_agent} field */
    public void setUserAgent(String userAgent) {
        this.userAgent = userAgent;
    }

    /** @return the {@code last_registered} field */
    public String getLastRegistered() {
        return lastRegistered;
    }

    /** @param lastRegistered the {@code last_registered} field */
    public void setLastRegistered(String lastRegistered) {
        this.lastRegistered = lastRegistered;
    }

    /** @return the {@code contacts} field */
    public List<SipRegistrationContact> getContacts() {
        return contacts;
    }

    /** @param contacts the {@code contacts} field */
    public void setContacts(List<SipRegistrationContact> contacts) {
        this.contacts = contacts;
    }

    /** @return the {@code active_call} field */
    public ActiveCall getActiveCall() {
        return activeCall;
    }

    /** @param activeCall the {@code active_call} field */
    public void setActiveCall(ActiveCall activeCall) {
        this.activeCall = activeCall;
    }

    /** The {@code ActiveCall} object. */
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class ActiveCall {

        @JsonProperty("call_sid")
        private String callSid;

        @JsonProperty("direction")
        private String direction;

        @JsonProperty("remote")
        private String remote;

        @JsonProperty("since")
        private String since;

        /** @return the {@code call_sid} field */
        public String getCallSid() {
            return callSid;
        }

        /** @param callSid the {@code call_sid} field */
        public void setCallSid(String callSid) {
            this.callSid = callSid;
        }

        /** @return the {@code direction} field */
        public String getDirection() {
            return direction;
        }

        /** @param direction the {@code direction} field */
        public void setDirection(String direction) {
            this.direction = direction;
        }

        /** @return the {@code remote} field */
        public String getRemote() {
            return remote;
        }

        /** @param remote the {@code remote} field */
        public void setRemote(String remote) {
            this.remote = remote;
        }

        /** @return the {@code since} field */
        public String getSince() {
            return since;
        }

        /** @param since the {@code since} field */
        public void setSince(String since) {
            this.since = since;
        }
    }
}
