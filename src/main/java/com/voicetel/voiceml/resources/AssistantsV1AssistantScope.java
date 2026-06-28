package com.voicetel.voiceml.resources;

import com.voicetel.voiceml.Transport;

/**
 * Per-Assistant scope. Returned by
 * {@link AssistantsV1Resource#assistants(String)}, exposes the four
 * Assistant-scoped sub-resources (Tools, Knowledge, Messages, Feedbacks).
 */
public final class AssistantsV1AssistantScope {

    private final AssistantsV1AssistantToolsResource tools;
    private final AssistantsV1AssistantKnowledgeResource knowledge;
    private final AssistantsV1AssistantMessagesResource messages;
    private final AssistantsV1AssistantFeedbacksResource feedbacks;

    public AssistantsV1AssistantScope(Transport transport, String assistantId) {
        this.tools = new AssistantsV1AssistantToolsResource(transport, assistantId);
        this.knowledge = new AssistantsV1AssistantKnowledgeResource(transport, assistantId);
        this.messages = new AssistantsV1AssistantMessagesResource(transport, assistantId);
        this.feedbacks = new AssistantsV1AssistantFeedbacksResource(transport, assistantId);
    }

    public AssistantsV1AssistantToolsResource tools() { return tools; }
    public AssistantsV1AssistantKnowledgeResource knowledge() { return knowledge; }
    public AssistantsV1AssistantMessagesResource messages() { return messages; }
    public AssistantsV1AssistantFeedbacksResource feedbacks() { return feedbacks; }
}
