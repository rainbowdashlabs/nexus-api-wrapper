/*
 *     SPDX-License-Identifier: LGPL-3.0-or-later
 *
 *     Copyright (C) RainbowDashLabs and Contributor
 */

package de.chojo.nexus.requests.v1.assets;

import de.chojo.nexus.NexusRest;
import de.chojo.nexus.entities.AssetXO;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.InputStream;

class DownloadStreamTest {
    @Test
    void complete() throws IOException {
        NexusRest rest = NexusRest.builder("eldonexus.de").build();
        AssetXO asset = LiveAssets.jar(rest);
        try (InputStream in = rest.v1().assets().downloadStream(asset.id()).complete()) {
            byte[] bytes = in.readAllBytes();
            Assertions.assertTrue(bytes.length > 0);
            Assertions.assertEquals(asset.checksum().md5(), LiveAssets.md5(bytes));
        }
    }
}
