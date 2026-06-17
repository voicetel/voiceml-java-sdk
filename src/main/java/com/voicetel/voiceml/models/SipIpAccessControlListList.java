package com.voicetel.voiceml.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;

/** Paginated {@code /SIP/IpAccessControlLists} response envelope. */
@JsonIgnoreProperties(ignoreUnknown = true)
public class SipIpAccessControlListList extends Page {

    @JsonProperty("ip_access_control_lists")
    private List<SipIpAccessControlList> ipAccessControlLists = new ArrayList<>();

    public List<SipIpAccessControlList> getIpAccessControlLists() { return ipAccessControlLists; }
    public void setIpAccessControlLists(List<SipIpAccessControlList> v) { this.ipAccessControlLists = v != null ? v : new ArrayList<>(); }
}
