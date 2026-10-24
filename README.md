# Nexus API Wrapper

A Java client for the REST API of Sonatype Nexus Repository 3: search components and assets, download and delete
assets, list repositories. Every request runs blocking or asynchronous, and all requests share a rate limit.

[![Release](https://img.shields.io/nexus/maven-releases/de.chojo/nexus-api-wrapper?label=Release&logo=Release&server=https%3A%2F%2Feldonexus.de&style=for-the-badge)][release]
[![Snapshot](https://img.shields.io/nexus/s/de.chojo/nexus-api-wrapper?color=orange&label=Snapshot&server=https%3A%2F%2Feldonexus.de&style=for-the-badge)][snapshot]

## Requirements

Java 21 or newer, and a Nexus Repository 3.

## Dependency

```kotlin
repositories {
    maven("https://eldonexus.de/repository/maven-public/")
}

dependencies {
    implementation("de.chojo:nexus-api-wrapper:2.0.0")
}
```

```xml
<repository>
    <id>eldonexus</id>
    <url>https://eldonexus.de/repository/maven-public/</url>
</repository>

<dependency>
    <groupId>de.chojo</groupId>
    <artifactId>nexus-api-wrapper</artifactId>
    <version>2.0.0</version>
</dependency>
```

Documentation: https://docs.chojo.dev/nexus-api-wrapper

[release]: https://eldonexus.de/#browse/browse:maven-releases:de%2Fchojo%2Fnexus-api-wrapper
[snapshot]: https://eldonexus.de/#browse/browse:maven-snapshots:de%2Fchojo%2Fnexus-api-wrapper
