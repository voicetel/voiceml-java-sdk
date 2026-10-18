package com.voicetel.voiceml.resources;

import com.voicetel.voiceml.Pager;
import com.voicetel.voiceml.Transport;
import com.voicetel.voiceml.models.ListNotificationsParams;
import com.voicetel.voiceml.models.Notification;
import com.voicetel.voiceml.models.NotificationsList;

import java.util.Map;

/** Account-scoped {@code /Notifications}: error and warning log entries. */
public final class NotificationsResource extends BaseResource {

    /**
     * Create the resource; normally obtained from {@code client.notifications()}.
     *
     * @param transport the transport
     */
    public NotificationsResource(Transport transport) {
        super(transport);
    }

    /**
     * {@code GET /Notifications}.
     *
     * @param params filters and paging, or {@code null}
     * @return the page
     */
    public NotificationsList list(ListNotificationsParams params) {
        Map<String, Object> q = params != null ? params.toQuery() : null;
        return decode(
                transport.request("GET", accountPath("Notifications"), q, null),
                NotificationsList.class);
    }

    /** @return the first page of notifications */
    public NotificationsList list() {
        return list(null);
    }

    /**
     * Lazily iterate every notification matching {@code params}.
     *
     * @param params filters and paging, or {@code null}
     * @return the pager
     */
    public Pager<Notification> iterate(ListNotificationsParams params) {
        return pager(() -> list(params), NotificationsList.class, NotificationsList::getNotifications);
    }

    /**
     * {@code GET /Notifications/{Sid}}.
     *
     * @param notificationSid the notification SID
     * @return the notification
     */
    public Notification get(String notificationSid) {
        return decode(
                transport.request("GET", accountPath("Notifications", notificationSid), null, null),
                Notification.class);
    }
}
