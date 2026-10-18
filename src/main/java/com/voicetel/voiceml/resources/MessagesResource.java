package com.voicetel.voiceml.resources;

import com.voicetel.voiceml.Pager;
import com.voicetel.voiceml.Transport;
import com.voicetel.voiceml.exceptions.ValidationException;
import com.voicetel.voiceml.models.CreateMessageFeedbackRequest;
import com.voicetel.voiceml.models.CreateMessageRequest;
import com.voicetel.voiceml.models.ListMessageMediaParams;
import com.voicetel.voiceml.models.ListMessagesParams;
import com.voicetel.voiceml.models.Message;
import com.voicetel.voiceml.models.MessageFeedback;
import com.voicetel.voiceml.models.MessageList;
import com.voicetel.voiceml.models.MessageMedia;
import com.voicetel.voiceml.models.MessageMediaList;
import com.voicetel.voiceml.models.UpdateMessageRequest;

import java.util.Map;

/** {@code /Messages}: the Twilio-compatible SMS and MMS surface, plus message media. */
public final class MessagesResource extends BaseResource {

    public MessagesResource(Transport transport) {
        super(transport);
    }

    /**
     * {@code POST /Messages}: send an SMS, or an MMS when {@code MediaUrl} is set. Never retried by
     * the SDK on a 5xx or timeout.
     *
     * @param body the message (see {@link CreateMessageRequest})
     * @return the message
     */
    public Message create(CreateMessageRequest body) {
        if (body == null) {
            throw new ValidationException("a message needs To and Body or MediaUrl");
        }
        return decode(
                transport.request("POST", accountPath("Messages"), null, body.toForm()),
                Message.class);
    }

    /** Fetch a previously-sent Message by sid. */
    public Message fetch(String messageSid) {
        return decode(
                transport.request("GET", accountPath("Messages", messageSid), null, null),
                Message.class);
    }

    /** List Messages, optionally filtered. {@code null} params are dropped. */
    public MessageList list(ListMessagesParams params) {
        Map<String, Object> q = params != null ? params.toQuery() : null;
        return decode(
                transport.request("GET", accountPath("Messages"), q, null),
                MessageList.class);
    }

    /** List all Messages (no filters). */
    public MessageList list() {
        return list(null);
    }

    /**
     * Auto-paginate through all pages of {@code GET /Messages}, collecting every {@link Message}
     * into a single list.
     */
    public Pager<Message> iterate(ListMessagesParams params) {
        return pager(() -> list(params), MessageList.class, MessageList::getMessages);
    }

    /** Auto-paginate all Messages (no filters). */
    public Pager<Message> iterate() {
        return iterate(null);
    }

    /** Update a Message — redact Body or attempt Status=canceled. */
    public Message update(String messageSid, UpdateMessageRequest body) {
        Map<String, Object> form = body != null ? body.toForm() : null;
        return decode(
                transport.request("POST", accountPath("Messages", messageSid), null, form),
                Message.class);
    }

    /** Delete a Message resource from the account's store. */
    public void delete(String messageSid) {
        transport.request("DELETE", accountPath("Messages", messageSid), null, null);
    }

    // --- /Messages/{MessageSid}/Feedback ---

    /**
     * {@code POST /Messages/{MessageSid}/Feedback.json}: submit delivery feedback for a Message.
     * An omitted {@code Outcome} is reported as {@code unconfirmed}.
     *
     * @param messageSid the message SID
     * @return the echoed Feedback resource
     */
    public MessageFeedback createFeedback(String messageSid) {
        return createFeedback(messageSid, null);
    }

    /**
     * {@code POST /Messages/{MessageSid}/Feedback.json}: submit delivery feedback for a Message
     * with an {@code Outcome} ({@code confirmed} or {@code unconfirmed}).
     *
     * @param messageSid the message SID
     * @param body the feedback ({@code Outcome}), or {@code null}
     * @return the echoed Feedback resource
     */
    public MessageFeedback createFeedback(String messageSid, CreateMessageFeedbackRequest body) {
        Map<String, Object> form = body != null ? body.toForm() : null;
        return decode(
                transport.request("POST", accountPath("Messages", messageSid, "Feedback"), null, form),
                MessageFeedback.class);
    }

    // --- /Messages/{MessageSid}/Media ---

    /**
     * {@code GET /Messages/{MessageSid}/Media}.
     *
     * @param messageSid the message SID
     * @param params filters and paging, or {@code null}
     * @return the page
     */
    public MessageMediaList listMedia(String messageSid, ListMessageMediaParams params) {
        Map<String, Object> q = params != null ? params.toQuery() : null;
        return decode(
                transport.request("GET", accountPath("Messages", messageSid, "Media"), q, null),
                MessageMediaList.class);
    }

    /**
     * @param messageSid the message SID
     * @return the first page of the message's media
     */
    public MessageMediaList listMedia(String messageSid) {
        return listMedia(messageSid, null);
    }

    /**
     * Lazily iterate every media item of a message.
     *
     * @param messageSid the message SID
     * @param params filters and paging, or {@code null}
     * @return the pager
     */
    public Pager<MessageMedia> iterateMedia(String messageSid, ListMessageMediaParams params) {
        return pager(() -> listMedia(messageSid, params), MessageMediaList.class, MessageMediaList::getMediaList);
    }

    /**
     * {@code GET /Messages/{MessageSid}/Media/{Sid}}: the media metadata.
     *
     * @param messageSid the message SID
     * @param mediaSid the media SID
     * @return the media metadata
     */
    public MessageMedia fetchMedia(String messageSid, String mediaSid) {
        return decode(
                transport.request("GET", accountPath("Messages", messageSid, "Media", mediaSid), null, null),
                MessageMedia.class);
    }

    /**
     * {@code DELETE /Messages/{MessageSid}/Media/{Sid}}.
     *
     * @param messageSid the message SID
     * @param mediaSid the media SID
     */
    public void deleteMedia(String messageSid, String mediaSid) {
        transport.request("DELETE", accountPath("Messages", messageSid, "Media", mediaSid), null, null);
    }
}
