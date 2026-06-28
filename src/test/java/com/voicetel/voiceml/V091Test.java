package com.voicetel.voiceml;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.net.httpserver.HttpServer;
import com.voicetel.voiceml.models.AssistantsV1Assistant;
import com.voicetel.voiceml.models.AssistantsV1AssistantList;
import com.voicetel.voiceml.models.AssistantsV1AssistantWithToolsAndKnowledge;
import com.voicetel.voiceml.models.AssistantsV1Feedback;
import com.voicetel.voiceml.models.AssistantsV1FeedbackList;
import com.voicetel.voiceml.models.AssistantsV1Knowledge;
import com.voicetel.voiceml.models.AssistantsV1KnowledgeChunkList;
import com.voicetel.voiceml.models.AssistantsV1KnowledgeList;
import com.voicetel.voiceml.models.AssistantsV1KnowledgeStatus;
import com.voicetel.voiceml.models.AssistantsV1MessageList;
import com.voicetel.voiceml.models.AssistantsV1PolicyList;
import com.voicetel.voiceml.models.AssistantsV1SendMessageResponse;
import com.voicetel.voiceml.models.AssistantsV1Session;
import com.voicetel.voiceml.models.AssistantsV1SessionList;
import com.voicetel.voiceml.models.AssistantsV1Tool;
import com.voicetel.voiceml.models.AssistantsV1ToolList;
import com.voicetel.voiceml.models.AssistantsV1ToolWithPolicies;
import com.voicetel.voiceml.models.CreateAssistantsV1AssistantRequest;
import com.voicetel.voiceml.models.CreateAssistantsV1FeedbackRequest;
import com.voicetel.voiceml.models.CreateAssistantsV1KnowledgeRequest;
import com.voicetel.voiceml.models.CreateAssistantsV1ToolRequest;
import com.voicetel.voiceml.models.ListAssistantsV1KnowledgeParams;
import com.voicetel.voiceml.models.ListAssistantsV1PoliciesParams;
import com.voicetel.voiceml.models.ListAssistantsV1ToolsParams;
import com.voicetel.voiceml.models.SendAssistantsV1MessageRequest;
import com.voicetel.voiceml.models.UpdateAssistantsV1AssistantRequest;
import com.voicetel.voiceml.models.UpdateAssistantsV1KnowledgeRequest;
import com.voicetel.voiceml.models.UpdateAssistantsV1ToolRequest;
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
 * Wire-shape smoke tests for the v0.9.1 Assistants v1 surface
 * (assistants.twilio.com/v1, mapped on the VoiceML server at {@code /v1/*}).
 *
 * <p>Unlike Conversations v1 which uses {@code application/x-www-form-urlencoded},
 * Assistants v1 declares {@code application/json} request bodies — these tests
 * verify the SDK serialises the right snake_case JSON.
 */
class V091Test {

    private static final String ACCOUNT = "AC" + "f".repeat(32);
    private static final String AID = "aia_asst_alpha";
    private static final String TID = "aia_tool_one";
    private static final String KID = "aia_know_two";
    private static final String SID = "sess_three";
    private static final String MID = "aia_msg_four";
    private static final String FID = "aia_fdbk_five";
    private static final String PID = "aia_plcy_six";
    private static final String USER_SID = "US" + "0".repeat(32);

    private static final ObjectMapper MAPPER = new ObjectMapper();

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
        if (server != null) server.stop(0);
    }

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
            String contentType = exchange.getRequestHeaders().getFirst("Content-Type");
            recorded.addLast(new RecordedRequest(
                    exchange.getRequestMethod(),
                    exchange.getRequestURI().getPath(),
                    query == null ? "" : query,
                    new String(reqBody, StandardCharsets.UTF_8),
                    contentType));
            byte[] payload = body == null ? new byte[0] : body.getBytes(StandardCharsets.UTF_8);
            exchange.getResponseHeaders().set("Content-Type", "application/json");
            exchange.sendResponseHeaders(status, payload.length == 0 ? -1 : payload.length);
            try (OutputStream os = exchange.getResponseBody()) {
                os.write(payload);
            }
        });
    }

    /**
     * Register a single context that dispatches by HTTP method. {@code methodResponses} maps each
     * upper-case method name to {@code [status, body]}; bodies of {@code null} mean an empty 2xx.
     */
    private void handleByMethod(String path, Map<String, Object[]> methodResponses) {
        server.createContext(path, exchange -> {
            byte[] reqBody = exchange.getRequestBody().readAllBytes();
            String query = exchange.getRequestURI().getRawQuery();
            String contentType = exchange.getRequestHeaders().getFirst("Content-Type");
            String method = exchange.getRequestMethod().toUpperCase();
            recorded.addLast(new RecordedRequest(
                    method,
                    exchange.getRequestURI().getPath(),
                    query == null ? "" : query,
                    new String(reqBody, StandardCharsets.UTF_8),
                    contentType));
            Object[] resp = methodResponses.get(method);
            int status = resp != null ? (Integer) resp[0] : 200;
            String body = resp != null ? (String) resp[1] : null;
            byte[] payload = body == null ? new byte[0] : body.getBytes(StandardCharsets.UTF_8);
            exchange.getResponseHeaders().set("Content-Type", "application/json");
            exchange.sendResponseHeaders(status, payload.length == 0 ? -1 : payload.length);
            try (OutputStream os = exchange.getResponseBody()) {
                os.write(payload);
            }
        });
    }

    private static JsonNode parseJson(String s) {
        try {
            return MAPPER.readTree(s);
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }

    // --- factory wiring sanity ---

    @Test
    void assistantsV1FactoryWiresAllSubResources() {
        VoicemlClient c = client();
        assertThat(c.assistantsV1()).isNotNull();
        assertThat(c.assistantsV1().assistants()).isNotNull();
        assertThat(c.assistantsV1().assistants(AID)).isNotNull();
        assertThat(c.assistantsV1().assistants(AID).tools()).isNotNull();
        assertThat(c.assistantsV1().assistants(AID).knowledge()).isNotNull();
        assertThat(c.assistantsV1().assistants(AID).messages()).isNotNull();
        assertThat(c.assistantsV1().assistants(AID).feedbacks()).isNotNull();
        assertThat(c.assistantsV1().tools()).isNotNull();
        assertThat(c.assistantsV1().knowledge()).isNotNull();
        assertThat(c.assistantsV1().knowledge(KID)).isNotNull();
        assertThat(c.assistantsV1().knowledge(KID).status()).isNotNull();
        assertThat(c.assistantsV1().knowledge(KID).chunks()).isNotNull();
        assertThat(c.assistantsV1().sessions()).isNotNull();
        assertThat(c.assistantsV1().sessions(SID)).isNotNull();
        assertThat(c.assistantsV1().sessions(SID).messages()).isNotNull();
        assertThat(c.assistantsV1().policies()).isNotNull();
    }

    // ===== Assistant family (5 CRUD) =====

    @Test
    void assistantCreateSendsJsonBody() {
        String body = "{\"id\":\"" + AID + "\",\"account_sid\":\"" + ACCOUNT + "\","
                + "\"name\":\"Alpha\",\"owner\":\"alice\",\"model\":\"gpt-4o\","
                + "\"personality_prompt\":\"You are helpful\","
                + "\"customer_ai\":{\"perception_engine_enabled\":true},"
                + "\"date_created\":\"2026-06-28T00:00:00Z\","
                + "\"date_updated\":\"2026-06-28T00:00:00Z\","
                + "\"url\":\"/v1/Assistants/" + AID + "\"}";
        handle("/v1/Assistants", 201, body);

        Map<String, Object> customerAi = new HashMap<>();
        customerAi.put("perception_engine_enabled", true);

        AssistantsV1Assistant a = client().assistantsV1().assistants()
                .create(CreateAssistantsV1AssistantRequest.builder()
                        .name("Alpha")
                        .owner("alice")
                        .personalityPrompt("You are helpful")
                        .model("gpt-4o")
                        .customerAi(customerAi)
                        .build());
        assertThat(a.getId()).isEqualTo(AID);
        assertThat(a.getName()).isEqualTo("Alpha");

        RecordedRequest r = recorded.removeFirst();
        assertThat(r.method).isEqualTo("POST");
        assertThat(r.path).isEqualTo("/v1/Assistants");
        assertThat(r.path).doesNotContain(".json");
        assertThat(r.contentType).startsWith("application/json");
        JsonNode req = parseJson(r.body);
        assertThat(req.get("name").asText()).isEqualTo("Alpha");
        assertThat(req.get("owner").asText()).isEqualTo("alice");
        assertThat(req.get("personality_prompt").asText()).isEqualTo("You are helpful");
        assertThat(req.get("model").asText()).isEqualTo("gpt-4o");
        assertThat(req.get("customer_ai").get("perception_engine_enabled").asBoolean()).isTrue();
    }

    @Test
    void assistantListAndFetchInlinesToolsAndKnowledge() {
        String listBody = "{\"assistants\":[{\"id\":\"" + AID + "\",\"account_sid\":\"" + ACCOUNT + "\","
                + "\"name\":\"Alpha\",\"owner\":\"alice\",\"model\":\"gpt-4o\","
                + "\"personality_prompt\":\"P\",\"customer_ai\":{},"
                + "\"date_created\":\"2026-06-28T00:00:00Z\","
                + "\"date_updated\":\"2026-06-28T00:00:00Z\",\"url\":\"/x\"}],"
                + "\"meta\":{\"page\":0,\"page_size\":50,\"key\":\"assistants\"}}";
        handle("/v1/Assistants", 200, listBody);

        AssistantsV1AssistantList page = client().assistantsV1().assistants().list();
        assertThat(page.getAssistants()).hasSize(1);
        assertThat(page.getMeta().getKey()).isEqualTo("assistants");

        String fetchBody = "{\"id\":\"" + AID + "\",\"account_sid\":\"" + ACCOUNT + "\","
                + "\"name\":\"Alpha\",\"owner\":\"alice\",\"model\":\"gpt-4o\","
                + "\"personality_prompt\":\"P\",\"customer_ai\":{},"
                + "\"date_created\":\"2026-06-28T00:00:00Z\","
                + "\"date_updated\":\"2026-06-28T00:00:00Z\",\"url\":\"/x\","
                + "\"tools\":[{\"id\":\"" + TID + "\",\"name\":\"weather\",\"type\":\"function\","
                + "\"enabled\":true,\"requires_auth\":false,\"meta\":{},"
                + "\"description\":\"d\","
                + "\"date_created\":\"2026-06-28T00:00:00Z\","
                + "\"date_updated\":\"2026-06-28T00:00:00Z\"}],"
                + "\"knowledge\":[{\"id\":\"" + KID + "\",\"name\":\"docs\",\"type\":\"url\","
                + "\"date_created\":\"2026-06-28T00:00:00Z\","
                + "\"date_updated\":\"2026-06-28T00:00:00Z\"}]}";
        handle("/v1/Assistants/" + AID, 200, fetchBody);

        AssistantsV1AssistantWithToolsAndKnowledge fetched =
                client().assistantsV1().assistants().fetch(AID);
        assertThat(fetched.getId()).isEqualTo(AID);
        assertThat(fetched.getTools()).hasSize(1);
        assertThat(fetched.getTools().get(0).getId()).isEqualTo(TID);
        assertThat(fetched.getKnowledge()).hasSize(1);
        assertThat(fetched.getKnowledge().get(0).getId()).isEqualTo(KID);

        RecordedRequest listReq = recorded.removeFirst();
        assertThat(listReq.method).isEqualTo("GET");
        assertThat(listReq.path).isEqualTo("/v1/Assistants");
        assertThat(listReq.path).doesNotContain(".json");

        RecordedRequest fetchReq = recorded.removeFirst();
        assertThat(fetchReq.method).isEqualTo("GET");
        assertThat(fetchReq.path).isEqualTo("/v1/Assistants/" + AID);
    }

    @Test
    void assistantUpdateUsesPutWithJsonBody() {
        String body = "{\"id\":\"" + AID + "\",\"account_sid\":\"" + ACCOUNT + "\","
                + "\"name\":\"Alpha2\",\"owner\":\"alice\",\"model\":\"gpt-4o\","
                + "\"personality_prompt\":\"P\",\"customer_ai\":{},"
                + "\"date_created\":\"2026-06-28T00:00:00Z\","
                + "\"date_updated\":\"2026-06-28T00:00:00Z\",\"url\":\"/x\"}";
        handle("/v1/Assistants/" + AID, 200, body);

        AssistantsV1Assistant a = client().assistantsV1().assistants()
                .update(AID, UpdateAssistantsV1AssistantRequest.builder()
                        .name("Alpha2")
                        .personalityPrompt("New")
                        .build());
        assertThat(a.getName()).isEqualTo("Alpha2");

        RecordedRequest r = recorded.removeFirst();
        assertThat(r.method).isEqualTo("PUT");
        assertThat(r.path).isEqualTo("/v1/Assistants/" + AID);
        assertThat(r.contentType).startsWith("application/json");
        JsonNode req = parseJson(r.body);
        assertThat(req.get("name").asText()).isEqualTo("Alpha2");
        assertThat(req.get("personality_prompt").asText()).isEqualTo("New");
        assertThat(req.has("owner")).isFalse();
    }

    @Test
    void assistantDeleteUsesDelete() {
        handle("/v1/Assistants/" + AID, 204, null);
        client().assistantsV1().assistants().delete(AID);

        RecordedRequest r = recorded.removeFirst();
        assertThat(r.method).isEqualTo("DELETE");
        assertThat(r.path).isEqualTo("/v1/Assistants/" + AID);
    }

    // ===== Tool family (5 CRUD top-level + 3 attach/detach/list scoped) =====

    @Test
    void toolCreateSendsJsonBody() {
        String body = "{\"id\":\"" + TID + "\",\"name\":\"weather\",\"type\":\"function\","
                + "\"enabled\":true,\"requires_auth\":false,\"meta\":{},"
                + "\"description\":\"d\","
                + "\"date_created\":\"2026-06-28T00:00:00Z\","
                + "\"date_updated\":\"2026-06-28T00:00:00Z\"}";
        handle("/v1/Tools", 201, body);

        AssistantsV1Tool t = client().assistantsV1().tools()
                .create(CreateAssistantsV1ToolRequest.builder()
                        .name("weather")
                        .type("function")
                        .enabled(true)
                        .description("d")
                        .assistantId(AID)
                        .build());
        assertThat(t.getId()).isEqualTo(TID);

        RecordedRequest r = recorded.removeFirst();
        assertThat(r.method).isEqualTo("POST");
        assertThat(r.path).isEqualTo("/v1/Tools");
        assertThat(r.contentType).startsWith("application/json");
        JsonNode req = parseJson(r.body);
        assertThat(req.get("name").asText()).isEqualTo("weather");
        assertThat(req.get("type").asText()).isEqualTo("function");
        assertThat(req.get("enabled").asBoolean()).isTrue();
        assertThat(req.get("assistant_id").asText()).isEqualTo(AID);
    }

    @Test
    void toolListFiltersByAssistantId() {
        String body = "{\"tools\":[{\"id\":\"" + TID + "\",\"name\":\"w\",\"type\":\"function\","
                + "\"enabled\":true,\"requires_auth\":false,\"meta\":{},"
                + "\"date_created\":\"2026-06-28T00:00:00Z\","
                + "\"date_updated\":\"2026-06-28T00:00:00Z\"}],"
                + "\"meta\":{\"page\":0,\"page_size\":50,\"key\":\"tools\"}}";
        handle("/v1/Tools", 200, body);

        AssistantsV1ToolList page = client().assistantsV1().tools()
                .list(ListAssistantsV1ToolsParams.builder()
                        .assistantId(AID).pageSize(25).build());
        assertThat(page.getTools()).hasSize(1);

        RecordedRequest r = recorded.removeFirst();
        assertThat(r.method).isEqualTo("GET");
        assertThat(r.path).isEqualTo("/v1/Tools");
        assertThat(r.query).contains("AssistantId=" + AID);
        assertThat(r.query).contains("PageSize=25");
    }

    @Test
    void toolFetchInlinesPolicies() {
        String body = "{\"id\":\"" + TID + "\",\"name\":\"w\",\"type\":\"function\","
                + "\"enabled\":true,\"requires_auth\":false,\"meta\":{},"
                + "\"date_created\":\"2026-06-28T00:00:00Z\","
                + "\"date_updated\":\"2026-06-28T00:00:00Z\","
                + "\"policies\":[{\"id\":\"" + PID + "\",\"name\":\"p1\",\"type\":\"deny\","
                + "\"policy_details\":{}}]}";
        handle("/v1/Tools/" + TID, 200, body);

        AssistantsV1ToolWithPolicies t = client().assistantsV1().tools().fetch(TID);
        assertThat(t.getId()).isEqualTo(TID);
        assertThat(t.getPolicies()).hasSize(1);
        assertThat(t.getPolicies().get(0).getId()).isEqualTo(PID);

        RecordedRequest r = recorded.removeFirst();
        assertThat(r.method).isEqualTo("GET");
        assertThat(r.path).isEqualTo("/v1/Tools/" + TID);
    }

    @Test
    void toolUpdateAndDelete() {
        String body = "{\"id\":\"" + TID + "\",\"name\":\"w2\",\"type\":\"function\","
                + "\"enabled\":false,\"requires_auth\":false,\"meta\":{},"
                + "\"date_created\":\"2026-06-28T00:00:00Z\","
                + "\"date_updated\":\"2026-06-28T00:00:00Z\"}";
        Map<String, Object[]> dispatch = new HashMap<>();
        dispatch.put("PUT", new Object[] {200, body});
        dispatch.put("DELETE", new Object[] {204, null});
        handleByMethod("/v1/Tools/" + TID, dispatch);

        VoicemlClient c = client();
        AssistantsV1Tool t = c.assistantsV1().tools()
                .update(TID, UpdateAssistantsV1ToolRequest.builder()
                        .name("w2").enabled(false).build());
        assertThat(t.getName()).isEqualTo("w2");

        RecordedRequest updReq = recorded.removeFirst();
        assertThat(updReq.method).isEqualTo("PUT");
        assertThat(updReq.path).isEqualTo("/v1/Tools/" + TID);
        JsonNode req = parseJson(updReq.body);
        assertThat(req.get("name").asText()).isEqualTo("w2");
        assertThat(req.get("enabled").asBoolean()).isFalse();

        c.assistantsV1().tools().delete(TID);
        RecordedRequest delReq = recorded.removeFirst();
        assertThat(delReq.method).isEqualTo("DELETE");
        assertThat(delReq.path).isEqualTo("/v1/Tools/" + TID);
    }

    @Test
    void toolAttachAndDetachUsePostAndDelete() {
        handle("/v1/Assistants/" + AID + "/Tools/" + TID, 204, null);

        VoicemlClient c = client();
        c.assistantsV1().assistants(AID).tools().attach(TID);
        c.assistantsV1().assistants(AID).tools().detach(TID);

        RecordedRequest attachReq = recorded.removeFirst();
        assertThat(attachReq.method).isEqualTo("POST");
        assertThat(attachReq.path).isEqualTo("/v1/Assistants/" + AID + "/Tools/" + TID);

        RecordedRequest detachReq = recorded.removeFirst();
        assertThat(detachReq.method).isEqualTo("DELETE");
        assertThat(detachReq.path).isEqualTo("/v1/Assistants/" + AID + "/Tools/" + TID);
    }

    @Test
    void toolAssistantScopedListUsesAssistantPath() {
        String body = "{\"tools\":[{\"id\":\"" + TID + "\",\"name\":\"w\",\"type\":\"function\","
                + "\"enabled\":true,\"requires_auth\":false,\"meta\":{},"
                + "\"date_created\":\"2026-06-28T00:00:00Z\","
                + "\"date_updated\":\"2026-06-28T00:00:00Z\"}],"
                + "\"meta\":{\"page\":0,\"page_size\":50,\"key\":\"tools\"}}";
        handle("/v1/Assistants/" + AID + "/Tools", 200, body);

        AssistantsV1ToolList page = client().assistantsV1().assistants(AID).tools().list();
        assertThat(page.getTools()).hasSize(1);

        RecordedRequest r = recorded.removeFirst();
        assertThat(r.method).isEqualTo("GET");
        assertThat(r.path).isEqualTo("/v1/Assistants/" + AID + "/Tools");
    }

    // ===== Knowledge family (5 CRUD + status + chunks + 3 scoped) =====

    @Test
    void knowledgeCreateSendsJsonBody() {
        String body = "{\"id\":\"" + KID + "\",\"name\":\"docs\",\"type\":\"url\","
                + "\"status\":\"pending\",\"embedding_model\":\"e5\","
                + "\"date_created\":\"2026-06-28T00:00:00Z\","
                + "\"date_updated\":\"2026-06-28T00:00:00Z\"}";
        handle("/v1/Knowledge", 201, body);

        Map<String, Object> details = new HashMap<>();
        details.put("source", "https://example.com");

        AssistantsV1Knowledge k = client().assistantsV1().knowledge()
                .create(CreateAssistantsV1KnowledgeRequest.builder()
                        .name("docs")
                        .type("url")
                        .description("the docs")
                        .embeddingModel("e5")
                        .knowledgeSourceDetails(details)
                        .build());
        assertThat(k.getId()).isEqualTo(KID);

        RecordedRequest r = recorded.removeFirst();
        assertThat(r.method).isEqualTo("POST");
        assertThat(r.path).isEqualTo("/v1/Knowledge");
        assertThat(r.contentType).startsWith("application/json");
        JsonNode req = parseJson(r.body);
        assertThat(req.get("name").asText()).isEqualTo("docs");
        assertThat(req.get("type").asText()).isEqualTo("url");
        assertThat(req.get("embedding_model").asText()).isEqualTo("e5");
        assertThat(req.get("knowledge_source_details").get("source").asText())
                .isEqualTo("https://example.com");
    }

    @Test
    void knowledgeListFiltersByAssistantId() {
        String body = "{\"knowledge\":[{\"id\":\"" + KID + "\",\"name\":\"docs\","
                + "\"type\":\"url\","
                + "\"date_created\":\"2026-06-28T00:00:00Z\","
                + "\"date_updated\":\"2026-06-28T00:00:00Z\"}],"
                + "\"meta\":{\"page\":0,\"page_size\":50,\"key\":\"knowledge\"}}";
        handle("/v1/Knowledge", 200, body);

        AssistantsV1KnowledgeList page = client().assistantsV1().knowledge()
                .list(ListAssistantsV1KnowledgeParams.builder()
                        .assistantId(AID).build());
        assertThat(page.getKnowledge()).hasSize(1);

        RecordedRequest r = recorded.removeFirst();
        assertThat(r.method).isEqualTo("GET");
        assertThat(r.path).isEqualTo("/v1/Knowledge");
        assertThat(r.query).contains("AssistantId=" + AID);
    }

    @Test
    void knowledgeFetchUpdateAndDelete() {
        String fetchBody = "{\"id\":\"" + KID + "\",\"name\":\"docs\",\"type\":\"url\","
                + "\"status\":\"ready\","
                + "\"date_created\":\"2026-06-28T00:00:00Z\","
                + "\"date_updated\":\"2026-06-28T00:00:00Z\"}";
        String updBody = "{\"id\":\"" + KID + "\",\"name\":\"docs2\",\"type\":\"url\","
                + "\"date_created\":\"2026-06-28T00:00:00Z\","
                + "\"date_updated\":\"2026-06-28T00:00:00Z\"}";
        Map<String, Object[]> dispatch = new HashMap<>();
        dispatch.put("GET", new Object[] {200, fetchBody});
        dispatch.put("PUT", new Object[] {200, updBody});
        dispatch.put("DELETE", new Object[] {204, null});
        handleByMethod("/v1/Knowledge/" + KID, dispatch);

        VoicemlClient c = client();
        AssistantsV1Knowledge fetched = c.assistantsV1().knowledge().fetch(KID);
        assertThat(fetched.getId()).isEqualTo(KID);

        AssistantsV1Knowledge updated = c.assistantsV1().knowledge().update(KID,
                UpdateAssistantsV1KnowledgeRequest.builder()
                        .name("docs2").build());
        assertThat(updated.getName()).isEqualTo("docs2");

        c.assistantsV1().knowledge().delete(KID);

        RecordedRequest f = recorded.removeFirst();
        assertThat(f.method).isEqualTo("GET");
        assertThat(f.path).isEqualTo("/v1/Knowledge/" + KID);

        RecordedRequest upd = recorded.removeFirst();
        assertThat(upd.method).isEqualTo("PUT");
        assertThat(upd.path).isEqualTo("/v1/Knowledge/" + KID);
        assertThat(upd.contentType).startsWith("application/json");
        assertThat(parseJson(upd.body).get("name").asText()).isEqualTo("docs2");

        RecordedRequest del = recorded.removeFirst();
        assertThat(del.method).isEqualTo("DELETE");
        assertThat(del.path).isEqualTo("/v1/Knowledge/" + KID);
    }

    @Test
    void knowledgeStatusAndChunks() {
        String statusBody = "{\"status\":\"ready\",\"last_status\":\"indexing\","
                + "\"date_updated\":\"2026-06-28T00:00:00Z\"}";
        handle("/v1/Knowledge/" + KID + "/Status", 200, statusBody);
        AssistantsV1KnowledgeStatus s = client().assistantsV1().knowledge(KID).status().fetch();
        assertThat(s.getStatus()).isEqualTo("ready");
        assertThat(s.getLastStatus()).isEqualTo("indexing");

        String chunksBody = "{\"chunks\":[{\"content\":\"hello\",\"metadata\":{\"page\":1},"
                + "\"date_created\":\"2026-06-28T00:00:00Z\","
                + "\"date_updated\":\"2026-06-28T00:00:00Z\"}],"
                + "\"meta\":{\"page\":0,\"page_size\":50,\"key\":\"chunks\"}}";
        handle("/v1/Knowledge/" + KID + "/Chunks", 200, chunksBody);
        AssistantsV1KnowledgeChunkList cl = client().assistantsV1().knowledge(KID).chunks().list();
        assertThat(cl.getChunks()).hasSize(1);
        assertThat(cl.getChunks().get(0).getContent()).isEqualTo("hello");

        RecordedRequest sReq = recorded.removeFirst();
        assertThat(sReq.method).isEqualTo("GET");
        assertThat(sReq.path).isEqualTo("/v1/Knowledge/" + KID + "/Status");

        RecordedRequest cReq = recorded.removeFirst();
        assertThat(cReq.method).isEqualTo("GET");
        assertThat(cReq.path).isEqualTo("/v1/Knowledge/" + KID + "/Chunks");
    }

    @Test
    void knowledgeAttachAndDetach() {
        handle("/v1/Assistants/" + AID + "/Knowledge/" + KID, 204, null);

        VoicemlClient c = client();
        c.assistantsV1().assistants(AID).knowledge().attach(KID);
        c.assistantsV1().assistants(AID).knowledge().detach(KID);

        RecordedRequest aReq = recorded.removeFirst();
        assertThat(aReq.method).isEqualTo("POST");
        assertThat(aReq.path).isEqualTo("/v1/Assistants/" + AID + "/Knowledge/" + KID);

        RecordedRequest dReq = recorded.removeFirst();
        assertThat(dReq.method).isEqualTo("DELETE");
        assertThat(dReq.path).isEqualTo("/v1/Assistants/" + AID + "/Knowledge/" + KID);
    }

    @Test
    void knowledgeAssistantScopedListUsesAssistantPath() {
        String body = "{\"knowledge\":[{\"id\":\"" + KID + "\",\"name\":\"docs\","
                + "\"type\":\"url\","
                + "\"date_created\":\"2026-06-28T00:00:00Z\","
                + "\"date_updated\":\"2026-06-28T00:00:00Z\"}],"
                + "\"meta\":{\"page\":0,\"page_size\":50,\"key\":\"knowledge\"}}";
        handle("/v1/Assistants/" + AID + "/Knowledge", 200, body);

        AssistantsV1KnowledgeList page = client().assistantsV1().assistants(AID).knowledge().list();
        assertThat(page.getKnowledge()).hasSize(1);

        RecordedRequest r = recorded.removeFirst();
        assertThat(r.method).isEqualTo("GET");
        assertThat(r.path).isEqualTo("/v1/Assistants/" + AID + "/Knowledge");
    }

    // ===== Session family (3 ops) =====

    @Test
    void sessionListAndFetch() {
        String listBody = "{\"sessions\":[{\"id\":\"" + SID + "\",\"account_sid\":\"" + ACCOUNT + "\","
                + "\"assistant_id\":\"" + AID + "\",\"identity\":\"alice\",\"verified\":true,"
                + "\"date_created\":\"2026-06-28T00:00:00Z\","
                + "\"date_updated\":\"2026-06-28T00:00:00Z\"}],"
                + "\"meta\":{\"page\":0,\"page_size\":50,\"key\":\"sessions\"}}";
        handle("/v1/Sessions", 200, listBody);

        AssistantsV1SessionList page = client().assistantsV1().sessions().list();
        assertThat(page.getSessions()).hasSize(1);
        assertThat(page.getSessions().get(0).getVerified()).isTrue();

        String fetchBody = "{\"id\":\"" + SID + "\",\"account_sid\":\"" + ACCOUNT + "\","
                + "\"assistant_id\":\"" + AID + "\",\"identity\":\"alice\",\"verified\":true,"
                + "\"date_created\":\"2026-06-28T00:00:00Z\","
                + "\"date_updated\":\"2026-06-28T00:00:00Z\"}";
        handle("/v1/Sessions/" + SID, 200, fetchBody);
        AssistantsV1Session sess = client().assistantsV1().sessions().fetch(SID);
        assertThat(sess.getId()).isEqualTo(SID);

        RecordedRequest l = recorded.removeFirst();
        assertThat(l.method).isEqualTo("GET");
        assertThat(l.path).isEqualTo("/v1/Sessions");

        RecordedRequest f = recorded.removeFirst();
        assertThat(f.method).isEqualTo("GET");
        assertThat(f.path).isEqualTo("/v1/Sessions/" + SID);
    }

    @Test
    void sessionMessagesList() {
        String body = "{\"messages\":[{\"id\":\"" + MID + "\",\"assistant_id\":\"" + AID + "\","
                + "\"session_id\":\"" + SID + "\",\"role\":\"user\",\"content\":{\"text\":\"hi\"},"
                + "\"date_created\":\"2026-06-28T00:00:00Z\","
                + "\"date_updated\":\"2026-06-28T00:00:00Z\"}],"
                + "\"meta\":{\"page\":0,\"page_size\":50,\"key\":\"messages\"}}";
        handle("/v1/Sessions/" + SID + "/Messages", 200, body);

        AssistantsV1MessageList page = client().assistantsV1().sessions(SID).messages().list();
        assertThat(page.getMessages()).hasSize(1);
        assertThat(page.getMessages().get(0).getId()).isEqualTo(MID);

        RecordedRequest r = recorded.removeFirst();
        assertThat(r.method).isEqualTo("GET");
        assertThat(r.path).isEqualTo("/v1/Sessions/" + SID + "/Messages");
    }

    // ===== Message family (1 op: send) =====

    @Test
    void messageSendReturnsSendMessageResponse() {
        String body = "{\"status\":\"ok\",\"flagged\":false,\"aborted\":false,"
                + "\"session_id\":\"" + SID + "\",\"account_sid\":\"" + ACCOUNT + "\","
                + "\"body\":\"Hello back\"}";
        handle("/v1/Assistants/" + AID + "/Messages", 200, body);

        AssistantsV1SendMessageResponse resp = client().assistantsV1().assistants(AID).messages()
                .send(SendAssistantsV1MessageRequest.builder()
                        .identity("alice")
                        .body("Hello")
                        .sessionId(SID)
                        .mode("sync")
                        .build());
        assertThat(resp.getStatus()).isEqualTo("ok");
        assertThat(resp.getSessionId()).isEqualTo(SID);
        assertThat(resp.getBody()).isEqualTo("Hello back");

        RecordedRequest r = recorded.removeFirst();
        assertThat(r.method).isEqualTo("POST");
        assertThat(r.path).isEqualTo("/v1/Assistants/" + AID + "/Messages");
        assertThat(r.contentType).startsWith("application/json");
        JsonNode req = parseJson(r.body);
        assertThat(req.get("identity").asText()).isEqualTo("alice");
        assertThat(req.get("body").asText()).isEqualTo("Hello");
        assertThat(req.get("session_id").asText()).isEqualTo(SID);
        assertThat(req.get("mode").asText()).isEqualTo("sync");
    }

    // ===== Feedback family (2 ops) =====

    @Test
    void feedbackListAndCreate() {
        String listBody = "{\"feedbacks\":[{\"id\":\"" + FID + "\",\"assistant_id\":\"" + AID + "\","
                + "\"session_id\":\"" + SID + "\",\"message_id\":\"" + MID + "\","
                + "\"score\":1.0,\"text\":\"good\",\"user_sid\":\"" + USER_SID + "\","
                + "\"date_created\":\"2026-06-28T00:00:00Z\","
                + "\"date_updated\":\"2026-06-28T00:00:00Z\"}],"
                + "\"meta\":{\"page\":0,\"page_size\":50,\"key\":\"feedbacks\"}}";
        String createBody = "{\"id\":\"" + FID + "\",\"assistant_id\":\"" + AID + "\","
                + "\"session_id\":\"" + SID + "\",\"message_id\":\"" + MID + "\","
                + "\"score\":1.0,\"text\":\"good\","
                + "\"date_created\":\"2026-06-28T00:00:00Z\","
                + "\"date_updated\":\"2026-06-28T00:00:00Z\"}";
        Map<String, Object[]> dispatch = new HashMap<>();
        dispatch.put("GET", new Object[] {200, listBody});
        dispatch.put("POST", new Object[] {201, createBody});
        handleByMethod("/v1/Assistants/" + AID + "/Feedbacks", dispatch);

        VoicemlClient c = client();
        AssistantsV1FeedbackList page = c.assistantsV1().assistants(AID).feedbacks().list();
        assertThat(page.getFeedbacks()).hasSize(1);
        assertThat(page.getFeedbacks().get(0).getId()).isEqualTo(FID);

        AssistantsV1Feedback fb = c.assistantsV1().assistants(AID).feedbacks()
                .create(CreateAssistantsV1FeedbackRequest.builder()
                        .sessionId(SID)
                        .messageId(MID)
                        .score(1.0f)
                        .text("good")
                        .build());
        assertThat(fb.getId()).isEqualTo(FID);

        RecordedRequest l = recorded.removeFirst();
        assertThat(l.method).isEqualTo("GET");
        assertThat(l.path).isEqualTo("/v1/Assistants/" + AID + "/Feedbacks");

        RecordedRequest cr = recorded.removeFirst();
        assertThat(cr.method).isEqualTo("POST");
        assertThat(cr.path).isEqualTo("/v1/Assistants/" + AID + "/Feedbacks");
        assertThat(cr.contentType).startsWith("application/json");
        JsonNode req = parseJson(cr.body);
        assertThat(req.get("session_id").asText()).isEqualTo(SID);
        assertThat(req.get("message_id").asText()).isEqualTo(MID);
        assertThat(req.get("score").asDouble()).isEqualTo(1.0d);
        assertThat(req.get("text").asText()).isEqualTo("good");
    }

    // ===== Policy family (1 op) =====

    @Test
    void policyListFiltersByToolAndKnowledgeId() {
        String body = "{\"policies\":[{\"id\":\"" + PID + "\",\"type\":\"deny\","
                + "\"name\":\"p1\",\"policy_details\":{\"reason\":\"bad\"},"
                + "\"date_created\":\"2026-06-28T00:00:00Z\","
                + "\"date_updated\":\"2026-06-28T00:00:00Z\"}],"
                + "\"meta\":{\"page\":0,\"page_size\":50,\"key\":\"policies\"}}";
        handle("/v1/Policies", 200, body);

        AssistantsV1PolicyList page = client().assistantsV1().policies()
                .list(ListAssistantsV1PoliciesParams.builder()
                        .toolId(TID).knowledgeId(KID).pageSize(10).build());
        assertThat(page.getPolicies()).hasSize(1);
        assertThat(page.getPolicies().get(0).getType()).isEqualTo("deny");

        RecordedRequest r = recorded.removeFirst();
        assertThat(r.method).isEqualTo("GET");
        assertThat(r.path).isEqualTo("/v1/Policies");
        assertThat(r.query).contains("ToolId=" + TID);
        assertThat(r.query).contains("KnowledgeId=" + KID);
        assertThat(r.query).contains("PageSize=10");
    }

    /** Plain POJO so we don't need records on Java 11. */
    private static final class RecordedRequest {
        final String method;
        final String path;
        final String query;
        final String body;
        final String contentType;

        RecordedRequest(String method, String path, String query, String body, String contentType) {
            this.method = method;
            this.path = path;
            this.query = query;
            this.body = body;
            this.contentType = contentType == null ? "" : contentType;
        }
    }
}
