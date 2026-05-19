package com.voicetel.voiceml.resources;

import com.fasterxml.jackson.databind.JsonNode;
import com.voicetel.voiceml.Transport;
import com.voicetel.voiceml.exceptions.ApiException;
import com.voicetel.voiceml.models.HealthStatus;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.time.Duration;

/**
 * Diagnostic surfaces — {@code /health} and the OpenAPI doc endpoints.
 *
 * <p>These don't sit under {@code /2010-04-01/Accounts/{AccountSid}/…}; they're mounted at the
 * server root and don't require auth (the spec marks them {@code security: []}).
 */
public final class DiagnosticsResource {

    private final Transport transport;

    public DiagnosticsResource(Transport transport) {
        this.transport = transport;
    }

    /**
     * Hit {@code /health}. A 200 means all hard checks pass; a 503 raises
     * {@code ServerException} with the failure list on {@code body}.
     */
    public HealthStatus health() {
        JsonNode node = unauthRequest("/health");
        try {
            return transport.mapper().treeToValue(node, HealthStatus.class);
        } catch (IOException ex) {
            throw new ApiException(
                    "failed to decode /health response: " + ex.getMessage(), 0, null, null, ex);
        }
    }

    /** Fetch the OpenAPI spec as parsed JSON. */
    public JsonNode openapiJson() {
        return unauthRequest("/openapi.json");
    }

    private JsonNode unauthRequest(String path) {
        HttpRequest req = HttpRequest.newBuilder()
                .uri(URI.create(transport.baseUrl() + path))
                .timeout(Duration.ofSeconds(10))
                .header("Accept", "application/json")
                .header("User-Agent", transport.options().getUserAgent())
                .GET()
                .build();

        HttpClient http = HttpClient.newBuilder()
                .connectTimeout(Duration.ofSeconds(10))
                .build();

        HttpResponse<String> resp;
        try {
            resp = http.send(req, HttpResponse.BodyHandlers.ofString(StandardCharsets.UTF_8));
        } catch (IOException ex) {
            throw new ApiException(
                    "transport error: " + ex.getMessage(), 0, null, null, ex);
        } catch (InterruptedException ex) {
            Thread.currentThread().interrupt();
            throw new ApiException(
                    "request interrupted: " + ex.getMessage(), 0, null, null, ex);
        }
        int status = resp.statusCode();
        String body = resp.body();
        if (status < 200 || status >= 300) {
            throw Transport.mapError(status, body);
        }
        try {
            return transport.mapper().readTree(body);
        } catch (IOException ex) {
            throw new ApiException(
                    "non-JSON response from " + path + ": " + ex.getMessage(),
                    status,
                    null,
                    body,
                    ex);
        }
    }
}
