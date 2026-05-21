package com.voicetel.voiceml.resources;

import com.voicetel.voiceml.Transport;
import com.voicetel.voiceml.models.Conference;
import com.voicetel.voiceml.models.ConferenceList;
import com.voicetel.voiceml.models.EndConferenceRequest;
import com.voicetel.voiceml.models.ListConferencesParams;
import com.voicetel.voiceml.models.ListParticipantsParams;
import com.voicetel.voiceml.models.Participant;
import com.voicetel.voiceml.models.ParticipantList;
import com.voicetel.voiceml.models.RecordingList;
import com.voicetel.voiceml.models.UpdateParticipantRequest;

import java.util.Map;

/** {@code /Conferences}, {@code /Conferences/{sid}/Participants}, {@code .../Recordings}. */
public final class ConferencesResource extends BaseResource {

    public ConferencesResource(Transport transport) {
        super(transport);
    }

    public ConferenceList list(ListConferencesParams params) {
        Map<String, Object> q = params != null ? params.toQuery() : null;
        return decode(
                transport.request("GET", accountPath("Conferences"), q, null),
                ConferenceList.class);
    }

    public ConferenceList list() {
        return list(null);
    }

    public Conference get(String conferenceSid) {
        return decode(
                transport.request("GET", accountPath("Conferences", conferenceSid), null, null),
                Conference.class);
    }

    /** End a live conference. {@code body} defaults to {@code Status=completed} when null. */
    public Conference end(String conferenceSid, EndConferenceRequest body) {
        EndConferenceRequest req = body != null ? body : new EndConferenceRequest();
        return decode(
                transport.request(
                        "POST", accountPath("Conferences", conferenceSid), null, req.toForm()),
                Conference.class);
    }

    public Conference end(String conferenceSid) {
        return end(conferenceSid, null);
    }

    // --- Participants ---

    public ParticipantList listParticipants(String conferenceSid, ListParticipantsParams params) {
        Map<String, Object> q = params != null ? params.toQuery() : null;
        return decode(
                transport.request(
                        "GET",
                        accountPath("Conferences", conferenceSid, "Participants"),
                        q,
                        null),
                ParticipantList.class);
    }

    public ParticipantList listParticipants(String conferenceSid) {
        return listParticipants(conferenceSid, null);
    }

    public Participant getParticipant(String conferenceSid, String callSid) {
        return decode(
                transport.request(
                        "GET",
                        accountPath("Conferences", conferenceSid, "Participants", callSid),
                        null,
                        null),
                Participant.class);
    }

    public Participant updateParticipant(
            String conferenceSid, String callSid, UpdateParticipantRequest body) {
        return decode(
                transport.request(
                        "POST",
                        accountPath("Conferences", conferenceSid, "Participants", callSid),
                        null,
                        body.toForm()),
                Participant.class);
    }

    public void kickParticipant(String conferenceSid, String callSid) {
        transport.request(
                "DELETE",
                accountPath("Conferences", conferenceSid, "Participants", callSid),
                null,
                null);
    }

    // --- Recordings ---

    public RecordingList listRecordings(String conferenceSid) {
        return decode(
                transport.request(
                        "GET",
                        accountPath("Conferences", conferenceSid, "Recordings"),
                        null,
                        null),
                RecordingList.class);
    }
}
