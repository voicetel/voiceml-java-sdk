package com.voicetel.voiceml.resources;

import com.voicetel.voiceml.Transport;

/**
 * The Monitor v1 API ({@code /v1/*} on the default host): the operational view over failure
 * events.
 *
 * <ul>
 *   <li>{@link #alerts()} — {@code /v1/Alerts} list and fetch.</li>
 * </ul>
 */
public final class MonitorV1Resource {

    private final MonitorV1AlertsResource alerts;

    public MonitorV1Resource(Transport transport) {
        this.alerts = new MonitorV1AlertsResource(transport);
    }

    /** @return {@code /v1/Alerts} list and fetch */
    public MonitorV1AlertsResource alerts() {
        return alerts;
    }
}
