/*
 *     SPDX-License-Identifier: AGPL-3.0-only
 *
 *     Copyright (C) RainbowDashLabs and Contributor
 */

package de.chojo.nexus.requests.v1.search.assets;

import de.chojo.nexus.NexusRestImpl;
import de.chojo.nexus.entities.PageAssetXO;
import de.chojo.nexus.requests.v1.search.ASearchRequest;

import java.util.concurrent.CompletableFuture;

public class SearchRequest extends ASearchRequest<SearchRequest, PageAssetXO> {
    public SearchRequest(NexusRestImpl rest) {
        super(rest);
        path("v1","search","assets");
    }

    @Override
    public CompletableFuture<PageAssetXO> queue() {
        return rest.getAsyncAndMap(uri(), PageAssetXO.class);
    }

    @Override
    public PageAssetXO complete() {
        return rest.getAndMap(uri(), PageAssetXO.class);
    }
}
