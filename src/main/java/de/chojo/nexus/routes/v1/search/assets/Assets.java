/*
 *     SPDX-License-Identifier: AGPL-3.0-only
 *
 *     Copyright (C) RainbowDashLabs and Contributor
 */

package de.chojo.nexus.routes.v1.search.assets;

import de.chojo.nexus.NexusRestImpl;
import de.chojo.nexus.requests.v1.search.assets.DownloadRequest;
import de.chojo.nexus.requests.v1.search.assets.SearchRequest;

public class Assets {
    private final NexusRestImpl rest;

    public Assets(NexusRestImpl rest) {
        this.rest = rest;
    }

    public SearchRequest search() {
        return new SearchRequest(rest);
    }

    public DownloadRequest download() {
        return new DownloadRequest(rest);
    }
}
