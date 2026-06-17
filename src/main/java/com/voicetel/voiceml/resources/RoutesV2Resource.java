package com.voicetel.voiceml.resources;

import com.voicetel.voiceml.Transport;

/**
 * Top-level {@code /v2/*} surface — Twilio routes/v2 Inbound Processing
 * Region API. One sub-resource today: {@link #sipDomains()}.
 */
public final class RoutesV2Resource {

    private final RoutesV2SipDomainsResource sipDomains;

    public RoutesV2Resource(Transport transport) {
        this.sipDomains = new RoutesV2SipDomainsResource(transport);
    }

    public RoutesV2SipDomainsResource sipDomains() { return sipDomains; }
}
