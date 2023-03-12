/*
 *     SPDX-License-Identifier: AGPL-3.0-only
 *
 *     Copyright (C) RainbowDashLabs and Contributor
 */

package de.chojo.nexus.requests.v1.repositories;

import de.chojo.nexus.NexusRest;
import de.chojo.nexus.entities.RepositoryXO;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

class ListRequestTest {
    NexusRest rest;
    ListRequest request;

    @BeforeEach
    void setUp() {
        rest = NexusRest.builder("eldonexus.de").build();
        request = rest.v1().repositories().list();
    }

    @Test
    void complete() {
        List<RepositoryXO> complete = request.complete();
        Assertions.assertTrue(complete.size() > 0);
    }
}
