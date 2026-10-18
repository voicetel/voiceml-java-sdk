package com.voicetel.voiceml.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/** A call notification (error or warning log entry). */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Notification {

    @JsonProperty("sid")
    private String sid;

    @JsonProperty("account_sid")
    private String accountSid;

    @JsonProperty("call_sid")
    private String callSid;

    @JsonProperty("api_version")
    private String apiVersion;

    @JsonProperty("error_code")
    private String errorCode;

    @JsonProperty("log")
    private String log;

    @JsonProperty("message_date")
    private String messageDate;

    @JsonProperty("message_text")
    private String messageText;

    @JsonProperty("more_info")
    private String moreInfo;

    @JsonProperty("request_method")
    private String requestMethod;

    @JsonProperty("request_url")
    private String requestUrl;

    @JsonProperty("uri")
    private String uri;

    @JsonProperty("date_created")
    private String dateCreated;

    @JsonProperty("date_updated")
    private String dateUpdated;

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

    /** @return the {@code call_sid} field */
    public String getCallSid() {
        return callSid;
    }

    /** @param callSid the {@code call_sid} field */
    public void setCallSid(String callSid) {
        this.callSid = callSid;
    }

    /** @return the {@code api_version} field */
    public String getApiVersion() {
        return apiVersion;
    }

    /** @param apiVersion the {@code api_version} field */
    public void setApiVersion(String apiVersion) {
        this.apiVersion = apiVersion;
    }

    /** @return the {@code error_code} field */
    public String getErrorCode() {
        return errorCode;
    }

    /** @param errorCode the {@code error_code} field */
    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    /** @return the {@code log} field */
    public String getLog() {
        return log;
    }

    /** @param log the {@code log} field */
    public void setLog(String log) {
        this.log = log;
    }

    /** @return the {@code message_date} field */
    public String getMessageDate() {
        return messageDate;
    }

    /** @param messageDate the {@code message_date} field */
    public void setMessageDate(String messageDate) {
        this.messageDate = messageDate;
    }

    /** @return the {@code message_text} field */
    public String getMessageText() {
        return messageText;
    }

    /** @param messageText the {@code message_text} field */
    public void setMessageText(String messageText) {
        this.messageText = messageText;
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

    /** @return the {@code uri} field */
    public String getUri() {
        return uri;
    }

    /** @param uri the {@code uri} field */
    public void setUri(String uri) {
        this.uri = uri;
    }

    /** @return the {@code date_created} field */
    public String getDateCreated() {
        return dateCreated;
    }

    /** @param dateCreated the {@code date_created} field */
    public void setDateCreated(String dateCreated) {
        this.dateCreated = dateCreated;
    }

    /** @return the {@code date_updated} field */
    public String getDateUpdated() {
        return dateUpdated;
    }

    /** @param dateUpdated the {@code date_updated} field */
    public void setDateUpdated(String dateUpdated) {
        this.dateUpdated = dateUpdated;
    }
}
