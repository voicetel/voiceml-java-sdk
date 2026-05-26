package com.voicetel.voiceml.resources;

import com.voicetel.voiceml.Transport;
import com.voicetel.voiceml.models.Call;
import com.voicetel.voiceml.models.CallList;
import com.voicetel.voiceml.models.CallTranscription;
import com.voicetel.voiceml.models.CreateCallRequest;
import com.voicetel.voiceml.models.EventsList;
import com.voicetel.voiceml.models.ListCallRecordingsParams;
import com.voicetel.voiceml.models.ListCallsParams;
import com.voicetel.voiceml.models.ListNotificationsParams;
import com.voicetel.voiceml.models.ListPageParams;
import com.voicetel.voiceml.models.NotificationsList;
import com.voicetel.voiceml.models.Recording;
import com.voicetel.voiceml.models.RecordingList;
import com.voicetel.voiceml.models.SiprecList;
import com.voicetel.voiceml.models.SiprecSession;
import com.voicetel.voiceml.models.StartRecordingRequest;
import com.voicetel.voiceml.models.StartSiprecRequest;
import com.voicetel.voiceml.models.StartStreamRequest;
import com.voicetel.voiceml.models.StartTranscriptionRequest;
import com.voicetel.voiceml.models.StopSiprecRequest;
import com.voicetel.voiceml.models.StopStreamRequest;
import com.voicetel.voiceml.models.StopTranscriptionRequest;
import com.voicetel.voiceml.models.Stream;
import com.voicetel.voiceml.models.StreamList;
import com.voicetel.voiceml.models.TranscriptionList;
import com.voicetel.voiceml.models.UpdateCallRequest;
import com.voicetel.voiceml.models.UpdateRecordingRequest;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * {@code /Calls} and call-scoped sub-resources (Recordings, Streams, Siprec, Transcriptions,
 * Notifications, Events, UserDefinedMessages).
 */
public final class CallsResource extends BaseResource {

    public CallsResource(Transport transport) {
        super(transport);
    }

    // --- /Calls ---

    /** List calls, optionally filtered. {@code null} params are dropped. */
    public CallList list(ListCallsParams params) {
        Map<String, Object> q = params != null ? params.toQuery() : null;
        return decode(transport.request("GET", accountPath("Calls"), q, null), CallList.class);
    }

    /** List all calls (no filters). */
    public CallList list() {
        return list(null);
    }

    /**
     * Auto-paginate through all pages of {@code GET /Calls}, collecting every {@link Call} into a
     * single list. Starts at page 0 (or the page specified in {@code params}) and follows
     * {@code next_page_uri} until exhausted.
     */
    public List<Call> iterate(ListCallsParams params) {
        List<Call> out = new ArrayList<>();
        Map<String, Object> q = params != null ? params.toQuery() : new LinkedHashMap<>();
        int page = q.containsKey("Page") ? ((Number) q.get("Page")).intValue() : 0;
        while (true) {
            q.put("Page", page);
            CallList chunk = decode(
                    transport.request("GET", accountPath("Calls"), q, null), CallList.class);
            out.addAll(chunk.getCalls());
            if (chunk.getNextPageUri() == null || chunk.getNextPageUri().isEmpty()
                    || chunk.getCalls().isEmpty()) {
                return out;
            }
            page++;
        }
    }

    /** Auto-paginate all calls (no filters). */
    public List<Call> iterate() {
        return iterate(null);
    }

    /** Create a new outbound call. */
    public Call create(CreateCallRequest body) {
        return decode(
                transport.request("POST", accountPath("Calls"), null, body.toForm()),
                Call.class);
    }

    public Call get(String callSid) {
        return decode(transport.request("GET", accountPath("Calls", callSid), null, null), Call.class);
    }

    /**
     * Update or terminate a live call. {@code POST /Calls/{sid}} (not PUT) — matches Twilio.
     */
    public Call update(String callSid, UpdateCallRequest body) {
        return decode(
                transport.request("POST", accountPath("Calls", callSid), null, body.toForm()),
                Call.class);
    }

    public void delete(String callSid) {
        transport.request("DELETE", accountPath("Calls", callSid), null, null);
    }

    // --- Recordings (call-scoped) ---

    public RecordingList listRecordings(String callSid, ListCallRecordingsParams params) {
        Map<String, Object> q = params != null ? params.toQuery() : null;
        return decode(
                transport.request("GET", accountPath("Calls", callSid, "Recordings"), q, null),
                RecordingList.class);
    }

    public RecordingList listRecordings(String callSid) {
        return listRecordings(callSid, null);
    }

