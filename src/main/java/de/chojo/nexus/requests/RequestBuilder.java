/*
 *     SPDX-License-Identifier: AGPL-3.0-only
 *
 *     Copyright (C) RainbowDashLabs and Contributor
 */

package de.chojo.nexus.requests;

import de.chojo.nexus.NexusRestImpl;
import org.apache.hc.core5.net.URIBuilder;
import org.slf4j.Logger;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;

import static org.slf4j.LoggerFactory.getLogger;

/**
 * A request builder used to build, execute requests and parse the response
 *
 * @param <T> type of response
 */
public abstract class RequestBuilder<T> implements Request<T> {
    private static final Logger log = getLogger(RequestBuilder.class);
    protected final NexusRestImpl rest;
    private final URIBuilder uriBuilder;

    /**
     * Create a new request builder
     *
     * @param rest   rest client
     */
    public RequestBuilder(NexusRestImpl rest) {
        this.rest = rest;
        uriBuilder = rest.uri();
    }


    private URIBuilder uriBuilder() {
        return uriBuilder;
    }

    /**
     * Adds a parameter to the uri
     *
     * @param key    parameter key
     * @param object parameter value
     */
    public void parameter(String key, Object object) {
        uriBuilder.addParameter(key, String.valueOf(object));
    }

    /**
     * Adds an element to the path.
     *
     * @param path path
     */
    public void path(String... path) {
        uriBuilder.appendPathSegments(path);
    }

    /**
     * Adds an element to the path.
     *
     * @param path path
     */
    public void path(Object... path) {
        for (Object o : path) {
            uriBuilder.appendPath(String.valueOf(o));
        }
    }

    protected URI uri() {
        try {
            return uriBuilder.build();
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public abstract CompletableFuture<T> queue();

    @Override
    public abstract T complete();
}
