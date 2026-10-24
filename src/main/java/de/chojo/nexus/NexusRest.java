/*
 *     SPDX-License-Identifier: LGPL-3.0-or-later
 *
 *     Copyright (C) RainbowDashLabs and Contributor
 */

package de.chojo.nexus;

import de.chojo.nexus.routes.v1.V1;
import org.jetbrains.annotations.CheckReturnValue;

import java.net.URI;

/**
 * Class to access the nexus api.
 */
public interface NexusRest {
    /**
     * Get an api with default settings for a Nexus reachable at {@code https://<host>}.
     *
     * @param host host name, optionally with a port
     * @return api instance
     */
    @CheckReturnValue
    static NexusRest defaultApi(String host) {
        return new NexusRestBuilder(host).build();
    }

    /**
     * Get a builder to configure the api for a Nexus reachable at {@code https://<host>}.
     *
     * @param host host name, optionally with a port
     * @return builder
     */
    @CheckReturnValue
    static NexusRestBuilder builder(String host) {
        return new NexusRestBuilder(host);
    }

    /**
     * Get a builder for a Nexus at the given base url, like {@code http://localhost:8081} or
     * {@code https://example.com/nexus}. The REST API is expected under {@code <baseUrl>/service/rest}.
     *
     * @param baseUrl base url of the Nexus
     * @return builder
     */
    @CheckReturnValue
    static NexusRestBuilder builder(URI baseUrl) {
        return new NexusRestBuilder(baseUrl);
    }

    /**
     * The routes of version 1 of the REST API.
     *
     * @return v1 routes
     */
    V1 v1();
}
