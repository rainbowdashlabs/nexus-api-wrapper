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

When Nexus answers with a status outside 200 to 299, the request fails with a `RuntimeException` whose message is
the response body. Network errors and broken JSON also end up as a `RuntimeException`, with the original exception as
the cause.

- `complete()` throws the exception, wrapped in one more `RuntimeException`.
- `queue()` completes the future exceptionally.

The HTTP status code is not part of the exception. Nexus usually puts it into the body, for example
`"status-code" : 422`.
