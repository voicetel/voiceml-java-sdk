package com.voicetel.voiceml;

import org.junit.jupiter.api.Test;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

/** C10: X-Twilio-Signature validation against the shared fleet vectors. */
class RequestValidatorTest {

    private static final String KEY = "test-api-key-0123456789";
    private final RequestValidator validator = new RequestValidator(KEY);

    private static Map<String, List<String>> v1Params() {
        Map<String, List<String>> p = new LinkedHashMap<>();
        p.put("CallSid", List.of("CA0123456789abcdef0123456789abcdef"));
        p.put("From", List.of("+14155550100"));
        p.put("To", List.of("+14155550101"));
        p.put("Digits", List.of("1234"));
        p.put("AccountSid", List.of("ACXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX"));
        return p;
    }

    @Test
    void v1PostVector() {
        String url = "https://example.com/voice/answer?case=1";
        assertThat(validator.computeSignature(url, v1Params())).isEqualTo("RweoLyWqNC7H8+MLXPb7EPz18OI=");
        assertThat(validator.validate(url, v1Params(), "RweoLyWqNC7H8+MLXPb7EPz18OI=")).isTrue();
    }

    @Test
    void v1DefaultPortIsNormalisedBothWays() {
        String withPort = "https://example.com:443/voice/answer?case=1";
        String without = "https://example.com/voice/answer?case=1";
        assertThat(validator.validate(withPort, v1Params(), "RweoLyWqNC7H8+MLXPb7EPz18OI=")).isTrue();
        assertThat(validator.computeSignature(withPort, v1Params())).isEqualTo("xE0r2wPa6d1ktQeCFpPyINmTNYg=");
        assertThat(validator.validate(without, v1Params(), "xE0r2wPa6d1ktQeCFpPyINmTNYg=")).isTrue();
    }

    @Test
    void v3RepeatedKeysKeepReceivedOrder() {
        Map<String, List<String>> p = new LinkedHashMap<>();
        p.put("To", List.of("+14155550101"));
        p.put("Event", List.of("answered", "initiated"));
        assertThat(validator.computeSignature("https://example.com/hook", p)).isEqualTo("jCnpBgyg45aZYwUie5fmq4EYUic=");
        assertThat(validator.validate("https://example.com/hook", p, "jCnpBgyg45aZYwUie5fmq4EYUic=")).isTrue();
        Map<String, List<String>> reordered = new LinkedHashMap<>();
        reordered.put("To", List.of("+14155550101"));
        reordered.put("Event", List.of("initiated", "answered"));
        assertThat(validator.validate("https://example.com/hook", reordered, "jCnpBgyg45aZYwUie5fmq4EYUic=")).isFalse();
    }

    @Test
    void v4GetSignsTheUrlOnly() {
        String url = "https://example.com/voice/answer?CallSid=CA0123456789abcdef0123456789abcdef&From=%2B14155550100";
        assertThat(validator.computeSignature(url, null)).isEqualTo("9I4odWBHhF7TMIkIrNtPEPjD9fU=");
        assertThat(validator.validate(url, null, "9I4odWBHhF7TMIkIrNtPEPjD9fU=")).isTrue();
        assertThat(validator.validate(url, Map.of(), "9I4odWBHhF7TMIkIrNtPEPjD9fU=")).isTrue();
        assertThat(validator.validateSingle(url + "#fragment", null, "9I4odWBHhF7TMIkIrNtPEPjD9fU=")).isTrue();
    }

    @Test
    void negativeCases() {
        String url = "https://example.com/voice/answer?case=1";
        Map<String, List<String>> tampered = v1Params();
        tampered.put("Digits", List.of("9999"));
        assertThat(validator.validate(url, tampered, "RweoLyWqNC7H8+MLXPb7EPz18OI=")).isFalse();
        assertThat(new RequestValidator("wrong-key").validate(url, v1Params(), "RweoLyWqNC7H8+MLXPb7EPz18OI=")).isFalse();
        assertThat(validator.validate(url, v1Params(), "")).isFalse();
        assertThat(validator.validate(url, v1Params(), null)).isFalse();
        assertThat(validator.validate("https://example.com:8443/voice/answer?case=1", v1Params(),
                "RweoLyWqNC7H8+MLXPb7EPz18OI=")).isFalse();
    }

    @Test
    void singleValuedConvenienceMatches() {
        Map<String, String> p = new LinkedHashMap<>();
        v1Params().forEach((k, v) -> p.put(k, v.get(0)));
        assertThat(validator.computeSignatureSingle("https://example.com/voice/answer?case=1", p))
                .isEqualTo("RweoLyWqNC7H8+MLXPb7EPz18OI=");
    }

    @Test
    void urlVariants() {
        assertThat(RequestValidator.urlVariants("http://example.com/x"))
                .containsExactly("http://example.com/x", "http://example.com:80/x");
        assertThat(RequestValidator.urlVariants("http://example.com:80/x"))
                .containsExactly("http://example.com:80/x", "http://example.com/x");
        assertThat(RequestValidator.urlVariants("https://example.com:8443/x"))
                .containsExactly("https://example.com:8443/x");
    }
}
