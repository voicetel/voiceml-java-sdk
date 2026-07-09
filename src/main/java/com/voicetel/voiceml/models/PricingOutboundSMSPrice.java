package com.voicetel.voiceml.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;

/** Outbound SMS price leaf, keyed by carrier/MCC/MNC (Pricing v1 messaging country body). */
@JsonIgnoreProperties(ignoreUnknown = true)
public class PricingOutboundSMSPrice {

    @JsonProperty("carrier") private String carrier;
    @JsonProperty("mcc") private String mcc;
    @JsonProperty("mnc") private String mnc;
    @JsonProperty("prices") private List<PricingInboundCallPrice> prices = new ArrayList<>();

    public String getCarrier() { return carrier; }
    public String getMcc() { return mcc; }
    public String getMnc() { return mnc; }
    public List<PricingInboundCallPrice> getPrices() { return prices; }

    public void setCarrier(String v) { this.carrier = v; }
    public void setMcc(String v) { this.mcc = v; }
    public void setMnc(String v) { this.mnc = v; }
    public void setPrices(List<PricingInboundCallPrice> v) {
        this.prices = v != null ? v : new ArrayList<>();
    }
}
