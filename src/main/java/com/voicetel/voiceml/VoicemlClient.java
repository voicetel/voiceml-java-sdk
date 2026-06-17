package com.voicetel.voiceml;

import com.voicetel.voiceml.resources.ApplicationsResource;
import com.voicetel.voiceml.resources.CallsResource;
import com.voicetel.voiceml.resources.ConferencesResource;
import com.voicetel.voiceml.resources.DiagnosticsResource;
import com.voicetel.voiceml.resources.IncomingPhoneNumbersResource;
import com.voicetel.voiceml.resources.MessagesResource;
import com.voicetel.voiceml.resources.NotificationsResource;
import com.voicetel.voiceml.resources.QueuesResource;
import com.voicetel.voiceml.resources.RecordingsResource;
import com.voicetel.voiceml.resources.SipResource;

/**
 * Synchronous client for the VoiceML REST API.
 *
 * <p>VoiceML uses HTTP Basic auth: the {@code accountSid} (Twilio-format {@code AC} + 32 hex) is
 * the username and {@code apiKey} is the password. Drop-in compatible with Twilio's Java client
 * constructor shape (same Basic auth, same wire body, same response envelope).
 *
 * <p>Async surfaces are deliberately omitted in this version — the JDK {@code HttpClient} the
 * transport wraps can be used asynchronously by the caller if needed, but providing a parallel
 * {@code CompletableFuture}-shaped surface would double the SDK's API surface.
 *
 * <p>Usage:
 * <pre>{@code
 * VoicemlClient client = VoicemlClient.builder()
 *         .accountSid("AC...")
 *         .apiKey("...")
 *         .build();
 *
 * Call call = client.calls().create(
 *         CreateCallRequest.builder()
 *                 .to("+18005551234")
 *                 .from("+18005550000")
 *                 .url("https://example.com/twiml")
 *                 .machineDetection("DetectMessageEnd")
 *                 .build());
 * }</pre>
 */
public final class VoicemlClient {

    private final Transport transport;
    private final CallsResource calls;
    private final ConferencesResource conferences;
    private final QueuesResource queues;
    private final ApplicationsResource applications;
    private final RecordingsResource recordings;
    private final IncomingPhoneNumbersResource incomingPhoneNumbers;
    private final MessagesResource messages;
    private final NotificationsResource notifications;
    private final SipResource sip;
    private final DiagnosticsResource diagnostics;

    private VoicemlClient(ClientOptions options) {
        this.transport = new Transport(options);
        this.calls = new CallsResource(transport);
        this.conferences = new ConferencesResource(transport);
        this.queues = new QueuesResource(transport);
        this.applications = new ApplicationsResource(transport);
        this.recordings = new RecordingsResource(transport);
        this.incomingPhoneNumbers = new IncomingPhoneNumbersResource(transport);
        this.messages = new MessagesResource(transport);
        this.notifications = new NotificationsResource(transport);
        this.sip = new SipResource(transport);
        this.diagnostics = new DiagnosticsResource(transport);
    }

    public CallsResource calls() {
        return calls;
    }

    public ConferencesResource conferences() {
        return conferences;
    }

    public QueuesResource queues() {
        return queues;
    }

    public ApplicationsResource applications() {
        return applications;
    }

    public RecordingsResource recordings() {
        return recordings;
    }

    public IncomingPhoneNumbersResource incomingPhoneNumbers() {
        return incomingPhoneNumbers;
    }

    public MessagesResource messages() {
        return messages;
    }

    public NotificationsResource notifications() {
        return notifications;
    }

    public SipResource sip() {
        return sip;
    }

    public DiagnosticsResource diagnostics() {
        return diagnostics;
    }

    /** The configured AccountSid. */
    public String accountSid() {
        return transport.accountSid();
    }

    /** The configured base URL (with trailing slash stripped). */
    public String baseUrl() {
        return transport.baseUrl();
    }

    /** Underlying transport — useful for tests and advanced custom request flows. */
    public Transport transport() {
        return transport;
    }

    public static Builder builder() {
        return new Builder();
    }

    /** Fluent builder. Mirrors {@link ClientOptions.Builder} so callers stay in one place. */
    public static final class Builder {

        private final ClientOptions.Builder opts = ClientOptions.builder();

        public Builder accountSid(String accountSid) {
            opts.accountSid(accountSid);
            return this;
        }

        public Builder apiKey(String apiKey) {
            opts.apiKey(apiKey);
            return this;
        }

        /**
         * Twilio-named alias for {@link #apiKey(String)}. Setting both raises
         * {@link IllegalStateException} at {@link #build()} time.
         */
        public Builder authToken(String authToken) {
            opts.authToken(authToken);
            return this;
        }

        public Builder baseUrl(String baseUrl) {
            opts.baseUrl(baseUrl);
            return this;
        }

        public Builder timeout(java.time.Duration timeout) {
            opts.timeout(timeout);
            return this;
        }

        public Builder maxRetries(int maxRetries) {
            opts.maxRetries(maxRetries);
            return this;
        }

        public Builder userAgent(String userAgent) {
            opts.userAgent(userAgent);
            return this;
        }

        public Builder httpClient(java.net.http.HttpClient httpClient) {
            opts.httpClient(httpClient);
            return this;
        }

        public Builder options(ClientOptions options) {
            // Convenience: drop a pre-built ClientOptions onto the builder. Last-call wins for
            // the credentials and base URL.
            opts.accountSid(options.getAccountSid())
                .apiKey(options.getApiKey())
                .baseUrl(options.getBaseUrl())
                .timeout(options.getTimeout())
                .maxRetries(options.getMaxRetries())
                .userAgent(options.getUserAgent())
                .httpClient(options.getHttpClient());
            return this;
        }

        public VoicemlClient build() {
            return new VoicemlClient(opts.build());
        }
    }
}
