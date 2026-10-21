# Changelog

All notable changes to the VoiceML Java SDK (`com.voicetel:voiceml`) are recorded here. The
format follows [Keep a Changelog](https://keepachangelog.com/en/1.1.0/); versions follow
[Semantic Versioning](https://semver.org/). Earlier releases are described on the
[GitHub Releases](https://github.com/voicetel/voiceml-java-sdk/releases) page.

## [0.9.4] - 2026-10-21

### Added

- IncomingPhoneNumbers create/update: `VoiceApplicationSid`, `StatusCallback`,
  `StatusCallbackMethod`; OutgoingCallerIds create: `CallDelay`, `Extension`, `StatusCallback`,
  `StatusCallbackMethod`.

### Build

- `central-publishing-maven-plugin` 0.11.0 → 0.12.0. With Maven 3.10.0, the default on GitHub's
  `ubuntu-latest` runners, 0.11.0 left `maven-metadata-local.xml` and `_remote.repositories` in
  the upload bundle, and the Central Portal rejected the v0.9.3 deployment ("Bundle has content
  that does NOT have a .pom file"). The publish workflow no longer pins an older Maven, and a new
  CI job builds the bundle on every push and fails if its layout would be rejected.

## [0.9.3] - 2026-10-18

### Breaking changes

Removed endpoints. The server answers these with 405 Method Not Allowed (code 20004); the paths
now accept only `POST`, so `startStream` / `stopStream`, `startSiprec` / `stopSiprec` and
`startTranscription` / `stopTranscription` stay:

- `CallsResource.listStreams`, `iterateStreams`: `GET /2010-04-01/Accounts/{AccountSid}/Calls/{CallSid}/Streams.json`
- `CallsResource.getStream`: `GET /2010-04-01/Accounts/{AccountSid}/Calls/{CallSid}/Streams/{Sid}.json`
- `CallsResource.listSiprec`, `iterateSiprec`: `GET /2010-04-01/Accounts/{AccountSid}/Calls/{CallSid}/Siprec.json`
- `CallsResource.getSiprec`: `GET /2010-04-01/Accounts/{AccountSid}/Calls/{CallSid}/Siprec/{Sid}.json`
- `CallsResource.listTranscriptions`, `iterateTranscriptions`: `GET /2010-04-01/Accounts/{AccountSid}/Calls/{CallSid}/Transcriptions.json`
- `CallsResource.getTranscription`: `GET /2010-04-01/Accounts/{AccountSid}/Calls/{CallSid}/Transcriptions/{Sid}.json`
- The `StreamList` and `SiprecList` models are removed. `TranscriptionList` now models the
  post-recording transcription lists (`GET …/Transcriptions.json`,
  `GET …/Recordings/{RecordingSid}/Transcriptions.json`); its items are `Transcription`.

Pagination:

- Every `iterate…(...)` method returns a lazy `Pager<T>` (an `Iterable<T>` with `stream()`)
  instead of an eagerly collected `List<T>`. Collect explicitly if you need a list:
  `client.calls().iterate().stream().collect(Collectors.toList())`.
- `Page.getTotal()` / `setTotal()` and `getNumPages()` / `setNumPages()` are removed from every
  list envelope; the API no longer returns `total` or `num_pages`. `NotificationsList` and
  `EventsList` now extend `Page` (gaining `start`, `end`, `first_page_uri`, `next_page_uri`,
  `previous_page_uri`) and lose their own `getTotal()`.
- The `/v1` lists that declare only `PageSize` (Conversations, Voice v1, Assistants tools /
  knowledge / policies / sessions / messages / feedback) no longer send `Page` / `PageToken`; setting
  either on the `ListPageParams` passed to them raises `ValidationException`. Use
  `VoicemlClient.nextPage(...)` to follow `meta.next_page_url`.

Requests:

- `GET …/Calls.json`: `ListCallsParams.Builder.startTimeGte` / `startTimeLte` are removed. They
  sent `StartTime>=` / `StartTime<=`, which the API does not declare. Use `startTimeGt` /
  `startTimeLt` (`StartTime>` / `StartTime<`).
- `POST …/Messages.json`: `CreateMessageRequest.Builder.build()` raises `ValidationException`
  when neither `Body` nor `MediaUrl` is set, and `messages().create(null)` raises
  `ValidationException` instead of sending an empty form.
- `POST …/IncomingPhoneNumbers/Local.json`, `…/Mobile.json`, `…/TollFree.json`: `createLocal`,
  `createMobile` and `createTollFree` take the new `CreateTypedIncomingPhoneNumberRequest`
  (`PhoneNumber`, `VoiceUrl`, `VoiceMethod`) and no longer send `VoiceFallbackUrl` /
  `VoiceFallbackMethod`, which those endpoints do not accept.
- `POST …/Calls/{CallSid}/UserDefinedMessages.json`: `sendUserDefinedMessage(callSid, payload)` is
  replaced by `sendUserDefinedMessage(callSid)`; the endpoint declares no body (the server
  answers 501).
- `GET …/Calls/{CallSid}/Notifications/{Sid}.json` and `GET …/Notifications/{Sid}.json`:
  `calls().getNotification(...)` and `notifications().get(...)` return the typed `Notification`
  instead of `Map<String, Object>`. `NotificationsList.getNotifications()` returns
  `List<Notification>` and `EventsList.getEvents()` returns `List<CallEvent>` (were `List<Object>`).
- Path parameters are percent-encoded per RFC 3986 (`+` → `%2B`, space → `%20`, `/` → `%2F`,
  `?` → `%3F`, `#` → `%23`, `%` → `%25`). A `null`, empty, `.` or `..` identifier raises
  `ValidationException` before anything is sent; previously an empty SID was dropped from the
  path (`calls().get("")` requested `GET …/Calls.json`), and Pricing numbers were form-encoded
  (a space became `+`).
- `Transport.request(...)` / `requestJson(...)` send the path exactly as given and no longer append
  `.json`; the resource classes build the suffix from the route (`…/Recordings/Twilio.CURRENT`
  now correctly requests `Twilio.CURRENT.json`).

Responses and errors:

- Response fields the API no longer returns are removed: `Application.statusCallbackEvent`,
  `Participant.apiVersion`, `QueueMember.accountSid`, `Stream.apiVersion` / `dateCreated`,
  `SiprecSession.apiVersion` / `dateCreated` / `connectorName`, and
  `CallTranscription.apiVersion` / `dateCreated` / `languageCode` / `transcriptionEngine`.
- Every model enum has an `UNKNOWN` constant (`AnsweredBy.UNKNOWN` already existed). A value this
  SDK version does not recognise decodes to `UNKNOWN` (it used to become `null`), and `fromString`
  returns `UNKNOWN` for unrecognised text. Sending `UNKNOWN` raises `ValidationException`.
- `ApiException.setMoreInfo(String)` is removed: exceptions are immutable, and `more_info` is set
  through the new constructors.
- `Transport.mapper()` is removed: the SDK's `ObjectMapper` is private. Use `Transport.decode(...)`.
- Setting both `apiKey(...)` and `authToken(...)` raises `ConfigurationException` (was
  `IllegalStateException`), as do an invalid `baseUrl` (not absolute `http(s)`, or carrying user
  info, a query or a fragment) and a zero or negative `timeout`.

### Fixed

- `POST` / `PATCH` requests (`calls.create`, `messages.create`, every create and update) are no
  longer retried on 500 / 502 / 503 / 504, read timeouts or reset connections, so a call or message
  is never sent twice by the SDK. They are retried only on 429 and on connection failures where the
  request provably was not sent (DNS failure, connection refused, connect timeout, TLS handshake
  failure). `GET` / `HEAD` / `PUT` / `DELETE` keep retrying on 429 / 5xx and network errors.
- `Retry-After` is parsed as delta-seconds or an HTTP-date (it was seconds only, and accepted
  `Infinity`). A delay above 60 seconds is not slept: the error is raised immediately with the
  requested delay on `ApiException.getRetryAfter()`. Backoff uses full jitter (0.5 s base, 8 s
  maximum). Interrupting a waiting thread aborts the request instead of continuing to retry.
- Auto-pagination follows `next_page_uri` verbatim, including its `PageToken`, resolved against the
  host that served the page. It no longer rebuilds `Page=N+1`, which looped forever on Calls and
  Conferences and failed with 400 on page 2 of Messages and Recordings. It stops on an empty link
  or an empty page, and raises `PaginationException` for a link that does not advance or points at
  another host.
- `VoicemlClient.Builder.options(ClientOptions)` now carries the Messaging Service and
  Conversations host overrides; previously `messagingV1()` and `conversationsV1()` were routed to the
  base host.
- Recording audio (`recordings().getAudio`) uses the client's `HttpClient` and timeout instead of
  a fresh client per call, follows redirects manually (at most 5), drops `Authorization` once a
  redirect leaves the API origin, refuses an https→http redirect and sends
  `Accept: audio/*, */*`.
- Diagnostics (`health`, `openapiJson`) go through the client's transport (its `HttpClient`, proxy,
  timeout, retries and `User-Agent`) instead of a hard-coded 10-second client, still without
  `Authorization`. A 503 from `/health` raises `ServerException` without retrying; `warnings` and
  `failures` default to empty lists when the server omits them.
- Timeouts raise `RequestTimeoutException` and other network failures `TransportException` (both
  `ApiException` subclasses with status 0, cause preserved). A 2xx body that is not valid JSON
  raises `ApiException` "could not decode HTTP 200 response…" with the parser error as cause.
- Unknown response properties are ignored globally, not only on annotated classes.
- `GetRecordingParams` Javadoc had an unterminated inline tag.

### Added

- The 22 operations that were never implemented:
  - `client.accounts()`: `fetch()` / `fetch(sid)` and `update(...)` for `GET` / `POST /2010-04-01/Accounts/{Sid}.json`.
  - `client.balance().fetch()`: `GET …/Balance.json`.
  - `client.outgoingCallerIds()`: list, iterate, create (returns a `ValidationRequest`), fetch, update and delete (`…/OutgoingCallerIds.json`, `…/OutgoingCallerIds/{Sid}.json`).
  - `client.messages()`: `listMedia`, `iterateMedia`, `fetchMedia` and `deleteMedia` (`…/Messages/{MessageSid}/Media[/{Sid}].json`).
  - `client.exports()`: `list()` and `download(date)` (`…/Exports.json`, `…/Exports/{Date}`).
  - `client.transcriptions()`: list, iterate, fetch and delete (`…/Transcriptions[/{Sid}].json`).
  - `client.recordings()`: `listTranscriptions`, `iterateTranscriptions`, `getTranscription` and `deleteTranscription` (`…/Recordings/{RecordingSid}/Transcriptions[/{Sid}].json`).
  - `client.conferences().startRecording(...)`: `POST …/Conferences/{ConferenceSid}/Recordings.json`.
  - `client.diagnostics().openapiYaml()` and `openapiYml()`.
- The 14 operations added to the API since 0.9.2:
  - `client.usageRecords()`: `list` plus `listAllTime`, `listDaily`, `listLastMonth`, `listMonthly`, `listThisMonth`, `listToday`, `listYearly` and `listYesterday` (`…/Usage/Records[/{Window}].json`).
  - `client.sip().registrations()`: list and iterate (`…/SIP/Registrations.json`).
  - `client.messagingV1().services().phoneNumbers(serviceSid)`: list, create, fetch and delete (`/v1/Services/{Sid}/PhoneNumbers[/{Sid}]`, on the messaging host).
- The 4 operations newly documented in the API:
  - `client.monitorV1().alerts()`: list (`GET /v1/Alerts`, filters `LogLevel` / `StartDate` / `EndDate`, a single `PageSize`-limited page in the `meta` envelope), fetch (`GET /v1/Alerts/{Sid}`, with the stored request/response detail) and `iterate` over the page.
  - `client.messages().createFeedback(...)`: `POST …/Messages/{Sid}/Feedback.json` with `Outcome=confirmed|unconfirmed`; the no-argument-body overload submits an empty body.
  - `client.accounts().rotateKey()`: `POST …/Accounts/{AccountSid}/RotateKey.json`, returning `AccountWithKey` with the freshly generated `api_key`. A concurrent update raises `ConflictException` (code 20409).
- Request parameters the API accepts:
  - `messages.create`: `MediaUrl` (repeated keys, for MMS), `ValidityPeriod` and `StatusCallbackMethod`.
  - IncomingPhoneNumbers create and update: `SmsUrl`, `SmsMethod`, `SmsFallbackUrl`, `SmsFallbackMethod` and `SmsApplicationSid`.
  - IncomingPhoneNumbers list: `FriendlyName`, `Beta` and `Origin`.
  - Applications create and update: `MessageStatusCallback` and `SmsStatusCallback`.
  - Call recordings: `RecordingTrack` on start; `PauseBehavior` on update, via the new `UpdateCallRecordingRequest`.
  - SIP Domains, CredentialLists, IpAccessControlLists and IpAddresses lists: `FriendlyName`, via the new `ListSipParams`.
- Response fields:
  - `Application`: `sms_url`, `sms_method`, `sms_fallback_url`, `sms_fallback_method`, `message_status_callback` and `sms_status_callback`.
  - `IncomingPhoneNumber`: `subresource_uris`.
  - `MessagingService`: `advanced_opt_out`.
  - `Recording`: `track`.
  - New typed models: `Notification`, `CallEvent`, `Account`, `Balance`, `OutgoingCallerId`, `ValidationRequest`, `MessageMedia`, `Transcription`, `UsageRecord`, `SipRegistration`, `MessagingServicePhoneNumber` and `Export`.
  - New typed models for the newly documented operations: `MonitorV1Alert` / `MonitorV1AlertList`, `MessageFeedback` (+ `CreateMessageFeedbackRequest`), `AccountWithKey`, and `ListMonitorAlertsParams`.
- `RequestValidator` validates and computes the `X-Twilio-Signature` webhook signature (HMAC-SHA1 over the URL plus sorted parameters). It compares in constant time and accepts the default port added or removed.
- Pagination helpers:
  - `VoicemlClient.nextPage(page, Type.class)` for both `/2010-04-01` and `/v1` lists.
  - `VoicemlClient.paginate(firstPage, Type.class, items)`.
  - `Page.hasNextPage()`.
  - The `MetaPage` interface on `/v1` list envelopes.
  - `iterateEvents` on calls; `iterate` on notifications.
- `VoicemlClient.builder().buildDiagnostics()` reaches the unauthenticated diagnostics without credentials.
- New exceptions: `TransportException`, `RequestTimeoutException`, `MethodNotAllowedException` (405), `ValidationException` and `PaginationException`. `ApiException.getRetryAfter()` is new.
- Form and query values: `java.time` dates and date-times are sent as ISO-8601 UTC, `LocalDate` as `YYYY-MM-DD`, and maps / JSON trees as compact JSON. `ListUsageRecordsParams` accepts `LocalDate`.
- The jar manifest declares `Automatic-Module-Name: com.voicetel.voiceml`.

### Security

- jackson-databind / jackson-core 2.22.1 → **2.22.3**, which fixes:
  - jackson-databind: GHSA-cxp5-3px4-pw24, GHSA-wv8q-qhhj-9h54, GHSA-q4xh-88c3-wmh7, GHSA-gx83-3vf8-gh7j and GHSA-wjgm-6hv5-3cvf;
  - jackson-core: GHSA-7hhh-6rmp-j9qf and GHSA-p6pp-m3f8-5c89.

  0.9.2 on Maven Central pins 2.22.0. The modules are aligned through `jackson-bom`, and `jackson-core` / `jackson-annotations` are now declared explicitly.
- Retry behaviour no longer replays non-idempotent requests (see Fixed).
- Credentials are never forwarded across a redirect to another origin, and pagination links to other hosts are refused.
- `ClientOptions` and `RequestValidator` have redacted `toString()` output.
- The `AccountWithKey` returned by `accounts().rotateKey(...)` renders its secret `api_key` as `[redacted]` in `toString()`; no other debug output includes the rotated key.

### Docs

- README:
  - Coverage is stated as 333 of 333 operations.
  - Authentication is described correctly: your VoiceML AccountSid and API key in the HTTP Basic positions twilio-java uses; Twilio-issued credentials do not work.
  - Retry, `Retry-After` and pagination behaviour is documented, `getTotal()` is gone, and the `StartTime<` / `StartTime>` names are correct.
  - The full resource table, webhook validation, downloads and credential-less diagnostics are covered.
  - Every Java example compiles; this is checked by `ReadmeSnippetsTest`.
- SECURITY.md lists 0.9.x as supported and describes the retry, redirect, pagination-host and path-encoding behaviour.
- Placeholder phone numbers use the fictional `+1 415 555 01xx` range.
- The POM declares the licence as "MIT with Commons Clause" and points the project URL at the GitHub repository.
- CI:
  - A lint job runs `-Xlint:all` with warnings as errors, plus javadoc doclint `all,-missing`.
  - Tests run on JDK 17 and 21.
  - A JUnit-free runtime smoke test runs on JDK 11.
  - Publishing requires the test job and a tag that matches the POM and `Version.java`.
