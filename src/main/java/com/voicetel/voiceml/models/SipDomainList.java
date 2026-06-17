package com.voicetel.voiceml.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;

/** Paginated {@code /SIP/Domains} response envelope. */
@JsonIgnoreProperties(ignoreUnknown = true)
public class SipDomainList extends Page {

    @JsonProperty("domains")
    private List<SipDomain> domains = new ArrayList<>();

    public List<SipDomain> getDomains() { return domains; }
    public void setDomains(List<SipDomain> v) { this.domains = v != null ? v : new ArrayList<>(); }
}
