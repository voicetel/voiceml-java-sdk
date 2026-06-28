package com.voicetel.voiceml.resources;

import com.voicetel.voiceml.Transport;
import com.voicetel.voiceml.models.AssistantsV1SendMessageResponse;
import com.voicetel.voiceml.models.SendAssistantsV1MessageRequest;

import java.util.Map;

/** {@code POST /v1/Assistants/{id}/Messages} — send a message to the Assistant. */
public final class AssistantsV1AssistantMessagesResource extends BaseResource {

    private final String assistantId;

    public AssistantsV1AssistantMessagesResource(Transport transport, String assistantId) {
        super(transport);
        this.assistantId = assistantId;
    }

    /**
     * Send a message to this Assistant. Creates a Session (or appends to the
     * supplied {@code session_id}) and returns the send result envelope.
     */
    public AssistantsV1SendMessageResponse send(SendAssistantsV1MessageRequest body) {
        Map<String, Object> json = body != null ? body.toJsonBody() : null;
        return decode(transport.requestJson("POST",
                "/v1/Assistants/" + assistantId + "/Messages", null, json),
                AssistantsV1SendMessageResponse.class);
    }
}
