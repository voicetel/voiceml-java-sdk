package com.voicetel.voiceml.models;

import com.voicetel.voiceml.exceptions.ValidationException;

import java.time.Instant;
import java.time.format.DateTimeFormatter;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Form body for {@code POST /Messages}.
 *
 * <p>{@code To} is required, plus {@code Body} or at least one {@code MediaUrl} (an MMS). Each
 * {@code MediaUrl} is sent as a repeated form key. {@code From} falls back to the account's default
 * sender when omitted.
 */
public final class CreateMessageRequest {

    private final String to;
    private final String from;
    private final String body;
    private final List<String> mediaUrl;
    private final String messagingServiceSid;
    private final Integer validityPeriod;
    private final String statusCallback;
    private final String statusCallbackMethod;
    private final String sendAt;
    private final String scheduleType;

    private CreateMessageRequest(Builder b) {
        this.to = b.to;
        this.from = b.from;
        this.body = b.body;
        this.mediaUrl = b.mediaUrl == null ? null : List.copyOf(b.mediaUrl);
        this.messagingServiceSid = b.messagingServiceSid;
        this.validityPeriod = b.validityPeriod;
        this.statusCallback = b.statusCallback;
        this.statusCallbackMethod = b.statusCallbackMethod;
        this.sendAt = b.sendAt;
        this.scheduleType = b.scheduleType;
    }

    /** @return the form fields, keyed by wire name; unset values are omitted */
    public Map<String, Object> toForm() {
        Map<String, Object> m = new LinkedHashMap<>();
        if (to != null) m.put("To", to);
        if (from != null) m.put("From", from);
        if (body != null) m.put("Body", body);
        if (mediaUrl != null) m.put("MediaUrl", mediaUrl);
        if (messagingServiceSid != null) m.put("MessagingServiceSid", messagingServiceSid);
        if (validityPeriod != null) m.put("ValidityPeriod", validityPeriod);
        if (statusCallback != null) m.put("StatusCallback", statusCallback);
        if (statusCallbackMethod != null) m.put("StatusCallbackMethod", statusCallbackMethod);
        if (sendAt != null) m.put("SendAt", sendAt);
        if (scheduleType != null) m.put("ScheduleType", scheduleType);
        return m;
    }

    /** @return the {@code To} value, or {@code null} when unset */
    public String getTo() {
        return to;
    }

    /** @return the {@code From} value, or {@code null} when unset */
    public String getFrom() {
        return from;
    }

    /** @return the {@code Body} value, or {@code null} when unset */
    public String getBody() {
        return body;
    }

    /** @return the {@code MediaUrl} value, or {@code null} when unset */
    public List<String> getMediaUrl() {
        return mediaUrl;
    }

    /** @return the {@code MessagingServiceSid} value, or {@code null} when unset */
    public String getMessagingServiceSid() {
        return messagingServiceSid;
    }

    /** @return the {@code ValidityPeriod} value, or {@code null} when unset */
    public Integer getValidityPeriod() {
        return validityPeriod;
    }

    /** @return the {@code StatusCallback} value, or {@code null} when unset */
    public String getStatusCallback() {
        return statusCallback;
    }

    /** @return the {@code StatusCallbackMethod} value, or {@code null} when unset */
    public String getStatusCallbackMethod() {
        return statusCallbackMethod;
    }

    /** @return the {@code SendAt} value (ISO-8601), or {@code null} when unset */
    public String getSendAt() {
        return sendAt;
    }

    /** @return the {@code ScheduleType} value, or {@code null} when unset */
    public String getScheduleType() {
        return scheduleType;
    }

    /** @return a new builder */
    public static Builder builder() {
        return new Builder();
    }

    /** Builder for {@link CreateMessageRequest}. */
    public static final class Builder {
        private String to;
        private String from;
        private String body;
        private List<String> mediaUrl;
        private String messagingServiceSid;
        private Integer validityPeriod;
        private String statusCallback;
        private String statusCallbackMethod;
        private String sendAt;
        private String scheduleType;

        /** Create an empty builder; prefer {@link CreateMessageRequest#builder()}. */
        public Builder() {
            // all fields start unset
        }

        /**
         * @param v {@code To} (required)
         * @return this builder
         */
        public Builder to(String v) {
            this.to = v;
            return this;
        }

        /**
         * @param v {@code From}
         * @return this builder
         */
        public Builder from(String v) {
            this.from = v;
            return this;
        }

        /**
         * @param v {@code Body}
         * @return this builder
         */
        public Builder body(String v) {
            this.body = v;
            return this;
        }

        /**
         * @param v {@code MediaUrl}
         * @return this builder
         */
        public Builder mediaUrl(List<String> v) {
            this.mediaUrl = v;
            return this;
        }

        /**
         * @param v {@code MessagingServiceSid}
         * @return this builder
         */
        public Builder messagingServiceSid(String v) {
            this.messagingServiceSid = v;
            return this;
        }

        /**
         * @param v {@code ValidityPeriod}
         * @return this builder
         */
        public Builder validityPeriod(Integer v) {
            this.validityPeriod = v;
            return this;
        }

        /**
         * @param v {@code StatusCallback}
         * @return this builder
         */
        public Builder statusCallback(String v) {
            this.statusCallback = v;
            return this;
        }

        /**
         * @param v {@code StatusCallbackMethod}: one of {@code GET}, {@code POST}
         * @return this builder
         */
        public Builder statusCallbackMethod(String v) {
            this.statusCallbackMethod = v;
            return this;
        }

        /**
         * @param v {@code SendAt}: schedule the message for a future UTC time; requires
         *     {@code ScheduleType} and {@code MessagingServiceSid}
         * @return this builder
         */
        public Builder sendAt(String v) {
            this.sendAt = v;
            return this;
        }

        /**
         * @param v {@code SendAt} as an instant (ISO-8601 UTC on the wire)
         * @return this builder
         */
        public Builder sendAt(Instant v) {
            this.sendAt = v == null ? null : DateTimeFormatter.ISO_INSTANT.format(v);
            return this;
        }

        /**
         * @param v {@code ScheduleType}: {@code fixed} is the only supported value; requires
         *     {@code SendAt} and {@code MessagingServiceSid}
         * @return this builder
         */
        public Builder scheduleType(String v) {
            this.scheduleType = v;
            return this;
        }

        /**
         * @return the request
         * @throws ValidationException when the combination of values is invalid
         */
        public CreateMessageRequest build() {
            if ((body == null || body.isEmpty()) && (mediaUrl == null || mediaUrl.isEmpty())) {
                throw new ValidationException("a message needs Body or at least one MediaUrl");
            }
            return new CreateMessageRequest(this);
        }
    }
}
