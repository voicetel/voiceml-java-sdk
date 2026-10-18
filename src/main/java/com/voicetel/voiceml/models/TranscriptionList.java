package com.voicetel.voiceml.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;

/** Paginated {@code GET /Transcriptions} or {@code GET /Recordings/{RecordingSid}/Transcriptions} response. */
@JsonIgnoreProperties(ignoreUnknown = true)
public class TranscriptionList extends Page {

    @JsonProperty("transcriptions")
    private List<Transcription> transcriptions = new ArrayList<>();

    /** @return the {@code transcriptions} field */
    public List<Transcription> getTranscriptions() {
        return transcriptions;
    }

    /** @param transcriptions the {@code transcriptions} field */
    public void setTranscriptions(List<Transcription> transcriptions) {
        this.transcriptions = transcriptions;
    }
}
