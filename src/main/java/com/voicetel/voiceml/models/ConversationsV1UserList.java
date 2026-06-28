package com.voicetel.voiceml.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;

/** Paginated {@code /v1/Users} response. */
@JsonIgnoreProperties(ignoreUnknown = true)
public class ConversationsV1UserList {

    @JsonProperty("users")
    private List<ConversationsV1User> users = new ArrayList<>();

    @JsonProperty("meta")
    private VoiceV1Meta meta;

    public List<ConversationsV1User> getUsers() { return users; }
    public VoiceV1Meta getMeta() { return meta; }

    public void setUsers(List<ConversationsV1User> v) {
        this.users = v != null ? v : new ArrayList<>();
    }
    public void setMeta(VoiceV1Meta v) { this.meta = v; }
}
