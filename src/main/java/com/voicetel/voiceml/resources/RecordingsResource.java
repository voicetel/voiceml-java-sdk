package com.voicetel.voiceml.resources;

import com.voicetel.voiceml.Pager;
import com.voicetel.voiceml.Transport;
import com.voicetel.voiceml.models.GetRecordingParams;
import com.voicetel.voiceml.models.ListRecordingsParams;
import com.voicetel.voiceml.models.ListTranscriptionsParams;
import com.voicetel.voiceml.models.Recording;
import com.voicetel.voiceml.models.RecordingAudio;
import com.voicetel.voiceml.models.RecordingList;
import com.voicetel.voiceml.models.Transcription;
import com.voicetel.voiceml.models.TranscriptionList;

import java.util.Map;

/**
 * Account-scoped {@code /Recordings} operations.
 *
 * <p>Per-call recording start/stop/list lives on {@link CallsResource} — this resource handles
 * the account-wide list, single-recording fetch (metadata and audio), delete, and the
 * recording's post-recording transcriptions.
 */
public final class RecordingsResource extends BaseResource {

    public RecordingsResource(Transport transport) {
        super(transport);
    }

    /** List recordings for the account. */
    public RecordingList list(ListRecordingsParams params) {
        Map<String, Object> q = params != null ? params.toQuery() : null;
        return decode(
                transport.request("GET", accountPath("Recordings"), q, null), RecordingList.class);
    }

    public RecordingList list(Integer page, Integer pageSize) {
        return list(ListRecordingsParams.builder().page(page).pageSize(pageSize).build());
    }

    public RecordingList list() {
        return list((ListRecordingsParams) null);
    }

    /**
     * Auto-paginate through all pages of {@code GET /Recordings}, collecting every
     * {@link Recording} into a single list.
     */
    public Pager<Recording> iterate(ListRecordingsParams params) {
        return pager(() -> list(params), RecordingList.class, RecordingList::getRecordings);
    }

    /** Auto-paginate all recordings (no filters). */
    public Pager<Recording> iterate() {
        return iterate(null);
    }

    /** Fetch the metadata JSON for a recording. */
    public Recording get(String recordingSid) {
        return get(recordingSid, null);
    }

    /** Fetch the metadata JSON for a recording with optional query params. */
    public Recording get(String recordingSid, GetRecordingParams params) {
        Map<String, Object> q = params != null ? params.toQuery() : null;
        return decode(
                transport.request("GET", accountPath("Recordings", recordingSid), q, null),
                Recording.class);
    }

    /**
     * Fetch the WAV audio for a recording.
     *
     * <p>Three server delivery shapes are flattened into one result: {@code 200 OK} (local file),
     * {@code 302 Found} to a presigned storage URL (followed without the {@code Authorization}
     * header; an https-to-http redirect is refused), and {@code 410 Gone} (no local file and no
     * stored copy; raises {@code GoneException}). The request uses the client's {@code HttpClient}
     * and timeout and sends {@code Accept: audio/*, *}{@code /*}.
     *
     * @param recordingSid the recording SID
     * @return the audio
     */
    public RecordingAudio getAudio(String recordingSid) {
        Transport.BinaryResponse resp =
                transport.fetchBytes(accountPathWithSuffix(".wav", "Recordings", recordingSid));
        return new RecordingAudio(
                recordingSid, resp.getContent(), resp.getContentType(), resp.isViaRedirect());
    }

    public void delete(String recordingSid) {
        transport.request("DELETE", accountPath("Recordings", recordingSid), null, null);
    }

    // --- /Recordings/{RecordingSid}/Transcriptions ---

    /**
     * {@code GET /Recordings/{RecordingSid}/Transcriptions}.
     *
     * @param recordingSid the recording SID
     * @param params filters and paging, or {@code null}
     * @return the page
     */
    public TranscriptionList listTranscriptions(String recordingSid, ListTranscriptionsParams params) {
        Map<String, Object> q = params != null ? params.toQuery() : null;
        return decode(
                transport.request("GET", accountPath("Recordings", recordingSid, "Transcriptions"), q, null),
                TranscriptionList.class);
    }

    /**
     * @param recordingSid the recording SID
     * @return the first page of the recording's transcriptions
     */
    public TranscriptionList listTranscriptions(String recordingSid) {
        return listTranscriptions(recordingSid, null);
    }

    /**
     * Lazily iterate every transcription of a recording.
     *
     * @param recordingSid the recording SID
     * @param params filters and paging, or {@code null}
     * @return the pager
     */
    public Pager<Transcription> iterateTranscriptions(String recordingSid, ListTranscriptionsParams params) {
        return pager(() -> listTranscriptions(recordingSid, params), TranscriptionList.class,
                TranscriptionList::getTranscriptions);
    }

    /**
     * {@code GET /Recordings/{RecordingSid}/Transcriptions/{Sid}}.
     *
     * @param recordingSid the recording SID
     * @param transcriptionSid the transcription SID
     * @return the transcription
     */
    public Transcription getTranscription(String recordingSid, String transcriptionSid) {
        return decode(
                transport.request("GET",
                        accountPath("Recordings", recordingSid, "Transcriptions", transcriptionSid), null, null),
                Transcription.class);
    }

    /**
     * {@code DELETE /Recordings/{RecordingSid}/Transcriptions/{Sid}}.
     *
     * @param recordingSid the recording SID
     * @param transcriptionSid the transcription SID
     */
    public void deleteTranscription(String recordingSid, String transcriptionSid) {
        transport.request("DELETE",
                accountPath("Recordings", recordingSid, "Transcriptions", transcriptionSid), null, null);
    }
}
