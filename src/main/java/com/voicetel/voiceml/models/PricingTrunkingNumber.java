package com.voicetel.voiceml.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;

/** {@code GET /v2/Trunking/Numbers/{DestinationNumber}} body. */
@JsonIgnoreProperties(ignoreUnknown = true)
public class PricingTrunkingNumber {

    @JsonProperty("destination_number") private String destinationNumber;
    @JsonProperty("origination_number") private String originationNumber;
    @JsonProperty("country") private String country;
    @JsonProperty("iso_country") private String isoCountry;
    @JsonProperty("terminating_prefix_prices")
    private List<PricingOutboundPrefixPriceWithOrigin> terminatingPrefixPrices = new ArrayList<>();
    @JsonProperty("originating_call_price") private PricingInboundCallPrice originatingCallPrice;
    @JsonProperty("price_unit") private String priceUnit;
    @JsonProperty("url") private String url;

    public String getDestinationNumber() { return destinationNumber; }
    public String getOriginationNumber() { return originationNumber; }
    public String getCountry() { return country; }
    public String getIsoCountry() { return isoCountry; }
    public List<PricingOutboundPrefixPriceWithOrigin> getTerminatingPrefixPrices() {
        return terminatingPrefixPrices;
    }
    public PricingInboundCallPrice getOriginatingCallPrice() { return originatingCallPrice; }
    public String getPriceUnit() { return priceUnit; }
    public String getUrl() { return url; }

    public void setDestinationNumber(String v) { this.destinationNumber = v; }
    public void setOriginationNumber(String v) { this.originationNumber = v; }
    public void setCountry(String v) { this.country = v; }
    public void setIsoCountry(String v) { this.isoCountry = v; }
    public void setTerminatingPrefixPrices(List<PricingOutboundPrefixPriceWithOrigin> v) {
        this.terminatingPrefixPrices = v != null ? v : new ArrayList<>();
    }
    public void setOriginatingCallPrice(PricingInboundCallPrice v) { this.originatingCallPrice = v; }
    public void setPriceUnit(String v) { this.priceUnit = v; }
    public void setUrl(String v) { this.url = v; }
}
