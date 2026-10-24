---
title: Client
sidebar_position: 2
---

# Client

Everything starts with a `NexusRest`. Create one per Nexus and reuse it. It holds the HTTP client, the rate limit and
the threads for asynchronous requests.

## Create a client

```java
NexusRest nexus = NexusRest.defaultApi("eldonexus.de");
```

`defaultApi(host)` is short for `NexusRest.builder(host).build()`. Use the builder when you want to change anything:

```java
NexusRest nexus = NexusRest.builder("nexus.example.com")
        .setPasswordAuth("deployer", System.getenv("NEXUS_PASSWORD"))
        .build();
```

With a host name, like `nexus.example.com` or `nexus.example.com:8443`, the client talks to
`https://<host>/service/rest`.

For a Nexus on plain HTTP or below a path, pass its base URL instead. The client adds `/service/rest` itself:

```java
NexusRest nexus = NexusRest.builder(URI.create("http://localhost:8081")).build();
NexusRest behindProxy = NexusRest.builder(URI.create("https://example.com/nexus")).build();
```

## Log in

Anonymous access is enough for public repositories. For anything else, log in with one of these:

| Method | What it does |
|---|---|
| `setPasswordAuth(username, password)` | Sends an HTTP Basic `Authorization` header with every request. Works for users and for user tokens. |
| `setAuth(Authenticator)` | Sets a `java.net.Authenticator` on the HTTP client. Java only sends credentials after the server asked for them. |
| `header(key, value)` | Adds any header to every request, for example a token header of a proxy in front of your Nexus. |

The headers go to every URL the client calls, also to the `downloadUrl` of an asset. Nexus returns download URLs on
its own host, so this is the same server.

## Use your own HTTP client

By default the builder creates a `java.net.http.HttpClient` that prefers HTTP/2, follows redirects (but not from
HTTPS to HTTP) and waits up to 60 seconds for a connection. Pass your own client to change that:

```java
HttpClient http = HttpClient.newBuilder()
        .connectTimeout(Duration.ofSeconds(10))
        .build();

NexusRest nexus = NexusRest.builder("nexus.example.com")
        .setHttp(http)
        .build();
```

:::note
With `setHttp(...)` the builder uses your client as it is. An `Authenticator` set with `setAuth(...)` is ignored then;
set it on your own client instead. Headers from `setPasswordAuth(...)` and `header(...)` still work.
:::

## Threads for asynchronous requests

`queue()` runs requests on a `ScheduledExecutorService`. By default the builder creates a pool with two threads. Pass
your own to control the threads:

```java
ScheduledExecutorService executor = Executors.newScheduledThreadPool(4);

NexusRest nexus = NexusRest.builder("nexus.example.com")
        .setExecutorService(executor)
        .build();
```

The default pool uses daemon threads named `nexus-api-wrapper-<n>`, so it never keeps your program running. The client
has no `close()`; an executor you pass in is yours to shut down.

## Logging

The client logs every request URI and every JSON response body at `TRACE` level through SLF4J, under the logger
`de.chojo.nexus.NexusRestImpl`.
