package com.voicetel.voiceml.resources;

import com.voicetel.voiceml.Transport;

/** {@code /v2} pricing: {@code voice()}, {@code trunking()}. */
public final class PricingV2Resource {

    private final PricingV2VoiceResource voice;
    private final PricingV2TrunkingResource trunking;

    PricingV2Resource(Transport transport) {
        this.voice = new PricingV2VoiceResource(transport);
        this.trunking = new PricingV2TrunkingResource(transport);
    }

    public PricingV2VoiceResource voice() { return voice; }
    public PricingV2TrunkingResource trunking() { return trunking; }
}
