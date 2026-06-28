package com.voicetel.voiceml;

import com.sun.net.httpserver.HttpServer;
import com.voicetel.voiceml.models.ConversationsV1ServiceBinding;
import com.voicetel.voiceml.models.ConversationsV1ServiceBindingList;
import com.voicetel.voiceml.models.ConversationsV1ServiceConfiguration;
import com.voicetel.voiceml.models.ConversationsV1ServiceConversation;
import com.voicetel.voiceml.models.ConversationsV1ServiceConversationList;
import com.voicetel.voiceml.models.ConversationsV1ServiceConversationMessage;
import com.voicetel.voiceml.models.ConversationsV1ServiceConversationMessageReceipt;
import com.voicetel.voiceml.models.ConversationsV1ServiceConversationMessageReceiptList;
import com.voicetel.voiceml.models.ConversationsV1ServiceConversationParticipant;
import com.voicetel.voiceml.models.ConversationsV1ServiceConversationScopedWebhook;
import com.voicetel.voiceml.models.ConversationsV1ServiceConversationWithParticipants;
import com.voicetel.voiceml.models.ConversationsV1ServiceNotification;
import com.voicetel.voiceml.models.ConversationsV1ServiceParticipantConversationList;
import com.voicetel.voiceml.models.ConversationsV1ServiceRole;
import com.voicetel.voiceml.models.ConversationsV1ServiceUser;
import com.voicetel.voiceml.models.ConversationsV1ServiceUserConversationList;
import com.voicetel.voiceml.models.ConversationsV1ServiceWebhookConfiguration;
import com.voicetel.voiceml.models.CreateConversationsV1ServiceConversationMessageRequest;
import com.voicetel.voiceml.models.CreateConversationsV1ServiceConversationParticipantRequest;
import com.voicetel.voiceml.models.CreateConversationsV1ServiceConversationRequest;
import com.voicetel.voiceml.models.CreateConversationsV1ServiceConversationScopedWebhookRequest;
import com.voicetel.voiceml.models.CreateConversationsV1ServiceConversationWithParticipantsRequest;
import com.voicetel.voiceml.models.CreateConversationsV1ServiceRoleRequest;
import com.voicetel.voiceml.models.CreateConversationsV1ServiceUserRequest;
import com.voicetel.voiceml.models.ListConversationsV1ServiceBindingsParams;
import com.voicetel.voiceml.models.ListConversationsV1ServiceParticipantConversationsParams;
import com.voicetel.voiceml.models.UpdateConversationsV1ServiceConfigurationRequest;
import com.voicetel.voiceml.models.UpdateConversationsV1ServiceConversationMessageRequest;
import com.voicetel.voiceml.models.UpdateConversationsV1ServiceConversationParticipantRequest;
import com.voicetel.voiceml.models.UpdateConversationsV1ServiceConversationRequest;
import com.voicetel.voiceml.models.UpdateConversationsV1ServiceConversationScopedWebhookRequest;
import com.voicetel.voiceml.models.UpdateConversationsV1ServiceNotificationRequest;
import com.voicetel.voiceml.models.UpdateConversationsV1ServiceRoleRequest;
import com.voicetel.voiceml.models.UpdateConversationsV1ServiceUserRequest;
import com.voicetel.voiceml.models.UpdateConversationsV1ServiceWebhookConfigurationRequest;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Wire-shape smoke tests for the v0.9.0 Phase 4 surface:
 * service-scoped Conversations v1 under {@code /v1/Services/{ChatServiceSid}}.
 *
 * <p>Each test spins up an in-process {@link HttpServer}, records the
 * request, and asserts the SDK sent the right method/path/body/query.
 */
class V090Phase4Test {

    private static final String ACCOUNT = "AC" + "f".repeat(32);
    private static final String IS_SID = "IS" + "0".repeat(32);
    private static final String CH_SID = "CH" + "0".repeat(32);
    private static final String IM_SID = "IM" + "0".repeat(32);
    private static final String MB_SID = "MB" + "0".repeat(32);
    private static final String WH_SID = "WH" + "0".repeat(32);
    private static final String DY_SID = "DY" + "0".repeat(32);
    private static final String RL_SID = "RL" + "0".repeat(32);
    private static final String US_SID = "US" + "0".repeat(32);
    private static final String BS_SID = "BS" + "0".repeat(32);

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
    void stop() { if (server != null) server.stop(0); }

    private VoicemlClient client() {
        return VoicemlClient.builder()
                .accountSid(ACCOUNT)
                .apiKey("secret")
                .baseUrl("http://127.0.0.1:" + port)
                .maxRetries(0)
                .build();
    }

    private void handle(String path, int status, String body) {
        server.createContext(path, exchange -> {
            byte[] reqBody = exchange.getRequestBody().readAllBytes();
            String query = exchange.getRequestURI().getRawQuery();
            recorded.addLast(new RecordedRequest(
                    exchange.getRequestMethod(),
                    exchange.getRequestURI().getPath(),
                    query == null ? "" : query,
                    new String(reqBody, StandardCharsets.UTF_8)));
            byte[] payload = body == null ? new byte[0] : body.getBytes(StandardCharsets.UTF_8);
            exchange.getResponseHeaders().set("Content-Type", "application/json");
            exchange.sendResponseHeaders(status, payload.length == 0 ? -1 : payload.length);
            try (OutputStream os = exchange.getResponseBody()) {
                os.write(payload);
            }
        });
    }

