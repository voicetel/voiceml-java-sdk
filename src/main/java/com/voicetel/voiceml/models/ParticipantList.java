package com.voicetel.voiceml.models;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;

/** Paginated response from {@code GET /Conferences/{sid}/Participants}. */
public class ParticipantList extends Page {

    @JsonProperty("participants")
    private List<Participant> participants = new ArrayList<>();

    public List<Participant> getParticipants() {
        return participants;
    }

    public void setParticipants(List<Participant> participants) {
        this.participants = participants;
    }
}
