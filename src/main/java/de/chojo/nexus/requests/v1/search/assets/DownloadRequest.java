/*
 *     SPDX-License-Identifier: LGPL-3.0-or-later
 *
 *     Copyright (C) RainbowDashLabs and Contributor
 */

package de.chojo.nexus.requests.v1.search.assets;

import de.chojo.nexus.NexusRestImpl;
import de.chojo.nexus.requests.v1.search.ASearchRequest;

import java.util.concurrent.CompletableFuture;

public class DownloadRequest extends ASearchRequest<DownloadRequest, byte[]> {
    public DownloadRequest(NexusRestImpl rest) {
        super(rest);
        path("v1", "search", "assets", "download");
    }

    @Override
    public CompletableFuture<byte[]> queue() {
        return rest.getAsyncBytes(uri());
    }

    @Override
    public byte[] complete() {
        return rest.getBytes(uri());
    }
}
