package com.voicetel.voiceml.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;

/** {@code GET /v2/Trunking/Countries/{IsoCountry}} body. */
@JsonIgnoreProperties(ignoreUnknown = true)
public class PricingTrunkingCountry {

    @JsonProperty("country") private String country;
    @JsonProperty("iso_country") private String isoCountry;
    @JsonProperty("terminating_prefix_prices")
    private List<PricingOutboundPrefixPriceWithOrigin> terminatingPrefixPrices = new ArrayList<>();
    @JsonProperty("originating_call_prices")
    private List<PricingInboundCallPrice> originatingCallPrices = new ArrayList<>();
    @JsonProperty("price_unit") private String priceUnit;
    @JsonProperty("url") private String url;

    public String getCountry() { return country; }
    public String getIsoCountry() { return isoCountry; }
    public List<PricingOutboundPrefixPriceWithOrigin> getTerminatingPrefixPrices() {
        return terminatingPrefixPrices;
    }
    public List<PricingInboundCallPrice> getOriginatingCallPrices() { return originatingCallPrices; }
    public String getPriceUnit() { return priceUnit; }
    public String getUrl() { return url; }

    public void setCountry(String v) { this.country = v; }
    public void setIsoCountry(String v) { this.isoCountry = v; }
    public void setTerminatingPrefixPrices(List<PricingOutboundPrefixPriceWithOrigin> v) {
        this.terminatingPrefixPrices = v != null ? v : new ArrayList<>();
    }
    public void setOriginatingCallPrices(List<PricingInboundCallPrice> v) {
        this.originatingCallPrices = v != null ? v : new ArrayList<>();
    }
    public void setPriceUnit(String v) { this.priceUnit = v; }
    public void setUrl(String v) { this.url = v; }
}
