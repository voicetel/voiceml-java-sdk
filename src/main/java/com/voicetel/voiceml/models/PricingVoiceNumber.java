package com.voicetel.voiceml.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/** {@code GET /v1/Voice/Numbers/{Number}} body. */
@JsonIgnoreProperties(ignoreUnknown = true)
public class PricingVoiceNumber {

    @JsonProperty("number") private String number;
    @JsonProperty("country") private String country;
    @JsonProperty("iso_country") private String isoCountry;
    @JsonProperty("outbound_call_price") private PricingOutboundCallPrice outboundCallPrice;
    @JsonProperty("inbound_call_price") private PricingInboundCallPrice inboundCallPrice;
    @JsonProperty("price_unit") private String priceUnit;
    @JsonProperty("url") private String url;

    public String getNumber() { return number; }
    public String getCountry() { return country; }
    public String getIsoCountry() { return isoCountry; }
    public PricingOutboundCallPrice getOutboundCallPrice() { return outboundCallPrice; }
    public PricingInboundCallPrice getInboundCallPrice() { return inboundCallPrice; }
    public String getPriceUnit() { return priceUnit; }
    public String getUrl() { return url; }

    public void setNumber(String v) { this.number = v; }
    public void setCountry(String v) { this.country = v; }
    public void setIsoCountry(String v) { this.isoCountry = v; }
    public void setOutboundCallPrice(PricingOutboundCallPrice v) { this.outboundCallPrice = v; }
    public void setInboundCallPrice(PricingInboundCallPrice v) { this.inboundCallPrice = v; }
    public void setPriceUnit(String v) { this.priceUnit = v; }
    public void setUrl(String v) { this.url = v; }
}
