package com.voicetel.voiceml.resources;

import com.voicetel.voiceml.Transport;
import com.voicetel.voiceml.models.CreateSipCredentialListRequest;
import com.voicetel.voiceml.models.CreateSipCredentialRequest;
import com.voicetel.voiceml.models.ListPageParams;
import com.voicetel.voiceml.models.SipCredential;
import com.voicetel.voiceml.models.SipCredentialList;
import com.voicetel.voiceml.models.SipCredentialListList;
import com.voicetel.voiceml.models.SipCredentialListPage;
import com.voicetel.voiceml.models.UpdateSipCredentialListRequest;
import com.voicetel.voiceml.models.UpdateSipCredentialRequest;

import java.util.Map;

/** {@code /SIP/CredentialLists} plus per-list /Credentials sub-resource. */
public final class SipCredentialListsResource extends BaseResource {

    public SipCredentialListsResource(Transport transport) {
        super(transport);
    }

    // --- /SIP/CredentialLists CRUD ------------------------------------------

    public SipCredentialListList list(ListPageParams params) {
        Map<String, Object> q = params != null ? params.toQuery() : null;
        return decode(transport.request("GET", accountPath("SIP", "CredentialLists"), q, null),
                SipCredentialListList.class);
    }

    public SipCredentialListList list() { return list(null); }

    public SipCredentialList create(CreateSipCredentialListRequest body) {
        Map<String, Object> form = body != null ? body.toForm() : null;
        return decode(transport.request("POST", accountPath("SIP", "CredentialLists"), null, form),
                SipCredentialList.class);
    }

    public SipCredentialList fetch(String credentialListSid) {
        return decode(transport.request("GET", accountPath("SIP", "CredentialLists", credentialListSid), null, null),
                SipCredentialList.class);
    }

    public SipCredentialList update(String credentialListSid, UpdateSipCredentialListRequest body) {
        Map<String, Object> form = body != null ? body.toForm() : null;
        return decode(transport.request("POST", accountPath("SIP", "CredentialLists", credentialListSid), null, form),
                SipCredentialList.class);
    }

    public void delete(String credentialListSid) {
        transport.request("DELETE", accountPath("SIP", "CredentialLists", credentialListSid), null, null);
    }

    // --- /SIP/CredentialLists/{Sid}/Credentials sub-resource ---------------

    public SipCredentialListPage listCredentials(String credentialListSid, ListPageParams params) {
        Map<String, Object> q = params != null ? params.toQuery() : null;
        return decode(transport.request("GET",
                accountPath("SIP", "CredentialLists", credentialListSid, "Credentials"), q, null),
                SipCredentialListPage.class);
    }

    public SipCredential createCredential(String credentialListSid, CreateSipCredentialRequest body) {
        Map<String, Object> form = body != null ? body.toForm() : null;
        return decode(transport.request("POST",
                accountPath("SIP", "CredentialLists", credentialListSid, "Credentials"), null, form),
                SipCredential.class);
    }

    public SipCredential fetchCredential(String credentialListSid, String credentialSid) {
        return decode(transport.request("GET",
                accountPath("SIP", "CredentialLists", credentialListSid, "Credentials", credentialSid), null, null),
                SipCredential.class);
    }

    public SipCredential updateCredential(String credentialListSid, String credentialSid, UpdateSipCredentialRequest body) {
        Map<String, Object> form = body != null ? body.toForm() : null;
        return decode(transport.request("POST",
                accountPath("SIP", "CredentialLists", credentialListSid, "Credentials", credentialSid), null, form),
                SipCredential.class);
    }

    public void deleteCredential(String credentialListSid, String credentialSid) {
        transport.request("DELETE",
                accountPath("SIP", "CredentialLists", credentialListSid, "Credentials", credentialSid), null, null);
    }
}
