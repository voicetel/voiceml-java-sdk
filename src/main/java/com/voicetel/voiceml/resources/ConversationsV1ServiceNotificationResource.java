package com.voicetel.voiceml.resources;

import com.voicetel.voiceml.Transport;
import com.voicetel.voiceml.models.ConversationsV1ServiceNotification;
import com.voicetel.voiceml.models.UpdateConversationsV1ServiceNotificationRequest;

import java.util.Map;

/** Operations on {@code /v1/Services/{ChatServiceSid}/Configuration/Notifications}. */
public final class ConversationsV1ServiceNotificationResource extends BaseResource {

    private final String chatServiceSid;

    public ConversationsV1ServiceNotificationResource(Transport transport, String chatServiceSid) {
        super(transport);
        this.chatServiceSid = chatServiceSid;
    }

    private String base() {
        return "/v1/Services/" + chatServiceSid + "/Configuration/Notifications";
    }

    public ConversationsV1ServiceNotification fetch() {
        return decode(transport.request("GET", base(), null, null),
                ConversationsV1ServiceNotification.class);
    }

    public ConversationsV1ServiceNotification update(UpdateConversationsV1ServiceNotificationRequest body) {
        Map<String, Object> form = body != null ? body.toForm() : null;
        return decode(transport.request("POST", base(), null, form),
                ConversationsV1ServiceNotification.class);
    }
}
