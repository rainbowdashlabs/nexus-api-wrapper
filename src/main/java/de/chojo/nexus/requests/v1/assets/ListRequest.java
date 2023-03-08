/*
 *     SPDX-License-Identifier: AGPL-3.0-only
 *
 *     Copyright (C) RainbowDashLabs and Contributor
 */

package de.chojo.nexus.requests.v1.assets;

import de.chojo.nexus.NexusRestImpl;
import de.chojo.nexus.entities.PageAssetXO;
import de.chojo.nexus.requests.RequestBuilder;

import java.util.concurrent.CompletableFuture;

public class ListRequest extends RequestBuilder<PageAssetXO> {
    public ListRequest(NexusRestImpl rest, String repository) {
        super(rest, PageAssetXO.class);
        path("v1", "assets");
        parameter("repository", repository);
    }

    public ListRequest continuationToken(String token) {
        parameter("continuationToken", token);
        return this;
    }

    @Override
    public CompletableFuture<PageAssetXO> queue() {
        return queueGetAndMap();
    }

    @Override
    public PageAssetXO complete() {
        return completeGetAndMap();
    }
}
