package com.voicetel.voiceml;

import com.voicetel.voiceml.exceptions.ConfigurationException;

import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.time.Duration;
import java.util.Objects;

/**
 * Immutable configuration for {@link VoicemlClient}.
 *
 * <p>Build via {@link #builder()}. All fields are validated at construction; invalid or
 * conflicting values throw {@link ConfigurationException}.
 *
 * <p>{@link #toString()} never includes the API key.
 */
public final class ClientOptions {

    /** Default production base URL for the VoiceML REST API. */
    public static final String DEFAULT_BASE_URL = "https://voiceml.voicetel.com";

    /** Default per-request timeout. */
    public static final Duration DEFAULT_TIMEOUT = Duration.ofSeconds(30);

    /**
     * Default retry count. Idempotent requests ({@code GET}, {@code HEAD}, {@code PUT},
     * {@code DELETE}) are retried on {@code 429}, {@code 500}, {@code 502}, {@code 503},
     * {@code 504} and on network errors; {@code POST} and {@code PATCH} only on {@code 429} and on
     * connection failures where the request provably was not sent.
     */
    public static final int DEFAULT_MAX_RETRIES = 2;

    private final String accountSid;
    private final String apiKey;
    private final String baseUrl;
    private final String messagingBaseUrlOverride;
    private final String conversationsBaseUrlOverride;
    private final Duration timeout;
    private final int maxRetries;
    private final String userAgent;
    private final HttpClient httpClient;

    private ClientOptions(Builder b, boolean requireCredentials) {
        if (b.apiKey != null && !b.apiKey.isEmpty()
                && b.authToken != null && !b.authToken.isEmpty()) {
            throw new ConfigurationException(
                    "set either apiKey or authToken, not both: they are aliases");
        }
        String resolvedKey = (b.apiKey != null && !b.apiKey.isEmpty()) ? b.apiKey : b.authToken;
        if (requireCredentials) {
            if (b.accountSid == null || b.accountSid.isEmpty()) {
                throw new ConfigurationException("accountSid is required");
            }
            if (resolvedKey == null || resolvedKey.isEmpty()) {
                throw new ConfigurationException("apiKey is required");
            }
        }
        if (b.maxRetries < 0) {
            throw new ConfigurationException("maxRetries must be >= 0");
        }
        if (b.timeout != null && (b.timeout.isNegative() || b.timeout.isZero())) {
            throw new ConfigurationException("timeout must be positive");
        }
        this.accountSid = requireCredentials ? b.accountSid : null;
        this.apiKey = requireCredentials ? resolvedKey : null;
        this.baseUrl = validateBaseUrl("baseUrl", b.baseUrl != null ? b.baseUrl : DEFAULT_BASE_URL);
        this.messagingBaseUrlOverride = b.messagingBaseUrl == null
                ? null : validateBaseUrl("messagingBaseUrl", b.messagingBaseUrl);
        this.conversationsBaseUrlOverride = b.conversationsBaseUrl == null
                ? null : validateBaseUrl("conversationsBaseUrl", b.conversationsBaseUrl);
        this.timeout = Objects.requireNonNullElse(b.timeout, DEFAULT_TIMEOUT);
        this.maxRetries = b.maxRetries;
        this.userAgent = b.userAgent != null
                ? b.userAgent
                : "voiceml-java/" + Version.VERSION
                        + " (+https://github.com/voicetel/voiceml-java-sdk)";
        this.httpClient = b.httpClient;
    }

    /**
     * The AccountSid sent as the HTTP Basic username.
     *
     * @return the AccountSid, or {@code null} for a diagnostics-only configuration
     */
    public String getAccountSid() {
        return accountSid;
    }

    /**
     * The VoiceML API key sent as the HTTP Basic password.
     *
     * @return the key, or {@code null} for a diagnostics-only configuration
     */
    public String getApiKey() {
        return apiKey;
    }

    /** Whether this configuration carries credentials (false only for diagnostics-only use). */
    boolean hasCredentials() {
        return accountSid != null && apiKey != null;
    }

    /**
     * The base URL for everything except the Messaging Service and Conversations groups.
     *
     * @return the URL, without a trailing slash
     */
    public String getBaseUrl() {
        return baseUrl;
    }

