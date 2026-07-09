package com.voicetel.voiceml.resources;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

/**
 * URL-encoding for Pricing number path segments. E.164 numbers carry a leading {@code +}, which
 * must be percent-encoded ({@code +} → {@code %2B}) so the segment isn't misread as a space or a
 * bare plus by the server, mirroring how Routes V2 encodes phone-number path segments.
 */
final class PricingNumberEncoding {

    private PricingNumberEncoding() {}

    static String encodeSegment(String value) {
        return URLEncoder.encode(value, StandardCharsets.UTF_8);
    }
}