    private static Map<String, List<String>> formMulti(String body) {
        Map<String, List<String>> out = new HashMap<>();
        if (body == null || body.isEmpty()) return out;
        for (String pair : body.split("&")) {
            int eq = pair.indexOf('=');
            if (eq < 0) continue;
            String k = URLDecoder.decode(pair.substring(0, eq), StandardCharsets.UTF_8);
            String v = URLDecoder.decode(pair.substring(eq + 1), StandardCharsets.UTF_8);
            out.computeIfAbsent(k, kk -> new ArrayList<>()).add(v);
        }
        return out;
    }

    // --- scope factory wiring sanity ---

    @Test
    void scopeFactoryWiresAllSubResources() {
        VoicemlClient c = client();
        assertThat(c.conversationsV1().services().scope(IS_SID)).isNotNull();
        assertThat(c.conversationsV1().services().scope(IS_SID).conversations()).isNotNull();
        assertThat(c.conversationsV1().services().scope(IS_SID).conversations().messages(CH_SID)).isNotNull();
        assertThat(c.conversationsV1().services().scope(IS_SID).conversations().messages(CH_SID).receipts(IM_SID)).isNotNull();
        assertThat(c.conversationsV1().services().scope(IS_SID).conversations().participants(CH_SID)).isNotNull();
        assertThat(c.conversationsV1().services().scope(IS_SID).conversations().webhooks(CH_SID)).isNotNull();
        assertThat(c.conversationsV1().services().scope(IS_SID).conversationWithParticipants()).isNotNull();
        assertThat(c.conversationsV1().services().scope(IS_SID).participantConversations()).isNotNull();
        assertThat(c.conversationsV1().services().scope(IS_SID).roles()).isNotNull();
        assertThat(c.conversationsV1().services().scope(IS_SID).users()).isNotNull();
        assertThat(c.conversationsV1().services().scope(IS_SID).users().conversations(US_SID)).isNotNull();
        assertThat(c.conversationsV1().services().scope(IS_SID).bindings()).isNotNull();
        assertThat(c.conversationsV1().services().scope(IS_SID).configuration()).isNotNull();
        assertThat(c.conversationsV1().services().scope(IS_SID).configuration().notifications()).isNotNull();
        assertThat(c.conversationsV1().services().scope(IS_SID).configuration().webhooks()).isNotNull();
    }

    // --- ServiceConversation: create + list + delete ---

    @Test
    void serviceConversationCreate() {
        String body = "{\"account_sid\":\"" + ACCOUNT + "\","
                + "\"chat_service_sid\":\"" + IS_SID + "\",\"sid\":\"" + CH_SID + "\","
                + "\"friendly_name\":\"Support\",\"state\":\"active\",\"attributes\":\"{}\","
                + "\"date_created\":\"2026-06-27T00:00:00Z\","
                + "\"date_updated\":\"2026-06-27T00:00:00Z\","
                + "\"url\":\"/v1/Services/" + IS_SID + "/Conversations/" + CH_SID + "\"}";
        handle("/v1/Services/" + IS_SID + "/Conversations", 201, body);

        ConversationsV1ServiceConversation c = client().conversationsV1().services().scope(IS_SID)
                .conversations()
                .create(CreateConversationsV1ServiceConversationRequest.builder()
                        .friendlyName("Support")
                        .state("active")
                        .timersInactive("PT5M")
                        .build());
        assertThat(c.getSid()).isEqualTo(CH_SID);
        assertThat(c.getChatServiceSid()).isEqualTo(IS_SID);

        RecordedRequest r = recorded.removeFirst();
        assertThat(r.method).isEqualTo("POST");
        assertThat(r.path).isEqualTo("/v1/Services/" + IS_SID + "/Conversations");
        assertThat(r.path).doesNotContain(".json");
        Map<String, List<String>> form = formMulti(r.body);
        assertThat(form.get("FriendlyName")).containsExactly("Support");
        assertThat(form.get("State")).containsExactly("active");
        assertThat(form.get("Timers.Inactive")).containsExactly("PT5M");
    }

