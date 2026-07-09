package com.voicetel.voiceml.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;

/** Outbound prefix price leaf (Pricing v1 voice country body). */
@JsonIgnoreProperties(ignoreUnknown = true)
public class PricingOutboundPrefixPrice {

    @JsonProperty("prefixes") private List<String> prefixes = new ArrayList<>();
    @JsonProperty("base_price") private String basePrice;
    @JsonProperty("current_price") private String currentPrice;
    @JsonProperty("friendly_name") private String friendlyName;

    public List<String> getPrefixes() { return prefixes; }
    public String getBasePrice() { return basePrice; }
    public String getCurrentPrice() { return currentPrice; }
    public String getFriendlyName() { return friendlyName; }

    public void setPrefixes(List<String> v) {
        this.prefixes = v != null ? v : new ArrayList<>();
    }
    public void setBasePrice(String v) { this.basePrice = v; }
    public void setCurrentPrice(String v) { this.currentPrice = v; }
    public void setFriendlyName(String v) { this.friendlyName = v; }
}
