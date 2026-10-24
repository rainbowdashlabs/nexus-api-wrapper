---
title: Upgrading to 2.0
sidebar_position: 8
---

# Upgrading to 2.0

Version 2.0.0 follows 1.0.5. Most code keeps compiling; check these points.

## Requirements

- Java 21 or newer (1.0.5 needed Java 18).
- Jackson 3 (`tools.jackson`) instead of Jackson 2. `NexusRestImpl.objectMapper()` and `Mapper.create(...)` return a
  `tools.jackson.databind.ObjectMapper` now. Your own code can keep using Jackson 2 next to it.
- The library has a module descriptor, module `de.chojo.nexus`.
- It depends on `httpcore5` instead of `httpclient5`, on `bucket4j_jdk17-core` instead of `bucket4j-core`, and no
  longer on the Kotlin standard library or `jsr305`. Add them yourself if your code used them through this library.

## API changes

| 1.0.5 | 2.0.0 |
|---|---|
| `AssetXO.Repository()`, always `null` | `AssetXO.repository()`, the repository name |
| `int AssetXO.fileSize()` | `long AssetXO.fileSize()` |
| errors as `RuntimeException`, wrapped twice by `complete()` | `NexusException` with `statusCode()`, `body()` and `uri()`; see [Errors](./requests.md#errors) |
| a search parameter set twice was sent twice | it replaces the earlier value, so a request can be reused for the next page |
| default executor with normal threads, kept the JVM running | daemon threads |
| only `NexusRest.builder(host)` | also `NexusRest.builder(URI baseUrl)` for plain HTTP or a context path |
| `NexusRestImpl(HttpClient, ScheduledExecutorService, String host, Map)` | `NexusRestImpl(HttpClient, ScheduledExecutorService, URI baseUrl, Map)` |
| `dispatch(Callable)`, `dispatchAsync(Callable)` | `dispatch(Supplier)`, `dispatchAsync(Supplier)` |

Removed, because they were empty or unused: `NexusAPI`, `serialization.serializer`, both `SearchResponse` classes,
`AbstractApiRepository` and `RepositoryFormat`.

## Licence

2.0.0 is licensed under LGPL-3.0-or-later. 1.0.5 was AGPL-3.0.
