package com.voicetel.voiceml.resources;

import com.voicetel.voiceml.Transport;

/**
 * The Twilio-compatible Messaging Service API ({@code /v1/*}, routed at the messaging host,
 * {@code messaging.voicetel.com}).
 *
 * <ul>
 *   <li>{@link #services()} — {@code /v1/Services} Messaging Service CRUD.
 * </ul>
 */
public final class MessagingV1Resource {

    private final MessagingV1ServicesResource services;

    public MessagingV1Resource(Transport transport) {
        this.services = new MessagingV1ServicesResource(transport);
    }

    /** @return {@code /v1/Services}: Messaging Services and their sender pools */
    public MessagingV1ServicesResource services() { return services; }
}
