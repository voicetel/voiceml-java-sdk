package com.voicetel.voiceml.models;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;

/** Paginated response from {@code GET /Calls/{sid}/Transcriptions}. */
public class TranscriptionList extends Page {

    @JsonProperty("transcriptions")
    private List<CallTranscription> transcriptions = new ArrayList<>();

    public List<CallTranscription> getTranscriptions() {
        return transcriptions;
    }

    public void setTranscriptions(List<CallTranscription> transcriptions) {
        this.transcriptions = transcriptions;
    }
}
