package com.voicetel.voiceml;

import com.voicetel.voiceml.exceptions.ConfigurationException;
import com.voicetel.voiceml.exceptions.PaginationException;
import com.voicetel.voiceml.models.MetaPage;
import com.voicetel.voiceml.models.Page;
import com.voicetel.voiceml.resources.AccountsResource;
import com.voicetel.voiceml.resources.ApplicationsResource;
import com.voicetel.voiceml.resources.AssistantsV1Resource;
import com.voicetel.voiceml.resources.BalanceResource;
import com.voicetel.voiceml.resources.CallsResource;
import com.voicetel.voiceml.resources.ConferencesResource;
import com.voicetel.voiceml.resources.ConversationsV1Resource;
import com.voicetel.voiceml.resources.DiagnosticsResource;
import com.voicetel.voiceml.resources.ExportsResource;
import com.voicetel.voiceml.resources.IncomingPhoneNumbersResource;
import com.voicetel.voiceml.resources.MessagesResource;
import com.voicetel.voiceml.resources.MessagingV1Resource;
import com.voicetel.voiceml.resources.MonitorV1Resource;
import com.voicetel.voiceml.resources.NotificationsResource;
import com.voicetel.voiceml.resources.OutgoingCallerIdsResource;
import com.voicetel.voiceml.resources.PricingResource;
import com.voicetel.voiceml.resources.QueuesResource;
import com.voicetel.voiceml.resources.RecordingsResource;
import com.voicetel.voiceml.resources.RoutesV2Resource;
import com.voicetel.voiceml.resources.SipResource;
import com.voicetel.voiceml.resources.TranscriptionsResource;
import com.voicetel.voiceml.resources.UsageRecordsResource;
import com.voicetel.voiceml.resources.VoiceV1Resource;

import java.util.List;
import java.util.function.Function;

/**
 * Synchronous client for the VoiceML REST API.
 *
 * <p>VoiceML uses HTTP Basic auth: your VoiceML {@code accountSid} ({@code AC} + 32 hex) is the
 * username and your VoiceML {@code apiKey} is the password, in the same positions twilio-java uses
 * for an AccountSid and auth token, so the client is migration-compatible. Twilio-issued
 * credentials do not authenticate against VoiceML.
 *
 * <p>Instances are thread-safe and should be reused. Async surfaces are deliberately omitted:
 * the JDK {@code HttpClient} the transport wraps can be used asynchronously by the caller if
 * needed.
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
 *                 .to("+14155550100")
 *                 .from("+14155550101")
 *                 .url("https://example.com/twiml")
 *                 .machineDetection("DetectMessageEnd")
 *                 .build());
 * }</pre>
 */
public final class VoicemlClient {

    private final Transport transport;
    private final Transport messagingTransport;
    private final Transport conversationsTransport;
    private final AccountsResource accounts;
    private final BalanceResource balance;
    private final CallsResource calls;
    private final ConferencesResource conferences;
    private final QueuesResource queues;
    private final ApplicationsResource applications;
    private final RecordingsResource recordings;
    private final TranscriptionsResource transcriptions;
    private final IncomingPhoneNumbersResource incomingPhoneNumbers;
    private final OutgoingCallerIdsResource outgoingCallerIds;
    private final MessagesResource messages;
    private final MessagingV1Resource messagingV1;
    private final NotificationsResource notifications;
    private final UsageRecordsResource usageRecords;
    private final ExportsResource exports;
    private final PricingResource pricing;
    private final SipResource sip;
    private final RoutesV2Resource routesV2;
    private final VoiceV1Resource voiceV1;
    private final MonitorV1Resource monitorV1;
    private final ConversationsV1Resource conversationsV1;
    private final AssistantsV1Resource assistantsV1;
    private final DiagnosticsResource diagnostics;

