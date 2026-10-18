package com.voicetel.voiceml.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;

/** Paginated {@code GET /Messages/{MessageSid}/Media} response. */
@JsonIgnoreProperties(ignoreUnknown = true)
public class MessageMediaList extends Page {

    @JsonProperty("media_list")
    private List<MessageMedia> mediaList = new ArrayList<>();

    /** @return the {@code media_list} field */
    public List<MessageMedia> getMediaList() {
        return mediaList;
    }

    /** @param mediaList the {@code media_list} field */
    public void setMediaList(List<MessageMedia> mediaList) {
        this.mediaList = mediaList;
    }
}
