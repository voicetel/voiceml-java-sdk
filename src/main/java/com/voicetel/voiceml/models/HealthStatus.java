package com.voicetel.voiceml.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;

/**
 * {@code GET /health} response — composite probe.
 *
 * <p>Hard-check failures flip {@link #isOk()} to {@code false} (server returns 503). Soft-check
 * warnings surface in {@link #getWarnings()} only and don't take the host out of rotation.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class HealthStatus {

    @JsonProperty("ok")
    private boolean ok;

    @JsonProperty("warnings")
    private List<HealthFailure> warnings = new ArrayList<>();

    @JsonProperty("failures")
    private List<HealthFailure> failures = new ArrayList<>();

    public boolean isOk() { return ok; }
    public void setOk(boolean ok) { this.ok = ok; }
    public List<HealthFailure> getWarnings() { return warnings; }
    public void setWarnings(List<HealthFailure> warnings) { this.warnings = warnings; }
    public List<HealthFailure> getFailures() { return failures; }
    public void setFailures(List<HealthFailure> failures) { this.failures = failures; }
}