    private VoicemlClient(ClientOptions options) {
        this.transport = new Transport(options);
        // Conversations and Messaging Service ride their own product subdomains
        // (they share the /v1/Services path; the host is what disambiguates them).
        this.messagingTransport = transport.withBaseUrl(options.getMessagingBaseUrl());
        this.conversationsTransport = transport.withBaseUrl(options.getConversationsBaseUrl());
        this.accounts = new AccountsResource(transport);
        this.balance = new BalanceResource(transport);
        this.calls = new CallsResource(transport);
        this.conferences = new ConferencesResource(transport);
        this.queues = new QueuesResource(transport);
        this.applications = new ApplicationsResource(transport);
        this.recordings = new RecordingsResource(transport);
        this.transcriptions = new TranscriptionsResource(transport);
        this.incomingPhoneNumbers = new IncomingPhoneNumbersResource(transport);
        this.outgoingCallerIds = new OutgoingCallerIdsResource(transport);
        this.messages = new MessagesResource(transport);
        this.messagingV1 = new MessagingV1Resource(messagingTransport);
        this.notifications = new NotificationsResource(transport);
        this.usageRecords = new UsageRecordsResource(transport);
        this.exports = new ExportsResource(transport);
        this.pricing = new PricingResource(transport);
        this.sip = new SipResource(transport);
        this.routesV2 = new RoutesV2Resource(transport);
        this.voiceV1 = new VoiceV1Resource(transport);
        this.monitorV1 = new MonitorV1Resource(transport);
        this.conversationsV1 = new ConversationsV1Resource(conversationsTransport);
        this.assistantsV1 = new AssistantsV1Resource(transport);
        this.diagnostics = new DiagnosticsResource(transport);
    }

    /** @return {@code /Accounts/{Sid}} (fetch and update your account) */
    public AccountsResource accounts() {
        return accounts;
    }

    /** @return {@code /Balance} */
    public BalanceResource balance() {
        return balance;
    }

    /** @return {@code /Calls} and call-scoped sub-resources */
    public CallsResource calls() {
        return calls;
    }

    /** @return {@code /Conferences}, participants and conference recordings */
    public ConferencesResource conferences() {
        return conferences;
    }

    /** @return {@code /Queues} and queue members */
    public QueuesResource queues() {
        return queues;
    }

    /** @return {@code /Applications} */
    public ApplicationsResource applications() {
        return applications;
    }

    /** @return account-wide {@code /Recordings}, audio and recording transcriptions */
    public RecordingsResource recordings() {
        return recordings;
    }

    /** @return account-wide {@code /Transcriptions} */
    public TranscriptionsResource transcriptions() {
        return transcriptions;
    }

    /** @return {@code /IncomingPhoneNumbers} */
    public IncomingPhoneNumbersResource incomingPhoneNumbers() {
        return incomingPhoneNumbers;
    }

    /** @return {@code /OutgoingCallerIds} */
    public OutgoingCallerIdsResource outgoingCallerIds() {
        return outgoingCallerIds;
    }

    /** @return {@code /Messages} and message media */
    public MessagesResource messages() {
        return messages;
    }

    /** @return Messaging Service group ({@code /v1/Services} on {@code messaging.voicetel.com}) */
    public MessagingV1Resource messagingV1() {
        return messagingV1;
    }

    /** @return account-wide {@code /Notifications} */
    public NotificationsResource notifications() {
        return notifications;
    }

    /** @return {@code /Usage/Records} and its time windows */
    public UsageRecordsResource usageRecords() {
        return usageRecords;
    }

    /** @return {@code /Exports} archives */
    public ExportsResource exports() {
        return exports;
    }

    /** @return Pricing group ({@code /v1} + {@code /v2}, read-only, on the default host) */
    public PricingResource pricing() {
        return pricing;
    }

    /** @return {@code /SIP} domains, credential lists, IP access control lists and registrations */
    public SipResource sip() {
        return sip;
    }

    /** @return Routes v2 ({@code /v2/PhoneNumbers}, {@code /v2/SipDomains}) */
    public RoutesV2Resource routesV2() {
        return routesV2;
    }

    /** @return Voice v1 ({@code /v1/ByocTrunks}, connection policies, IP records, settings) */
    public VoiceV1Resource voiceV1() {
        return voiceV1;
    }

    /** @return Monitor v1 ({@code /v1/Alerts}) */
    public MonitorV1Resource monitorV1() {
        return monitorV1;
    }

    /** @return Conversations v1 (on {@code conversations.voicetel.com}) */
    public ConversationsV1Resource conversationsV1() {
        return conversationsV1;
    }

    /** @return Assistants v1 */
    public AssistantsV1Resource assistantsV1() {
        return assistantsV1;
    }

