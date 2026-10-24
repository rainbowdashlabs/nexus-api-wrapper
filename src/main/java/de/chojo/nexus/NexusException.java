/*
 *     SPDX-License-Identifier: LGPL-3.0-or-later
 *
 *     Copyright (C) RainbowDashLabs and Contributor
 */

package de.chojo.nexus;

import java.net.URI;

/**
 * Thrown when Nexus answers a request with a status outside 200 to 299.
 */
public class NexusException extends RuntimeException {
    private final URI uri;
    private final int statusCode;
    private final String body;

    /**
     * Creates a new exception for a failed request.
     *
     * @param uri        the requested uri
     * @param statusCode the HTTP status code of the answer
     * @param body       the body of the answer, empty when there was none
     */
    public NexusException(URI uri, int statusCode, String body) {
        super("Nexus answered %d for %s: %s".formatted(statusCode, uri, body));
        this.uri = uri;
        this.statusCode = statusCode;
        this.body = body;
    }

    /**
     * The requested uri.
     *
     * @return uri
     */
    public URI uri() {
        return uri;
    }

    /**
     * The HTTP status code of the answer.
     *
     * @return status code
     */
    public int statusCode() {
        return statusCode;
    }

    /**
     * The body of the answer. Nexus usually sends JSON or a short text that explains the error.
     *
     * @return body, empty when there was none
     */
    public String body() {
        return body;
    }
}
