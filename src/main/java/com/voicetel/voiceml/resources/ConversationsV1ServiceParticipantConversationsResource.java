package com.voicetel.voiceml.resources;

import com.voicetel.voiceml.Transport;
import com.voicetel.voiceml.models.ConversationsV1ServiceParticipantConversationList;
import com.voicetel.voiceml.models.ListConversationsV1ServiceParticipantConversationsParams;

import java.util.Map;

/** List-only ops on {@code /v1/Services/{ChatServiceSid}/ParticipantConversations}. */
public final class ConversationsV1ServiceParticipantConversationsResource extends BaseResource {

    private final String chatServiceSid;

    public ConversationsV1ServiceParticipantConversationsResource(Transport transport, String chatServiceSid) {
        super(transport);
        this.chatServiceSid = chatServiceSid;
    }

    public ConversationsV1ServiceParticipantConversationList list(
            ListConversationsV1ServiceParticipantConversationsParams params) {
        Map<String, Object> q = params != null ? params.toQuery() : null;
        return decode(transport.request("GET",
                        "/v1/Services/" + chatServiceSid + "/ParticipantConversations",
                        q, null),
                ConversationsV1ServiceParticipantConversationList.class);
    }

    public ConversationsV1ServiceParticipantConversationList list() { return list(null); }
}
