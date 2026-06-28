package com.voicetel.voiceml.resources;

import com.voicetel.voiceml.Transport;

/**
 * Per-Session scope. Returned by
 * {@link AssistantsV1Resource#sessions(String)}, exposes the Session-scoped
 * {@code /Messages} sub-resource.
 */
public final class AssistantsV1SessionScope {

    private final AssistantsV1SessionMessagesResource messages;

    public AssistantsV1SessionScope(Transport transport, String sessionId) {
        this.messages = new AssistantsV1SessionMessagesResource(transport, sessionId);
    }

    public AssistantsV1SessionMessagesResource messages() { return messages; }
}
