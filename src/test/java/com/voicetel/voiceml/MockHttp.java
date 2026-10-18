package com.voicetel.voiceml;

import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.nio.charset.StandardCharsets;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Minimal programmable HTTP server for wire-level tests. Records every request exactly as
 * received (raw path, raw query, raw body bytes, headers) and answers from a FIFO of scripted
 * responses; when the script is exhausted the last response repeats.
 */
final class MockHttp implements AutoCloseable {

    /** One recorded request. */
    static final class Req {
        final String method;
        final String rawPath;
        final String rawQuery;
        final String body;
        final Map<String, String> headers;

        Req(String method, String rawPath, String rawQuery, String body, Map<String, String> headers) {
            this.method = method;
            this.rawPath = rawPath;
            this.rawQuery = rawQuery;
            this.body = body;
            this.headers = headers;
        }

        String header(String name) {
            return headers.get(name.toLowerCase());
        }
    }

    /** One scripted response. */
    static final class Resp {
        final int status;
        final String body;
        final Map<String, String> headers = new LinkedHashMap<>();
        long delayMs;

        Resp(int status, String body) {
            this.status = status;
            this.body = body;
        }

        Resp header(String name, String value) {
            headers.put(name, value);
            return this;
        }

        Resp delay(long ms) {
            this.delayMs = ms;
            return this;
        }
    }

    private final HttpServer server;
    final List<Req> requests = new CopyOnWriteArrayList<>();
    private final Deque<Resp> script = new ArrayDeque<>();
    private Resp last = new Resp(200, "{}");

    MockHttp() throws IOException {
        server = HttpServer.create(new InetSocketAddress("127.0.0.1", 0), 0);
        server.createContext("/", ex -> {
            byte[] body = ex.getRequestBody().readAllBytes();
            Map<String, String> headers = new LinkedHashMap<>();
            ex.getRequestHeaders().forEach((k, v) -> headers.put(k.toLowerCase(), String.join(",", v)));
            requests.add(new Req(ex.getRequestMethod(), ex.getRequestURI().getRawPath(),
                    ex.getRequestURI().getRawQuery() == null ? "" : ex.getRequestURI().getRawQuery(),
                    new String(body, StandardCharsets.UTF_8), headers));
            Resp r;
            synchronized (script) {
                r = script.isEmpty() ? last : script.pollFirst();
                last = r;
            }
            if (r.delayMs > 0) {
                try {
                    Thread.sleep(r.delayMs);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
            byte[] payload = r.body == null ? new byte[0] : r.body.getBytes(StandardCharsets.UTF_8);
            if (!r.headers.containsKey("Content-Type")) {
                ex.getResponseHeaders().set("Content-Type", "application/json");
            }
            r.headers.forEach((k, v) -> ex.getResponseHeaders().set(k, v));
            try {
                ex.sendResponseHeaders(r.status, payload.length == 0 ? -1 : payload.length);
                if (payload.length > 0) {
                    try (OutputStream os = ex.getResponseBody()) {
                        os.write(payload);
                    }
                }
            } catch (IOException clientWentAway) {
                // the client timed out; nothing to deliver
            } finally {
                ex.close();
            }
        });
        server.setExecutor(java.util.concurrent.Executors.newCachedThreadPool());
        server.start();
    }

    MockHttp respond(int status, String body) {
        return respond(new Resp(status, body));
    }

    MockHttp respond(Resp r) {
        synchronized (script) {
            script.addLast(r);
        }
        return this;
    }

    String url() {
        return "http://127.0.0.1:" + server.getAddress().getPort();
    }

    int port() {
        return server.getAddress().getPort();
    }

    Req only() {
        if (requests.size() != 1) {
            throw new AssertionError("expected exactly 1 request, got " + requests.size());
        }
        return requests.get(0);
    }

    VoicemlClient client(int maxRetries) {
        return VoicemlClient.builder()
                .accountSid("ACXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX")
                .apiKey("test-secret-key")
                .baseUrl(url())
                .maxRetries(maxRetries)
                .build();
    }

    @Override
    public void close() {
        server.stop(0);
    }
}
