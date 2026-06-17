package com.voicetel.voiceml.resources;

import com.voicetel.voiceml.Transport;
import com.voicetel.voiceml.models.CreateSipCredentialListMappingRequest;
import com.voicetel.voiceml.models.CreateSipDomainRequest;
import com.voicetel.voiceml.models.CreateSipIpAccessControlListMappingRequest;
import com.voicetel.voiceml.models.ListPageParams;
import com.voicetel.voiceml.models.SipCredentialListMappingList;
import com.voicetel.voiceml.models.SipDomain;
import com.voicetel.voiceml.models.SipDomainList;
import com.voicetel.voiceml.models.SipDomainMapping;
import com.voicetel.voiceml.models.SipIpAccessControlListMappingList;
import com.voicetel.voiceml.models.UpdateSipDomainRequest;

import java.util.Map;

/**
 * {@code /SIP/Domains} — SIP ingress endpoints plus the four mapping
 * endpoints attached to a SipDomain (historical aliases +
 * {@code /Auth/Calls/…} + {@code /Auth/Registrations/CredentialListMappings}).
 */
public final class SipDomainsResource extends BaseResource {

    public SipDomainsResource(Transport transport) {
        super(transport);
    }

    // --- /SIP/Domains CRUD --------------------------------------------------

    public SipDomainList list(ListPageParams params) {
        Map<String, Object> q = params != null ? params.toQuery() : null;
        return decode(transport.request("GET", accountPath("SIP", "Domains"), q, null), SipDomainList.class);
    }

    public SipDomainList list() { return list(null); }

    public SipDomain create(CreateSipDomainRequest body) {
        Map<String, Object> form = body != null ? body.toForm() : null;
        return decode(transport.request("POST", accountPath("SIP", "Domains"), null, form), SipDomain.class);
    }

    public SipDomain fetch(String domainSid) {
        return decode(transport.request("GET", accountPath("SIP", "Domains", domainSid), null, null), SipDomain.class);
    }

    public SipDomain update(String domainSid, UpdateSipDomainRequest body) {
        Map<String, Object> form = body != null ? body.toForm() : null;
        return decode(transport.request("POST", accountPath("SIP", "Domains", domainSid), null, form), SipDomain.class);
    }

    public void delete(String domainSid) {
        transport.request("DELETE", accountPath("SIP", "Domains", domainSid), null, null);
    }

    // --- /SIP/Domains/{Sid}/CredentialListMappings (historical) ------------

    public SipCredentialListMappingList listCredentialListMappings(String domainSid, ListPageParams params) {
        Map<String, Object> q = params != null ? params.toQuery() : null;
        return decode(transport.request("GET",
                accountPath("SIP", "Domains", domainSid, "CredentialListMappings"), q, null),
                SipCredentialListMappingList.class);
    }

    public SipDomainMapping createCredentialListMapping(String domainSid, CreateSipCredentialListMappingRequest body) {
        Map<String, Object> form = body != null ? body.toForm() : null;
        return decode(transport.request("POST",
                accountPath("SIP", "Domains", domainSid, "CredentialListMappings"), null, form),
                SipDomainMapping.class);
    }

    public SipDomainMapping fetchCredentialListMapping(String domainSid, String mappingSid) {
        return decode(transport.request("GET",
                accountPath("SIP", "Domains", domainSid, "CredentialListMappings", mappingSid), null, null),
                SipDomainMapping.class);
    }

    public void deleteCredentialListMapping(String domainSid, String mappingSid) {
        transport.request("DELETE",
                accountPath("SIP", "Domains", domainSid, "CredentialListMappings", mappingSid), null, null);
    }

    // --- /SIP/Domains/{Sid}/IpAccessControlListMappings (historical) -------

    public SipIpAccessControlListMappingList listIpAccessControlListMappings(String domainSid, ListPageParams params) {
        Map<String, Object> q = params != null ? params.toQuery() : null;
        return decode(transport.request("GET",
                accountPath("SIP", "Domains", domainSid, "IpAccessControlListMappings"), q, null),
                SipIpAccessControlListMappingList.class);
    }

    public SipDomainMapping createIpAccessControlListMapping(String domainSid, CreateSipIpAccessControlListMappingRequest body) {
        Map<String, Object> form = body != null ? body.toForm() : null;
        return decode(transport.request("POST",
                accountPath("SIP", "Domains", domainSid, "IpAccessControlListMappings"), null, form),
                SipDomainMapping.class);
    }

    public SipDomainMapping fetchIpAccessControlListMapping(String domainSid, String mappingSid) {
        return decode(transport.request("GET",
                accountPath("SIP", "Domains", domainSid, "IpAccessControlListMappings", mappingSid), null, null),
                SipDomainMapping.class);
    }

