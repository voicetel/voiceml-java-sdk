package com.voicetel.voiceml.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;

/** Paginated {@code /v1/Configuration/Addresses} response. */
@JsonIgnoreProperties(ignoreUnknown = true)
public class ConversationsV1ConfigAddressList {

    @JsonProperty("addresses")
    private List<ConversationsV1ConfigAddress> addresses = new ArrayList<>();

    @JsonProperty("meta")
    private VoiceV1Meta meta;

    public List<ConversationsV1ConfigAddress> getAddresses() { return addresses; }
    public VoiceV1Meta getMeta() { return meta; }

    public void setAddresses(List<ConversationsV1ConfigAddress> v) {
        this.addresses = v != null ? v : new ArrayList<>();
    }
    public void setMeta(VoiceV1Meta v) { this.meta = v; }
}
