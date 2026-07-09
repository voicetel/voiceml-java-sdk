package com.voicetel.voiceml.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;

/** Prefix price leaf carrying both origination and destination prefixes (Pricing v2 bodies). */
@JsonIgnoreProperties(ignoreUnknown = true)
public class PricingOutboundPrefixPriceWithOrigin {

    @JsonProperty("origination_prefixes") private List<String> originationPrefixes = new ArrayList<>();
    @JsonProperty("destination_prefixes") private List<String> destinationPrefixes = new ArrayList<>();
    @JsonProperty("base_price") private String basePrice;
    @JsonProperty("current_price") private String currentPrice;
    @JsonProperty("friendly_name") private String friendlyName;

    public List<String> getOriginationPrefixes() { return originationPrefixes; }
    public List<String> getDestinationPrefixes() { return destinationPrefixes; }
    public String getBasePrice() { return basePrice; }
    public String getCurrentPrice() { return currentPrice; }
    public String getFriendlyName() { return friendlyName; }

    public void setOriginationPrefixes(List<String> v) {
        this.originationPrefixes = v != null ? v : new ArrayList<>();
    }
    public void setDestinationPrefixes(List<String> v) {
        this.destinationPrefixes = v != null ? v : new ArrayList<>();
    }
    public void setBasePrice(String v) { this.basePrice = v; }
    public void setCurrentPrice(String v) { this.currentPrice = v; }
    public void setFriendlyName(String v) { this.friendlyName = v; }
}
