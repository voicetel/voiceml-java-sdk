package com.voicetel.voiceml.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/** Phone-number rental price leaf (Pricing v1 phone-number country body). */
@JsonIgnoreProperties(ignoreUnknown = true)
public class PricingPhoneNumberPrice {

    @JsonProperty("number_type") private String numberType;
    @JsonProperty("base_price") private String basePrice;
    @JsonProperty("current_price") private String currentPrice;

    public String getNumberType() { return numberType; }
    public String getBasePrice() { return basePrice; }
    public String getCurrentPrice() { return currentPrice; }

    public void setNumberType(String v) { this.numberType = v; }
    public void setBasePrice(String v) { this.basePrice = v; }
    public void setCurrentPrice(String v) { this.currentPrice = v; }
}
