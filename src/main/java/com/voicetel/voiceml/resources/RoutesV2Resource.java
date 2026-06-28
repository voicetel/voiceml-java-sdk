package com.voicetel.voiceml.resources;

import com.voicetel.voiceml.Transport;

/**
 * Top-level {@code /v2/*} surface — Twilio routes/v2 Inbound Processing
 * Region API. Sub-resources:
 *
 * <ul>
 *   <li>{@link #sipDomains()} — {@code /v2/SipDomains/{SipDomain}}.
 *   <li>{@link #phoneNumbers()} — {@code /v2/PhoneNumbers/{PhoneNumber}}.
 * </ul>
 */
public final class RoutesV2Resource {

    private final RoutesV2SipDomainsResource sipDomains;
    private final RoutesV2PhoneNumbersResource phoneNumbers;

    public RoutesV2Resource(Transport transport) {
        this.sipDomains = new RoutesV2SipDomainsResource(transport);
        this.phoneNumbers = new RoutesV2PhoneNumbersResource(transport);
    }

    public RoutesV2SipDomainsResource sipDomains() { return sipDomains; }
    public RoutesV2PhoneNumbersResource phoneNumbers() { return phoneNumbers; }
}
