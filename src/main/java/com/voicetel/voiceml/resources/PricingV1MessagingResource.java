package com.voicetel.voiceml.resources;

import com.voicetel.voiceml.Transport;
import com.voicetel.voiceml.models.PricingMessagingCountry;

/** {@code /v1/Messaging} pricing: {@code countries()} only. */
public final class PricingV1MessagingResource {

    private final PricingCountriesResource<PricingMessagingCountry> countries;

    PricingV1MessagingResource(Transport transport) {
        this.countries = new PricingCountriesResource<>(
                transport, "/v1/Messaging/Countries", PricingMessagingCountry.class);
    }

    public PricingCountriesResource<PricingMessagingCountry> countries() { return countries; }
}
