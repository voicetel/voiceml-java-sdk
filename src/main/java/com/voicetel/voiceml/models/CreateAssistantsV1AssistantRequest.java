package com.voicetel.voiceml.models;

import java.util.LinkedHashMap;
import java.util.Map;

/** JSON body for {@code POST /v1/Assistants}. {@code name} is required. */
public final class CreateAssistantsV1AssistantRequest {

    private final String name;
    private final String owner;
    private final String personalityPrompt;
    private final String model;
    private final Map<String, Object> customerAi;
    private final Map<String, Object> segmentCredential;

    private CreateAssistantsV1AssistantRequest(Builder b) {
        this.name = b.name;
        this.owner = b.owner;
        this.personalityPrompt = b.personalityPrompt;
        this.model = b.model;
        this.customerAi = b.customerAi;
        this.segmentCredential = b.segmentCredential;
    }

    public Map<String, Object> toJsonBody() {
        Map<String, Object> m = new LinkedHashMap<>();
        if (name != null) m.put("name", name);
        if (owner != null) m.put("owner", owner);
        if (personalityPrompt != null) m.put("personality_prompt", personalityPrompt);
        if (model != null) m.put("model", model);
        if (customerAi != null) m.put("customer_ai", customerAi);
        if (segmentCredential != null) m.put("segment_credential", segmentCredential);
        return m;
    }

    public static Builder builder() { return new Builder(); }

    public static final class Builder {
        private String name;
        private String owner;
        private String personalityPrompt;
        private String model;
        private Map<String, Object> customerAi;
        private Map<String, Object> segmentCredential;

        public Builder name(String s) { this.name = s; return this; }
        public Builder owner(String s) { this.owner = s; return this; }
        public Builder personalityPrompt(String s) { this.personalityPrompt = s; return this; }
        public Builder model(String s) { this.model = s; return this; }
        public Builder customerAi(Map<String, Object> v) { this.customerAi = v; return this; }
        public Builder segmentCredential(Map<String, Object> v) { this.segmentCredential = v; return this; }

        public CreateAssistantsV1AssistantRequest build() {
            return new CreateAssistantsV1AssistantRequest(this);
        }
    }
}
