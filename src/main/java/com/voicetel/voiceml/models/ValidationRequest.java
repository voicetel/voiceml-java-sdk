package com.voicetel.voiceml.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/** Result of {@code POST /OutgoingCallerIds}: the validation call placed to the number. */
@JsonIgnoreProperties(ignoreUnknown = true)
public class ValidationRequest {

    @JsonProperty("account_sid")
    private String accountSid;

    @JsonProperty("phone_number")
    private String phoneNumber;

    @JsonProperty("friendly_name")
    private String friendlyName;

    @JsonProperty("validation_code")
    private String validationCode;

    @JsonProperty("call_sid")
    private String callSid;

    /** @return the {@code account_sid} field */
    public String getAccountSid() {
        return accountSid;
    }

    /** @param accountSid the {@code account_sid} field */
    public void setAccountSid(String accountSid) {
        this.accountSid = accountSid;
    }

    /** @return the {@code phone_number} field */
    public String getPhoneNumber() {
        return phoneNumber;
    }

    /** @param phoneNumber the {@code phone_number} field */
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    /** @return the {@code friendly_name} field */
    public String getFriendlyName() {
        return friendlyName;
    }

    /** @param friendlyName the {@code friendly_name} field */
    public void setFriendlyName(String friendlyName) {
        this.friendlyName = friendlyName;
    }

    /** @return the {@code validation_code} field */
    public String getValidationCode() {
        return validationCode;
    }

    /** @param validationCode the {@code validation_code} field */
    public void setValidationCode(String validationCode) {
        this.validationCode = validationCode;
    }

    /** @return the {@code call_sid} field */
    public String getCallSid() {
        return callSid;
    }

    /** @param callSid the {@code call_sid} field */
    public void setCallSid(String callSid) {
        this.callSid = callSid;
    }
}
