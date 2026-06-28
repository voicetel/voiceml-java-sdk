package com.voicetel.voiceml.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;

/** Paginated {@code .../Messages/{MessageSid}/Receipts} response. */
@JsonIgnoreProperties(ignoreUnknown = true)
public class ConversationsV1ConversationMessageReceiptList {

    @JsonProperty("delivery_receipts")
    private List<ConversationsV1ConversationMessageReceipt> deliveryReceipts = new ArrayList<>();

    @JsonProperty("meta")
    private VoiceV1Meta meta;

    public List<ConversationsV1ConversationMessageReceipt> getDeliveryReceipts() { return deliveryReceipts; }
    public VoiceV1Meta getMeta() { return meta; }

    public void setDeliveryReceipts(List<ConversationsV1ConversationMessageReceipt> v) {
        this.deliveryReceipts = v != null ? v : new ArrayList<>();
    }
    public void setMeta(VoiceV1Meta v) { this.meta = v; }
}
