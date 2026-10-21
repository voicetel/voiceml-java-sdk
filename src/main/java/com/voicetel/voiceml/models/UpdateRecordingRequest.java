package com.voicetel.voiceml.models;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Form body for {@code POST /Calls/{sid}/Recordings/{rsid}} and
 * {@code POST /Conferences/{sid}/Recordings/{rsid}} — stop / pause / resume, with an optional
 * {@code PauseBehavior} ({@code skip} or {@code silence}).
 */
public final class UpdateRecordingRequest {

    private final String status;
    private final String pauseBehavior;

    public UpdateRecordingRequest(String status) {
        this(status, null);
    }

    public UpdateRecordingRequest(String status, String pauseBehavior) {
        this.status = status;
        this.pauseBehavior = pauseBehavior;
    }

    public Map<String, Object> toForm() {
        Map<String, Object> form = new LinkedHashMap<>();
        if (status != null) form.put("Status", status);
        if (pauseBehavior != null) form.put("PauseBehavior", pauseBehavior);
        return form;
    }

    public String getStatus() {
        return status;
    }

    /** @return the {@code PauseBehavior} value, or {@code null} when unset */
    public String getPauseBehavior() {
        return pauseBehavior;
    }
}
