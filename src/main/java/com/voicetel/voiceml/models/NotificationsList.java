package com.voicetel.voiceml.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;

/** Paginated {@code GET /Notifications} or {@code GET /Calls/{CallSid}/Notifications} response. */
@JsonIgnoreProperties(ignoreUnknown = true)
public class NotificationsList extends Page {

    @JsonProperty("notifications")
    private List<Notification> notifications = new ArrayList<>();

    /** @return the {@code notifications} field */
    public List<Notification> getNotifications() {
        return notifications;
    }

    /** @param notifications the {@code notifications} field */
    public void setNotifications(List<Notification> notifications) {
        this.notifications = notifications;
    }
}
