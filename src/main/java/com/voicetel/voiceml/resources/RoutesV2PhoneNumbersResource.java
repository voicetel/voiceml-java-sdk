package com.voicetel.voiceml.resources;

import com.voicetel.voiceml.Transport;
import com.voicetel.voiceml.models.RoutesV2PhoneNumber;
import com.voicetel.voiceml.models.UpdateRoutesV2PhoneNumberRequest;

import java.util.Map;

/**
 * Operations on {@code /v2/PhoneNumbers/{PhoneNumber}}. Keyed by the
 * phone number in E.164 (or its PN SID); the account is resolved from
 * HTTP Basic auth so paths bypass the {@code /2010-04-01/Accounts/{Sid}/}
 * prefix used by the rest of the SDK.
 */
public final class RoutesV2PhoneNumbersResource extends BaseResource {

    public RoutesV2PhoneNumbersResource(Transport transport) {
        super(transport);
    }

    /** Fetch a phone number's Inbound Processing Region binding. */
    public RoutesV2PhoneNumber fetch(String phoneNumber) {
        return decode(transport.request("GET", "/v2/PhoneNumbers/" + phoneNumber, null, null),
                RoutesV2PhoneNumber.class);
    }

    /** Update a phone number's voice region and/or friendly name. */
    public RoutesV2PhoneNumber update(String phoneNumber, UpdateRoutesV2PhoneNumberRequest body) {
        Map<String, Object> form = body != null ? body.toForm() : null;
        return decode(transport.request("POST", "/v2/PhoneNumbers/" + phoneNumber, null, form),
                RoutesV2PhoneNumber.class);
    }
}
