/*
 *     SPDX-License-Identifier: LGPL-3.0-or-later
 *
 *     Copyright (C) RainbowDashLabs and Contributor
 */

package de.chojo.nexus.requests.v1.assets;

import de.chojo.nexus.NexusRest;
import de.chojo.nexus.entities.AssetXO;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

@Tag("live")
class DownloadToFileRequestTest {
    @Test
    void complete() throws IOException {
        NexusRest rest = NexusRest.builder("eldonexus.de").build();
        AssetXO asset = LiveAssets.jar(rest);
        Path file = rest.v1().assets().downloadToFile(asset.id()).complete();
        try {
            Assertions.assertEquals(asset.checksum().md5(), LiveAssets.md5(Files.readAllBytes(file)));
        } finally {
            Files.deleteIfExists(file);
        }
    }
}
