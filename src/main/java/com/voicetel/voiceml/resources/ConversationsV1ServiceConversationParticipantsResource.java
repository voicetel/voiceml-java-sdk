package com.voicetel.voiceml.resources;

import com.voicetel.voiceml.Transport;
import com.voicetel.voiceml.models.ConversationsV1ServiceConversationParticipant;
import com.voicetel.voiceml.models.ConversationsV1ServiceConversationParticipantList;
import com.voicetel.voiceml.models.CreateConversationsV1ServiceConversationParticipantRequest;
import com.voicetel.voiceml.models.ListPageParams;
import com.voicetel.voiceml.models.UpdateConversationsV1ServiceConversationParticipantRequest;

import java.util.Map;

/** Operations on {@code /v1/Services/{ChatServiceSid}/Conversations/{ConversationSid}/Participants[/{ParticipantSid}]}. */
public final class ConversationsV1ServiceConversationParticipantsResource extends BaseResource {

    private final String chatServiceSid;
    private final String conversationSid;

    public ConversationsV1ServiceConversationParticipantsResource(Transport transport,
                                                                  String chatServiceSid,
                                                                  String conversationSid) {
        super(transport);
        this.chatServiceSid = chatServiceSid;
        this.conversationSid = conversationSid;
    }

    private String base() {
        return "/v1/Services/" + chatServiceSid + "/Conversations/" + conversationSid + "/Participants";
    }

    public ConversationsV1ServiceConversationParticipant create(CreateConversationsV1ServiceConversationParticipantRequest body) {
        Map<String, Object> form = body != null ? body.toForm() : null;
        return decode(transport.request("POST", base(), null, form),
                ConversationsV1ServiceConversationParticipant.class);
    }

    public ConversationsV1ServiceConversationParticipantList list(ListPageParams params) {
        Map<String, Object> q = params != null ? params.toQuery() : null;
        return decode(transport.request("GET", base(), q, null),
                ConversationsV1ServiceConversationParticipantList.class);
    }

    public ConversationsV1ServiceConversationParticipantList list() { return list(null); }

    public ConversationsV1ServiceConversationParticipant fetch(String participantSid) {
        return decode(transport.request("GET", base() + "/" + participantSid, null, null),
                ConversationsV1ServiceConversationParticipant.class);
    }

    public ConversationsV1ServiceConversationParticipant update(String participantSid,
                                                                UpdateConversationsV1ServiceConversationParticipantRequest body) {
        Map<String, Object> form = body != null ? body.toForm() : null;
        return decode(transport.request("POST", base() + "/" + participantSid, null, form),
                ConversationsV1ServiceConversationParticipant.class);
    }

    public void delete(String participantSid) {
        transport.request("DELETE", base() + "/" + participantSid, null, null);
    }
}
