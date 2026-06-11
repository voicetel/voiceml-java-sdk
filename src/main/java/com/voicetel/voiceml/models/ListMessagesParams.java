package com.voicetel.voiceml.models;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Query-string parameters for {@code GET /Messages}.
 *
 * <p>Twilio's documented filter set: {@code To}, {@code From}, {@code DateSent}, plus the
 * {@code DateSent<} / {@code DateSent>} comparators. Pagination via {@code Page} / {@code PageSize}
 * / {@code PageToken}.
 */
public final class ListMessagesParams {

    private final String to;
    private final String from;
    private final String dateSent;
    private final String dateSentLt;
    private final String dateSentGt;
    private final Integer page;
    private final Integer pageSize;
    private final String pageToken;

    private ListMessagesParams(Builder b) {
        this.to = b.to;
        this.from = b.from;
        this.dateSent = b.dateSent;
        this.dateSentLt = b.dateSentLt;
        this.dateSentGt = b.dateSentGt;
        this.page = b.page;
        this.pageSize = b.pageSize;
        this.pageToken = b.pageToken;
    }

    public Map<String, Object> toQuery() {
        Map<String, Object> q = new LinkedHashMap<>();
        if (to != null) q.put("To", to);
        if (from != null) q.put("From", from);
        if (dateSent != null) q.put("DateSent", dateSent);
        if (dateSentLt != null) q.put("DateSent<", dateSentLt);
        if (dateSentGt != null) q.put("DateSent>", dateSentGt);
        if (page != null) q.put("Page", page);
        if (pageSize != null) q.put("PageSize", pageSize);
        if (pageToken != null) q.put("PageToken", pageToken);
        return q;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder {
        private String to;
        private String from;
        private String dateSent;
        private String dateSentLt;
        private String dateSentGt;
        private Integer page;
        private Integer pageSize;
        private String pageToken;

        public Builder to(String s) { this.to = s; return this; }
        public Builder from(String s) { this.from = s; return this; }
        public Builder dateSent(String s) { this.dateSent = s; return this; }
        /** Exclusive upper bound — wire name {@code DateSent<}. */
        public Builder dateSentLt(String s) { this.dateSentLt = s; return this; }
        /** Exclusive lower bound — wire name {@code DateSent>}. */
        public Builder dateSentGt(String s) { this.dateSentGt = s; return this; }
        public Builder page(Integer v) { this.page = v; return this; }
        public Builder pageSize(Integer v) { this.pageSize = v; return this; }
        public Builder pageToken(String s) { this.pageToken = s; return this; }

        public ListMessagesParams build() {
            return new ListMessagesParams(this);
        }
    }
}
