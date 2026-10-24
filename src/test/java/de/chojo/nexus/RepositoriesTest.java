/*
 *     SPDX-License-Identifier: LGPL-3.0-or-later
 *
 *     Copyright (C) RainbowDashLabs and Contributor
 */

package de.chojo.nexus;

import de.chojo.nexus.entities.RepositoryType;
import de.chojo.nexus.entities.RepositoryXO;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class RepositoriesTest {
    @Test
    void list() throws IOException {
        try (FakeNexus nexus = FakeNexus.start()) {
            List<RepositoryXO> repositories =
                    nexus.client().v1().repositories().list().complete();
            assertEquals(
                    List.of("maven-releases", "maven-public", "maven-dev"),
                    repositories.stream().map(RepositoryXO::name).toList());
            assertEquals(RepositoryType.GROUP, repositories.get(1).type());
            assertEquals("maven2", repositories.getFirst().format());
        }
    }
}
