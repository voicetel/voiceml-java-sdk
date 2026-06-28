package com.voicetel.voiceml.resources;

import com.voicetel.voiceml.Transport;
import com.voicetel.voiceml.models.ConversationsV1ConversationWithParticipants;
import com.voicetel.voiceml.models.CreateConversationsV1ConversationWithParticipantsRequest;

import java.util.Map;

/** Operations on {@code /v1/ConversationWithParticipants} (one-shot create). */
public final class ConversationsV1ConversationWithParticipantsResource extends BaseResource {

    public ConversationsV1ConversationWithParticipantsResource(Transport transport) {
        super(transport);
    }

    public ConversationsV1ConversationWithParticipants create(
            CreateConversationsV1ConversationWithParticipantsRequest body) {
        Map<String, Object> form = body != null ? body.toForm() : null;
        return decode(transport.request("POST", "/v1/ConversationWithParticipants", null, form),
                ConversationsV1ConversationWithParticipants.class);
    }
}
