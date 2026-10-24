---
title: Entities
sidebar_position: 7
---

# Entities

The objects the requests return. Fields that Nexus sends but the client does not know are ignored.

## AssetXO

A single file.

| Method | Content |
|---|---|
| `id()` | the asset id |
| `path()` | the path in the repository, like `/de/chojo/nexus-api-wrapper/1.0.5/nexus-api-wrapper-1.0.5.jar` |
| `downloadUrl()` | the URL of the file |
| `repository()` | the repository name |
| `format()` | the format, like `maven2` |
| `contentType()` | the media type, like `application/java-archive` |
| `lastModified()` | when the file was last changed |
| `lastDownloaded()` | when it was last downloaded, or `null` |
| `checksum()` | a `Checksum` with `md5()`, `sha1()`, `sha256()` and `sha512()` |
| `uploader()`, `uploaderIp()` | who uploaded the file, if Nexus knows it |
| `fileSize()` | the size in bytes, as a `long` |
| `maven2()` | a `MavenMeta` for Maven assets: `groupId()`, `artifactId()`, `version()`, `classifier()`, `extension()` |

## ComponentXO

A component with its files: `id()`, `repository()`, `format()`, `group()`, `name()`, `version()` and `assets()`, a list
of `AssetXO`.

## Pages

| Type | Content |
|---|---|
| `PageComponentXO` | `items()` and `continuationToken()`. You can also loop over it directly, or use `stream()`, `size()` and `isEmpty()`. |
| `PageAssetXO` | `items()` and `continuationToken()` |

## RepositoryXO

See [Repositories](./repositories.md).
