package com.voicetel.voiceml.resources;

import com.voicetel.voiceml.Transport;
import com.voicetel.voiceml.models.CreateMessageRequest;
import com.voicetel.voiceml.models.ListMessagesParams;
import com.voicetel.voiceml.models.Message;
import com.voicetel.voiceml.models.MessageList;
import com.voicetel.voiceml.models.UpdateMessageRequest;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/** {@code /Messages} — Twilio-compatible SMS surface (outbound-only today). */
public final class MessagesResource extends BaseResource {

    public MessagesResource(Transport transport) {
        super(transport);
    }

    /** Send an outbound SMS. */
    public Message create(CreateMessageRequest body) {
        Map<String, Object> form = body != null ? body.toForm() : null;
        return decode(
                transport.request("POST", accountPath("Messages"), null, form),
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
    public List<Message> iterate(ListMessagesParams params) {
        List<Message> out = new ArrayList<>();
        Map<String, Object> q = params != null ? params.toQuery() : new LinkedHashMap<>();
        int page = q.containsKey("Page") ? ((Number) q.get("Page")).intValue() : 0;
        while (true) {
            q.put("Page", page);
            MessageList chunk = decode(
                    transport.request("GET", accountPath("Messages"), q, null), MessageList.class);
            out.addAll(chunk.getMessages());
            if (chunk.getNextPageUri() == null || chunk.getNextPageUri().isEmpty()
                    || chunk.getMessages().isEmpty()) {
                return out;
            }
            page++;
        }
    }

    /** Auto-paginate all Messages (no filters). */
    public List<Message> iterate() {
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
}
