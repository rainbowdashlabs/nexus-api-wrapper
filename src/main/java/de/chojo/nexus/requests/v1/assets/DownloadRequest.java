/*
 *     SPDX-License-Identifier: AGPL-3.0-only
 *
 *     Copyright (C) RainbowDashLabs and Contributor
 */

package de.chojo.nexus.requests.v1.assets;

import de.chojo.nexus.NexusRestImpl;
import de.chojo.nexus.entities.AssetXO;
import de.chojo.nexus.requests.RequestBuilder;

import java.net.URI;
import java.util.concurrent.CompletableFuture;

public class DownloadRequest extends RequestBuilder<byte[]> {
    private final String id;

    public DownloadRequest(NexusRestImpl rest, String id) {
        super(rest);
        this.id = id;
    }

    @Override
    public CompletableFuture<byte[]> queue() {
        return rest.v1().assets().get(id)
                .queue()
                .thenApply(asset -> rest.getBytes(URI.create(asset.downloadUrl())));
    }

    @Override
    public byte[] complete() {
        AssetXO complete = rest.v1().assets().get(id).complete();
        return rest.getBytes(URI.create(complete.downloadUrl()));
    }
}
