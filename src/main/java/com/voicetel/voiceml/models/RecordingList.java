package com.voicetel.voiceml.models;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;

/**
 * Recordings list response.
 *
 * <p>The account-scoped endpoint ({@code GET /Recordings}) returns the full Twilio-compatible
 * pagination envelope. The per-call ({@code GET /Calls/{sid}/Recordings}) and per-conference
 * ({@code GET /Conferences/{sid}/Recordings}) endpoints currently return only the
 * {@code recordings} array — the inherited pagination fields will be {@code null}.
 */
public class RecordingList extends Page {

    @JsonProperty("recordings")
    private List<Recording> recordings = new ArrayList<>();

    public List<Recording> getRecordings() {
        return recordings;
    }

    public void setRecordings(List<Recording> recordings) {
        this.recordings = recordings;
    }
}
