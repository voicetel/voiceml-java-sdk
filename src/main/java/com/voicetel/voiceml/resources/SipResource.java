package com.voicetel.voiceml.resources;

import com.voicetel.voiceml.Transport;

/**
 * Top-level {@code /SIP/*} surface. Three sub-resources:
 *
 * <ul>
 *   <li>{@link #domains()} — SIP ingress endpoints ({@code SD…}) plus domain↔mapping bindings.
 *   <li>{@link #credentialLists()} — named bags of SIP-digest credentials ({@code CL…}) and per-list credentials ({@code CR…}).
 *   <li>{@link #ipAccessControlLists()} — named bags of CIDR-bound IPs ({@code AL…}) and per-list IP entries ({@code IP…}).
 * </ul>
 */
public final class SipResource {

    private final SipDomainsResource domains;
    private final SipCredentialListsResource credentialLists;
    private final SipIpAccessControlListsResource ipAccessControlLists;

    public SipResource(Transport transport) {
        this.domains = new SipDomainsResource(transport);
        this.credentialLists = new SipCredentialListsResource(transport);
        this.ipAccessControlLists = new SipIpAccessControlListsResource(transport);
    }

    public SipDomainsResource domains() { return domains; }
    public SipCredentialListsResource credentialLists() { return credentialLists; }
    public SipIpAccessControlListsResource ipAccessControlLists() { return ipAccessControlLists; }
}
