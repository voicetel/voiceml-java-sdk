package com.voicetel.voiceml.resources;

import com.voicetel.voiceml.Transport;
import com.voicetel.voiceml.models.GetRecordingParams;
import com.voicetel.voiceml.models.ListRecordingsParams;
import com.voicetel.voiceml.models.Recording;
import com.voicetel.voiceml.models.RecordingAudio;
import com.voicetel.voiceml.models.RecordingList;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Account-scoped {@code /Recordings} operations.
 *
 * <p>Per-call recording start/stop/list lives on {@link CallsResource} — this resource handles
 * the account-wide list, single-recording fetch (metadata and audio), and delete.
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
    public List<Recording> iterate(ListRecordingsParams params) {
        List<Recording> out = new ArrayList<>();
        Map<String, Object> q = params != null ? params.toQuery() : new LinkedHashMap<>();
        int page = q.containsKey("Page") ? ((Number) q.get("Page")).intValue() : 0;
        while (true) {
            q.put("Page", page);
            RecordingList chunk = decode(
                    transport.request("GET", accountPath("Recordings"), q, null),
                    RecordingList.class);
            out.addAll(chunk.getRecordings());
            if (chunk.getNextPageUri() == null || chunk.getNextPageUri().isEmpty()
                    || chunk.getRecordings().isEmpty()) {
                return out;
            }
            page++;
        }
    }

    /** Auto-paginate all recordings (no filters). */
    public List<Recording> iterate() {
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
     * <p>Three server delivery shapes are flattened into one result by following any 302 redirect
     * to S3: {@code 200 OK} (local file), {@code 302 Found} → presigned S3 URL, {@code 410 Gone}
     * (no local file and no S3 key — raises {@code GoneException}).
     */
    public RecordingAudio getAudio(String recordingSid) {
        Transport.BinaryResponse resp =
                transport.fetchBytes(accountPath("Recordings", recordingSid) + ".wav");
        return new RecordingAudio(
                recordingSid, resp.getContent(), resp.getContentType(), resp.isViaRedirect());
    }

    public void delete(String recordingSid) {
        transport.request("DELETE", accountPath("Recordings", recordingSid), null, null);
    }
}
