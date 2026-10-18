package com.voicetel.voiceml.resources;

import com.voicetel.voiceml.Pager;
import com.voicetel.voiceml.Transport;
import com.voicetel.voiceml.models.Call;
import com.voicetel.voiceml.models.CallEvent;
import com.voicetel.voiceml.models.CallList;
import com.voicetel.voiceml.models.CallPayment;
import com.voicetel.voiceml.models.CallTranscription;
import com.voicetel.voiceml.models.CreateCallRequest;
import com.voicetel.voiceml.models.EventsList;
import com.voicetel.voiceml.models.ListCallRecordingsParams;
import com.voicetel.voiceml.models.ListCallsParams;
import com.voicetel.voiceml.models.ListNotificationsParams;
import com.voicetel.voiceml.models.ListPageParams;
import com.voicetel.voiceml.models.Notification;
import com.voicetel.voiceml.models.NotificationsList;
import com.voicetel.voiceml.models.Recording;
import com.voicetel.voiceml.models.RecordingList;
import com.voicetel.voiceml.models.SiprecSession;
import com.voicetel.voiceml.models.StartPaymentRequest;
import com.voicetel.voiceml.models.StartRecordingRequest;
import com.voicetel.voiceml.models.StartSiprecRequest;
import com.voicetel.voiceml.models.StartStreamRequest;
import com.voicetel.voiceml.models.StartTranscriptionRequest;
import com.voicetel.voiceml.models.StopSiprecRequest;
import com.voicetel.voiceml.models.StopStreamRequest;
import com.voicetel.voiceml.models.StopTranscriptionRequest;
import com.voicetel.voiceml.models.Stream;
import com.voicetel.voiceml.models.UpdateCallRecordingRequest;
import com.voicetel.voiceml.models.UpdateCallRequest;
import com.voicetel.voiceml.models.UpdatePaymentRequest;
import com.voicetel.voiceml.models.UpdateRecordingRequest;

import java.util.Map;

/**
 * {@code /Calls} and call-scoped sub-resources (Recordings, Streams, Siprec, Transcriptions,
 * Notifications, Events, Payments, UserDefinedMessages).
 *
 * <p>Streams, SIPREC sessions and real-time transcriptions are started and stopped with
 * {@code POST}; the API has no list or fetch endpoints for them.
 */
public final class CallsResource extends BaseResource {

    /**
     * Create the resource; normally obtained from {@code client.calls()}.
     *
     * @param transport the transport
     */
    public CallsResource(Transport transport) {
        super(transport);
    }

    // --- /Calls ---

    /**
     * {@code GET /Calls}: one page of calls, optionally filtered.
     *
     * @param params filters and paging, or {@code null}
     * @return the page
     */
    public CallList list(ListCallsParams params) {
        Map<String, Object> q = params != null ? params.toQuery() : null;
        return decode(transport.request("GET", accountPath("Calls"), q, null), CallList.class);
    }

    /** @return the first page of calls (no filters) */
    public CallList list() {
        return list(null);
    }

    /**
     * Lazily iterate every call matching {@code params}, following {@code next_page_uri}.
     *
     * @param params filters and paging, or {@code null}
     * @return the pager
     */
    public Pager<Call> iterate(ListCallsParams params) {
        return pager(() -> list(params), CallList.class, CallList::getCalls);
    }

    /** @return a lazy pager over every call (no filters) */
    public Pager<Call> iterate() {
        return iterate(null);
    }

    /**
     * {@code POST /Calls}: create a new outbound call. Never retried by the SDK on a 5xx or timeout.
     *
     * @param body the call parameters
     * @return the call
     */
    public Call create(CreateCallRequest body) {
        return decode(
                transport.request("POST", accountPath("Calls"), null, body.toForm()),
                Call.class);
    }

    /**
     * {@code GET /Calls/{Sid}}.
     *
     * @param callSid the call SID
     * @return the call
     */
    public Call get(String callSid) {
        return decode(transport.request("GET", accountPath("Calls", callSid), null, null), Call.class);
    }

