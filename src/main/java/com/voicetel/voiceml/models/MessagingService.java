package com.voicetel.voiceml.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * A Messaging Service — Twilio {@code MG…} resource, served on
 * {@code messaging.voicetel.com/v1/Services}.
 *
 * <p>The various feature-toggle fields ({@code stickySender}, {@code mmsConverter}, …) are
 * accept-and-echo on VoiceML; the service's operative role is gating scheduled sends (a real
 * {@code messagingServiceSid} is required on {@code POST /Messages} when
 * {@code sendAt}/{@code scheduleType} is set).
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class MessagingService {

    @JsonProperty("sid") private String sid;
    @JsonProperty("account_sid") private String accountSid;
    @JsonProperty("friendly_name") private String friendlyName;
    @JsonProperty("date_created") private String dateCreated;
    @JsonProperty("date_updated") private String dateUpdated;
    @JsonProperty("inbound_request_url") private String inboundRequestUrl;
    @JsonProperty("inbound_method") private String inboundMethod;
    @JsonProperty("fallback_url") private String fallbackUrl;
    @JsonProperty("fallback_method") private String fallbackMethod;
    @JsonProperty("status_callback") private String statusCallback;
    @JsonProperty("sticky_sender") private Boolean stickySender;
    @JsonProperty("mms_converter") private Boolean mmsConverter;
    @JsonProperty("smart_encoding") private Boolean smartEncoding;
    @JsonProperty("scan_message_content") private String scanMessageContent;
    @JsonProperty("fallback_to_long_code") private Boolean fallbackToLongCode;
    @JsonProperty("area_code_geomatch") private Boolean areaCodeGeomatch;
    @JsonProperty("synchronous_validation") private Boolean synchronousValidation;
    @JsonProperty("validity_period") private Integer validityPeriod;
    @JsonProperty("url") private String url;
    @JsonProperty("usecase") private String usecase;
    @JsonProperty("use_inbound_webhook_on_number") private Boolean useInboundWebhookOnNumber;

    public String getSid() { return sid; }
    public String getAccountSid() { return accountSid; }
    public String getFriendlyName() { return friendlyName; }
    public String getDateCreated() { return dateCreated; }
    public String getDateUpdated() { return dateUpdated; }
    public String getInboundRequestUrl() { return inboundRequestUrl; }
    public String getInboundMethod() { return inboundMethod; }
    public String getFallbackUrl() { return fallbackUrl; }
    public String getFallbackMethod() { return fallbackMethod; }
    public String getStatusCallback() { return statusCallback; }
    public Boolean getStickySender() { return stickySender; }
    public Boolean getMmsConverter() { return mmsConverter; }
    public Boolean getSmartEncoding() { return smartEncoding; }
    public String getScanMessageContent() { return scanMessageContent; }
    public Boolean getFallbackToLongCode() { return fallbackToLongCode; }
    public Boolean getAreaCodeGeomatch() { return areaCodeGeomatch; }
    public Boolean getSynchronousValidation() { return synchronousValidation; }
    public Integer getValidityPeriod() { return validityPeriod; }
    public String getUrl() { return url; }
    public String getUsecase() { return usecase; }
    public Boolean getUseInboundWebhookOnNumber() { return useInboundWebhookOnNumber; }

    public void setSid(String v) { this.sid = v; }
    public void setAccountSid(String v) { this.accountSid = v; }
    public void setFriendlyName(String v) { this.friendlyName = v; }
    public void setDateCreated(String v) { this.dateCreated = v; }
    public void setDateUpdated(String v) { this.dateUpdated = v; }
    public void setInboundRequestUrl(String v) { this.inboundRequestUrl = v; }
    public void setInboundMethod(String v) { this.inboundMethod = v; }
    public void setFallbackUrl(String v) { this.fallbackUrl = v; }
    public void setFallbackMethod(String v) { this.fallbackMethod = v; }
    public void setStatusCallback(String v) { this.statusCallback = v; }
    public void setStickySender(Boolean v) { this.stickySender = v; }
    public void setMmsConverter(Boolean v) { this.mmsConverter = v; }
    public void setSmartEncoding(Boolean v) { this.smartEncoding = v; }
    public void setScanMessageContent(String v) { this.scanMessageContent = v; }
    public void setFallbackToLongCode(Boolean v) { this.fallbackToLongCode = v; }
    public void setAreaCodeGeomatch(Boolean v) { this.areaCodeGeomatch = v; }
    public void setSynchronousValidation(Boolean v) { this.synchronousValidation = v; }
    public void setValidityPeriod(Integer v) { this.validityPeriod = v; }
    public void setUrl(String v) { this.url = v; }
    public void setUsecase(String v) { this.usecase = v; }
    public void setUseInboundWebhookOnNumber(Boolean v) { this.useInboundWebhookOnNumber = v; }
}
