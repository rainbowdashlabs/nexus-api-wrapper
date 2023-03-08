/*
 *     SPDX-License-Identifier: AGPL-3.0-only
 *
 *     Copyright (C) RainbowDashLabs and Contributor
 */

package de.chojo.nexus.requests.v1.assets;

import de.chojo.nexus.NexusRest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.*;

class DownloadToFileRequestTest {
    NexusRest rest;
    DownloadToFileRequest request;

    @BeforeEach
    void setUp() {
        rest = NexusRest.builder("eldonexus.de").build();
        // Download the first asset we can find
        request = rest.v1().assets().downloadToFile("bWF2ZW4tcmVsZWFzZXM6MTNiMjllNDQ5ZjBlM2I4ZGE0ZWFlMWFiNjI0MzhiZTU");
    }

    @Test
    void complete() {
        Path complete = request.complete();
        Assertions.assertTrue(complete.toFile().exists());
    }
}