    /**
     * Effective base URL for the Messaging Service group ({@code client.messagingV1()}).
     *
     * <p>An explicit {@code messagingBaseUrl(...)} override wins; otherwise the host is derived
     * from {@link #getBaseUrl()} by swapping the {@code voiceml} label for {@code messaging} on
     * recognised {@code *.voicetel.com} hosts, falling back to the base URL unchanged for
     * single-host deployments.
     *
     * @return the URL, without a trailing slash
     */
    public String getMessagingBaseUrl() {
        return messagingBaseUrlOverride != null
                ? messagingBaseUrlOverride
                : Hosts.deriveProductHost(baseUrl, "messaging");
    }

    /**
     * Effective base URL for the Conversations group ({@code client.conversationsV1()}).
     *
     * <p>An explicit {@code conversationsBaseUrl(...)} override wins; otherwise the host is derived
     * from {@link #getBaseUrl()} by swapping the {@code voiceml} label for {@code conversations}.
     *
     * @return the URL, without a trailing slash
     */
    public String getConversationsBaseUrl() {
        return conversationsBaseUrlOverride != null
                ? conversationsBaseUrlOverride
                : Hosts.deriveProductHost(baseUrl, "conversations");
    }

    /** The explicit Messaging Service override, or {@code null} when the host is derived. */
    String getMessagingBaseUrlOverride() {
        return messagingBaseUrlOverride;
    }

    /** The explicit Conversations override, or {@code null} when the host is derived. */
    String getConversationsBaseUrlOverride() {
        return conversationsBaseUrlOverride;
    }

    /**
     * Per-request timeout, applied to every attempt.
     *
     * @return the timeout
     */
    public Duration getTimeout() {
        return timeout;
    }

    /**
     * Maximum number of retries after the first attempt.
     *
     * @return the retry count; {@code 0} disables retries
     */
    public int getMaxRetries() {
        return maxRetries;
    }

    /**
     * The {@code User-Agent} header sent on every request.
     *
     * @return the header value
     */
    public String getUserAgent() {
        return userAgent;
    }

    /**
     * The caller-provided client.
     *
     * @return the client, or {@code null} to let the transport build a default
     */
    public HttpClient getHttpClient() {
        return httpClient;
    }

    /**
     * Start building options.
     *
     * @return a new builder
     */
    public static Builder builder() {
        return new Builder();
    }

    /** Redacted description: the API key is never included. */
    @Override
    public String toString() {
        return "ClientOptions(accountSid=" + accountSid
                + ", apiKey=" + (apiKey == null ? "null" : "[redacted]")
                + ", baseUrl=" + baseUrl
                + ", messagingBaseUrl=" + getMessagingBaseUrl()
                + ", conversationsBaseUrl=" + getConversationsBaseUrl()
                + ", timeout=" + timeout
                + ", maxRetries=" + maxRetries
                + ")";
    }

    private static String validateBaseUrl(String field, String url) {
        String stripped = Hosts.stripTrailingSlash(url);
        URI uri;
        try {
            uri = new URI(stripped);
        } catch (URISyntaxException ex) {
            throw new ConfigurationException(field + " is not a valid URL: " + url);
        }
        String scheme = uri.getScheme();
        if (scheme == null || !(scheme.equalsIgnoreCase("https") || scheme.equalsIgnoreCase("http"))) {
            throw new ConfigurationException(field + " must be an absolute http(s) URL: " + url);
        }
        if (uri.getHost() == null || uri.getHost().isEmpty()) {
            throw new ConfigurationException(field + " must include a host: " + url);
        }
        if (uri.getRawUserInfo() != null || uri.getRawQuery() != null || uri.getRawFragment() != null) {
            throw new ConfigurationException(
                    field + " must not carry user info, a query string or a fragment: " + url);
        }
        return stripped;
    }

    /** Fluent builder for {@link ClientOptions}. */
    public static final class Builder {
        private String accountSid;
        private String apiKey;
        private String authToken;
        private String baseUrl;
        private String messagingBaseUrl;
        private String conversationsBaseUrl;
        private Duration timeout;
        private int maxRetries = DEFAULT_MAX_RETRIES;
        private String userAgent;
        private HttpClient httpClient;

        /** Create an empty builder; prefer {@link ClientOptions#builder()}. */
        public Builder() {
            // defaults are set on the fields
        }

