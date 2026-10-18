package com.voicetel.voiceml.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;
import java.util.StringJoiner;

/**
 * The account view returned by key rotation
 * ({@code POST /2010-04-01/Accounts/{AccountSid}/RotateKey.json}): the account configuration plus
 * the freshly generated {@code api_key}.
 *
 * <p>No read endpoint returns the key, so store it as soon as the response arrives; requests using
 * the previous key fail with 401 from that point on. The {@link #toString()} output never includes
 * the {@code api_key}.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class AccountWithKey {

    @JsonProperty("sid")
    private String sid;

    @JsonProperty("api_key")
    private String apiKey;

    @JsonProperty("sip_username")
    private String sipUsername;

    @JsonProperty("name")
    private String name;

    @JsonProperty("phone_regions")
    private List<String> phoneRegions = new ArrayList<>();

    @JsonProperty("allow_cidr")
    private List<String> allowCidr = new ArrayList<>();

    @JsonProperty("disabled")
    private Boolean disabled;

    @JsonProperty("stt_fs_vendor")
    private String sttFsVendor;

    @JsonProperty("stt_http_url")
    private String sttHttpUrl;

    @JsonProperty("stt_http_token_set")
    private Boolean sttHttpTokenSet;

    @JsonProperty("tts_http_url")
    private String ttsHttpUrl;

    @JsonProperty("tts_http_token_set")
    private Boolean ttsHttpTokenSet;

    @JsonProperty("tts_engine_name")
    private String ttsEngineName;

    @JsonProperty("tts_default_voice")
    private String ttsDefaultVoice;

    @JsonProperty("tts_default_language")
    private String ttsDefaultLanguage;

    @JsonProperty("stripe_secret_key_set")
    private Boolean stripeSecretKeySet;

    @JsonProperty("pay_enabled")
    private Boolean payEnabled;

    @JsonProperty("sms_provider")
    private String smsProvider;

    @JsonProperty("sms_allowed_providers")
    private List<String> smsAllowedProviders = new ArrayList<>();

    @JsonProperty("stt_allowed_providers")
    private List<String> sttAllowedProviders = new ArrayList<>();

    @JsonProperty("stt_provider")
    private String sttProvider;

    @JsonProperty("voice_url")
    private String voiceUrl;

    @JsonProperty("voice_method")
    private String voiceMethod;

    @JsonProperty("voice_fallback_url")
    private String voiceFallbackUrl;

    @JsonProperty("voice_fallback_method")
    private String voiceFallbackMethod;

    @JsonProperty("aws_bucket")
    private String awsBucket;

    @JsonProperty("aws_region")
    private String awsRegion;

    @JsonProperty("aws_prefix")
    private String awsPrefix;

    @JsonProperty("aws_access_key_id")
    private String awsAccessKeyId;

    @JsonProperty("aws_secret_access_key_set")
    private Boolean awsSecretAccessKeySet;

    @JsonProperty("aws_kms_key_id")
    private String awsKmsKeyId;

    @JsonProperty("created_at")
    private String createdAt;

    @JsonProperty("updated_at")
    private String updatedAt;

    /** @return the {@code sid} field */
    public String getSid() {
        return sid;
    }

    /** @param sid the {@code sid} field */
    public void setSid(String sid) {
        this.sid = sid;
    }

    /** @return the freshly generated {@code api_key}; store it, no read endpoint returns it */
    public String getApiKey() {
        return apiKey;
    }

    /** @param apiKey the {@code api_key} field */
    public void setApiKey(String apiKey) {
        this.apiKey = apiKey;
    }

    /** @return the {@code sip_username} field */
    public String getSipUsername() {
        return sipUsername;
    }

    /** @param sipUsername the {@code sip_username} field */
    public void setSipUsername(String sipUsername) {
        this.sipUsername = sipUsername;
    }

    /** @return the {@code name} field */
    public String getName() {
        return name;
    }

    /** @param name the {@code name} field */
    public void setName(String name) {
        this.name = name;
    }

    /** @return the {@code phone_regions} field */
    public List<String> getPhoneRegions() {
        return phoneRegions;
    }

    /** @param phoneRegions the {@code phone_regions} field */
    public void setPhoneRegions(List<String> phoneRegions) {
        this.phoneRegions = phoneRegions != null ? phoneRegions : new ArrayList<>();
    }

    /** @return the {@code allow_cidr} field */
    public List<String> getAllowCidr() {
        return allowCidr;
    }

    /** @param allowCidr the {@code allow_cidr} field */
    public void setAllowCidr(List<String> allowCidr) {
        this.allowCidr = allowCidr != null ? allowCidr : new ArrayList<>();
    }

    /** @return the {@code disabled} field */
    public Boolean getDisabled() {
        return disabled;
    }

    /** @param disabled the {@code disabled} field */
    public void setDisabled(Boolean disabled) {
        this.disabled = disabled;
    }

    /** @return the {@code stt_fs_vendor} field */
    public String getSttFsVendor() {
        return sttFsVendor;
    }

    /** @param sttFsVendor the {@code stt_fs_vendor} field */
    public void setSttFsVendor(String sttFsVendor) {
        this.sttFsVendor = sttFsVendor;
    }

    /** @return the {@code stt_http_url} field */
    public String getSttHttpUrl() {
        return sttHttpUrl;
    }

    /** @param sttHttpUrl the {@code stt_http_url} field */
    public void setSttHttpUrl(String sttHttpUrl) {
        this.sttHttpUrl = sttHttpUrl;
    }

    /** @return the {@code stt_http_token_set} field */
    public Boolean getSttHttpTokenSet() {
        return sttHttpTokenSet;
    }

    /** @param sttHttpTokenSet the {@code stt_http_token_set} field */
    public void setSttHttpTokenSet(Boolean sttHttpTokenSet) {
        this.sttHttpTokenSet = sttHttpTokenSet;
    }

    /** @return the {@code tts_http_url} field */
    public String getTtsHttpUrl() {
        return ttsHttpUrl;
    }

    /** @param ttsHttpUrl the {@code tts_http_url} field */
    public void setTtsHttpUrl(String ttsHttpUrl) {
        this.ttsHttpUrl = ttsHttpUrl;
    }

    /** @return the {@code tts_http_token_set} field */
    public Boolean getTtsHttpTokenSet() {
        return ttsHttpTokenSet;
    }

    /** @param ttsHttpTokenSet the {@code tts_http_token_set} field */
    public void setTtsHttpTokenSet(Boolean ttsHttpTokenSet) {
        this.ttsHttpTokenSet = ttsHttpTokenSet;
    }

    /** @return the {@code tts_engine_name} field */
    public String getTtsEngineName() {
        return ttsEngineName;
    }

    /** @param ttsEngineName the {@code tts_engine_name} field */
    public void setTtsEngineName(String ttsEngineName) {
        this.ttsEngineName = ttsEngineName;
    }

    /** @return the {@code tts_default_voice} field */
    public String getTtsDefaultVoice() {
        return ttsDefaultVoice;
    }

    /** @param ttsDefaultVoice the {@code tts_default_voice} field */
    public void setTtsDefaultVoice(String ttsDefaultVoice) {
        this.ttsDefaultVoice = ttsDefaultVoice;
    }

    /** @return the {@code tts_default_language} field */
    public String getTtsDefaultLanguage() {
        return ttsDefaultLanguage;
    }

    /** @param ttsDefaultLanguage the {@code tts_default_language} field */
    public void setTtsDefaultLanguage(String ttsDefaultLanguage) {
        this.ttsDefaultLanguage = ttsDefaultLanguage;
    }

    /** @return the {@code stripe_secret_key_set} field */
    public Boolean getStripeSecretKeySet() {
        return stripeSecretKeySet;
    }

    /** @param stripeSecretKeySet the {@code stripe_secret_key_set} field */
    public void setStripeSecretKeySet(Boolean stripeSecretKeySet) {
        this.stripeSecretKeySet = stripeSecretKeySet;
    }

    /** @return the {@code pay_enabled} field */
    public Boolean getPayEnabled() {
        return payEnabled;
    }

    /** @param payEnabled the {@code pay_enabled} field */
    public void setPayEnabled(Boolean payEnabled) {
        this.payEnabled = payEnabled;
    }

    /** @return the {@code sms_provider} field */
    public String getSmsProvider() {
        return smsProvider;
    }

    /** @param smsProvider the {@code sms_provider} field */
    public void setSmsProvider(String smsProvider) {
        this.smsProvider = smsProvider;
    }

    /** @return the {@code sms_allowed_providers} field */
    public List<String> getSmsAllowedProviders() {
        return smsAllowedProviders;
    }

    /** @param smsAllowedProviders the {@code sms_allowed_providers} field */
    public void setSmsAllowedProviders(List<String> smsAllowedProviders) {
        this.smsAllowedProviders = smsAllowedProviders != null ? smsAllowedProviders : new ArrayList<>();
    }

    /** @return the {@code stt_allowed_providers} field */
    public List<String> getSttAllowedProviders() {
        return sttAllowedProviders;
    }

    /** @param sttAllowedProviders the {@code stt_allowed_providers} field */
    public void setSttAllowedProviders(List<String> sttAllowedProviders) {
        this.sttAllowedProviders = sttAllowedProviders != null ? sttAllowedProviders : new ArrayList<>();
    }

    /** @return the {@code stt_provider} field */
    public String getSttProvider() {
        return sttProvider;
    }

    /** @param sttProvider the {@code stt_provider} field */
    public void setSttProvider(String sttProvider) {
        this.sttProvider = sttProvider;
    }

    /** @return the {@code voice_url} field */
    public String getVoiceUrl() {
        return voiceUrl;
    }

    /** @param voiceUrl the {@code voice_url} field */
    public void setVoiceUrl(String voiceUrl) {
        this.voiceUrl = voiceUrl;
    }

    /** @return the {@code voice_method} field */
    public String getVoiceMethod() {
        return voiceMethod;
    }

    /** @param voiceMethod the {@code voice_method} field */
    public void setVoiceMethod(String voiceMethod) {
        this.voiceMethod = voiceMethod;
    }

    /** @return the {@code voice_fallback_url} field */
    public String getVoiceFallbackUrl() {
        return voiceFallbackUrl;
    }

    /** @param voiceFallbackUrl the {@code voice_fallback_url} field */
    public void setVoiceFallbackUrl(String voiceFallbackUrl) {
        this.voiceFallbackUrl = voiceFallbackUrl;
    }

    /** @return the {@code voice_fallback_method} field */
    public String getVoiceFallbackMethod() {
        return voiceFallbackMethod;
    }

    /** @param voiceFallbackMethod the {@code voice_fallback_method} field */
    public void setVoiceFallbackMethod(String voiceFallbackMethod) {
        this.voiceFallbackMethod = voiceFallbackMethod;
    }

    /** @return the {@code aws_bucket} field */
    public String getAwsBucket() {
        return awsBucket;
    }

    /** @param awsBucket the {@code aws_bucket} field */
    public void setAwsBucket(String awsBucket) {
        this.awsBucket = awsBucket;
    }

    /** @return the {@code aws_region} field */
    public String getAwsRegion() {
        return awsRegion;
    }

    /** @param awsRegion the {@code aws_region} field */
    public void setAwsRegion(String awsRegion) {
        this.awsRegion = awsRegion;
    }

    /** @return the {@code aws_prefix} field */
    public String getAwsPrefix() {
        return awsPrefix;
    }

    /** @param awsPrefix the {@code aws_prefix} field */
    public void setAwsPrefix(String awsPrefix) {
        this.awsPrefix = awsPrefix;
    }

    /** @return the {@code aws_access_key_id} field */
    public String getAwsAccessKeyId() {
        return awsAccessKeyId;
    }

    /** @param awsAccessKeyId the {@code aws_access_key_id} field */
    public void setAwsAccessKeyId(String awsAccessKeyId) {
        this.awsAccessKeyId = awsAccessKeyId;
    }

    /** @return the {@code aws_secret_access_key_set} field */
    public Boolean getAwsSecretAccessKeySet() {
        return awsSecretAccessKeySet;
    }

    /** @param awsSecretAccessKeySet the {@code aws_secret_access_key_set} field */
    public void setAwsSecretAccessKeySet(Boolean awsSecretAccessKeySet) {
        this.awsSecretAccessKeySet = awsSecretAccessKeySet;
    }

    /** @return the {@code aws_kms_key_id} field */
    public String getAwsKmsKeyId() {
        return awsKmsKeyId;
    }

    /** @param awsKmsKeyId the {@code aws_kms_key_id} field */
    public void setAwsKmsKeyId(String awsKmsKeyId) {
        this.awsKmsKeyId = awsKmsKeyId;
    }

    /** @return the {@code created_at} field */
    public String getCreatedAt() {
        return createdAt;
    }

    /** @param createdAt the {@code created_at} field */
    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    /** @return the {@code updated_at} field */
    public String getUpdatedAt() {
        return updatedAt;
    }

    /** @param updatedAt the {@code updated_at} field */
    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    /**
     * Every field except the secret {@code api_key}, which renders as {@code [redacted]}.
     *
     * @return a debug rendering that never contains the rotated key
     */
    @Override
    public String toString() {
        return new StringJoiner(", ", AccountWithKey.class.getSimpleName() + "{", "}")
                .add("sid=" + sid)
                .add("apiKey=[redacted]")
                .add("sipUsername=" + sipUsername)
                .add("name=" + name)
                .add("phoneRegions=" + phoneRegions)
                .add("allowCidr=" + allowCidr)
                .add("disabled=" + disabled)
                .add("sttFsVendor=" + sttFsVendor)
                .add("sttHttpUrl=" + sttHttpUrl)
                .add("sttHttpTokenSet=" + sttHttpTokenSet)
                .add("ttsHttpUrl=" + ttsHttpUrl)
                .add("ttsHttpTokenSet=" + ttsHttpTokenSet)
                .add("ttsEngineName=" + ttsEngineName)
                .add("ttsDefaultVoice=" + ttsDefaultVoice)
                .add("ttsDefaultLanguage=" + ttsDefaultLanguage)
                .add("stripeSecretKeySet=" + stripeSecretKeySet)
                .add("payEnabled=" + payEnabled)
                .add("smsProvider=" + smsProvider)
                .add("smsAllowedProviders=" + smsAllowedProviders)
                .add("sttAllowedProviders=" + sttAllowedProviders)
                .add("sttProvider=" + sttProvider)
                .add("voiceUrl=" + voiceUrl)
                .add("voiceMethod=" + voiceMethod)
                .add("voiceFallbackUrl=" + voiceFallbackUrl)
                .add("voiceFallbackMethod=" + voiceFallbackMethod)
                .add("awsBucket=" + awsBucket)
                .add("awsRegion=" + awsRegion)
                .add("awsPrefix=" + awsPrefix)
                .add("awsAccessKeyId=" + awsAccessKeyId)
                .add("awsSecretAccessKeySet=" + awsSecretAccessKeySet)
                .add("awsKmsKeyId=" + awsKmsKeyId)
                .add("createdAt=" + createdAt)
                .add("updatedAt=" + updatedAt)
                .toString();
    }
}
