/*
 *     SPDX-License-Identifier: LGPL-3.0-or-later
 *
 *     Copyright (C) RainbowDashLabs and Contributor
 */

package de.chojo.nexus.requests.v1.repositories;

import de.chojo.nexus.NexusRestImpl;
import de.chojo.nexus.entities.RepositoryXO;
import de.chojo.nexus.requests.RequestBuilder;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public class ListRequest extends RequestBuilder<List<RepositoryXO>> {
    public ListRequest(NexusRestImpl rest) {
        super(rest);
        path("v1", "repositories");
    }

    @Override
    public CompletableFuture<List<RepositoryXO>> queue() {
        return rest.getAsyncAndMapList(uri(), RepositoryXO.class);
    }

    @Override
    public List<RepositoryXO> complete() {
        return rest.getAndMapList(uri(), RepositoryXO.class);
    }
}
