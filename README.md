# VoiceML Java SDK

Official Java SDK for the [VoiceML REST API](https://voicetel.com/docs/api/v0.6/voiceml/). VoiceML is
VoiceTel's outbound voice + AMD service with a **Twilio-shaped REST surface** — the wire
shape, auth model, error codes, and pagination envelope all match Twilio's documented
behaviour, so existing Twilio client patterns map across.

- Server: `https://voiceml.voicetel.com`
- Auth: HTTP Basic — `AccountSid` is the username, the per-tenant API key is the password
- Pure JDK + Jackson — no OkHttp, Apache HttpClient, Lombok, or Spring dependencies
- Java 11 baseline (compiled with `--release 11`)

## Install

Maven:

```xml
<dependency>
  <groupId>com.voicetel</groupId>
  <artifactId>voiceml</artifactId>
  <version>0.6.1</version>
</dependency>
```

Gradle:

```groovy
implementation 'com.voicetel:voiceml:0.6.1'
```

## Quickstart

```java
import com.voicetel.voiceml.VoicemlClient;
import com.voicetel.voiceml.models.Call;
import com.voicetel.voiceml.models.CreateCallRequest;

VoicemlClient client = VoicemlClient.builder()
        .accountSid("AC...")
        .apiKey("...")
        .build();

Call call = client.calls().create(
        CreateCallRequest.builder()
                .to("+18005551234")
                .from("+18005550000")
                .url("https://example.com/twiml")
                .machineDetection("DetectMessageEnd")
                .build());

System.out.println(call.getSid() + " " + call.getStatus());
```

## Resource map

| Resource                          | Accessor                              | Notes                                                                                  |
|-----------------------------------|---------------------------------------|----------------------------------------------------------------------------------------|
| Calls + sub-resources             | `client.calls()`                      | Recordings, Streams, Siprec, Transcriptions, Notifications, Events, UserDefinedMessages |
| Conferences + participants        | `client.conferences()`                | List/end/update participants; list per-conference recordings                            |
| Queues + members                  | `client.queues()`                     | Create, list, dequeue front/by-sid                                                      |
| Applications                      | `client.applications()`               | Persistent TwiML + callback bundles                                                     |
| Account-scoped recordings         | `client.recordings()`                 | Account-wide list, metadata, audio download (follows S3 redirect), delete               |
| Health + OpenAPI                  | `client.diagnostics()`                | Unauthenticated probes                                                                  |

## Error mapping

All SDK exceptions extend `VoiceMLException`. The API-side hierarchy:

| HTTP status | Exception                  | Typical cause                                            |
|-------------|----------------------------|----------------------------------------------------------|
| 400         | `BadRequestException`      | Malformed body or validation failure                     |
| 401         | `AuthenticationException`  | Bad/missing credentials or source-IP not allowed         |
| 403         | `PermissionDeniedException`| Authenticated, not authorized                            |
| 404         | `NotFoundException`        | SID does not exist (or belongs to a different tenant)    |
| 409         | `ConflictException`        | Conflicting state (e.g. deleting a non-empty queue)      |
| 410         | `GoneException`            | Recording audio no longer available                       |
| 429         | `RateLimitException`       | Per-account rate limit hit                                |
| 501         | `NotImplementedException`  | Stubbed endpoint (e.g. `UserDefinedMessages`)            |
| 5xx         | `ServerException`          | Server-side error; transport retries by default          |
| other       | `ApiException`             | Catch-all; carries `statusCode`, `code`, `body`          |

`ApiException.getCode()` returns the numeric Twilio-style error code from the response body
when present. `ApiException.getBody()` carries the raw payload for inspection.

## Twilio drop-in

The SDK is wire-compatible with Twilio: same Basic auth, same form body, same response envelope.
A migration from `com.twilio:twilio` typically looks like:

```java
// Twilio:
//   Twilio.init(accountSid, apiKey);
//   Call call = Call.creator(...).create();
//
// VoiceML:
VoicemlClient client = VoicemlClient.builder().accountSid(accountSid).apiKey(apiKey).build();
Call call = client.calls().create(CreateCallRequest.builder()....build());
```

## Pagination

List endpoints inherit from `Page`, which exposes `getNextPageUri()`, `getPage()`,
`getPageSize()`, `getTotal()`, etc. Walk pages manually using the literal `Page` /
`PageSize` query params (see `ListCallsParams.builder().page(...).pageSize(...).build()`).

## Filtering calls by start time

The Twilio wire names `StartTime>=` and `StartTime<=` aren't valid Java identifiers; in the
SDK they're modelled as `startTimeGte` / `startTimeLte` on `ListCallsParams`, then translated
back to the wire names when the query string is built.

```java
client.calls().list(
        ListCallsParams.builder()
                .startTimeGte("2026-04-01T00:00:00Z")
                .startTimeLte("2026-04-30T23:59:59Z")
                .pageSize(50)
                .build());
```

## Retries

Transient failures (HTTP 429, 5xx, transport errors) are retried up to `maxRetries` times
(default 2) with exponential backoff. A `Retry-After` header on a 429 or 503 overrides the
backoff schedule.

```java
VoicemlClient.builder()
        .accountSid("...")
        .apiKey("...")
        .maxRetries(5)
        .timeout(java.time.Duration.ofSeconds(60))
        .build();
```

## Async

This v1 ships only the synchronous surface. The underlying JDK `HttpClient` can be used
asynchronously in your application code if needed; a `CompletableFuture`-shaped SDK surface
may land in a later release. For now, dispatch SDK calls onto your own executor when you
need parallelism.

## 📖 API Documentation

- **Reference docs:** [voicetel.com/docs/api/v0.6/voiceml/](https://voicetel.com/docs/api/v0.6/voiceml/)
- **Validator:** [voicetel.com/voiceml/validator/](https://voicetel.com/voiceml/validator/)
- **SDK catalogue:** [voicetel.com/docs/voiceml-sdks/](https://voicetel.com/docs/voiceml-sdks/)

## License

MIT License with Commons Clause Restriction. See `LICENSE` and
[voicetel.com/legal](https://voicetel.com/legal/).