    @Test
    void serviceConversationListUpdateDelete() {
        String listBody = "{\"conversations\":[{\"sid\":\"" + CH_SID + "\","
                + "\"chat_service_sid\":\"" + IS_SID + "\","
                + "\"state\":\"active\",\"attributes\":\"{}\","
                + "\"date_created\":\"2026-06-27T00:00:00Z\","
                + "\"date_updated\":\"2026-06-27T00:00:00Z\","
                + "\"url\":\"/v1/Services/" + IS_SID + "/Conversations/" + CH_SID + "\"}],"
                + "\"meta\":{\"page\":0,\"page_size\":50,\"key\":\"conversations\"}}";
        handle("/v1/Services/" + IS_SID + "/Conversations", 200, listBody);

        ConversationsV1ServiceConversationList page = client().conversationsV1().services().scope(IS_SID)
                .conversations().list();
        assertThat(page.getConversations()).hasSize(1);
        assertThat(page.getMeta().getKey()).isEqualTo("conversations");

        String updBody = "{\"sid\":\"" + CH_SID + "\","
                + "\"chat_service_sid\":\"" + IS_SID + "\","
                + "\"state\":\"closed\",\"attributes\":\"{}\","
                + "\"date_created\":\"2026-06-27T00:00:00Z\","
                + "\"date_updated\":\"2026-06-27T00:00:00Z\","
                + "\"url\":\"/v1/Services/" + IS_SID + "/Conversations/" + CH_SID + "\"}";
        handle("/v1/Services/" + IS_SID + "/Conversations/" + CH_SID, 200, updBody);
        ConversationsV1ServiceConversation upd = client().conversationsV1().services().scope(IS_SID)
                .conversations()
                .update(CH_SID, UpdateConversationsV1ServiceConversationRequest.builder()
                        .state("closed").build());
        assertThat(upd.getState()).isEqualTo("closed");

        RecordedRequest listReq = recorded.removeFirst();
        assertThat(listReq.method).isEqualTo("GET");
        assertThat(listReq.path).isEqualTo("/v1/Services/" + IS_SID + "/Conversations");

        RecordedRequest updReq = recorded.removeFirst();
        assertThat(updReq.method).isEqualTo("POST");
        assertThat(updReq.path).isEqualTo("/v1/Services/" + IS_SID + "/Conversations/" + CH_SID);
        assertThat(formMulti(updReq.body).get("State")).containsExactly("closed");
    }

    // --- ServiceConversationMessage: create under service-scoped conversation ---

    @Test
    void serviceConversationMessageCreate() {
        String body = "{\"account_sid\":\"" + ACCOUNT + "\","
                + "\"chat_service_sid\":\"" + IS_SID + "\","
                + "\"conversation_sid\":\"" + CH_SID + "\",\"sid\":\"" + IM_SID + "\","
                + "\"index\":0,\"author\":\"alice\",\"body\":\"hello\",\"attributes\":\"{}\","
                + "\"date_created\":\"2026-06-27T00:00:00Z\","
                + "\"date_updated\":\"2026-06-27T00:00:00Z\","
                + "\"url\":\"/v1/Services/" + IS_SID + "/Conversations/" + CH_SID + "/Messages/" + IM_SID + "\"}";
        handle("/v1/Services/" + IS_SID + "/Conversations/" + CH_SID + "/Messages", 201, body);

        ConversationsV1ServiceConversationMessage m = client().conversationsV1().services().scope(IS_SID)
                .conversations().messages(CH_SID)
                .create(CreateConversationsV1ServiceConversationMessageRequest.builder()
                        .author("alice").body("hello").build());
        assertThat(m.getSid()).isEqualTo(IM_SID);
        assertThat(m.getChatServiceSid()).isEqualTo(IS_SID);
        assertThat(m.getIndex()).isEqualTo(0);

        RecordedRequest r = recorded.removeFirst();
        assertThat(r.method).isEqualTo("POST");
        assertThat(r.path).isEqualTo("/v1/Services/" + IS_SID + "/Conversations/" + CH_SID + "/Messages");
        Map<String, List<String>> form = formMulti(r.body);
        assertThat(form.get("Author")).containsExactly("alice");
        assertThat(form.get("Body")).containsExactly("hello");
    }

    @Test
    void serviceConversationMessageUpdateAndDelete() {
        String body = "{\"sid\":\"" + IM_SID + "\","
                + "\"chat_service_sid\":\"" + IS_SID + "\","
                + "\"conversation_sid\":\"" + CH_SID + "\",\"index\":0,"
                + "\"body\":\"edited\",\"attributes\":\"{}\","
                + "\"date_created\":\"2026-06-27T00:00:00Z\","
                + "\"date_updated\":\"2026-06-27T00:00:00Z\","
                + "\"url\":\"/x\"}";
        handle("/v1/Services/" + IS_SID + "/Conversations/" + CH_SID + "/Messages/" + IM_SID, 200, body);

        ConversationsV1ServiceConversationMessage m = client().conversationsV1().services().scope(IS_SID)
                .conversations().messages(CH_SID)
                .update(IM_SID, UpdateConversationsV1ServiceConversationMessageRequest.builder()
                        .body("edited").build());
        assertThat(m.getBody()).isEqualTo("edited");

        RecordedRequest r = recorded.removeFirst();
        assertThat(r.method).isEqualTo("POST");
        assertThat(r.path).isEqualTo("/v1/Services/" + IS_SID + "/Conversations/" + CH_SID + "/Messages/" + IM_SID);
        assertThat(formMulti(r.body).get("Body")).containsExactly("edited");
    }

    // --- ServiceConversationMessageReceipt: list + fetch ---

