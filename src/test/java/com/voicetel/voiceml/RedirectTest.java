package com.voicetel.voiceml;

import com.sun.net.httpserver.HttpsConfigurator;
import com.sun.net.httpserver.HttpsServer;
import com.voicetel.voiceml.exceptions.GoneException;
import com.voicetel.voiceml.exceptions.RequestTimeoutException;
import com.voicetel.voiceml.exceptions.TransportException;
import com.voicetel.voiceml.models.RecordingAudio;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.IOException;
import java.io.InputStream;
import java.net.InetSocketAddress;
import java.net.http.HttpClient;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.security.KeyStore;
import java.time.Duration;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;
import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManagerFactory;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

/** C12: binary downloads use the caller's client, drop auth across origins, refuse downgrades. */
class RedirectTest {

    private static final String ACCOUNT = "ACXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX";
    private static final String AUDIO = "/2010-04-01/Accounts/" + ACCOUNT + "/Recordings/RE1.wav";

    @TempDir
    static Path tmp;
    private static SSLContext tls;
    private static HttpsServer https;
    private static final List<String> httpsSeen = new CopyOnWriteArrayList<>();
    private static volatile String httpsRedirectTarget;

    @BeforeAll
    static void startHttps() throws Exception {
        Path ks = tmp.resolve("test.p12");
        Process p = new ProcessBuilder(Path.of(System.getProperty("java.home"), "bin", "keytool").toString(),
                "-genkeypair", "-alias", "test", "-keyalg", "RSA", "-keysize", "2048", "-validity", "2",
                "-dname", "CN=127.0.0.1", "-ext", "SAN=ip:127.0.0.1", "-storetype", "PKCS12",
                "-keystore", ks.toString(), "-storepass", "changeit", "-keypass", "changeit")
                .redirectErrorStream(true).start();
        try (InputStream out = p.getInputStream()) {
            out.readAllBytes();
        }
        assertThat(p.waitFor()).isZero();
        KeyStore store = KeyStore.getInstance("PKCS12");
        try (InputStream in = Files.newInputStream(ks)) {
            store.load(in, "changeit".toCharArray());
        }
        KeyManagerFactory kmf = KeyManagerFactory.getInstance(KeyManagerFactory.getDefaultAlgorithm());
        kmf.init(store, "changeit".toCharArray());
        TrustManagerFactory tmf = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
        tmf.init(store);
        tls = SSLContext.getInstance("TLS");
        tls.init(kmf.getKeyManagers(), tmf.getTrustManagers(), null);

        https = HttpsServer.create(new InetSocketAddress("127.0.0.1", 0), 0);
        https.setHttpsConfigurator(new HttpsConfigurator(tls));
        https.createContext("/", ex -> {
            ex.getRequestBody().readAllBytes();
            httpsSeen.add(ex.getRequestURI().getRawPath() + " auth=" + (ex.getRequestHeaders().getFirst("Authorization") != null));
            ex.getResponseHeaders().set("Location", httpsRedirectTarget);
            ex.sendResponseHeaders(302, -1);
            ex.close();
        });
        https.start();
    }

    @AfterAll
    static void stopHttps() {
        https.stop(0);
    }

    @Test
    void crossOriginRedirectDropsAuthorization() throws IOException {
        try (MockHttp api = new MockHttp(); MockHttp storage = new MockHttp()) {
            api.respond(new MockHttp.Resp(302, null).header("Location", storage.url() + "/bucket/RE1.wav?sig=abc"));
            storage.respond(new MockHttp.Resp(200, "RIFFdata").header("Content-Type", "audio/wav"));
            RecordingAudio audio = api.client(0).recordings().getAudio("RE1");
            assertThat(new String(audio.getContent(), StandardCharsets.UTF_8)).isEqualTo("RIFFdata");
            assertThat(audio.getContentType()).isEqualTo("audio/wav");
            assertThat(audio.isViaRedirect()).isTrue();
            MockHttp.Req first = api.only();
            assertThat(first.rawPath).isEqualTo(AUDIO);
            assertThat(first.header("Authorization")).startsWith("Basic ");
            assertThat(first.header("Accept")).isEqualTo("audio/*, */*");
            MockHttp.Req hop = storage.only();
            assertThat(hop.rawPath).isEqualTo("/bucket/RE1.wav");
            assertThat(hop.rawQuery).isEqualTo("sig=abc");
            assertThat(hop.header("Authorization")).isNull();
        }
    }

