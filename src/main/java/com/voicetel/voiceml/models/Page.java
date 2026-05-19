package com.voicetel.voiceml.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Twilio-shape pagination envelope.
 *
 * <p>Subclasses bind a concrete list field with the resource-specific name
 * ({@code calls}, {@code conferences}, {@code recordings}, ...) — this base only carries the
 * pagination metadata. The concrete list is what callers iterate; {@link #getNextPageUri()}
 * drives auto-pagination helpers.
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

    @JsonProperty("num_pages")
    private Integer numPages;

    @JsonProperty("total")
    private Integer total;

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

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public Integer getNumPages() {
        return numPages;
    }

    public void setNumPages(Integer numPages) {
        this.numPages = numPages;
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    public Integer getStart() {
        return start;
    }

    public void setStart(Integer start) {
        this.start = start;
    }

    public Integer getEnd() {
        return end;
    }

    public void setEnd(Integer end) {
        this.end = end;
    }

    public String getFirstPageUri() {
        return firstPageUri;
    }

    public void setFirstPageUri(String firstPageUri) {
        this.firstPageUri = firstPageUri;
    }

    public String getNextPageUri() {
        return nextPageUri;
    }

    public void setNextPageUri(String nextPageUri) {
        this.nextPageUri = nextPageUri;
    }

    public String getPreviousPageUri() {
        return previousPageUri;
    }

    public void setPreviousPageUri(String previousPageUri) {
        this.previousPageUri = previousPageUri;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }
}
