package com.voicetel.voiceml.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;

/** A phone number in a Messaging Service sender pool ({@code /v1/Services/{Sid}/PhoneNumbers}). */
@JsonIgnoreProperties(ignoreUnknown = true)
public class MessagingServicePhoneNumber {

    @JsonProperty("sid")
    private String sid;

    @JsonProperty("account_sid")
    private String accountSid;

    @JsonProperty("service_sid")
    private String serviceSid;

    @JsonProperty("date_created")
    private String dateCreated;

    @JsonProperty("date_updated")
    private String dateUpdated;

    @JsonProperty("phone_number")
    private String phoneNumber;

    @JsonProperty("country_code")
    private String countryCode;

    @JsonProperty("capabilities")
    private List<String> capabilities = new ArrayList<>();

    @JsonProperty("url")
    private String url;

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

    /** @return the {@code service_sid} field */
    public String getServiceSid() {
        return serviceSid;
    }

    /** @param serviceSid the {@code service_sid} field */
    public void setServiceSid(String serviceSid) {
        this.serviceSid = serviceSid;
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

    /** @return the {@code phone_number} field */
    public String getPhoneNumber() {
        return phoneNumber;
    }

    /** @param phoneNumber the {@code phone_number} field */
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    /** @return the {@code country_code} field */
    public String getCountryCode() {
        return countryCode;
    }

    /** @param countryCode the {@code country_code} field */
    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    /** @return the {@code capabilities} field */
    public List<String> getCapabilities() {
        return capabilities;
    }

    /** @param capabilities the {@code capabilities} field */
    public void setCapabilities(List<String> capabilities) {
        this.capabilities = capabilities;
    }

    /** @return the {@code url} field */
    public String getUrl() {
        return url;
    }

    /** @param url the {@code url} field */
    public void setUrl(String url) {
        this.url = url;
    }
}
