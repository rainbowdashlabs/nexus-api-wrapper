/*
 *     SPDX-License-Identifier: AGPL-3.0-only
 *
 *     Copyright (C) 2022 RainbowDashLabs and Contributor
 */

package de.chojo.nexus.requests;

import java.util.concurrent.CompletableFuture;

/**
 * A request allowing to retrieve a response
 *
 * @param <T> type of response
 */
public interface Request<T> {
    /**
     * Queue the request asynchronous.
     *
     * @return completable future providing the response
     */
    CompletableFuture<T> queue();

    /**
     * Sends the request and handles the response on the current thread
     *
     * @return response
     */
    T complete();
}
