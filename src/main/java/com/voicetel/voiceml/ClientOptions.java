package com.voicetel.voiceml;

import com.voicetel.voiceml.exceptions.ConfigurationException;

import java.net.http.HttpClient;
import java.time.Duration;
import java.util.Objects;

/**
 * Immutable configuration for {@link VoicemlClient}.
 *
 * <p>Build via {@link #builder()}. All fields are validated at construction; invalid values
 * throw {@link ConfigurationException}.
 */
public final class ClientOptions {

    /** Default production base URL for the VoiceML REST API. */
    public static final String DEFAULT_BASE_URL = "https://voiceml.voicetel.com";

    /** Default per-request timeout. */
    public static final Duration DEFAULT_TIMEOUT = Duration.ofSeconds(30);

    /** Default retry count for transient failures (5xx, 429, transport errors). */
    public static final int DEFAULT_MAX_RETRIES = 2;

    private final String accountSid;
    private final String apiKey;
    private final String baseUrl;
    private final Duration timeout;
    private final int maxRetries;
    private final String userAgent;
    private final HttpClient httpClient;

    private ClientOptions(Builder b) {
        if (b.accountSid == null || b.accountSid.isEmpty()) {
            throw new ConfigurationException("accountSid is required");
        }
        if (b.apiKey != null && !b.apiKey.isEmpty()
                && b.authToken != null && !b.authToken.isEmpty()) {
            throw new IllegalStateException(
                    "set either apiKey or authToken, not both — they are aliases");
        }
        String resolvedKey = (b.apiKey != null && !b.apiKey.isEmpty()) ? b.apiKey : b.authToken;
        if (resolvedKey == null || resolvedKey.isEmpty()) {
            throw new ConfigurationException("apiKey is required");
        }
        if (b.maxRetries < 0) {
            throw new ConfigurationException("maxRetries must be >= 0");
        }
        this.accountSid = b.accountSid;
        this.apiKey = resolvedKey;
        this.baseUrl = stripTrailingSlash(b.baseUrl != null ? b.baseUrl : DEFAULT_BASE_URL);
        this.timeout = Objects.requireNonNullElse(b.timeout, DEFAULT_TIMEOUT);
        this.maxRetries = b.maxRetries;
        this.userAgent = b.userAgent != null
                ? b.userAgent
                : "voiceml-java/" + Version.VERSION
                        + " (+https://github.com/voicetel/voiceml-java-sdk)";
        this.httpClient = b.httpClient;
    }

    public String getAccountSid() {
        return accountSid;
    }

    public String getApiKey() {
        return apiKey;
    }

    public String getBaseUrl() {
        return baseUrl;
    }

    public Duration getTimeout() {
        return timeout;
    }

    public int getMaxRetries() {
        return maxRetries;
    }

    public String getUserAgent() {
        return userAgent;
    }

    /** Caller-provided HttpClient, or {@code null} to let the transport build a default. */
    public HttpClient getHttpClient() {
        return httpClient;
    }

    public static Builder builder() {
        return new Builder();
    }

    private static String stripTrailingSlash(String url) {
        return url.endsWith("/") ? url.substring(0, url.length() - 1) : url;
    }

    /** Fluent builder for {@link ClientOptions}. */
    public static final class Builder {
        private String accountSid;
        private String apiKey;
        private String authToken;
        private String baseUrl;
        private Duration timeout;
        private int maxRetries = DEFAULT_MAX_RETRIES;
        private String userAgent;
        private HttpClient httpClient;

        /** Twilio-format AccountSid ({@code AC} + 32 hex). Sent as the HTTP Basic username. */
        public Builder accountSid(String accountSid) {
            this.accountSid = accountSid;
            return this;
        }

        /** Per-tenant API key. Sent as the HTTP Basic password. */
        public Builder apiKey(String apiKey) {
            this.apiKey = apiKey;
            return this;
        }

        /**
         * Twilio-named alias for {@link #apiKey(String)} — set the HTTP Basic password under the
         * name twilio-java users expect. Setting both {@code apiKey(...)} and
         * {@code authToken(...)} on the same builder is rejected at {@link #build()} time with
         * {@link IllegalStateException}.
         */
        public Builder authToken(String authToken) {
            this.authToken = authToken;
            return this;
        }

        /** Override the server base URL. Useful for staging or VPC endpoints. */
        public Builder baseUrl(String baseUrl) {
            this.baseUrl = baseUrl;
            return this;
        }

        /** Per-request timeout. Default 30 seconds. */
        public Builder timeout(Duration timeout) {
            this.timeout = timeout;
            return this;
        }

        /** Retry count for 5xx/429/transport errors. Default 2 ({@code 0} disables). */
        public Builder maxRetries(int maxRetries) {
            this.maxRetries = maxRetries;
            return this;
        }

        /** Override the {@code User-Agent} header. */
        public Builder userAgent(String userAgent) {
            this.userAgent = userAgent;
            return this;
        }

        /** Supply a pre-built {@link HttpClient}. The transport will use it instead of constructing one. */
        public Builder httpClient(HttpClient httpClient) {
            this.httpClient = httpClient;
            return this;
        }

        public ClientOptions build() {
            return new ClientOptions(this);
        }
    }
}
