/*
 *     SPDX-License-Identifier: AGPL-3.0-only
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
