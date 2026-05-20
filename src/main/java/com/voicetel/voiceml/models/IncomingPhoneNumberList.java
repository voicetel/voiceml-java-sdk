package com.voicetel.voiceml.models;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;

/** Paginated response from {@code GET /IncomingPhoneNumbers}. */
public class IncomingPhoneNumberList extends Page {

    @JsonProperty("incoming_phone_numbers")
    private List<IncomingPhoneNumber> incomingPhoneNumbers = new ArrayList<>();

    public List<IncomingPhoneNumber> getIncomingPhoneNumbers() {
        return incomingPhoneNumbers;
    }

    public void setIncomingPhoneNumbers(List<IncomingPhoneNumber> incomingPhoneNumbers) {
        this.incomingPhoneNumbers = incomingPhoneNumbers;
    }
}
