---
title: Assets
sidebar_position: 5
---

# Assets

An asset is a single file in a repository. You need its id for the calls on this page. Ids come from a
[search](./search.md) or from a list. They change when a Nexus moves to another datastore, so do not store them for
long.

## Read an asset

```java
AssetXO asset = nexus.v1().assets().get(id).complete();
```

## List the assets of a repository

```java
PageAssetXO page = nexus.v1().assets().list("maven-releases").complete();
```

The list comes in pages like a search. Pass the token of the previous page with `continuationToken(token)` on a new
request; see [Pages](./search.md#pages).

## Download

There are three ways to get the file. Each first reads the asset and then downloads its `downloadUrl`.

```java
byte[] bytes = nexus.v1().assets().download(id).complete();

try (InputStream in = nexus.v1().assets().downloadStream(id).complete()) {
    in.transferTo(out);
}

Path file = nexus.v1().assets().downloadToFile(id)
        .path(Path.of("download.jar"))
        .complete();
```

| Request | Result |
|---|---|
| `download(id)` | the whole file in memory |
| `downloadStream(id)` | an open stream of the file. Close it when you are done. |
| `downloadToFile(id)` | the path of the written file. Without `path(...)` a new temporary file is created. |

If you already have an `AssetXO`, it has the same three as shortcuts: `asset.download()`, `asset.downloadStream()` and
`asset.downloadToFile()`.

## Delete an asset

```java
nexus.v1().assets().delete(id).complete();
```

The user you logged in with needs the delete privilege on the repository.
