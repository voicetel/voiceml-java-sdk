package com.voicetel.voiceml.models;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;

/** Paginated response from {@code GET /Queues/{sid}/Members}. */
public class QueueMemberList extends Page {

    @JsonProperty("queue_members")
    private List<QueueMember> queueMembers = new ArrayList<>();

    public List<QueueMember> getQueueMembers() {
        return queueMembers;
    }

    public void setQueueMembers(List<QueueMember> queueMembers) {
        this.queueMembers = queueMembers;
    }
}
