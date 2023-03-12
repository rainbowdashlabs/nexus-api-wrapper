/*
 *     SPDX-License-Identifier: AGPL-3.0-only
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