    /**
     * {@code POST /Calls/{Sid}}: update or terminate a live call.
     *
     * @param callSid the call SID
     * @param body the changes
     * @return the call
     */
    public Call update(String callSid, UpdateCallRequest body) {
        return decode(
                transport.request("POST", accountPath("Calls", callSid), null, body.toForm()),
                Call.class);
    }

    /**
     * {@code DELETE /Calls/{Sid}}.
     *
     * @param callSid the call SID
     */
    public void delete(String callSid) {
        transport.request("DELETE", accountPath("Calls", callSid), null, null);
    }

    // --- Recordings (call-scoped) ---

    /**
     * {@code GET /Calls/{CallSid}/Recordings}.
     *
     * @param callSid the call SID
     * @param params filters and paging, or {@code null}
     * @return the page
     */
    public RecordingList listRecordings(String callSid, ListCallRecordingsParams params) {
        Map<String, Object> q = params != null ? params.toQuery() : null;
        return decode(
                transport.request("GET", accountPath("Calls", callSid, "Recordings"), q, null),
                RecordingList.class);
    }

    /**
     * @param callSid the call SID
     * @return the first page of the call's recordings
     */
    public RecordingList listRecordings(String callSid) {
        return listRecordings(callSid, null);
    }

    /**
     * Lazily iterate every recording of a call.
     *
     * @param callSid the call SID
     * @param params filters and paging, or {@code null}
     * @return the pager
     */
    public Pager<Recording> iterateRecordings(String callSid, ListCallRecordingsParams params) {
        return pager(() -> listRecordings(callSid, params), RecordingList.class, RecordingList::getRecordings);
    }

    /**
     * @param callSid the call SID
     * @return a lazy pager over every recording of the call
     */
    public Pager<Recording> iterateRecordings(String callSid) {
        return iterateRecordings(callSid, null);
    }

    /**
     * {@code POST /Calls/{CallSid}/Recordings}: start recording a live call.
     *
     * @param callSid the call SID
     * @param body recording options, or {@code null}
     * @return the recording
     */
    public Recording startRecording(String callSid, StartRecordingRequest body) {
        Map<String, Object> form = body != null ? body.toForm() : null;
        return decode(
                transport.request("POST", accountPath("Calls", callSid, "Recordings"), null, form),
                Recording.class);
    }

    /**
     * @param callSid the call SID
     * @return the recording, started with default options
     */
    public Recording startRecording(String callSid) {
        return startRecording(callSid, null);
    }

    /**
     * {@code GET /Calls/{CallSid}/Recordings/{Sid}}. {@code Twilio.CURRENT} addresses the call's
     * active recording.
     *
     * @param callSid the call SID
     * @param recordingSid the recording SID
     * @return the recording
     */
    public Recording getRecording(String callSid, String recordingSid) {
        return decode(
                transport.request("GET", accountPath("Calls", callSid, "Recordings", recordingSid), null, null),
                Recording.class);
    }

    /**
     * {@code POST /Calls/{CallSid}/Recordings/{Sid}}: stop, pause or resume, with an optional
     * {@code PauseBehavior}.
     *
     * @param callSid the call SID
     * @param recordingSid the recording SID (or {@code Twilio.CURRENT})
     * @param body the new status
     * @return the recording
     */
    public Recording updateRecording(String callSid, String recordingSid, UpdateCallRecordingRequest body) {
        return decode(
                transport.request(
                        "POST",
                        accountPath("Calls", callSid, "Recordings", recordingSid),
                        null,
                        body.toForm()),
                Recording.class);
    }

    /**
     * {@code POST /Calls/{CallSid}/Recordings/{Sid}}: stop, pause or resume.
     *
     * @param callSid the call SID
     * @param recordingSid the recording SID (or {@code Twilio.CURRENT})
     * @param body the new status
     * @return the recording
     */
    public Recording updateRecording(String callSid, String recordingSid, UpdateRecordingRequest body) {
        return decode(
                transport.request(
                        "POST",
                        accountPath("Calls", callSid, "Recordings", recordingSid),
                        null,
                        body.toForm()),
                Recording.class);
    }

