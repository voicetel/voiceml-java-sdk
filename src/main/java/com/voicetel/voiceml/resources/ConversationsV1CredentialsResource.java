package com.voicetel.voiceml.resources;

import com.voicetel.voiceml.Transport;
import com.voicetel.voiceml.models.ConversationsV1Credential;
import com.voicetel.voiceml.models.ConversationsV1CredentialList;
import com.voicetel.voiceml.models.CreateConversationsV1CredentialRequest;
import com.voicetel.voiceml.models.ListPageParams;
import com.voicetel.voiceml.models.UpdateConversationsV1CredentialRequest;

import java.util.Map;

/** Operations on {@code /v1/Credentials[/{Sid}]} (push credentials). */
public final class ConversationsV1CredentialsResource extends BaseResource {

    public ConversationsV1CredentialsResource(Transport transport) {
        super(transport);
    }

    public ConversationsV1Credential create(CreateConversationsV1CredentialRequest body) {
        Map<String, Object> form = body != null ? body.toForm() : null;
        return decode(transport.request("POST", "/v1/Credentials", null, form),
                ConversationsV1Credential.class);
    }

    public ConversationsV1CredentialList list(ListPageParams params) {
        Map<String, Object> q = params != null ? params.toQuery() : null;
        return decode(transport.request("GET", "/v1/Credentials", q, null),
                ConversationsV1CredentialList.class);
    }

    public ConversationsV1CredentialList list() { return list(null); }

    public ConversationsV1Credential fetch(String sid) {
        return decode(transport.request("GET", "/v1/Credentials/" + sid, null, null),
                ConversationsV1Credential.class);
    }

    public ConversationsV1Credential update(String sid, UpdateConversationsV1CredentialRequest body) {
        Map<String, Object> form = body != null ? body.toForm() : null;
        return decode(transport.request("POST", "/v1/Credentials/" + sid, null, form),
                ConversationsV1Credential.class);
    }

    public void delete(String sid) {
        transport.request("DELETE", "/v1/Credentials/" + sid, null, null);
    }
}
