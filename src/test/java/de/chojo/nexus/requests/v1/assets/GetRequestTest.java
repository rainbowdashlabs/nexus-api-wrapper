/*
 *     SPDX-License-Identifier: AGPL-3.0-only
 *
 *     Copyright (C) RainbowDashLabs and Contributor
 */

package de.chojo.nexus.requests.v1.assets;

import de.chojo.nexus.NexusRest;
import de.chojo.nexus.entities.AssetXO;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GetRequestTest {
    NexusRest rest;
    GetRequest request;
    String id = "bWF2ZW4tcmVsZWFzZXM6MTNiMjllNDQ5ZjBlM2I4ZGE0ZWFlMWFiNjI0MzhiZTU";

    @BeforeEach
    void setUp() {
        rest = NexusRest.builder("eldonexus.de").build();
        // Download the first asset we can find
        request = rest.v1().assets().get(id);
    }

    @Test
    void complete() {
        AssetXO complete = request.complete();
        Assertions.assertEquals(id, complete.id());
    }
}