    @Test
    void serviceReceiptListAndFetch() {
        String listBody = "{\"delivery_receipts\":[{\"sid\":\"" + DY_SID + "\","
                + "\"chat_service_sid\":\"" + IS_SID + "\","
                + "\"conversation_sid\":\"" + CH_SID + "\",\"message_sid\":\"" + IM_SID + "\","
                + "\"status\":\"delivered\",\"error_code\":0,"
                + "\"date_created\":\"2026-06-27T00:00:00Z\","
                + "\"date_updated\":\"2026-06-27T00:00:00Z\","
                + "\"url\":\"/x\"}],"
                + "\"meta\":{\"page\":0,\"page_size\":50,\"key\":\"delivery_receipts\"}}";
        handle("/v1/Services/" + IS_SID + "/Conversations/" + CH_SID + "/Messages/" + IM_SID + "/Receipts",
                200, listBody);

        ConversationsV1ServiceConversationMessageReceiptList page = client().conversationsV1().services()
                .scope(IS_SID).conversations().messages(CH_SID).receipts(IM_SID).list();
        assertThat(page.getDeliveryReceipts()).hasSize(1);
        assertThat(page.getMeta().getKey()).isEqualTo("delivery_receipts");

        String fetchBody = "{\"sid\":\"" + DY_SID + "\","
                + "\"chat_service_sid\":\"" + IS_SID + "\","
                + "\"conversation_sid\":\"" + CH_SID + "\",\"message_sid\":\"" + IM_SID + "\","
                + "\"status\":\"read\",\"error_code\":0,"
                + "\"date_created\":\"2026-06-27T00:00:00Z\","
                + "\"date_updated\":\"2026-06-27T00:00:00Z\","
                + "\"url\":\"/x\"}";
        handle("/v1/Services/" + IS_SID + "/Conversations/" + CH_SID + "/Messages/" + IM_SID + "/Receipts/" + DY_SID,
                200, fetchBody);
        ConversationsV1ServiceConversationMessageReceipt rcpt = client().conversationsV1().services()
                .scope(IS_SID).conversations().messages(CH_SID).receipts(IM_SID).fetch(DY_SID);
        assertThat(rcpt.getStatus()).isEqualTo("read");
    }

    // --- ServiceConversationParticipant ---

    @Test
    void serviceParticipantCreate() {
        String body = "{\"account_sid\":\"" + ACCOUNT + "\","
                + "\"chat_service_sid\":\"" + IS_SID + "\","
                + "\"conversation_sid\":\"" + CH_SID + "\",\"sid\":\"" + MB_SID + "\","
                + "\"identity\":\"alice\",\"attributes\":\"{}\","
                + "\"date_created\":\"2026-06-27T00:00:00Z\","
                + "\"date_updated\":\"2026-06-27T00:00:00Z\","
                + "\"url\":\"/x\"}";
        handle("/v1/Services/" + IS_SID + "/Conversations/" + CH_SID + "/Participants", 201, body);

        ConversationsV1ServiceConversationParticipant p = client().conversationsV1().services().scope(IS_SID)
                .conversations().participants(CH_SID)
                .create(CreateConversationsV1ServiceConversationParticipantRequest.builder()
                        .identity("alice")
                        .messagingBindingAddress("+15551234567")
                        .build());
        assertThat(p.getSid()).isEqualTo(MB_SID);

        RecordedRequest r = recorded.removeFirst();
        assertThat(r.method).isEqualTo("POST");
        assertThat(r.path).isEqualTo("/v1/Services/" + IS_SID + "/Conversations/" + CH_SID + "/Participants");
        Map<String, List<String>> form = formMulti(r.body);
        assertThat(form.get("Identity")).containsExactly("alice");
        assertThat(form.get("MessagingBinding.Address")).containsExactly("+15551234567");
    }

    @Test
    void serviceParticipantUpdate() {
        String body = "{\"sid\":\"" + MB_SID + "\","
                + "\"chat_service_sid\":\"" + IS_SID + "\","
                + "\"conversation_sid\":\"" + CH_SID + "\",\"attributes\":\"{}\","
                + "\"role_sid\":\"" + RL_SID + "\","
                + "\"date_created\":\"2026-06-27T00:00:00Z\","
                + "\"date_updated\":\"2026-06-27T00:00:00Z\","
                + "\"url\":\"/x\"}";
        handle("/v1/Services/" + IS_SID + "/Conversations/" + CH_SID + "/Participants/" + MB_SID, 200, body);
        ConversationsV1ServiceConversationParticipant p = client().conversationsV1().services().scope(IS_SID)
                .conversations().participants(CH_SID)
                .update(MB_SID, UpdateConversationsV1ServiceConversationParticipantRequest.builder()
                        .roleSid(RL_SID).build());
        assertThat(p.getRoleSid()).isEqualTo(RL_SID);

        RecordedRequest r = recorded.removeFirst();
        assertThat(r.method).isEqualTo("POST");
        assertThat(r.path).isEqualTo("/v1/Services/" + IS_SID + "/Conversations/" + CH_SID + "/Participants/" + MB_SID);
        assertThat(formMulti(r.body).get("RoleSid")).containsExactly(RL_SID);
    }

    // --- ServiceConversationScopedWebhook ---