    @Test
    void sameOriginRedirectKeepsAuthorization() throws IOException {
        try (MockHttp api = new MockHttp()) {
            api.respond(new MockHttp.Resp(302, null).header("Location", "/local/RE1.wav"))
               .respond(new MockHttp.Resp(200, "RIFF").header("Content-Type", "audio/wav"));
            api.client(0).recordings().getAudio("RE1");
            assertThat(api.requests).hasSize(2);
            assertThat(api.requests.get(1).rawPath).isEqualTo("/local/RE1.wav");
            assertThat(api.requests.get(1).header("Authorization")).startsWith("Basic ");
        }
    }

    @Test
    void httpsToHttpRedirectIsRefused() throws IOException {
        try (MockHttp plain = new MockHttp()) {
            httpsSeen.clear();
            httpsRedirectTarget = plain.url() + "/downgraded.wav";
            HttpClient client = HttpClient.newBuilder().sslContext(tls).build();
            VoicemlClient voiceml = VoicemlClient.builder().accountSid(ACCOUNT).apiKey("k")
                    .baseUrl("https://127.0.0.1:" + https.getAddress().getPort())
                    .httpClient(client).maxRetries(0).build();
            assertThatThrownBy(() -> voiceml.recordings().getAudio("RE1"))
                    .isInstanceOf(TransportException.class)
                    .hasMessageContaining("refusing redirect from https to http");
            assertThat(httpsSeen).containsExactly(AUDIO + " auth=true");
            assertThat(plain.requests).isEmpty();
        }
    }

    @Test
    void usesTheCallersHttpClientAndTimeout() throws IOException {
        try (MockHttp api = new MockHttp()) {
            api.respond(new MockHttp.Resp(200, "RIFF").header("Content-Type", "audio/wav").delay(1500));
            AtomicInteger used = new AtomicInteger();
            java.util.concurrent.ExecutorService pool = Executors.newCachedThreadPool();
            Executor counting = command -> {
                used.incrementAndGet();
                pool.execute(command);
            };
            HttpClient mine = HttpClient.newBuilder().executor(counting).build();
            VoicemlClient voiceml = VoicemlClient.builder().accountSid(ACCOUNT).apiKey("k").baseUrl(api.url())
                    .httpClient(mine).timeout(Duration.ofMillis(300)).maxRetries(0).build();
            assertThatThrownBy(() -> voiceml.recordings().getAudio("RE1"))
                    .isInstanceOf(RequestTimeoutException.class);
            assertThat(used.get()).isPositive();
            pool.shutdownNow();
        }
    }

    @Test
    void goneAudioRaisesGoneException() throws IOException {
        try (MockHttp api = new MockHttp()) {
            api.respond(410, "{\"code\":20410,\"message\":\"recording audio is gone\"}");
            assertThatThrownBy(() -> api.client(0).recordings().getAudio("RE1")).isInstanceOf(GoneException.class);
        }
    }

    @Test
    void redirectLoopsAreBounded() throws IOException {
        try (MockHttp api = new MockHttp()) {
            api.respond(new MockHttp.Resp(302, null).header("Location", "/again"));
            assertThatThrownBy(() -> api.client(0).recordings().getAudio("RE1"))
                    .isInstanceOf(TransportException.class).hasMessageContaining("too many redirects");
            assertThat(api.requests).hasSize(Transport.MAX_REDIRECTS + 1);
        }
    }

    @Test
    void exportsDownloadFollowsTheRedirectWithoutAuth() throws IOException {
        try (MockHttp api = new MockHttp(); MockHttp storage = new MockHttp()) {
            api.respond(new MockHttp.Resp(302, null).header("Location", storage.url() + "/exports/2026-10-01.tar.gz"));
            storage.respond(new MockHttp.Resp(200, "archive").header("Content-Type", "application/gzip"));
            Transport.BinaryResponse r = api.client(0).exports().download("2026-10-01");
            assertThat(new String(r.getContent(), StandardCharsets.UTF_8)).isEqualTo("archive");
            assertThat(api.only().rawPath).isEqualTo("/2010-04-01/Accounts/" + ACCOUNT + "/Exports/2026-10-01");
            assertThat(storage.only().header("Authorization")).isNull();
        }
    }
}
