/*
 *     SPDX-License-Identifier: AGPL-3.0-only
 *
 *     Copyright (C) RainbowDashLabs and Contributor
 */

package de.chojo.nexus.requests.v1.assets;

import de.chojo.nexus.NexusRestImpl;
import de.chojo.nexus.requests.RequestBuilder;

import java.util.concurrent.CompletableFuture;

public class DeleteRequest extends RequestBuilder<Void> {
    private final String id;

    public DeleteRequest(NexusRestImpl rest, String id) {
        super(rest, Void.class);
        this.id = id;
        path("v1", "assets", id);
    }

    @Override
    public CompletableFuture<Void> queue() {
        return rest.deleteAsync(uri());
    }

    @Override
    public Void complete() {
        return rest.delete(uri());
    }
}
