package com.voicetel.voiceml.resources;

import com.voicetel.voiceml.Transport;
import com.voicetel.voiceml.models.ConversationsV1ServiceConversationWithParticipants;
import com.voicetel.voiceml.models.CreateConversationsV1ServiceConversationWithParticipantsRequest;

import java.util.Map;

/** Operations on {@code /v1/Services/{ChatServiceSid}/ConversationWithParticipants}. */
public final class ConversationsV1ServiceConversationWithParticipantsResource extends BaseResource {

    private final String chatServiceSid;

    public ConversationsV1ServiceConversationWithParticipantsResource(Transport transport, String chatServiceSid) {
        super(transport);
        this.chatServiceSid = chatServiceSid;
    }

    public ConversationsV1ServiceConversationWithParticipants create(
            CreateConversationsV1ServiceConversationWithParticipantsRequest body) {
        Map<String, Object> form = body != null ? body.toForm() : null;
        return decode(transport.request("POST",
                        "/v1/Services/" + chatServiceSid + "/ConversationWithParticipants",
                        null, form),
                ConversationsV1ServiceConversationWithParticipants.class);
    }
}
