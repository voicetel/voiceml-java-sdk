package com.voicetel.voiceml.resources;

import com.voicetel.voiceml.Pager;
import com.voicetel.voiceml.Transport;
import com.voicetel.voiceml.models.ListUsageRecordsParams;
import com.voicetel.voiceml.models.UsageRecord;
import com.voicetel.voiceml.models.UsageRecordList;

import java.util.Map;

/**
 * {@code /Usage/Records} and its time windows ({@code AllTime}, {@code Daily}, {@code LastMonth},
 * {@code Monthly}, {@code ThisMonth}, {@code Today}, {@code Yearly}, {@code Yesterday}). Use
 * {@code VoicemlClient.paginate(...)} to iterate a window's pages.
 */
public final class UsageRecordsResource extends BaseResource {

    /**
     * Create the resource; normally obtained from {@code client.usageRecords()}.
     *
     * @param transport the transport
     */
    public UsageRecordsResource(Transport transport) {
        super(transport);
    }

    private UsageRecordList get(String window, ListUsageRecordsParams params) {
        Map<String, Object> q = params != null ? params.toQuery() : null;
        String path = window == null ? accountPath("Usage", "Records") : accountPath("Usage", "Records", window);
        return decode(transport.request("GET", path, q, null), UsageRecordList.class);
    }

    /**
     * {@code GET /Usage/Records}.
     *
     * @param params filters and paging, or {@code null}
     * @return the page
     */
    public UsageRecordList list(ListUsageRecordsParams params) {
        return get(null, params);
    }

    /** @return the first page of usage records */
    public UsageRecordList list() {
        return list(null);
    }

    /**
     * Lazily iterate every usage record matching {@code params}.
     *
     * @param params filters and paging, or {@code null}
     * @return the pager
     */
    public Pager<UsageRecord> iterate(ListUsageRecordsParams params) {
        return pager(() -> list(params), UsageRecordList.class, UsageRecordList::getUsageRecords);
    }

    /**
     * {@code GET /Usage/Records/AllTime}.
     *
     * @param params filters and paging, or {@code null}
     * @return the page
     */
    public UsageRecordList listAllTime(ListUsageRecordsParams params) {
        return get("AllTime", params);
    }

    /** @return the first page of {@code /Usage/Records/AllTime} */
    public UsageRecordList listAllTime() {
        return listAllTime(null);
    }

    /**
     * {@code GET /Usage/Records/Daily}.
     *
     * @param params filters and paging, or {@code null}
     * @return the page
     */
    public UsageRecordList listDaily(ListUsageRecordsParams params) {
        return get("Daily", params);
    }

    /** @return the first page of {@code /Usage/Records/Daily} */
    public UsageRecordList listDaily() {
        return listDaily(null);
    }

    /**
     * {@code GET /Usage/Records/LastMonth}.
     *
     * @param params filters and paging, or {@code null}
     * @return the page
     */
    public UsageRecordList listLastMonth(ListUsageRecordsParams params) {
        return get("LastMonth", params);
    }

    /** @return the first page of {@code /Usage/Records/LastMonth} */
    public UsageRecordList listLastMonth() {
        return listLastMonth(null);
    }

    /**
     * {@code GET /Usage/Records/Monthly}.
     *
     * @param params filters and paging, or {@code null}
     * @return the page
     */
    public UsageRecordList listMonthly(ListUsageRecordsParams params) {
        return get("Monthly", params);
    }

    /** @return the first page of {@code /Usage/Records/Monthly} */
    public UsageRecordList listMonthly() {
        return listMonthly(null);
    }

    /**
     * {@code GET /Usage/Records/ThisMonth}.
     *
     * @param params filters and paging, or {@code null}
     * @return the page
     */
    public UsageRecordList listThisMonth(ListUsageRecordsParams params) {
        return get("ThisMonth", params);
    }

    /** @return the first page of {@code /Usage/Records/ThisMonth} */
    public UsageRecordList listThisMonth() {
        return listThisMonth(null);
    }

    /**
     * {@code GET /Usage/Records/Today}.
     *
     * @param params filters and paging, or {@code null}
     * @return the page
     */
    public UsageRecordList listToday(ListUsageRecordsParams params) {
        return get("Today", params);
    }

    /** @return the first page of {@code /Usage/Records/Today} */
    public UsageRecordList listToday() {
        return listToday(null);
    }

    /**
     * {@code GET /Usage/Records/Yearly}.
     *
     * @param params filters and paging, or {@code null}
     * @return the page
     */
    public UsageRecordList listYearly(ListUsageRecordsParams params) {
        return get("Yearly", params);
    }

    /** @return the first page of {@code /Usage/Records/Yearly} */
    public UsageRecordList listYearly() {
        return listYearly(null);
    }

    /**
     * {@code GET /Usage/Records/Yesterday}.
     *
     * @param params filters and paging, or {@code null}
     * @return the page
     */
    public UsageRecordList listYesterday(ListUsageRecordsParams params) {
        return get("Yesterday", params);
    }

    /** @return the first page of {@code /Usage/Records/Yesterday} */
    public UsageRecordList listYesterday() {
        return listYesterday(null);
    }
}
