package com.voicetel.voiceml.resources;

import com.voicetel.voiceml.Transport;
import com.voicetel.voiceml.models.CreateQueueRequest;
import com.voicetel.voiceml.models.DequeueRequest;
import com.voicetel.voiceml.models.ListPageParams;
import com.voicetel.voiceml.models.Queue;
import com.voicetel.voiceml.models.QueueList;
import com.voicetel.voiceml.models.QueueMember;
import com.voicetel.voiceml.models.QueueMemberList;
import com.voicetel.voiceml.models.UpdateQueueRequest;

import java.util.Map;

/** {@code /Queues} and {@code /Queues/{sid}/Members}. */
public final class QueuesResource extends BaseResource {

    public QueuesResource(Transport transport) {
        super(transport);
    }

    public Queue create(CreateQueueRequest body) {
        return decode(
                transport.request("POST", accountPath("Queues"), null, body.toForm()),
                Queue.class);
    }

    public QueueList list() {
        return decode(
                transport.request("GET", accountPath("Queues"), null, null), QueueList.class);
    }

    public Queue get(String queueSid) {
        return decode(
                transport.request("GET", accountPath("Queues", queueSid), null, null),
                Queue.class);
    }

    public Queue update(String queueSid, UpdateQueueRequest body) {
        return decode(
                transport.request(
                        "POST", accountPath("Queues", queueSid), null, body.toForm()),
                Queue.class);
    }

    public void delete(String queueSid) {
        transport.request("DELETE", accountPath("Queues", queueSid), null, null);
    }

    // --- Members ---

    public QueueMemberList listMembers(String queueSid, ListPageParams params) {
        Map<String, Object> q = params != null ? params.toQuery() : null;
        return decode(
                transport.request("GET", accountPath("Queues", queueSid, "Members"), q, null),
                QueueMemberList.class);
    }

    public QueueMemberList listMembers(String queueSid) {
        return listMembers(queueSid, null);
    }

    public QueueMember peekFront(String queueSid) {
        return decode(
                transport.request(
                        "GET", accountPath("Queues", queueSid, "Members", "Front"), null, null),
                QueueMember.class);
    }

    public QueueMember dequeueFront(String queueSid, DequeueRequest body) {
        return decode(
                transport.request(
                        "POST",
                        accountPath("Queues", queueSid, "Members", "Front"),
                        null,
                        body.toForm()),
                QueueMember.class);
    }

    public QueueMember getMember(String queueSid, String callSid) {
        return decode(
                transport.request(
                        "GET", accountPath("Queues", queueSid, "Members", callSid), null, null),
                QueueMember.class);
    }

    public QueueMember dequeueMember(String queueSid, String callSid, DequeueRequest body) {
        return decode(
                transport.request(
                        "POST",
                        accountPath("Queues", queueSid, "Members", callSid),
                        null,
                        body.toForm()),
                QueueMember.class);
    }
}
