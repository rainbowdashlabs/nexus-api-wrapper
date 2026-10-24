/*
 *     SPDX-License-Identifier: LGPL-3.0-or-later
 *
 *     Copyright (C) RainbowDashLabs and Contributor
 */

package de.chojo.nexus;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.concurrent.CompletionException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ClientTest {
    @Test
    void baseUrlWithContextPathAndTrailingSlash() throws IOException {
        try (FakeNexus nexus = FakeNexus.start("/nexus")) {
            NexusRest rest =
                    NexusRest.builder(URI.create(nexus.baseUrl() + "/")).build();
            assertEquals(3, rest.v1().repositories().list().complete().size());
            assertEquals(
                    "/nexus/service/rest/v1/repositories",
                    nexus.requests().getLast().path());
        }
    }

    @Test
    void passwordAuthSendsBasicHeader() throws IOException {
        try (FakeNexus nexus = FakeNexus.start()) {
            NexusRest rest = NexusRest.builder(nexus.baseUrl())
                    .setPasswordAuth("deployer", "pässword")
                    .build();
            rest.v1().repositories().list().complete();
            String expected =
                    "Basic " + Base64.getEncoder().encodeToString("deployer:pässword".getBytes(StandardCharsets.UTF_8));
            assertEquals(expected, nexus.last("/v1/repositories").authorization());
        }
    }

    @Test
    void defaultExecutorUsesDaemonThreads() throws IOException {
        try (FakeNexus nexus = FakeNexus.start()) {
            NexusRestImpl rest = (NexusRestImpl) nexus.client();
            assertTrue(
                    rest.dispatchAsync(() -> Thread.currentThread().isDaemon()).join());
        }
    }

    @Test
    void completeThrowsNexusException() throws IOException {
        try (FakeNexus nexus = FakeNexus.start()) {
            NexusException e = assertThrows(
                    NexusException.class,
                    () -> nexus.client().v1().assets().get("missing").complete());
            assertEquals(422, e.statusCode());
            assertTrue(e.body().contains("\"status-code\" : 422"));
            assertTrue(e.uri().getPath().endsWith("/v1/assets/missing"));
        }
    }

    @Test
    void queueFailsWithNexusException() throws IOException {
        try (FakeNexus nexus = FakeNexus.start()) {
            CompletionException e = assertThrows(
                    CompletionException.class,
                    () -> nexus.client().v1().assets().get("missing").queue().join());
            assertEquals(
                    422, assertInstanceOf(NexusException.class, e.getCause()).statusCode());
        }
    }
}
