package com.voicetel.voiceml.resources;

import com.voicetel.voiceml.Transport;
import com.voicetel.voiceml.models.CreateMessagingServiceRequest;
import com.voicetel.voiceml.models.MessagingService;
import com.voicetel.voiceml.models.MessagingServiceList;
import com.voicetel.voiceml.models.UpdateMessagingServiceRequest;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Operations on {@code /v1/Services[/{sid}]} at the messaging host
 * ({@code messaging.voicetel.com}).
 *
 * <p>A Messaging Service ({@code MG…}) shares the {@code /v1/Services} path shape with a
 * Conversation Service ({@code IS…}); the two are disambiguated on the wire by host — the whole
 * {@code client.messagingV1()} group is routed at the messaging host by the client. Only the
 * Messaging Service has an {@code update} verb, so {@code POST /v1/Services/{sid}} has no path
 * collision.
 */
public final class MessagingV1ServicesResource extends BaseResource {

    public MessagingV1ServicesResource(Transport transport) {
        super(transport);
    }

    public MessagingService create(CreateMessagingServiceRequest body) {
        Map<String, Object> form = body != null ? body.toForm() : null;
        return decode(transport.request("POST", "/v1/Services", null, form),
                MessagingService.class);
    }

    public MessagingServiceList list(Integer pageSize) {
        Map<String, Object> q = null;
        if (pageSize != null) {
            q = new LinkedHashMap<>();
            q.put("PageSize", pageSize);
        }
        return decode(transport.request("GET", "/v1/Services", q, null),
                MessagingServiceList.class);
    }

    public MessagingServiceList list() {
        return list(null);
    }

    public MessagingService fetch(String sid) {
        return decode(transport.request("GET", "/v1/Services/" + sid, null, null),
                MessagingService.class);
    }

    public MessagingService update(String sid, UpdateMessagingServiceRequest body) {
        Map<String, Object> form = body != null ? body.toForm() : null;
        return decode(transport.request("POST", "/v1/Services/" + sid, null, form),
                MessagingService.class);
    }

    public void delete(String sid) {
        transport.request("DELETE", "/v1/Services/" + sid, null, null);
    }
}
