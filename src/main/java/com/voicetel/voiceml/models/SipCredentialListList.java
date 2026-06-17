package com.voicetel.voiceml.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;

/** Paginated {@code /SIP/CredentialLists} response envelope. */
@JsonIgnoreProperties(ignoreUnknown = true)
public class SipCredentialListList extends Page {

    @JsonProperty("credential_lists")
    private List<SipCredentialList> credentialLists = new ArrayList<>();

    public List<SipCredentialList> getCredentialLists() { return credentialLists; }
    public void setCredentialLists(List<SipCredentialList> v) { this.credentialLists = v != null ? v : new ArrayList<>(); }
}
