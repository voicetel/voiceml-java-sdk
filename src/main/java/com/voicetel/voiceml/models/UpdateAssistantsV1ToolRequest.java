package com.voicetel.voiceml.models;

import java.util.LinkedHashMap;
import java.util.Map;

/** JSON body for {@code PUT /v1/Tools/{id}}. All fields optional. */
public final class UpdateAssistantsV1ToolRequest {

    private final String name;
    private final String type;
    private final Boolean enabled;
    private final String description;
    private final Map<String, Object> meta;

    private UpdateAssistantsV1ToolRequest(Builder b) {
        this.name = b.name;
        this.type = b.type;
        this.enabled = b.enabled;
        this.description = b.description;
        this.meta = b.meta;
    }

    public Map<String, Object> toJsonBody() {
        Map<String, Object> m = new LinkedHashMap<>();
        if (name != null) m.put("name", name);
        if (type != null) m.put("type", type);
        if (enabled != null) m.put("enabled", enabled);
        if (description != null) m.put("description", description);
        if (meta != null) m.put("meta", meta);
        return m;
    }

    public static Builder builder() { return new Builder(); }

    public static final class Builder {
        private String name;
        private String type;
        private Boolean enabled;
        private String description;
        private Map<String, Object> meta;

        public Builder name(String s) { this.name = s; return this; }
        public Builder type(String s) { this.type = s; return this; }
        public Builder enabled(Boolean v) { this.enabled = v; return this; }
        public Builder description(String s) { this.description = s; return this; }
        public Builder meta(Map<String, Object> v) { this.meta = v; return this; }

        public UpdateAssistantsV1ToolRequest build() {
            return new UpdateAssistantsV1ToolRequest(this);
        }
    }
}
