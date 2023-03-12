/*
 *     SPDX-License-Identifier: AGPL-3.0-only
 *
 *     Copyright (C) RainbowDashLabs and Contributor
 */

package de.chojo.nexus.routes.v1;

import de.chojo.nexus.NexusRestImpl;
import de.chojo.nexus.routes.v1.assets.Assets;
import de.chojo.nexus.routes.v1.repositories.Repositories;
import de.chojo.nexus.routes.v1.search.Search;

public class V1 {
    private final Search search;
    private final Assets assets;
    private final Repositories repositories;

    public V1(NexusRestImpl rest) {
        search = new Search(rest);
        assets = new Assets(rest);
        repositories = new Repositories(rest);
    }

    public Search search() {
        return search;
    }

    public Assets assets() {
        return assets;
    }

    public Repositories repositories() {
        return repositories;
    }
}
