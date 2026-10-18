# Security Policy

## Supported Versions

| Version | Supported |
| ------- | --------- |
| 0.9.x   | ✅        |
| < 0.9   | ❌        |

## Reporting a Vulnerability

Please **do not** open a public issue for security vulnerabilities.

Use GitHub's private vulnerability reporting for this repository:
**Security → Report a vulnerability** (or
<https://github.com/voicetel/voiceml-java-sdk/security/advisories/new>).

Include, where possible:

- A description of the issue and its impact
- Steps to reproduce or a proof of concept
- Affected version(s) and configuration

You can expect an acknowledgement within a few business days. Please
allow reasonable time for a fix before any public disclosure.

## Scope Notes

This SDK constructs authenticated HTTP requests to the VoiceML REST
API. Hardening expectations on the consumer side:

- Do not log the configured API key or the `Authorization` header;
  both carry your secret in HTTP Basic form. `toString()` of the
  client, `ClientOptions` and `RequestValidator` never includes the key.
- Keep the AccountSid and API key out of source control; load them from
  a secret manager or the environment.
- The SDK uses `java.net.http.HttpClient` with the JDK default TLS
  posture (TLS 1.2+ via JSSE). If you supply your own `HttpClient`, you
  are responsible for matching that posture, and you should leave its
  redirect policy at the default `Redirect.NEVER` so the SDK's redirect
  rules (below) apply.
- Verify inbound webhooks with `RequestValidator` before acting on them.

## SDK Behaviour Relevant to Security

- **Retries never duplicate side effects.** `GET`, `HEAD`, `PUT` and
  `DELETE` are retried on 429 / 500 / 502 / 503 / 504 and on network
  errors. `POST` and `PATCH` are retried only on 429 and on connection
  failures where the request provably was not sent (DNS failure,
  connection refused, connect timeout, TLS handshake failure), never on
  a 5xx, a read timeout or a reset connection. `Retry-After` is honoured
  (delta-seconds or HTTP-date) up to 60 seconds; a longer request raises
  the error immediately with the delay on `getRetryAfter()`.
- **Redirects.** Only binary downloads (recording audio, exports) follow
  redirects, manually and at most 5 hops. `Authorization` is dropped as
  soon as a hop leaves the API's scheme, host and port, and an
  https→http redirect is refused.
- **Pagination links.** Next-page links are followed only on the host
  that served the page; a link to any other host raises
  `PaginationException` without sending credentials.
- **Path parameters** are percent-encoded per RFC 3986 (an ID cannot
  inject a query string or traverse the path), and empty, `.` or `..`
  IDs are rejected before any request is sent.
- **Diagnostics** (`/health`, `/openapi.*`) are requested without
  `Authorization`.

Out of scope: vulnerabilities in the published `com.voicetel:voiceml`
Maven artifact caused by a forked / vendored copy that has been
modified.
