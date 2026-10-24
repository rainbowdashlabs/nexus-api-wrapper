---
title: Search
sidebar_position: 4
---

# Search

Nexus searches components (a group, name and version with all its files) or assets (single files). Both take the
same parameters.

## Search components

```java
PageComponentXO page = nexus.v1().search().search()
        .repository("maven-releases")
        .group("de.chojo")
        .name("nexus-api-wrapper")
        .complete();

for (ComponentXO component : page) {
    System.out.println(component.version() + " has " + component.assets().size() + " files");
}
```

## Search assets

```java
PageAssetXO page = nexus.v1().search().assets().search()
        .repository("maven-releases")
        .mavenGroupId("de.chojo")
        .mavenArtifactId("nexus-api-wrapper")
        .mavenExtension("jar")
        .sort(Sort.VERSION)
        .direction(Direction.DESC)
        .complete();

for (AssetXO asset : page.items()) {
    System.out.println(asset.path());
}
```

## Download the one matching asset

`search().assets().download()` downloads the file of the asset that matches your search, in one call:

```java
byte[] jar = nexus.v1().search().assets().download()
        .repository("maven-releases")
        .mavenGroupId("de.chojo")
        .mavenArtifactId("nexus-api-wrapper")
        .mavenBaseVersion("1.0.5")
        .mavenExtension("jar")
        .complete();
```

The search has to match exactly one asset. If it matches more, Nexus answers with an error, unless you set `sort(...)`:
then it takes the first one. If nothing matches, it answers with 404.

## Parameters

Every parameter is a method on the search request. Values are sent as they are; Nexus decides how to match them.

| Method | Nexus parameter |
|---|---|
| `query(String)` | `q`, a keyword search |
| `repository(String)` | `repository` |
| `format(String)` | `format`, like `maven2`, `npm` or `docker` |
| `group(String)`, `name(String)`, `version(String)` | `group`, `name`, `version` |
| `prerelease(String)` | `prerelease` |
| `md5(String)`, `sha1(String)`, `sha256(String)`, `sha512(String)` | the checksums of an asset |
| `sort(Sort)` | `sort`: `GROUP`, `NAME`, `VERSION` or `REPOSITORY` |
| `direction(Direction)` | `direction`: `ASC` or `DESC`. Nexus sorts ascending by default, except by version, which is descending. |
| `timeout(int)` | `timeout`, seconds Nexus waits for search results |
| `continuationToken(String)` | `continuationToken`, see [Pages](#pages) |

Format specific parameters:

| Format | Methods |
|---|---|
| Maven | `mavenGroupId`, `mavenArtifactId`, `mavenBaseVersion`, `mavenExtension`, `mavenClassifier`, `gavec` |
| Docker | `dockerImageName`, `dockerImageTag`, `dockerLayerId`, `dockerContentDigest` |
| npm | `npmScope`, `npmAuthor`, `npmDescription`, `npmKeywords`, `npmLicense`, `npmTaggedIs`, `npmTaggedNot` |
| NuGet | `nugetId`, `nugetTags`, `nugetTitle`, `nugetAuthors`, `nugetDescription`, `nugetSummary` |
| PyPI | `pypiClassifiers`, `pypiDescription`, `pypiKeywords`, `pypiSummary` |
| RubyGems | `rubygemsDescription`, `rubygemsPlatform`, `rubygemsSummary` |
| Yum | `yumArchitecture`, `yumName` |
| Conan | `conanBaseVersion`, `conanChannel`, `conanRevision` |
| P2 | `p2PluginName` |

Newer Nexus versions know more parameters (for example `repositoryName`, `raw.name` or the OCI, Composer and
Terraform ones). The client does not offer them yet.

## Pages

Nexus returns results in pages. The page has a `continuationToken()` while there are more results, and `null` on the
last page. Build a new request for every page and pass the token of the previous one:

```java
List<AssetXO> all = new ArrayList<>();
String token = null;
do {
    var request = nexus.v1().search().assets().search().repository("maven-releases");
    if (token != null) request.continuationToken(token);
    PageAssetXO page = request.complete();
    all.addAll(page.items());
    token = page.continuationToken();
} while (token != null);
```

:::warning
Do not reuse one request object for the next page. Each call of a parameter method adds the parameter again, and Nexus
only reads the first token, so you get the same page again.
:::
