package com.voicetel.voiceml.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;

/** Paginated {@code /IpAddresses} response envelope. */
@JsonIgnoreProperties(ignoreUnknown = true)
public class SipIpAddressList extends Page {

    @JsonProperty("ip_addresses")
    private List<SipIpAddress> ipAddresses = new ArrayList<>();

    public List<SipIpAddress> getIpAddresses() { return ipAddresses; }
    public void setIpAddresses(List<SipIpAddress> v) { this.ipAddresses = v != null ? v : new ArrayList<>(); }
}
