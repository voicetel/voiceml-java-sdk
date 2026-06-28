package com.voicetel.voiceml.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;

/** Paginated service-scoped Bindings response. */
@JsonIgnoreProperties(ignoreUnknown = true)
public class ConversationsV1ServiceBindingList {

    @JsonProperty("bindings")
    private List<ConversationsV1ServiceBinding> bindings = new ArrayList<>();

    @JsonProperty("meta")
    private VoiceV1Meta meta;

    public List<ConversationsV1ServiceBinding> getBindings() { return bindings; }
    public VoiceV1Meta getMeta() { return meta; }

    public void setBindings(List<ConversationsV1ServiceBinding> v) {
        this.bindings = v != null ? v : new ArrayList<>();
    }
    public void setMeta(VoiceV1Meta v) { this.meta = v; }
}
