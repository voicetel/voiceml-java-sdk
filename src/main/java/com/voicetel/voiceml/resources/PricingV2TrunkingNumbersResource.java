package com.voicetel.voiceml.resources;

import com.voicetel.voiceml.Transport;
import com.voicetel.voiceml.models.PricingTrunkingNumber;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * {@code GET /v2/Trunking/Numbers/{DestinationNumber}?OriginationNumber=}. The destination number
 * path segment is URL-encoded; {@code OriginationNumber} is an optional query parameter.
 */
public final class PricingV2TrunkingNumbersResource extends BaseResource {

    PricingV2TrunkingNumbersResource(Transport transport) {
        super(transport);
    }

    public PricingTrunkingNumber fetch(String destinationNumber, String originationNumber) {
        String path = "/v2/Trunking/Numbers/"
                + PricingNumberEncoding.encodeSegment(destinationNumber);
        Map<String, Object> q = null;
        if (originationNumber != null) {
            q = new LinkedHashMap<>();
            q.put("OriginationNumber", originationNumber);
        }
        return decode(transport.request("GET", path, q, null), PricingTrunkingNumber.class);
    }

    public PricingTrunkingNumber fetch(String destinationNumber) {
        return fetch(destinationNumber, null);
    }
}
