package com.voicetel.voiceml.models;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * JSON body for {@code POST /2010-04-01/Accounts/{Sid}}. Only the properties you set are sent; an
 * empty {@code AllowCidr} list clears the allow-list. A concurrent update raises
 * {@code ConflictException} (code {@code 20409}).
 */
public final class UpdateAccountRequest {

    private final String friendlyName;
    private final String voiceUrl;
    private final String voiceMethod;
    private final String voiceFallbackUrl;
    private final String voiceFallbackMethod;
    private final String sttFsVendor;
    private final String sttHttpUrl;
    private final String sttHttpToken;
    private final String ttsHttpUrl;
    private final String ttsHttpToken;
    private final String ttsEngineName;
    private final String ttsDefaultVoice;
    private final String ttsDefaultLanguage;
    private final String awsBucket;
    private final String awsRegion;
    private final String awsPrefix;
    private final String awsAccessKeyId;
    private final String awsSecretAccessKey;
    private final String awsKmsKeyId;
    private final List<String> allowCidr;

    private UpdateAccountRequest(Builder b) {
        this.friendlyName = b.friendlyName;
        this.voiceUrl = b.voiceUrl;
        this.voiceMethod = b.voiceMethod;
        this.voiceFallbackUrl = b.voiceFallbackUrl;
        this.voiceFallbackMethod = b.voiceFallbackMethod;
        this.sttFsVendor = b.sttFsVendor;
        this.sttHttpUrl = b.sttHttpUrl;
        this.sttHttpToken = b.sttHttpToken;
        this.ttsHttpUrl = b.ttsHttpUrl;
        this.ttsHttpToken = b.ttsHttpToken;
        this.ttsEngineName = b.ttsEngineName;
        this.ttsDefaultVoice = b.ttsDefaultVoice;
        this.ttsDefaultLanguage = b.ttsDefaultLanguage;
        this.awsBucket = b.awsBucket;
        this.awsRegion = b.awsRegion;
        this.awsPrefix = b.awsPrefix;
        this.awsAccessKeyId = b.awsAccessKeyId;
        this.awsSecretAccessKey = b.awsSecretAccessKey;
        this.awsKmsKeyId = b.awsKmsKeyId;
        this.allowCidr = b.allowCidr == null ? null : List.copyOf(b.allowCidr);
    }

    /** @return the JSON body properties, keyed by wire name; unset values are omitted */
    public Map<String, Object> toJsonBody() {
        Map<String, Object> m = new LinkedHashMap<>();
        if (friendlyName != null) m.put("FriendlyName", friendlyName);
        if (voiceUrl != null) m.put("VoiceUrl", voiceUrl);
        if (voiceMethod != null) m.put("VoiceMethod", voiceMethod);
        if (voiceFallbackUrl != null) m.put("VoiceFallbackUrl", voiceFallbackUrl);
        if (voiceFallbackMethod != null) m.put("VoiceFallbackMethod", voiceFallbackMethod);
        if (sttFsVendor != null) m.put("SttFsVendor", sttFsVendor);
        if (sttHttpUrl != null) m.put("SttHttpUrl", sttHttpUrl);
        if (sttHttpToken != null) m.put("SttHttpToken", sttHttpToken);
        if (ttsHttpUrl != null) m.put("TtsHttpUrl", ttsHttpUrl);
        if (ttsHttpToken != null) m.put("TtsHttpToken", ttsHttpToken);
        if (ttsEngineName != null) m.put("TtsEngineName", ttsEngineName);
        if (ttsDefaultVoice != null) m.put("TtsDefaultVoice", ttsDefaultVoice);
        if (ttsDefaultLanguage != null) m.put("TtsDefaultLanguage", ttsDefaultLanguage);
        if (awsBucket != null) m.put("AwsBucket", awsBucket);
        if (awsRegion != null) m.put("AwsRegion", awsRegion);
        if (awsPrefix != null) m.put("AwsPrefix", awsPrefix);
        if (awsAccessKeyId != null) m.put("AwsAccessKeyId", awsAccessKeyId);
        if (awsSecretAccessKey != null) m.put("AwsSecretAccessKey", awsSecretAccessKey);
        if (awsKmsKeyId != null) m.put("AwsKmsKeyId", awsKmsKeyId);
        if (allowCidr != null) m.put("AllowCidr", allowCidr);
        return m;
    }

