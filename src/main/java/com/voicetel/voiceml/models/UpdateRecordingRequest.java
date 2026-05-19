package com.voicetel.voiceml.models;

import java.util.LinkedHashMap;
import java.util.Map;

/** Form body for {@code POST /Calls/{sid}/Recordings/{rsid}} — stop / pause / resume. */
public final class UpdateRecordingRequest {

    private final String status;

    public UpdateRecordingRequest(String status) {
        this.status = status;
    }

    public Map<String, Object> toForm() {
        Map<String, Object> form = new LinkedHashMap<>();
        if (status != null) form.put("Status", status);
        return form;
    }

    public String getStatus() {
        return status;
    }
}
