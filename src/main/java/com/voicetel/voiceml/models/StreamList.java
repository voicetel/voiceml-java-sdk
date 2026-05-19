package com.voicetel.voiceml.models;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;

/** Paginated response from {@code GET /Calls/{sid}/Streams}. */
public class StreamList extends Page {

    @JsonProperty("streams")
    private List<Stream> streams = new ArrayList<>();

    public List<Stream> getStreams() {
        return streams;
    }

    public void setStreams(List<Stream> streams) {
        this.streams = streams;
    }
}