    /** @return the {@code FriendlyName} value, or {@code null} when unset */
    public String getFriendlyName() {
        return friendlyName;
    }

    /** @return the {@code VoiceUrl} value, or {@code null} when unset */
    public String getVoiceUrl() {
        return voiceUrl;
    }

    /** @return the {@code VoiceMethod} value, or {@code null} when unset */
    public String getVoiceMethod() {
        return voiceMethod;
    }

    /** @return the {@code VoiceFallbackUrl} value, or {@code null} when unset */
    public String getVoiceFallbackUrl() {
        return voiceFallbackUrl;
    }

    /** @return the {@code VoiceFallbackMethod} value, or {@code null} when unset */
    public String getVoiceFallbackMethod() {
        return voiceFallbackMethod;
    }

    /** @return the {@code SttFsVendor} value, or {@code null} when unset */
    public String getSttFsVendor() {
        return sttFsVendor;
    }

    /** @return the {@code SttHttpUrl} value, or {@code null} when unset */
    public String getSttHttpUrl() {
        return sttHttpUrl;
    }

    /** @return the {@code SttHttpToken} value, or {@code null} when unset */
    public String getSttHttpToken() {
        return sttHttpToken;
    }

    /** @return the {@code TtsHttpUrl} value, or {@code null} when unset */
    public String getTtsHttpUrl() {
        return ttsHttpUrl;
    }

    /** @return the {@code TtsHttpToken} value, or {@code null} when unset */
    public String getTtsHttpToken() {
        return ttsHttpToken;
    }

    /** @return the {@code TtsEngineName} value, or {@code null} when unset */
    public String getTtsEngineName() {
        return ttsEngineName;
    }

    /** @return the {@code TtsDefaultVoice} value, or {@code null} when unset */
    public String getTtsDefaultVoice() {
        return ttsDefaultVoice;
    }

    /** @return the {@code TtsDefaultLanguage} value, or {@code null} when unset */
    public String getTtsDefaultLanguage() {
        return ttsDefaultLanguage;
    }

    /** @return the {@code AwsBucket} value, or {@code null} when unset */
    public String getAwsBucket() {
        return awsBucket;
    }

    /** @return the {@code AwsRegion} value, or {@code null} when unset */
    public String getAwsRegion() {
        return awsRegion;
    }

    /** @return the {@code AwsPrefix} value, or {@code null} when unset */
    public String getAwsPrefix() {
        return awsPrefix;
    }

    /** @return the {@code AwsAccessKeyId} value, or {@code null} when unset */
    public String getAwsAccessKeyId() {
        return awsAccessKeyId;
    }

    /** @return the {@code AwsSecretAccessKey} value, or {@code null} when unset */
    public String getAwsSecretAccessKey() {
        return awsSecretAccessKey;
    }

    /** @return the {@code AwsKmsKeyId} value, or {@code null} when unset */
    public String getAwsKmsKeyId() {
        return awsKmsKeyId;
    }

    /** @return the {@code AllowCidr} value, or {@code null} when unset */
    public List<String> getAllowCidr() {
        return allowCidr;
    }

    /** @return a new builder */
    public static Builder builder() {
        return new Builder();
    }

    /** Builder for {@link UpdateAccountRequest}. */
    public static final class Builder {
        private String friendlyName;
        private String voiceUrl;
        private String voiceMethod;
        private String voiceFallbackUrl;
        private String voiceFallbackMethod;
        private String sttFsVendor;
        private String sttHttpUrl;
        private String sttHttpToken;
        private String ttsHttpUrl;
        private String ttsHttpToken;
        private String ttsEngineName;
        private String ttsDefaultVoice;
        private String ttsDefaultLanguage;
        private String awsBucket;
        private String awsRegion;
        private String awsPrefix;
        private String awsAccessKeyId;
        private String awsSecretAccessKey;
        private String awsKmsKeyId;
        private List<String> allowCidr;

