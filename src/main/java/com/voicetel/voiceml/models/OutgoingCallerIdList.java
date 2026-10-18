package com.voicetel.voiceml.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;

/** Paginated {@code GET /OutgoingCallerIds} response. */
@JsonIgnoreProperties(ignoreUnknown = true)
public class OutgoingCallerIdList extends Page {

    @JsonProperty("outgoing_caller_ids")
    private List<OutgoingCallerId> outgoingCallerIds = new ArrayList<>();

    /** @return the {@code outgoing_caller_ids} field */
    public List<OutgoingCallerId> getOutgoingCallerIds() {
        return outgoingCallerIds;
    }

    /** @param outgoingCallerIds the {@code outgoing_caller_ids} field */
    public void setOutgoingCallerIds(List<OutgoingCallerId> outgoingCallerIds) {
        this.outgoingCallerIds = outgoingCallerIds;
    }
}
