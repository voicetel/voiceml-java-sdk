package com.voicetel.voiceml;

import com.voicetel.voiceml.exceptions.ApiException;
import com.voicetel.voiceml.exceptions.RateLimitException;
import com.voicetel.voiceml.exceptions.RequestTimeoutException;
import com.voicetel.voiceml.exceptions.ServerException;
import com.voicetel.voiceml.exceptions.TransportException;
import com.voicetel.voiceml.models.CreateCallRequest;
import com.voicetel.voiceml.models.CreateMessageRequest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.io.IOException;
import java.net.ConnectException;
import java.net.ServerSocket;
import java.net.http.HttpConnectTimeoutException;
import java.net.http.HttpTimeoutException;
import java.time.Duration;
import java.time.Instant;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import javax.net.ssl.SSLHandshakeException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

/** C2 retry policy: idempotent requests retry, POSTs never duplicate. */
class RetryPolicyTest {

    private static final String CALL_JSON = "{\"sid\":\"CA0123456789abcdef0123456789abcdef\",\"status\":\"queued\"}";

    private static CreateCallRequest call() {
        return CreateCallRequest.builder().to("+14155550100").from("+14155550101")
                .url("https://example.com/twiml").build();
    }

    @ParameterizedTest
    @ValueSource(ints = {500, 502, 503, 504})
    void postIsNotRetriedOn5xx(int status) throws IOException {
        try (MockHttp mock = new MockHttp()) {
            mock.respond(status, "{\"code\":20500,\"message\":\"originate failed\"}");
            long start = System.nanoTime();
            assertThatThrownBy(() -> mock.client(2).calls().create(call()))
                    .isInstanceOf(ServerException.class)
                    .satisfies(e -> assertThat(((ApiException) e).getStatusCode()).isEqualTo(status));
            assertThat(mock.requests).hasSize(1);
            assertThat(Duration.ofNanos(System.nanoTime() - start)).isLessThan(Duration.ofMillis(400));
        }
    }

    @Test
    void postIsNotRetriedOnReadTimeout() throws IOException {
        try (MockHttp mock = new MockHttp()) {
            mock.respond(new MockHttp.Resp(201, "{}").delay(1500));
            VoicemlClient client = VoicemlClient.builder()
                    .accountSid("ACXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX").apiKey("k")
                    .baseUrl(mock.url()).timeout(Duration.ofMillis(300)).maxRetries(2).build();
            assertThatThrownBy(() -> client.messages().create(
                    CreateMessageRequest.builder().to("+14155550100").body("hi").build()))
                    .isInstanceOf(RequestTimeoutException.class)
                    .hasCauseInstanceOf(HttpTimeoutException.class)
                    .satisfies(e -> assertThat(((ApiException) e).getStatusCode()).isZero());
            assertThat(mock.requests).hasSize(1);
        }
    }

    @Test
    void postIsRetriedOn429() throws IOException {
        try (MockHttp mock = new MockHttp()) {
            mock.respond(new MockHttp.Resp(429, "{\"code\":20429}").header("Retry-After", "0"))
                .respond(201, CALL_JSON);
            assertThat(mock.client(2).calls().create(call()).getSid()).startsWith("CA");
            assertThat(mock.requests).hasSize(2);
            assertThat(mock.requests.get(0).body).isEqualTo(mock.requests.get(1).body);
        }
    }

    @Test
    void postIsRetriedWhenTheConnectionIsRefused() throws IOException {
        int closedPort;
        try (ServerSocket s = new ServerSocket(0)) {
            closedPort = s.getLocalPort();
        }
        VoicemlClient client = VoicemlClient.builder()
                .accountSid("ACXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX").apiKey("k")
                .baseUrl("http://127.0.0.1:" + closedPort).maxRetries(2).build();
        assertThatThrownBy(() -> client.calls().create(call()))
                .isInstanceOf(TransportException.class)
                .hasMessageContaining("(3 attempts)")
                .hasCauseInstanceOf(ConnectException.class);
    }

    @Test
    void connectPhaseFailuresAreClassifiedAsNotSent() {
        assertThat(Transport.provablyNotSent(new ConnectException("refused"))).isTrue();
        assertThat(Transport.provablyNotSent(new HttpConnectTimeoutException("connect"))).isTrue();
        assertThat(Transport.provablyNotSent(new SSLHandshakeException("bad cert"))).isTrue();
        assertThat(Transport.provablyNotSent(new IOException(new java.net.UnknownHostException("x")))).isTrue();
        assertThat(Transport.provablyNotSent(new HttpTimeoutException("request timed out"))).isFalse();
        assertThat(Transport.provablyNotSent(new IOException("Connection reset"))).isFalse();
    }

    @Test
    void getIsRetriedOn503() throws IOException {
        try (MockHttp mock = new MockHttp()) {
            mock.respond(new MockHttp.Resp(503, "{}").header("Retry-After", "0"))
                .respond(new MockHttp.Resp(502, "{}").header("Retry-After", "0"))
                .respond(200, CALL_JSON);
            assertThat(mock.client(2).calls().get("CA1").getSid()).startsWith("CA");
            assertThat(mock.requests).hasSize(3);
        }
    }

    @Test
    void getIsRetriedOnReadTimeout() throws IOException {
        try (MockHttp mock = new MockHttp()) {
            mock.respond(new MockHttp.Resp(200, CALL_JSON).delay(1500)).respond(200, CALL_JSON);
            VoicemlClient client = VoicemlClient.builder()
                    .accountSid("ACXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX").apiKey("k")
                    .baseUrl(mock.url()).timeout(Duration.ofMillis(300)).maxRetries(1).build();
            assertThat(client.calls().get("CA1").getSid()).startsWith("CA");
            assertThat(mock.requests).hasSize(2);
        }
    }

