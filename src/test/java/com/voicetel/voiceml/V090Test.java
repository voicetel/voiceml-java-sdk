package com.voicetel.voiceml;

import com.sun.net.httpserver.HttpServer;
import com.voicetel.voiceml.models.ConversationsV1ConfigAddress;
import com.voicetel.voiceml.models.ConversationsV1Configuration;
import com.voicetel.voiceml.models.ConversationsV1ConfigurationWebhook;
import com.voicetel.voiceml.models.ConversationsV1Conversation;
import com.voicetel.voiceml.models.ConversationsV1ConversationList;
import com.voicetel.voiceml.models.ConversationsV1ConversationMessage;
import com.voicetel.voiceml.models.ConversationsV1ConversationMessageReceipt;
import com.voicetel.voiceml.models.ConversationsV1ConversationMessageReceiptList;
import com.voicetel.voiceml.models.ConversationsV1ConversationParticipant;
import com.voicetel.voiceml.models.ConversationsV1ConversationScopedWebhook;
import com.voicetel.voiceml.models.ConversationsV1ConversationWithParticipants;
import com.voicetel.voiceml.models.ConversationsV1Credential;
import com.voicetel.voiceml.models.ConversationsV1ParticipantConversationList;
import com.voicetel.voiceml.models.ConversationsV1Role;
import com.voicetel.voiceml.models.ConversationsV1RoleList;
import com.voicetel.voiceml.models.ConversationsV1Service;
import com.voicetel.voiceml.models.ConversationsV1User;
import com.voicetel.voiceml.models.ConversationsV1UserConversation;
import com.voicetel.voiceml.models.CreateConversationsV1ConfigAddressRequest;
import com.voicetel.voiceml.models.CreateConversationsV1ConversationMessageRequest;
import com.voicetel.voiceml.models.CreateConversationsV1ConversationParticipantRequest;
import com.voicetel.voiceml.models.CreateConversationsV1ConversationRequest;
import com.voicetel.voiceml.models.CreateConversationsV1ConversationScopedWebhookRequest;
import com.voicetel.voiceml.models.CreateConversationsV1ConversationWithParticipantsRequest;
import com.voicetel.voiceml.models.CreateConversationsV1CredentialRequest;
import com.voicetel.voiceml.models.CreateConversationsV1RoleRequest;
import com.voicetel.voiceml.models.CreateConversationsV1ServiceRequest;
import com.voicetel.voiceml.models.CreateConversationsV1UserRequest;
import com.voicetel.voiceml.models.CreateVoiceV1ByocTrunkRequest;
import com.voicetel.voiceml.models.CreateVoiceV1ConnectionPolicyRequest;
import com.voicetel.voiceml.models.CreateVoiceV1ConnectionPolicyTargetRequest;
import com.voicetel.voiceml.models.CreateVoiceV1IpRecordRequest;
import com.voicetel.voiceml.models.CreateVoiceV1SourceIpMappingRequest;
import com.voicetel.voiceml.models.ListConversationsV1ParticipantConversationsParams;
import com.voicetel.voiceml.models.ListPageParams;
import com.voicetel.voiceml.models.RoutesV2PhoneNumber;
import com.voicetel.voiceml.models.UpdateConversationsV1ConfigurationRequest;
import com.voicetel.voiceml.models.UpdateConversationsV1ConfigurationWebhookRequest;
import com.voicetel.voiceml.models.UpdateConversationsV1UserConversationRequest;
import com.voicetel.voiceml.models.UpdateRoutesV2PhoneNumberRequest;
import com.voicetel.voiceml.models.UpdateVoiceV1SettingsRequest;
import com.voicetel.voiceml.models.VoiceV1ByocTrunk;
import com.voicetel.voiceml.models.VoiceV1ConnectionPolicy;
import com.voicetel.voiceml.models.VoiceV1ConnectionPolicyTarget;
import com.voicetel.voiceml.models.VoiceV1DialingPermissionsSettings;
import com.voicetel.voiceml.models.VoiceV1IpRecord;
import com.voicetel.voiceml.models.VoiceV1IpRecordList;
import com.voicetel.voiceml.models.VoiceV1SourceIpMapping;
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
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Wire-shape smoke tests for the v0.9.0 surface: Routes V2 PhoneNumbers,
 * Voice V1 (IpRecords / SourceIpMappings / ByocTrunks / ConnectionPolicies +
 * Targets / Settings) and Conversations V1 (Conversations + nested Messages
 * / Participants / Webhooks / Receipts; Roles, Users + UserConversations,
 * Credentials, Configuration + Webhooks + Addresses, ParticipantConversations,
 * ConversationWithParticipants, Services).
 *
 * <p>Each test spins up an in-process {@link HttpServer}, records the request,
 * and asserts the SDK sent the right method/path/body/query.
 */
class V090Test {

