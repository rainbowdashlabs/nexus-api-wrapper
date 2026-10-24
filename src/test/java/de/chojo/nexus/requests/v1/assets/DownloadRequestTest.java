/*
 *     SPDX-License-Identifier: AGPL-3.0-only
 *
 *     Copyright (C) RainbowDashLabs and Contributor
 */

package de.chojo.nexus.requests.v1.assets;

import de.chojo.nexus.NexusRest;
import de.chojo.nexus.entities.AssetXO;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class DownloadRequestTest {
    @Test
    void complete() {
        NexusRest rest = NexusRest.builder("eldonexus.de").build();
        AssetXO asset = LiveAssets.jar(rest);
        byte[] bytes = rest.v1().assets().download(asset.id()).complete();
        Assertions.assertTrue(bytes.length > 0);
        Assertions.assertEquals(asset.checksum().md5(), LiveAssets.md5(bytes));
    }
}
