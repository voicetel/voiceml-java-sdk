package com.voicetel.voiceml.resources;

import com.voicetel.voiceml.Transport;
import com.voicetel.voiceml.models.CreateMessagingServicePhoneNumberRequest;
import com.voicetel.voiceml.models.ListMessagingServicePhoneNumbersParams;
import com.voicetel.voiceml.models.MessagingServicePhoneNumber;
import com.voicetel.voiceml.models.MessagingServicePhoneNumberList;

import java.util.Map;

/**
 * The sender pool of one Messaging Service: {@code /v1/Services/{Sid}/PhoneNumbers} on the messaging
 * host (the server answers 404 for these paths on any other host).
 */
public final class MessagingV1ServicePhoneNumbersResource extends BaseResource {

    private final String serviceSid;

    MessagingV1ServicePhoneNumbersResource(Transport transport, String serviceSid) {
        super(transport);
        this.serviceSid = serviceSid;
    }

    private String base() {
        return "/v1/Services/" + seg(serviceSid) + "/PhoneNumbers";
    }

    /**
     * {@code GET /v1/Services/{Sid}/PhoneNumbers}.
     *
     * @param params paging, or {@code null}
     * @return the page
     */
    public MessagingServicePhoneNumberList list(ListMessagingServicePhoneNumbersParams params) {
        Map<String, Object> q = params != null ? params.toQuery() : null;
        return decode(transport.request("GET", base(), q, null), MessagingServicePhoneNumberList.class);
    }

    /** @return the first page of the sender pool */
    public MessagingServicePhoneNumberList list() {
        return list(null);
    }

    /**
     * {@code POST /v1/Services/{Sid}/PhoneNumbers}: add an IncomingPhoneNumber to the pool.
     *
     * @param body the {@code PhoneNumberSid} to add
     * @return the pool entry
     */
    public MessagingServicePhoneNumber create(CreateMessagingServicePhoneNumberRequest body) {
        return decode(transport.request("POST", base(), null, body.toForm()), MessagingServicePhoneNumber.class);
    }

    /**
     * {@code GET /v1/Services/{Sid}/PhoneNumbers/{Sid}}.
     *
     * @param phoneNumberSid the pool entry SID ({@code PN…})
     * @return the pool entry
     */
    public MessagingServicePhoneNumber fetch(String phoneNumberSid) {
        return decode(transport.request("GET", base() + "/" + seg(phoneNumberSid), null, null),
                MessagingServicePhoneNumber.class);
    }

    /**
     * {@code DELETE /v1/Services/{Sid}/PhoneNumbers/{Sid}}: remove a number from the pool.
     *
     * @param phoneNumberSid the pool entry SID ({@code PN…})
     */
    public void delete(String phoneNumberSid) {
        transport.request("DELETE", base() + "/" + seg(phoneNumberSid), null, null);
    }
}
