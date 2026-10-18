package com.voicetel.voiceml.models;

import com.fasterxml.jackson.annotation.JsonEnumDefaultValue;
import com.fasterxml.jackson.annotation.JsonValue;

/** Source of a Recording resource on the wire. */
public enum RecordingSource {

    OUTBOUND_API("OutboundAPI"),
    RECORD_VERB("RecordVerb"),
    DIAL_VERB("DialVerb"),
    CONFERENCE("Conference"),
    TRUNKING("Trunking"),
    START_CALL_RECORDING_API("StartCallRecordingAPI"),
    START_CONFERENCE_RECORDING_API("StartConferenceRecordingAPI"),

    /**
     * Placeholder for a value this SDK version does not recognise. Decode-only: the server may add
     * values at any time, and they decode to {@code UNKNOWN} instead of failing. The SDK refuses to
     * send it.
     */
    @JsonEnumDefaultValue
    UNKNOWN("unknown");

    private final String value;

    RecordingSource(String value) {
        this.value = value;
    }

    /** @return the wire value */
    @JsonValue
    public String getValue() {
        return value;
    }

    /**
     * Parse a wire value.
     *
     * @param text the wire value
     * @return the constant, {@code UNKNOWN} for an unrecognised value, or {@code null} for {@code null}
     */
    public static RecordingSource fromString(String text) {
        if (text == null) {
            return null;
        }
        for (RecordingSource s : values()) {
            if (s.value.equals(text)) {
                return s;
            }
        }
        return UNKNOWN;
    }

    @Override
    public String toString() {
        return value;
    }
}
