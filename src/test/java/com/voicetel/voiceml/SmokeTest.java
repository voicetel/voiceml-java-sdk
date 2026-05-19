package com.voicetel.voiceml;

import com.sun.net.httpserver.HttpServer;
import com.voicetel.voiceml.exceptions.ApiException;
import com.voicetel.voiceml.exceptions.AuthenticationException;
import com.voicetel.voiceml.exceptions.ConfigurationException;
import com.voicetel.voiceml.exceptions.NotFoundException;
import com.voicetel.voiceml.exceptions.NotImplementedException;
import com.voicetel.voiceml.exceptions.RateLimitException;
import com.voicetel.voiceml.models.Call;
import com.voicetel.voiceml.models.CallList;
import com.voicetel.voiceml.models.CreateCallRequest;
import com.voicetel.voiceml.models.ListCallsParams;
import com.voicetel.voiceml.models.UpdateParticipantRequest;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.nio.charset.StandardCharsets;
import java.util.ArrayDeque;
import java.util.Base64;
import java.util.Deque;
import java.util.concurrent.atomic.AtomicInteger;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.Assertions.fail;

/**
 * Smoke tests for the VoiceML Java SDK.
 *
 * <p>Spins up a {@link HttpServer} (built into the JDK) on a random port and points the SDK at
 * it. The server records every request into a queue so each test can assert path, headers,
 * query, and body.
 */
class SmokeTest {

    private HttpServer server;
    private int port;
    private Deque<RecordedRequest> recorded;

    @BeforeEach
    void start() throws IOException {
        server = HttpServer.create(new InetSocketAddress("127.0.0.1", 0), 0);
        port = server.getAddress().getPort();
        recorded = new ArrayDeque<>();
        server.start();
    }

    @AfterEach
    void stop() {
        if (server != null) {
            server.stop(0);
        }
    }

    private VoicemlClient client(int maxRetries) {
        return VoicemlClient.builder()
                .accountSid("ACtest")
                .apiKey("secret")
                .baseUrl("http://127.0.0.1:" + port)
                .maxRetries(maxRetries)
                .build();
    }

    private VoicemlClient client() {
        return client(0);
    }

    /** Register a one-shot handler that records the request and returns the supplied response. */
    private void handle(String path, int status, String body) {
        server.createContext(path, exchange -> {
            byte[] reqBody = exchange.getRequestBody().readAllBytes();
            String query = exchange.getRequestURI().getRawQuery();
            recorded.addLast(new RecordedRequest(
                    exchange.getRequestMethod(),
                    exchange.getRequestURI().getPath(),
                    query == null ? "" : query,
                    new String(reqBody, StandardCharsets.UTF_8),
                    exchange.getRequestHeaders().getFirst("Authorization"),
                    exchange.getRequestHeaders().getFirst("Content-Type"),
                    exchange.getRequestHeaders().getFirst("User-Agent")));
            byte[] payload = body == null ? new byte[0] : body.getBytes(StandardCharsets.UTF_8);
            exchange.getResponseHeaders().set("Content-Type", "application/json");
            exchange.sendResponseHeaders(status, payload.length == 0 ? -1 : payload.length);
            try (OutputStream os = exchange.getResponseBody()) {
                os.write(payload);
            }
        });
    }

    /** Register a sequenced handler that returns a different status each call. */
    private void handleSequence(String path, int[] statuses, String[] bodies) {
        AtomicInteger idx = new AtomicInteger();
        server.createContext(path, exchange -> {
            int i = idx.getAndIncrement();
            byte[] reqBody = exchange.getRequestBody().readAllBytes();
            recorded.addLast(new RecordedRequest(
                    exchange.getRequestMethod(),
                    exchange.getRequestURI().getPath(),
                    exchange.getRequestURI().getRawQuery() == null
                            ? ""
                            : exchange.getRequestURI().getRawQuery(),
                    new String(reqBody, StandardCharsets.UTF_8),
                    exchange.getRequestHeaders().getFirst("Authorization"),
                    exchange.getRequestHeaders().getFirst("Content-Type"),
                    exchange.getRequestHeaders().getFirst("User-Agent")));
            int status = statuses[Math.min(i, statuses.length - 1)];
            String body = bodies[Math.min(i, bodies.length - 1)];
            byte[] payload = body == null ? new byte[0] : body.getBytes(StandardCharsets.UTF_8);
            exchange.getResponseHeaders().set("Content-Type", "application/json");
            exchange.sendResponseHeaders(status, payload.length == 0 ? -1 : payload.length);
            try (OutputStream os = exchange.getResponseBody()) {
                os.write(payload);
            }
        });
    }

