package com.voicetel.voiceml.models;

import java.util.LinkedHashMap;
import java.util.Map;

/** Form body for {@code POST /Calls/{sid}/Streams/{sid}}. */
public final class StopStreamRequest {

    private final String status;

    public StopStreamRequest() {
        this("stopped");
    }

    public StopStreamRequest(String status) {
        this.status = status;
    }

    public Map<String, Object> toForm() {
        Map<String, Object> form = new LinkedHashMap<>();
        form.put("Status", status != null ? status : "stopped");
        return form;
    }

    public String getStatus() {
        return status;
    }
}