        /** Create an empty builder; prefer {@link UpdateAccountRequest#builder()}. */
        public Builder() {
            // all fields start unset
        }

        /**
         * @param v {@code FriendlyName}
         * @return this builder
         */
        public Builder friendlyName(String v) {
            this.friendlyName = v;
            return this;
        }

        /**
         * @param v {@code VoiceUrl}
         * @return this builder
         */
        public Builder voiceUrl(String v) {
            this.voiceUrl = v;
            return this;
        }

        /**
         * @param v {@code VoiceMethod}: one of {@code GET}, {@code POST}
         * @return this builder
         */
        public Builder voiceMethod(String v) {
            this.voiceMethod = v;
            return this;
        }

        /**
         * @param v {@code VoiceFallbackUrl}
         * @return this builder
         */
        public Builder voiceFallbackUrl(String v) {
            this.voiceFallbackUrl = v;
            return this;
        }

        /**
         * @param v {@code VoiceFallbackMethod}: one of {@code GET}, {@code POST}
         * @return this builder
         */
        public Builder voiceFallbackMethod(String v) {
            this.voiceFallbackMethod = v;
            return this;
        }

        /**
         * @param v {@code SttFsVendor}
         * @return this builder
         */
        public Builder sttFsVendor(String v) {
            this.sttFsVendor = v;
            return this;
        }

        /**
         * @param v {@code SttHttpUrl}
         * @return this builder
         */
        public Builder sttHttpUrl(String v) {
            this.sttHttpUrl = v;
            return this;
        }

        /**
         * @param v {@code SttHttpToken}
         * @return this builder
         */
        public Builder sttHttpToken(String v) {
            this.sttHttpToken = v;
            return this;
        }

        /**
         * @param v {@code TtsHttpUrl}
         * @return this builder
         */
        public Builder ttsHttpUrl(String v) {
            this.ttsHttpUrl = v;
            return this;
        }

        /**
         * @param v {@code TtsHttpToken}
         * @return this builder
         */
        public Builder ttsHttpToken(String v) {
            this.ttsHttpToken = v;
            return this;
        }

        /**
         * @param v {@code TtsEngineName}
         * @return this builder
         */
        public Builder ttsEngineName(String v) {
            this.ttsEngineName = v;
            return this;
        }

        /**
         * @param v {@code TtsDefaultVoice}
         * @return this builder
         */
        public Builder ttsDefaultVoice(String v) {
            this.ttsDefaultVoice = v;
            return this;
        }

        /**
         * @param v {@code TtsDefaultLanguage}
         * @return this builder
         */
        public Builder ttsDefaultLanguage(String v) {
            this.ttsDefaultLanguage = v;
            return this;
        }

        /**
         * @param v {@code AwsBucket}
         * @return this builder
         */
        public Builder awsBucket(String v) {
            this.awsBucket = v;
            return this;
        }

        /**
         * @param v {@code AwsRegion}
         * @return this builder
         */
        public Builder awsRegion(String v) {
            this.awsRegion = v;
            return this;
        }

        /**
         * @param v {@code AwsPrefix}
         * @return this builder
         */
        public Builder awsPrefix(String v) {
            this.awsPrefix = v;
            return this;
        }

        /**
         * @param v {@code AwsAccessKeyId}
         * @return this builder
         */
        public Builder awsAccessKeyId(String v) {
            this.awsAccessKeyId = v;
            return this;
        }

        /**
         * @param v {@code AwsSecretAccessKey}
         * @return this builder
         */
        public Builder awsSecretAccessKey(String v) {
            this.awsSecretAccessKey = v;
            return this;
        }

        /**
         * @param v {@code AwsKmsKeyId}
         * @return this builder
         */
        public Builder awsKmsKeyId(String v) {
            this.awsKmsKeyId = v;
            return this;
        }

        /**
         * @param v {@code AllowCidr}
         * @return this builder
         */
        public Builder allowCidr(List<String> v) {
            this.allowCidr = v;
            return this;
        }

        /**
         * @return the request
         */
        public UpdateAccountRequest build() {
            return new UpdateAccountRequest(this);
        }
    }
}
