package com.voicetel.voiceml.models;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

/** Machine-detection result for a Call resource on the wire. */
public enum AnsweredBy {

    HUMAN("human"),
    MACHINE_START("machine_start"),
    MACHINE_END_BEEP("machine_end_beep"),
    MACHINE_END_SILENCE("machine_end_silence"),
    MACHINE_END_OTHER("machine_end_other"),
    FAX("fax"),
    UNKNOWN("unknown");

    private final String value;

    AnsweredBy(String value) {
        this.value = value;
    }

    @JsonValue
    public String getValue() {
        return value;
    }

    @JsonCreator
    public static AnsweredBy fromString(String text) {
        if (text == null) {
            return null;
        }
        for (AnsweredBy a : values()) {
            if (a.value.equals(text)) {
                return a;
            }
        }
        return null;
    }

    @Override
    public String toString() {
        return value;
    }
}
