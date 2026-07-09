package com.voicetel.voiceml.resources;

import com.voicetel.voiceml.Transport;
import com.voicetel.voiceml.models.PricingVoiceNumber;

/** {@code GET /v1/Voice/Numbers/{Number}}. The number path segment is URL-encoded. */
public final class PricingV1VoiceNumbersResource extends BaseResource {

    PricingV1VoiceNumbersResource(Transport transport) {
        super(transport);
    }

    public PricingVoiceNumber fetch(String number) {
        String path = "/v1/Voice/Numbers/" + PricingNumberEncoding.encodeSegment(number);
        return decode(transport.request("GET", path, null, null), PricingVoiceNumber.class);
    }
}
