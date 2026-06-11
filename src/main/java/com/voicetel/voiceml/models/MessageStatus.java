package com.voicetel.voiceml.models;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

/** Status of a Message resource on the wire. */
public enum MessageStatus {

    QUEUED("queued"),
    SENDING("sending"),
    SENT("sent"),
    FAILED("failed"),
    DELIVERED("delivered"),
    UNDELIVERED("undelivered"),
    RECEIVING("receiving"),
    RECEIVED("received"),
    ACCEPTED("accepted"),
    SCHEDULED("scheduled"),
    READ("read"),
    CANCELED("canceled");

    private final String value;

    MessageStatus(String value) {
        this.value = value;
    }

    @JsonValue
    public String getValue() {
        return value;
    }

    @JsonCreator
    public static MessageStatus fromString(String text) {
        if (text == null) {
            return null;
        }
        for (MessageStatus s : values()) {
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
