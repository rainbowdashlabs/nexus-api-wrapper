/*
 *     SPDX-License-Identifier: LGPL-3.0-or-later
 *
 *     Copyright (C) RainbowDashLabs and Contributor
 */

package de.chojo.nexus;

import de.chojo.nexus.entities.AssetXO;
import de.chojo.nexus.entities.PageAssetXO;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.OffsetDateTime;
import java.util.HexFormat;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

class AssetsTest {
    private static final String ID = "bWF2ZW4tcmVsZWFzZXM6YTJlZGRmNQ";

    @Test
    void getMapsTheAsset() throws IOException {
        try (FakeNexus nexus = FakeNexus.start()) {
            AssetXO asset = nexus.client().v1().assets().get(ID).complete();
            assertEquals(ID, asset.id());
            assertEquals("maven-releases", asset.repository());
            assertEquals("maven2", asset.format());
            assertEquals(45836L, asset.fileSize());
            assertEquals("1.0.5", asset.maven2().version());
            assertEquals("jar", asset.maven2().extension());
            assertNull(asset.maven2().classifier());
            assertEquals(
                    OffsetDateTime.parse("2023-03-12T15:54:13.984Z").toInstant(),
                    asset.lastModified().toInstant());
        }
    }

    @Test
    void fileSizeAboveIntRange() throws IOException {
        try (FakeNexus nexus = FakeNexus.start()) {
            PageAssetXO page =
                    nexus.client().v1().assets().list("maven-releases").complete();
            assertEquals(3_000_000_000L, page.items().get(1).fileSize());
        }
    }

    @Test
    void listPages() throws IOException {
        try (FakeNexus nexus = FakeNexus.start()) {
            NexusRest rest = nexus.client();
            PageAssetXO first = rest.v1().assets().list("maven-releases").complete();
            assertEquals("2", first.continuationToken());
            PageAssetXO second = rest.v1()
                    .assets()
                    .list("maven-releases")
                    .continuationToken(first.continuationToken())
                    .complete();
            assertNull(second.continuationToken());
            assertEquals(
                    "repository=maven-releases&continuationToken=2",
                    nexus.last("/v1/assets").query());
        }
    }

    @Test
    void download() throws IOException, NoSuchAlgorithmException {
        try (FakeNexus nexus = FakeNexus.start()) {
            AssetXO asset = nexus.client().v1().assets().get(ID).complete();
            byte[] bytes = asset.download().complete();
            assertArrayEquals(FakeNexus.JAR, bytes);
            assertEquals(
                    asset.checksum().md5(),
                    HexFormat.of().formatHex(MessageDigest.getInstance("MD5").digest(bytes)));
        }
    }

    @Test
    void downloadStream() throws IOException {
        try (FakeNexus nexus = FakeNexus.start();
                InputStream in = nexus.client().v1().assets().downloadStream(ID).complete()) {
            assertArrayEquals(FakeNexus.JAR, in.readAllBytes());
        }
    }

    @Test
    void downloadToFile(@TempDir Path dir) throws IOException {
        try (FakeNexus nexus = FakeNexus.start()) {
            Path target = dir.resolve("download.jar");
            Path file =
                    nexus.client().v1().assets().downloadToFile(ID).path(target).complete();
            assertEquals(target, file);
            assertArrayEquals(FakeNexus.JAR, Files.readAllBytes(file));
        }
    }

    @Test
    void downloadToTemporaryFile() throws IOException {
        try (FakeNexus nexus = FakeNexus.start()) {
            Path file = nexus.client().v1().assets().downloadToFile(ID).complete();
            try {
                assertArrayEquals(FakeNexus.JAR, Files.readAllBytes(file));
            } finally {
                Files.deleteIfExists(file);
            }
        }
    }

    @Test
    void failedStreamDownload() throws IOException {
        try (FakeNexus nexus = FakeNexus.start()) {
            NexusException e = assertThrows(
                    NexusException.class,
                    () -> nexus.client().v1().assets().downloadStream("broken").complete());
            assertEquals(500, e.statusCode());
            assertEquals("storage unavailable", e.body());
        }
    }

    @Test
    void failedFileDownloadDeletesTheFile(@TempDir Path dir) throws IOException {
        try (FakeNexus nexus = FakeNexus.start()) {
            Path target = dir.resolve("download.jar");
            NexusException e = assertThrows(
                    NexusException.class,
                    () -> nexus.client()
                            .v1()
                            .assets()
                            .downloadToFile("broken")
                            .path(target)
                            .complete());
            assertEquals("storage unavailable", e.body());
            assertFalse(Files.exists(target));
        }
    }

    @Test
    void delete() throws IOException {
        try (FakeNexus nexus = FakeNexus.start()) {
            nexus.client().v1().assets().delete(ID).complete();
            assertEquals("DELETE", nexus.last("/v1/assets/" + ID).method());
        }
    }
}
