package com.voicetel.voiceml.resources;

import com.fasterxml.jackson.core.type.TypeReference;
import com.voicetel.voiceml.Transport;
import com.voicetel.voiceml.models.ListNotificationsParams;
import com.voicetel.voiceml.models.NotificationsList;

import java.util.Map;

/** Account-scoped {@code /Notifications} compat stubs (always empty list; fetch returns 404). */
public final class NotificationsResource extends BaseResource {

    public NotificationsResource(Transport transport) {
        super(transport);
    }

    public NotificationsList list(ListNotificationsParams params) {
        Map<String, Object> q = params != null ? params.toQuery() : null;
        return decode(
                transport.request("GET", accountPath("Notifications"), q, null),
                NotificationsList.class);
    }

    public NotificationsList list() {
        return list(null);
    }

    public Map<String, Object> get(String notificationSid) {
        return decode(
                transport.request("GET", accountPath("Notifications", notificationSid), null, null),
                new TypeReference<Map<String, Object>>() {});
    }
}
