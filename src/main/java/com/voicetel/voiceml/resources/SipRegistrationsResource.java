package com.voicetel.voiceml.resources;

import com.voicetel.voiceml.Pager;
import com.voicetel.voiceml.Transport;
import com.voicetel.voiceml.models.ListPageParams;
import com.voicetel.voiceml.models.SipRegistration;
import com.voicetel.voiceml.models.SipRegistrationList;

import java.util.Map;

/** {@code /SIP/Registrations}: the account's registered SIP endpoints. */
public final class SipRegistrationsResource extends BaseResource {

    /**
     * Create the resource; normally obtained from {@code client.sip().registrations()}.
     *
     * @param transport the transport
     */
    public SipRegistrationsResource(Transport transport) {
        super(transport);
    }

    /**
     * {@code GET /SIP/Registrations}.
     *
     * @param params paging, or {@code null}
     * @return the page
     */
    public SipRegistrationList list(ListPageParams params) {
        Map<String, Object> q = params != null ? params.toQuery() : null;
        return decode(transport.request("GET", accountPath("SIP", "Registrations"), q, null),
                SipRegistrationList.class);
    }

    /** @return the first page of registrations */
    public SipRegistrationList list() {
        return list(null);
    }

    /**
     * Lazily iterate every registration.
     *
     * @param params paging, or {@code null}
     * @return the pager
     */
    public Pager<SipRegistration> iterate(ListPageParams params) {
        return pager(() -> list(params), SipRegistrationList.class, SipRegistrationList::getRegistrations);
    }
}