    /**
     * {@code DELETE /Calls/{CallSid}/Recordings/{Sid}}.
     *
     * @param callSid the call SID
     * @param recordingSid the recording SID
     */
    public void deleteRecording(String callSid, String recordingSid) {
        transport.request(
                "DELETE", accountPath("Calls", callSid, "Recordings", recordingSid), null, null);
    }

    // --- Streams ---

    /**
     * {@code POST /Calls/{CallSid}/Streams}: start a media stream.
     *
     * @param callSid the call SID
     * @param body stream options ({@code Url} is required)
     * @return the stream
     */
    public Stream startStream(String callSid, StartStreamRequest body) {
        return decode(
                transport.request("POST", accountPath("Calls", callSid, "Streams"), null, body.toForm()),
                Stream.class);
    }

    /**
     * {@code POST /Calls/{CallSid}/Streams/{Sid}}: stop a media stream.
     *
     * @param callSid the call SID
     * @param streamSid the stream SID (or name)
     * @param body the new status, or {@code null} for {@code Status=stopped}
     * @return the stream
     */
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

    /**
     * @param callSid the call SID
     * @param streamSid the stream SID (or name)
     * @return the stopped stream
     */
    public Stream stopStream(String callSid, String streamSid) {
        return stopStream(callSid, streamSid, null);
    }

    // --- SIPREC ---

    /**
     * {@code POST /Calls/{CallSid}/Siprec}: start a SIPREC session.
     *
     * @param callSid the call SID
     * @param body session options, or {@code null}
     * @return the session
     */
    public SiprecSession startSiprec(String callSid, StartSiprecRequest body) {
        Map<String, Object> form = body != null ? body.toForm() : null;
        return decode(
                transport.request("POST", accountPath("Calls", callSid, "Siprec"), null, form),
                SiprecSession.class);
    }

    /**
     * @param callSid the call SID
     * @return the session, started with default options
     */
    public SiprecSession startSiprec(String callSid) {
        return startSiprec(callSid, null);
    }

    /**
     * {@code POST /Calls/{CallSid}/Siprec/{Sid}}: stop a SIPREC session.
     *
     * @param callSid the call SID
     * @param siprecSid the session SID (or name)
     * @param body the new status, or {@code null} for {@code Status=stopped}
     * @return the session
     */
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

    /**
     * @param callSid the call SID
     * @param siprecSid the session SID (or name)
     * @return the stopped session
     */
    public SiprecSession stopSiprec(String callSid, String siprecSid) {
        return stopSiprec(callSid, siprecSid, null);
    }

    // --- Real-time transcriptions ---

    /**
     * {@code POST /Calls/{CallSid}/Transcriptions}: start a real-time transcription.
     *
     * @param callSid the call SID
     * @param body transcription options, or {@code null}
     * @return the transcription
     */
    public CallTranscription startTranscription(String callSid, StartTranscriptionRequest body) {
        Map<String, Object> form = body != null ? body.toForm() : null;
        return decode(
                transport.request(
                        "POST", accountPath("Calls", callSid, "Transcriptions"), null, form),
                CallTranscription.class);
    }

    /**
     * @param callSid the call SID
     * @return the transcription, started with default options
     */
    public CallTranscription startTranscription(String callSid) {
        return startTranscription(callSid, null);
    }

    /**
     * {@code POST /Calls/{CallSid}/Transcriptions/{Sid}}: stop a real-time transcription.
     *
     * @param callSid the call SID
     * @param transcriptionSid the transcription SID (or name)
     * @param body the new status, or {@code null} for {@code Status=stopped}
     * @return the transcription
     */
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

    /**
     * @param callSid the call SID
     * @param transcriptionSid the transcription SID (or name)
     * @return the stopped transcription
     */
    public CallTranscription stopTranscription(String callSid, String transcriptionSid) {
        return stopTranscription(callSid, transcriptionSid, null);
    }

