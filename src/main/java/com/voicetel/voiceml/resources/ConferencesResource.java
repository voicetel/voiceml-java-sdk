package com.voicetel.voiceml.resources;

import com.voicetel.voiceml.Transport;
import com.voicetel.voiceml.models.Conference;
import com.voicetel.voiceml.models.ConferenceList;
import com.voicetel.voiceml.models.CreateParticipantRequest;
import com.voicetel.voiceml.models.EndConferenceRequest;
import com.voicetel.voiceml.models.ListCallRecordingsParams;
import com.voicetel.voiceml.models.ListConferencesParams;
import com.voicetel.voiceml.models.ListParticipantsParams;
import com.voicetel.voiceml.models.Participant;
import com.voicetel.voiceml.models.ParticipantList;
import com.voicetel.voiceml.models.Recording;
import com.voicetel.voiceml.models.RecordingList;
import com.voicetel.voiceml.models.UpdateParticipantRequest;
import com.voicetel.voiceml.models.UpdateRecordingRequest;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
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

    /**
     * Auto-paginate through all pages of {@code GET /Conferences}, collecting every
     * {@link Conference} into a single list.
     */
    public List<Conference> iterate(ListConferencesParams params) {
        List<Conference> out = new ArrayList<>();
        Map<String, Object> q = params != null ? params.toQuery() : new LinkedHashMap<>();
        int page = q.containsKey("Page") ? ((Number) q.get("Page")).intValue() : 0;
        while (true) {
            q.put("Page", page);
            ConferenceList chunk = decode(
                    transport.request("GET", accountPath("Conferences"), q, null),
                    ConferenceList.class);
            out.addAll(chunk.getConferences());
            if (chunk.getNextPageUri() == null || chunk.getNextPageUri().isEmpty()
                    || chunk.getConferences().isEmpty()) {
                return out;
            }
            page++;
        }
    }

    /** Auto-paginate all conferences (no filters). */
    public List<Conference> iterate() {
        return iterate(null);
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

    /** Auto-paginate through all conference participants. */
    public List<Participant> iterateParticipants(String conferenceSid, ListParticipantsParams params) {
        List<Participant> out = new ArrayList<>();
        Map<String, Object> q = params != null ? params.toQuery() : new LinkedHashMap<>();
        int page = q.containsKey("Page") ? ((Number) q.get("Page")).intValue() : 0;
        while (true) {
            q.put("Page", page);
            ParticipantList chunk = decode(
                    transport.request(
                            "GET",
                            accountPath("Conferences", conferenceSid, "Participants"),
                            q,
                            null),
                    ParticipantList.class);
            out.addAll(chunk.getParticipants());
            if (chunk.getNextPageUri() == null || chunk.getNextPageUri().isEmpty()
                    || chunk.getParticipants().isEmpty()) {
                return out;
            }
            page++;
        }
    }

    /** Auto-paginate all conference participants (no filters). */
    public List<Participant> iterateParticipants(String conferenceSid) {
        return iterateParticipants(conferenceSid, null);
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

    /** Dial a leg into a conference. {@code POST /Conferences/{sid}/Participants}. */
    public Participant createParticipant(String conferenceSid, CreateParticipantRequest body) {
        return decode(
                transport.request(
                        "POST",
                        accountPath("Conferences", conferenceSid, "Participants"),
                        null,
                        body.toForm()),
                Participant.class);
    }

    // --- Recordings ---

    public RecordingList listRecordings(String conferenceSid, ListCallRecordingsParams params) {
        Map<String, Object> q = params != null ? params.toQuery() : null;
        return decode(
                transport.request(
                        "GET",
                        accountPath("Conferences", conferenceSid, "Recordings"),
                        q,
                        null),
                RecordingList.class);
    }

    public RecordingList listRecordings(String conferenceSid) {
        return listRecordings(conferenceSid, null);
    }

    /** Auto-paginate through all conference-scoped recordings. */
    public List<Recording> iterateRecordings(String conferenceSid, ListCallRecordingsParams params) {
        List<Recording> out = new ArrayList<>();
        Map<String, Object> q = params != null ? params.toQuery() : new LinkedHashMap<>();
        int page = q.containsKey("Page") ? ((Number) q.get("Page")).intValue() : 0;
        while (true) {
            q.put("Page", page);
            RecordingList chunk = decode(
                    transport.request(
                            "GET",
                            accountPath("Conferences", conferenceSid, "Recordings"),
                            q,
                            null),
                    RecordingList.class);
            out.addAll(chunk.getRecordings());
            if (chunk.getNextPageUri() == null || chunk.getNextPageUri().isEmpty()
                    || chunk.getRecordings().isEmpty()) {
                return out;
            }
            page++;
        }
    }

    /** Auto-paginate all conference recordings (no filters). */
    public List<Recording> iterateRecordings(String conferenceSid) {
        return iterateRecordings(conferenceSid, null);
    }

    public Recording getRecording(String conferenceSid, String recordingSid) {
        return decode(
                transport.request(
                        "GET",
                        accountPath("Conferences", conferenceSid, "Recordings", recordingSid),
                        null,
                        null),
                Recording.class);
    }

    public Recording updateRecording(
            String conferenceSid, String recordingSid, UpdateRecordingRequest body) {
        return decode(
                transport.request(
                        "POST",
                        accountPath("Conferences", conferenceSid, "Recordings", recordingSid),
                        null,
                        body.toForm()),
                Recording.class);
    }

    public void deleteRecording(String conferenceSid, String recordingSid) {
        transport.request(
                "DELETE",
                accountPath("Conferences", conferenceSid, "Recordings", recordingSid),
                null,
                null);
    }
}
