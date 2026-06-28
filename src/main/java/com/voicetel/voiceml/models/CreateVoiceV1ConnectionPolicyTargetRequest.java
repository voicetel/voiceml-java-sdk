package com.voicetel.voiceml.models;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Form body for {@code POST /v1/ConnectionPolicies/{ConnectionPolicySid}/Targets}.
 * {@code target} is required (SIP URI).
 */
public final class CreateVoiceV1ConnectionPolicyTargetRequest {

    private final String target;
    private final String friendlyName;
    private final Integer priority;
    private final Integer weight;
    private final Boolean enabled;

    private CreateVoiceV1ConnectionPolicyTargetRequest(Builder b) {
        this.target = b.target;
        this.friendlyName = b.friendlyName;
        this.priority = b.priority;
        this.weight = b.weight;
        this.enabled = b.enabled;
    }

    public Map<String, Object> toForm() {
        Map<String, Object> f = new LinkedHashMap<>();
        if (target != null) f.put("Target", target);
        if (friendlyName != null) f.put("FriendlyName", friendlyName);
        if (priority != null) f.put("Priority", priority);
        if (weight != null) f.put("Weight", weight);
        if (enabled != null) f.put("Enabled", enabled);
        return f;
    }

    public static Builder builder() { return new Builder(); }

    public static final class Builder {
        private String target;
        private String friendlyName;
        private Integer priority;
        private Integer weight;
        private Boolean enabled;

        public Builder target(String s) { this.target = s; return this; }
        public Builder friendlyName(String s) { this.friendlyName = s; return this; }
        public Builder priority(Integer v) { this.priority = v; return this; }
        public Builder weight(Integer v) { this.weight = v; return this; }
        public Builder enabled(Boolean v) { this.enabled = v; return this; }

        public CreateVoiceV1ConnectionPolicyTargetRequest build() {
            return new CreateVoiceV1ConnectionPolicyTargetRequest(this);
        }
    }
}
