package com.voicetel.voiceml.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;

/** Shared {@code Countries} list envelope returned by every Pricing {@code countries().list()}. */
@JsonIgnoreProperties(ignoreUnknown = true)
public class PricingCountriesList {

    @JsonProperty("countries") private List<PricingCountryRef> countries = new ArrayList<>();
    @JsonProperty("meta") private VoiceV1Meta meta;

    public List<PricingCountryRef> getCountries() { return countries; }
    public VoiceV1Meta getMeta() { return meta; }

    public void setCountries(List<PricingCountryRef> v) {
        this.countries = v != null ? v : new ArrayList<>();
    }
    public void setMeta(VoiceV1Meta v) { this.meta = v; }
}