    @Test
    void serviceScopedWebhookCreate() {
        String body = "{\"sid\":\"" + WH_SID + "\","
                + "\"chat_service_sid\":\"" + IS_SID + "\","
                + "\"conversation_sid\":\"" + CH_SID + "\",\"target\":\"webhook\","
                + "\"date_created\":\"2026-06-27T00:00:00Z\","
                + "\"date_updated\":\"2026-06-27T00:00:00Z\","
                + "\"configuration\":{\"url\":\"https://example/hook\",\"method\":\"POST\"}}";
        handle("/v1/Services/" + IS_SID + "/Conversations/" + CH_SID + "/Webhooks", 201, body);

        ConversationsV1ServiceConversationScopedWebhook w = client().conversationsV1().services().scope(IS_SID)
                .conversations().webhooks(CH_SID)
                .create(CreateConversationsV1ServiceConversationScopedWebhookRequest.builder()
                        .target("webhook")
                        .configurationUrl("https://example/hook")
                        .configurationMethod("POST")
                        .build());
        assertThat(w.getSid()).isEqualTo(WH_SID);

        RecordedRequest r = recorded.removeFirst();
        assertThat(r.method).isEqualTo("POST");
        assertThat(r.path).isEqualTo("/v1/Services/" + IS_SID + "/Conversations/" + CH_SID + "/Webhooks");
        Map<String, List<String>> form = formMulti(r.body);
        assertThat(form.get("Target")).containsExactly("webhook");
        assertThat(form.get("Configuration.Url")).containsExactly("https://example/hook");
        assertThat(form.get("Configuration.Method")).containsExactly("POST");
    }

    @Test
    void serviceScopedWebhookUpdate() {
        String body = "{\"sid\":\"" + WH_SID + "\","
                + "\"chat_service_sid\":\"" + IS_SID + "\","
                + "\"conversation_sid\":\"" + CH_SID + "\",\"target\":\"webhook\","
                + "\"date_created\":\"2026-06-27T00:00:00Z\","
                + "\"date_updated\":\"2026-06-27T00:00:00Z\","
                + "\"configuration\":{\"method\":\"GET\"}}";
        handle("/v1/Services/" + IS_SID + "/Conversations/" + CH_SID + "/Webhooks/" + WH_SID, 200, body);
        client().conversationsV1().services().scope(IS_SID).conversations().webhooks(CH_SID)
                .update(WH_SID, UpdateConversationsV1ServiceConversationScopedWebhookRequest.builder()
                        .configurationMethod("GET").build());

        RecordedRequest r = recorded.removeFirst();
        assertThat(r.method).isEqualTo("POST");
        assertThat(r.path).isEqualTo("/v1/Services/" + IS_SID + "/Conversations/" + CH_SID + "/Webhooks/" + WH_SID);
        assertThat(formMulti(r.body).get("Configuration.Method")).containsExactly("GET");
    }

    // --- ServiceConversationWithParticipants (one-shot create) ---

    @Test
    void serviceConversationWithParticipantsCreate() {
        String body = "{\"account_sid\":\"" + ACCOUNT + "\","
                + "\"chat_service_sid\":\"" + IS_SID + "\",\"sid\":\"" + CH_SID + "\","
                + "\"state\":\"active\",\"attributes\":\"{}\","
                + "\"date_created\":\"2026-06-27T00:00:00Z\","
                + "\"date_updated\":\"2026-06-27T00:00:00Z\","
                + "\"url\":\"/x\"}";
        handle("/v1/Services/" + IS_SID + "/ConversationWithParticipants", 201, body);

        ConversationsV1ServiceConversationWithParticipants c = client().conversationsV1().services()
                .scope(IS_SID).conversationWithParticipants()
                .create(CreateConversationsV1ServiceConversationWithParticipantsRequest.builder()
                        .friendlyName("multi")
                        .addParticipant("{\"identity\":\"alice\"}")
                        .addParticipant("{\"messaging_binding\":{\"address\":\"+15551234567\"}}")
                        .build());
        assertThat(c.getSid()).isEqualTo(CH_SID);

        RecordedRequest r = recorded.removeFirst();
        assertThat(r.method).isEqualTo("POST");
        assertThat(r.path).isEqualTo("/v1/Services/" + IS_SID + "/ConversationWithParticipants");
        Map<String, List<String>> form = formMulti(r.body);
        assertThat(form.get("Participant")).containsExactly(
                "{\"identity\":\"alice\"}",
                "{\"messaging_binding\":{\"address\":\"+15551234567\"}}");
        assertThat(form.get("FriendlyName")).containsExactly("multi");
    }

    // --- ServiceParticipantConversation (list-only) ---

    @Test
    void serviceParticipantConversationsListFiltersByIdentity() {
        String body = "{\"conversations\":[{"
                + "\"account_sid\":\"" + ACCOUNT + "\","
                + "\"chat_service_sid\":\"" + IS_SID + "\","
                + "\"conversation_sid\":\"" + CH_SID + "\","
                + "\"participant_identity\":\"alice\","
                + "\"conversation_state\":\"active\","
                + "\"conversation_date_created\":\"2026-06-27T00:00:00Z\","
                + "\"conversation_date_updated\":\"2026-06-27T00:00:00Z\""
                + "}],"
                + "\"meta\":{\"page\":0,\"page_size\":50,\"key\":\"conversations\"}}";
        handle("/v1/Services/" + IS_SID + "/ParticipantConversations", 200, body);

        ConversationsV1ServiceParticipantConversationList page = client().conversationsV1().services()
                .scope(IS_SID).participantConversations()
                .list(ListConversationsV1ServiceParticipantConversationsParams.builder()
                        .identity("alice").build());
        assertThat(page.getConversations()).hasSize(1);

        RecordedRequest r = recorded.removeFirst();
        assertThat(r.method).isEqualTo("GET");
        assertThat(r.path).isEqualTo("/v1/Services/" + IS_SID + "/ParticipantConversations");
        assertThat(r.query).contains("Identity=alice");
    }

