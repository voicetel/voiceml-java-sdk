package com.voicetel.voiceml.resources;

import com.voicetel.voiceml.Transport;
import com.voicetel.voiceml.models.ConversationsV1ServiceConfiguration;
import com.voicetel.voiceml.models.UpdateConversationsV1ServiceConfigurationRequest;

import java.util.Map;

/**
 * Operations on {@code /v1/Services/{ChatServiceSid}/Configuration} and its
 * two sub-resources: per-service push notifications and per-service webhook
 * configuration.
 */
public final class ConversationsV1ServiceConfigurationResource extends BaseResource {

    private final String chatServiceSid;
    private final ConversationsV1ServiceNotificationResource notifications;
    private final ConversationsV1ServiceWebhookConfigurationResource webhooks;

    public ConversationsV1ServiceConfigurationResource(Transport transport, String chatServiceSid) {
        super(transport);
        this.chatServiceSid = chatServiceSid;
        this.notifications = new ConversationsV1ServiceNotificationResource(transport, chatServiceSid);
        this.webhooks = new ConversationsV1ServiceWebhookConfigurationResource(transport, chatServiceSid);
    }

    private String base() { return "/v1/Services/" + chatServiceSid + "/Configuration"; }

    public ConversationsV1ServiceConfiguration fetch() {
        return decode(transport.request("GET", base(), null, null),
                ConversationsV1ServiceConfiguration.class);
    }

    public ConversationsV1ServiceConfiguration update(UpdateConversationsV1ServiceConfigurationRequest body) {
        Map<String, Object> form = body != null ? body.toForm() : null;
        return decode(transport.request("POST", base(), null, form),
                ConversationsV1ServiceConfiguration.class);
    }

    public ConversationsV1ServiceNotificationResource notifications() { return notifications; }
    public ConversationsV1ServiceWebhookConfigurationResource webhooks() { return webhooks; }
}
