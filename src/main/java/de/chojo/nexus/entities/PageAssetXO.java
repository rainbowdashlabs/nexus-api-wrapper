package de.chojo.nexus.entities;

import java.util.List;

public record PageAssetXO(List<AssetXO> items, String continuationToken) {
}
