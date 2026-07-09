package com.voicetel.voiceml.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;

/** {@code GET /v1/Messaging/Countries/{IsoCountry}} body. */
@JsonIgnoreProperties(ignoreUnknown = true)
public class PricingMessagingCountry {

    @JsonProperty("country") private String country;
    @JsonProperty("iso_country") private String isoCountry;
    @JsonProperty("outbound_sms_prices")
    private List<PricingOutboundSMSPrice> outboundSmsPrices = new ArrayList<>();
    @JsonProperty("inbound_sms_prices")
    private List<PricingInboundCallPrice> inboundSmsPrices = new ArrayList<>();
    @JsonProperty("price_unit") private String priceUnit;
    @JsonProperty("url") private String url;

    public String getCountry() { return country; }
    public String getIsoCountry() { return isoCountry; }
    public List<PricingOutboundSMSPrice> getOutboundSmsPrices() { return outboundSmsPrices; }
    public List<PricingInboundCallPrice> getInboundSmsPrices() { return inboundSmsPrices; }
    public String getPriceUnit() { return priceUnit; }
    public String getUrl() { return url; }

    public void setCountry(String v) { this.country = v; }
    public void setIsoCountry(String v) { this.isoCountry = v; }
    public void setOutboundSmsPrices(List<PricingOutboundSMSPrice> v) {
        this.outboundSmsPrices = v != null ? v : new ArrayList<>();
    }
    public void setInboundSmsPrices(List<PricingInboundCallPrice> v) {
        this.inboundSmsPrices = v != null ? v : new ArrayList<>();
    }
    public void setPriceUnit(String v) { this.priceUnit = v; }
    public void setUrl(String v) { this.url = v; }
}
