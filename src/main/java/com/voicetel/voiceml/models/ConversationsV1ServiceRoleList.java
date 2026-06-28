package com.voicetel.voiceml.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;

/** Paginated service-scoped Roles response. */
@JsonIgnoreProperties(ignoreUnknown = true)
public class ConversationsV1ServiceRoleList {

    @JsonProperty("roles")
    private List<ConversationsV1ServiceRole> roles = new ArrayList<>();

    @JsonProperty("meta")
    private VoiceV1Meta meta;

    public List<ConversationsV1ServiceRole> getRoles() { return roles; }
    public VoiceV1Meta getMeta() { return meta; }

    public void setRoles(List<ConversationsV1ServiceRole> v) {
        this.roles = v != null ? v : new ArrayList<>();
    }
    public void setMeta(VoiceV1Meta v) { this.meta = v; }
}
