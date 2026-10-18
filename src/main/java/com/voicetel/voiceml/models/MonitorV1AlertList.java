package com.voicetel.voiceml.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;

/** Paginated {@code /v1/Alerts} response. */
@JsonIgnoreProperties(ignoreUnknown = true)
public class MonitorV1AlertList implements MetaPage {

    @JsonProperty("alerts")
    private List<MonitorV1Alert> alerts = new ArrayList<>();

    @JsonProperty("meta")
    private VoiceV1Meta meta;

    public List<MonitorV1Alert> getAlerts() { return alerts; }
    public VoiceV1Meta getMeta() { return meta; }

    public void setAlerts(List<MonitorV1Alert> v) {
        this.alerts = v != null ? v : new ArrayList<>();
    }
    public void setMeta(VoiceV1Meta v) { this.meta = v; }
}
