/*
 *     SPDX-License-Identifier: LGPL-3.0-or-later
 *
 *     Copyright (C) RainbowDashLabs and Contributor
 */

package de.chojo.nexus.requests.v1.assets;

import de.chojo.nexus.NexusRest;
import de.chojo.nexus.entities.AssetXO;
import de.chojo.nexus.requests.v1.search.Direction;
import de.chojo.nexus.requests.v1.search.Sort;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HexFormat;

/**
 * Finds a real asset on eldonexus.de, so the tests do not depend on asset ids, which change with the Nexus datastore.
 */
final class LiveAssets {
    private LiveAssets() {
        throw new UnsupportedOperationException("This is a utility class.");
    }

    /**
     * The jar of the oldest released version of this library.
     *
     * @param rest client
     * @return asset
     */
    static AssetXO jar(NexusRest rest) {
        return rest.v1()
                .search()
                .assets()
                .search()
                .repository("maven-releases")
                .mavenGroupId("de.chojo")
                .mavenArtifactId("nexus-api-wrapper")
                .mavenExtension("jar")
                .sort(Sort.VERSION)
                .direction(Direction.ASC)
                .complete()
                .items()
                .getFirst();
    }

    /**
     * The md5 hash of the bytes as lower case hex.
     *
     * @param bytes bytes
     * @return hash
     */
    static String md5(byte[] bytes) {
        try {
            return HexFormat.of().formatHex(MessageDigest.getInstance("MD5").digest(bytes));
        } catch (NoSuchAlgorithmException e) {
            throw new IllegalStateException(e);
        }
    }
}