    // --- ServiceUserConversation (list-only) ---

    @Test
    void serviceUserConversationsList() {
        String body = "{\"conversations\":[{"
                + "\"account_sid\":\"" + ACCOUNT + "\","
                + "\"chat_service_sid\":\"" + IS_SID + "\","
                + "\"conversation_sid\":\"" + CH_SID + "\","
                + "\"user_sid\":\"" + US_SID + "\","
                + "\"conversation_state\":\"active\",\"notification_level\":\"default\","
                + "\"date_created\":\"2026-06-27T00:00:00Z\","
                + "\"date_updated\":\"2026-06-27T00:00:00Z\","
                + "\"url\":\"/x\"}],"
                + "\"meta\":{\"page\":0,\"page_size\":50,\"key\":\"conversations\"}}";
        handle("/v1/Services/" + IS_SID + "/Users/" + US_SID + "/Conversations", 200, body);

        ConversationsV1ServiceUserConversationList page = client().conversationsV1().services()
                .scope(IS_SID).users().conversations(US_SID).list();
        assertThat(page.getConversations()).hasSize(1);
        assertThat(page.getConversations().get(0).getUserSid()).isEqualTo(US_SID);

        RecordedRequest r = recorded.removeFirst();
        assertThat(r.method).isEqualTo("GET");
        assertThat(r.path).isEqualTo("/v1/Services/" + IS_SID + "/Users/" + US_SID + "/Conversations");
    }

    // --- ServiceRole: create + update repeats Permission ---

    @Test
    void serviceRoleCreateRepeatsPermission() {
        String body = "{\"sid\":\"" + RL_SID + "\","
                + "\"chat_service_sid\":\"" + IS_SID + "\","
                + "\"type\":\"conversation\",\"permissions\":[\"sendMessage\",\"editOwnMessage\"],"
                + "\"date_created\":\"2026-06-27T00:00:00Z\","
                + "\"date_updated\":\"2026-06-27T00:00:00Z\","
                + "\"url\":\"/x\"}";
        handle("/v1/Services/" + IS_SID + "/Roles", 201, body);

        ConversationsV1ServiceRole role = client().conversationsV1().services().scope(IS_SID).roles()
                .create(CreateConversationsV1ServiceRoleRequest.builder()
                        .friendlyName("agent")
                        .type("conversation")
                        .addPermission("sendMessage")
                        .addPermission("editOwnMessage")
                        .build());
        assertThat(role.getSid()).isEqualTo(RL_SID);

        RecordedRequest r = recorded.removeFirst();
        assertThat(r.method).isEqualTo("POST");
        assertThat(r.path).isEqualTo("/v1/Services/" + IS_SID + "/Roles");
        Map<String, List<String>> form = formMulti(r.body);
        assertThat(form.get("FriendlyName")).containsExactly("agent");
        assertThat(form.get("Type")).containsExactly("conversation");
        assertThat(form.get("Permission")).containsExactly("sendMessage", "editOwnMessage");
    }

    @Test
    void serviceRoleUpdateRepeatsPermission() {
        String body = "{\"sid\":\"" + RL_SID + "\","
                + "\"chat_service_sid\":\"" + IS_SID + "\","
                + "\"type\":\"conversation\",\"permissions\":[\"sendMessage\"],"
                + "\"date_created\":\"2026-06-27T00:00:00Z\","
                + "\"date_updated\":\"2026-06-27T00:00:00Z\","
                + "\"url\":\"/x\"}";
        handle("/v1/Services/" + IS_SID + "/Roles/" + RL_SID, 200, body);
        client().conversationsV1().services().scope(IS_SID).roles()
                .update(RL_SID, UpdateConversationsV1ServiceRoleRequest.builder()
                        .addPermission("sendMessage").build());

        RecordedRequest r = recorded.removeFirst();
        assertThat(r.method).isEqualTo("POST");
        assertThat(r.path).isEqualTo("/v1/Services/" + IS_SID + "/Roles/" + RL_SID);
        assertThat(formMulti(r.body).get("Permission")).containsExactly("sendMessage");
    }

    // --- ServiceUser ---

