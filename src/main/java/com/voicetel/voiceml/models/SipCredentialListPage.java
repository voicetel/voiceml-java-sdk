package com.voicetel.voiceml.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;

/**
 * Paginated page of credentials within a CredentialList. Spec name
 * {@code SipCredentialListPage} mirrors Twilio — note: it's a *page of
 * credentials*, not of credential-lists.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class SipCredentialListPage extends Page {

    @JsonProperty("credentials")
    private List<SipCredential> credentials = new ArrayList<>();

    public List<SipCredential> getCredentials() { return credentials; }
    public void setCredentials(List<SipCredential> v) { this.credentials = v != null ? v : new ArrayList<>(); }
}