    /** @return the unauthenticated diagnostics ({@code /health}, {@code /openapi.*}) */
    public DiagnosticsResource diagnostics() {
        return diagnostics;
    }

    /** @return the configured AccountSid */
    public String accountSid() {
        return transport.accountSid();
    }

    /** @return the configured base URL (with trailing slash stripped) */
    public String baseUrl() {
        return transport.baseUrl();
    }

    /** @return the underlying transport, for tests and advanced custom request flows */
    public Transport transport() {
        return transport;
    }

    /**
     * Fetch the page after {@code page} on a {@code /2010-04-01} list by following its
     * {@code next_page_uri} verbatim.
     *
     * @param page a page returned by a {@code list(...)} call
     * @param type the page's class, e.g. {@code CallList.class}
     * @param <P> page type
     * @return the next page, or {@code null} when {@code page} is the last one
     */
    public <P extends Page> P nextPage(P page, Class<P> type) {
        if (page == null || !page.hasNextPage()) {
            return null;
        }
        return transport.decode(transport.getLink(page.getNextPageUri()), type);
    }

    /**
     * Fetch the page after {@code page} on a {@code /v1} list by following its
     * {@code meta.next_page_url}. The link is followed only when it points at one of the configured
     * hosts (base, Messaging Service or Conversations); otherwise {@link PaginationException} is
     * raised and nothing is sent.
     *
     * @param page a page returned by a {@code list(...)} call
     * @param type the page's class
     * @param <P> page type
     * @return the next page, or {@code null} when {@code page} is the last one
     */
    public <P extends MetaPage> P nextPage(P page, Class<P> type) {
        String link = page == null || page.getMeta() == null ? null : page.getMeta().getNextPageUrl();
        if (link == null || link.isEmpty()) {
            return null;
        }
        Transport t = transportForLink(link);
        return t.decode(t.getLink(link), type);
    }

    /**
     * Lazily iterate every item of a {@code /2010-04-01} list, starting from an already fetched
     * first page. See {@link Pager}.
     *
     * @param firstPage the first page
     * @param type the page's class
     * @param items extracts the items of a page, e.g. {@code CallList::getCalls}
     * @param <P> page type
     * @param <T> item type
     * @return the pager
     */
    public <P extends Page, T> Pager<T> paginate(P firstPage, Class<P> type, Function<? super P, List<T>> items) {
        return Pager.create(() -> firstPage, items, Page::getNextPageUri,
                link -> transport.decode(transport.getLink(link), type));
    }

    /**
     * Lazily iterate every item of a {@code /v1} list, starting from an already fetched first page.
     * See {@link Pager} and {@link #nextPage(MetaPage, Class)}.
     *
     * @param firstPage the first page
     * @param type the page's class
     * @param items extracts the items of a page
     * @param <P> page type
     * @param <T> item type
     * @return the pager
     */
    public <P extends MetaPage, T> Pager<T> paginate(P firstPage, Class<P> type, Function<? super P, List<T>> items) {
        return Pager.create(() -> firstPage, items,
                p -> p.getMeta() == null ? null : p.getMeta().getNextPageUrl(),
                link -> {
                    Transport t = transportForLink(link);
                    return t.decode(t.getLink(link), type);
                });
    }

    private Transport transportForLink(String link) {
        for (Transport t : new Transport[] {transport, messagingTransport, conversationsTransport}) {
            try {
                t.resolveLink(link);
                return t;
            } catch (PaginationException notThisHost) {
                // try the next configured host
            }
        }
        // Raises the descriptive error for the base host.
        transport.resolveLink(link);
        throw new PaginationException("refusing to follow pagination link " + link);
    }

    /** @return a new builder */
    public static Builder builder() {
        return new Builder();
    }

    /** Fluent builder. Mirrors {@link ClientOptions.Builder} so callers stay in one place. */
    public static final class Builder {

        private final ClientOptions.Builder opts = ClientOptions.builder();

        /** Create an empty builder; prefer {@link VoicemlClient#builder()}. */
        public Builder() {
            // options are accumulated on the wrapped ClientOptions.Builder
        }

        /**
         * @param accountSid your VoiceML AccountSid (HTTP Basic username)
         * @return this builder
         */
        public Builder accountSid(String accountSid) {
            opts.accountSid(accountSid);
            return this;
        }