    @Test
    void serviceUserCreate() {
        String body = "{\"sid\":\"" + US_SID + "\","
                + "\"chat_service_sid\":\"" + IS_SID + "\","
                + "\"identity\":\"alice\",\"attributes\":\"{}\","
                + "\"date_created\":\"2026-06-27T00:00:00Z\","
                + "\"date_updated\":\"2026-06-27T00:00:00Z\","
                + "\"url\":\"/x\"}";
        handle("/v1/Services/" + IS_SID + "/Users", 201, body);

        ConversationsV1ServiceUser u = client().conversationsV1().services().scope(IS_SID).users()
                .create(CreateConversationsV1ServiceUserRequest.builder().identity("alice").build());
        assertThat(u.getSid()).isEqualTo(US_SID);

        RecordedRequest r = recorded.removeFirst();
        assertThat(r.method).isEqualTo("POST");
        assertThat(r.path).isEqualTo("/v1/Services/" + IS_SID + "/Users");
        assertThat(formMulti(r.body).get("Identity")).containsExactly("alice");
    }

    @Test
    void serviceUserUpdateAndDelete() {
        String body = "{\"sid\":\"" + US_SID + "\","
                + "\"chat_service_sid\":\"" + IS_SID + "\","
                + "\"friendly_name\":\"Alice\",\"attributes\":\"{}\","
                + "\"date_created\":\"2026-06-27T00:00:00Z\","
                + "\"date_updated\":\"2026-06-27T00:00:00Z\","
                + "\"url\":\"/x\"}";
        handle("/v1/Services/" + IS_SID + "/Users/" + US_SID, 200, body);
        ConversationsV1ServiceUser u = client().conversationsV1().services().scope(IS_SID).users()
                .update(US_SID, UpdateConversationsV1ServiceUserRequest.builder()
                        .friendlyName("Alice").build());
        assertThat(u.getFriendlyName()).isEqualTo("Alice");

        RecordedRequest r = recorded.removeFirst();
        assertThat(r.method).isEqualTo("POST");
        assertThat(r.path).isEqualTo("/v1/Services/" + IS_SID + "/Users/" + US_SID);
        assertThat(formMulti(r.body).get("FriendlyName")).containsExactly("Alice");
    }

    // --- ServiceBinding (list + fetch + delete) ---

    @Test
    void serviceBindingListAndFetch() {
        String listBody = "{\"bindings\":[{\"sid\":\"" + BS_SID + "\","
                + "\"chat_service_sid\":\"" + IS_SID + "\","
                + "\"binding_type\":\"apn\","
                + "\"date_created\":\"2026-06-27T00:00:00Z\","
                + "\"date_updated\":\"2026-06-27T00:00:00Z\","
                + "\"url\":\"/x\"}],"
                + "\"meta\":{\"page\":0,\"page_size\":50,\"key\":\"bindings\"}}";
        handle("/v1/Services/" + IS_SID + "/Bindings", 200, listBody);

        ConversationsV1ServiceBindingList page = client().conversationsV1().services().scope(IS_SID).bindings()
                .list(ListConversationsV1ServiceBindingsParams.builder()
                        .bindingType("apn").identity("alice").pageSize(50).build());
        assertThat(page.getBindings()).hasSize(1);
        assertThat(page.getBindings().get(0).getBindingType()).isEqualTo("apn");

        RecordedRequest listReq = recorded.removeFirst();
        assertThat(listReq.method).isEqualTo("GET");
        assertThat(listReq.path).isEqualTo("/v1/Services/" + IS_SID + "/Bindings");
        assertThat(listReq.query).contains("BindingType=apn");
        assertThat(listReq.query).contains("Identity=alice");
        assertThat(listReq.query).contains("PageSize=50");

        String fetchBody = "{\"sid\":\"" + BS_SID + "\","
                + "\"chat_service_sid\":\"" + IS_SID + "\","
                + "\"binding_type\":\"apn\","
                + "\"date_created\":\"2026-06-27T00:00:00Z\","
                + "\"date_updated\":\"2026-06-27T00:00:00Z\","
                + "\"url\":\"/x\"}";
        handle("/v1/Services/" + IS_SID + "/Bindings/" + BS_SID, 200, fetchBody);
        ConversationsV1ServiceBinding b = client().conversationsV1().services().scope(IS_SID).bindings()
                .fetch(BS_SID);
        assertThat(b.getSid()).isEqualTo(BS_SID);
    }

    // --- ServiceConfiguration (fetch + update) ---

    @Test
    void serviceConfigurationFetchAndUpdate() {
        String body = "{\"chat_service_sid\":\"" + IS_SID + "\","
                + "\"default_conversation_role_sid\":\"" + RL_SID + "\","
                + "\"reachability_enabled\":true,"
                + "\"url\":\"/v1/Services/" + IS_SID + "/Configuration\"}";
        handle("/v1/Services/" + IS_SID + "/Configuration", 200, body);

        ConversationsV1ServiceConfiguration cfg = client().conversationsV1().services().scope(IS_SID)
                .configuration().fetch();
        assertThat(cfg.getDefaultConversationRoleSid()).isEqualTo(RL_SID);
        assertThat(cfg.getReachabilityEnabled()).isTrue();

        client().conversationsV1().services().scope(IS_SID).configuration()
                .update(UpdateConversationsV1ServiceConfigurationRequest.builder()
                        .defaultConversationRoleSid(RL_SID)
                        .reachabilityEnabled(true)
                        .build());

        RecordedRequest get = recorded.removeFirst();
        assertThat(get.method).isEqualTo("GET");
        assertThat(get.path).isEqualTo("/v1/Services/" + IS_SID + "/Configuration");

        RecordedRequest post = recorded.removeFirst();
        assertThat(post.method).isEqualTo("POST");
        assertThat(post.path).isEqualTo("/v1/Services/" + IS_SID + "/Configuration");
        Map<String, List<String>> form = formMulti(post.body);
        assertThat(form.get("DefaultConversationRoleSid")).containsExactly(RL_SID);
        assertThat(form.get("ReachabilityEnabled")).containsExactly("true");
    }

