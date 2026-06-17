package com.voicetel.voiceml.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;

/** Paginated IpAccessControlListMapping list (historical + Auth/Calls; no registrations counterpart). */
@JsonIgnoreProperties(ignoreUnknown = true)
public class SipIpAccessControlListMappingList extends Page {

    @JsonProperty("ip_access_control_list_mappings")
    private List<SipDomainMapping> ipAccessControlListMappings = new ArrayList<>();

    public List<SipDomainMapping> getIpAccessControlListMappings() { return ipAccessControlListMappings; }
    public void setIpAccessControlListMappings(List<SipDomainMapping> v) { this.ipAccessControlListMappings = v != null ? v : new ArrayList<>(); }
}