    /** Auto-paginate through all call-scoped recordings. */
    public List<Recording> iterateRecordings(String callSid, ListCallRecordingsParams params) {
        List<Recording> out = new ArrayList<>();
        Map<String, Object> q = params != null ? params.toQuery() : new LinkedHashMap<>();
        int page = q.containsKey("Page") ? ((Number) q.get("Page")).intValue() : 0;
        while (true) {
            q.put("Page", page);
            RecordingList chunk = decode(
                    transport.request("GET", accountPath("Calls", callSid, "Recordings"), q, null),
                    RecordingList.class);
            out.addAll(chunk.getRecordings());
            if (chunk.getNextPageUri() == null || chunk.getNextPageUri().isEmpty()
                    || chunk.getRecordings().isEmpty()) {
                return out;
            }
            page++;
        }
    }

    /** Auto-paginate through all call-scoped recordings (no filters). */
    public List<Recording> iterateRecordings(String callSid) {
        return iterateRecordings(callSid, null);
    }

    public Recording startRecording(String callSid, StartRecordingRequest body) {
        Map<String, Object> form = body != null ? body.toForm() : null;
        return decode(
                transport.request("POST", accountPath("Calls", callSid, "Recordings"), null, form),
                Recording.class);
    }

    public Recording startRecording(String callSid) {
        return startRecording(callSid, null);
    }

    public Recording getRecording(String callSid, String recordingSid) {
        return decode(
                transport.request("GET", accountPath("Calls", callSid, "Recordings", recordingSid), null, null),
                Recording.class);
    }

    public Recording updateRecording(String callSid, String recordingSid, UpdateRecordingRequest body) {
        return decode(
                transport.request(
                        "POST",
                        accountPath("Calls", callSid, "Recordings", recordingSid),
                        null,
                        body.toForm()),
                Recording.class);
    }

    public void deleteRecording(String callSid, String recordingSid) {
        transport.request(
                "DELETE", accountPath("Calls", callSid, "Recordings", recordingSid), null, null);
    }

    // --- Streams ---

    public StreamList listStreams(String callSid) {
        return decode(
                transport.request("GET", accountPath("Calls", callSid, "Streams"), null, null),
                StreamList.class);
    }

    /** Auto-paginate through all call-scoped streams. */
    public List<Stream> iterateStreams(String callSid) {
        List<Stream> out = new ArrayList<>();
        Map<String, Object> q = new LinkedHashMap<>();
        int page = 0;
        while (true) {
            q.put("Page", page);
            StreamList chunk = decode(
                    transport.request("GET", accountPath("Calls", callSid, "Streams"), q, null),
                    StreamList.class);
            out.addAll(chunk.getStreams());
            if (chunk.getNextPageUri() == null || chunk.getNextPageUri().isEmpty()
                    || chunk.getStreams().isEmpty()) {
                return out;
            }
            page++;
        }
    }

    public Stream startStream(String callSid, StartStreamRequest body) {
        return decode(
                transport.request("POST", accountPath("Calls", callSid, "Streams"), null, body.toForm()),
                Stream.class);
    }

    public Stream getStream(String callSid, String streamSid) {
        return decode(
                transport.request("GET", accountPath("Calls", callSid, "Streams", streamSid), null, null),
                Stream.class);
    }

    public Stream stopStream(String callSid, String streamSid, StopStreamRequest body) {
        StopStreamRequest req = body != null ? body : new StopStreamRequest();
        return decode(
                transport.request(
                        "POST",
                        accountPath("Calls", callSid, "Streams", streamSid),
                        null,
                        req.toForm()),
                Stream.class);
    }

    public Stream stopStream(String callSid, String streamSid) {
        return stopStream(callSid, streamSid, null);
    }

    // --- SIPREC ---

    public SiprecList listSiprec(String callSid) {
        return decode(
                transport.request("GET", accountPath("Calls", callSid, "Siprec"), null, null),
                SiprecList.class);
    }

    /** Auto-paginate through all call-scoped SIPREC sessions. */
    public List<SiprecSession> iterateSiprec(String callSid) {
        List<SiprecSession> out = new ArrayList<>();
        Map<String, Object> q = new LinkedHashMap<>();
        int page = 0;
        while (true) {
            q.put("Page", page);
            SiprecList chunk = decode(
                    transport.request("GET", accountPath("Calls", callSid, "Siprec"), q, null),
                    SiprecList.class);
            out.addAll(chunk.getSiprec());
            if (chunk.getNextPageUri() == null || chunk.getNextPageUri().isEmpty()
                    || chunk.getSiprec().isEmpty()) {
                return out;
            }
            page++;
        }
    }

    public SiprecSession startSiprec(String callSid, StartSiprecRequest body) {
        Map<String, Object> form = body != null ? body.toForm() : null;
        return decode(
                transport.request("POST", accountPath("Calls", callSid, "Siprec"), null, form),
                SiprecSession.class);
    }

