/*
 *     SPDX-License-Identifier: AGPL-3.0-only
 *
 *     Copyright (C) RainbowDashLabs and Contributor
 */

package de.chojo.nexus;

import de.chojo.nexus.routes.v1.V1;

import javax.annotation.CheckReturnValue;

/**
 * Class to access the nexus api.
 */
public interface NexusRest {
    /**
     * Get a api with default settings.
     *
     * @return api instance
     */
    @CheckReturnValue
    static NexusRest defaultApi(String host) {
        return new NexusRestBuilder(host).build();
    }

    /**
     * Get a builder to configure the api.
     *
     * @return builder
     */
    @CheckReturnValue
    static NexusRestBuilder builder(String host) {
        return new NexusRestBuilder(host);
    }

    V1 v1();
}