        /**
         * @param apiKey your VoiceML API key (HTTP Basic password)
         * @return this builder
         */
        public Builder apiKey(String apiKey) {
            opts.apiKey(apiKey);
            return this;
        }

        /**
         * Alias for {@link #apiKey(String)} under the twilio-java name. The value is your VoiceML
         * API key. Setting both raises {@link ConfigurationException} at {@link #build()} time.
         *
         * @param authToken your VoiceML API key
         * @return this builder
         */
        public Builder authToken(String authToken) {
            opts.authToken(authToken);
            return this;
        }

        /**
         * @param baseUrl the API base URL (absolute {@code http(s)})
         * @return this builder
         */
        public Builder baseUrl(String baseUrl) {
            opts.baseUrl(baseUrl);
            return this;
        }

        /**
         * Override the base URL for the Messaging Service group ({@code messagingV1()}). When
         * unset it is derived from {@link #baseUrl(String)}. See
         * {@link ClientOptions.Builder#messagingBaseUrl(String)}.
         *
         * @param messagingBaseUrl the Messaging Service base URL
         * @return this builder
         */
        public Builder messagingBaseUrl(String messagingBaseUrl) {
            opts.messagingBaseUrl(messagingBaseUrl);
            return this;
        }

        /**
         * Override the base URL for the Conversations group ({@code conversationsV1()}). When unset
         * it is derived from {@link #baseUrl(String)}. See
         * {@link ClientOptions.Builder#conversationsBaseUrl(String)}.
         *
         * @param conversationsBaseUrl the Conversations base URL
         * @return this builder
         */
        public Builder conversationsBaseUrl(String conversationsBaseUrl) {
            opts.conversationsBaseUrl(conversationsBaseUrl);
            return this;
        }

        /**
         * @param timeout per-request timeout
         * @return this builder
         */
        public Builder timeout(java.time.Duration timeout) {
            opts.timeout(timeout);
            return this;
        }

        /**
         * @param maxRetries retries after the first attempt ({@code 0} disables)
         * @return this builder
         */
        public Builder maxRetries(int maxRetries) {
            opts.maxRetries(maxRetries);
            return this;
        }

        /**
         * @param userAgent the {@code User-Agent} header
         * @return this builder
         */
        public Builder userAgent(String userAgent) {
            opts.userAgent(userAgent);
            return this;
        }

        /**
         * @param httpClient the JDK client to send every request with
         * @return this builder
         */
        public Builder httpClient(java.net.http.HttpClient httpClient) {
            opts.httpClient(httpClient);
            return this;
        }

        /**
         * Copy every setting of a pre-built {@link ClientOptions} onto this builder, including the
         * explicit Messaging Service and Conversations host overrides. Later calls on this builder
         * win.
         *
         * @param options the options to copy
         * @return this builder
         */
        public Builder options(ClientOptions options) {
            opts.accountSid(options.getAccountSid())
                .apiKey(options.getApiKey())
                .baseUrl(options.getBaseUrl())
                .messagingBaseUrl(options.getMessagingBaseUrlOverride())
                .conversationsBaseUrl(options.getConversationsBaseUrlOverride())
                .timeout(options.getTimeout())
                .maxRetries(options.getMaxRetries())
                .userAgent(options.getUserAgent())
                .httpClient(options.getHttpClient());
            return this;
        }

        /**
         * Validate the configuration and build the client.
         *
         * @return the client
         * @throws ConfigurationException when a value is missing, invalid or conflicting
         */
        public VoicemlClient build() {
            return new VoicemlClient(opts.build());
        }

        /**
         * Build a credential-less handle on the diagnostics endpoints ({@code /health},
         * {@code /openapi.json}, {@code /openapi.yaml}, {@code /openapi.yml}), which the API serves
         * without authentication. The base URL, HTTP client, timeout, retry count and
         * {@code User-Agent} of this builder apply; any credentials set are ignored.
         *
         * @return the diagnostics resource
         * @throws ConfigurationException when the base URL or another setting is invalid
         */
        public DiagnosticsResource buildDiagnostics() {
            return new DiagnosticsResource(new Transport(opts.buildWithoutCredentials()));
        }
    }
}
