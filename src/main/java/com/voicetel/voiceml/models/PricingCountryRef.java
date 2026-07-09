package com.voicetel.voiceml.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/** A country entry in a Pricing {@code Countries} list envelope. */
@JsonIgnoreProperties(ignoreUnknown = true)
public class PricingCountryRef {

    @JsonProperty("country") private String country;
    @JsonProperty("iso_country") private String isoCountry;
    @JsonProperty("url") private String url;

    public String getCountry() { return country; }
    public String getIsoCountry() { return isoCountry; }
    public String getUrl() { return url; }

    public void setCountry(String v) { this.country = v; }
    public void setIsoCountry(String v) { this.isoCountry = v; }
    public void setUrl(String v) { this.url = v; }
}
