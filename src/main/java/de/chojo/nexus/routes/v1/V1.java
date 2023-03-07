/*
 *     SPDX-License-Identifier: AGPL-3.0-only
 *
 *     Copyright (C) RainbowDashLabs and Contributor
 */

package de.chojo.nexus.routes.v1;

import de.chojo.nexus.NexusRestImpl;
import de.chojo.nexus.routes.v1.search.Search;

public class V1 {
    private final Search search;

    public V1(NexusRestImpl rest) {
        search = new Search(rest);
    }

    public Search search() {
        return search;
    }
}
