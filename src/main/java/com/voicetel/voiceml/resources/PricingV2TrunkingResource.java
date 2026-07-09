package com.voicetel.voiceml.resources;

import com.voicetel.voiceml.Transport;
import com.voicetel.voiceml.models.PricingTrunkingCountry;

/** {@code /v2/Trunking} pricing: {@code countries()} + {@code numbers()}. */
public final class PricingV2TrunkingResource {

    private final PricingCountriesResource<PricingTrunkingCountry> countries;
    private final PricingV2TrunkingNumbersResource numbers;

    PricingV2TrunkingResource(Transport transport) {
        this.countries = new PricingCountriesResource<>(
                transport, "/v2/Trunking/Countries", PricingTrunkingCountry.class);
        this.numbers = new PricingV2TrunkingNumbersResource(transport);
    }

    public PricingCountriesResource<PricingTrunkingCountry> countries() { return countries; }
    public PricingV2TrunkingNumbersResource numbers() { return numbers; }
}
