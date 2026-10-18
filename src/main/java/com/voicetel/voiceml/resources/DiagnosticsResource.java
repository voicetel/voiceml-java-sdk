package com.voicetel.voiceml.resources;

import com.fasterxml.jackson.databind.JsonNode;
import com.voicetel.voiceml.Transport;
import com.voicetel.voiceml.models.HealthStatus;

/**
 * Diagnostic surfaces: {@code /health} and the OpenAPI document endpoints.
 *
 * <p>These are mounted at the server root, outside {@code /2010-04-01/Accounts/{AccountSid}/…},
 * and the spec marks them {@code security: []}: the SDK never sends {@code Authorization} to them.
 * Requests still go through the client's transport, so the configured {@code HttpClient} (proxy,
 * TLS), timeout, retry count and {@code User-Agent} apply. Use
 * {@code VoicemlClient.builder().baseUrl(...).buildDiagnostics()} to reach them without
 * credentials.
 */
public final class DiagnosticsResource {

    private final Transport transport;

    /**
     * Create the resource; normally obtained from {@code client.diagnostics()} or
     * {@code VoicemlClient.builder().buildDiagnostics()}.
     *
     * @param transport the transport
     */
    public DiagnosticsResource(Transport transport) {
        this.transport = transport;
    }

    /**
     * {@code GET /health}. A 200 means every hard check passes ({@code warnings} lists soft-check
     * problems). A 503 raises {@code ServerException} with the {@code failures} JSON on
     * {@code getBody()}; it is a definitive answer, so it is not retried.
     *
     * @return the health status
     */
    public HealthStatus health() {
        return transport.decode(transport.requestUnauthenticated("/health", false), HealthStatus.class);
    }

    /**
     * {@code GET /openapi.json}: the OpenAPI document as parsed JSON.
     *
     * @return the document
     */
    public JsonNode openapiJson() {
        return transport.requestUnauthenticated("/openapi.json", true);
    }

    /**
     * {@code GET /openapi.yaml}: the OpenAPI document as YAML text.
     *
     * @return the document
     */
    public String openapiYaml() {
        return transport.requestTextUnauthenticated("/openapi.yaml", "application/yaml, text/yaml, */*");
    }

    /**
     * {@code GET /openapi.yml}: the OpenAPI document as YAML text (alias of {@link #openapiYaml()}).
     *
     * @return the document
     */
    public String openapiYml() {
        return transport.requestTextUnauthenticated("/openapi.yml", "application/yaml, text/yaml, */*");
    }
}
