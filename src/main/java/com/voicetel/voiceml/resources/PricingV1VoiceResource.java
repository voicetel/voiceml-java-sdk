package com.voicetel.voiceml.resources;

import com.voicetel.voiceml.Transport;
import com.voicetel.voiceml.models.PricingVoiceCountry;

/** {@code /v1/Voice} pricing: {@code countries()} + {@code numbers()}. */
public final class PricingV1VoiceResource {

    private final PricingCountriesResource<PricingVoiceCountry> countries;
    private final PricingV1VoiceNumbersResource numbers;

    PricingV1VoiceResource(Transport transport) {
        this.countries = new PricingCountriesResource<>(
                transport, "/v1/Voice/Countries", PricingVoiceCountry.class);
        this.numbers = new PricingV1VoiceNumbersResource(transport);
    }

    public PricingCountriesResource<PricingVoiceCountry> countries() { return countries; }
    public PricingV1VoiceNumbersResource numbers() { return numbers; }
}
