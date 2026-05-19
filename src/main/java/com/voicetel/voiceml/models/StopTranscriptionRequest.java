package com.voicetel.voiceml.models;

import java.util.LinkedHashMap;
import java.util.Map;

/** Form body for {@code POST /Calls/{sid}/Transcriptions/{sid}}. */
public final class StopTranscriptionRequest {

    private final String status;

    public StopTranscriptionRequest() {
        this("stopped");
    }

    public StopTranscriptionRequest(String status) {
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
