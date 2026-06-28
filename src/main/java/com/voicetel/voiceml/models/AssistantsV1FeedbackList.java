package com.voicetel.voiceml.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;

/** Paginated {@code /v1/Assistants/{id}/Feedbacks} response. */
@JsonIgnoreProperties(ignoreUnknown = true)
public class AssistantsV1FeedbackList {

    @JsonProperty("feedbacks")
    private List<AssistantsV1Feedback> feedbacks = new ArrayList<>();

    @JsonProperty("meta")
    private VoiceV1Meta meta;

    public List<AssistantsV1Feedback> getFeedbacks() { return feedbacks; }
    public VoiceV1Meta getMeta() { return meta; }

    public void setFeedbacks(List<AssistantsV1Feedback> v) {
        this.feedbacks = v != null ? v : new ArrayList<>();
    }
    public void setMeta(VoiceV1Meta v) { this.meta = v; }
}
