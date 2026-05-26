package com.voicetel.voiceml.models;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

/** Status of a Participant resource on the wire. */
public enum ParticipantStatus {

    QUEUED("queued"),
    CONNECTING("connecting"),
    RINGING("ringing"),
    CONNECTED("connected"),
    ON_HOLD("on-hold"),
    COMPLETE("complete"),
    FAILED("failed"),
    COMPLETED("completed");

    private final String value;

    ParticipantStatus(String value) {
        this.value = value;
    }

    @JsonValue
    public String getValue() {
        return value;
    }

    @JsonCreator
    public static ParticipantStatus fromString(String text) {
        if (text == null) {
            return null;
        }
        for (ParticipantStatus s : values()) {
            if (s.value.equals(text)) {
                return s;
            }
        }
        return null;
    }

    @Override
    public String toString() {
        return value;
    }
}
