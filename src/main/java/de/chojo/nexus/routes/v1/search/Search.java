/*
 *     SPDX-License-Identifier: AGPL-3.0-only
 *
 *     Copyright (C) RainbowDashLabs and Contributor
 */

package de.chojo.nexus.routes.v1.search;

import de.chojo.nexus.NexusRestImpl;
import de.chojo.nexus.requests.v1.search.SearchRequest;
import de.chojo.nexus.routes.v1.search.assets.Assets;

public class Search {
    Assets assets ;
    private final NexusRestImpl rest;

    public Search(NexusRestImpl rest) {
        this.rest = rest;
        assets= new Assets(rest);
    }

    public SearchRequest search() {
        return new SearchRequest(rest);
    }

    public Assets assets() {
        return assets;
    }
}
