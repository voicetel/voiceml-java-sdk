package com.voicetel.voiceml.resources;

import com.voicetel.voiceml.Pager;
import com.voicetel.voiceml.Transport;
import com.voicetel.voiceml.models.CreateOutgoingCallerIdRequest;
import com.voicetel.voiceml.models.ListOutgoingCallerIdsParams;
import com.voicetel.voiceml.models.OutgoingCallerId;
import com.voicetel.voiceml.models.OutgoingCallerIdList;
import com.voicetel.voiceml.models.UpdateOutgoingCallerIdRequest;
import com.voicetel.voiceml.models.ValidationRequest;

import java.util.Map;

/** {@code /OutgoingCallerIds}: verified caller IDs. */
public final class OutgoingCallerIdsResource extends BaseResource {

    /**
     * Create the resource; normally obtained from {@code client.outgoingCallerIds()}.
     *
     * @param transport the transport
     */
    public OutgoingCallerIdsResource(Transport transport) {
        super(transport);
    }

    /**
     * {@code GET /OutgoingCallerIds}.
     *
     * @param params filters and paging, or {@code null}
     * @return the page
     */
    public OutgoingCallerIdList list(ListOutgoingCallerIdsParams params) {
        Map<String, Object> q = params != null ? params.toQuery() : null;
        return decode(transport.request("GET", accountPath("OutgoingCallerIds"), q, null),
                OutgoingCallerIdList.class);
    }

    /** @return the first page of caller IDs */
    public OutgoingCallerIdList list() {
        return list(null);
    }

    /**
     * Lazily iterate every caller ID matching {@code params}.
     *
     * @param params filters and paging, or {@code null}
     * @return the pager
     */
    public Pager<OutgoingCallerId> iterate(ListOutgoingCallerIdsParams params) {
        return pager(() -> list(params), OutgoingCallerIdList.class, OutgoingCallerIdList::getOutgoingCallerIds);
    }

    /**
     * {@code POST /OutgoingCallerIds}: start validating a number. The response carries the
     * validation code the callee must enter.
     *
     * @param body the number to validate
     * @return the validation request
     */
    public ValidationRequest create(CreateOutgoingCallerIdRequest body) {
        return decode(transport.request("POST", accountPath("OutgoingCallerIds"), null, body.toForm()),
                ValidationRequest.class);
    }

    /**
     * {@code GET /OutgoingCallerIds/{Sid}}.
     *
     * @param sid the caller ID SID
     * @return the caller ID
     */
    public OutgoingCallerId fetch(String sid) {
        return decode(transport.request("GET", accountPath("OutgoingCallerIds", sid), null, null),
                OutgoingCallerId.class);
    }

    /**
     * {@code POST /OutgoingCallerIds/{Sid}}.
     *
     * @param sid the caller ID SID
     * @param body the changes
     * @return the caller ID
     */
    public OutgoingCallerId update(String sid, UpdateOutgoingCallerIdRequest body) {
        return decode(transport.request("POST", accountPath("OutgoingCallerIds", sid), null, body.toForm()),
                OutgoingCallerId.class);
    }

    /**
     * {@code DELETE /OutgoingCallerIds/{Sid}}.
     *
     * @param sid the caller ID SID
     */
    public void delete(String sid) {
        transport.request("DELETE", accountPath("OutgoingCallerIds", sid), null, null);
    }
}
