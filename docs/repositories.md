---
title: Repositories
sidebar_position: 6
---

# Repositories

```java
List<RepositoryXO> repositories = nexus.v1().repositories().list().complete();

for (RepositoryXO repository : repositories) {
    System.out.println(repository.name() + " " + repository.type() + " " + repository.format());
}
```

Nexus lists the repositories the current user may browse.

| Method | Content |
|---|---|
| `name()` | the name you use in searches, like `maven-releases` |
| `format()` | the format, like `maven2`, `npm` or `docker` |
| `type()` | `RepositoryType.HOSTED`, `PROXY` or `GROUP` |
| `url()` | the URL of the repository |
| `attributes()` | format specific attributes as a map, often empty |
