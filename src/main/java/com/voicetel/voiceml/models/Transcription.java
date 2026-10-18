package com.voicetel.voiceml.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/** A post-recording transcription ({@code /Transcriptions}, {@code /Recordings/{Sid}/Transcriptions}). */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Transcription {

    @JsonProperty("sid")
    private String sid;

    @JsonProperty("account_sid")
    private String accountSid;

    @JsonProperty("status")
    private String status;

    @JsonProperty("recording_sid")
    private String recordingSid;

    @JsonProperty("duration")
    private String duration;

    @JsonProperty("transcription_text")
    private String transcriptionText;

    @JsonProperty("type")
    private String type;

    @JsonProperty("price")
    private String price;

    @JsonProperty("price_unit")
    private String priceUnit;

    @JsonProperty("api_version")
    private String apiVersion;

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

    /** @return the {@code status} field */
    public String getStatus() {
        return status;
    }

    /** @param status the {@code status} field */
    public void setStatus(String status) {
        this.status = status;
    }

    /** @return the {@code recording_sid} field */
    public String getRecordingSid() {
        return recordingSid;
    }

    /** @param recordingSid the {@code recording_sid} field */
    public void setRecordingSid(String recordingSid) {
        this.recordingSid = recordingSid;
    }

    /** @return the {@code duration} field */
    public String getDuration() {
        return duration;
    }

    /** @param duration the {@code duration} field */
    public void setDuration(String duration) {
        this.duration = duration;
    }

    /** @return the {@code transcription_text} field */
    public String getTranscriptionText() {
        return transcriptionText;
    }

    /** @param transcriptionText the {@code transcription_text} field */
    public void setTranscriptionText(String transcriptionText) {
        this.transcriptionText = transcriptionText;
    }

    /** @return the {@code type} field */
    public String getType() {
        return type;
    }

    /** @param type the {@code type} field */
    public void setType(String type) {
        this.type = type;
    }

    /** @return the {@code price} field */
    public String getPrice() {
        return price;
    }

    /** @param price the {@code price} field */
    public void setPrice(String price) {
        this.price = price;
    }

    /** @return the {@code price_unit} field */
    public String getPriceUnit() {
        return priceUnit;
    }

    /** @param priceUnit the {@code price_unit} field */
    public void setPriceUnit(String priceUnit) {
        this.priceUnit = priceUnit;
    }

    /** @return the {@code api_version} field */
    public String getApiVersion() {
        return apiVersion;
    }

    /** @param apiVersion the {@code api_version} field */
    public void setApiVersion(String apiVersion) {
        this.apiVersion = apiVersion;
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
