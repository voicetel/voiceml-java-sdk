package com.voicetel.voiceml;

import com.sun.net.httpserver.HttpServer;
import com.voicetel.voiceml.models.UpdateRoutesV2SipDomainRequest;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

/** Tests for the Routes V2 (Inbound Processing Region) API. */
class RoutesV2Test {

    private static final String ACCOUNT = "AC" + "f".repeat(32);
    private static final String DOMAIN_NAME = "ingress.example.com";
    private static final String QQ_SID = "QQ" + "0".repeat(32);

    private HttpServer server;
    private int port;
    private Deque<String> recordedMethods;
    private Deque<String> recordedPaths;
    private Deque<String> recordedBodies;

    @BeforeEach
    void start() throws IOException {
        server = HttpServer.create(new InetSocketAddress("127.0.0.1", 0), 0);
        port = server.getAddress().getPort();
        recordedMethods = new ArrayDeque<>();
        recordedPaths = new ArrayDeque<>();
        recordedBodies = new ArrayDeque<>();
        server.start();
    }

    @AfterEach
    void stop() { if (server != null) server.stop(0); }

    private VoicemlClient client() {
        return VoicemlClient.builder()
                .accountSid(ACCOUNT)
                .apiKey("secret")
                .baseUrl("http://127.0.0.1:" + port)
                .maxRetries(0)
                .build();
    }

    private void handle(String path, String body) {
        server.createContext(path, exchange -> {
            byte[] reqBody = exchange.getRequestBody().readAllBytes();
            recordedMethods.addLast(exchange.getRequestMethod());
            recordedPaths.addLast(exchange.getRequestURI().getPath());
            recordedBodies.addLast(new String(reqBody, StandardCharsets.UTF_8));
            byte[] payload = body.getBytes(StandardCharsets.UTF_8);
            exchange.getResponseHeaders().set("Content-Type", "application/json");
            exchange.sendResponseHeaders(200, payload.length);
            try (OutputStream os = exchange.getResponseBody()) { os.write(payload); }
        });
    }

    private static Map<String, String> form(String body) {
        Map<String, String> out = new HashMap<>();
        if (body == null || body.isEmpty()) return out;
        for (String pair : body.split("&")) {
            int eq = pair.indexOf('=');
            if (eq < 0) continue;
            out.put(URLDecoder.decode(pair.substring(0, eq), StandardCharsets.UTF_8),
                    URLDecoder.decode(pair.substring(eq + 1), StandardCharsets.UTF_8));
        }
        return out;
    }

    private static String responseBody() {
        return "{\"sid\":\"" + QQ_SID + "\",\"sip_domain\":\"" + DOMAIN_NAME + "\","
                + "\"account_sid\":\"" + ACCOUNT + "\",\"friendly_name\":\"ingress\","
                + "\"voice_region\":\"us1\",\"url\":\"https://example/v2/SipDomains/" + DOMAIN_NAME + "\","
                + "\"date_created\":\"2026-06-17T20:00:00Z\","
                + "\"date_updated\":\"2026-06-17T20:00:00Z\"}";
    }

    @Test
    void routesV2WiredOnClient() {
        assertThat(client().routesV2()).isNotNull();
        assertThat(client().routesV2().sipDomains()).isNotNull();
    }

    @Test
    void fetchUsesV2NamespaceNotAccountPrefix() {
        handle("/v2/SipDomains/" + DOMAIN_NAME, responseBody());
        var rv = client().routesV2().sipDomains().fetch(DOMAIN_NAME);
        assertThat(rv.getSid()).isEqualTo(QQ_SID);
        assertThat(rv.getVoiceRegion()).isEqualTo("us1");
        assertThat(recordedPaths.peekFirst()).isEqualTo("/v2/SipDomains/" + DOMAIN_NAME);
        assertThat(recordedPaths.peekFirst()).doesNotContain(ACCOUNT);
    }

    @Test
    void updateSendsBothFields() {
        handle("/v2/SipDomains/" + DOMAIN_NAME, responseBody());
        client().routesV2().sipDomains().update(DOMAIN_NAME,
                UpdateRoutesV2SipDomainRequest.builder()
                        .voiceRegion("ie1")
                        .friendlyName("renamed")
                        .build());
        var body = form(recordedBodies.peekFirst());
        assertThat(body).containsEntry("VoiceRegion", "ie1")
                .containsEntry("FriendlyName", "renamed");
        assertThat(recordedMethods.peekFirst()).isEqualTo("POST");
    }

    @Test
    void updateSendsPartialBody() {
        handle("/v2/SipDomains/" + DOMAIN_NAME, responseBody());
        client().routesV2().sipDomains().update(DOMAIN_NAME,
                UpdateRoutesV2SipDomainRequest.builder().voiceRegion("us1").build());
        var body = form(recordedBodies.peekFirst());
        assertThat(body).containsEntry("VoiceRegion", "us1").hasSize(1);
    }
}
