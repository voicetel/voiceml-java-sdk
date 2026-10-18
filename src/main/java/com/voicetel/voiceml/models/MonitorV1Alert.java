package com.voicetel.voiceml.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/** A Monitor v1 Alert: a failure event ({@code GET /v1/Alerts}). */
@JsonIgnoreProperties(ignoreUnknown = true)
public class MonitorV1Alert {

    @JsonProperty("sid")
    private String sid;

    @JsonProperty("account_sid")
    private String accountSid;

    @JsonProperty("alert_text")
    private String alertText;

    @JsonProperty("api_version")
    private String apiVersion;

    @JsonProperty("date_created")
    private String dateCreated;

    @JsonProperty("date_generated")
    private String dateGenerated;

    @JsonProperty("date_updated")
    private String dateUpdated;

    @JsonProperty("error_code")
    private String errorCode;

    @JsonProperty("log_level")
    private String logLevel;

    @JsonProperty("more_info")
    private String moreInfo;

    @JsonProperty("request_method")
    private String requestMethod;

    @JsonProperty("request_url")
    private String requestUrl;

    @JsonProperty("resource_sid")
    private String resourceSid;

    @JsonProperty("service_sid")
    private String serviceSid;

    @JsonProperty("url")
    private String url;

    @JsonProperty("request_headers")
    private String requestHeaders;

    @JsonProperty("request_variables")
    private String requestVariables;

    @JsonProperty("response_body")
    private String responseBody;

    @JsonProperty("response_headers")
    private String responseHeaders;

    /** @return the {@code sid} field */
    public String getSid() {
        return sid;
    }

    /** @param sid the {@code sid} field */
    public void setSid(String sid) {
        this.sid = sid;
    }

    /** @return the {@code account_sid} field */
    public String getAccountSid() {
        return accountSid;
    }

    /** @param accountSid the {@code account_sid} field */
    public void setAccountSid(String accountSid) {
        this.accountSid = accountSid;
    }

    /** @return the {@code alert_text} field */
    public String getAlertText() {
        return alertText;
    }

    /** @param alertText the {@code alert_text} field */
    public void setAlertText(String alertText) {
        this.alertText = alertText;
    }

    /** @return the {@code api_version} field (always {@code null}) */
    public String getApiVersion() {
        return apiVersion;
    }

    /** @param apiVersion the {@code api_version} field */
    public void setApiVersion(String apiVersion) {
        this.apiVersion = apiVersion;
    }

    /** @return the {@code date_created} field */
    public String getDateCreated() {
        return dateCreated;
    }

    /** @param dateCreated the {@code date_created} field */
    public void setDateCreated(String dateCreated) {
        this.dateCreated = dateCreated;
    }

    /** @return the {@code date_generated} field */
    public String getDateGenerated() {
        return dateGenerated;
    }

    /** @param dateGenerated the {@code date_generated} field */
    public void setDateGenerated(String dateGenerated) {
        this.dateGenerated = dateGenerated;
    }

    /** @return the {@code date_updated} field */
    public String getDateUpdated() {
        return dateUpdated;
    }

    /** @param dateUpdated the {@code date_updated} field */
    public void setDateUpdated(String dateUpdated) {
        this.dateUpdated = dateUpdated;
    }

    /** @return the {@code error_code} field (numeric code, as a string) */
    public String getErrorCode() {
        return errorCode;
    }

    /** @param errorCode the {@code error_code} field */
    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    /** @return the {@code log_level} field ({@code error} or {@code warning}) */
    public String getLogLevel() {
        return logLevel;
    }

    /** @param logLevel the {@code log_level} field */
    public void setLogLevel(String logLevel) {
        this.logLevel = logLevel;
    }

    /** @return the {@code more_info} field */
    public String getMoreInfo() {
        return moreInfo;
    }

    /** @param moreInfo the {@code more_info} field */
    public void setMoreInfo(String moreInfo) {
        this.moreInfo = moreInfo;
    }

    /** @return the {@code request_method} field */
    public String getRequestMethod() {
        return requestMethod;
    }

    /** @param requestMethod the {@code request_method} field */
    public void setRequestMethod(String requestMethod) {
        this.requestMethod = requestMethod;
    }

    /** @return the {@code request_url} field */
    public String getRequestUrl() {
        return requestUrl;
    }

    /** @param requestUrl the {@code request_url} field */
    public void setRequestUrl(String requestUrl) {
        this.requestUrl = requestUrl;
    }

    /** @return the {@code resource_sid} field (message or call sid, {@code null} when neither) */
    public String getResourceSid() {
        return resourceSid;
    }

    /** @param resourceSid the {@code resource_sid} field */
    public void setResourceSid(String resourceSid) {
        this.resourceSid = resourceSid;
    }

    /** @return the {@code service_sid} field (set only for message-linked rows) */
    public String getServiceSid() {
        return serviceSid;
    }

    /** @param serviceSid the {@code service_sid} field */
    public void setServiceSid(String serviceSid) {
        this.serviceSid = serviceSid;
    }

    /** @return the {@code url} field */
    public String getUrl() {
        return url;
    }

    /** @param url the {@code url} field */
    public void setUrl(String url) {
        this.url = url;
    }

    /** @return the {@code request_headers} field (fetch only, when stored) */
    public String getRequestHeaders() {
        return requestHeaders;
    }

    /** @param requestHeaders the {@code request_headers} field */
    public void setRequestHeaders(String requestHeaders) {
        this.requestHeaders = requestHeaders;
    }

    /** @return the {@code request_variables} field (fetch only, when stored) */
    public String getRequestVariables() {
        return requestVariables;
    }

    /** @param requestVariables the {@code request_variables} field */
    public void setRequestVariables(String requestVariables) {
        this.requestVariables = requestVariables;
    }

    /** @return the {@code response_body} field (fetch only, when stored) */
    public String getResponseBody() {
        return responseBody;
    }

    /** @param responseBody the {@code response_body} field */
    public void setResponseBody(String responseBody) {
        this.responseBody = responseBody;
    }

    /** @return the {@code response_headers} field (fetch only, when stored) */
    public String getResponseHeaders() {
        return responseHeaders;
    }

    /** @param responseHeaders the {@code response_headers} field */
    public void setResponseHeaders(String responseHeaders) {
        this.responseHeaders = responseHeaders;
    }
}
