/*
 *     SPDX-License-Identifier: AGPL-3.0-only
 *
 *     Copyright (C) RainbowDashLabs and Contributor
 */

package de.chojo.nexus.entities;

import java.util.Collection;
import java.util.List;

public record PageComponentXO(List<ComponentXO> items,
                              String continuationToken) implements CollectionAdapter<ComponentXO> {

    @Override
    public Collection<ComponentXO> collection() {
        return items;
    }
}
