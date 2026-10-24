/*
 *     SPDX-License-Identifier: LGPL-3.0-or-later
 *
 *     Copyright (C) RainbowDashLabs and Contributor
 */

package de.chojo.nexus.requests.v1.assets;

import de.chojo.nexus.NexusRestImpl;
import de.chojo.nexus.entities.AssetXO;
import de.chojo.nexus.requests.RequestBuilder;

import java.io.InputStream;
import java.net.URI;
import java.util.concurrent.CompletableFuture;

public class DownloadStreamRequest extends RequestBuilder<InputStream> {
    private final String id;

    public DownloadStreamRequest(NexusRestImpl rest, String id) {
        super(rest);
        this.id = id;
    }

    @Override
    public CompletableFuture<InputStream> queue() {
        return rest.v1().assets().get(id).queue().thenApply(asset -> rest.getStream(URI.create(asset.downloadUrl())));
    }

    @Override
    public InputStream complete() {
        AssetXO complete = rest.v1().assets().get(id).complete();
        return rest.getStream(URI.create(complete.downloadUrl()));
    }
}
