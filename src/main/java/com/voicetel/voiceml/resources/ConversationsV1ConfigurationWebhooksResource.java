package com.voicetel.voiceml.resources;

import com.voicetel.voiceml.Transport;
import com.voicetel.voiceml.models.ConversationsV1ConfigurationWebhook;
import com.voicetel.voiceml.models.UpdateConversationsV1ConfigurationWebhookRequest;

import java.util.Map;

/** Operations on {@code /v1/Configuration/Webhooks} (account-global webhook config). */
public final class ConversationsV1ConfigurationWebhooksResource extends BaseResource {

    public ConversationsV1ConfigurationWebhooksResource(Transport transport) {
        super(transport);
    }

    public ConversationsV1ConfigurationWebhook fetch() {
        return decode(transport.request("GET", "/v1/Configuration/Webhooks", null, null),
                ConversationsV1ConfigurationWebhook.class);
    }

    public ConversationsV1ConfigurationWebhook update(UpdateConversationsV1ConfigurationWebhookRequest body) {
        Map<String, Object> form = body != null ? body.toForm() : null;
        return decode(transport.request("POST", "/v1/Configuration/Webhooks", null, form),
                ConversationsV1ConfigurationWebhook.class);
    }
}
