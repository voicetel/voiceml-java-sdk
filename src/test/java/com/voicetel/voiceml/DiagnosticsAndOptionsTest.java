package com.voicetel.voiceml;

import com.voicetel.voiceml.exceptions.ConfigurationException;
import com.voicetel.voiceml.exceptions.ServerException;
import com.voicetel.voiceml.models.HealthStatus;
import com.voicetel.voiceml.resources.DiagnosticsResource;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.time.Duration;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

/** C7 diagnostics without credentials, and the {@code Builder.options(...)} host overrides. */
class DiagnosticsAndOptionsTest {

    @Test
    void diagnosticsWorkWithoutCredentialsAndSendNoAuthorization() throws IOException {
        try (MockHttp mock = new MockHttp()) {
            mock.respond(200, "{\"ok\":true}")
                .respond(new MockHttp.Resp(200, "openapi: 3.1.0\n").header("Content-Type", "application/yaml"))
                .respond(new MockHttp.Resp(200, "openapi: 3.1.0\n").header("Content-Type", "application/yaml"))
                .respond(200, "{\"openapi\":\"3.1.0\"}");
            DiagnosticsResource diag = VoicemlClient.builder().baseUrl(mock.url()).userAgent("probe/1.0")
                    .buildDiagnostics();
            HealthStatus health = diag.health();
            assertThat(health.isOk()).isTrue();
            assertThat(health.getWarnings()).isEmpty();
            assertThat(health.getFailures()).isEmpty();
            assertThat(diag.openapiYaml()).startsWith("openapi:");
            assertThat(diag.openapiYml()).startsWith("openapi:");
            assertThat(diag.openapiJson().path("openapi").asText()).isEqualTo("3.1.0");
            assertThat(mock.requests).extracting(r -> r.rawPath)
                    .containsExactly("/health", "/openapi.yaml", "/openapi.yml", "/openapi.json");
            for (MockHttp.Req r : mock.requests) {
                assertThat(r.header("Authorization")).isNull();
                assertThat(r.header("User-Agent")).isEqualTo("probe/1.0");
            }
            assertThat(mock.requests.get(1).header("Accept")).contains("application/yaml");
        }
    }

    @Test
    void anAuthenticatedClientAlsoSendsNoAuthorizationToDiagnostics() throws IOException {
        try (MockHttp mock = new MockHttp()) {
            mock.respond(200, "{\"ok\":true,\"warnings\":[{\"check\":\"s3\",\"detail\":\"slow\"}]}");
            HealthStatus h = mock.client(0).diagnostics().health();
            assertThat(h.getWarnings()).hasSize(1);
            assertThat(mock.only().header("Authorization")).isNull();
        }
    }

    @Test
    void unhealthyIsAServerErrorAndIsNotRetried() throws IOException {
        try (MockHttp mock = new MockHttp()) {
            mock.respond(503, "{\"ok\":false,\"failures\":[{\"check\":\"db\",\"detail\":\"down\"}]}");
            assertThatThrownBy(() -> mock.client(2).diagnostics().health())
                    .isInstanceOf(ServerException.class)
                    .satisfies(e -> assertThat(((ServerException) e).getBody()).contains("\"failures\""));
            assertThat(mock.requests).hasSize(1);
        }
    }

    @Test
    void aDiagnosticsOnlyClientRefusesAuthenticatedCalls() {
        VoicemlClient.Builder b = VoicemlClient.builder().baseUrl("http://127.0.0.1:9");
        assertThatThrownBy(b::build).isInstanceOf(ConfigurationException.class);
        assertThat(b.buildDiagnostics()).isNotNull();
    }

    @Test
    void optionsCarriesTheMessagingAndConversationsOverrides() throws IOException {
        try (MockHttp base = new MockHttp(); MockHttp messaging = new MockHttp(); MockHttp conversations = new MockHttp()) {
            messaging.respond(200, "{\"services\":[],\"meta\":{}}");
            conversations.respond(200, "{\"services\":[],\"meta\":{}}");
            ClientOptions options = ClientOptions.builder()
                    .accountSid("ACXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX").apiKey("k")
                    .baseUrl(base.url())
                    .messagingBaseUrl(messaging.url())
                    .conversationsBaseUrl(conversations.url())
                    .timeout(Duration.ofSeconds(5))
                    .maxRetries(0)
                    .build();
            VoicemlClient client = VoicemlClient.builder().options(options).build();
            client.messagingV1().services().list();
            client.conversationsV1().services().list();
            assertThat(messaging.only().rawPath).isEqualTo("/v1/Services");
            assertThat(conversations.only().rawPath).isEqualTo("/v1/Services");
            assertThat(base.requests).isEmpty();
        }
    }

    @Test
    void optionsWithoutOverridesStillDerivesProductHosts() {
        ClientOptions options = ClientOptions.builder()
                .accountSid("ACXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX").apiKey("k").build();
        VoicemlClient.builder().options(options).build();
        assertThat(options.getMessagingBaseUrl()).isEqualTo("https://messaging.voicetel.com");
        assertThat(options.getConversationsBaseUrl()).isEqualTo("https://conversations.voicetel.com");
    }

    @Test
    void invalidConfigurationRaisesConfigurationException() {
        ClientOptions.Builder good = ClientOptions.builder().accountSid("AC1").apiKey("k");
        assertThatThrownBy(() -> ClientOptions.builder().accountSid("AC1").apiKey("k").baseUrl("not a url").build())
                .isInstanceOf(ConfigurationException.class);
        assertThatThrownBy(() -> ClientOptions.builder().accountSid("AC1").apiKey("k").baseUrl("ftp://x").build())
                .isInstanceOf(ConfigurationException.class);
        assertThatThrownBy(() -> ClientOptions.builder().accountSid("AC1").apiKey("k")
                .baseUrl("https://user:pass@voiceml.voicetel.com").build())
                .isInstanceOf(ConfigurationException.class);
        assertThatThrownBy(() -> ClientOptions.builder().accountSid("AC1").apiKey("k")
                .timeout(Duration.ZERO).build()).isInstanceOf(ConfigurationException.class);
        assertThatThrownBy(() -> ClientOptions.builder().accountSid("AC1").apiKey("k").maxRetries(-1).build())
                .isInstanceOf(ConfigurationException.class);
        assertThat(good.build().getBaseUrl()).isEqualTo(ClientOptions.DEFAULT_BASE_URL);
    }
}
