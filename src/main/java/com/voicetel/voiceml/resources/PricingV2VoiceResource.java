package com.voicetel.voiceml.resources;

import com.voicetel.voiceml.Transport;
import com.voicetel.voiceml.models.PricingVoiceCountryV2;

/** {@code /v2/Voice} pricing: {@code countries()} + {@code numbers()}. */
public final class PricingV2VoiceResource {

    private final PricingCountriesResource<PricingVoiceCountryV2> countries;
    private final PricingV2VoiceNumbersResource numbers;

    PricingV2VoiceResource(Transport transport) {
        this.countries = new PricingCountriesResource<>(
                transport, "/v2/Voice/Countries", PricingVoiceCountryV2.class);
        this.numbers = new PricingV2VoiceNumbersResource(transport);
    }

    public PricingCountriesResource<PricingVoiceCountryV2> countries() { return countries; }
    public PricingV2VoiceNumbersResource numbers() { return numbers; }
}
