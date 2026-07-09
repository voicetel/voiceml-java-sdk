package com.voicetel.voiceml.resources;

import com.voicetel.voiceml.Transport;

/** {@code /v1} pricing: {@code voice()}, {@code messaging()}, {@code phoneNumbers()}. */
public final class PricingV1Resource {

    private final PricingV1VoiceResource voice;
    private final PricingV1MessagingResource messaging;
    private final PricingV1PhoneNumbersResource phoneNumbers;

    PricingV1Resource(Transport transport) {
        this.voice = new PricingV1VoiceResource(transport);
        this.messaging = new PricingV1MessagingResource(transport);
        this.phoneNumbers = new PricingV1PhoneNumbersResource(transport);
    }

    public PricingV1VoiceResource voice() { return voice; }
    public PricingV1MessagingResource messaging() { return messaging; }
    public PricingV1PhoneNumbersResource phoneNumbers() { return phoneNumbers; }
}
