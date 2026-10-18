package com.voicetel.voiceml.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;

/** Paginated {@code GET /v1/Services/{Sid}/PhoneNumbers} response on the messaging host. */
@JsonIgnoreProperties(ignoreUnknown = true)
public class MessagingServicePhoneNumberList implements MetaPage {

    @JsonProperty("phone_numbers")
    private List<MessagingServicePhoneNumber> phoneNumbers = new ArrayList<>();

    @JsonProperty("meta")
    private VoiceV1Meta meta;

    /** @return the phone numbers on this page */
    public List<MessagingServicePhoneNumber> getPhoneNumbers() {
        return phoneNumbers;
    }

    /** @param phoneNumbers the phone numbers on this page */
    public void setPhoneNumbers(List<MessagingServicePhoneNumber> phoneNumbers) {
        this.phoneNumbers = phoneNumbers != null ? phoneNumbers : new ArrayList<>();
    }

    @Override
    public VoiceV1Meta getMeta() {
        return meta;
    }

    /** @param meta the pagination metadata */
    public void setMeta(VoiceV1Meta meta) {
        this.meta = meta;
    }
}
