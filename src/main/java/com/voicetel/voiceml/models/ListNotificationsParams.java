package com.voicetel.voiceml.models;

import java.util.LinkedHashMap;
import java.util.Map;

/** Query params for {@code GET /Notifications} and {@code GET /Calls/{sid}/Notifications}. */
public final class ListNotificationsParams {

    private final Integer page;
    private final Integer pageSize;
    private final String pageToken;
    private final Integer log;
    private final String messageDate;
    private final String messageDateLt;
    private final String messageDateGt;

    private ListNotificationsParams(Builder b) {
        this.page = b.page;
        this.pageSize = b.pageSize;
        this.pageToken = b.pageToken;
        this.log = b.log;
        this.messageDate = b.messageDate;
        this.messageDateLt = b.messageDateLt;
        this.messageDateGt = b.messageDateGt;
    }

    public Map<String, Object> toQuery() {
        Map<String, Object> q = new LinkedHashMap<>();
        if (page != null) q.put("Page", page);
        if (pageSize != null) q.put("PageSize", pageSize);
        if (pageToken != null) q.put("PageToken", pageToken);
        if (log != null) q.put("Log", log);
        if (messageDate != null) q.put("MessageDate", messageDate);
        if (messageDateLt != null) q.put("MessageDate<", messageDateLt);
        if (messageDateGt != null) q.put("MessageDate>", messageDateGt);
        return q;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder {
        private Integer page;
        private Integer pageSize;
        private String pageToken;
        private Integer log;
        private String messageDate;
        private String messageDateLt;
        private String messageDateGt;

        public Builder page(Integer v) { this.page = v; return this; }
        public Builder pageSize(Integer v) { this.pageSize = v; return this; }
        public Builder pageToken(String v) { this.pageToken = v; return this; }
        public Builder log(Integer v) { this.log = v; return this; }
        public Builder messageDate(String v) { this.messageDate = v; return this; }
        public Builder messageDateLt(String v) { this.messageDateLt = v; return this; }
        public Builder messageDateGt(String v) { this.messageDateGt = v; return this; }

        public ListNotificationsParams build() {
            return new ListNotificationsParams(this);
        }
    }
}
