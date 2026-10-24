/*
 *     SPDX-License-Identifier: LGPL-3.0-or-later
 *
 *     Copyright (C) RainbowDashLabs and Contributor
 */

package de.chojo.nexus.entities;

import java.util.Collection;
import java.util.List;

public record PageAssetXO(List<AssetXO> items, String continuationToken) implements CollectionProvider<AssetXO> {
    @Override
    public Collection<AssetXO> collection() {
        return items;
    }
}
