package com.voicetel.voiceml.resources;

import com.voicetel.voiceml.Transport;
import com.voicetel.voiceml.models.AssistantsV1Feedback;
import com.voicetel.voiceml.models.AssistantsV1FeedbackList;
import com.voicetel.voiceml.models.CreateAssistantsV1FeedbackRequest;
import com.voicetel.voiceml.models.ListPageParams;

import java.util.Map;

/** Operations on {@code /v1/Assistants/{id}/Feedbacks}. */
public final class AssistantsV1AssistantFeedbacksResource extends BaseResource {

    private final String assistantId;

    public AssistantsV1AssistantFeedbacksResource(Transport transport, String assistantId) {
        super(transport);
        this.assistantId = assistantId;
    }

    public AssistantsV1FeedbackList list(ListPageParams params) {
        Map<String, Object> q = params != null ? params.toQuery() : null;
        return decode(transport.requestJson("GET",
                "/v1/Assistants/" + assistantId + "/Feedbacks", q, null),
                AssistantsV1FeedbackList.class);
    }

    public AssistantsV1FeedbackList list() { return list(null); }

    public AssistantsV1Feedback create(CreateAssistantsV1FeedbackRequest body) {
        Map<String, Object> json = body != null ? body.toJsonBody() : null;
        return decode(transport.requestJson("POST",
                "/v1/Assistants/" + assistantId + "/Feedbacks", null, json),
                AssistantsV1Feedback.class);
    }
}
