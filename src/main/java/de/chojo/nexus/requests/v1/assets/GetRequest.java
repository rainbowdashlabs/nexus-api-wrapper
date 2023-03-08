/*
 *     SPDX-License-Identifier: AGPL-3.0-only
 *
 *     Copyright (C) RainbowDashLabs and Contributor
 */

package de.chojo.nexus.requests.v1.assets;

import de.chojo.nexus.NexusRestImpl;
import de.chojo.nexus.entities.AssetXO;
import de.chojo.nexus.requests.RequestBuilder;

import java.util.concurrent.CompletableFuture;

public class GetRequest extends RequestBuilder<AssetXO> {
    public GetRequest(NexusRestImpl rest, String id) {
        super(rest, AssetXO.class);
        path("v1", "assets", id);
    }

    @Override
    public CompletableFuture<AssetXO> queue() {
        return queueGetAndMap();
    }

    @Override
    public AssetXO complete() {
        return completeGetAndMap();
    }
}
