package com.voicetel.voiceml.models;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

/** Source of a Recording resource on the wire. */
public enum RecordingSource {

    OUTBOUND_API("OutboundAPI"),
    RECORD_VERB("RecordVerb"),
    DIAL_VERB("DialVerb"),
    CONFERENCE("Conference"),
    TRUNKING("Trunking"),
    START_CALL_RECORDING_API("StartCallRecordingAPI"),
    START_CONFERENCE_RECORDING_API("StartConferenceRecordingAPI");

    private final String value;

    RecordingSource(String value) {
        this.value = value;
    }

    @JsonValue
    public String getValue() {
        return value;
    }

    @JsonCreator
    public static RecordingSource fromString(String text) {
        if (text == null) {
            return null;
        }
        for (RecordingSource s : values()) {
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
