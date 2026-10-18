package com.voicetel.voiceml.smoke;

import com.sun.net.httpserver.HttpServer;
import com.voicetel.voiceml.RequestValidator;
import com.voicetel.voiceml.VoicemlClient;
import com.voicetel.voiceml.exceptions.ServerException;
import com.voicetel.voiceml.models.AccountWithKey;
import com.voicetel.voiceml.models.Call;
import com.voicetel.voiceml.models.CallStatus;
import com.voicetel.voiceml.models.CreateCallRequest;
import com.voicetel.voiceml.models.ListMonitorAlertsParams;
import com.voicetel.voiceml.models.MonitorV1Alert;
import com.voicetel.voiceml.models.RecordingAudio;

import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * JUnit-free runtime smoke test of the built jar, run on the declared Java 11 floor in CI (the
 * JUnit 6 suite needs Java 17). Exercises the transport (form encoding, auth, retries off),
 * lazy pagination, JSON decoding with an unknown enum, the redirect rules of binary downloads,
 * unauthenticated diagnostics, error mapping, the webhook validator, key-rotation redaction and
 * the Monitor alerts envelope. Exits non-zero on failure.
 *
 * <pre>
 *   javac --release 11 -cp voiceml.jar:jackson-*.jar -d out src/smoke/java/com/voicetel/voiceml/smoke/Jdk11Smoke.java
 *   java -cp out:voiceml.jar:jackson-*.jar com.voicetel.voiceml.smoke.Jdk11Smoke
 * </pre>
 */
public final class Jdk11Smoke {

    private static final String ACCOUNT = "ACXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX";
    private static final List<String> seen = Collections.synchronizedList(new ArrayList<>());

    private Jdk11Smoke() {}

