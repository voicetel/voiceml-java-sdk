package com.voicetel.voiceml.models;

import java.util.LinkedHashMap;
import java.util.Map;

/** Form body for {@code POST /Conferences/{sid}}. v1 supports only {@code Status=completed}. */
public final class EndConferenceRequest {

    private final String status;

    public EndConferenceRequest() {
        this("completed");
    }

    public EndConferenceRequest(String status) {
        this.status = status;
    }

    public Map<String, Object> toForm() {
        Map<String, Object> form = new LinkedHashMap<>();
        form.put("Status", status != null ? status : "completed");
        return form;
    }

    public String getStatus() {
        return status;
    }
}
