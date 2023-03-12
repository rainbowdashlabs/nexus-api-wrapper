/*
 *     SPDX-License-Identifier: AGPL-3.0-only
 *
 *     Copyright (C) RainbowDashLabs and Contributor
 */

package de.chojo.nexus.requests.v1.search;

import de.chojo.nexus.NexusRestImpl;
import de.chojo.nexus.entities.PageComponentXO;

import java.util.concurrent.CompletableFuture;

public class SearchRequest extends ASearchRequest<SearchRequest, PageComponentXO> {
    public SearchRequest(NexusRestImpl rest) {
        super(rest);
        path("v1", "search");
    }

    @Override
    public CompletableFuture<PageComponentXO> queue() {
        return rest.getAsyncAndMap(uri(), PageComponentXO.class);
    }

    @Override
    public PageComponentXO complete() {
        return rest.getAndMap(uri(), PageComponentXO.class);
    }
}
