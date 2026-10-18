package com.voicetel.voiceml;

import com.voicetel.voiceml.exceptions.ValidationException;
import com.voicetel.voiceml.models.UpdateCallRecordingRequest;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

/** C3 / C4: every caller-supplied segment is percent-encoded; suffixes come from the route. */
class PathEncodingTest {

    private static final String A = "/2010-04-01/Accounts/ACXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX";

    private static String fetchPath(String sid) throws IOException {
        try (MockHttp mock = new MockHttp()) {
            mock.respond(200, "{\"sid\":\"CA1\"}");
            mock.client(0).calls().get(sid);
            MockHttp.Req r = mock.only();
            assertThat(r.rawQuery).isEmpty();
            return r.rawPath;
        }
    }

    @Test
    void reservedCharactersAreEncodedNotInterpreted() throws IOException {
        assertThat(fetchPath("CA1?Status=x")).isEqualTo(A + "/Calls/CA1%3FStatus%3Dx.json");
        assertThat(fetchPath("CA1#frag")).isEqualTo(A + "/Calls/CA1%23frag.json");
        assertThat(fetchPath("../Queues/QU1")).isEqualTo(A + "/Calls/..%2FQueues%2FQU1.json");
        assertThat(fetchPath("CA 1")).isEqualTo(A + "/Calls/CA%201.json");
        assertThat(fetchPath("100%")).isEqualTo(A + "/Calls/100%25.json");
        assertThat(fetchPath("a+b")).isEqualTo(A + "/Calls/a%2Bb.json");
        assertThat(fetchPath("café")).isEqualTo(A + "/Calls/caf%C3%A9.json");
        assertThat(fetchPath("A-z_0.9~")).isEqualTo(A + "/Calls/A-z_0.9~.json");
    }

    @Test
    void emptyAndDotSegmentsAreRejectedBeforeSending() throws IOException {
        try (MockHttp mock = new MockHttp()) {
            VoicemlClient client = mock.client(0);
            for (String bad : new String[] {"", ".", ".."}) {
                assertThatThrownBy(() -> client.calls().get(bad)).isInstanceOf(ValidationException.class);
                assertThatThrownBy(() -> client.calls().delete(bad)).isInstanceOf(ValidationException.class);
            }
            assertThatThrownBy(() -> client.calls().get(null)).isInstanceOf(ValidationException.class);
            assertThat(mock.requests).isEmpty();
        }
    }

    @Test
    void phoneNumbersAreEncodedTheSameEverywhere() throws IOException {
        try (MockHttp mock = new MockHttp()) {
            mock.respond(200, "{}");
            VoicemlClient client = mock.client(0);
            client.routesV2().phoneNumbers().fetch("+14155550100");
            client.pricing().v1().voice().numbers().fetch("+1 415 555 0100");
            client.pricing().v2().voice().numbers().fetch("+14155550100", "+14155550101");
            assertThat(mock.requests.get(0).rawPath).isEqualTo("/v2/PhoneNumbers/%2B14155550100");
            assertThat(mock.requests.get(1).rawPath).isEqualTo("/v1/Voice/Numbers/%2B1%20415%20555%200100");
            assertThat(mock.requests.get(2).rawPath).isEqualTo("/v2/Voice/Numbers/%2B14155550100");
            assertThat(mock.requests.get(2).rawQuery).isEqualTo("OriginationNumber=%2B14155550101");
        }
    }

    @Test
    void v1SegmentsAreEncoded() throws IOException {
        try (MockHttp mock = new MockHttp()) {
            mock.respond(200, "{}");
            VoicemlClient client = mock.client(0);
            client.conversationsV1().conversations().fetch("CH/1?x");
            assertThat(mock.only().rawPath).isEqualTo("/v1/Conversations/CH%2F1%3Fx");
        }
    }

    @Test
    void jsonSuffixComesFromTheRoute() throws IOException {
        try (MockHttp mock = new MockHttp()) {
            mock.respond(200, "{\"sid\":\"RE1\"}");
            VoicemlClient client = mock.client(0);
            // a dotted SID still gets the route's .json suffix
            client.calls().getRecording("CA1", "Twilio.CURRENT");
            client.calls().updateRecording("CA1", "Twilio.CURRENT",
                    UpdateCallRecordingRequest.builder().status("paused").pauseBehavior("skip").build());
            assertThat(mock.requests.get(0).rawPath).isEqualTo(A + "/Calls/CA1/Recordings/Twilio.CURRENT.json");
            assertThat(mock.requests.get(1).rawPath).isEqualTo(A + "/Calls/CA1/Recordings/Twilio.CURRENT.json");
            assertThat(mock.requests.get(1).body).isEqualTo("Status=paused&PauseBehavior=skip");
        }
    }

    @Test
    void exportsAndAudioUseTheirOwnSuffixes() throws IOException {
        try (MockHttp mock = new MockHttp()) {
            mock.respond(new MockHttp.Resp(200, "zip").header("Content-Type", "application/zip"))
                .respond(new MockHttp.Resp(200, "RIFF").header("Content-Type", "audio/wav"))
                .respond(200, "{\"ok\":true}");
            VoicemlClient client = mock.client(0);
            client.exports().download(java.time.LocalDate.of(2026, 10, 1));
            client.recordings().getAudio("RE1");
            client.diagnostics().health();
            assertThat(mock.requests.get(0).rawPath).isEqualTo(A + "/Exports/2026-10-01");
            assertThat(mock.requests.get(0).header("Accept")).isEqualTo("*/*");
            assertThat(mock.requests.get(1).rawPath).isEqualTo(A + "/Recordings/RE1.wav");
            assertThat(mock.requests.get(1).header("Accept")).isEqualTo("audio/*, */*");
            assertThat(mock.requests.get(2).rawPath).isEqualTo("/health");
        }
    }
}
