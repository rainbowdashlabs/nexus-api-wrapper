/*
 *     SPDX-License-Identifier: AGPL-3.0-only
 *
 *     Copyright (C) RainbowDashLabs and Contributor
 */

package de.chojo.nexus.requests.v1.assets;

import com.google.common.hash.Hashing;
import de.chojo.nexus.NexusRest;
import de.chojo.nexus.entities.AssetXO;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.InputStream;
import java.security.NoSuchAlgorithmException;

class DownloadStreamTest {
    NexusRest rest;
    DownloadStreamRequest request;
    String id = "bWF2ZW4tcmVsZWFzZXM6MTNiMjllNDQ5ZjBlM2I4ZGE0ZWFlMWFiNjI0MzhiZTU";

    @BeforeEach
    void setUp() {
        rest = NexusRest.builder("eldonexus.de").build();
        // Download the first asset we can find
        request = rest.v1().assets().downloadStream(id);
    }

    @Test
    void complete() throws IOException {
        AssetXO complete1 = rest.v1().assets().get(id).complete();
        InputStream complete = request.complete();
        byte[] bytes = complete.readAllBytes();
        Assertions.assertTrue(bytes.length > 0);
        String md5 = Hashing.md5().hashBytes(bytes).toString();
        Assertions.assertEquals(complete1.checksum().md5(), md5);
    }
}
