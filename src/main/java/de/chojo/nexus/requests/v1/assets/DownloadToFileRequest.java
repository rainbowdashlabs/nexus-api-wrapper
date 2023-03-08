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
import java.nio.file.Path;
import java.util.concurrent.CompletableFuture;

public class DownloadToFileRequest extends RequestBuilder<Path> {

    private final String id;
    private Path path;

    public DownloadToFileRequest(NexusRestImpl rest, String id) {
        super(rest, Path.class);
        this.id = id;
    }

    public DownloadToFileRequest path(Path path) {
        this.path = path;
        return this;
    }

    @Override
    public CompletableFuture<Path> queue() {
        return rest.v1().assets().get(id)
                .queue()
                .thenApply(asset -> rest.getToFile(URI.create(asset.downloadUrl()), path));
    }

    @Override
    public Path complete() {
        AssetXO complete = rest.v1().assets().get(id).complete();
        return rest.getToFile(URI.create(complete.downloadUrl()), path);
    }
}
