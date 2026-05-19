package com.voicetel.voiceml.models;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;

/** Paginated response from {@code GET /Calls/{sid}/Siprec}. */
public class SiprecList extends Page {

    @JsonProperty("siprec")
    private List<SiprecSession> siprec = new ArrayList<>();

    public List<SiprecSession> getSiprec() {
        return siprec;
    }

    public void setSiprec(List<SiprecSession> siprec) {
        this.siprec = siprec;
    }
}
