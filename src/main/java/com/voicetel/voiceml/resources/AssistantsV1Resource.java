package com.voicetel.voiceml.resources;

import com.voicetel.voiceml.Transport;

/**
 * Top-level {@code /v1/*} surface for Twilio assistants.twilio.com/v1
 * (Phase 5). Sub-resources:
 *
 * <ul>
 *   <li>{@link #assistants()} — {@code /v1/Assistants} CRUD.
 *   <li>{@link #assistants(String)} — scope to one Assistant; gives access to
 *       attached {@code /Tools}, {@code /Knowledge}, {@code /Messages},
 *       {@code /Feedbacks}.
 *   <li>{@link #tools()} — {@code /v1/Tools} CRUD.
 *   <li>{@link #knowledge()} — {@code /v1/Knowledge} CRUD.
 *   <li>{@link #knowledge(String)} — scope to one Knowledge; gives access to
 *       {@code /Status} and {@code /Chunks}.
 *   <li>{@link #sessions()} — {@code /v1/Sessions} list+fetch.
 *   <li>{@link #sessions(String)} — scope to one Session; gives access to
 *       {@code /Messages}.
 *   <li>{@link #policies()} — {@code /v1/Policies} list.
 * </ul>
 */
public final class AssistantsV1Resource {

    private final Transport transport;
    private final AssistantsV1AssistantsResource assistants;
    private final AssistantsV1ToolsResource tools;
    private final AssistantsV1KnowledgeResource knowledge;
    private final AssistantsV1SessionsResource sessions;
    private final AssistantsV1PoliciesResource policies;

    public AssistantsV1Resource(Transport transport) {
        this.transport = transport;
        this.assistants = new AssistantsV1AssistantsResource(transport);
        this.tools = new AssistantsV1ToolsResource(transport);
        this.knowledge = new AssistantsV1KnowledgeResource(transport);
        this.sessions = new AssistantsV1SessionsResource(transport);
        this.policies = new AssistantsV1PoliciesResource(transport);
    }

    public AssistantsV1AssistantsResource assistants() { return assistants; }

    /** Scope to one Assistant — exposes attached {@code /Tools}, {@code /Knowledge},
     *  {@code /Messages}, {@code /Feedbacks}. */
    public AssistantsV1AssistantScope assistants(String assistantId) {
        return new AssistantsV1AssistantScope(transport, assistantId);
    }

    public AssistantsV1ToolsResource tools() { return tools; }

    public AssistantsV1KnowledgeResource knowledge() { return knowledge; }

    /** Scope to one Knowledge — exposes {@code /Status} and {@code /Chunks}. */
    public AssistantsV1KnowledgeScope knowledge(String knowledgeId) {
        return new AssistantsV1KnowledgeScope(transport, knowledgeId);
    }

    public AssistantsV1SessionsResource sessions() { return sessions; }

    /** Scope to one Session — exposes {@code /Messages}. */
    public AssistantsV1SessionScope sessions(String sessionId) {
        return new AssistantsV1SessionScope(transport, sessionId);
    }

    public AssistantsV1PoliciesResource policies() { return policies; }
}