    @Test
    void deleteIsRetriedOn503() throws IOException {
        try (MockHttp mock = new MockHttp()) {
            mock.respond(new MockHttp.Resp(503, "{}").header("Retry-After", "0")).respond(204, null);
            mock.client(1).calls().delete("CA1");
            assertThat(mock.requests).hasSize(2);
            assertThat(mock.requests.get(1).method).isEqualTo("DELETE");
        }
    }

    @Test
    void retryAfterSecondsIsHonoured() throws IOException {
        try (MockHttp mock = new MockHttp()) {
            mock.respond(new MockHttp.Resp(429, "{}").header("Retry-After", "1")).respond(200, CALL_JSON);
            long start = System.nanoTime();
            mock.client(1).calls().get("CA1");
            assertThat(Duration.ofNanos(System.nanoTime() - start)).isGreaterThanOrEqualTo(Duration.ofMillis(950));
            assertThat(mock.requests).hasSize(2);
        }
    }

    @Test
    void retryAfterHttpDateIsHonoured() throws IOException {
        try (MockHttp mock = new MockHttp()) {
            String when = DateTimeFormatter.RFC_1123_DATE_TIME.format(
                    Instant.now().plusSeconds(2).atOffset(ZoneOffset.UTC));
            mock.respond(new MockHttp.Resp(503, "{}").header("Retry-After", when)).respond(200, CALL_JSON);
            long start = System.nanoTime();
            mock.client(1).calls().get("CA1");
            // second granularity: the wait is between 1 and 2 seconds
            assertThat(Duration.ofNanos(System.nanoTime() - start)).isGreaterThanOrEqualTo(Duration.ofMillis(900));
            assertThat(mock.requests).hasSize(2);
        }
    }

    @Test
    void retryAfterAboveTheCapRaisesImmediatelyWithTheDelay() throws IOException {
        try (MockHttp mock = new MockHttp()) {
            mock.respond(new MockHttp.Resp(429, "{\"code\":20429,\"message\":\"slow down\"}")
                    .header("Retry-After", "3600"));
            long start = System.nanoTime();
            assertThatThrownBy(() -> mock.client(2).calls().list())
                    .isInstanceOf(RateLimitException.class)
                    .satisfies(e -> assertThat(((ApiException) e).getRetryAfter()).isEqualTo(Duration.ofHours(1)));
            assertThat(mock.requests).hasSize(1);
            assertThat(Duration.ofNanos(System.nanoTime() - start)).isLessThan(Duration.ofMillis(500));
        }
    }

    @Test
    void retryAfterParsing() {
        Instant now = Instant.parse("2026-10-17T12:00:00Z");
        assertThat(Transport.parseRetryAfter("7", now)).isEqualTo(Duration.ofSeconds(7));
        assertThat(Transport.parseRetryAfter(" 0 ", now)).isEqualTo(Duration.ZERO);
        assertThat(Transport.parseRetryAfter("Sat, 17 Oct 2026 12:00:30 GMT", now)).isEqualTo(Duration.ofSeconds(30));
        assertThat(Transport.parseRetryAfter("Sat, 17 Oct 2026 11:00:00 GMT", now)).isEqualTo(Duration.ZERO);
        assertThat(Transport.parseRetryAfter(null, now)).isNull();
        assertThat(Transport.parseRetryAfter("", now)).isNull();
        assertThat(Transport.parseRetryAfter("Infinity", now)).isNull();
        assertThat(Transport.parseRetryAfter("1e3", now)).isNull();
        assertThat(Transport.parseRetryAfter("-5", now)).isNull();
        assertThat(Transport.parseRetryAfter("1.5", now)).isNull();
        assertThat(Transport.parseRetryAfter("soon", now)).isNull();
    }

    @Test
    void backoffIsFullJitterWithinTheCap() {
        for (int attempt = 0; attempt < 8; attempt++) {
            long cap = Math.min(8000L, 500L << Math.min(attempt, 4));
            for (int i = 0; i < 200; i++) {
                assertThat(Transport.backoffDelayMs(attempt)).isBetween(0L, cap);
            }
        }
    }

    @Test
    void retryableStatusDependsOnIdempotency() {
        for (int s : new int[] {429, 500, 502, 503, 504}) {
            assertThat(Transport.retryableStatus(s, true)).isTrue();
        }
        assertThat(Transport.retryableStatus(429, false)).isTrue();
        for (int s : new int[] {500, 502, 503, 504, 400, 409}) {
            assertThat(Transport.retryableStatus(s, false)).isFalse();
        }
    }

    @Test
    void interruptedRequestIsNotRetriedAndKeepsTheFlag() throws Exception {
        try (MockHttp mock = new MockHttp()) {
            mock.respond(new MockHttp.Resp(503, "{}").header("Retry-After", "5"));
            VoicemlClient client = mock.client(2);
            Throwable[] seen = new Throwable[1];
            boolean[] flag = new boolean[1];
            Thread t = new Thread(() -> {
                try {
                    client.calls().get("CA1");
                } catch (Throwable e) {
                    seen[0] = e;
                    flag[0] = Thread.currentThread().isInterrupted();
                }
            });
            t.start();
            Thread.sleep(500);
            t.interrupt();
            t.join(5000);
            assertThat(seen[0]).isInstanceOf(TransportException.class).hasCauseInstanceOf(InterruptedException.class);
            assertThat(flag[0]).isTrue();
            assertThat(mock.requests).hasSize(1);
        }
    }
}