    // --- Tests ---

    @Test
    void builderRequiresAccountSid() {
        assertThatThrownBy(() -> VoicemlClient.builder().apiKey("k").build())
                .isInstanceOf(ConfigurationException.class)
                .hasMessageContaining("accountSid");
    }

    @Test
    void builderRequiresApiKey() {
        assertThatThrownBy(() -> VoicemlClient.builder().accountSid("AC").build())
                .isInstanceOf(ConfigurationException.class)
                .hasMessageContaining("apiKey");
    }

    @Test
    void defaultBaseUrl() {
        VoicemlClient c = VoicemlClient.builder().accountSid("ACtest").apiKey("k").build();
        assertThat(c.baseUrl()).isEqualTo("https://voiceml.voicetel.com");
    }

    @Test
    void createCallSendsFormBodyAndBasicAuth() {
        handle("/2010-04-01/Accounts/ACtest/Calls", 201,
                "{\"sid\":\"CA1\",\"account_sid\":\"ACtest\",\"api_version\":\"2010-04-01\","
                        + "\"status\":\"queued\",\"direction\":\"outbound-api\"}");

        Call call = client().calls().create(
                CreateCallRequest.builder()
                        .to("+18005551234")
                        .from("+18005550000")
                        .url("https://example.com/twiml")
                        .machineDetection("DetectMessageEnd")
                        .build());

        assertThat(call.getSid()).isEqualTo("CA1");
        assertThat(call.getStatus()).isEqualTo("queued");

        RecordedRequest r = recorded.removeFirst();
        assertThat(r.method).isEqualTo("POST");
        assertThat(r.path).isEqualTo("/2010-04-01/Accounts/ACtest/Calls");
        assertThat(r.contentType).isEqualTo("application/x-www-form-urlencoded");
        assertThat(r.body)
                .contains("To=%2B18005551234")
                .contains("From=%2B18005550000")
                .contains("Url=https%3A%2F%2Fexample.com%2Ftwiml")
                .contains("MachineDetection=DetectMessageEnd");

        String expectedAuth =
                "Basic " + Base64.getEncoder().encodeToString("ACtest:secret".getBytes(StandardCharsets.UTF_8));
        assertThat(r.authorization).isEqualTo(expectedAuth);
        assertThat(r.userAgent).startsWith("voiceml-java/0.4.0");
    }

    @Test
    void listCallsTranslatesStartTimeGteAndLte() {
        handle("/2010-04-01/Accounts/ACtest/Calls", 200,
                "{\"calls\":[],\"page\":0,\"page_size\":50}");

        CallList list = client().calls().list(
                ListCallsParams.builder()
                        .startTimeGte("2026-04-01T00:00:00Z")
                        .startTimeLte("2026-04-30T23:59:59Z")
                        .pageSize(50)
                        .build());

        assertThat(list.getCalls()).isEmpty();

        RecordedRequest r = recorded.removeFirst();
        assertThat(r.method).isEqualTo("GET");
        assertThat(r.query).contains("StartTime%3E%3D=2026-04-01T00%3A00%3A00Z");
        assertThat(r.query).contains("StartTime%3C%3D=2026-04-30T23%3A59%3A59Z");
        assertThat(r.query).contains("PageSize=50");
    }

    @Test
    void booleanEncodingTrueAndFalse() {
        handle(
                "/2010-04-01/Accounts/ACtest/Conferences/CF1/Participants/CA1",
                200,
                "{\"call_sid\":\"CA1\",\"conference_sid\":\"CF1\",\"account_sid\":\"ACtest\","
                        + "\"muted\":true,\"hold\":false,\"start_conference_on_enter\":true,"
                        + "\"end_conference_on_exit\":false,\"status\":\"connected\","
                        + "\"api_version\":\"2010-04-01\",\"uri\":\"/x\"}");

        client().conferences().updateParticipant(
                "CF1",
                "CA1",
                UpdateParticipantRequest.builder().muted(true).hold(false).build());

        RecordedRequest r = recorded.removeFirst();
        assertThat(r.method).isEqualTo("POST");
        assertThat(r.body)
                .contains("Muted=true")
                .contains("Hold=false");
    }

