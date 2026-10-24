/*
 *     SPDX-License-Identifier: LGPL-3.0-or-later
 *
 *     Copyright (C) RainbowDashLabs and Contributor
 */

package de.chojo.nexus.entities;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Collection;
import java.util.List;

public record PageComponentXO(
        @JsonProperty List<ComponentXO> items, @JsonProperty String continuationToken)
        implements CollectionAdapter<ComponentXO> {

    public Collection<ComponentXO> collection() {
        return items;
    }
}
