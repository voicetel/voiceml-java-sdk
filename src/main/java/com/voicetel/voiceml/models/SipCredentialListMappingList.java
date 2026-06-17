package com.voicetel.voiceml.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;

/** Paginated CredentialListMapping list (historical / Auth/Calls / Auth/Registrations). */
@JsonIgnoreProperties(ignoreUnknown = true)
public class SipCredentialListMappingList extends Page {

    @JsonProperty("credential_list_mappings")
    private List<SipDomainMapping> credentialListMappings = new ArrayList<>();

    public List<SipDomainMapping> getCredentialListMappings() { return credentialListMappings; }
    public void setCredentialListMappings(List<SipDomainMapping> v) { this.credentialListMappings = v != null ? v : new ArrayList<>(); }
}
