/*
 *     SPDX-License-Identifier: LGPL-3.0-or-later
 *
 *     Copyright (C) RainbowDashLabs and Contributor
 */

package de.chojo.nexus.entities;

import java.util.Collection;

public interface CollectionProvider<T> {
    default Collection<T> collection() {
        return null;
    }
}
