/*
 *     SPDX-License-Identifier: LGPL-3.0-or-later
 *
 *     Copyright (C) RainbowDashLabs and Contributor
 */

package de.chojo.nexus.routes.v1.repositories;

import de.chojo.nexus.NexusRestImpl;
import de.chojo.nexus.requests.v1.repositories.ListRequest;

public class Repositories {
    private final NexusRestImpl rest;

    public Repositories(NexusRestImpl rest) {
        this.rest = rest;
    }

    public ListRequest list() {
        return new ListRequest(rest);
    }
}
