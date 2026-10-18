package com.voicetel.voiceml.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/** Account balance ({@code GET /Balance}). VoiceML reports a static zero. */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Balance {

    @JsonProperty("account_sid")
    private String accountSid;

    @JsonProperty("balance")
    private String balance;

    @JsonProperty("currency")
    private String currency;

    /** @return the {@code account_sid} field */
    public String getAccountSid() {
        return accountSid;
    }

    /** @param accountSid the {@code account_sid} field */
    public void setAccountSid(String accountSid) {
        this.accountSid = accountSid;
    }

    /** @return the {@code balance} field */
    public String getBalance() {
        return balance;
    }

    /** @param balance the {@code balance} field */
    public void setBalance(String balance) {
        this.balance = balance;
    }

    /** @return the {@code currency} field */
    public String getCurrency() {
        return currency;
    }

    /** @param currency the {@code currency} field */
    public void setCurrency(String currency) {
        this.currency = currency;
    }
}