    public SiprecSession startSiprec(String callSid) {
        return startSiprec(callSid, null);
    }

    public SiprecSession getSiprec(String callSid, String siprecSid) {
        return decode(
                transport.request("GET", accountPath("Calls", callSid, "Siprec", siprecSid), null, null),
                SiprecSession.class);
    }

    public SiprecSession stopSiprec(String callSid, String siprecSid, StopSiprecRequest body) {
        StopSiprecRequest req = body != null ? body : new StopSiprecRequest();
        return decode(
                transport.request(
                        "POST",
                        accountPath("Calls", callSid, "Siprec", siprecSid),
                        null,
                        req.toForm()),
                SiprecSession.class);
    }

    public SiprecSession stopSiprec(String callSid, String siprecSid) {
        return stopSiprec(callSid, siprecSid, null);
    }

    // --- Transcriptions ---

    public TranscriptionList listTranscriptions(String callSid) {
        return decode(
                transport.request("GET", accountPath("Calls", callSid, "Transcriptions"), null, null),
                TranscriptionList.class);
    }

    /** Auto-paginate through all call-scoped transcriptions. */
    public List<CallTranscription> iterateTranscriptions(String callSid) {
        List<CallTranscription> out = new ArrayList<>();
        Map<String, Object> q = new LinkedHashMap<>();
        int page = 0;
        while (true) {
            q.put("Page", page);
            TranscriptionList chunk = decode(
                    transport.request(
                            "GET", accountPath("Calls", callSid, "Transcriptions"), q, null),
                    TranscriptionList.class);
            out.addAll(chunk.getTranscriptions());
            if (chunk.getNextPageUri() == null || chunk.getNextPageUri().isEmpty()
                    || chunk.getTranscriptions().isEmpty()) {
                return out;
            }
            page++;
        }
    }

    public CallTranscription startTranscription(String callSid, StartTranscriptionRequest body) {
        Map<String, Object> form = body != null ? body.toForm() : null;
        return decode(
                transport.request(
                        "POST", accountPath("Calls", callSid, "Transcriptions"), null, form),
                CallTranscription.class);
    }

    public CallTranscription startTranscription(String callSid) {
        return startTranscription(callSid, null);
    }

    public CallTranscription getTranscription(String callSid, String transcriptionSid) {
        return decode(
                transport.request(
                        "GET",
                        accountPath("Calls", callSid, "Transcriptions", transcriptionSid),
                        null,
                        null),
                CallTranscription.class);
    }

    public CallTranscription stopTranscription(
            String callSid, String transcriptionSid, StopTranscriptionRequest body) {
        StopTranscriptionRequest req = body != null ? body : new StopTranscriptionRequest();
        return decode(
                transport.request(
                        "POST",
                        accountPath("Calls", callSid, "Transcriptions", transcriptionSid),
                        null,
                        req.toForm()),
                CallTranscription.class);
    }

    public CallTranscription stopTranscription(String callSid, String transcriptionSid) {
        return stopTranscription(callSid, transcriptionSid, null);
    }

    // --- Notifications / Events (compat stubs) ---

    public NotificationsList listNotifications(String callSid, ListNotificationsParams params) {
        Map<String, Object> q = params != null ? params.toQuery() : null;
        return decode(
                transport.request(
                        "GET", accountPath("Calls", callSid, "Notifications"), q, null),
                NotificationsList.class);
    }

    public NotificationsList listNotifications(String callSid) {
        return listNotifications(callSid, null);
    }

    public Map<String, Object> getNotification(String callSid, String notificationSid) {
        return decode(
                transport.request(
                        "GET",
                        accountPath("Calls", callSid, "Notifications", notificationSid),
                        null,
                        null),
                new com.fasterxml.jackson.core.type.TypeReference<Map<String, Object>>() {});
    }

    public EventsList listEvents(String callSid, ListPageParams params) {
        Map<String, Object> q = params != null ? params.toQuery() : null;
        return decode(
                transport.request("GET", accountPath("Calls", callSid, "Events"), q, null),
                EventsList.class);
    }

    public EventsList listEvents(String callSid) {
        return listEvents(callSid, null);
    }

    // --- UserDefinedMessages — server returns 501. Kept for API completeness. ---

    /**
     * Forward to {@code POST /Calls/{sid}/UserDefinedMessages}.
     *
     * <p>The server mounts this only as a 501 stub. Calling will always raise
     * {@code NotImplementedException} — the SDK forwards the call so consumers get a clean
     * exception rather than discovering at runtime that the endpoint doesn't exist.
     */
    public void sendUserDefinedMessage(String callSid, Map<String, Object> payload) {
        transport.request(
                "POST", accountPath("Calls", callSid, "UserDefinedMessages"), null, payload);
    }
}
