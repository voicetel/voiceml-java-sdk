package com.voicetel.voiceml;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

/** Exact wire assertions on raw (still percent-encoded) form bodies and query strings. */
final class WireAssert {

    private WireAssert() {}

    /**
     * Assert that {@code raw} consists of exactly the given {@code key=value} pairs, compared as raw
     * encoded bytes (so {@code +} and {@code %2B} stay distinct), in any order, with no extras.
     */
    static void exactPairs(String raw, String... pairs) {
        List<String> actual = raw == null || raw.isEmpty() ? List.of() : Arrays.asList(raw.split("&", -1));
        assertThat(actual).as("raw pairs of '" + raw + "'").containsExactlyInAnyOrder(pairs);
    }
}
