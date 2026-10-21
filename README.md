# 📞 VoiceML Java SDK

The official Java client for the [VoiceML REST API](https://voicetel.com/docs/api/v0.9/voiceml/), VoiceTel's Twilio-compatible voice and messaging API, with typed Jackson models on the standard `java.net.http` transport.

![Version](https://img.shields.io/badge/version-0.9.4-blue)
![Java](https://img.shields.io/badge/java-11%2B-blue)
![License](https://img.shields.io/badge/license-MIT%20%2B%20Commons%20Clause-green)
[![CI](https://github.com/voicetel/voiceml-java-sdk/actions/workflows/ci.yml/badge.svg)](https://github.com/voicetel/voiceml-java-sdk/actions/workflows/ci.yml)

## 📚 Table of Contents

- [Features](#-features)
- [Installation](#-installation)
- [Quickstart](#-quickstart)
- [Authentication](#-authentication)
- [Resource Reference](#-resource-reference)
- [Pagination](#-pagination)
- [Webhook Signatures](#-webhook-signatures)
- [Downloads and Diagnostics](#-downloads-and-diagnostics)
- [Error Handling](#-error-handling)
- [Retries and Rate Limits](#-retries-and-rate-limits)
- [Async Support](#-async-support)
- [Migration from twilio-java](#-migration-from-twilio-java)
- [Development](#-development)
- [API Documentation](#-api-documentation)
- [Contributors](#-contributors)
- [Sponsors](#-sponsors)
- [License](#-license)

## ✨ Features

- **Covers 333 of the 333 operations** in the public OpenAPI document, checked on every build by a spec-driven contract test. The test calls every public method against mock hosts and checks the path, host, auth, parameters and response models against the spec.
- **Typed models** for every request and response. Unknown response fields are ignored, and an enum value this SDK version does not know yet decodes to `UNKNOWN` instead of failing.
- **Standard runtime.** Built on `java.net.http.HttpClient` (Java 11+). The only runtime dependencies are `jackson-databind`, `jackson-core` and `jackson-annotations`, kept aligned through `jackson-bom`. The jar declares `Automatic-Module-Name: com.voicetel.voiceml`.
- **Safe retries.** Idempotent requests retry on 429 and 5xx. `POST` is never re-sent after the server may have received it, so a retry never places a call or sends a message twice. `Retry-After` is honoured in both forms.
- **Lazy pagination** that follows the server's `next_page_uri` (including its `PageToken`) verbatim.
- **Webhook signature validation** (`X-Twilio-Signature`) with constant-time comparison.
- **Per-product host routing.** Conversations and Messaging Service go to their own subdomains.

## 🚀 Installation

### Maven

```xml
<dependency>
  <groupId>com.voicetel</groupId>
  <artifactId>voiceml</artifactId>
  <version>0.9.4</version>
</dependency>
```

### Gradle (Groovy DSL)

```groovy
implementation 'com.voicetel:voiceml:0.9.4'
```

### Gradle (Kotlin DSL)

```kotlin
implementation("com.voicetel:voiceml:0.9.4")
```

Requires Java 11 or later. The latest release on Maven Central is 0.9.4. This README documents the `main` branch; its changes since 0.9.4, including the breaking ones, are listed under **Unreleased** in [CHANGELOG.md](CHANGELOG.md).

## 🏁 Quickstart

```java
import com.voicetel.voiceml.VoicemlClient;
import com.voicetel.voiceml.models.Call;
import com.voicetel.voiceml.models.CreateCallRequest;
import com.voicetel.voiceml.models.Queue;

public class Example {
    public static void main(String[] args) {
        VoicemlClient client = VoicemlClient.builder()
                .accountSid(System.getenv("VOICEML_ACCOUNT_SID"))
                .apiKey(System.getenv("VOICEML_API_KEY"))
                .build();

        Call call = client.calls().create(
                CreateCallRequest.builder()
                        .to("+14155550100")
                        .from("+14155550101")
                        .url("https://example.com/twiml")
                        .machineDetection("DetectMessageEnd")
                        .build());

        System.out.println(call.getSid() + " " + call.getStatus());

        for (Queue q : client.queues().list().getQueues()) {
            System.out.println(q.getFriendlyName() + " " + q.getCurrentSize());
        }
    }
}
```

## 🔑 Authentication

Every endpoint except the diagnostics uses **HTTP Basic** auth:
- the username is your VoiceML `AccountSid` (`AC` + 32 hex);
- the password is your VoiceML API key.

These are the same two positions twilio-java uses for an AccountSid and auth token, so ported code keeps its shape. Twilio-issued credentials do **not** authenticate against VoiceML; use the AccountSid and API key VoiceTel issued you. `authToken(...)` is accepted as an alias of `apiKey(...)`. Setting both raises `ConfigurationException`.

```java
VoicemlClient client = VoicemlClient.builder()
        .accountSid("ACXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX")
        .apiKey(System.getenv("VOICEML_API_KEY"))
        .build();
```

The client is thread-safe; build one and reuse it. Neither `toString()` of the client nor of `ClientOptions` includes the API key.

> Don't have credentials yet? See **[voicetel.com/docs/api/v0.9/voiceml/](https://voicetel.com/docs/api/v0.9/voiceml/)** for issuance and rotation.

## 🗺️ Resource Reference

| Resource | Accessor | Covers |
|---|---|---|
| Accounts | `client.accounts()` | fetch, update (JSON body; a concurrent update raises `ConflictException`, code 20409), rotate the account's API key (`rotateKey`; the response carries the new key) |
| Balance | `client.balance()` | fetch |
| Calls | `client.calls()` | create, fetch, list, update, delete; call recordings (start, fetch, update incl. `PauseBehavior`, delete); start/stop streams, SIPREC and real-time transcriptions; notifications; events; payments; user-defined messages |
| Conferences | `client.conferences()` | list, fetch, end; participants (create, mute / hold, kick); conference recordings (start, list, fetch, update, delete) |
| Queues | `client.queues()` | create, list, fetch, update, delete; members (list, fetch, peek front, dequeue) |
| Applications | `client.applications()` | create, list, fetch, update, delete (incl. `MessageStatusCallback`, `SmsStatusCallback`) |
| Recordings | `client.recordings()` | list, fetch, audio download, delete; a recording's transcriptions (list, fetch, delete) |
| Transcriptions | `client.transcriptions()` | list, fetch, delete |
| Messages | `client.messages()` | send SMS/MMS (`MediaUrl`, `ValidityPeriod`, `StatusCallbackMethod`), fetch, list, update, delete; message media (list, fetch, delete); delivery feedback (`createFeedback`, `Outcome=confirmed`) |
| IncomingPhoneNumbers | `client.incomingPhoneNumbers()` | list (`PhoneNumber`, `FriendlyName`, `Beta`, `Origin`), create, fetch, update (voice and `Sms*` routing, `SmsApplicationSid`), delete; Local / Mobile / TollFree list and create |
| OutgoingCallerIds | `client.outgoingCallerIds()` | list, create (validation request), fetch, update, delete |
| Notifications | `client.notifications()` | list, fetch |
| Usage | `client.usageRecords()` | list, plus the AllTime, Daily, LastMonth, Monthly, ThisMonth, Today, Yearly and Yesterday windows |
| Exports | `client.exports()` | list, download one day's archive |
| SIP | `client.sip()` | domains (+ credential-list and IP-ACL mappings), credential lists (+ credentials), IP access control lists (+ IP addresses), registrations |
| Routes v2 | `client.routesV2()` | phone numbers, SIP domains |
| Voice v1 | `client.voiceV1()` | BYOC trunks, connection policies (+ targets), IP records, source IP mappings, dialing-permission settings |
| Monitor v1 | `client.monitorV1().alerts()` | list (`LogLevel`, `StartDate`, `EndDate`, single `PageSize`-limited page), fetch (with stored request/response detail) |
| Pricing | `client.pricing()` | read-only `v1`/`v2` voice, messaging, phone-number and trunking prices |
| Messaging Service | `client.messagingV1().services()` | create, list, fetch, update, delete; sender pool via `.phoneNumbers(serviceSid)` (list, add, fetch, remove) |
| Conversations | `client.conversationsV1()` | conversations, messages, participants, webhooks, receipts, users, roles, credentials, configuration, services (with their scoped sub-resources) |
| Assistants | `client.assistantsV1()` | assistants, tools, knowledge, policies, sessions, messages, feedback |
| Diagnostics | `client.diagnostics()` | `/health`, `/openapi.json`, `/openapi.yaml`, `/openapi.yml` (unauthenticated) |

Every method that takes a request body accepts a typed builder from `com.voicetel.voiceml.models`:

```java
import com.voicetel.voiceml.models.CallPayment;
import com.voicetel.voiceml.models.CreateMessageFeedbackRequest;
import com.voicetel.voiceml.models.CreateMessageRequest;
import com.voicetel.voiceml.models.Message;
import com.voicetel.voiceml.models.MessageFeedback;
import com.voicetel.voiceml.models.MonitorV1AlertList;
import com.voicetel.voiceml.models.AccountWithKey;
import com.voicetel.voiceml.models.ListMonitorAlertsParams;
import com.voicetel.voiceml.models.StartPaymentRequest;
import java.time.LocalDate;
import java.util.List;

// An MMS: each MediaUrl is sent as a repeated form key. A message needs Body or MediaUrl.
Message mms = client.messages().create(CreateMessageRequest.builder()
        .to("+14155550100")
        .from("+14155550101")
        .body("Your receipt")
        .mediaUrl(List.of("https://example.com/receipt.png"))
        .build());

// On a live call, open a Pay session:
CallPayment session = client.calls().startPayment("CA0123456789abcdef0123456789abcdef",
        StartPaymentRequest.builder()
                .idempotencyKey("order-482917")
                .statusCallback("https://example.com/pay-status")
                .build());

// Report delivery feedback for a message:
MessageFeedback feedback = client.messages().createFeedback("SM0123456789abcdef0123456789abcdef",
        CreateMessageFeedbackRequest.builder().outcome("confirmed").build());

// Failure events from the Monitor view (a single PageSize-limited page):
MonitorV1AlertList alerts = client.monitorV1().alerts().list(ListMonitorAlertsParams.builder()
        .logLevel("error")
        .startDate(LocalDate.of(2026, 10, 1))
        .pageSize(100)
        .build());

// Rotate the account's API key; the new key is on the response only — store it right away:
AccountWithKey rotated = client.accounts().rotateKey();

System.out.println(mms.getSid() + " " + session.getSid() + " " + feedback.getOutcome()
        + " " + alerts.getAlerts().size() + " " + rotated.getSid());
```

### Product hosts

Given the configured `baseUrl`, two groups are routed to their own product hosts automatically:

| Group | Host derived from `https://voiceml.voicetel.com` |
|---|---|
| `client.conversationsV1()` | `https://conversations.voicetel.com` |
| `client.messagingV1()` | `https://messaging.voicetel.com` |
| everything else (Calls, Voice v1, Routes v2, Pricing, …) | `https://voiceml.voicetel.com` (default) |

Derivation swaps the leftmost `voiceml` label on recognised `*.voicetel.com` hosts. Any other base URL (a self-hosted instance, a test stub) keeps all groups on the single configured host. When self-hosting, override a product host explicitly; `VoicemlClient.builder().options(...)` carries these overrides over as well:

```java
VoicemlClient selfHosted = VoicemlClient.builder()
        .accountSid("ACXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX")
        .apiKey(System.getenv("VOICEML_API_KEY"))
        .baseUrl("https://pbx.example.com")
        .messagingBaseUrl("https://messaging.example.com")
        .conversationsBaseUrl("https://conversations.example.com")
        .build();
```

## 📄 Pagination

`/2010-04-01` list methods return one page, a model extending `Page`. A `Page` exposes `getPage()`, `getPageSize()`, `getStart()`, `getEnd()`, `getFirstPageUri()`, `getNextPageUri()`, `getPreviousPageUri()` and `getUri()`. The API does not report a total count.

`iterate(...)` returns a lazy `Pager`:
- no request is sent until you start iterating;
- each further page is fetched by following `next_page_uri` exactly as the server returned it, `PageToken` included;
- iteration stops when the link is empty or a page is empty;
- breaking out early sends no further requests.

```java
import com.voicetel.voiceml.models.Call;
import com.voicetel.voiceml.models.CallList;
import com.voicetel.voiceml.models.ListCallsParams;

for (Call c : client.calls().iterate(ListCallsParams.builder().status("completed").pageSize(200).build())) {
    System.out.println(c.getSid());
}

long recent = client.calls().iterate().stream().limit(500).count();

// Or page by hand:
CallList page = client.calls().list(ListCallsParams.builder()
        .startTimeGt("2026-04-01T00:00:00Z")   // StartTime>
        .startTimeLt("2026-04-30T23:59:59Z")   // StartTime<
        .pageSize(50)
        .build());
CallList next = client.nextPage(page, CallList.class);   // null on the last page
```

The `/v1` lists (Conversations, Messaging Service, Voice v1, Assistants, Pricing) carry their links in `meta.next_page_url`. Most of them accept only `PageSize`, so passing `Page` or `PageToken` raises `ValidationException`. Follow the link with `client.nextPage(page, Type.class)` or `client.paginate(firstPage, Type.class, Type::getItems)`. The SDK follows an absolute link only when it points at the configured host that served the page. A link to any other host raises `PaginationException`, and no credentials are sent there.

## 🔏 Webhook Signatures

VoiceML signs every webhook with an `X-Twilio-Signature` header: HMAC-SHA1 keyed with your API key, over the full URL followed (for a form `POST`) by the sorted parameters. Verify it before trusting the request:

```java
import com.voicetel.voiceml.RequestValidator;
import java.util.List;
import java.util.Map;

RequestValidator validator = new RequestValidator(System.getenv("VOICEML_API_KEY"));

Map<String, List<String>> form = Map.of(
        "CallSid", List.of("CA0123456789abcdef0123456789abcdef"),
        "From", List.of("+14155550100"));
boolean valid = validator.validate(
        "https://example.com/voice/answer",   // the URL exactly as configured on VoiceML
        form,                                  // the POST parameters, repeated keys in received order
        "signature-from-the-X-Twilio-Signature-header");
```

Validation compares in constant time and also accepts the URL with the default port (`:443` / `:80`) added or removed. For a `GET` webhook, pass `null` parameters. `computeSignature(...)` produces the signature for your own tests.

## 📥 Downloads and Diagnostics

`client.recordings().getAudio(sid)` (`.wav`) and `client.exports().download(date)` behave as follows:
- they use the client's own `HttpClient` and timeout;
- `Authorization` is sent only while the target keeps the API's origin, so the presigned storage URL the server redirects to never receives your credentials;
- an https→http redirect is refused with `TransportException`.

If you supply your own `HttpClient`, leave its redirect policy at the default `Redirect.NEVER` so these rules apply.

The diagnostics endpoints are unauthenticated, and no `Authorization` header is sent to them. You can reach them without credentials:

```java
import com.voicetel.voiceml.models.HealthStatus;
import com.voicetel.voiceml.resources.DiagnosticsResource;

DiagnosticsResource diagnostics = VoicemlClient.builder()
        .baseUrl("https://voiceml.voicetel.com")
        .buildDiagnostics();

HealthStatus health = diagnostics.health();       // a 503 raises ServerException with the failures on getBody()
String openapiYaml = diagnostics.openapiYaml();
```

## 🚨 Error Handling

Every SDK exception extends `com.voicetel.voiceml.exceptions.VoiceMLException`. HTTP errors are subclasses of `ApiException`:

| Status | Exception |
|--------|-----------|
| 400 | `BadRequestException` |
| 401 | `AuthenticationException` |
| 403 | `PermissionDeniedException` |
| 404 | `NotFoundException` |
| 405 | `MethodNotAllowedException` |
| 409 | `ConflictException` |
| 410 | `GoneException` |
| 429 | `RateLimitException` |
| 501 | `NotImplementedException` |
| 5xx | `ServerException` |
| other | `ApiException` |
| no response (DNS, refused, reset, TLS, unsafe redirect, interrupted) | `TransportException` (an `ApiException` with status 0; cause preserved) |
| timeout | `RequestTimeoutException` (a `TransportException`) |

Other `VoiceMLException` subclasses:
- `ConfigurationException`: invalid client settings.
- `ValidationException`: an argument was rejected before sending, such as an empty path ID or a message with neither `Body` nor `MediaUrl`.
- `PaginationException`: a next-page link was unsafe to follow.

```java
import com.voicetel.voiceml.exceptions.ApiException;
import com.voicetel.voiceml.exceptions.NotFoundException;
import com.voicetel.voiceml.exceptions.RateLimitException;

try {
    Call missing = client.calls().get("CA0000000000000000000000000000aaaa");
    System.out.println(missing.getStatus());
} catch (NotFoundException e) {
    System.out.println("That call isn't on your account.");
} catch (RateLimitException e) {
    System.out.println("Retry after " + e.getRetryAfter());
} catch (ApiException e) {
    System.out.println(e.getStatusCode() + " " + e.getCode() + " " + e.getMoreInfo());
}
```

The error envelope (`code`, `message`, `more_info`) is parsed into `getCode()`, `getMessage()` and `getMoreInfo()`, with the raw payload on `getBody()`. A non-JSON error body still produces an `ApiException` (message `HTTP <status>`).

## ⏱️ Retries and Rate Limits

`maxRetries` (default 2) bounds the retries after the first attempt. Backoff is exponential with full jitter: 0.5 s base, 8 s maximum.

- `GET`, `HEAD`, `PUT` and `DELETE` retry on 429, 500, 502, 503 and 504, and on network errors.
- `POST` and `PATCH` retry **only** on 429, and on connection failures where the request was provably never sent (DNS failure, connection refused, connect timeout, TLS handshake failure).
- They are never retried on a 5xx, a read timeout or a reset connection, because the server may already have placed the call or sent the message.
- `Retry-After` is honoured as delta-seconds or an HTTP-date.
- If the server asks for more than 60 seconds, the SDK does not sleep. It raises the error immediately, with the requested delay on `getRetryAfter()`.

```java
import java.time.Duration;

VoicemlClient patient = VoicemlClient.builder()
        .accountSid("ACXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX")
        .apiKey(System.getenv("VOICEML_API_KEY"))
        .maxRetries(4)
        .timeout(Duration.ofSeconds(60))
        .build();
```

## ⚡ Async Support

This release ships only the synchronous surface. For parallelism, dispatch SDK calls onto your own executor. Interrupting a waiting thread aborts the request and does not retry it; the interrupt flag is preserved.

```java
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executors;

var pool = Executors.newFixedThreadPool(8);

CompletableFuture<Call> future = CompletableFuture.supplyAsync(
        () -> client.calls().create(CreateCallRequest.builder()
                .to("+14155550100")
                .from("+14155550101")
                .url("https://example.com/twiml")
                .build()),
        pool);
```

## 🔁 Migration from twilio-java

The API is migration-compatible with twilio-java at the wire level: the same Basic-auth positions, form bodies and response envelopes. Swap the initialisation and use your VoiceML AccountSid and API key:

```java
// Before (twilio-java)
// Twilio.init(accountSid, authToken);
// Call call = Call.creator(to, from, uri).create();

// After (VoiceML)
VoicemlClient voiceml = VoicemlClient.builder()
        .accountSid(System.getenv("VOICEML_ACCOUNT_SID"))
        .apiKey(System.getenv("VOICEML_API_KEY"))
        .build();

Call call = voiceml.calls().create(CreateCallRequest.builder()
        .to("+14155550100")
        .from("+14155550101")
        .url("https://example.com/twiml")
        .build());
```

Method names follow the resource map above (`client.calls().create(...)`, `client.queues().list()`, …) rather than twilio-java's static creator chain.

## 🛠️ Development

```bash
git clone https://github.com/voicetel/voiceml-java-sdk
cd voiceml-java-sdk

# Tests, -Xlint:all (warnings fail the build), javadoc doclint, sources + javadoc jars.
# Needs JDK 17+ (the JUnit 6 test suite); the library itself is compiled for Java 11.
mvn verify
```

- `contract.SpecContractTest` checks every public method against the vendored public spec (`src/test/resources/spec/voiceml-openapi.json`, which is not packaged into the jar).
- `ReadmeSnippetsTest` compiles the Java examples in this README.
- CI also runs `src/smoke/java/.../Jdk11Smoke.java` against the built jar on JDK 11.
- `ConformanceTest` decodes a private fixture corpus. It is included automatically when `VOICEML_CONFORMANCE_FIXTURES` points at the corpus.

## 📖 API Documentation

- **Reference docs:** [voicetel.com/docs/api/v0.9/voiceml/](https://voicetel.com/docs/api/v0.9/voiceml/)
- **Validator:** [voicetel.com/voiceml/validator/](https://voicetel.com/voiceml/validator/)
- **SDK catalogue:** [voicetel.com/docs/voiceml-sdks/](https://voicetel.com/docs/voiceml-sdks/)
- **Changelog:** [CHANGELOG.md](CHANGELOG.md)

## 🙌 Contributors

- [Michael Mavroudis](https://github.com/mavroudis) — Lead Developer

Contributions welcome. Open an issue describing the change you want to make, or send a pull request against `main`.

## 💖 Sponsors

| Sponsor | Contribution |
|---------|--------------|
| [VoiceTel Communications](https://voicetel.com) | Primary development and production hosting |

## 📄 License

MIT with the Commons Clause restriction. See [LICENSE](LICENSE) and [voicetel.com/legal/](https://voicetel.com/legal/).
