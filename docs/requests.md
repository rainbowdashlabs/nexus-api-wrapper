---
title: Requests
sidebar_position: 3
---

# Requests

Each call on the client returns a request object. Nothing is sent until you run it. Every request has two ways to run.

## Blocking or asynchronous

```java
AssetXO asset = nexus.v1().assets().get(id).complete();

nexus.v1().assets().get(id).queue()
        .thenAccept(asset -> System.out.println(asset.path()));
```

| Method | Runs on | Returns |
|---|---|---|
| `complete()` | your thread, blocks until the answer is there | the result |
| `queue()` | the client's executor (see [Client](./client.md#threads-for-asynchronous-requests)) | a `CompletableFuture` with the result |

## Rate limit

All requests of one client share a limit of 50 requests, refilled at 50 requests per 2 seconds. When the limit is
used up, `complete()` waits on your thread and `queue()` waits without blocking a thread. The limit is fixed.

Some requests send more than one HTTP request. Downloading an asset by id first reads the asset and then downloads
its file, so it counts twice.

## Errors

When Nexus answers with a status outside 200 to 299, the request fails with a `NexusException`:

```java
try {
    nexus.v1().assets().get(id).complete();
} catch (NexusException e) {
    System.out.println(e.statusCode() + " " + e.body());
}
```

| Method | Content |
|---|---|
| `statusCode()` | the HTTP status code, like `404` or `422` |
| `body()` | the body of the answer, often JSON with details, empty when there was none |
| `uri()` | the requested URI |

Other failures:

- A network error is an `UncheckedIOException`.
- A body that does not fit the expected type is a Jackson `JacksonException`.
- An interrupt while waiting for Nexus or the rate limit is an `IllegalStateException`, and the thread keeps its
  interrupt flag.

`complete()` throws these directly. `queue()` completes the future exceptionally; `join()` then throws a
`CompletionException` with the exception as its cause.

A failed stream download closes the stream, and a failed file download deletes the file, before the exception is
thrown. The body of the error answer is in `body()`.