    // --- Notifications / Events ---

    /**
     * {@code GET /Calls/{CallSid}/Notifications}.
     *
     * @param callSid the call SID
     * @param params filters and paging, or {@code null}
     * @return the page
     */
    public NotificationsList listNotifications(String callSid, ListNotificationsParams params) {
        Map<String, Object> q = params != null ? params.toQuery() : null;
        return decode(
                transport.request(
                        "GET", accountPath("Calls", callSid, "Notifications"), q, null),
                NotificationsList.class);
    }

    /**
     * @param callSid the call SID
     * @return the first page of the call's notifications
     */
    public NotificationsList listNotifications(String callSid) {
        return listNotifications(callSid, null);
    }

    /**
     * {@code GET /Calls/{CallSid}/Notifications/{Sid}}.
     *
     * @param callSid the call SID
     * @param notificationSid the notification SID
     * @return the notification
     */
    public Notification getNotification(String callSid, String notificationSid) {
        return decode(
                transport.request(
                        "GET",
                        accountPath("Calls", callSid, "Notifications", notificationSid),
                        null,
                        null),
                Notification.class);
    }

    /**
     * {@code GET /Calls/{CallSid}/Events}: the request/response log of the call's webhooks.
     *
     * @param callSid the call SID
     * @param params paging, or {@code null}
     * @return the page
     */
    public EventsList listEvents(String callSid, ListPageParams params) {
        Map<String, Object> q = params != null ? params.toQuery() : null;
        return decode(
                transport.request("GET", accountPath("Calls", callSid, "Events"), q, null),
                EventsList.class);
    }

    /**
     * @param callSid the call SID
     * @return the first page of the call's events
     */
    public EventsList listEvents(String callSid) {
        return listEvents(callSid, null);
    }

    /**
     * Lazily iterate every event of a call.
     *
     * @param callSid the call SID
     * @return the pager
     */
    public Pager<CallEvent> iterateEvents(String callSid) {
        return pager(() -> listEvents(callSid), EventsList.class, EventsList::getEvents);
    }

    // --- Payments (call-scoped <Pay> REST companion) ---

    /**
     * Start a {@code <Pay>} session on a live call. Returns 201 with the freshly-minted
     * {@link CallPayment}. The server returns 403 when payments are not enabled for the account.
     *
     * @param callSid the call SID
     * @param body payment options, or {@code null}
     * @return the payment session
     */
    public CallPayment startPayment(String callSid, StartPaymentRequest body) {
        Map<String, Object> form = body != null ? body.toForm() : null;
        return decode(
                transport.request("POST", accountPath("Calls", callSid, "Payments"), null, form),
                CallPayment.class);
    }

    /**
     * Advance or terminate an existing Pay session. {@code Status=complete} captures the collected
     * fields; {@code Status=cancel} aborts the session. {@code Capture=...} tells the runtime which
     * input the caller is about to type next.
     *
     * @param callSid the call SID
     * @param paymentSid the payment SID
     * @param body the update, or {@code null}
     * @return the payment session
     */
    public CallPayment updatePayment(String callSid, String paymentSid, UpdatePaymentRequest body) {
        Map<String, Object> form = body != null ? body.toForm() : null;
        return decode(
                transport.request(
                        "POST",
                        accountPath("Calls", callSid, "Payments", paymentSid),
                        null,
                        form),
                CallPayment.class);
    }

    // --- UserDefinedMessages: the server answers 501 ---

    /**
     * {@code POST /Calls/{CallSid}/UserDefinedMessages}.
     *
     * <p>The server mounts this only as a 501 stub, so this always raises
     * {@code NotImplementedException}; the method exists so ported code gets a clean exception.
     *
     * @param callSid the call SID
     */
    public void sendUserDefinedMessage(String callSid) {
        transport.request(
                "POST", accountPath("Calls", callSid, "UserDefinedMessages"), null, null);
    }
}
