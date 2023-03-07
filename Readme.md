![GitHub Workflow Status](https://img.shields.io/github/actions/workflow/status/rainbowdashlabs/nexus-api-wrapper/verify.yml?branch=master&style=for-the-badge&label=Building)
![GitHub Workflow Status](https://img.shields.io/github/actions/workflow/status/rainbowdashlabs/nexus-api-wrapper/publish_to_nexus.yml?branch=master&style=for-the-badge&label=Publishing) \
![Sonatype Nexus (Releases)](https://img.shields.io/nexus/maven-releases/de.chojo/nexus-api-wrapper?label=Release&logo=Release&server=https%3A%2F%2Feldonexus.de&style=for-the-badge)
![Sonatype Nexus (Development)](https://img.shields.io/nexus/maven-dev/de.chojo/nexus-api-wrapper?label=DEV&logo=Release&server=https%3A%2F%2Feldonexus.de&style=for-the-badge)
![Sonatype Nexus (Snapshots)](https://img.shields.io/nexus/s/de.chojo/nexus-api-wrapper?color=orange&label=Snapshot&server=https%3A%2F%2Feldonexus.de&style=for-the-badge)

### [Javadocs](https://rainbowdashlabs.github.io/nexus-api-wrapper/)

## Dependency

**Gradle**

``` kotlin
repositories {
    maven("https://eldonexus.de/repository/maven-public")
}

dependencies {
    compileOnly("de.chojo", "nexus-api-wrapper", "version")
}
```

**Maven**

``` xml
<repository>
    <id>EldoNexus</id>
    <url>https://eldonexus.de/repository/maven-public/</url>
</repository>

<dependency>
    <groupId>de.chojo</groupId>
    <artifactId>nexus-api-wrapper</artifactId>
    <version>version</version>
</dependency>
```
