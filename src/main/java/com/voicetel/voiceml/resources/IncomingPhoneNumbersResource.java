package com.voicetel.voiceml.resources;

import com.voicetel.voiceml.Transport;
import com.voicetel.voiceml.models.CreateIncomingPhoneNumberRequest;
import com.voicetel.voiceml.models.IncomingPhoneNumber;
import com.voicetel.voiceml.models.IncomingPhoneNumberList;
import com.voicetel.voiceml.models.ListIncomingPhoneNumbersParams;
import com.voicetel.voiceml.models.UpdateIncomingPhoneNumberRequest;

import java.util.Map;

/**
 * {@code /IncomingPhoneNumbers} — tenant-self-serve DID assignment.
 *
 * <p>Twilio-shape: {@code list / create / get / update / delete} on
 * {@code /2010-04-01/Accounts/{AccountSid}/IncomingPhoneNumbers[/{PhoneNumberSid}]}. The
 * canonical {@code PN}-prefixed sid is the only accepted identifier on
 * fetch/update/delete; use {@link #list} with a {@code phoneNumber} filter to translate from
 * E.164.
 */
public final class IncomingPhoneNumbersResource extends BaseResource {

    public IncomingPhoneNumbersResource(Transport transport) {
        super(transport);
    }

    /** List DIDs assigned to the authenticated tenant, optionally filtered. */
    public IncomingPhoneNumberList list(ListIncomingPhoneNumbersParams params) {
        Map<String, Object> q = params != null ? params.toQuery() : null;
        return decode(
                transport.request("GET", accountPath("IncomingPhoneNumbers"), q, null),
                IncomingPhoneNumberList.class);
    }

    /** List all assigned DIDs (no filters). */
    public IncomingPhoneNumberList list() {
        return list(null);
    }

    /** Assign a DID to the authenticated tenant (idempotent on the same tenant). */
    public IncomingPhoneNumber create(CreateIncomingPhoneNumberRequest req) {
        return decode(
                transport.request("POST", accountPath("IncomingPhoneNumbers"), null, req.toForm()),
                IncomingPhoneNumber.class);
    }

    /** Fetch a single assigned DID by its canonical {@code PN}-prefixed sid. */
    public IncomingPhoneNumber get(String sid) {
        return decode(
                transport.request("GET", accountPath("IncomingPhoneNumbers", sid), null, null),
                IncomingPhoneNumber.class);
    }

    /** Update voice routing on an assigned DID. {@code POST} (not PUT) — matches Twilio. */
    public IncomingPhoneNumber update(String sid, UpdateIncomingPhoneNumberRequest req) {
        return decode(
                transport.request(
                        "POST", accountPath("IncomingPhoneNumbers", sid), null, req.toForm()),
                IncomingPhoneNumber.class);
    }

    /** Release a DID from the authenticated tenant. Idempotent — 204 even when already gone. */
    public void delete(String sid) {
        transport.request("DELETE", accountPath("IncomingPhoneNumbers", sid), null, null);
    }
}
