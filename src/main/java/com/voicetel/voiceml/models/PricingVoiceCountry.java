package com.voicetel.voiceml.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;

/** {@code GET /v1/Voice/Countries/{IsoCountry}} body. */
@JsonIgnoreProperties(ignoreUnknown = true)
public class PricingVoiceCountry {

    @JsonProperty("country") private String country;
    @JsonProperty("iso_country") private String isoCountry;
    @JsonProperty("outbound_prefix_prices")
    private List<PricingOutboundPrefixPrice> outboundPrefixPrices = new ArrayList<>();
    @JsonProperty("inbound_call_prices")
    private List<PricingInboundCallPrice> inboundCallPrices = new ArrayList<>();
    @JsonProperty("price_unit") private String priceUnit;
    @JsonProperty("url") private String url;

    public String getCountry() { return country; }
    public String getIsoCountry() { return isoCountry; }
    public List<PricingOutboundPrefixPrice> getOutboundPrefixPrices() { return outboundPrefixPrices; }
    public List<PricingInboundCallPrice> getInboundCallPrices() { return inboundCallPrices; }
    public String getPriceUnit() { return priceUnit; }
    public String getUrl() { return url; }

    public void setCountry(String v) { this.country = v; }
    public void setIsoCountry(String v) { this.isoCountry = v; }
    public void setOutboundPrefixPrices(List<PricingOutboundPrefixPrice> v) {
        this.outboundPrefixPrices = v != null ? v : new ArrayList<>();
    }
    public void setInboundCallPrices(List<PricingInboundCallPrice> v) {
        this.inboundCallPrices = v != null ? v : new ArrayList<>();
    }
    public void setPriceUnit(String v) { this.priceUnit = v; }
    public void setUrl(String v) { this.url = v; }
}
