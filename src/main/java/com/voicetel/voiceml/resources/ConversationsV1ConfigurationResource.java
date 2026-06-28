package com.voicetel.voiceml.resources;

import com.voicetel.voiceml.Transport;
import com.voicetel.voiceml.models.ConversationsV1Configuration;
import com.voicetel.voiceml.models.UpdateConversationsV1ConfigurationRequest;

import java.util.Map;

/**
 * Operations on {@code /v1/Configuration} (the account-global Conversations
 * Configuration singleton) and its two sub-resources: account webhook config
 * and Configuration Addresses.
 */
public final class ConversationsV1ConfigurationResource extends BaseResource {

    private final ConversationsV1ConfigurationWebhooksResource webhooks;
    private final ConversationsV1ConfigAddressesResource addresses;

    public ConversationsV1ConfigurationResource(Transport transport) {
        super(transport);
        this.webhooks = new ConversationsV1ConfigurationWebhooksResource(transport);
        this.addresses = new ConversationsV1ConfigAddressesResource(transport);
    }

    public ConversationsV1Configuration fetch() {
        return decode(transport.request("GET", "/v1/Configuration", null, null),
                ConversationsV1Configuration.class);
    }

    public ConversationsV1Configuration update(UpdateConversationsV1ConfigurationRequest body) {
        Map<String, Object> form = body != null ? body.toForm() : null;
        return decode(transport.request("POST", "/v1/Configuration", null, form),
                ConversationsV1Configuration.class);
    }

    public ConversationsV1ConfigurationWebhooksResource webhooks() { return webhooks; }
    public ConversationsV1ConfigAddressesResource addresses() { return addresses; }
}
