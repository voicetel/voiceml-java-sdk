package com.voicetel.voiceml.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;

/** Outbound call price leaf carrying origination prefixes (Pricing v2 number body). */
@JsonIgnoreProperties(ignoreUnknown = true)
public class PricingOutboundCallPriceWithOrigin {

    @JsonProperty("origination_prefixes") private List<String> originationPrefixes = new ArrayList<>();
    @JsonProperty("base_price") private String basePrice;
    @JsonProperty("current_price") private String currentPrice;

    public List<String> getOriginationPrefixes() { return originationPrefixes; }
    public String getBasePrice() { return basePrice; }
    public String getCurrentPrice() { return currentPrice; }

    public void setOriginationPrefixes(List<String> v) {
        this.originationPrefixes = v != null ? v : new ArrayList<>();
    }
    public void setBasePrice(String v) { this.basePrice = v; }
    public void setCurrentPrice(String v) { this.currentPrice = v; }
}
