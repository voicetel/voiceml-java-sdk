package com.voicetel.voiceml.models;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;

/** Paginated response from {@code GET /Conferences}. */
public class ConferenceList extends Page {

    @JsonProperty("conferences")
    private List<Conference> conferences = new ArrayList<>();

    public List<Conference> getConferences() {
        return conferences;
    }

    public void setConferences(List<Conference> conferences) {
        this.conferences = conferences;
    }
}
