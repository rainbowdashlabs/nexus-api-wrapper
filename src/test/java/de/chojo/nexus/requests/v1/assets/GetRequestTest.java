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

@Tag("live")
class GetRequestTest {
    @Test
    void complete() {
        NexusRest rest = NexusRest.builder("eldonexus.de").build();
        AssetXO asset = LiveAssets.jar(rest);
        Assertions.assertEquals(
                asset.id(), rest.v1().assets().get(asset.id()).complete().id());
    }
}
