package com.voicetel.voiceml.models;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Form body for {@code POST /Calls/{sid}/Siprec/{sid}}.
 *
 * <p>Clears VoiceML's session tracking only — the SRS recording itself continues until call
 * hangup (documented {@code mod_siprec} limitation).
 */
public final class StopSiprecRequest {

    private final String status;

    public StopSiprecRequest() {
        this("stopped");
    }

    public StopSiprecRequest(String status) {
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
