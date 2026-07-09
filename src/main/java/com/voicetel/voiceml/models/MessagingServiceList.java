package com.voicetel.voiceml.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;

/** Paginated {@code GET /v1/Services} response on the messaging host. */
@JsonIgnoreProperties(ignoreUnknown = true)
public class MessagingServiceList {

    @JsonProperty("services")
    private List<MessagingService> services = new ArrayList<>();

    @JsonProperty("meta")
    private VoiceV1Meta meta;

    public List<MessagingService> getServices() { return services; }
    public VoiceV1Meta getMeta() { return meta; }

    public void setServices(List<MessagingService> v) {
        this.services = v != null ? v : new ArrayList<>();
    }
    public void setMeta(VoiceV1Meta v) { this.meta = v; }
}
