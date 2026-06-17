package com.voicetel.voiceml.resources;

import com.voicetel.voiceml.Transport;
import com.voicetel.voiceml.models.RoutesV2SipDomain;
import com.voicetel.voiceml.models.UpdateRoutesV2SipDomainRequest;

import java.util.Map;

/**
 * Operations on {@code /v2/SipDomains/{SipDomain}}. Keyed by domain name —
 * the account is resolved from HTTP Basic auth, so paths bypass the
 * {@code /2010-04-01/Accounts/{Sid}/} prefix used by the rest of the SDK.
 */
public final class RoutesV2SipDomainsResource extends BaseResource {

    public RoutesV2SipDomainsResource(Transport transport) {
        super(transport);
    }

    /** Fetch a domain's Inbound Processing Region binding. */
    public RoutesV2SipDomain fetch(String domainName) {
        return decode(transport.request("GET", "/v2/SipDomains/" + domainName, null, null),
                RoutesV2SipDomain.class);
    }

    /** Update a domain's voice region and/or friendly name. */
    public RoutesV2SipDomain update(String domainName, UpdateRoutesV2SipDomainRequest body) {
        Map<String, Object> form = body != null ? body.toForm() : null;
        return decode(transport.request("POST", "/v2/SipDomains/" + domainName, null, form),
                RoutesV2SipDomain.class);
    }
}
