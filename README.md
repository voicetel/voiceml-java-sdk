# 📞 VoiceML Java SDK

The official Java client for the [VoiceML REST API](https://voicetel.com/docs/api/v0.7/voiceml/) — Twilio-compatible outbound voice and answering-machine-detection from VoiceTel, with strongly-typed Jackson models on the modern `java.net.http` transport.

![Version](https://img.shields.io/badge/version-0.8.1-blue)
![Java](https://img.shields.io/badge/java-11%2B-blue)
![License](https://img.shields.io/badge/license-MIT%20%2B%20Commons%20Clause-green)
![Tests](https://img.shields.io/badge/tests-51%20unit-brightgreen)
![Typed](https://img.shields.io/badge/typed-jackson-blue)

## 📚 Table of Contents

- [Features](#-features)
- [Installation](#-installation)
- [Quickstart](#-quickstart)
- [Authentication](#-authentication)
- [Resource Reference](#-resource-reference)
- [Error Handling](#-error-handling)
- [Async Support](#-async-support)
- [Pagination](#-pagination)
- [Migration from twilio-java](#-migration-from-twilio-java)
- [Rate Limits](#-rate-limits)
- [Development](#-development)
- [API Documentation](#-api-documentation)
- [Contributors](#-contributors)
- [Sponsors](#-sponsors)
- [License](#-license)

## ✨ Features

### 🛡️ Strongly Typed End-to-End
- **Jackson-databind models** for every one of the 81 API operations across 9 resource families — request builders, response payloads, and entity types.
- **Autocomplete everywhere.** Your IDE knows the shape of every field — `Call.getSid()`, `Recording.getDuration()`, `Queue.getCurrentSize()` are all typed.
- **Twilio-compatible wire shapes** — `AccountSid`, `From`, `To`, status callbacks, pagination envelopes — match what Twilio's Programmable Voice API documents.

### ⚡ Modern Runtime
- Built on the standard **`java.net.http.HttpClient`** (Java 11+). No OkHttp, Apache HttpClient, Lombok, or Spring on the classpath.
- **Single non-test dependency:** `jackson-databind`. Slim transitive footprint for downstream apps.
- Compatible with **Java 11 LTS through 21+**.

### 🔁 Production-Grade Transport
- **Automatic retry** with exponential backoff on 429 / 5xx — honors `Retry-After` headers.
- **Configurable timeout** via the builder, per-client.
- **HTTP Basic auth** with `AccountSid:ApiKey` — exactly what the Twilio SDK uses, so existing credentials work unchanged.
- **Structured exception hierarchy** — `RateLimitException`, `AuthenticationException`, `NotFoundException`, etc. all subclasses of `ApiException` you can catch broadly or narrowly. `ApiException` itself extends `VoiceMLException`.

### 📞 Complete API Coverage
- **Calls** — originate, fetch, terminate, update + per-call recordings, streams, siprec, transcriptions, notifications, events, user-defined messages, and the `/Calls/{sid}/Payments` lifecycle (Pay TwiML companion).
- **Conferences** — list, fetch, end conferences, plus participants (mute / hold / kick) and conference-scoped recordings.
- **Queues** — create, list, update, delete, peek, dequeue (front or specific member).
- **Applications** — CRUD on stored TwiML + callback bundles.
- **Recordings** — account-wide list, metadata fetch, audio fetch (follows S3 redirect), delete.
- **Messages** — create, fetch, list (To/From/DateSent filters + pagination), update (Body redaction; Status=canceled), delete.
- **IncomingPhoneNumbers** — list, fetch, update.
- **Notifications** — fetch, list.
- **SIP** — SIP Trunking: Domains (CRUD), CredentialLists + Credentials (CRUD), IpAccessControlLists + IpAddresses (CRUD), Domain↔ACL/CredentialList mappings (historical, Auth/Calls, Auth/Registrations namespaces).
- **Routes V2** — Twilio Inbound Processing Region API: `client.routesV2().sipDomains().fetch(name)` / `update(name, req)`.
- **Diagnostics** — `/health` deep probe, OpenAPI spec.

### 🧪 Tested
- **66 unit tests** covering transport, retry, error mapping, pagination envelope parsing, and request/response serialization.
- JUnit 5 + AssertJ; runs as part of `mvn test`.

### 📦 Clean Distribution
- Zero codegen footprint — every byte hand-written.
- Published as jar + sources + javadoc.

## 🚀 Installation

### Maven

```xml
<dependency>
  <groupId>com.voicetel</groupId>
  <artifactId>voiceml</artifactId>
  <version>0.7.1</version>
</dependency>
```

### Gradle (Groovy DSL)

```groovy
implementation 'com.voicetel:voiceml:0.7.1'
```

### Gradle (Kotlin DSL)

```kotlin
implementation("com.voicetel:voiceml:0.7.1")
```

Requires Java 11 or later.

## 🏁 Quickstart

```java
import com.voicetel.voiceml.VoicemlClient;
import com.voicetel.voiceml.models.Call;
import com.voicetel.voiceml.models.CreateCallRequest;
import com.voicetel.voiceml.models.Queue;

public class Example {
    public static void main(String[] args) {
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

        for (Queue q : client.queues().list().getQueues()) {
            System.out.println(q.getFriendlyName() + " " + q.getCurrentSize());
        }
    }
}
```

## 🔑 Authentication

Every endpoint uses **HTTP Basic** with your `AccountSid` as the username and your per-tenant API key as the password — identical to Twilio's auth shape, so credentials issued for Twilio code work here unchanged.

```java
VoicemlClient client = VoicemlClient.builder()
        .accountSid("AC...")
        .apiKey("...")
        .build();

// Uses your AccountSid + key on every call.
var health = client.diagnostics().health();
```

> Don't have credentials yet? See **[voicetel.com/docs/api/v0.7/voiceml/](https://voicetel.com/docs/api/v0.7/voiceml/)** for issuance and rotation.

## 🗺️ Resource Reference

| Resource | Accessor | Covers |
|---|---|---|
| Calls | `client.calls()` | originate, fetch, list, terminate, update + per-call recordings, streams, siprec, transcriptions, notifications, events, user-defined messages, payments |
| Conferences | `client.conferences()` | list, fetch, end; participants (mute / hold / kick); conference-scoped recordings |
| Queues | `client.queues()` | create, list, update, delete; peek, dequeue (front or specific member) |
| Applications | `client.applications()` | CRUD on TwiML + callback bundles |
| Recordings | `client.recordings()` | account-wide list, metadata, audio fetch, delete (follows S3 redirect for audio) |
| Messages | `client.messages()` | create, fetch, list, update, delete (To/From/DateSent filters; Body redaction; Status=canceled) |
| IncomingPhoneNumbers | `client.incomingPhoneNumbers()` | list, fetch, update |
| Notifications | `client.notifications()` | fetch, list |
| Diagnostics | `client.diagnostics()` | `/health`, OpenAPI spec |

Every method that takes a request body accepts a typed builder from `com.voicetel.voiceml.models`:

```java
import com.voicetel.voiceml.models.CreateCallRequest;
import com.voicetel.voiceml.models.StartPaymentRequest;

Call call = client.calls().create(CreateCallRequest.builder()
        .to("+18005551234")
        .from("+18005550000")
        .url("https://example.com/twiml")
        .build());

// On a live call, open a Pay session:
var session = client.calls().startPayment(call.getSid(),
        StartPaymentRequest.builder()
                .idempotencyKey("order-482917")
                .statusCallback("https://example.com/pay-status")
                .build());

System.out.println(session.getSid() + " " + session.getStatus());
```

## 🚨 Error Handling

All HTTP errors throw subclasses of `com.voicetel.voiceml.exceptions.ApiException` (itself a subclass of `VoiceMLException`). Catch broadly or narrowly:

| Status | Exception |
|--------|-----------|
| 400 | `BadRequestException` |
| 401 | `AuthenticationException` |
| 403 | `PermissionDeniedException` |
| 404 | `NotFoundException` |
| 409 | `ConflictException` |
| 410 | `GoneException` |
| 429 | `RateLimitException` |
| 501 | `NotImplementedException` |
| 5xx | `ServerException` |
| other | `ApiException` |

```java
import com.voicetel.voiceml.exceptions.NotFoundException;
import com.voicetel.voiceml.exceptions.RateLimitException;

try {
    Call call = client.calls().get("CA0000000000000000000000000000aaaa");
} catch (NotFoundException e) {
    System.out.println("That call isn't on your account.");
} catch (RateLimitException e) {
    System.out.println("Slow down — backoff and retry.");
}
```

The Twilio-compatible error body (`code`, `message`, `more_info`, `status`) is exposed on `ApiException.getCode()` (numeric Twilio-style code) and `ApiException.getBody()` (raw payload).

## ⚡ Async Support

This release ships only the synchronous surface. The underlying `java.net.http.HttpClient` is async-capable, and a `CompletableFuture`-shaped SDK surface may land in a later release. For now, dispatch SDK calls onto your own executor when you need parallelism:

```java
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executors;

var pool = Executors.newFixedThreadPool(8);

CompletableFuture<Call> future = CompletableFuture.supplyAsync(
        () -> client.calls().create(CreateCallRequest.builder()
                .to("+18005551234")
                .from("+18005550000")
                .url("https://example.com/twiml")
                .build()),
        pool);
```

## 📄 Pagination

List endpoints return models extending `Page`, which exposes `getPage()`, `getPageSize()`, `getTotal()`, `getNextPageUri()`, and `getPreviousPageUri()` — the Twilio-compatible pagination envelope. Walk pages manually using the literal `Page` / `PageSize` query params on the corresponding `…Params` builder:

```java
import com.voicetel.voiceml.models.ListCallsParams;

var page1 = client.calls().list(
        ListCallsParams.builder()
                .status("completed")
                .pageSize(200)
                .page(0)
                .build());
```

The Twilio wire names `StartTime>=` and `StartTime<=` aren't valid Java identifiers; in the SDK they're modeled as `startTimeGte` / `startTimeLte` on `ListCallsParams`, then translated back to the wire names when the query string is built:

```java
client.calls().list(
        ListCallsParams.builder()
                .startTimeGte("2026-04-01T00:00:00Z")
                .startTimeLte("2026-04-30T23:59:59Z")
                .pageSize(50)
                .build());
```

## 🔁 Migration from twilio-java

The `AccountSid` + API key pair Twilio's SDK initializes with works unchanged here:

```java
// Before — Twilio
// Twilio.init(accountSid, apiKey);
// Call call = Call.creator(...).create();

// After — VoiceML (Twilio-compatible)
VoicemlClient client = VoicemlClient.builder()
        .accountSid(accountSid)
        .apiKey(apiKey)
        .build();

Call call = client.calls().create(CreateCallRequest.builder()
        .to("+18005551234")
        .from("+18005550000")
        .url("https://example.com/twiml")
        .build());
```

Method names follow the resource map above (`client.calls().create(...)`, `client.queues().list()`, …) rather than Twilio's `Twilio.init(...)` + static-creator chain — flatter, fewer keystrokes, same wire format on the way out.

## ⏱️ Rate Limits

VoiceML applies per-tenant rate limits at the edge. The SDK automatically retries 429 responses with `Retry-After` honored, up to `maxRetries` (default 2). To bump it:

```java
import java.time.Duration;

VoicemlClient client = VoicemlClient.builder()
        .accountSid("AC...")
        .apiKey("...")
        .maxRetries(4)
        .timeout(Duration.ofSeconds(60))
        .build();
```

## 🛠️ Development

```bash
git clone https://github.com/voicetel/voiceml-java-sdk
cd voiceml-java-sdk

# Unit tests (fast, no network)
mvn test

# Full verify: compile + tests + package
mvn verify

# Build jar (also produces sources + javadoc jars)
mvn package
```

## 📖 API Documentation

- **Reference docs:** [voicetel.com/docs/api/v0.7/voiceml/](https://voicetel.com/docs/api/v0.7/voiceml/)
- **Validator:** [voicetel.com/voiceml/validator/](https://voicetel.com/voiceml/validator/)
- **SDK catalogue:** [voicetel.com/docs/voiceml-sdks/](https://voicetel.com/docs/voiceml-sdks/)
- **Type definitions:** see the `com.voicetel.voiceml.models` package — every wire shape has a Jackson model.

## 🙌 Contributors

- [Michael Mavroudis](https://github.com/mavroudis) — Lead Developer

Contributions welcome. Open an issue describing the change you want to make, or send a pull request against `main`.

## 💖 Sponsors

| Sponsor | Contribution |
|---------|--------------|
| [VoiceTel Communications](https://voicetel.com) | Primary development and production hosting |

## 📄 License

MIT with the Commons Clause restriction. See [LICENSE](LICENSE) and [voicetel.com/legal/](https://voicetel.com/legal/).