        /**
         * Your VoiceML AccountSid ({@code AC} + 32 hex). Sent as the HTTP Basic username.
         *
         * @param accountSid the AccountSid
         * @return this builder
         */
        public Builder accountSid(String accountSid) {
            this.accountSid = accountSid;
            return this;
        }

        /**
         * Your VoiceML API key. Sent as the HTTP Basic password.
         *
         * @param apiKey the API key
         * @return this builder
         */
        public Builder apiKey(String apiKey) {
            this.apiKey = apiKey;
            return this;
        }

        /**
         * Alias for {@link #apiKey(String)} under the name twilio-java users expect. The value is
         * still your VoiceML API key; Twilio-issued auth tokens do not authenticate against
         * VoiceML. Setting both {@code apiKey(...)} and {@code authToken(...)} on the same builder
         * is rejected at {@link #build()} time with {@link ConfigurationException}.
         *
         * @param authToken the VoiceML API key
         * @return this builder
         */
        public Builder authToken(String authToken) {
            this.authToken = authToken;
            return this;
        }

        /**
         * Override the server base URL (an absolute {@code http(s)} URL). Useful for staging or
         * VPC endpoints.
         *
         * @param baseUrl the base URL
         * @return this builder
         */
        public Builder baseUrl(String baseUrl) {
            this.baseUrl = baseUrl;
            return this;
        }

        /**
         * Override the base URL for the Messaging Service group ({@code client.messagingV1()}).
         * When unset it is derived from {@link #baseUrl(String)} (swap the {@code voiceml} label
         * for {@code messaging} on {@code *.voicetel.com} hosts, single-host fallback otherwise).
         * Set this to reach Messaging Service on a custom subdomain in a self-hosted deployment.
         *
         * @param messagingBaseUrl the Messaging Service base URL
         * @return this builder
         */
        public Builder messagingBaseUrl(String messagingBaseUrl) {
            this.messagingBaseUrl = messagingBaseUrl;
            return this;
        }

        /**
         * Override the base URL for the Conversations group ({@code client.conversationsV1()}).
         * When unset it is derived from {@link #baseUrl(String)} (swap the {@code voiceml} label
         * for {@code conversations} on {@code *.voicetel.com} hosts, single-host fallback
         * otherwise).
         *
         * @param conversationsBaseUrl the Conversations base URL
         * @return this builder
         */
        public Builder conversationsBaseUrl(String conversationsBaseUrl) {
            this.conversationsBaseUrl = conversationsBaseUrl;
            return this;
        }

        /**
         * Per-request timeout. Default 30 seconds.
         *
         * @param timeout a positive duration
         * @return this builder
         */
        public Builder timeout(Duration timeout) {
            this.timeout = timeout;
            return this;
        }

        /**
         * Retry count. Default 2 ({@code 0} disables retries). See
         * {@link ClientOptions#DEFAULT_MAX_RETRIES} for which failures are retried.
         *
         * @param maxRetries retries after the first attempt
         * @return this builder
         */
        public Builder maxRetries(int maxRetries) {
            this.maxRetries = maxRetries;
            return this;
        }

        /**
         * Override the {@code User-Agent} header.
         *
         * @param userAgent the header value
         * @return this builder
         */
        public Builder userAgent(String userAgent) {
            this.userAgent = userAgent;
            return this;
        }

        /**
         * Supply a pre-built {@link HttpClient} (proxy, {@code SSLContext}, executor). The transport
         * uses it for every request, including recording audio, exports and diagnostics. Leave its
         * redirect policy at the default {@link HttpClient.Redirect#NEVER} so the SDK can apply its
         * own redirect rules (drop {@code Authorization} on a host change, refuse https to http).
         *
         * @param httpClient the client
         * @return this builder
         */
        public Builder httpClient(HttpClient httpClient) {
            this.httpClient = httpClient;
            return this;
        }

        /**
         * Validate and build the options.
         *
         * @return the options
         * @throws ConfigurationException when a value is missing, invalid or conflicting
         */
        public ClientOptions build() {
            return new ClientOptions(this, true);
        }

        /** Build options for the unauthenticated diagnostics endpoints; credentials are dropped. */
        ClientOptions buildWithoutCredentials() {
            return new ClientOptions(this, false);
        }
    }
}
