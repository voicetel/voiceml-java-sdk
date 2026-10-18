package com.voicetel.voiceml.resources;

import com.voicetel.voiceml.Pager;
import com.voicetel.voiceml.Transport;
import com.voicetel.voiceml.models.ListTranscriptionsParams;
import com.voicetel.voiceml.models.Transcription;
import com.voicetel.voiceml.models.TranscriptionList;

import java.util.Map;

/**
 * Account-wide {@code /Transcriptions} (post-recording transcriptions). A recording's own
 * transcriptions are on {@code client.recordings().listTranscriptions(...)}.
 */
public final class TranscriptionsResource extends BaseResource {

    /**
     * Create the resource; normally obtained from {@code client.transcriptions()}.
     *
     * @param transport the transport
     */
    public TranscriptionsResource(Transport transport) {
        super(transport);
    }

    /**
     * {@code GET /Transcriptions}.
     *
     * @param params filters and paging, or {@code null}
     * @return the page
     */
    public TranscriptionList list(ListTranscriptionsParams params) {
        Map<String, Object> q = params != null ? params.toQuery() : null;
        return decode(transport.request("GET", accountPath("Transcriptions"), q, null), TranscriptionList.class);
    }

    /** @return the first page of transcriptions */
    public TranscriptionList list() {
        return list(null);
    }

    /**
     * Lazily iterate every transcription matching {@code params}.
     *
     * @param params filters and paging, or {@code null}
     * @return the pager
     */
    public Pager<Transcription> iterate(ListTranscriptionsParams params) {
        return pager(() -> list(params), TranscriptionList.class, TranscriptionList::getTranscriptions);
    }

    /**
     * {@code GET /Transcriptions/{Sid}}.
     *
     * @param sid the transcription SID
     * @return the transcription
     */
    public Transcription fetch(String sid) {
        return decode(transport.request("GET", accountPath("Transcriptions", sid), null, null), Transcription.class);
    }

    /**
     * {@code DELETE /Transcriptions/{Sid}}.
     *
     * @param sid the transcription SID
     */
    public void delete(String sid) {
        transport.request("DELETE", accountPath("Transcriptions", sid), null, null);
    }
}
