package com.voicetel.voiceml;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.net.httpserver.HttpServer;
import com.voicetel.voiceml.exceptions.ApiException;
import com.voicetel.voiceml.exceptions.AuthenticationException;
import com.voicetel.voiceml.exceptions.ConfigurationException;
import com.voicetel.voiceml.exceptions.ConflictException;
import com.voicetel.voiceml.exceptions.NotFoundException;
import com.voicetel.voiceml.exceptions.NotImplementedException;
import com.voicetel.voiceml.exceptions.RateLimitException;
import com.voicetel.voiceml.models.Call;
import com.voicetel.voiceml.models.CallList;
import com.voicetel.voiceml.models.CallPayment;
import com.voicetel.voiceml.models.CallStatus;
import com.voicetel.voiceml.models.Conference;
import com.voicetel.voiceml.models.ConferenceList;
import com.voicetel.voiceml.models.CreateCallRequest;
import com.voicetel.voiceml.models.CreateIncomingPhoneNumberRequest;
import com.voicetel.voiceml.models.CreateMessageRequest;
import com.voicetel.voiceml.models.IncomingPhoneNumber;
import com.voicetel.voiceml.models.IncomingPhoneNumberList;
import com.voicetel.voiceml.models.CreateParticipantRequest;
import com.voicetel.voiceml.models.ListCallsParams;
import com.voicetel.voiceml.models.ListIncomingPhoneNumbersParams;
import com.voicetel.voiceml.models.ListMessagesParams;
import com.voicetel.voiceml.models.ListNotificationsParams;
import com.voicetel.voiceml.models.ListRecordingsParams;
import com.voicetel.voiceml.models.Message;
import com.voicetel.voiceml.models.MessageList;
import com.voicetel.voiceml.models.MessageStatus;
import com.voicetel.voiceml.models.Participant;
import com.voicetel.voiceml.models.PaymentCapture;
import com.voicetel.voiceml.models.PaymentMethod;
import com.voicetel.voiceml.models.PaymentSessionStatus;
import com.voicetel.voiceml.models.PaymentTokenType;
import com.voicetel.voiceml.models.Queue;
import com.voicetel.voiceml.models.QueueList;
import com.voicetel.voiceml.models.Recording;
import com.voicetel.voiceml.models.RecordingList;
import com.voicetel.voiceml.models.RecordingSource;
import com.voicetel.voiceml.models.RecordingStatus;
import com.voicetel.voiceml.models.StartPaymentRequest;
import com.voicetel.voiceml.models.UpdateIncomingPhoneNumberRequest;
import com.voicetel.voiceml.models.UpdateMessageRequest;
import com.voicetel.voiceml.models.UpdateParticipantRequest;
import com.voicetel.voiceml.models.UpdatePaymentRequest;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.util.ArrayDeque;
import java.util.Base64;
import java.util.Deque;
import java.util.List;
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

    private static <T> List<T> toList(Iterable<T> items) {
        List<T> out = new java.util.ArrayList<>();
        items.forEach(out::add);
        return out;
    }

    /** Register a one-shot handler that records the request and returns the supplied response. */
    private void handle(String path, int status, String body) {
        server.createContext(path, exchange -> {
            byte[] reqBody = exchange.getRequestBody().readAllBytes();
            String query = exchange.getRequestURI().getRawQuery();
            recorded.addLast(new RecordedRequest(
                    exchange.getRequestMethod(),
                    exchange.getRequestURI().getRawPath(),
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
                    exchange.getRequestURI().getRawPath(),
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
                        .to("+14155550100")
                        .from("+14155550101")
                        .url("https://example.com/twiml")
                        .machineDetection("DetectMessageEnd")
                        .timeLimit(3600)
                        .build());

        assertThat(call.getSid()).isEqualTo("CA1");
        assertThat(call.getStatus()).isEqualTo(CallStatus.QUEUED);

        RecordedRequest r = recorded.removeFirst();
        assertThat(r.method).isEqualTo("POST");
        assertThat(r.path).isEqualTo("/2010-04-01/Accounts/ACtest/Calls.json");
        assertThat(r.contentType).isEqualTo("application/x-www-form-urlencoded");
        WireAssert.exactPairs(r.body, "To=%2B14155550100", "From=%2B14155550101", "Url=https%3A%2F%2Fexample.com%2Ftwiml", "MachineDetection=DetectMessageEnd", "TimeLimit=3600");

        String expectedAuth =
                "Basic " + Base64.getEncoder().encodeToString("ACtest:secret".getBytes(StandardCharsets.UTF_8));
        assertThat(r.authorization).isEqualTo(expectedAuth);
        assertThat(r.userAgent).startsWith("voiceml-java/0.9.5");
    }

    @Test
    void listCallsSendsStartTimeRangeWireNames() {
        handle("/2010-04-01/Accounts/ACtest/Calls", 200,
                "{\"calls\":[],\"page\":0,\"page_size\":50}");

        CallList list = client().calls().list(
                ListCallsParams.builder()
                        .startTimeGt("2026-04-01T00:00:00Z")
                        .startTimeLt("2026-04-30T23:59:59Z")
                        .pageSize(50)
                        .build());

        assertThat(list.getCalls()).isEmpty();

        RecordedRequest r = recorded.removeFirst();
        assertThat(r.method).isEqualTo("GET");
        assertThat(r.path).isEqualTo("/2010-04-01/Accounts/ACtest/Calls.json");
        assertThat(r.query).isEqualTo(
                "StartTime%3C=2026-04-30T23%3A59%3A59Z&StartTime%3E=2026-04-01T00%3A00%3A00Z&PageSize=50");
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
        WireAssert.exactPairs(r.body, "Muted=true", "Hold=false");
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
        assertThatThrownBy(() -> client().calls().sendUserDefinedMessage("CA1"))
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

    // --- v0.5.0: .json URL suffix ---

    @Test
    void requestPathsCarryJsonSuffix() {
        handle("/2010-04-01/Accounts/ACtest/Calls/CA1", 200,
                "{\"sid\":\"CA1\",\"account_sid\":\"ACtest\",\"api_version\":\"2010-04-01\","
                        + "\"status\":\"completed\",\"direction\":\"outbound-api\"}");

        client().calls().get("CA1");

        RecordedRequest r = recorded.removeFirst();
        assertThat(r.path).isEqualTo("/2010-04-01/Accounts/ACtest/Calls/CA1.json");
    }

    // --- v0.5.0: IncomingPhoneNumbers ---

    @Test
    void incomingPhoneNumbersListSendsJsonPathAndDecodesEnvelope() {
        handle("/2010-04-01/Accounts/ACtest/IncomingPhoneNumbers", 200,
                "{\"incoming_phone_numbers\":[{"
                        + "\"sid\":\"PN" + "0123456789abcdef0123456789abcdef\","
                        + "\"account_sid\":\"ACtest\","
                        + "\"phone_number\":\"+14155550100\","
                        + "\"friendly_name\":null,"
                        + "\"api_version\":\"2010-04-01\","
                        + "\"uri\":\"/2010-04-01/Accounts/ACtest/IncomingPhoneNumbers/PN"
                        + "0123456789abcdef0123456789abcdef.json\","
                        + "\"voice_url\":\"https://example.com/twiml\","
                        + "\"voice_method\":\"POST\","
                        + "\"capabilities\":{\"voice\":true,\"sms\":false,\"mms\":false,\"fax\":false},"
                        + "\"date_created\":\"Wed, 20 May 2026 00:00:00 +0000\","
                        + "\"date_updated\":\"Wed, 20 May 2026 00:00:00 +0000\""
                        + "}],\"page\":0,\"page_size\":50,"
                        + "\"first_page_uri\":\"/x\",\"next_page_uri\":null}");

        IncomingPhoneNumberList list = client().incomingPhoneNumbers().list(
                ListIncomingPhoneNumbersParams.builder()
                        .phoneNumber("+14155550100")
                        .pageSize(50)
                        .build());

        assertThat(list.getIncomingPhoneNumbers()).hasSize(1);
        IncomingPhoneNumber ipn = list.getIncomingPhoneNumbers().get(0);
        assertThat(ipn.getSid()).startsWith("PN");
        assertThat(ipn.getSid()).hasSize(34);
        assertThat(ipn.getPhoneNumber()).isEqualTo("+14155550100");
        assertThat(ipn.getCapabilities()).isNotNull();
        assertThat(ipn.getCapabilities().getVoice()).isTrue();
        assertThat(list.getNextPageUri()).isNull();

        RecordedRequest r = recorded.removeFirst();
        assertThat(r.method).isEqualTo("GET");
        assertThat(r.path).isEqualTo("/2010-04-01/Accounts/ACtest/IncomingPhoneNumbers.json");
        WireAssert.exactPairs(r.query, "PhoneNumber=%2B14155550100", "PageSize=50");
    }

    @Test
    void incomingPhoneNumbersCreatePostsForm() {
        handle("/2010-04-01/Accounts/ACtest/IncomingPhoneNumbers", 201,
                "{\"sid\":\"PN" + "00000000000000000000000000000001\","
                        + "\"account_sid\":\"ACtest\","
                        + "\"phone_number\":\"+14155550100\","
                        + "\"api_version\":\"2010-04-01\","
                        + "\"uri\":\"/x\","
                        + "\"capabilities\":{\"voice\":true,\"sms\":false,\"mms\":false,\"fax\":false}}");

        IncomingPhoneNumber ipn = client().incomingPhoneNumbers().create(
                CreateIncomingPhoneNumberRequest.builder()
                        .phoneNumber("+14155550100")
                        .voiceUrl("https://example.com/twiml")
                        .voiceMethod("POST")
                        .voiceApplicationSid("AP0123456789abcdef0123456789abcdef")
                        .statusCallback("https://example.com/inbound")
                        .statusCallbackMethod("GET")
                        .build());

        assertThat(ipn.getSid()).matches("PN[a-f0-9]{32}");

        RecordedRequest r = recorded.removeFirst();
        assertThat(r.method).isEqualTo("POST");
        assertThat(r.path).isEqualTo("/2010-04-01/Accounts/ACtest/IncomingPhoneNumbers.json");
        assertThat(r.contentType).isEqualTo("application/x-www-form-urlencoded");
        WireAssert.exactPairs(r.body, "PhoneNumber=%2B14155550100", "VoiceUrl=https%3A%2F%2Fexample.com%2Ftwiml", "VoiceMethod=POST",
                "VoiceApplicationSid=AP0123456789abcdef0123456789abcdef",
                "StatusCallback=https%3A%2F%2Fexample.com%2Finbound", "StatusCallbackMethod=GET");
    }

    @Test
    void incomingPhoneNumbersGetByPnSidUsesJsonPath() {
        String sid = "PN0123456789abcdef0123456789abcdef";
        handle("/2010-04-01/Accounts/ACtest/IncomingPhoneNumbers/" + sid, 200,
                "{\"sid\":\"" + sid + "\",\"account_sid\":\"ACtest\","
                        + "\"phone_number\":\"+14155550100\",\"api_version\":\"2010-04-01\","
                        + "\"uri\":\"/x\","
                        + "\"capabilities\":{\"voice\":true,\"sms\":false,\"mms\":false,\"fax\":false}}");

        IncomingPhoneNumber ipn = client().incomingPhoneNumbers().get(sid);

        assertThat(ipn.getSid()).isEqualTo(sid);
        RecordedRequest r = recorded.removeFirst();
        assertThat(r.path)
                .isEqualTo("/2010-04-01/Accounts/ACtest/IncomingPhoneNumbers/" + sid + ".json");
    }

    @Test
    void incomingPhoneNumbersUpdatePostsForm() {
        String sid = "PN0123456789abcdef0123456789abcdef";
        handle("/2010-04-01/Accounts/ACtest/IncomingPhoneNumbers/" + sid, 200,
                "{\"sid\":\"" + sid + "\",\"account_sid\":\"ACtest\","
                        + "\"phone_number\":\"+14155550100\",\"api_version\":\"2010-04-01\","
                        + "\"uri\":\"/x\","
                        + "\"voice_url\":\"https://example.com/new\","
                        + "\"capabilities\":{\"voice\":true,\"sms\":false,\"mms\":false,\"fax\":false}}");

        IncomingPhoneNumber ipn = client().incomingPhoneNumbers().update(
                sid,
                UpdateIncomingPhoneNumberRequest.builder()
                        .voiceUrl("https://example.com/new")
                        .voiceMethod("POST")
                        .voiceApplicationSid("AP0123456789abcdef0123456789abcdef")
                        .statusCallback("https://example.com/inbound")
                        .statusCallbackMethod("GET")
                        .build());

        assertThat(ipn.getVoiceUrl()).isEqualTo("https://example.com/new");

        RecordedRequest r = recorded.removeFirst();
        assertThat(r.method).isEqualTo("POST");
        assertThat(r.path)
                .isEqualTo("/2010-04-01/Accounts/ACtest/IncomingPhoneNumbers/" + sid + ".json");
        WireAssert.exactPairs(r.body, "VoiceUrl=https%3A%2F%2Fexample.com%2Fnew", "VoiceMethod=POST",
                "VoiceApplicationSid=AP0123456789abcdef0123456789abcdef",
                "StatusCallback=https%3A%2F%2Fexample.com%2Finbound", "StatusCallbackMethod=GET");
    }

    @Test
    void incomingPhoneNumbersDeleteIsIdempotent204() {
        String sid = "PN0123456789abcdef0123456789abcdef";
        handle("/2010-04-01/Accounts/ACtest/IncomingPhoneNumbers/" + sid, 204, null);

        client().incomingPhoneNumbers().delete(sid);

        RecordedRequest r = recorded.removeFirst();
        assertThat(r.method).isEqualTo("DELETE");
        assertThat(r.path)
                .isEqualTo("/2010-04-01/Accounts/ACtest/IncomingPhoneNumbers/" + sid + ".json");
    }

    @Test
    void incomingPhoneNumbersCreate409MapsToConflictException() {
        handle("/2010-04-01/Accounts/ACtest/IncomingPhoneNumbers", 409,
                "{\"code\":20409,\"message\":\"PhoneNumber already assigned to another account\"}");

        assertThatThrownBy(() -> client().incomingPhoneNumbers().create(
                CreateIncomingPhoneNumberRequest.builder().phoneNumber("+14155550100").build()))
                .isInstanceOf(ConflictException.class)
                .satisfies(e -> assertThat(((ApiException) e).getStatusCode()).isEqualTo(409));
    }

    // --- v0.6.0: expanded IncomingPhoneNumber Twilio-compat outer field set ---

    @Test
    void incomingPhoneNumberDeserializesFullTwilioFieldSet() {
        String sid = "PN0123456789abcdef0123456789abcdef";
        handle("/2010-04-01/Accounts/ACtest/IncomingPhoneNumbers/" + sid, 200,
                "{\"sid\":\"" + sid + "\","
                        + "\"account_sid\":\"ACtest\","
                        + "\"phone_number\":\"+14155550100\","
                        + "\"friendly_name\":\"\","
                        + "\"api_version\":\"2010-04-01\","
                        + "\"uri\":\"/2010-04-01/Accounts/ACtest/IncomingPhoneNumbers/" + sid + ".json\","
                        + "\"origin\":\"\","
                        + "\"beta\":false,"
                        + "\"type\":\"local\","
                        + "\"voice_url\":\"https://example.com/twiml\","
                        + "\"voice_method\":\"POST\","
                        + "\"voice_fallback_url\":\"https://example.com/fallback\","
                        + "\"voice_fallback_method\":\"POST\","
                        + "\"voice_application_sid\":\"\","
                        + "\"voice_caller_id_lookup\":false,"
                        + "\"voice_receive_mode\":\"voice\","
                        + "\"sms_url\":\"\","
                        + "\"sms_method\":\"\","
                        + "\"sms_fallback_url\":\"\","
                        + "\"sms_fallback_method\":\"\","
                        + "\"sms_application_sid\":\"\","
                        + "\"status_callback\":\"\","
                        + "\"status_callback_method\":\"\","
                        + "\"trunk_sid\":\"\","
                        + "\"address_sid\":\"\","
                        + "\"address_requirements\":\"none\","
                        + "\"identity_sid\":\"\","
                        + "\"bundle_sid\":\"\","
                        + "\"emergency_status\":\"\","
                        + "\"emergency_address_sid\":\"\","
                        + "\"emergency_address_status\":\"\","
                        + "\"status\":\"\","
                        + "\"capabilities\":{\"voice\":true,\"sms\":false,\"mms\":false,\"fax\":false},"
                        + "\"date_created\":\"Wed, 20 May 2026 00:00:00 +0000\","
                        + "\"date_updated\":\"Wed, 20 May 2026 00:00:00 +0000\""
                        + "}");

        IncomingPhoneNumber ipn = client().incomingPhoneNumbers().get(sid);

        // Existing core fields still bind.
        assertThat(ipn.getSid()).isEqualTo(sid);
        assertThat(ipn.getAccountSid()).isEqualTo("ACtest");
        assertThat(ipn.getPhoneNumber()).isEqualTo("+14155550100");
        assertThat(ipn.getCapabilities().getVoice()).isTrue();

        // Spot-check the new Twilio-compat outer fields.
        assertThat(ipn.getEmergencyStatus()).isEqualTo("");
        assertThat(ipn.getVoiceCallerIdLookup()).isFalse();
        assertThat(ipn.getAddressRequirements()).isEqualTo("none");
        assertThat(ipn.getBeta()).isFalse();
        assertThat(ipn.getVoiceReceiveMode()).isEqualTo("voice");
        assertThat(ipn.getType()).isEqualTo("local");
    }

    // --- v0.5.0: authToken alias (CC-2) ---

    @Test
    void authTokenAliasWorksLikeApiKey() {
        VoicemlClient c = VoicemlClient.builder()
                .accountSid("ACtest")
                .authToken("aliased-secret")
                .baseUrl("http://127.0.0.1:" + port)
                .maxRetries(0)
                .build();

        handle("/2010-04-01/Accounts/ACtest/Calls", 200,
                "{\"calls\":[],\"page\":0,\"page_size\":50}");

        c.calls().list();

        RecordedRequest r = recorded.removeFirst();
        String expectedAuth = "Basic " + Base64.getEncoder()
                .encodeToString("ACtest:aliased-secret".getBytes(StandardCharsets.UTF_8));
        assertThat(r.authorization).isEqualTo(expectedAuth);
    }

    @Test
    void apiKeyAndAuthTokenSetSimultaneouslyThrowsConfigurationException() {
        assertThatThrownBy(() -> VoicemlClient.builder()
                .accountSid("ACtest")
                .apiKey("one")
                .authToken("two")
                .build())
                .isInstanceOf(ConfigurationException.class)
                .hasMessageContaining("apiKey")
                .hasMessageContaining("authToken");
    }

    @Test
    void authTokenAloneSatisfiesApiKeyRequirement() {
        VoicemlClient c = VoicemlClient.builder()
                .accountSid("ACtest")
                .authToken("only-set-here")
                .build();
        assertThat(c.accountSid()).isEqualTo("ACtest");
    }

    // --- v0.5.0: ApiException.getMoreInfo (CC-6) ---

    @Test
    void apiExceptionExposesMoreInfoFromErrorBody() {
        handle("/2010-04-01/Accounts/ACtest/Calls/CAmissing", 404,
                "{\"code\":20404,\"message\":\"Not Found\","
                        + "\"more_info\":\"https://voicetel.com/docs/errors/20404\"}");

        assertThatThrownBy(() -> client().calls().get("CAmissing"))
                .isInstanceOf(NotFoundException.class)
                .satisfies(e -> {
                    ApiException api = (ApiException) e;
                    assertThat(api.getMoreInfo())
                            .isEqualTo("https://voicetel.com/docs/errors/20404");
                });
    }

    @Test
    void apiExceptionMoreInfoIsNullWhenAbsent() {
        handle("/2010-04-01/Accounts/ACtest/Calls/CAmissing", 404,
                "{\"code\":20404,\"message\":\"Not Found\"}");

        assertThatThrownBy(() -> client().calls().get("CAmissing"))
                .isInstanceOf(NotFoundException.class)
                .satisfies(e -> assertThat(((ApiException) e).getMoreInfo()).isNull());
    }

    // --- v0.6.2: Recording.media_url (D5) ---

    @Test
    void recordingDeserializesMediaUrlWhenPresent() throws Exception {
        String json = "{\"sid\":\"RE0123456789abcdef0123456789abcdef\","
                + "\"account_sid\":\"ACtest\","
                + "\"call_sid\":\"CA0123456789abcdef0123456789abcdef\","
                + "\"status\":\"completed\","
                + "\"duration\":\"7\","
                + "\"api_version\":\"2010-04-01\","
                + "\"uri\":\"/2010-04-01/Accounts/ACtest/Recordings/RE0123456789abcdef0123456789abcdef.json\","
                + "\"media_url\":\"https://voiceml.voicetel.com/2010-04-01/Accounts/ACtest/Recordings/RE0123456789abcdef0123456789abcdef.mp3\""
                + "}";

        Recording rec = new ObjectMapper().readValue(json, Recording.class);

        assertThat(rec.getSid()).isEqualTo("RE0123456789abcdef0123456789abcdef");
        assertThat(rec.getMediaUrl())
                .isEqualTo("https://voiceml.voicetel.com/2010-04-01/Accounts/ACtest/Recordings/RE0123456789abcdef0123456789abcdef.mp3");
    }

    @Test
    void recordingDeserializesWithoutMediaUrl() throws Exception {
        String json = "{\"sid\":\"RE0123456789abcdef0123456789abcdef\","
                + "\"account_sid\":\"ACtest\","
                + "\"status\":\"completed\","
                + "\"api_version\":\"2010-04-01\","
                + "\"uri\":\"/x\""
                + "}";

        Recording rec = new ObjectMapper().readValue(json, Recording.class);

        assertThat(rec.getSid()).isEqualTo("RE0123456789abcdef0123456789abcdef");
        assertThat(rec.getMediaUrl()).isNull();
    }

    // --- v0.6.2: IncomingPhoneNumber.type (D6) confirmation ---

    @Test
    void incomingPhoneNumberTypeFieldDeserializes() throws Exception {
        String json = "{\"sid\":\"PN0123456789abcdef0123456789abcdef\","
                + "\"account_sid\":\"ACtest\","
                + "\"phone_number\":\"+14155550100\","
                + "\"api_version\":\"2010-04-01\","
                + "\"uri\":\"/x\","
                + "\"type\":\"toll-free\","
                + "\"capabilities\":{\"voice\":true,\"sms\":false,\"mms\":false,\"fax\":false}"
                + "}";

        IncomingPhoneNumber ipn = new ObjectMapper().readValue(json, IncomingPhoneNumber.class);

        assertThat(ipn.getType()).isEqualTo("toll-free");
    }

    // --- v0.6.3: Participant coaching, Recording.error_code, list filter params ---

    @Test
    void participantCoachingFieldsDeserialize() throws Exception {
        String json = "{\"call_sid\":\"CA0123456789abcdef0123456789abcdef\","
                + "\"conference_sid\":\"CF0123456789abcdef0123456789abcdef\","
                + "\"account_sid\":\"ACtest\","
                + "\"muted\":false,\"hold\":false,"
                + "\"coaching\":true,"
                + "\"call_sid_to_coach\":\"CAfedcba9876543210fedcba9876543210\","
                + "\"queue_time\":\"12\","
                + "\"start_conference_on_enter\":true,"
                + "\"end_conference_on_exit\":false,"
                + "\"status\":\"connected\","
                + "\"api_version\":\"2010-04-01\",\"uri\":\"/x\"}";

        Participant p = new ObjectMapper().readValue(json, Participant.class);

        assertThat(p.getCoaching()).isTrue();
        assertThat(p.getCallSidToCoach()).isEqualTo("CAfedcba9876543210fedcba9876543210");
        assertThat(p.getQueueTime()).isEqualTo("12");
    }

    @Test
    void recordingErrorCodeAndSourceDeserialize() throws Exception {
        String json = "{\"sid\":\"RE0123456789abcdef0123456789abcdef\","
                + "\"account_sid\":\"ACtest\","
                + "\"call_sid\":\"CA0123456789abcdef0123456789abcdef\","
                + "\"status\":\"completed\","
                + "\"source\":\"StartConferenceRecordingAPI\","
                + "\"error_code\":null,"
                + "\"api_version\":\"2010-04-01\",\"uri\":\"/x\"}";

        Recording rec = new ObjectMapper().readValue(json, Recording.class);

        assertThat(rec.getSource()).isEqualTo(RecordingSource.START_CONFERENCE_RECORDING_API);
        assertThat(rec.getErrorCode()).isNull();
    }

    @Test
    void listCallsTranslatesStartTimeEndTimeTripleOperators() {
        handle("/2010-04-01/Accounts/ACtest/Calls", 200,
                "{\"calls\":[],\"page\":0,\"page_size\":50}");

        client().calls().list(
                ListCallsParams.builder()
                        .startTime("2026-05-01")
                        .startTimeLt("2026-05-02")
                        .startTimeGt("2026-04-30")
                        .endTime("2026-05-21")
                        .endTimeLt("2026-05-22")
                        .endTimeGt("2026-05-20")
                        .build());

        RecordedRequest r = recorded.removeFirst();
        WireAssert.exactPairs(r.query,
                "StartTime=2026-05-01",
                "StartTime%3C=2026-05-02",
                "StartTime%3E=2026-04-30",
                "EndTime=2026-05-21",
                "EndTime%3C=2026-05-22",
                "EndTime%3E=2026-05-20");
    }

    @Test
    void listCallsSendsPageToken() {
        handle("/2010-04-01/Accounts/ACtest/Calls", 200,
                "{\"calls\":[],\"page\":0,\"page_size\":50}");

        client().calls().list(
                ListCallsParams.builder()
                        .pageToken("PTabc123")
                        .build());

        RecordedRequest r = recorded.removeFirst();
        WireAssert.exactPairs(r.query, "PageToken=PTabc123");
    }

    @Test
    void versionIs092() {
        assertThat(com.voicetel.voiceml.Version.VERSION).isEqualTo("0.9.5");
    }

    @Test
    void createParticipantSendsFromAndTo() {
        String confSid = "CF0123456789abcdef0123456789abcdef";
        handle("/2010-04-01/Accounts/ACtest/Conferences/" + confSid + "/Participants", 201,
                "{\"call_sid\":\"CA0123456789abcdef0123456789abcdef\","
                        + "\"conference_sid\":\"" + confSid + "\","
                        + "\"account_sid\":\"ACtest\",\"status\":\"queued\","
                        + "\"api_version\":\"2010-04-01\",\"uri\":\"/x\"}");

        client().conferences().createParticipant(confSid,
                CreateParticipantRequest.builder()
                        .from("+14155550101")
                        .to("+14155550100")
                        .conferenceStatusCallback("https://example.com/conf")
                        .conferenceStatusCallbackMethod("POST")
                        .conferenceStatusCallbackEvent("start end")
                        .build());

        RecordedRequest r = recorded.removeFirst();
        assertThat(r.method).isEqualTo("POST");
        WireAssert.exactPairs(r.body, "From=%2B14155550101", "To=%2B14155550100",
                "ConferenceStatusCallback=https%3A%2F%2Fexample.com%2Fconf",
                "ConferenceStatusCallbackMethod=POST", "ConferenceStatusCallbackEvent=start+end");
    }

    @Test
    void listCallNotificationsSendsLogAndMessageDateFilters() {
        String callSid = "CA0123456789abcdef0123456789abcdef";
        handle("/2010-04-01/Accounts/ACtest/Calls/" + callSid + "/Notifications", 200,
                "{\"notifications\":[],\"page\":0,\"page_size\":50,\"total\":0}");

        client().calls().listNotifications(callSid,
                ListNotificationsParams.builder()
                        .log(1)
                        .messageDate("2026-05-01")
                        .messageDateLt("2026-05-02")
                        .messageDateGt("2026-04-30")
                        .build());

        RecordedRequest r = recorded.removeFirst();
        WireAssert.exactPairs(r.query,
                "Log=1",
                "MessageDate=2026-05-01",
                "MessageDate%3C=2026-05-02",
                "MessageDate%3E=2026-04-30");
    }

    @Test
    void listRecordingsTranslatesDateCreatedFilters() {
        handle("/2010-04-01/Accounts/ACtest/Recordings", 200,
                "{\"recordings\":[],\"page\":0,\"page_size\":50}");

        client().recordings().list(
                ListRecordingsParams.builder()
                        .dateCreated("2026-05-01")
                        .dateCreatedLt("2026-05-02")
                        .dateCreatedGt("2026-04-30")
                        .callSid("CA0123456789abcdef0123456789abcdef")
                        .build());

        RecordedRequest r = recorded.removeFirst();
        WireAssert.exactPairs(r.query,
                "DateCreated=2026-05-01",
                "DateCreated%3C=2026-05-02",
                "DateCreated%3E=2026-04-30",
                "CallSid=CA0123456789abcdef0123456789abcdef");
    }

    // --- Pagination: iterate() auto-pagination tests ---

    @Test
    void iterateCallsCollectsAllPagesAndStopsAtNullNextPageUri() {
        handleSequence(
                "/2010-04-01/Accounts/ACtest/Calls",
                new int[] {200, 200},
                new String[] {
                        "{\"calls\":["
                                + "{\"sid\":\"CA01\",\"account_sid\":\"ACtest\",\"api_version\":\"2010-04-01\","
                                + "\"status\":\"completed\",\"direction\":\"outbound-api\"},"
                                + "{\"sid\":\"CA02\",\"account_sid\":\"ACtest\",\"api_version\":\"2010-04-01\","
                                + "\"status\":\"completed\",\"direction\":\"outbound-api\"}"
                                + "],\"page\":0,\"page_size\":2,"
                                + "\"next_page_uri\":\"/2010-04-01/Accounts/ACtest/Calls.json?PageSize=2&Page=1&PageToken=PACA02\"}",
                        "{\"calls\":["
                                + "{\"sid\":\"CA03\",\"account_sid\":\"ACtest\",\"api_version\":\"2010-04-01\","
                                + "\"status\":\"completed\",\"direction\":\"outbound-api\"}"
                                + "],\"page\":1,\"page_size\":2,"
                                + "\"next_page_uri\":null}"
                });

        List<Call> all = toList(client().calls().iterate());

        assertThat(all).hasSize(3);
        assertThat(all.get(0).getSid()).isEqualTo("CA01");
        assertThat(all.get(1).getSid()).isEqualTo("CA02");
        assertThat(all.get(2).getSid()).isEqualTo("CA03");
        assertThat(recorded).hasSize(2);
        RecordedRequest first = recorded.removeFirst();
        assertThat(first.path).isEqualTo("/2010-04-01/Accounts/ACtest/Calls.json");
        assertThat(first.query).isEmpty();
        RecordedRequest second = recorded.removeFirst();
        assertThat(second.path).isEqualTo("/2010-04-01/Accounts/ACtest/Calls.json");
        assertThat(second.query).isEqualTo("PageSize=2&Page=1&PageToken=PACA02");
    }

    @Test
    void iterateConferencesCollectsAllPages() {
        handleSequence(
                "/2010-04-01/Accounts/ACtest/Conferences",
                new int[] {200, 200},
                new String[] {
                        "{\"conferences\":["
                                + "{\"sid\":\"CF01\",\"account_sid\":\"ACtest\",\"status\":\"in-progress\","
                                + "\"api_version\":\"2010-04-01\",\"uri\":\"/x\"},"
                                + "{\"sid\":\"CF02\",\"account_sid\":\"ACtest\",\"status\":\"completed\","
                                + "\"api_version\":\"2010-04-01\",\"uri\":\"/x\"}"
                                + "],\"page\":0,\"page_size\":2,"
                                + "\"next_page_uri\":\"/2010-04-01/Accounts/ACtest/Conferences.json?PageSize=2&Page=1&PageToken=PACF02\"}",
                        "{\"conferences\":["
                                + "{\"sid\":\"CF03\",\"account_sid\":\"ACtest\",\"status\":\"completed\","
                                + "\"api_version\":\"2010-04-01\",\"uri\":\"/x\"}"
                                + "],\"page\":1,\"page_size\":2,"
                                + "\"next_page_uri\":null}"
                });

        List<Conference> all = toList(client().conferences().iterate());

        assertThat(all).hasSize(3);
        assertThat(all.get(0).getSid()).isEqualTo("CF01");
        assertThat(all.get(1).getSid()).isEqualTo("CF02");
        assertThat(all.get(2).getSid()).isEqualTo("CF03");
        assertThat(recorded).hasSize(2);
        recorded.removeFirst();
        RecordedRequest second = recorded.removeFirst();
        assertThat(second.path).isEqualTo("/2010-04-01/Accounts/ACtest/Conferences.json");
        assertThat(second.query).isEqualTo("PageSize=2&Page=1&PageToken=PACF02");
    }

    @Test
    void iterateRecordingsCollectsAllPages() {
        handleSequence(
                "/2010-04-01/Accounts/ACtest/Recordings",
                new int[] {200, 200},
                new String[] {
                        "{\"recordings\":["
                                + "{\"sid\":\"RE01\",\"account_sid\":\"ACtest\","
                                + "\"status\":\"completed\",\"api_version\":\"2010-04-01\",\"uri\":\"/x\"},"
                                + "{\"sid\":\"RE02\",\"account_sid\":\"ACtest\","
                                + "\"status\":\"completed\",\"api_version\":\"2010-04-01\",\"uri\":\"/x\"}"
                                + "],\"page\":0,\"page_size\":2,"
                                + "\"next_page_uri\":\"/2010-04-01/Accounts/ACtest/Recordings.json?PageSize=2&Page=1&PageToken=PARE02\"}",
                        "{\"recordings\":["
                                + "{\"sid\":\"RE03\",\"account_sid\":\"ACtest\","
                                + "\"status\":\"completed\",\"api_version\":\"2010-04-01\",\"uri\":\"/x\"}"
                                + "],\"page\":1,\"page_size\":2,"
                                + "\"next_page_uri\":null}"
                });

        List<Recording> all = toList(client().recordings().iterate());

        assertThat(all).hasSize(3);
        assertThat(all.get(0).getSid()).isEqualTo("RE01");
        assertThat(all.get(1).getSid()).isEqualTo("RE02");
        assertThat(all.get(2).getSid()).isEqualTo("RE03");
        assertThat(recorded).hasSize(2);
        recorded.removeFirst();
        RecordedRequest second = recorded.removeFirst();
        assertThat(second.path).isEqualTo("/2010-04-01/Accounts/ACtest/Recordings.json");
        assertThat(second.query).isEqualTo("PageSize=2&Page=1&PageToken=PARE02");
    }

    @Test
    void iterateQueuesCollectsAllPages() {
        handleSequence(
                "/2010-04-01/Accounts/ACtest/Queues",
                new int[] {200, 200},
                new String[] {
                        "{\"queues\":["
                                + "{\"sid\":\"QU01\",\"account_sid\":\"ACtest\","
                                + "\"friendly_name\":\"support\",\"uri\":\"/x\"},"
                                + "{\"sid\":\"QU02\",\"account_sid\":\"ACtest\","
                                + "\"friendly_name\":\"sales\",\"uri\":\"/x\"}"
                                + "],\"page\":0,\"page_size\":2,"
                                + "\"next_page_uri\":\"/2010-04-01/Accounts/ACtest/Queues.json?PageSize=2&Page=1&PageToken=PAQU02\"}",
                        "{\"queues\":["
                                + "{\"sid\":\"QU03\",\"account_sid\":\"ACtest\","
                                + "\"friendly_name\":\"billing\",\"uri\":\"/x\"}"
                                + "],\"page\":1,\"page_size\":2,"
                                + "\"next_page_uri\":null}"
                });

        List<Queue> all = toList(client().queues().iterate());

        assertThat(all).hasSize(3);
        assertThat(all.get(0).getSid()).isEqualTo("QU01");
        assertThat(all.get(1).getSid()).isEqualTo("QU02");
        assertThat(all.get(2).getSid()).isEqualTo("QU03");
        assertThat(recorded).hasSize(2);
        recorded.removeFirst();
        RecordedRequest second = recorded.removeFirst();
        assertThat(second.path).isEqualTo("/2010-04-01/Accounts/ACtest/Queues.json");
        assertThat(second.query).isEqualTo("PageSize=2&Page=1&PageToken=PAQU02");
    }

    @Test
    void iterateCallsSinglePageReturnsImmediately() {
        handle("/2010-04-01/Accounts/ACtest/Calls", 200,
                "{\"calls\":["
                        + "{\"sid\":\"CA99\",\"account_sid\":\"ACtest\",\"api_version\":\"2010-04-01\","
                        + "\"status\":\"completed\",\"direction\":\"outbound-api\"}"
                        + "],\"page\":0,\"page_size\":50,"
                        + "\"next_page_uri\":null}");

        List<Call> all = toList(client().calls().iterate());

        assertThat(all).hasSize(1);
        assertThat(all.get(0).getSid()).isEqualTo("CA99");
        assertThat(recorded).hasSize(1);
    }

    // --- v0.7.0: Messages resource ---

    @Test
    void createMessageSendsFormBodyAndDecodesResponse() {
        handle("/2010-04-01/Accounts/ACtest/Messages", 201,
                "{\"sid\":\"SM0123456789abcdef0123456789abcdef\","
                        + "\"account_sid\":\"ACtest\","
                        + "\"api_version\":\"2010-04-01\","
                        + "\"to\":\"+14155550100\","
                        + "\"from\":\"+14155550101\","
                        + "\"body\":\"hi\","
                        + "\"status\":\"sent\","
                        + "\"num_segments\":\"1\","
                        + "\"num_media\":\"0\","
                        + "\"direction\":\"outbound-api\","
                        + "\"price\":null,\"price_unit\":null,"
                        + "\"error_code\":null,\"error_message\":null,"
                        + "\"messaging_service_sid\":null,"
                        + "\"date_created\":\"Wed, 20 May 2026 00:00:00 +0000\","
                        + "\"date_updated\":\"Wed, 20 May 2026 00:00:00 +0000\","
                        + "\"date_sent\":null,"
                        + "\"uri\":\"/x\"}");

        Message msg = client().messages().create(
                CreateMessageRequest.builder()
                        .to("+14155550100")
                        .from("+14155550101")
                        .body("hi")
                        .statusCallback("https://example.com/sms-status")
                        .sendAt(Instant.parse("2026-10-22T12:00:00Z"))
                        .scheduleType("fixed")
                        .build());

        assertThat(msg.getSid()).isEqualTo("SM0123456789abcdef0123456789abcdef");
        assertThat(msg.getStatus()).isEqualTo(MessageStatus.SENT);
        assertThat(msg.getNumSegments()).isEqualTo("1");
        assertThat(msg.getNumMedia()).isEqualTo("0");
        assertThat(msg.getErrorCode()).isNull();

        RecordedRequest r = recorded.removeFirst();
        assertThat(r.method).isEqualTo("POST");
        assertThat(r.path).isEqualTo("/2010-04-01/Accounts/ACtest/Messages.json");
        assertThat(r.contentType).isEqualTo("application/x-www-form-urlencoded");
        WireAssert.exactPairs(r.body, "To=%2B14155550100", "From=%2B14155550101", "Body=hi", "StatusCallback=https%3A%2F%2Fexample.com%2Fsms-status", "SendAt=2026-10-22T12%3A00%3A00Z", "ScheduleType=fixed");
    }

    @Test
    void fetchMessageGetsJsonPath() {
        String sid = "SM0123456789abcdef0123456789abcdef";
        handle("/2010-04-01/Accounts/ACtest/Messages/" + sid, 200,
                "{\"sid\":\"" + sid + "\","
                        + "\"account_sid\":\"ACtest\","
                        + "\"api_version\":\"2010-04-01\","
                        + "\"to\":\"+14155550100\",\"from\":\"+14155550101\","
                        + "\"body\":\"hi\",\"status\":\"delivered\","
                        + "\"num_segments\":\"1\",\"num_media\":\"0\","
                        + "\"direction\":\"outbound-api\","
                        + "\"date_created\":\"Wed, 20 May 2026 00:00:00 +0000\","
                        + "\"date_updated\":\"Wed, 20 May 2026 00:00:00 +0000\","
                        + "\"uri\":\"/x\"}");

        Message msg = client().messages().fetch(sid);
        assertThat(msg.getSid()).isEqualTo(sid);
        assertThat(msg.getStatus()).isEqualTo(MessageStatus.DELIVERED);

        RecordedRequest r = recorded.removeFirst();
        assertThat(r.method).isEqualTo("GET");
        assertThat(r.path).isEqualTo("/2010-04-01/Accounts/ACtest/Messages/" + sid + ".json");
    }

    @Test
    void listMessagesTranslatesDateSentFiltersAndPagination() {
        handle("/2010-04-01/Accounts/ACtest/Messages", 200,
                "{\"messages\":[],\"page\":0,\"page_size\":25,"
                        + "\"uri\":\"/2010-04-01/Accounts/ACtest/Messages.json\","
                        + "\"first_page_uri\":\"/x\",\"next_page_uri\":null}");

        MessageList list = client().messages().list(
                ListMessagesParams.builder()
                        .to("+14155550100")
                        .from("+14155550101")
                        .dateSent("2026-05-01")
                        .dateSentLt("2026-05-02")
                        .dateSentGt("2026-04-30")
                        .pageSize(25)
                        .pageToken("PTmsg")
                        .build());

        assertThat(list.getMessages()).isEmpty();

        RecordedRequest r = recorded.removeFirst();
        assertThat(r.method).isEqualTo("GET");
        assertThat(r.path).isEqualTo("/2010-04-01/Accounts/ACtest/Messages.json");
        WireAssert.exactPairs(r.query, "To=%2B14155550100", "From=%2B14155550101", "DateSent=2026-05-01", "DateSent%3C=2026-05-02", "DateSent%3E=2026-04-30", "PageSize=25", "PageToken=PTmsg");
    }

    @Test
    void updateMessageSendsBodyRedaction() {
        String sid = "SM0123456789abcdef0123456789abcdef";
        handle("/2010-04-01/Accounts/ACtest/Messages/" + sid, 200,
                "{\"sid\":\"" + sid + "\","
                        + "\"account_sid\":\"ACtest\","
                        + "\"api_version\":\"2010-04-01\","
                        + "\"to\":\"+14155550100\",\"from\":\"+14155550101\","
                        + "\"body\":\"\",\"status\":\"sent\","
                        + "\"num_segments\":\"1\",\"num_media\":\"0\","
                        + "\"direction\":\"outbound-api\","
                        + "\"date_created\":\"Wed, 20 May 2026 00:00:00 +0000\","
                        + "\"date_updated\":\"Wed, 20 May 2026 00:00:00 +0000\","
                        + "\"uri\":\"/x\"}");

        Message msg = client().messages().update(sid,
                UpdateMessageRequest.builder().body("").build());

        assertThat(msg.getBody()).isEqualTo("");

        RecordedRequest r = recorded.removeFirst();
        assertThat(r.method).isEqualTo("POST");
        assertThat(r.path).isEqualTo("/2010-04-01/Accounts/ACtest/Messages/" + sid + ".json");
        WireAssert.exactPairs(r.body, "Body=");
    }

    @Test
    void deleteMessageSendsDelete204() {
        String sid = "SM0123456789abcdef0123456789abcdef";
        handle("/2010-04-01/Accounts/ACtest/Messages/" + sid, 204, null);
        client().messages().delete(sid);

        RecordedRequest r = recorded.removeFirst();
        assertThat(r.method).isEqualTo("DELETE");
        assertThat(r.path).isEqualTo("/2010-04-01/Accounts/ACtest/Messages/" + sid + ".json");
    }

    // --- v0.7.0: Payments (call-scoped) ---

    @Test
    void startPaymentPostsFormBodyAndDecodesCallPayment() {
        String callSid = "CA0123456789abcdef0123456789abcdef";
        String paySid = "PY0123456789abcdef0123456789abcdef";
        handle("/2010-04-01/Accounts/ACtest/Calls/" + callSid + "/Payments", 201,
                "{\"sid\":\"" + paySid + "\","
                        + "\"account_sid\":\"ACtest\","
                        + "\"call_sid\":\"" + callSid + "\","
                        + "\"api_version\":\"2010-04-01\","
                        + "\"date_created\":\"Wed, 20 May 2026 00:00:00 +0000\","
                        + "\"date_updated\":\"Wed, 20 May 2026 00:00:00 +0000\","
                        + "\"uri\":\"/x\"}");

        CallPayment pay = client().calls().startPayment(callSid,
                StartPaymentRequest.builder()
                        .idempotencyKey("idem-1")
                        .statusCallback("https://example.com/pay-status")
                        .chargeAmount("19.99")
                        .currency("USD")
                        .description("subscription")
                        .paymentMethod(PaymentMethod.CREDIT_CARD)
                        .tokenType(PaymentTokenType.ONE_TIME)
                        .timeout(7)
                        .postalCode(true)
                        .securityCode(true)
                        .confirmation(false)
                        .validCardTypes("visa mastercard amex")
                        .build());

        assertThat(pay.getSid()).isEqualTo(paySid);
        assertThat(pay.getCallSid()).isEqualTo(callSid);

        RecordedRequest r = recorded.removeFirst();
        assertThat(r.method).isEqualTo("POST");
        assertThat(r.path)
                .isEqualTo("/2010-04-01/Accounts/ACtest/Calls/" + callSid + "/Payments.json");
        assertThat(r.contentType).isEqualTo("application/x-www-form-urlencoded");
        WireAssert.exactPairs(r.body, "IdempotencyKey=idem-1", "StatusCallback=https%3A%2F%2Fexample.com%2Fpay-status", "ChargeAmount=19.99", "Currency=USD", "Description=subscription", "PaymentMethod=credit-card", "TokenType=one-time", "Timeout=7", "PostalCode=true", "SecurityCode=true", "Confirmation=false", "ValidCardTypes=visa+mastercard+amex");
    }

    @Test
    void updatePaymentSendsCaptureAndStatusEnums() {
        String callSid = "CA0123456789abcdef0123456789abcdef";
        String paySid = "PY0123456789abcdef0123456789abcdef";
        handle("/2010-04-01/Accounts/ACtest/Calls/" + callSid + "/Payments/" + paySid, 202,
                "{\"sid\":\"" + paySid + "\","
                        + "\"account_sid\":\"ACtest\","
                        + "\"call_sid\":\"" + callSid + "\","
                        + "\"api_version\":\"2010-04-01\","
                        + "\"date_created\":\"Wed, 20 May 2026 00:00:00 +0000\","
                        + "\"date_updated\":\"Wed, 20 May 2026 00:00:00 +0000\","
                        + "\"uri\":\"/x\"}");

        CallPayment pay = client().calls().updatePayment(callSid, paySid,
                UpdatePaymentRequest.builder()
                        .capture(PaymentCapture.SECURITY_CODE)
                        .status(PaymentSessionStatus.COMPLETE)
                        .build());

        assertThat(pay.getSid()).isEqualTo(paySid);

        RecordedRequest r = recorded.removeFirst();
        assertThat(r.method).isEqualTo("POST");
        assertThat(r.path).isEqualTo(
                "/2010-04-01/Accounts/ACtest/Calls/" + callSid + "/Payments/" + paySid + ".json");
        WireAssert.exactPairs(r.body, "Capture=security-code", "Status=complete");
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
}
