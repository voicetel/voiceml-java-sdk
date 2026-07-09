package com.voicetel.voiceml.resources;

import com.voicetel.voiceml.Transport;
import com.voicetel.voiceml.models.PricingPhoneNumberCountry;

/** {@code /v1/PhoneNumbers} pricing: {@code countries()} only. */
public final class PricingV1PhoneNumbersResource {

    private final PricingCountriesResource<PricingPhoneNumberCountry> countries;

    PricingV1PhoneNumbersResource(Transport transport) {
        this.countries = new PricingCountriesResource<>(
                transport, "/v1/PhoneNumbers/Countries", PricingPhoneNumberCountry.class);
    }

    public PricingCountriesResource<PricingPhoneNumberCountry> countries() { return countries; }
}
