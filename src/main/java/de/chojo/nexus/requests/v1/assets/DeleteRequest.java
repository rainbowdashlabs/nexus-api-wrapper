/*
 *     SPDX-License-Identifier: LGPL-3.0-or-later
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
        super(rest);
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
