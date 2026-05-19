package com.voicetel.voiceml.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/** One tripped check from the {@code /health} deep probe. */
@JsonIgnoreProperties(ignoreUnknown = true)
public class HealthFailure {

    @JsonProperty("check")
    private String check;

    @JsonProperty("detail")
    private String detail;

    public String getCheck() { return check; }
    public void setCheck(String check) { this.check = check; }
    public String getDetail() { return detail; }
    public void setDetail(String detail) { this.detail = detail; }
}
