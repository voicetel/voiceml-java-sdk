package com.voicetel.voiceml.models;

import java.util.LinkedHashMap;
import java.util.Map;

/** Form body for {@code POST /v1/Conversations/{ConversationSid}/Participants}. */
public final class CreateConversationsV1ConversationParticipantRequest {

    private final String identity;
    private final String attributes;
    private final String roleSid;
    private final String messagingBindingAddress;
    private final String messagingBindingProxyAddress;
    private final String messagingBindingProjectedAddress;

    private CreateConversationsV1ConversationParticipantRequest(Builder b) {
        this.identity = b.identity;
        this.attributes = b.attributes;
        this.roleSid = b.roleSid;
        this.messagingBindingAddress = b.messagingBindingAddress;
        this.messagingBindingProxyAddress = b.messagingBindingProxyAddress;
        this.messagingBindingProjectedAddress = b.messagingBindingProjectedAddress;
    }

    public Map<String, Object> toForm() {
        Map<String, Object> f = new LinkedHashMap<>();
        if (identity != null) f.put("Identity", identity);
        if (attributes != null) f.put("Attributes", attributes);
        if (roleSid != null) f.put("RoleSid", roleSid);
        if (messagingBindingAddress != null) f.put("MessagingBinding.Address", messagingBindingAddress);
        if (messagingBindingProxyAddress != null) f.put("MessagingBinding.ProxyAddress", messagingBindingProxyAddress);
        if (messagingBindingProjectedAddress != null) f.put("MessagingBinding.ProjectedAddress", messagingBindingProjectedAddress);
        return f;
    }

    public static Builder builder() { return new Builder(); }

    public static final class Builder {
        private String identity;
        private String attributes;
        private String roleSid;
        private String messagingBindingAddress;
        private String messagingBindingProxyAddress;
        private String messagingBindingProjectedAddress;

        public Builder identity(String s) { this.identity = s; return this; }
        public Builder attributes(String s) { this.attributes = s; return this; }
        public Builder roleSid(String s) { this.roleSid = s; return this; }
        public Builder messagingBindingAddress(String s) { this.messagingBindingAddress = s; return this; }
        public Builder messagingBindingProxyAddress(String s) { this.messagingBindingProxyAddress = s; return this; }
        public Builder messagingBindingProjectedAddress(String s) { this.messagingBindingProjectedAddress = s; return this; }

        public CreateConversationsV1ConversationParticipantRequest build() {
            return new CreateConversationsV1ConversationParticipantRequest(this);
        }
    }
}
