package com.voicetel.voiceml.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;

/** {@code GET /Exports} response: the archives available for download. */
@JsonIgnoreProperties(ignoreUnknown = true)
public class ExportList {

    @JsonProperty("account_sid")
    private String accountSid;

    @JsonProperty("uri")
    private String uri;

    @JsonProperty("exports")
    private List<Export> exports = new ArrayList<>();

    /** @return the {@code account_sid} field */
    public String getAccountSid() {
        return accountSid;
    }

    /** @param accountSid the {@code account_sid} field */
    public void setAccountSid(String accountSid) {
        this.accountSid = accountSid;
    }

    /** @return the {@code uri} field */
    public String getUri() {
        return uri;
    }

    /** @param uri the {@code uri} field */
    public void setUri(String uri) {
        this.uri = uri;
    }

    /** @return the {@code exports} field */
    public List<Export> getExports() {
        return exports;
    }

    /** @param exports the {@code exports} field */
    public void setExports(List<Export> exports) {
        this.exports = exports;
    }
}
