package com.voicetel.voiceml.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;

/** Paginated {@code /v1/Roles} response. */
@JsonIgnoreProperties(ignoreUnknown = true)
public class ConversationsV1RoleList {

    @JsonProperty("roles")
    private List<ConversationsV1Role> roles = new ArrayList<>();

    @JsonProperty("meta")
    private VoiceV1Meta meta;

    public List<ConversationsV1Role> getRoles() { return roles; }
    public VoiceV1Meta getMeta() { return meta; }

    public void setRoles(List<ConversationsV1Role> v) {
        this.roles = v != null ? v : new ArrayList<>();
    }
    public void setMeta(VoiceV1Meta v) { this.meta = v; }
}
