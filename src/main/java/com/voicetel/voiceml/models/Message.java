package com.voicetel.voiceml.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Map;

/**
 * A Twilio-compatible Message resource. Returned by the {@code /Messages} endpoints.
 *
 * <p>Note: {@code num_segments} and {@code num_media} are surfaced as {@code String} on the wire
 * to match Twilio's documented response shape.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Message {

    @JsonProperty("sid")
    private String sid;

    @JsonProperty("account_sid")
    private String accountSid;

    @JsonProperty("api_version")
    private String apiVersion;

    @JsonProperty("to")
    private String to;

    @JsonProperty("from")
    private String from;

    @JsonProperty("body")
    private String body;

    @JsonProperty("status")
    private MessageStatus status;

    @JsonProperty("num_segments")
    private String numSegments;

    @JsonProperty("num_media")
    private String numMedia;

    @JsonProperty("direction")
    private String direction;

    @JsonProperty("price")
    private String price;

    @JsonProperty("price_unit")
    private String priceUnit;

    @JsonProperty("error_code")
    private Integer errorCode;

    @JsonProperty("error_message")
    private String errorMessage;

    @JsonProperty("messaging_service_sid")
    private String messagingServiceSid;

    @JsonProperty("date_created")
    private String dateCreated;

    @JsonProperty("date_updated")
    private String dateUpdated;

    @JsonProperty("date_sent")
    private String dateSent;

    @JsonProperty("uri")
    private String uri;

    @JsonProperty("subresource_uris")
    private Map<String, String> subresourceUris;

    public String getSid() { return sid; }
    public void setSid(String sid) { this.sid = sid; }
    public String getAccountSid() { return accountSid; }
    public void setAccountSid(String accountSid) { this.accountSid = accountSid; }
    public String getApiVersion() { return apiVersion; }
    public void setApiVersion(String apiVersion) { this.apiVersion = apiVersion; }
    public String getTo() { return to; }
    public void setTo(String to) { this.to = to; }
    public String getFrom() { return from; }
    public void setFrom(String from) { this.from = from; }
    public String getBody() { return body; }
    public void setBody(String body) { this.body = body; }
    public MessageStatus getStatus() { return status; }
    public void setStatus(MessageStatus status) { this.status = status; }
    public String getNumSegments() { return numSegments; }
    public void setNumSegments(String numSegments) { this.numSegments = numSegments; }
    public String getNumMedia() { return numMedia; }
    public void setNumMedia(String numMedia) { this.numMedia = numMedia; }
    public String getDirection() { return direction; }
    public void setDirection(String direction) { this.direction = direction; }
    public String getPrice() { return price; }
    public void setPrice(String price) { this.price = price; }
    public String getPriceUnit() { return priceUnit; }
    public void setPriceUnit(String priceUnit) { this.priceUnit = priceUnit; }
    public Integer getErrorCode() { return errorCode; }
    public void setErrorCode(Integer errorCode) { this.errorCode = errorCode; }
    public String getErrorMessage() { return errorMessage; }
    public void setErrorMessage(String errorMessage) { this.errorMessage = errorMessage; }
    public String getMessagingServiceSid() { return messagingServiceSid; }
    public void setMessagingServiceSid(String messagingServiceSid) { this.messagingServiceSid = messagingServiceSid; }
    public String getDateCreated() { return dateCreated; }
    public void setDateCreated(String dateCreated) { this.dateCreated = dateCreated; }
    public String getDateUpdated() { return dateUpdated; }
    public void setDateUpdated(String dateUpdated) { this.dateUpdated = dateUpdated; }
    public String getDateSent() { return dateSent; }
    public void setDateSent(String dateSent) { this.dateSent = dateSent; }
    public String getUri() { return uri; }
    public void setUri(String uri) { this.uri = uri; }
    public Map<String, String> getSubresourceUris() { return subresourceUris; }
    public void setSubresourceUris(Map<String, String> subresourceUris) { this.subresourceUris = subresourceUris; }
}
