package com.voicetel.voiceml.resources;

import com.voicetel.voiceml.Transport;

/**
 * Top-level surface for Twilio pricing.twilio.com (#18), served on the default host — VoiceML has
 * no dedicated pricing subdomain. All operations are read-only {@code GET}s.
 *
 * <pre>{@code
 * client.pricing().v1().voice().countries().list();
 * client.pricing().v1().voice().numbers().fetch("+18005551234");
 * client.pricing().v2().trunking().countries().fetch("US");
 * }</pre>
 */
public final class PricingResource {

    private final PricingV1Resource v1;
    private final PricingV2Resource v2;

    public PricingResource(Transport transport) {
        this.v1 = new PricingV1Resource(transport);
        this.v2 = new PricingV2Resource(transport);
    }

    public PricingV1Resource v1() { return v1; }
    public PricingV2Resource v2() { return v2; }
}
