package com.voicetel.voiceml.resources;

import com.voicetel.voiceml.Transport;
import com.voicetel.voiceml.models.PricingVoiceNumberV2;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * {@code GET /v2/Voice/Numbers/{DestinationNumber}?OriginationNumber=}. The destination number
 * path segment is URL-encoded; {@code OriginationNumber} is an optional query parameter.
 */
public final class PricingV2VoiceNumbersResource extends BaseResource {

    PricingV2VoiceNumbersResource(Transport transport) {
        super(transport);
    }

    public PricingVoiceNumberV2 fetch(String destinationNumber, String originationNumber) {
        String path = "/v2/Voice/Numbers/" + PricingNumberEncoding.encodeSegment(destinationNumber);
        Map<String, Object> q = null;
        if (originationNumber != null) {
            q = new LinkedHashMap<>();
            q.put("OriginationNumber", originationNumber);
        }
        return decode(transport.request("GET", path, q, null), PricingVoiceNumberV2.class);
    }

    public PricingVoiceNumberV2 fetch(String destinationNumber) {
        return fetch(destinationNumber, null);
    }
}
