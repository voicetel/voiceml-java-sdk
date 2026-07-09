package com.voicetel.voiceml.models;

import java.util.LinkedHashMap;
import java.util.Map;

/** Form body for {@code POST /v1/Services/{sid}} on the messaging host. All fields optional. */
public final class UpdateMessagingServiceRequest {

    private final String friendlyName;
    private final String inboundRequestUrl;
    private final String inboundMethod;
    private final String fallbackUrl;
    private final String fallbackMethod;
    private final String statusCallback;
    private final Boolean stickySender;
    private final Boolean mmsConverter;
    private final Boolean smartEncoding;
    private final String scanMessageContent;
    private final Boolean fallbackToLongCode;
    private final Boolean areaCodeGeomatch;
    private final Boolean synchronousValidation;
    private final Integer validityPeriod;
    private final String usecase;
    private final Boolean useInboundWebhookOnNumber;

    private UpdateMessagingServiceRequest(Builder b) {
        this.friendlyName = b.friendlyName;
        this.inboundRequestUrl = b.inboundRequestUrl;
        this.inboundMethod = b.inboundMethod;
        this.fallbackUrl = b.fallbackUrl;
        this.fallbackMethod = b.fallbackMethod;
        this.statusCallback = b.statusCallback;
        this.stickySender = b.stickySender;
        this.mmsConverter = b.mmsConverter;
        this.smartEncoding = b.smartEncoding;
        this.scanMessageContent = b.scanMessageContent;
        this.fallbackToLongCode = b.fallbackToLongCode;
        this.areaCodeGeomatch = b.areaCodeGeomatch;
        this.synchronousValidation = b.synchronousValidation;
        this.validityPeriod = b.validityPeriod;
        this.usecase = b.usecase;
        this.useInboundWebhookOnNumber = b.useInboundWebhookOnNumber;
    }

    public Map<String, Object> toForm() {
        Map<String, Object> f = new LinkedHashMap<>();
        if (friendlyName != null) f.put("FriendlyName", friendlyName);
        if (inboundRequestUrl != null) f.put("InboundRequestUrl", inboundRequestUrl);
        if (inboundMethod != null) f.put("InboundMethod", inboundMethod);
        if (fallbackUrl != null) f.put("FallbackUrl", fallbackUrl);
        if (fallbackMethod != null) f.put("FallbackMethod", fallbackMethod);
        if (statusCallback != null) f.put("StatusCallback", statusCallback);
        if (stickySender != null) f.put("StickySender", stickySender);
        if (mmsConverter != null) f.put("MmsConverter", mmsConverter);
        if (smartEncoding != null) f.put("SmartEncoding", smartEncoding);
        if (scanMessageContent != null) f.put("ScanMessageContent", scanMessageContent);
        if (fallbackToLongCode != null) f.put("FallbackToLongCode", fallbackToLongCode);
        if (areaCodeGeomatch != null) f.put("AreaCodeGeomatch", areaCodeGeomatch);
        if (synchronousValidation != null) f.put("SynchronousValidation", synchronousValidation);
        if (validityPeriod != null) f.put("ValidityPeriod", validityPeriod);
        if (usecase != null) f.put("Usecase", usecase);
        if (useInboundWebhookOnNumber != null) {
            f.put("UseInboundWebhookOnNumber", useInboundWebhookOnNumber);
        }
        return f;
    }

    public static Builder builder() { return new Builder(); }

    public static final class Builder {
        private String friendlyName;
        private String inboundRequestUrl;
        private String inboundMethod;
        private String fallbackUrl;
        private String fallbackMethod;
        private String statusCallback;
        private Boolean stickySender;
        private Boolean mmsConverter;
        private Boolean smartEncoding;
        private String scanMessageContent;
        private Boolean fallbackToLongCode;
        private Boolean areaCodeGeomatch;
        private Boolean synchronousValidation;
        private Integer validityPeriod;
        private String usecase;
        private Boolean useInboundWebhookOnNumber;

        public Builder friendlyName(String v) { this.friendlyName = v; return this; }
        public Builder inboundRequestUrl(String v) { this.inboundRequestUrl = v; return this; }
        public Builder inboundMethod(String v) { this.inboundMethod = v; return this; }
        public Builder fallbackUrl(String v) { this.fallbackUrl = v; return this; }
        public Builder fallbackMethod(String v) { this.fallbackMethod = v; return this; }
        public Builder statusCallback(String v) { this.statusCallback = v; return this; }
        public Builder stickySender(Boolean v) { this.stickySender = v; return this; }
        public Builder mmsConverter(Boolean v) { this.mmsConverter = v; return this; }
        public Builder smartEncoding(Boolean v) { this.smartEncoding = v; return this; }
        public Builder scanMessageContent(String v) { this.scanMessageContent = v; return this; }
        public Builder fallbackToLongCode(Boolean v) { this.fallbackToLongCode = v; return this; }
        public Builder areaCodeGeomatch(Boolean v) { this.areaCodeGeomatch = v; return this; }
        public Builder synchronousValidation(Boolean v) { this.synchronousValidation = v; return this; }
        public Builder validityPeriod(Integer v) { this.validityPeriod = v; return this; }
        public Builder usecase(String v) { this.usecase = v; return this; }
        public Builder useInboundWebhookOnNumber(Boolean v) {
            this.useInboundWebhookOnNumber = v;
            return this;
        }

        public UpdateMessagingServiceRequest build() {
            return new UpdateMessagingServiceRequest(this);
        }
    }
}
