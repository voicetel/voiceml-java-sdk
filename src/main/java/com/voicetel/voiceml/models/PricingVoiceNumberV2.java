package com.voicetel.voiceml.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;

/** {@code GET /v2/Voice/Numbers/{DestinationNumber}} body. */
@JsonIgnoreProperties(ignoreUnknown = true)
public class PricingVoiceNumberV2 {

    @JsonProperty("destination_number") private String destinationNumber;
    @JsonProperty("origination_number") private String originationNumber;
    @JsonProperty("country") private String country;
    @JsonProperty("iso_country") private String isoCountry;
    @JsonProperty("outbound_call_prices")
    private List<PricingOutboundCallPriceWithOrigin> outboundCallPrices = new ArrayList<>();
    @JsonProperty("inbound_call_price") private PricingInboundCallPrice inboundCallPrice;
    @JsonProperty("price_unit") private String priceUnit;
    @JsonProperty("url") private String url;

    public String getDestinationNumber() { return destinationNumber; }
    public String getOriginationNumber() { return originationNumber; }
    public String getCountry() { return country; }
    public String getIsoCountry() { return isoCountry; }
    public List<PricingOutboundCallPriceWithOrigin> getOutboundCallPrices() {
        return outboundCallPrices;
    }
    public PricingInboundCallPrice getInboundCallPrice() { return inboundCallPrice; }
    public String getPriceUnit() { return priceUnit; }
    public String getUrl() { return url; }

    public void setDestinationNumber(String v) { this.destinationNumber = v; }
    public void setOriginationNumber(String v) { this.originationNumber = v; }
    public void setCountry(String v) { this.country = v; }
    public void setIsoCountry(String v) { this.isoCountry = v; }
    public void setOutboundCallPrices(List<PricingOutboundCallPriceWithOrigin> v) {
        this.outboundCallPrices = v != null ? v : new ArrayList<>();
    }
    public void setInboundCallPrice(PricingInboundCallPrice v) { this.inboundCallPrice = v; }
    public void setPriceUnit(String v) { this.priceUnit = v; }
    public void setUrl(String v) { this.url = v; }
}
