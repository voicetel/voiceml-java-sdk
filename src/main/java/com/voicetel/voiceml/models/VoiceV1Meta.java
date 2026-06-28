package com.voicetel.voiceml.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Pagination envelope used by Twilio's {@code /v1/*} resources (Voice v1
 * and Conversations v1). Distinct from {@link Page} (the {@code /2010-04-01}
 * envelope) — uses {@code first_page_url}/{@code next_page_url}/etc. and
 * carries a {@code key} field naming the array property in the parent
 * response.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class VoiceV1Meta {

    @JsonProperty("first_page_url") private String firstPageUrl;
    @JsonProperty("next_page_url") private String nextPageUrl;
    @JsonProperty("previous_page_url") private String previousPageUrl;
    @JsonProperty("url") private String url;
    @JsonProperty("page") private Integer page;
    @JsonProperty("page_size") private Integer pageSize;
    @JsonProperty("key") private String key;

    public String getFirstPageUrl() { return firstPageUrl; }
    public String getNextPageUrl() { return nextPageUrl; }
    public String getPreviousPageUrl() { return previousPageUrl; }
    public String getUrl() { return url; }
    public Integer getPage() { return page; }
    public Integer getPageSize() { return pageSize; }
    public String getKey() { return key; }

    public void setFirstPageUrl(String v) { this.firstPageUrl = v; }
    public void setNextPageUrl(String v) { this.nextPageUrl = v; }
    public void setPreviousPageUrl(String v) { this.previousPageUrl = v; }
    public void setUrl(String v) { this.url = v; }
    public void setPage(Integer v) { this.page = v; }
    public void setPageSize(Integer v) { this.pageSize = v; }
    public void setKey(String v) { this.key = v; }
}