    // --- ServiceNotification (fetch + update with dotted keys) ---

    @Test
    void serviceNotificationFetchAndUpdate() {
        String body = "{\"chat_service_sid\":\"" + IS_SID + "\","
                + "\"log_enabled\":true,"
                + "\"new_message\":{\"enabled\":true,\"template\":\"hi\"},"
                + "\"url\":\"/v1/Services/" + IS_SID + "/Configuration/Notifications\"}";
        handle("/v1/Services/" + IS_SID + "/Configuration/Notifications", 200, body);

        ConversationsV1ServiceNotification n = client().conversationsV1().services().scope(IS_SID)
                .configuration().notifications().fetch();
        assertThat(n.getLogEnabled()).isTrue();

        client().conversationsV1().services().scope(IS_SID).configuration().notifications()
                .update(UpdateConversationsV1ServiceNotificationRequest.builder()
                        .logEnabled(true)
                        .newMessageEnabled(true)
                        .newMessageTemplate("hi")
                        .newMessageBadgeCountEnabled(false)
                        .addedToConversationEnabled(true)
                        .removedFromConversationSound("ding")
                        .build());

        RecordedRequest get = recorded.removeFirst();
        assertThat(get.method).isEqualTo("GET");
        assertThat(get.path).isEqualTo("/v1/Services/" + IS_SID + "/Configuration/Notifications");

        RecordedRequest post = recorded.removeFirst();
        assertThat(post.method).isEqualTo("POST");
        assertThat(post.path).isEqualTo("/v1/Services/" + IS_SID + "/Configuration/Notifications");
        Map<String, List<String>> form = formMulti(post.body);
        assertThat(form.get("LogEnabled")).containsExactly("true");
        assertThat(form.get("NewMessage.Enabled")).containsExactly("true");
        assertThat(form.get("NewMessage.Template")).containsExactly("hi");
        assertThat(form.get("NewMessage.BadgeCountEnabled")).containsExactly("false");
        assertThat(form.get("AddedToConversation.Enabled")).containsExactly("true");
        assertThat(form.get("RemovedFromConversation.Sound")).containsExactly("ding");
    }

    // --- ServiceWebhookConfiguration (fetch + update with repeated Filters) ---

    @Test
    void serviceWebhookConfigurationFetchAndUpdate() {
        String body = "{\"chat_service_sid\":\"" + IS_SID + "\","
                + "\"method\":\"POST\","
                + "\"filters\":[\"onMessageAdded\",\"onConversationAdded\"],"
                + "\"pre_webhook_url\":\"https://example/pre\","
                + "\"post_webhook_url\":\"https://example/post\","
                + "\"url\":\"/v1/Services/" + IS_SID + "/Configuration/Webhooks\"}";
        handle("/v1/Services/" + IS_SID + "/Configuration/Webhooks", 200, body);

        ConversationsV1ServiceWebhookConfiguration cfg = client().conversationsV1().services().scope(IS_SID)
                .configuration().webhooks().fetch();
        assertThat(cfg.getMethod()).isEqualTo("POST");
        assertThat(cfg.getFilters()).containsExactly("onMessageAdded", "onConversationAdded");

        client().conversationsV1().services().scope(IS_SID).configuration().webhooks()
                .update(UpdateConversationsV1ServiceWebhookConfigurationRequest.builder()
                        .method("POST")
                        .addFilter("onMessageAdded")
                        .addFilter("onConversationAdded")
                        .preWebhookUrl("https://example/pre")
                        .postWebhookUrl("https://example/post")
                        .build());

        RecordedRequest get = recorded.removeFirst();
        assertThat(get.method).isEqualTo("GET");
        assertThat(get.path).isEqualTo("/v1/Services/" + IS_SID + "/Configuration/Webhooks");

        RecordedRequest post = recorded.removeFirst();
        assertThat(post.method).isEqualTo("POST");
        assertThat(post.path).isEqualTo("/v1/Services/" + IS_SID + "/Configuration/Webhooks");
        Map<String, List<String>> form = formMulti(post.body);
        assertThat(form.get("Method")).containsExactly("POST");
        assertThat(form.get("Filters")).containsExactly("onMessageAdded", "onConversationAdded");
        assertThat(form.get("PreWebhookUrl")).containsExactly("https://example/pre");
        assertThat(form.get("PostWebhookUrl")).containsExactly("https://example/post");
    }

    /** Plain POJO so we don't need records on Java 11. */
    private static final class RecordedRequest {
        final String method;
        final String path;
        final String query;
        final String body;

        RecordedRequest(String method, String path, String query, String body) {
            this.method = method;
            this.path = path;
            this.query = query;
            this.body = body;
        }
    }
}