    @Test
    void error401MapsToAuthenticationException() {
        handle("/2010-04-01/Accounts/ACtest/Calls", 401,
                "{\"code\":20003,\"message\":\"Authenticate\"}");
        assertThatThrownBy(() -> client().calls().list())
                .isInstanceOf(AuthenticationException.class)
                .satisfies(e -> {
                    ApiException api = (ApiException) e;
                    assertThat(api.getStatusCode()).isEqualTo(401);
                    assertThat(api.getCode()).isEqualTo(20003);
                });
    }

    @Test
    void error404MapsToNotFoundException() {
        handle("/2010-04-01/Accounts/ACtest/Calls/CAmissing", 404,
                "{\"code\":20404,\"message\":\"Not Found\"}");
        assertThatThrownBy(() -> client().calls().get("CAmissing"))
                .isInstanceOf(NotFoundException.class);
    }

    @Test
    void error429MapsToRateLimitException() {
        handle("/2010-04-01/Accounts/ACtest/Calls", 429,
                "{\"code\":20429,\"message\":\"Too Many Requests\"}");
        assertThatThrownBy(() -> client().calls().list())
                .isInstanceOf(RateLimitException.class);
    }

    @Test
    void error501MapsToNotImplementedException() {
        handle("/2010-04-01/Accounts/ACtest/Calls/CA1/UserDefinedMessages", 501,
                "{\"code\":20501,\"message\":\"Not Implemented\"}");
        assertThatThrownBy(() -> client().calls().sendUserDefinedMessage("CA1", null))
                .isInstanceOf(NotImplementedException.class);
    }

    @Test
    void error409CarriesCode20409() {
        handle("/2010-04-01/Accounts/ACtest/Queues/QU1", 409,
                "{\"code\":20409,\"message\":\"Queue not empty\"}");
        try {
            client().queues().delete("QU1");
            fail("expected ConflictException");
        } catch (ApiException ex) {
            assertThat(ex.getStatusCode()).isEqualTo(409);
            assertThat(ex.getCode()).isEqualTo(20409);
        }
    }

    @Test
    void retryOn503ThenSucceedsOn200() {
        handleSequence(
                "/2010-04-01/Accounts/ACtest/Calls",
                new int[] {503, 200},
                new String[] {
                        "{\"code\":20500,\"message\":\"Service Unavailable\"}",
                        "{\"calls\":[],\"page\":0,\"page_size\":50}"
                });

        CallList list = client(1).calls().list();

        assertThat(list).isNotNull();
        assertThat(recorded).hasSize(2);
    }

    /** Verifies the Twilio-style mock answers without retry exhaustion in default config. */
    @Test
    void retryDisabledRaisesImmediately() {
        handleSequence(
                "/2010-04-01/Accounts/ACtest/Calls",
                new int[] {503, 200},
                new String[] {
                        "{\"code\":20500,\"message\":\"Service Unavailable\"}",
                        "{\"calls\":[]}"
                });

        assertThatThrownBy(() -> client(0).calls().list())
                .isInstanceOf(ApiException.class)
                .satisfies(e -> assertThat(((ApiException) e).getStatusCode()).isEqualTo(503));
        assertThat(recorded).hasSize(1);
    }

    /** Sanity check that empty 2xx bodies don't blow up the JSON parser. */
    @Test
    void deleteSucceedsOnEmpty204() {
        handle("/2010-04-01/Accounts/ACtest/Calls/CA1", 204, null);
        client().calls().delete("CA1");
        RecordedRequest r = recorded.removeFirst();
        assertThat(r.method).isEqualTo("DELETE");
    }

    /** Recorded data class for tests. Plain POJO so we don't need records on Java 11. */
    private static final class RecordedRequest {
        final String method;
        final String path;
        final String query;
        final String body;
        final String authorization;
        final String contentType;
        final String userAgent;

        RecordedRequest(String method, String path, String query, String body,
                        String authorization, String contentType, String userAgent) {
            this.method = method;
            this.path = path;
            this.query = query;
            this.body = body;
            this.authorization = authorization;
            this.contentType = contentType;
            this.userAgent = userAgent;
        }
    }

    @SuppressWarnings("unused")
    private static byte[] drain(InputStream is) throws IOException {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        byte[] buf = new byte[1024];
        int n;
        while ((n = is.read(buf)) > 0) {
            out.write(buf, 0, n);
        }
        return out.toByteArray();
    }
}