    public void deleteIpAccessControlListMapping(String domainSid, String mappingSid) {
        transport.request("DELETE",
                accountPath("SIP", "Domains", domainSid, "IpAccessControlListMappings", mappingSid), null, null);
    }

    // --- /SIP/Domains/{Sid}/Auth/Calls/CredentialListMappings --------------

    public SipCredentialListMappingList listAuthCallsCredentialListMappings(String domainSid, ListPageParams params) {
        Map<String, Object> q = params != null ? params.toQuery() : null;
        return decode(transport.request("GET",
                accountPath("SIP", "Domains", domainSid, "Auth", "Calls", "CredentialListMappings"), q, null),
                SipCredentialListMappingList.class);
    }

    public SipDomainMapping createAuthCallsCredentialListMapping(String domainSid, CreateSipCredentialListMappingRequest body) {
        Map<String, Object> form = body != null ? body.toForm() : null;
        return decode(transport.request("POST",
                accountPath("SIP", "Domains", domainSid, "Auth", "Calls", "CredentialListMappings"), null, form),
                SipDomainMapping.class);
    }

    public SipDomainMapping fetchAuthCallsCredentialListMapping(String domainSid, String mappingSid) {
        return decode(transport.request("GET",
                accountPath("SIP", "Domains", domainSid, "Auth", "Calls", "CredentialListMappings", mappingSid), null, null),
                SipDomainMapping.class);
    }

    public void deleteAuthCallsCredentialListMapping(String domainSid, String mappingSid) {
        transport.request("DELETE",
                accountPath("SIP", "Domains", domainSid, "Auth", "Calls", "CredentialListMappings", mappingSid), null, null);
    }

    // --- /SIP/Domains/{Sid}/Auth/Calls/IpAccessControlListMappings ---------

    public SipIpAccessControlListMappingList listAuthCallsIpAccessControlListMappings(String domainSid, ListPageParams params) {
        Map<String, Object> q = params != null ? params.toQuery() : null;
        return decode(transport.request("GET",
                accountPath("SIP", "Domains", domainSid, "Auth", "Calls", "IpAccessControlListMappings"), q, null),
                SipIpAccessControlListMappingList.class);
    }

    public SipDomainMapping createAuthCallsIpAccessControlListMapping(String domainSid, CreateSipIpAccessControlListMappingRequest body) {
        Map<String, Object> form = body != null ? body.toForm() : null;
        return decode(transport.request("POST",
                accountPath("SIP", "Domains", domainSid, "Auth", "Calls", "IpAccessControlListMappings"), null, form),
                SipDomainMapping.class);
    }

    public SipDomainMapping fetchAuthCallsIpAccessControlListMapping(String domainSid, String mappingSid) {
        return decode(transport.request("GET",
                accountPath("SIP", "Domains", domainSid, "Auth", "Calls", "IpAccessControlListMappings", mappingSid), null, null),
                SipDomainMapping.class);
    }

    public void deleteAuthCallsIpAccessControlListMapping(String domainSid, String mappingSid) {
        transport.request("DELETE",
                accountPath("SIP", "Domains", domainSid, "Auth", "Calls", "IpAccessControlListMappings", mappingSid), null, null);
    }

    // --- /SIP/Domains/{Sid}/Auth/Registrations/CredentialListMappings ------

    public SipCredentialListMappingList listAuthRegistrationsCredentialListMappings(String domainSid, ListPageParams params) {
        Map<String, Object> q = params != null ? params.toQuery() : null;
        return decode(transport.request("GET",
                accountPath("SIP", "Domains", domainSid, "Auth", "Registrations", "CredentialListMappings"), q, null),
                SipCredentialListMappingList.class);
    }

    public SipDomainMapping createAuthRegistrationsCredentialListMapping(String domainSid, CreateSipCredentialListMappingRequest body) {
        Map<String, Object> form = body != null ? body.toForm() : null;
        return decode(transport.request("POST",
                accountPath("SIP", "Domains", domainSid, "Auth", "Registrations", "CredentialListMappings"), null, form),
                SipDomainMapping.class);
    }

    public SipDomainMapping fetchAuthRegistrationsCredentialListMapping(String domainSid, String mappingSid) {
        return decode(transport.request("GET",
                accountPath("SIP", "Domains", domainSid, "Auth", "Registrations", "CredentialListMappings", mappingSid), null, null),
                SipDomainMapping.class);
    }

    public void deleteAuthRegistrationsCredentialListMapping(String domainSid, String mappingSid) {
        transport.request("DELETE",
                accountPath("SIP", "Domains", domainSid, "Auth", "Registrations", "CredentialListMappings", mappingSid), null, null);
    }
}
