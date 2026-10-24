/*
 *     SPDX-License-Identifier: LGPL-3.0-or-later
 *
 *     Copyright (C) RainbowDashLabs and Contributor
 */

package de.chojo.nexus;

import de.chojo.nexus.entities.ComponentXO;
import de.chojo.nexus.entities.PageComponentXO;
import de.chojo.nexus.requests.v1.search.Direction;
import de.chojo.nexus.requests.v1.search.Sort;
import de.chojo.nexus.requests.v1.search.assets.SearchRequest;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class SearchTest {
    @Test
    void components() throws IOException {
        try (FakeNexus nexus = FakeNexus.start()) {
            PageComponentXO page = nexus.client()
                    .v1()
                    .search()
                    .search()
                    .repository("maven-releases")
                    .group("de.chojo")
                    .name("nexus-api-wrapper")
                    .complete();
            List<String> versions = page.stream().map(ComponentXO::version).toList();
            assertEquals(List.of("1.0.2", "1.0.3"), versions);
            assertEquals(2, page.items().getFirst().assets().size());
            assertEquals(
                    "repository=maven-releases&group=de.chojo&name=nexus-api-wrapper",
                    nexus.last("/v1/search").query());
        }
    }

    @Test
    void assetsWithMavenParameters() throws IOException {
        try (FakeNexus nexus = FakeNexus.start()) {
            nexus.client()
                    .v1()
                    .search()
                    .assets()
                    .search()
                    .repository("maven-releases")
                    .mavenGroupId("de.chojo")
                    .mavenArtifactId("nexus-api-wrapper")
                    .mavenExtension("jar")
                    .sort(Sort.VERSION)
                    .direction(Direction.DESC)
                    .complete();
            assertEquals(
                    "repository=maven-releases&maven.groupId=de.chojo&maven.artifactId=nexus-api-wrapper"
                            + "&maven.extension=jar&sort=version&direction=desc",
                    nexus.last("/v1/search/assets").query());
        }
    }

    @Test
    void reusedRequestSendsOneToken() throws IOException {
        try (FakeNexus nexus = FakeNexus.start()) {
            SearchRequest request =
                    nexus.client().v1().search().assets().search().repository("maven-releases");
            String token = request.complete().continuationToken();
            assertEquals("2", token);
            assertNull(request.continuationToken(token).complete().continuationToken());
            request.continuationToken("3").complete();
            assertEquals(
                    "repository=maven-releases&continuationToken=3",
                    nexus.last("/v1/search/assets").query());
        }
    }

    @Test
    void downloadFollowsTheRedirect() throws IOException {
        try (FakeNexus nexus = FakeNexus.start()) {
            byte[] bytes = nexus.client()
                    .v1()
                    .search()
                    .assets()
                    .download()
                    .repository("maven-releases")
                    .mavenBaseVersion("1.0.5")
                    .sort(Sort.VERSION)
                    .complete();
            assertArrayEquals(FakeNexus.JAR, bytes);
        }
    }
}
