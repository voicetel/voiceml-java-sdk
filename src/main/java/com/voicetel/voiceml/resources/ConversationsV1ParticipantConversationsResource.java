package com.voicetel.voiceml.resources;

import com.voicetel.voiceml.Transport;
import com.voicetel.voiceml.models.ConversationsV1ParticipantConversationList;
import com.voicetel.voiceml.models.ListConversationsV1ParticipantConversationsParams;

import java.util.Map;

/** Operations on {@code /v1/ParticipantConversations} (cross-conversation lookup). */
public final class ConversationsV1ParticipantConversationsResource extends BaseResource {

    public ConversationsV1ParticipantConversationsResource(Transport transport) {
        super(transport);
    }

    public ConversationsV1ParticipantConversationList list(
            ListConversationsV1ParticipantConversationsParams params) {
        Map<String, Object> q = params != null ? params.toQuery() : null;
        return decode(transport.request("GET", "/v1/ParticipantConversations", q, null),
                ConversationsV1ParticipantConversationList.class);
    }

    public ConversationsV1ParticipantConversationList list() { return list(null); }
}
