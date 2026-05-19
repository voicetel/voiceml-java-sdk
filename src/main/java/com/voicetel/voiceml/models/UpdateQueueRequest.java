package com.voicetel.voiceml.models;

import java.util.LinkedHashMap;
import java.util.Map;

/** Form body for {@code POST /Queues/{sid}}. */
public final class UpdateQueueRequest {

    private final String friendlyName;
    private final Integer maxSize;

    private UpdateQueueRequest(Builder b) {
        this.friendlyName = b.friendlyName;
        this.maxSize = b.maxSize;
    }

    public Map<String, Object> toForm() {
        Map<String, Object> form = new LinkedHashMap<>();
        if (friendlyName != null) form.put("FriendlyName", friendlyName);
        if (maxSize != null) form.put("MaxSize", maxSize);
        return form;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder {
        private String friendlyName;
        private Integer maxSize;

        public Builder friendlyName(String s) { this.friendlyName = s; return this; }
        public Builder maxSize(Integer v) { this.maxSize = v; return this; }

        public UpdateQueueRequest build() {
            return new UpdateQueueRequest(this);
        }
    }
}
