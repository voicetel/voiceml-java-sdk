package com.voicetel.voiceml.models;

import com.fasterxml.jackson.annotation.JsonEnumDefaultValue;
import com.fasterxml.jackson.annotation.JsonValue;

/** Machine-detection result for a Call resource on the wire. */
public enum AnsweredBy {

    HUMAN("human"),
    MACHINE_START("machine_start"),
    MACHINE_END_BEEP("machine_end_beep"),
    MACHINE_END_SILENCE("machine_end_silence"),
    MACHINE_END_OTHER("machine_end_other"),
    FAX("fax"),
    /**
     * The server could not determine who answered. Values this SDK version does not recognise
     * also decode to {@code UNKNOWN}.
     */
    @JsonEnumDefaultValue
    UNKNOWN("unknown");

    private final String value;

    AnsweredBy(String value) {
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
    public static AnsweredBy fromString(String text) {
        if (text == null) {
            return null;
        }
        for (AnsweredBy a : values()) {
            if (a.value.equals(text)) {
                return a;
            }
        }
        return UNKNOWN;
    }

    @Override
    public String toString() {
        return value;
    }
}
