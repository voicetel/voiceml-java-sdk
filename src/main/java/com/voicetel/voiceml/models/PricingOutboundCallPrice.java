package com.voicetel.voiceml.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/** Outbound call price leaf (Pricing v1 number body). */
@JsonIgnoreProperties(ignoreUnknown = true)
public class PricingOutboundCallPrice {

    @JsonProperty("base_price") private String basePrice;
    @JsonProperty("current_price") private String currentPrice;

    public String getBasePrice() { return basePrice; }
    public String getCurrentPrice() { return currentPrice; }

    public void setBasePrice(String v) { this.basePrice = v; }
    public void setCurrentPrice(String v) { this.currentPrice = v; }
}
