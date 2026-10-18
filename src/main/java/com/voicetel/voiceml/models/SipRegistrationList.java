package com.voicetel.voiceml.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;

/** Paginated {@code GET /SIP/Registrations} response. */
@JsonIgnoreProperties(ignoreUnknown = true)
public class SipRegistrationList extends Page {

    @JsonProperty("registrations")
    private List<SipRegistration> registrations = new ArrayList<>();

    /** @return the {@code registrations} field */
    public List<SipRegistration> getRegistrations() {
        return registrations;
    }

    /** @param registrations the {@code registrations} field */
    public void setRegistrations(List<SipRegistration> registrations) {
        this.registrations = registrations;
    }
}
