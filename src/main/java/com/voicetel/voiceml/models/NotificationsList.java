package com.voicetel.voiceml.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;

/** {@code GET /Calls/{sid}/Notifications} — always an empty list (compat stub). */
@JsonIgnoreProperties(ignoreUnknown = true)
public class NotificationsList {

    @JsonProperty("notifications")
    private List<Object> notifications = new ArrayList<>();

    @JsonProperty("page")
    private Integer page;

    @JsonProperty("page_size")
    private Integer pageSize;

    @JsonProperty("total")
    private Integer total;

    @JsonProperty("uri")
    private String uri;

    public List<Object> getNotifications() { return notifications; }
    public void setNotifications(List<Object> notifications) { this.notifications = notifications; }
    public Integer getPage() { return page; }
    public void setPage(Integer page) { this.page = page; }
    public Integer getPageSize() { return pageSize; }
    public void setPageSize(Integer pageSize) { this.pageSize = pageSize; }
    public Integer getTotal() { return total; }
    public void setTotal(Integer total) { this.total = total; }
    public String getUri() { return uri; }
    public void setUri(String uri) { this.uri = uri; }
}
