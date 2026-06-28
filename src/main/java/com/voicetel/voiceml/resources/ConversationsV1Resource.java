package com.voicetel.voiceml.resources;

import com.voicetel.voiceml.Transport;

/**
 * Top-level {@code /v1/*} surface for Twilio conversations.twilio.com/v1
 * (#421). Sub-resources:
 *
 * <ul>
 *   <li>{@link #conversations()} — {@code /v1/Conversations} (+ Messages/Participants/Webhooks).
 *   <li>{@link #roles()} — {@code /v1/Roles}.
 *   <li>{@link #users()} — {@code /v1/Users} (+ {@code /Conversations}).
 *   <li>{@link #credentials()} — {@code /v1/Credentials}.
 *   <li>{@link #configuration()} — {@code /v1/Configuration} (+ {@code /Webhooks}, {@code /Addresses}).
 *   <li>{@link #participantConversations()} — {@code /v1/ParticipantConversations}.
 *   <li>{@link #conversationWithParticipants()} — {@code /v1/ConversationWithParticipants}.
 *   <li>{@link #services()} — {@code /v1/Services}.
 * </ul>
 */
public final class ConversationsV1Resource {

    private final ConversationsV1ConversationsResource conversations;
    private final ConversationsV1RolesResource roles;
    private final ConversationsV1UsersResource users;
    private final ConversationsV1CredentialsResource credentials;
    private final ConversationsV1ConfigurationResource configuration;
    private final ConversationsV1ParticipantConversationsResource participantConversations;
    private final ConversationsV1ConversationWithParticipantsResource conversationWithParticipants;
    private final ConversationsV1ServicesResource services;

    public ConversationsV1Resource(Transport transport) {
        this.conversations = new ConversationsV1ConversationsResource(transport);
        this.roles = new ConversationsV1RolesResource(transport);
        this.users = new ConversationsV1UsersResource(transport);
        this.credentials = new ConversationsV1CredentialsResource(transport);
        this.configuration = new ConversationsV1ConfigurationResource(transport);
        this.participantConversations = new ConversationsV1ParticipantConversationsResource(transport);
        this.conversationWithParticipants = new ConversationsV1ConversationWithParticipantsResource(transport);
        this.services = new ConversationsV1ServicesResource(transport);
    }

    public ConversationsV1ConversationsResource conversations() { return conversations; }
    public ConversationsV1RolesResource roles() { return roles; }
    public ConversationsV1UsersResource users() { return users; }
    public ConversationsV1CredentialsResource credentials() { return credentials; }
    public ConversationsV1ConfigurationResource configuration() { return configuration; }
    public ConversationsV1ParticipantConversationsResource participantConversations() {
        return participantConversations;
    }
    public ConversationsV1ConversationWithParticipantsResource conversationWithParticipants() {
        return conversationWithParticipants;
    }
    public ConversationsV1ServicesResource services() { return services; }
}
