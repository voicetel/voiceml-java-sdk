package com.voicetel.voiceml.models;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;

/** Paginated response from {@code GET /Calls}. */
public class CallList extends Page {

    @JsonProperty("calls")
    private List<Call> calls = new ArrayList<>();

    public List<Call> getCalls() {
        return calls;
    }

    public void setCalls(List<Call> calls) {
        this.calls = calls;
    }
}
