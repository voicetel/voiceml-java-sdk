package com.voicetel.voiceml;

import com.sun.net.httpserver.HttpServer;
import com.voicetel.voiceml.models.CreateMessagingServiceRequest;
import com.voicetel.voiceml.models.MessagingService;
import com.voicetel.voiceml.models.MessagingServiceList;
import com.voicetel.voiceml.models.PricingCountriesList;
import com.voicetel.voiceml.models.PricingTrunkingCountry;
import com.voicetel.voiceml.models.PricingVoiceCountry;
import com.voicetel.voiceml.models.PricingVoiceNumber;
import com.voicetel.voiceml.models.PricingVoiceNumberV2;
import com.voicetel.voiceml.models.UpdateMessagingServiceRequest;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.nio.charset.StandardCharsets;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Wire-shape tests for the v0.9.2 surface: per-product host routing, Messaging Service (#16), and
 * Pricing v1/v2 (#18).
 *
 * <p>Messaging Service must ride the messaging host (that host is what disambiguates it from a
 * Conversation Service on the shared {@code /v1/Services} path); Pricing rides the default host.
 * Because the JDK cannot bind a real {@code *.voicetel.com} subdomain in a unit test, routing is
 * proven by pointing the default host and each product host at <em>separate</em> local servers and
 * asserting which server each request lands on. Host-label derivation is unit-tested directly
 * against {@link ClientOptions}.
 */
class V092Test {

    private static final String ACCOUNT = "AC" + "f".repeat(32);

    private MockServer defaultServer;
    private MockServer messagingServer;
    private MockServer conversationsServer;

    @BeforeEach
    void start() throws IOException {
        defaultServer = new MockServer();
        messagingServer = new MockServer();
        conversationsServer = new MockServer();
    }

    @AfterEach
    void stop() {
        if (defaultServer != null) defaultServer.stop();
        if (messagingServer != null) messagingServer.stop();
        if (conversationsServer != null) conversationsServer.stop();
    }

    // ---------------------------------------------------------------------
    // Host resolution (pure derivation, no network)
    // ---------------------------------------------------------------------

    private static ClientOptions opts(String baseUrl) {
        return ClientOptions.builder().accountSid(ACCOUNT).apiKey("k").baseUrl(baseUrl).build();
    }

    @Test
    void hostDerivationFromDefault() {
        ClientOptions o = opts("https://voiceml.voicetel.com");
        assertThat(o.getBaseUrl()).isEqualTo("https://voiceml.voicetel.com");
        assertThat(o.getMessagingBaseUrl()).isEqualTo("https://messaging.voicetel.com");
        assertThat(o.getConversationsBaseUrl()).isEqualTo("https://conversations.voicetel.com");
    }

    @Test
    void hostDerivationRegional() {
        ClientOptions o = opts("https://east-1.us.voiceml.voicetel.com");
        assertThat(o.getBaseUrl()).isEqualTo("https://east-1.us.voiceml.voicetel.com");
        assertThat(o.getMessagingBaseUrl()).isEqualTo("https://east-1.us.messaging.voicetel.com");
        assertThat(o.getConversationsBaseUrl())
                .isEqualTo("https://east-1.us.conversations.voicetel.com");
    }

    @Test
    void hostDerivationSelfHostedFallsBackToSingleHost() {
        // A custom host has no `voiceml` label to swap — every product stays on it.
        ClientOptions o = opts("https://pbx.acme.com");
        assertThat(o.getBaseUrl()).isEqualTo("https://pbx.acme.com");
        assertThat(o.getMessagingBaseUrl()).isEqualTo("https://pbx.acme.com");
        assertThat(o.getConversationsBaseUrl()).isEqualTo("https://pbx.acme.com");
    }

    @Test
    void hostDerivationExplicitOverridesWin() {
        ClientOptions o = ClientOptions.builder()
                .accountSid(ACCOUNT).apiKey("k")
                .baseUrl("https://pbx.acme.com")
                .messagingBaseUrl("https://msg.acme.com")
                .conversationsBaseUrl("https://conv.acme.com/")
                .build();
        assertThat(o.getBaseUrl()).isEqualTo("https://pbx.acme.com");
        assertThat(o.getMessagingBaseUrl()).isEqualTo("https://msg.acme.com");
        assertThat(o.getConversationsBaseUrl()).isEqualTo("https://conv.acme.com");
    }

    @Test
    void v092ResourcesWired() {
        VoicemlClient c = VoicemlClient.builder().accountSid(ACCOUNT).apiKey("k").build();
        assertThat(c.messagingV1()).isNotNull();
        assertThat(c.messagingV1().services()).isNotNull();
        assertThat(c.pricing().v1().voice().countries()).isNotNull();
        assertThat(c.pricing().v1().voice().numbers()).isNotNull();
        assertThat(c.pricing().v1().messaging().countries()).isNotNull();
        assertThat(c.pricing().v1().phoneNumbers().countries()).isNotNull();
        assertThat(c.pricing().v2().voice().countries()).isNotNull();
        assertThat(c.pricing().v2().voice().numbers()).isNotNull();
        assertThat(c.pricing().v2().trunking().countries()).isNotNull();
        assertThat(c.pricing().v2().trunking().numbers()).isNotNull();
    }

    // ---------------------------------------------------------------------
    // Messaging Service — CRUD on the messaging host
    // ---------------------------------------------------------------------

    private static String meta(String base) {
        return "{\"first_page_url\":\"" + base + "/v1/Services?Page=0\","
                + "\"next_page_url\":null,\"previous_page_url\":null,"
                + "\"url\":\"" + base + "/v1/Services\",\"page\":0,\"page_size\":50,"
                + "\"key\":\"services\"}";
    }

    private static String servicePayload(String sid, String base) {
        return "{\"sid\":\"" + sid + "\",\"account_sid\":\"" + ACCOUNT + "\","
                + "\"friendly_name\":\"alerts\",\"inbound_request_url\":\"https://example.com/in\","
                + "\"sticky_sender\":true,\"date_created\":\"2026-07-08T00:00:00Z\","
                + "\"date_updated\":\"2026-07-08T00:00:00Z\","
                + "\"url\":\"" + base + "/v1/Services/" + sid + "\"}";
    }

    private VoicemlClient twoHostClient() {
        return VoicemlClient.builder()
                .accountSid(ACCOUNT).apiKey("secret")
                .baseUrl(defaultServer.baseUrl())
                .messagingBaseUrl(messagingServer.baseUrl())
                .conversationsBaseUrl(conversationsServer.baseUrl())
                .maxRetries(0)
                .build();
    }

    @Test
    void messagingServiceCrudOnMessagingHost() {
        String sid = "MG" + "1".repeat(32);
        String base = messagingServer.baseUrl();
        messagingServer.route("POST", "/v1/Services", 201, servicePayload(sid, base));
        messagingServer.route("GET", "/v1/Services", 200,
                "{\"services\":[" + servicePayload(sid, base) + "],\"meta\":" + meta(base) + "}");
        messagingServer.route("GET", "/v1/Services/" + sid, 200, servicePayload(sid, base));
        messagingServer.route("POST", "/v1/Services/" + sid, 200, servicePayload(sid, base));
        messagingServer.route("DELETE", "/v1/Services/" + sid, 204, null);

        VoicemlClient c = twoHostClient();
        MessagingService created = c.messagingV1().services().create(
                CreateMessagingServiceRequest.builder()
                        .friendlyName("alerts")
                        .inboundRequestUrl("https://example.com/in")
                        .stickySender(true)
                        .build());
        MessagingServiceList listed = c.messagingV1().services().list(25);
        MessagingService fetched = c.messagingV1().services().fetch(sid);
        MessagingService updated = c.messagingV1().services().update(
                sid, UpdateMessagingServiceRequest.builder().friendlyName("renamed").build());
        c.messagingV1().services().delete(sid);

        assertThat(created.getSid()).isEqualTo(sid);
        assertThat(created.getSid()).startsWith("MG");
        assertThat(listed.getServices()).hasSize(1);
        assertThat(fetched.getSid()).isEqualTo(sid);
        assertThat(updated.getSid()).isEqualTo(sid);

        // Every request rode the messaging host; the default host saw nothing.
        assertThat(messagingServer.recorded).hasSize(5);
        assertThat(defaultServer.recorded).isEmpty();

        RecordedRequest createReq = messagingServer.recorded.removeFirst();
        assertThat(createReq.method).isEqualTo("POST");
        assertThat(createReq.rawPath).isEqualTo("/v1/Services");
        assertThat(createReq.body)
                .contains("FriendlyName=alerts")
                .contains("InboundRequestUrl=https%3A%2F%2Fexample.com%2Fin")
                .contains("StickySender=true");

        RecordedRequest listReq = messagingServer.recorded.removeFirst();
        assertThat(listReq.method).isEqualTo("GET");
        assertThat(listReq.rawQuery).contains("PageSize=25");

        messagingServer.recorded.removeFirst(); // fetch

        RecordedRequest updateReq = messagingServer.recorded.removeFirst();
        assertThat(updateReq.method).isEqualTo("POST");
        assertThat(updateReq.rawPath).isEqualTo("/v1/Services/" + sid);
        assertThat(updateReq.body).isEqualTo("FriendlyName=renamed");

        RecordedRequest deleteReq = messagingServer.recorded.removeFirst();
        assertThat(deleteReq.method).isEqualTo("DELETE");
    }

    @Test
    void messagingServiceHostOverride() {
        // Default host is a self-hosted pbx name (single-host fallback), but an explicit
        // messaging override sends the Messaging Service group to its own server.
        String base = messagingServer.baseUrl();
        messagingServer.route("GET", "/v1/Services", 200,
                "{\"services\":[],\"meta\":" + meta(base) + "}");

        VoicemlClient c = VoicemlClient.builder()
                .accountSid(ACCOUNT).apiKey("secret")
                .baseUrl(defaultServer.baseUrl())
                .messagingBaseUrl(messagingServer.baseUrl())
                .maxRetries(0)
                .build();
        c.messagingV1().services().list();

        assertThat(messagingServer.recorded).hasSize(1);
        assertThat(defaultServer.recorded).isEmpty();
    }

    // ---------------------------------------------------------------------
    // Conversations — routed at the conversations host
    // ---------------------------------------------------------------------

    @Test
    void conversationsRoutesToConversationsHost() {
        String base = conversationsServer.baseUrl();
        conversationsServer.route("GET", "/v1/Services", 200,
                "{\"services\":[],\"meta\":" + meta(base) + "}");

        VoicemlClient c = twoHostClient();
        c.conversationsV1().services().list();

        assertThat(conversationsServer.recorded).hasSize(1);
        assertThat(defaultServer.recorded).isEmpty();
        assertThat(messagingServer.recorded).isEmpty();
    }

    // ---------------------------------------------------------------------
    // Pricing v1/v2 — read-only on the default host
    // ---------------------------------------------------------------------

    private VoicemlClient defaultHostClient() {
        return VoicemlClient.builder()
                .accountSid(ACCOUNT).apiKey("secret")
                .baseUrl(defaultServer.baseUrl())
                .maxRetries(0)
                .build();
    }

    @Test
    void pricingV1VoiceCountriesAndNumber() {
        defaultServer.route("GET", "/v1/Voice/Countries", 200,
                "{\"countries\":[{\"country\":\"United States\",\"iso_country\":\"US\","
                        + "\"url\":\"/v1/Voice/Countries/US\"}],"
                        + "\"meta\":{\"page\":0,\"page_size\":50}}");
        defaultServer.route("GET", "/v1/Voice/Countries/US", 200,
                "{\"country\":\"United States\",\"iso_country\":\"US\","
                        + "\"outbound_prefix_prices\":[{\"prefixes\":[\"1\"],\"base_price\":\"0.013\","
                        + "\"current_price\":\"0.013\",\"friendly_name\":\"United States & Canada\"}],"
                        + "\"inbound_call_prices\":[{\"base_price\":\"0.0085\","
                        + "\"current_price\":\"0.0085\",\"number_type\":\"local\"}],"
                        + "\"price_unit\":\"USD\",\"url\":\"/v1/Voice/Countries/US\"}");
        // Number path is percent-encoded on the wire; server decodes it to +.
        defaultServer.route("GET", "/v1/Voice/Numbers/+18005551234", 200,
                "{\"number\":\"+18005551234\",\"country\":\"United States\",\"iso_country\":\"US\","
                        + "\"outbound_call_price\":{\"base_price\":\"0.013\",\"current_price\":\"0.013\"},"
                        + "\"inbound_call_price\":{\"base_price\":\"0.0085\",\"current_price\":\"0.0085\","
                        + "\"number_type\":\"toll free\"},\"price_unit\":\"USD\","
                        + "\"url\":\"/v1/Voice/Numbers/+18005551234\"}");

        VoicemlClient c = defaultHostClient();
        PricingCountriesList listed = c.pricing().v1().voice().countries().list();
        PricingVoiceCountry country = c.pricing().v1().voice().countries().fetch("US");
        PricingVoiceNumber num = c.pricing().v1().voice().numbers().fetch("+18005551234");

        assertThat(listed.getCountries().get(0).getIsoCountry()).isEqualTo("US");
        assertThat(country.getOutboundPrefixPrices().get(0).getPrefixes()).containsExactly("1");
        assertThat(num.getInboundCallPrice().getNumberType()).isEqualTo("toll free");

        // Everything on the default host; the encoded number path is on the wire.
        assertThat(messagingServer.recorded).isEmpty();
        RecordedRequest numReq = defaultServer.recorded.removeLast();
        assertThat(numReq.rawPath).isEqualTo("/v1/Voice/Numbers/%2B18005551234");
    }

    @Test
    void pricingV2VoiceNumberWithOrigination() {
        defaultServer.route("GET", "/v2/Voice/Numbers/+18005551234", 200,
                "{\"destination_number\":\"+18005551234\","
                        + "\"origination_number\":\"+15551112222\","
                        + "\"country\":\"United States\",\"iso_country\":\"US\","
                        + "\"outbound_call_prices\":[{\"origination_prefixes\":[\"1\"],"
                        + "\"base_price\":\"0.013\",\"current_price\":\"0.013\"}],"
                        + "\"inbound_call_price\":{\"base_price\":\"0.0085\","
                        + "\"current_price\":\"0.0085\",\"number_type\":\"local\"},"
                        + "\"price_unit\":\"USD\",\"url\":\"/v2/Voice/Numbers/+18005551234\"}");

        VoicemlClient c = defaultHostClient();
        PricingVoiceNumberV2 got = c.pricing().v2().voice().numbers()
                .fetch("+18005551234", "+15551112222");

        assertThat(got.getOriginationNumber()).isEqualTo("+15551112222");
        RecordedRequest r = defaultServer.recorded.removeFirst();
        assertThat(r.rawPath).isEqualTo("/v2/Voice/Numbers/%2B18005551234");
        assertThat(r.rawQuery).contains("OriginationNumber=%2B15551112222");
    }

    @Test
    void pricingV2TrunkingCountry() {
        defaultServer.route("GET", "/v2/Trunking/Countries/US", 200,
                "{\"country\":\"United States\",\"iso_country\":\"US\","
                        + "\"terminating_prefix_prices\":[{\"origination_prefixes\":[\"1\"],"
                        + "\"destination_prefixes\":[\"1\"],\"base_price\":\"0.013\","
                        + "\"current_price\":\"0.013\",\"friendly_name\":\"US\"}],"
                        + "\"originating_call_prices\":[{\"base_price\":\"0.0085\","
                        + "\"current_price\":\"0.0085\",\"number_type\":\"local\"}],"
                        + "\"price_unit\":\"USD\",\"url\":\"/v2/Trunking/Countries/US\"}");

        VoicemlClient c = defaultHostClient();
        PricingTrunkingCountry got = c.pricing().v2().trunking().countries().fetch("US");
        assertThat(got.getTerminatingPrefixPrices().get(0).getFriendlyName()).isEqualTo("US");
    }

    // ---------------------------------------------------------------------
    // Local mock server harness
    // ---------------------------------------------------------------------

    /** A local HTTP server that records requests and answers registered {@code METHOD path} routes. */
    private static final class MockServer {
        final HttpServer server;
        final int port;
        final Deque<RecordedRequest> recorded = new ArrayDeque<>();
        final Map<String, Object[]> routes = new HashMap<>();

        MockServer() throws IOException {
            server = HttpServer.create(new InetSocketAddress("127.0.0.1", 0), 0);
            port = server.getAddress().getPort();
            server.createContext("/", exchange -> {
                byte[] reqBody = exchange.getRequestBody().readAllBytes();
                String method = exchange.getRequestMethod().toUpperCase();
                String decodedPath = exchange.getRequestURI().getPath();
                String rawPath = exchange.getRequestURI().getRawPath();
                String rawQuery = exchange.getRequestURI().getRawQuery();
                recorded.addLast(new RecordedRequest(method, rawPath,
                        rawQuery == null ? "" : rawQuery,
                        new String(reqBody, StandardCharsets.UTF_8)));
                Object[] resp = routes.get(method + " " + decodedPath);
                int status = resp != null ? (Integer) resp[0] : 200;
                String body = resp != null ? (String) resp[1] : "{}";
                byte[] payload = body == null ? new byte[0] : body.getBytes(StandardCharsets.UTF_8);
                exchange.getResponseHeaders().set("Content-Type", "application/json");
                exchange.sendResponseHeaders(status, payload.length == 0 ? -1 : payload.length);
                try (OutputStream os = exchange.getResponseBody()) {
                    os.write(payload);
                }
            });
            server.start();
        }

        String baseUrl() {
            return "http://127.0.0.1:" + port;
        }

        void route(String method, String decodedPath, int status, String body) {
            routes.put(method + " " + decodedPath, new Object[] {status, body});
        }

        void stop() {
            server.stop(0);
        }
    }

    /** Plain POJO so we don't need records on Java 11. */
    private static final class RecordedRequest {
        final String method;
        final String rawPath;
        final String rawQuery;
        final String body;

        RecordedRequest(String method, String rawPath, String rawQuery, String body) {
            this.method = method;
            this.rawPath = rawPath;
            this.rawQuery = rawQuery;
            this.body = body;
        }
    }
}
