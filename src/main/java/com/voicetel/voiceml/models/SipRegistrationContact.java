package com.voicetel.voiceml.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/** One registered contact of a {@link SipRegistration}. */
@JsonIgnoreProperties(ignoreUnknown = true)
public class SipRegistrationContact {

    @JsonProperty("contact")
    private String contact;

    @JsonProperty("received")
    private String received;

    @JsonProperty("socket")
    private String socket;

    @JsonProperty("user_agent")
    private String userAgent;

    @JsonProperty("expires_at")
    private String expiresAt;

    @JsonProperty("registered")
    private Boolean registered;

    /** @return the {@code contact} field */
    public String getContact() {
        return contact;
    }

    /** @param contact the {@code contact} field */
    public void setContact(String contact) {
        this.contact = contact;
    }

    /** @return the {@code received} field */
    public String getReceived() {
        return received;
    }

    /** @param received the {@code received} field */
    public void setReceived(String received) {
        this.received = received;
    }

    /** @return the {@code socket} field */
    public String getSocket() {
        return socket;
    }

    /** @param socket the {@code socket} field */
    public void setSocket(String socket) {
        this.socket = socket;
    }

    /** @return the {@code user_agent} field */
    public String getUserAgent() {
        return userAgent;
    }

    /** @param userAgent the {@code user_agent} field */
    public void setUserAgent(String userAgent) {
        this.userAgent = userAgent;
    }

    /** @return the {@code expires_at} field */
    public String getExpiresAt() {
        return expiresAt;
    }

    /** @param expiresAt the {@code expires_at} field */
    public void setExpiresAt(String expiresAt) {
        this.expiresAt = expiresAt;
    }

    /** @return the {@code registered} field */
    public Boolean getRegistered() {
        return registered;
    }

    /** @param registered the {@code registered} field */
    public void setRegistered(Boolean registered) {
        this.registered = registered;
    }
}
