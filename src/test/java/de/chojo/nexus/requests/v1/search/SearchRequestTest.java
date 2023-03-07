/*
 *     SPDX-License-Identifier: AGPL-3.0-only
 *
 *     Copyright (C) RainbowDashLabs and Contributor
 */

package de.chojo.nexus.requests.v1.search;

import de.chojo.nexus.NexusRest;
import de.chojo.nexus.entities.PageComponentXO;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class SearchRequestTest {
    NexusRest rest;
    SearchRequest request;

    @BeforeEach
    void setUp() {
        rest = NexusRest.builder("eldonexus.de").build();
        request = rest.v1().search().search().repository("maven-releases").mavenExtension("jar");
    }

    @Test
    void queue() {
        PageComponentXO complete = request.queue().join();
        Assertions.assertTrue(complete.items().size() > 0);
    }

    @Test
    void complete() {
        PageComponentXO complete = request.complete();
        Assertions.assertTrue(complete.items().size() > 0);
    }
}
