/*
 *     SPDX-License-Identifier: AGPL-3.0-only
 *
 *     Copyright (C) RainbowDashLabs and Contributor
 */

package de.chojo.nexus.routes.v1.assets;

import de.chojo.nexus.NexusRestImpl;
import de.chojo.nexus.requests.v1.assets.DeleteRequest;
import de.chojo.nexus.requests.v1.assets.DownloadRequest;
import de.chojo.nexus.requests.v1.assets.DownloadToFileRequest;
import de.chojo.nexus.requests.v1.assets.GetRequest;
import de.chojo.nexus.requests.v1.assets.ListRequest;

public class Assets {
    NexusRestImpl rest;

    public Assets(NexusRestImpl rest) {
        this.rest = rest;
    }

    public GetRequest get(String id) {
        return new GetRequest(rest, id);
    }

    public DownloadRequest download(String id) {
        return new DownloadRequest(rest, id);
    }

    public DownloadToFileRequest downloadToFile(String id) {
        return new DownloadToFileRequest(rest, id);
    }

    public DeleteRequest delete(String id) {
        return new DeleteRequest(rest, id);
    }

    public ListRequest list(String repository) {
        return new ListRequest(rest, repository);
    }
}
