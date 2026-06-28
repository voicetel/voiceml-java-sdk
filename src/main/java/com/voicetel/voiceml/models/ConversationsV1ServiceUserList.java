package com.voicetel.voiceml.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;

/** Paginated service-scoped Users response. */
@JsonIgnoreProperties(ignoreUnknown = true)
public class ConversationsV1ServiceUserList {

    @JsonProperty("users")
    private List<ConversationsV1ServiceUser> users = new ArrayList<>();

    @JsonProperty("meta")
    private VoiceV1Meta meta;

    public List<ConversationsV1ServiceUser> getUsers() { return users; }
    public VoiceV1Meta getMeta() { return meta; }

    public void setUsers(List<ConversationsV1ServiceUser> v) {
        this.users = v != null ? v : new ArrayList<>();
    }
    public void setMeta(VoiceV1Meta v) { this.meta = v; }
}
