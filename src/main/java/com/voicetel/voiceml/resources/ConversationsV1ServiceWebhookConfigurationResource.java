package com.voicetel.voiceml.resources;

import com.voicetel.voiceml.Transport;
import com.voicetel.voiceml.models.ConversationsV1ServiceWebhookConfiguration;
import com.voicetel.voiceml.models.UpdateConversationsV1ServiceWebhookConfigurationRequest;

import java.util.Map;

/** Operations on {@code /v1/Services/{ChatServiceSid}/Configuration/Webhooks}. */
public final class ConversationsV1ServiceWebhookConfigurationResource extends BaseResource {

    private final String chatServiceSid;

    public ConversationsV1ServiceWebhookConfigurationResource(Transport transport, String chatServiceSid) {
        super(transport);
        this.chatServiceSid = chatServiceSid;
    }

    private String base() {
        return "/v1/Services/" + chatServiceSid + "/Configuration/Webhooks";
    }

    public ConversationsV1ServiceWebhookConfiguration fetch() {
        return decode(transport.request("GET", base(), null, null),
                ConversationsV1ServiceWebhookConfiguration.class);
    }

    public ConversationsV1ServiceWebhookConfiguration update(UpdateConversationsV1ServiceWebhookConfigurationRequest body) {
        Map<String, Object> form = body != null ? body.toForm() : null;
        return decode(transport.request("POST", base(), null, form),
                ConversationsV1ServiceWebhookConfiguration.class);
    }
}
