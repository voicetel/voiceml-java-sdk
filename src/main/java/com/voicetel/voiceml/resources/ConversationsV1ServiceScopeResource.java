package com.voicetel.voiceml.resources;

import com.voicetel.voiceml.Transport;

/**
 * Service-scoped {@code /v1/Services/{ChatServiceSid}/*} surface. Aggregates
 * the 14 sub-resource families that hang off a single Conversations v1
 * Service:
 *
 * <ul>
 *   <li>{@link #conversations()} — {@code .../Conversations} (+ Messages / Participants / Webhooks / Receipts).
 *   <li>{@link #conversationWithParticipants()} — {@code .../ConversationWithParticipants}.
 *   <li>{@link #participantConversations()} — {@code .../ParticipantConversations}.
 *   <li>{@link #roles()} — {@code .../Roles}.
 *   <li>{@link #users()} — {@code .../Users} (+ per-user conversations).
 *   <li>{@link #bindings()} — {@code .../Bindings}.
 *   <li>{@link #configuration()} — {@code .../Configuration} (+ Notifications, Webhooks).
 * </ul>
 */
public final class ConversationsV1ServiceScopeResource {

    private final ConversationsV1ServiceConversationsResource conversations;
    private final ConversationsV1ServiceConversationWithParticipantsResource conversationWithParticipants;
    private final ConversationsV1ServiceParticipantConversationsResource participantConversations;
    private final ConversationsV1ServiceRolesResource roles;
    private final ConversationsV1ServiceUsersResource users;
    private final ConversationsV1ServiceBindingsResource bindings;
    private final ConversationsV1ServiceConfigurationResource configuration;

    public ConversationsV1ServiceScopeResource(Transport transport, String chatServiceSid) {
        this.conversations = new ConversationsV1ServiceConversationsResource(transport, chatServiceSid);
        this.conversationWithParticipants = new ConversationsV1ServiceConversationWithParticipantsResource(transport, chatServiceSid);
        this.participantConversations = new ConversationsV1ServiceParticipantConversationsResource(transport, chatServiceSid);
        this.roles = new ConversationsV1ServiceRolesResource(transport, chatServiceSid);
        this.users = new ConversationsV1ServiceUsersResource(transport, chatServiceSid);
        this.bindings = new ConversationsV1ServiceBindingsResource(transport, chatServiceSid);
        this.configuration = new ConversationsV1ServiceConfigurationResource(transport, chatServiceSid);
    }

    public ConversationsV1ServiceConversationsResource conversations() { return conversations; }
    public ConversationsV1ServiceConversationWithParticipantsResource conversationWithParticipants() {
        return conversationWithParticipants;
    }
    public ConversationsV1ServiceParticipantConversationsResource participantConversations() {
        return participantConversations;
    }
    public ConversationsV1ServiceRolesResource roles() { return roles; }
    public ConversationsV1ServiceUsersResource users() { return users; }
    public ConversationsV1ServiceBindingsResource bindings() { return bindings; }
    public ConversationsV1ServiceConfigurationResource configuration() { return configuration; }
}
