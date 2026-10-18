package com.voicetel.voiceml.models;

import com.voicetel.voiceml.exceptions.ValidationException;

/** Guards request builders against sending the decode-only {@code UNKNOWN} enum placeholder. */
final class SendableEnums {

    private SendableEnums() {}

    static <E extends Enum<E>> E check(E value) {
        if (value != null && value.name().equals("UNKNOWN")) {
            throw new ValidationException(value.getDeclaringClass().getSimpleName()
                    + ".UNKNOWN is a decode-only placeholder and cannot be sent");
        }
        return value;
    }
}