    private static final String ACCOUNT = "AC" + "f".repeat(32);
    private static final String CH_SID = "CH" + "0".repeat(32);
    private static final String IM_SID = "IM" + "0".repeat(32);
    private static final String MB_SID = "MB" + "0".repeat(32);
    private static final String WH_SID = "WH" + "0".repeat(32);
    private static final String DY_SID = "DY" + "0".repeat(32);
    private static final String RL_SID = "RL" + "0".repeat(32);
    private static final String US_SID = "US" + "0".repeat(32);
    private static final String CR_SID = "CR" + "0".repeat(32);
    private static final String IG_SID = "IG" + "0".repeat(32);
    private static final String IS_SID = "IS" + "0".repeat(32);
    private static final String IL_SID = "IL" + "0".repeat(32);
    private static final String SD_SID = "SD" + "0".repeat(32);
    private static final String IB_SID = "IB" + "0".repeat(32);
    private static final String BY_SID = "BY" + "0".repeat(32);
    private static final String NY_SID = "NY" + "0".repeat(32);
    private static final String NE_SID = "NE" + "0".repeat(32);
    private static final String QQ_SID = "QQ" + "0".repeat(32);

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
            out.computeIfAbsent(k, kk -> new java.util.ArrayList<>()).add(v);
        }
        return out;
    }

    // --- wiring sanity ---

    @Test
    void newSurfacesWiredOnClient() {
        VoicemlClient c = client();
        assertThat(c.routesV2()).isNotNull();
        assertThat(c.routesV2().phoneNumbers()).isNotNull();
        assertThat(c.routesV2().sipDomains()).isNotNull();
        assertThat(c.voiceV1()).isNotNull();
        assertThat(c.voiceV1().ipRecords()).isNotNull();
        assertThat(c.voiceV1().sourceIpMappings()).isNotNull();
        assertThat(c.voiceV1().byocTrunks()).isNotNull();
        assertThat(c.voiceV1().connectionPolicies()).isNotNull();
        assertThat(c.voiceV1().connectionPolicies().targets(NY_SID)).isNotNull();
        assertThat(c.voiceV1().settings()).isNotNull();
        assertThat(c.conversationsV1()).isNotNull();
        assertThat(c.conversationsV1().conversations()).isNotNull();
        assertThat(c.conversationsV1().conversations().messages(CH_SID)).isNotNull();
        assertThat(c.conversationsV1().conversations().messages(CH_SID).receipts(IM_SID)).isNotNull();
        assertThat(c.conversationsV1().conversations().participants(CH_SID)).isNotNull();
        assertThat(c.conversationsV1().conversations().webhooks(CH_SID)).isNotNull();
        assertThat(c.conversationsV1().roles()).isNotNull();
        assertThat(c.conversationsV1().users()).isNotNull();
        assertThat(c.conversationsV1().users().conversations(US_SID)).isNotNull();
        assertThat(c.conversationsV1().credentials()).isNotNull();
        assertThat(c.conversationsV1().configuration()).isNotNull();
        assertThat(c.conversationsV1().configuration().webhooks()).isNotNull();
        assertThat(c.conversationsV1().configuration().addresses()).isNotNull();
        assertThat(c.conversationsV1().participantConversations()).isNotNull();
        assertThat(c.conversationsV1().conversationWithParticipants()).isNotNull();
        assertThat(c.conversationsV1().services()).isNotNull();
    }

    // --- Transport.withJsonSuffix: /v1/ must NOT get .json appended ---

    @Test
    void v1PathsDoNotGetJsonSuffix() {
        assertThat(Transport.withJsonSuffix("/v1/Conversations")).isEqualTo("/v1/Conversations");
        assertThat(Transport.withJsonSuffix("/v1/Conversations/" + CH_SID))
                .isEqualTo("/v1/Conversations/" + CH_SID);
        assertThat(Transport.withJsonSuffix("/v2/PhoneNumbers/+18005551234"))
                .isEqualTo("/v2/PhoneNumbers/+18005551234");
    }

    // --- Routes V2 PhoneNumbers ---

    @Test
    void routesV2PhoneNumbersFetchAndUpdate() {
        String pn = "+18005551234";
        String body = "{\"sid\":\"" + QQ_SID + "\",\"phone_number\":\"" + pn + "\","
                + "\"account_sid\":\"" + ACCOUNT + "\",\"friendly_name\":\"main\","
                + "\"voice_region\":\"us1\",\"url\":\"https://example/v2/PhoneNumbers/" + pn + "\","
                + "\"date_created\":\"2026-06-27T00:00:00Z\","
                + "\"date_updated\":\"2026-06-27T00:00:00Z\"}";

        handle("/v2/PhoneNumbers/" + pn, 200, body);

        RoutesV2PhoneNumber fetched = client().routesV2().phoneNumbers().fetch(pn);
        assertThat(fetched.getSid()).isEqualTo(QQ_SID);
        assertThat(fetched.getPhoneNumber()).isEqualTo(pn);
        assertThat(fetched.getVoiceRegion()).isEqualTo("us1");

        client().routesV2().phoneNumbers().update(pn,
                UpdateRoutesV2PhoneNumberRequest.builder()
                        .voiceRegion("ie1")
                        .friendlyName("renamed")
                        .build());

        RecordedRequest fetchReq = recorded.removeFirst();
        assertThat(fetchReq.method).isEqualTo("GET");
        assertThat(fetchReq.path).isEqualTo("/v2/PhoneNumbers/" + pn);
        assertThat(fetchReq.path).doesNotContain(".json");

        RecordedRequest updReq = recorded.removeFirst();
        assertThat(updReq.method).isEqualTo("POST");
        assertThat(updReq.path).isEqualTo("/v2/PhoneNumbers/" + pn);
        Map<String, List<String>> form = formMulti(updReq.body);
        assertThat(form.get("VoiceRegion")).containsExactly("ie1");
        assertThat(form.get("FriendlyName")).containsExactly("renamed");
    }

    // --- Voice V1: IpRecords ---

    @Test
    void voiceV1IpRecordsCrudAndListMeta() {
        String body = "{\"account_sid\":\"" + ACCOUNT + "\",\"sid\":\"" + IL_SID + "\","
                + "\"friendly_name\":\"edge-1\",\"ip_address\":\"203.0.113.10\","
                + "\"cidr_prefix_length\":32,"
                + "\"date_created\":\"2026-06-27T00:00:00Z\","
                + "\"date_updated\":\"2026-06-27T00:00:00Z\","
                + "\"url\":\"https://example/v1/IpRecords/" + IL_SID + "\"}";
        String listBody = "{\"ip_records\":[" + body + "],"
                + "\"meta\":{\"first_page_url\":\"https://x/?Page=0\","
                + "\"next_page_url\":null,\"previous_page_url\":null,"
                + "\"url\":\"https://x/?Page=0\",\"page\":0,\"page_size\":50,\"key\":\"ip_records\"}}";

        handle("/v1/IpRecords", 200, body);
        client().voiceV1().ipRecords().create(
                CreateVoiceV1IpRecordRequest.builder()
                        .ipAddress("203.0.113.10")
                        .friendlyName("edge-1")
                        .cidrPrefixLength(32)
                        .build());

        RecordedRequest createReq = recorded.removeFirst();
        assertThat(createReq.method).isEqualTo("POST");
        assertThat(createReq.path).isEqualTo("/v1/IpRecords");
        Map<String, List<String>> form = formMulti(createReq.body);
        assertThat(form.get("IpAddress")).containsExactly("203.0.113.10");
        assertThat(form.get("FriendlyName")).containsExactly("edge-1");
        assertThat(form.get("CidrPrefixLength")).containsExactly("32");

        // list: re-register the context (HttpServer only allows one handler per path,
        // so we tear down and re-create)
        server.removeContext("/v1/IpRecords");
        handle("/v1/IpRecords", 200, listBody);
        VoiceV1IpRecordList page = client().voiceV1().ipRecords().list(
                ListPageParams.builder().pageSize(50).build());
        assertThat(page.getIpRecords()).hasSize(1);
        VoiceV1IpRecord rec = page.getIpRecords().get(0);
        assertThat(rec.getSid()).isEqualTo(IL_SID);
        assertThat(rec.getCidrPrefixLength()).isEqualTo(32);
        assertThat(page.getMeta()).isNotNull();
        assertThat(page.getMeta().getKey()).isEqualTo("ip_records");
        assertThat(page.getMeta().getNextPageUrl()).isNull();

        RecordedRequest listReq = recorded.removeFirst();
        assertThat(listReq.method).isEqualTo("GET");
        assertThat(listReq.query).contains("PageSize=50");
    }

    @Test
    void voiceV1IpRecordFetchUpdateDelete() {
        String body = "{\"account_sid\":\"" + ACCOUNT + "\",\"sid\":\"" + IL_SID + "\","
                + "\"ip_address\":\"203.0.113.10\",\"cidr_prefix_length\":32,"
                + "\"date_created\":\"2026-06-27T00:00:00Z\","
                + "\"date_updated\":\"2026-06-27T00:00:00Z\","
                + "\"url\":\"/v1/IpRecords/" + IL_SID + "\"}";
        handle("/v1/IpRecords/" + IL_SID, 200, body);

        VoiceV1IpRecord rec = client().voiceV1().ipRecords().fetch(IL_SID);
        assertThat(rec.getSid()).isEqualTo(IL_SID);

        RecordedRequest r = recorded.removeFirst();
        assertThat(r.method).isEqualTo("GET");
        assertThat(r.path).isEqualTo("/v1/IpRecords/" + IL_SID);
    }

    // --- Voice V1: SourceIpMappings ---

    @Test
    void voiceV1SourceIpMappingCreate() {
        String body = "{\"sid\":\"" + IB_SID + "\","
                + "\"ip_record_sid\":\"" + IL_SID + "\","
                + "\"sip_domain_sid\":\"" + SD_SID + "\","
                + "\"date_created\":\"2026-06-27T00:00:00Z\","
                + "\"date_updated\":\"2026-06-27T00:00:00Z\","
                + "\"url\":\"/v1/SourceIpMappings/" + IB_SID + "\"}";
        handle("/v1/SourceIpMappings", 201, body);

        VoiceV1SourceIpMapping m = client().voiceV1().sourceIpMappings().create(
                CreateVoiceV1SourceIpMappingRequest.builder()
                        .ipRecordSid(IL_SID).sipDomainSid(SD_SID).build());
        assertThat(m.getSid()).isEqualTo(IB_SID);

        RecordedRequest r = recorded.removeFirst();
        assertThat(r.method).isEqualTo("POST");
        assertThat(r.path).isEqualTo("/v1/SourceIpMappings");
        Map<String, List<String>> form = formMulti(r.body);
        assertThat(form.get("IpRecordSid")).containsExactly(IL_SID);
        assertThat(form.get("SipDomainSid")).containsExactly(SD_SID);
    }

    // --- Voice V1: ByocTrunks ---

    @Test
    void voiceV1ByocTrunkCreate() {
        String body = "{\"account_sid\":\"" + ACCOUNT + "\",\"sid\":\"" + BY_SID + "\","
                + "\"friendly_name\":\"carrier\","
                + "\"voice_method\":\"POST\",\"cnam_lookup_enabled\":true,"
                + "\"date_created\":\"2026-06-27T00:00:00Z\","
                + "\"date_updated\":\"2026-06-27T00:00:00Z\","
                + "\"url\":\"/v1/ByocTrunks/" + BY_SID + "\"}";
        handle("/v1/ByocTrunks", 201, body);

        VoiceV1ByocTrunk t = client().voiceV1().byocTrunks().create(
                CreateVoiceV1ByocTrunkRequest.builder()
                        .friendlyName("carrier")
                        .voiceUrl("https://example.com/twiml")
                        .voiceMethod("POST")
                        .cnamLookupEnabled(true)
                        .build());
        assertThat(t.getSid()).isEqualTo(BY_SID);
        assertThat(t.getCnamLookupEnabled()).isTrue();

        RecordedRequest r = recorded.removeFirst();
        assertThat(r.method).isEqualTo("POST");
        assertThat(r.path).isEqualTo("/v1/ByocTrunks");
        Map<String, List<String>> form = formMulti(r.body);
        assertThat(form.get("FriendlyName")).containsExactly("carrier");
        assertThat(form.get("VoiceMethod")).containsExactly("POST");
        assertThat(form.get("CnamLookupEnabled")).containsExactly("true");
    }

    // --- Voice V1: ConnectionPolicies and nested Targets ---

    @Test
    void voiceV1ConnectionPolicyCreate() {
        String body = "{\"account_sid\":\"" + ACCOUNT + "\",\"sid\":\"" + NY_SID + "\","
                + "\"friendly_name\":\"policy\","
                + "\"date_created\":\"2026-06-27T00:00:00Z\","
                + "\"date_updated\":\"2026-06-27T00:00:00Z\","
                + "\"url\":\"/v1/ConnectionPolicies/" + NY_SID + "\","
                + "\"links\":{\"targets\":\"/v1/ConnectionPolicies/" + NY_SID + "/Targets\"}}";
        handle("/v1/ConnectionPolicies", 201, body);

        VoiceV1ConnectionPolicy p = client().voiceV1().connectionPolicies().create(
                CreateVoiceV1ConnectionPolicyRequest.builder().friendlyName("policy").build());
        assertThat(p.getSid()).isEqualTo(NY_SID);
        assertThat(p.getLinks()).containsKey("targets");

        RecordedRequest r = recorded.removeFirst();
        assertThat(r.method).isEqualTo("POST");
        assertThat(r.path).isEqualTo("/v1/ConnectionPolicies");
    }

    @Test
    void voiceV1ConnectionPolicyTargetCreate() {
        String body = "{\"account_sid\":\"" + ACCOUNT + "\","
                + "\"connection_policy_sid\":\"" + NY_SID + "\",\"sid\":\"" + NE_SID + "\","
                + "\"target\":\"sip:edge@example.com\","
                + "\"priority\":10,\"weight\":10,\"enabled\":true,"
                + "\"date_created\":\"2026-06-27T00:00:00Z\","
                + "\"date_updated\":\"2026-06-27T00:00:00Z\","
                + "\"url\":\"/v1/ConnectionPolicies/" + NY_SID + "/Targets/" + NE_SID + "\"}";
        handle("/v1/ConnectionPolicies/" + NY_SID + "/Targets", 201, body);

        VoiceV1ConnectionPolicyTarget t = client().voiceV1().connectionPolicies()
                .targets(NY_SID)
                .create(CreateVoiceV1ConnectionPolicyTargetRequest.builder()
                        .target("sip:edge@example.com")
                        .priority(10).weight(10).enabled(true).build());
        assertThat(t.getSid()).isEqualTo(NE_SID);
        assertThat(t.getTarget()).isEqualTo("sip:edge@example.com");

        RecordedRequest r = recorded.removeFirst();
        assertThat(r.method).isEqualTo("POST");
        assertThat(r.path).isEqualTo("/v1/ConnectionPolicies/" + NY_SID + "/Targets");
        Map<String, List<String>> form = formMulti(r.body);
        assertThat(form.get("Target")).containsExactly("sip:edge@example.com");
        assertThat(form.get("Priority")).containsExactly("10");
        assertThat(form.get("Enabled")).containsExactly("true");
    }

    // --- Voice V1: Settings ---

    @Test
    void voiceV1SettingsFetchAndUpdate() {
        String body = "{\"dialing_permissions_inheritance\":true,\"url\":\"/v1/Settings\"}";
        handle("/v1/Settings", 200, body);

        VoiceV1DialingPermissionsSettings s = client().voiceV1().settings().fetch();
        assertThat(s.getDialingPermissionsInheritance()).isTrue();

        client().voiceV1().settings().update(
                UpdateVoiceV1SettingsRequest.builder()
                        .dialingPermissionsInheritance(false).build());

        RecordedRequest get = recorded.removeFirst();
        assertThat(get.method).isEqualTo("GET");
        assertThat(get.path).isEqualTo("/v1/Settings");

        RecordedRequest post = recorded.removeFirst();
        assertThat(post.method).isEqualTo("POST");
        assertThat(post.path).isEqualTo("/v1/Settings");
        assertThat(formMulti(post.body).get("DialingPermissionsInheritance"))
                .containsExactly("false");
    }

    // --- Conversations V1: Conversations ---

    @Test
    void conversationsV1ConversationCreate() {
        String body = "{\"account_sid\":\"" + ACCOUNT + "\",\"sid\":\"" + CH_SID + "\","
                + "\"friendly_name\":\"Support\",\"state\":\"active\",\"attributes\":\"{}\","
                + "\"date_created\":\"2026-06-27T00:00:00Z\","
                + "\"date_updated\":\"2026-06-27T00:00:00Z\","
                + "\"url\":\"/v1/Conversations/" + CH_SID + "\"}";
        handle("/v1/Conversations", 201, body);

        ConversationsV1Conversation c = client().conversationsV1().conversations().create(
                CreateConversationsV1ConversationRequest.builder()
                        .friendlyName("Support")
                        .state("active")
                        .timersInactive("PT5M")
                        .bindingsEmailAddress("ops@example.com")
                        .build());
        assertThat(c.getSid()).isEqualTo(CH_SID);
        assertThat(c.getState()).isEqualTo("active");

        RecordedRequest r = recorded.removeFirst();
        assertThat(r.method).isEqualTo("POST");
        assertThat(r.path).isEqualTo("/v1/Conversations");
        assertThat(r.path).doesNotContain(".json");
        Map<String, List<String>> form = formMulti(r.body);
        assertThat(form.get("FriendlyName")).containsExactly("Support");
        assertThat(form.get("State")).containsExactly("active");
        assertThat(form.get("Timers.Inactive")).containsExactly("PT5M");
        assertThat(form.get("Bindings.Email.Address")).containsExactly("ops@example.com");
    }

    @Test
    void conversationsV1ConversationListAndDelete() {
        String listBody = "{\"conversations\":[{\"sid\":\"" + CH_SID + "\","
                + "\"account_sid\":\"" + ACCOUNT + "\",\"state\":\"active\",\"attributes\":\"{}\","
                + "\"date_created\":\"2026-06-27T00:00:00Z\","
                + "\"date_updated\":\"2026-06-27T00:00:00Z\","
                + "\"url\":\"/v1/Conversations/" + CH_SID + "\"}],"
                + "\"meta\":{\"page\":0,\"page_size\":50,\"key\":\"conversations\"}}";
        handle("/v1/Conversations", 200, listBody);

        ConversationsV1ConversationList page = client().conversationsV1().conversations().list();
        assertThat(page.getConversations()).hasSize(1);
        assertThat(page.getMeta().getKey()).isEqualTo("conversations");

        handle("/v1/Conversations/" + CH_SID, 204, null);
        client().conversationsV1().conversations().delete(CH_SID);

        RecordedRequest del = recorded.removeLast();
        assertThat(del.method).isEqualTo("DELETE");
        assertThat(del.path).isEqualTo("/v1/Conversations/" + CH_SID);
    }

    // --- Conversations V1: Messages (nested) ---

    @Test
    void conversationsV1MessageCreateUnderConversation() {
        String body = "{\"account_sid\":\"" + ACCOUNT + "\","
                + "\"conversation_sid\":\"" + CH_SID + "\",\"sid\":\"" + IM_SID + "\","
                + "\"index\":0,\"author\":\"+15551234567\",\"body\":\"hi\",\"attributes\":\"{}\","
                + "\"date_created\":\"2026-06-27T00:00:00Z\","
                + "\"date_updated\":\"2026-06-27T00:00:00Z\","
                + "\"url\":\"/v1/Conversations/" + CH_SID + "/Messages/" + IM_SID + "\"}";
        handle("/v1/Conversations/" + CH_SID + "/Messages", 201, body);

        ConversationsV1ConversationMessage m = client().conversationsV1().conversations()
                .messages(CH_SID)
                .create(CreateConversationsV1ConversationMessageRequest.builder()
                        .author("+15551234567").body("hi").build());

        assertThat(m.getSid()).isEqualTo(IM_SID);
        assertThat(m.getIndex()).isEqualTo(0);

        RecordedRequest r = recorded.removeFirst();
        assertThat(r.method).isEqualTo("POST");
        assertThat(r.path).isEqualTo("/v1/Conversations/" + CH_SID + "/Messages");
        Map<String, List<String>> form = formMulti(r.body);
        assertThat(form.get("Author")).containsExactly("+15551234567");
        assertThat(form.get("Body")).containsExactly("hi");
    }

    // --- Conversations V1: Receipts (deeply nested) ---

    @Test
    void conversationsV1ReceiptListAndFetch() {
        String listBody = "{\"delivery_receipts\":[{\"sid\":\"" + DY_SID + "\","
                + "\"conversation_sid\":\"" + CH_SID + "\",\"message_sid\":\"" + IM_SID + "\","
                + "\"status\":\"delivered\",\"error_code\":0,"
                + "\"date_created\":\"2026-06-27T00:00:00Z\","
                + "\"date_updated\":\"2026-06-27T00:00:00Z\","
                + "\"url\":\"/x\"}],"
                + "\"meta\":{\"page\":0,\"page_size\":50,\"key\":\"delivery_receipts\"}}";
        handle("/v1/Conversations/" + CH_SID + "/Messages/" + IM_SID + "/Receipts", 200, listBody);

        ConversationsV1ConversationMessageReceiptList page = client().conversationsV1().conversations()
                .messages(CH_SID).receipts(IM_SID).list();
        assertThat(page.getDeliveryReceipts()).hasSize(1);
        assertThat(page.getMeta().getKey()).isEqualTo("delivery_receipts");

        String fetchBody = "{\"sid\":\"" + DY_SID + "\","
                + "\"conversation_sid\":\"" + CH_SID + "\",\"message_sid\":\"" + IM_SID + "\","
                + "\"status\":\"read\",\"error_code\":0,"
                + "\"date_created\":\"2026-06-27T00:00:00Z\","
                + "\"date_updated\":\"2026-06-27T00:00:00Z\","
                + "\"url\":\"/x\"}";
        handle("/v1/Conversations/" + CH_SID + "/Messages/" + IM_SID + "/Receipts/" + DY_SID,
                200, fetchBody);
        ConversationsV1ConversationMessageReceipt rcpt = client().conversationsV1().conversations()
                .messages(CH_SID).receipts(IM_SID).fetch(DY_SID);
        assertThat(rcpt.getStatus()).isEqualTo("read");
    }

    // --- Conversations V1: Participants ---

    @Test
    void conversationsV1ParticipantCreate() {
        String body = "{\"account_sid\":\"" + ACCOUNT + "\","
                + "\"conversation_sid\":\"" + CH_SID + "\",\"sid\":\"" + MB_SID + "\","
                + "\"identity\":\"alice\",\"attributes\":\"{}\","
                + "\"date_created\":\"2026-06-27T00:00:00Z\","
                + "\"date_updated\":\"2026-06-27T00:00:00Z\","
                + "\"url\":\"/v1/Conversations/" + CH_SID + "/Participants/" + MB_SID + "\"}";
        handle("/v1/Conversations/" + CH_SID + "/Participants", 201, body);

        ConversationsV1ConversationParticipant p = client().conversationsV1().conversations()
                .participants(CH_SID)
                .create(CreateConversationsV1ConversationParticipantRequest.builder()
                        .identity("alice")
                        .messagingBindingAddress("+15551234567")
                        .build());

        assertThat(p.getSid()).isEqualTo(MB_SID);

        RecordedRequest r = recorded.removeFirst();
        assertThat(r.method).isEqualTo("POST");
        assertThat(r.path).isEqualTo("/v1/Conversations/" + CH_SID + "/Participants");
        Map<String, List<String>> form = formMulti(r.body);
        assertThat(form.get("Identity")).containsExactly("alice");
        assertThat(form.get("MessagingBinding.Address")).containsExactly("+15551234567");
    }

    // --- Conversations V1: Scoped Webhooks ---

    @Test
    void conversationsV1ScopedWebhookCreate() {
        String body = "{\"sid\":\"" + WH_SID + "\",\"account_sid\":\"" + ACCOUNT + "\","
                + "\"conversation_sid\":\"" + CH_SID + "\",\"target\":\"webhook\","
                + "\"date_created\":\"2026-06-27T00:00:00Z\","
                + "\"date_updated\":\"2026-06-27T00:00:00Z\","
                + "\"configuration\":{\"url\":\"https://example/webhook\",\"method\":\"POST\"}}";
        handle("/v1/Conversations/" + CH_SID + "/Webhooks", 201, body);

        ConversationsV1ConversationScopedWebhook w = client().conversationsV1().conversations()
                .webhooks(CH_SID)
                .create(CreateConversationsV1ConversationScopedWebhookRequest.builder()
                        .target("webhook")
                        .configurationUrl("https://example/webhook")
                        .configurationMethod("POST")
                        .build());
        assertThat(w.getSid()).isEqualTo(WH_SID);

        RecordedRequest r = recorded.removeFirst();
        assertThat(r.method).isEqualTo("POST");
        assertThat(r.path).isEqualTo("/v1/Conversations/" + CH_SID + "/Webhooks");
        Map<String, List<String>> form = formMulti(r.body);
        assertThat(form.get("Target")).containsExactly("webhook");
        assertThat(form.get("Configuration.Url")).containsExactly("https://example/webhook");
        assertThat(form.get("Configuration.Method")).containsExactly("POST");
    }

    // --- Conversations V1: Roles (repeated Permission) ---

    @Test
    void conversationsV1RoleCreateRepeatsPermission() {
        String body = "{\"sid\":\"" + RL_SID + "\",\"account_sid\":\"" + ACCOUNT + "\","
                + "\"type\":\"conversation\",\"permissions\":[\"sendMessage\",\"editOwnMessage\"],"
                + "\"date_created\":\"2026-06-27T00:00:00Z\","
                + "\"date_updated\":\"2026-06-27T00:00:00Z\","
                + "\"url\":\"/v1/Roles/" + RL_SID + "\"}";
        handle("/v1/Roles", 201, body);

        ConversationsV1Role role = client().conversationsV1().roles().create(
                CreateConversationsV1RoleRequest.builder()
                        .friendlyName("agent")
                        .type("conversation")
                        .addPermission("sendMessage")
                        .addPermission("editOwnMessage")
                        .build());
        assertThat(role.getSid()).isEqualTo(RL_SID);

        RecordedRequest r = recorded.removeFirst();
        assertThat(r.method).isEqualTo("POST");
        assertThat(r.path).isEqualTo("/v1/Roles");
        Map<String, List<String>> form = formMulti(r.body);
        assertThat(form.get("FriendlyName")).containsExactly("agent");
        assertThat(form.get("Type")).containsExactly("conversation");
        assertThat(form.get("Permission")).containsExactly("sendMessage", "editOwnMessage");
    }

    @Test
    void conversationsV1RoleListMetaDecodes() {
        String listBody = "{\"roles\":[{\"sid\":\"" + RL_SID + "\","
                + "\"account_sid\":\"" + ACCOUNT + "\",\"type\":\"conversation\","
                + "\"permissions\":[\"sendMessage\"],"
                + "\"date_created\":\"2026-06-27T00:00:00Z\","
                + "\"date_updated\":\"2026-06-27T00:00:00Z\","
                + "\"url\":\"/v1/Roles/" + RL_SID + "\"}],"
                + "\"meta\":{\"page\":0,\"page_size\":50,\"key\":\"roles\"}}";
        handle("/v1/Roles", 200, listBody);

        ConversationsV1RoleList page = client().conversationsV1().roles().list();
        assertThat(page.getRoles()).hasSize(1);
        assertThat(page.getMeta().getKey()).isEqualTo("roles");
    }

    // --- Conversations V1: Users + nested per-user Conversations ---

    @Test
    void conversationsV1UserCreate() {
        String body = "{\"sid\":\"" + US_SID + "\",\"account_sid\":\"" + ACCOUNT + "\","
                + "\"identity\":\"alice\",\"attributes\":\"{}\","
                + "\"date_created\":\"2026-06-27T00:00:00Z\","
                + "\"date_updated\":\"2026-06-27T00:00:00Z\","
                + "\"url\":\"/v1/Users/" + US_SID + "\"}";
        handle("/v1/Users", 201, body);

        ConversationsV1User u = client().conversationsV1().users().create(
                CreateConversationsV1UserRequest.builder().identity("alice").build());
        assertThat(u.getSid()).isEqualTo(US_SID);

        RecordedRequest r = recorded.removeFirst();
        assertThat(r.method).isEqualTo("POST");
        assertThat(r.path).isEqualTo("/v1/Users");
        assertThat(formMulti(r.body).get("Identity")).containsExactly("alice");
    }

    @Test
    void conversationsV1UserConversationUpdate() {
        String body = "{\"account_sid\":\"" + ACCOUNT + "\","
                + "\"conversation_sid\":\"" + CH_SID + "\",\"user_sid\":\"" + US_SID + "\","
                + "\"conversation_state\":\"active\",\"notification_level\":\"muted\","
                + "\"date_created\":\"2026-06-27T00:00:00Z\","
                + "\"date_updated\":\"2026-06-27T00:00:00Z\","
                + "\"url\":\"/v1/Users/" + US_SID + "/Conversations/" + CH_SID + "\"}";
        handle("/v1/Users/" + US_SID + "/Conversations/" + CH_SID, 200, body);

        ConversationsV1UserConversation uc = client().conversationsV1().users()
                .conversations(US_SID)
                .update(CH_SID, UpdateConversationsV1UserConversationRequest.builder()
                        .notificationLevel("muted")
                        .lastReadMessageIndex(5).build());
        assertThat(uc.getNotificationLevel()).isEqualTo("muted");

        RecordedRequest r = recorded.removeFirst();
        assertThat(r.method).isEqualTo("POST");
        assertThat(r.path).isEqualTo("/v1/Users/" + US_SID + "/Conversations/" + CH_SID);
        Map<String, List<String>> form = formMulti(r.body);
        assertThat(form.get("NotificationLevel")).containsExactly("muted");
        assertThat(form.get("LastReadMessageIndex")).containsExactly("5");
    }

    // --- Conversations V1: Credentials ---

    @Test
    void conversationsV1CredentialCreate() {
        String body = "{\"sid\":\"" + CR_SID + "\",\"account_sid\":\"" + ACCOUNT + "\","
                + "\"type\":\"apn\","
                + "\"date_created\":\"2026-06-27T00:00:00Z\","
                + "\"date_updated\":\"2026-06-27T00:00:00Z\","
                + "\"url\":\"/v1/Credentials/" + CR_SID + "\"}";
        handle("/v1/Credentials", 201, body);

        ConversationsV1Credential c = client().conversationsV1().credentials().create(
                CreateConversationsV1CredentialRequest.builder()
                        .type("apn").friendlyName("ios").sandbox(true).build());
        assertThat(c.getSid()).isEqualTo(CR_SID);

        RecordedRequest r = recorded.removeFirst();
        assertThat(r.method).isEqualTo("POST");
        assertThat(r.path).isEqualTo("/v1/Credentials");
        Map<String, List<String>> form = formMulti(r.body);
        assertThat(form.get("Type")).containsExactly("apn");
        assertThat(form.get("Sandbox")).containsExactly("true");
    }

    // --- Conversations V1: Configuration + Webhook + Addresses ---

    @Test
    void conversationsV1ConfigurationFetchAndUpdate() {
        String body = "{\"account_sid\":\"" + ACCOUNT + "\","
                + "\"default_chat_service_sid\":\"" + IS_SID + "\","
                + "\"default_inactive_timer\":\"PT5M\",\"url\":\"/v1/Configuration\"}";
        handle("/v1/Configuration", 200, body);

        ConversationsV1Configuration cfg = client().conversationsV1().configuration().fetch();
        assertThat(cfg.getDefaultChatServiceSid()).isEqualTo(IS_SID);

        client().conversationsV1().configuration().update(
                UpdateConversationsV1ConfigurationRequest.builder()
                        .defaultChatServiceSid(IS_SID)
                        .defaultInactiveTimer("PT5M")
                        .build());

        RecordedRequest get = recorded.removeFirst();
        assertThat(get.method).isEqualTo("GET");
        assertThat(get.path).isEqualTo("/v1/Configuration");

        RecordedRequest post = recorded.removeFirst();
        assertThat(post.method).isEqualTo("POST");
        assertThat(post.path).isEqualTo("/v1/Configuration");
        Map<String, List<String>> form = formMulti(post.body);
        assertThat(form.get("DefaultChatServiceSid")).containsExactly(IS_SID);
        assertThat(form.get("DefaultInactiveTimer")).containsExactly("PT5M");
    }

    @Test
    void conversationsV1ConfigurationWebhookUpdateRepeatsFilters() {
        String body = "{\"account_sid\":\"" + ACCOUNT + "\","
                + "\"method\":\"POST\","
                + "\"filters\":[\"onMessageAdded\",\"onConversationAdded\"],"
                + "\"target\":\"webhook\","
                + "\"url\":\"/v1/Configuration/Webhooks\"}";
        handle("/v1/Configuration/Webhooks", 200, body);

        ConversationsV1ConfigurationWebhook w = client().conversationsV1().configuration().webhooks()
                .update(UpdateConversationsV1ConfigurationWebhookRequest.builder()
                        .method("POST")
                        .target("webhook")
                        .addFilter("onMessageAdded")
                        .addFilter("onConversationAdded")
                        .preWebhookUrl("https://example/pre")
                        .build());
        assertThat(w.getMethod()).isEqualTo("POST");

        RecordedRequest r = recorded.removeFirst();
        assertThat(r.method).isEqualTo("POST");
        assertThat(r.path).isEqualTo("/v1/Configuration/Webhooks");
        Map<String, List<String>> form = formMulti(r.body);
        assertThat(form.get("Method")).containsExactly("POST");
        assertThat(form.get("Filters")).containsExactly("onMessageAdded", "onConversationAdded");
        assertThat(form.get("PreWebhookUrl")).containsExactly("https://example/pre");
    }

    @Test
    void conversationsV1ConfigAddressCreate() {
        String body = "{\"sid\":\"" + IG_SID + "\",\"account_sid\":\"" + ACCOUNT + "\","
                + "\"type\":\"sms\",\"address\":\"+18005551234\","
                + "\"date_created\":\"2026-06-27T00:00:00Z\","
                + "\"date_updated\":\"2026-06-27T00:00:00Z\","
                + "\"url\":\"/v1/Configuration/Addresses/" + IG_SID + "\"}";
        handle("/v1/Configuration/Addresses", 201, body);

        ConversationsV1ConfigAddress addr = client().conversationsV1().configuration().addresses()
                .create(CreateConversationsV1ConfigAddressRequest.builder()
                        .type("sms")
                        .address("+18005551234")
                        .autoCreationEnabled(true)
                        .autoCreationType("webhook")
                        .build());
        assertThat(addr.getSid()).isEqualTo(IG_SID);

        RecordedRequest r = recorded.removeFirst();
        assertThat(r.method).isEqualTo("POST");
        assertThat(r.path).isEqualTo("/v1/Configuration/Addresses");
        Map<String, List<String>> form = formMulti(r.body);
        assertThat(form.get("Type")).containsExactly("sms");
        assertThat(form.get("Address")).containsExactly("+18005551234");
        assertThat(form.get("AutoCreation.Enabled")).containsExactly("true");
        assertThat(form.get("AutoCreation.Type")).containsExactly("webhook");
    }

    // --- Conversations V1: ParticipantConversations (list-only) ---

    @Test
    void conversationsV1ParticipantConversationsListFiltersByIdentity() {
        String body = "{\"conversations\":[{"
                + "\"account_sid\":\"" + ACCOUNT + "\","
                + "\"conversation_sid\":\"" + CH_SID + "\","
                + "\"participant_identity\":\"alice\","
                + "\"conversation_state\":\"active\","
                + "\"conversation_date_created\":\"2026-06-27T00:00:00Z\","
                + "\"conversation_date_updated\":\"2026-06-27T00:00:00Z\""
                + "}],"
                + "\"meta\":{\"page\":0,\"page_size\":50,\"key\":\"conversations\"}}";
        handle("/v1/ParticipantConversations", 200, body);

        ConversationsV1ParticipantConversationList page = client().conversationsV1()
                .participantConversations()
                .list(ListConversationsV1ParticipantConversationsParams.builder()
                        .identity("alice").build());
        assertThat(page.getConversations()).hasSize(1);

        RecordedRequest r = recorded.removeFirst();
        assertThat(r.method).isEqualTo("GET");
        assertThat(r.path).isEqualTo("/v1/ParticipantConversations");
        assertThat(r.query).contains("Identity=alice");
    }

    // --- Conversations V1: ConversationWithParticipants (one-shot create) ---

    @Test
    void conversationsV1ConversationWithParticipantsCreate() {
        String body = "{\"account_sid\":\"" + ACCOUNT + "\",\"sid\":\"" + CH_SID + "\","
                + "\"state\":\"active\",\"attributes\":\"{}\","
                + "\"date_created\":\"2026-06-27T00:00:00Z\","
                + "\"date_updated\":\"2026-06-27T00:00:00Z\","
                + "\"url\":\"/v1/Conversations/" + CH_SID + "\"}";
        handle("/v1/ConversationWithParticipants", 201, body);

        ConversationsV1ConversationWithParticipants c = client().conversationsV1()
                .conversationWithParticipants()
                .create(CreateConversationsV1ConversationWithParticipantsRequest.builder()
                        .friendlyName("multi")
                        .addParticipant("{\"identity\":\"alice\"}")
                        .addParticipant("{\"messaging_binding\":{\"address\":\"+15551234567\"}}")
                        .build());
        assertThat(c.getSid()).isEqualTo(CH_SID);

        RecordedRequest r = recorded.removeFirst();
        assertThat(r.method).isEqualTo("POST");
        assertThat(r.path).isEqualTo("/v1/ConversationWithParticipants");
        Map<String, List<String>> form = formMulti(r.body);
        assertThat(form.get("Participant")).containsExactly(
                "{\"identity\":\"alice\"}",
                "{\"messaging_binding\":{\"address\":\"+15551234567\"}}");
    }

    // --- Conversations V1: Services ---

    @Test
    void conversationsV1ServiceCreate() {
        String body = "{\"sid\":\"" + IS_SID + "\",\"account_sid\":\"" + ACCOUNT + "\","
                + "\"friendly_name\":\"acme-chat\","
                + "\"date_created\":\"2026-06-27T00:00:00Z\","
                + "\"date_updated\":\"2026-06-27T00:00:00Z\","
                + "\"url\":\"/v1/Services/" + IS_SID + "\"}";
        handle("/v1/Services", 201, body);

        ConversationsV1Service s = client().conversationsV1().services().create(
                CreateConversationsV1ServiceRequest.builder().friendlyName("acme-chat").build());
        assertThat(s.getSid()).isEqualTo(IS_SID);

        RecordedRequest r = recorded.removeFirst();
        assertThat(r.method).isEqualTo("POST");
        assertThat(r.path).isEqualTo("/v1/Services");
        assertThat(formMulti(r.body).get("FriendlyName")).containsExactly("acme-chat");
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
