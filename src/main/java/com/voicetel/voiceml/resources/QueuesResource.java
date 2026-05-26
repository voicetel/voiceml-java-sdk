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

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
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

    public QueueList list(ListPageParams params) {
        Map<String, Object> q = params != null ? params.toQuery() : null;
        return decode(
                transport.request("GET", accountPath("Queues"), q, null), QueueList.class);
    }

    public QueueList list() {
        return list(null);
    }

    /**
     * Auto-paginate through all pages of {@code GET /Queues}, collecting every {@link Queue} into
     * a single list.
     */
    public List<Queue> iterate(ListPageParams params) {
        List<Queue> out = new ArrayList<>();
        Map<String, Object> q = params != null ? params.toQuery() : new LinkedHashMap<>();
        int page = q.containsKey("Page") ? ((Number) q.get("Page")).intValue() : 0;
        while (true) {
            q.put("Page", page);
            QueueList chunk = decode(
                    transport.request("GET", accountPath("Queues"), q, null), QueueList.class);
            out.addAll(chunk.getQueues());
            if (chunk.getNextPageUri() == null || chunk.getNextPageUri().isEmpty()
                    || chunk.getQueues().isEmpty()) {
                return out;
            }
            page++;
        }
    }

    /** Auto-paginate all queues (no filters). */
    public List<Queue> iterate() {
        return iterate(null);
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

    /** Auto-paginate through all queue members. */
    public List<QueueMember> iterateMembers(String queueSid, ListPageParams params) {
        List<QueueMember> out = new ArrayList<>();
        Map<String, Object> q = params != null ? params.toQuery() : new LinkedHashMap<>();
        int page = q.containsKey("Page") ? ((Number) q.get("Page")).intValue() : 0;
        while (true) {
            q.put("Page", page);
            QueueMemberList chunk = decode(
                    transport.request(
                            "GET", accountPath("Queues", queueSid, "Members"), q, null),
                    QueueMemberList.class);
            out.addAll(chunk.getQueueMembers());
            if (chunk.getNextPageUri() == null || chunk.getNextPageUri().isEmpty()
                    || chunk.getQueueMembers().isEmpty()) {
                return out;
            }
            page++;
        }
    }

    /** Auto-paginate all queue members (no filters). */
    public List<QueueMember> iterateMembers(String queueSid) {
        return iterateMembers(queueSid, null);
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
