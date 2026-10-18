package com.voicetel.voiceml.resources;

import com.voicetel.voiceml.Pager;
import com.voicetel.voiceml.Transport;
import com.voicetel.voiceml.models.ListMonitorAlertsParams;
import com.voicetel.voiceml.models.MonitorV1Alert;
import com.voicetel.voiceml.models.MonitorV1AlertList;

import java.util.Map;

/** {@code /v1/Alerts[/{Sid}]}: the Monitor view over failure events. */
public final class MonitorV1AlertsResource extends BaseResource {

    /**
     * Create the resource; normally obtained from {@code client.monitorV1().alerts()}.
     *
     * @param transport the transport
     */
    public MonitorV1AlertsResource(Transport transport) {
        super(transport);
    }

    /**
     * {@code GET /v1/Alerts}: failure events in the Alert shape, without request/response detail.
     * A single page is served; {@code PageSize} (default 50, capped at 1000) limits it, and
     * {@code meta.next_page_url} is always {@code null}.
     *
     * @param params filters ({@code LogLevel}, {@code StartDate}, {@code EndDate}) and paging, or
     *     {@code null}
     * @return the page
     */
    public MonitorV1AlertList list(ListMonitorAlertsParams params) {
        Map<String, Object> q = params != null ? params.toQuery() : null;
        return decode(transport.request("GET", "/v1/Alerts", q, null), MonitorV1AlertList.class);
    }

    /** @return the first page of alerts (no filters) */
    public MonitorV1AlertList list() {
        return list(null);
    }

    /**
     * Lazily iterate the alerts page. The server serves a single page, so the pager yields it and
     * stops; it is here so alerts compose like every other list.
     *
     * @param params filters and paging, or {@code null}
     * @return the pager
     */
    public Pager<MonitorV1Alert> iterate(ListMonitorAlertsParams params) {
        return metaPager(() -> list(params), MonitorV1AlertList.class, MonitorV1AlertList::getAlerts);
    }

    /** Lazily iterate the alerts (no filters). */
    public Pager<MonitorV1Alert> iterate() {
        return iterate(null);
    }

    /**
     * {@code GET /v1/Alerts/{Sid}}: one alert, including the stored request/response detail when
     * the row carries any.
     *
     * @param sid the Alert sid
     * @return the alert
     */
    public MonitorV1Alert fetch(String sid) {
        return decode(transport.request("GET", "/v1/Alerts/" + seg(sid), null, null), MonitorV1Alert.class);
    }
}
