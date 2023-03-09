/*
 *     SPDX-License-Identifier: AGPL-3.0-only
 *
 *     Copyright (C) RainbowDashLabs and Contributor
 */

package de.chojo.nexus.requests.v1.assets;

import de.chojo.nexus.NexusRestImpl;
import de.chojo.nexus.entities.AssetXO;
import de.chojo.nexus.requests.RequestBuilder;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.URI;
import java.util.concurrent.CompletableFuture;

public class DownloadStreamRequest extends RequestBuilder<InputStream> {
    private final String id;

    public DownloadStreamRequest(NexusRestImpl rest, String id) {
        super(rest, InputStream.class);
        this.id = id;
    }

    @Override
    public CompletableFuture<InputStream> queue() {
        return rest.v1().assets().get(id)
                .queue()
                .thenApply(asset -> rest.getStream(URI.create(asset.downloadUrl())));
    }

    @Override
    public InputStream complete() {
        AssetXO complete = rest.v1().assets().get(id).complete();
        return rest.getStream(URI.create(complete.downloadUrl()));
    }
}
