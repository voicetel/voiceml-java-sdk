package com.voicetel.voiceml.resources;

import com.voicetel.voiceml.Transport;
import com.voicetel.voiceml.models.ConversationsV1ConversationParticipant;
import com.voicetel.voiceml.models.ConversationsV1ConversationParticipantList;
import com.voicetel.voiceml.models.CreateConversationsV1ConversationParticipantRequest;
import com.voicetel.voiceml.models.ListPageParams;
import com.voicetel.voiceml.models.UpdateConversationsV1ConversationParticipantRequest;

import java.util.Map;

/** Operations on {@code /v1/Conversations/{ConversationSid}/Participants[/{ParticipantSid}]}. */
public final class ConversationsV1ConversationParticipantsResource extends BaseResource {

    private final String conversationSid;

    public ConversationsV1ConversationParticipantsResource(Transport transport, String conversationSid) {
        super(transport);
        this.conversationSid = conversationSid;
    }

    private String base() {
        return "/v1/Conversations/" + conversationSid + "/Participants";
    }

    public ConversationsV1ConversationParticipant create(CreateConversationsV1ConversationParticipantRequest body) {
        Map<String, Object> form = body != null ? body.toForm() : null;
        return decode(transport.request("POST", base(), null, form),
                ConversationsV1ConversationParticipant.class);
    }

    public ConversationsV1ConversationParticipantList list(ListPageParams params) {
        Map<String, Object> q = params != null ? params.toQuery() : null;
        return decode(transport.request("GET", base(), q, null),
                ConversationsV1ConversationParticipantList.class);
    }

    public ConversationsV1ConversationParticipantList list() { return list(null); }

    public ConversationsV1ConversationParticipant fetch(String participantSid) {
        return decode(transport.request("GET", base() + "/" + participantSid, null, null),
                ConversationsV1ConversationParticipant.class);
    }

    public ConversationsV1ConversationParticipant update(String participantSid,
                                                         UpdateConversationsV1ConversationParticipantRequest body) {
        Map<String, Object> form = body != null ? body.toForm() : null;
        return decode(transport.request("POST", base() + "/" + participantSid, null, form),
                ConversationsV1ConversationParticipant.class);
    }

    public void delete(String participantSid) {
        transport.request("DELETE", base() + "/" + participantSid, null, null);
    }
}
