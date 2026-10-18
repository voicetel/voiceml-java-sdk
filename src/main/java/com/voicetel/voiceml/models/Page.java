package com.voicetel.voiceml.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Twilio-compatible pagination envelope of the {@code /2010-04-01} lists.
 *
 * <p>Subclasses bind a concrete list field with the resource-specific name
 * ({@code calls}, {@code conferences}, {@code recordings}, ...); this base only carries the
 * pagination metadata. {@link #getNextPageUri()} is a relative URI (path and query, including any
 * {@code PageToken}) that the SDK's iterators and {@code VoicemlClient.nextPage(...)} follow
 * verbatim. The server does not report a total count.
 *
 * <p>The default {@code @JsonIgnoreProperties(ignoreUnknown = true)} on this base type is
 * inherited by subclasses, so the server can add new fields without breaking deserialization.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public abstract class Page {

    @JsonProperty("page")
    private Integer page;

    @JsonProperty("page_size")
    private Integer pageSize;

    @JsonProperty("start")
    private Integer start;

    @JsonProperty("end")
    private Integer end;

    @JsonProperty("first_page_uri")
    private String firstPageUri;

    @JsonProperty("next_page_uri")
    private String nextPageUri;

    @JsonProperty("previous_page_uri")
    private String previousPageUri;

    @JsonProperty("uri")
    private String uri;

    /** @return zero-based page number */
    public Integer getPage() {
        return page;
    }

    /** @param page zero-based page number */
    public void setPage(Integer page) {
        this.page = page;
    }

    /** @return page size used for this page */
    public Integer getPageSize() {
        return pageSize;
    }

    /** @param pageSize page size used for this page */
    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    /** @return index of the first item on this page */
    public Integer getStart() {
        return start;
    }

    /** @param start index of the first item on this page */
    public void setStart(Integer start) {
        this.start = start;
    }

    /** @return index of the last item on this page */
    public Integer getEnd() {
        return end;
    }

    /** @param end index of the last item on this page */
    public void setEnd(Integer end) {
        this.end = end;
    }

    /** @return relative URI of the first page */
    public String getFirstPageUri() {
        return firstPageUri;
    }

    /** @param firstPageUri relative URI of the first page */
    public void setFirstPageUri(String firstPageUri) {
        this.firstPageUri = firstPageUri;
    }

    /** @return relative URI of the next page, or {@code null} on the last page */
    public String getNextPageUri() {
        return nextPageUri;
    }

    /** @param nextPageUri relative URI of the next page */
    public void setNextPageUri(String nextPageUri) {
        this.nextPageUri = nextPageUri;
    }

    /** @return relative URI of the previous page, or {@code null} on the first page */
    public String getPreviousPageUri() {
        return previousPageUri;
    }

    /** @param previousPageUri relative URI of the previous page */
    public void setPreviousPageUri(String previousPageUri) {
        this.previousPageUri = previousPageUri;
    }

    /** @return relative URI of this page */
    public String getUri() {
        return uri;
    }

    /** @param uri relative URI of this page */
    public void setUri(String uri) {
        this.uri = uri;
    }

    /** @return whether {@link #getNextPageUri()} points at another page */
    @JsonIgnore
    public boolean hasNextPage() {
        return nextPageUri != null && !nextPageUri.isEmpty();
    }
}
