package com.voicetel.voiceml.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/** One daily export archive listed by {@code GET /Exports}. */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Export {

    @JsonProperty("date")
    private String date;

    @JsonProperty("size")
    private Long size;

    @JsonProperty("uri")
    private String uri;

    /** @return the {@code date} field */
    public String getDate() {
        return date;
    }

    /** @param date the {@code date} field */
    public void setDate(String date) {
        this.date = date;
    }

    /** @return the {@code size} field */
    public Long getSize() {
        return size;
    }

    /** @param size the {@code size} field */
    public void setSize(Long size) {
        this.size = size;
    }

    /** @return the {@code uri} field */
    public String getUri() {
        return uri;
    }

    /** @param uri the {@code uri} field */
    public void setUri(String uri) {
        this.uri = uri;
    }
}
