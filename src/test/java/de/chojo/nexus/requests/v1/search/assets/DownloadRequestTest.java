package de.chojo.nexus.requests.v1.search.assets;

import de.chojo.nexus.NexusRest;
import de.chojo.nexus.requests.v1.search.Direction;
import de.chojo.nexus.requests.v1.search.Sort;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class DownloadRequestTest {

    NexusRest rest;
    DownloadRequest request;

    @BeforeEach
    void setUp() {
        rest = NexusRest.builder("eldonexus.de").build();
        // Download the first asset we can find
        request = rest.v1().search().assets().download().repository("maven-releases").sort(Sort.NAME).direction(Direction.ASC).mavenExtension("jar");
    }

    @Test
    void queue() {
        byte[] complete = request.queue().join();
        Assertions.assertTrue(complete.length > 0);
    }

    @Test
    void complete() {
        byte[] complete = request.complete();
        Assertions.assertTrue(complete.length > 0);
    }
}
