package com.voicetel.voiceml.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;

/** {@code GET /v1/PhoneNumbers/Countries/{IsoCountry}} body. */
@JsonIgnoreProperties(ignoreUnknown = true)
public class PricingPhoneNumberCountry {

    @JsonProperty("country") private String country;
    @JsonProperty("iso_country") private String isoCountry;
    @JsonProperty("phone_number_prices")
    private List<PricingPhoneNumberPrice> phoneNumberPrices = new ArrayList<>();
    @JsonProperty("price_unit") private String priceUnit;
    @JsonProperty("url") private String url;

    public String getCountry() { return country; }
    public String getIsoCountry() { return isoCountry; }
    public List<PricingPhoneNumberPrice> getPhoneNumberPrices() { return phoneNumberPrices; }
    public String getPriceUnit() { return priceUnit; }
    public String getUrl() { return url; }

    public void setCountry(String v) { this.country = v; }
    public void setIsoCountry(String v) { this.isoCountry = v; }
    public void setPhoneNumberPrices(List<PricingPhoneNumberPrice> v) {
        this.phoneNumberPrices = v != null ? v : new ArrayList<>();
    }
    public void setPriceUnit(String v) { this.priceUnit = v; }
    public void setUrl(String v) { this.url = v; }
}
