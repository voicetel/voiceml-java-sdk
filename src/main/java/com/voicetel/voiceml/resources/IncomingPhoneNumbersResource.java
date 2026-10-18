package com.voicetel.voiceml.resources;

import com.voicetel.voiceml.Pager;
import com.voicetel.voiceml.Transport;
import com.voicetel.voiceml.models.CreateIncomingPhoneNumberRequest;
import com.voicetel.voiceml.models.CreateTypedIncomingPhoneNumberRequest;
import com.voicetel.voiceml.models.IncomingPhoneNumber;
import com.voicetel.voiceml.models.IncomingPhoneNumberList;
import com.voicetel.voiceml.models.ListIncomingPhoneNumbersParams;
import com.voicetel.voiceml.models.ListTypedIncomingPhoneNumbersParams;
import com.voicetel.voiceml.models.UpdateIncomingPhoneNumberRequest;

import java.util.Map;

/**
 * {@code /IncomingPhoneNumbers} — tenant-self-serve DID assignment.
 *
 * <p>Twilio-compatible: {@code list / create / get / update / delete} on
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

    /**
     * Auto-paginate through all pages of {@code GET /IncomingPhoneNumbers}, collecting every
     * {@link IncomingPhoneNumber} into a single list.
     */
    public Pager<IncomingPhoneNumber> iterate(ListIncomingPhoneNumbersParams params) {
        return pager(() -> list(params), IncomingPhoneNumberList.class,
                IncomingPhoneNumberList::getIncomingPhoneNumbers);
    }

    /** Auto-paginate all assigned DIDs (no filters). */
    public Pager<IncomingPhoneNumber> iterate() {
        return iterate(null);
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

    public IncomingPhoneNumberList listLocal(ListTypedIncomingPhoneNumbersParams params) {
        return listTyped("Local", params);
    }

    public IncomingPhoneNumberList listLocal() {
        return listLocal(null);
    }

    /** Auto-paginate through all Local DIDs. */
    public Pager<IncomingPhoneNumber> iterateLocal(ListTypedIncomingPhoneNumbersParams params) {
        return iterateTyped("Local", params);
    }

    /** Auto-paginate all Local DIDs (no filters). */
    public Pager<IncomingPhoneNumber> iterateLocal() {
        return iterateLocal(null);
    }

    public IncomingPhoneNumber createLocal(CreateTypedIncomingPhoneNumberRequest req) {
        return createTyped("Local", req);
    }

    public IncomingPhoneNumberList listMobile(ListTypedIncomingPhoneNumbersParams params) {
        return listTyped("Mobile", params);
    }

    public IncomingPhoneNumberList listMobile() {
        return listMobile(null);
    }

    /** Auto-paginate through all Mobile DIDs. */
    public Pager<IncomingPhoneNumber> iterateMobile(ListTypedIncomingPhoneNumbersParams params) {
        return iterateTyped("Mobile", params);
    }

    /** Auto-paginate all Mobile DIDs (no filters). */
    public Pager<IncomingPhoneNumber> iterateMobile() {
        return iterateMobile(null);
    }

    public IncomingPhoneNumber createMobile(CreateTypedIncomingPhoneNumberRequest req) {
        return createTyped("Mobile", req);
    }

    public IncomingPhoneNumberList listTollFree(ListTypedIncomingPhoneNumbersParams params) {
        return listTyped("TollFree", params);
    }

    public IncomingPhoneNumberList listTollFree() {
        return listTollFree(null);
    }

    /** Auto-paginate through all TollFree DIDs. */
    public Pager<IncomingPhoneNumber> iterateTollFree(ListTypedIncomingPhoneNumbersParams params) {
        return iterateTyped("TollFree", params);
    }

    /** Auto-paginate all TollFree DIDs (no filters). */
    public Pager<IncomingPhoneNumber> iterateTollFree() {
        return iterateTollFree(null);
    }

    public IncomingPhoneNumber createTollFree(CreateTypedIncomingPhoneNumberRequest req) {
        return createTyped("TollFree", req);
    }

    private IncomingPhoneNumberList listTyped(String kind, ListTypedIncomingPhoneNumbersParams params) {
        Map<String, Object> q = params != null ? params.toQuery() : null;
        return decode(
                transport.request("GET", accountPath("IncomingPhoneNumbers", kind), q, null),
                IncomingPhoneNumberList.class);
    }

    private IncomingPhoneNumber createTyped(String kind, CreateTypedIncomingPhoneNumberRequest req) {
        return decode(
                transport.request(
                        "POST", accountPath("IncomingPhoneNumbers", kind), null, req.toForm()),
                IncomingPhoneNumber.class);
    }

    private Pager<IncomingPhoneNumber> iterateTyped(String kind, ListTypedIncomingPhoneNumbersParams params) {
        return pager(() -> listTyped(kind, params), IncomingPhoneNumberList.class,
                IncomingPhoneNumberList::getIncomingPhoneNumbers);
    }
}