    public static void main(String[] args) throws Exception {
        HttpServer storage = HttpServer.create(new InetSocketAddress("127.0.0.1", 0), 0);
        storage.createContext("/", ex -> {
            seen.add("storage auth=" + (ex.getRequestHeaders().getFirst("Authorization") != null));
            reply(ex, 200, "audio/wav", "RIFF");
        });
        storage.start();
        String storageUrl = "http://127.0.0.1:" + storage.getAddress().getPort();

        HttpServer api = HttpServer.create(new InetSocketAddress("127.0.0.1", 0), 0);
        String calls = "/2010-04-01/Accounts/" + ACCOUNT + "/Calls.json";
        api.createContext("/", ex -> {
            String path = ex.getRequestURI().getRawPath();
            String query = ex.getRequestURI().getRawQuery();
            String body = new String(ex.getRequestBody().readAllBytes(), StandardCharsets.UTF_8);
            seen.add(ex.getRequestMethod() + " " + path + (query == null ? "" : "?" + query) + " " + body
                    + " auth=" + (ex.getRequestHeaders().getFirst("Authorization") != null));
            if (path.equals(calls) && "POST".equals(ex.getRequestMethod())) {
                reply(ex, 201, "application/json", "{\"sid\":\"CA1\",\"status\":\"queued\",\"extra\":1}");
            } else if (path.equals(calls) && query == null) {
                reply(ex, 200, "application/json", "{\"calls\":[{\"sid\":\"CA1\",\"status\":\"scheduled\"}],"
                        + "\"next_page_uri\":\"" + calls + "?Page=1&PageToken=PA1\"}");
            } else if (path.equals(calls)) {
                reply(ex, 200, "application/json", "{\"calls\":[{\"sid\":\"CA2\",\"status\":\"completed\"}],"
                        + "\"next_page_uri\":null}");
            } else if (path.endsWith("/Recordings/RE1.wav")) {
                ex.getResponseHeaders().set("Location", storageUrl + "/bucket/RE1.wav");
                reply(ex, 302, "text/plain", "");
            } else if (path.endsWith("/RotateKey.json") && "POST".equals(ex.getRequestMethod())) {
                reply(ex, 200, "application/json", "{\"sid\":\"" + ACCOUNT + "\","
                        + "\"api_key\":\"rotated-key-0123456789\",\"allow_cidr\":[],\"disabled\":false}");
            } else if (path.equals("/v1/Alerts")) {
                reply(ex, 200, "application/json", "{\"alerts\":[{\"sid\":\"NO1\",\"error_code\":\"11200\","
                        + "\"log_level\":\"error\",\"alert_text\":\"failed\"}],"
                        + "\"meta\":{\"page\":0,\"page_size\":20,\"next_page_url\":null}}");
            } else if (path.equals("/health")) {
                reply(ex, 503, "application/json", "{\"ok\":false,\"failures\":[{\"check\":\"db\"}]}");
            } else {
                reply(ex, 404, "application/json", "{\"code\":20404,\"message\":\"nope\"}");
            }
        });
        api.start();
        String apiUrl = "http://127.0.0.1:" + api.getAddress().getPort();
        try {
            VoicemlClient client = VoicemlClient.builder().accountSid(ACCOUNT).apiKey("smoke-key")
                    .baseUrl(apiUrl).maxRetries(0).build();

            Call call = client.calls().create(CreateCallRequest.builder()
                    .to("+14155550100").from("+14155550101").url("https://example.com/twiml").build());
            check("create decodes", "CA1".equals(call.getSid()) && call.getStatus() == CallStatus.QUEUED);
            check("create form body", seen.get(0).contains(
                    "To=%2B14155550100&From=%2B14155550101&Url=https%3A%2F%2Fexample.com%2Ftwiml auth=true"));

            List<String> sids = new ArrayList<>();
            for (Call c : client.calls().iterate()) {
                sids.add(c.getSid() + ":" + c.getStatus());
            }
            check("pagination", sids.equals(List.of("CA1:unknown", "CA2:completed")));
            check("pagination follows PageToken", seen.get(2).contains("?Page=1&PageToken=PA1"));

            RecordingAudio audio = client.recordings().getAudio("RE1");
            check("audio via redirect", audio.isViaRedirect() && new String(audio.getContent(), StandardCharsets.UTF_8).equals("RIFF"));
            check("redirect drops auth", seen.contains("storage auth=false"));

            try {
                VoicemlClient.builder().baseUrl(apiUrl).buildDiagnostics().health();
                check("health 503 raises", false);
            } catch (ServerException expected) {
                check("health 503 raises", expected.getStatusCode() == 503);
            }
            check("diagnostics unauthenticated", seen.stream().anyMatch(s -> s.startsWith("GET /health") && s.endsWith("auth=false")));

            AccountWithKey rotated = client.accounts().rotateKey();
            check("rotateKey decodes", "rotated-key-0123456789".equals(rotated.getApiKey()));
            check("rotated key redacted from toString",
                    rotated.toString().contains("[redacted]") && !rotated.toString().contains("rotated-key-0123456789"));
            MonitorV1Alert alert = client.monitorV1().alerts().list(ListMonitorAlertsParams.builder()
                    .logLevel("error").pageSize(20).build()).getAlerts().get(0);
            check("monitor alerts decode", "NO1".equals(alert.getSid()) && "error".equals(alert.getLogLevel()));
            check("monitor alerts query", seen.stream().anyMatch(s ->
                    s.startsWith("GET /v1/Alerts?LogLevel=error&PageSize=20") && s.endsWith("auth=true")));

            RequestValidator validator = new RequestValidator("test-api-key-0123456789");
            Map<String, List<String>> params = new LinkedHashMap<>();
            params.put("To", List.of("+14155550101"));
            params.put("Event", List.of("answered", "initiated"));
            check("webhook signature", validator.validate("https://example.com/hook", params, "jCnpBgyg45aZYwUie5fmq4EYUic="));

            System.out.println("JDK " + System.getProperty("java.version") + " smoke OK (" + seen.size() + " requests)");
        } finally {
            api.stop(0);
            storage.stop(0);
        }
    }

    private static void reply(com.sun.net.httpserver.HttpExchange ex, int status, String type, String body)
            throws java.io.IOException {
        byte[] b = body.getBytes(StandardCharsets.UTF_8);
        ex.getResponseHeaders().set("Content-Type", type);
        ex.sendResponseHeaders(status, b.length == 0 ? -1 : b.length);
        if (b.length > 0) {
            try (OutputStream os = ex.getResponseBody()) {
                os.write(b);
            }
        }
        ex.close();
    }

    private static void check(String what, boolean ok) {
        if (!ok) {
            System.err.println("SMOKE FAILED: " + what + "\nrequests: " + seen);
            System.exit(1);
        }
    }
}
