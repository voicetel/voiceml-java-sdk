package com.voicetel.voiceml.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;

/** Paginated {@code GET /Calls/{CallSid}/Events} response. */
@JsonIgnoreProperties(ignoreUnknown = true)
public class EventsList extends Page {

    @JsonProperty("events")
    private List<CallEvent> events = new ArrayList<>();

    /** @return the {@code events} field */
    public List<CallEvent> getEvents() {
        return events;
    }

    /** @param events the {@code events} field */
    public void setEvents(List<CallEvent> events) {
        this.events = events;
    }
}
