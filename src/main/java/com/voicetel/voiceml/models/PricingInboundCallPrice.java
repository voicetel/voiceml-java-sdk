package com.voicetel.voiceml.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/** Inbound call/SMS price leaf. {@code numberType} is {@code local} or {@code "toll free"}. */
@JsonIgnoreProperties(ignoreUnknown = true)
public class PricingInboundCallPrice {

    @JsonProperty("base_price") private String basePrice;
    @JsonProperty("current_price") private String currentPrice;
    @JsonProperty("number_type") private String numberType;

    public String getBasePrice() { return basePrice; }
    public String getCurrentPrice() { return currentPrice; }
    public String getNumberType() { return numberType; }

    public void setBasePrice(String v) { this.basePrice = v; }
    public void setCurrentPrice(String v) { this.currentPrice = v; }
    public void setNumberType(String v) { this.numberType = v; }
}
