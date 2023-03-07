/*
 *     SPDX-License-Identifier: AGPL-3.0-only
 *
 *     Copyright (C) RainbowDashLabs and Contributor
 */

package de.chojo.nexus.requests;

import io.github.bucket4j.Bandwidth;
import io.github.bucket4j.Bucket;

import java.time.Duration;

/**
 * Provides buckets for rate limiting
 */
public final class Buckets {
    private Buckets() {
        throw new UnsupportedOperationException("This is a utility class.");
    }

    /**
     * Preconfigured bucket
     *
     * @return bucket
     */
    public static Bucket newDefaultBucket() {
        return Bucket.builder().addLimit(Bandwidth.simple(50, Duration.ofSeconds(2))).build();
    }
}
