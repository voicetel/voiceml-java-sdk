package com.voicetel.voiceml.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;

/** Paginated {@code /v1/Services} response. */
@JsonIgnoreProperties(ignoreUnknown = true)
public class ConversationsV1ServiceList {

    @JsonProperty("services")
    private List<ConversationsV1Service> services = new ArrayList<>();

    @JsonProperty("meta")
    private VoiceV1Meta meta;

    public List<ConversationsV1Service> getServices() { return services; }
    public VoiceV1Meta getMeta() { return meta; }

    public void setServices(List<ConversationsV1Service> v) {
        this.services = v != null ? v : new ArrayList<>();
    }
    public void setMeta(VoiceV1Meta v) { this.meta = v; }
}
