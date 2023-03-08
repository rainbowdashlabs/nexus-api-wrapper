/*
 *     SPDX-License-Identifier: AGPL-3.0-only
 *
 *     Copyright (C) RainbowDashLabs and Contributor
 */

package de.chojo.nexus;

import com.fasterxml.jackson.databind.ObjectMapper;
import de.chojo.nexus.requests.Buckets;
import de.chojo.nexus.requests.Mapper;
import de.chojo.nexus.routes.v1.V1;
import io.github.bucket4j.Bucket;
import org.apache.hc.core5.net.URIBuilder;
import org.jetbrains.annotations.Nullable;
import org.slf4j.Logger;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ScheduledExecutorService;

import static org.slf4j.LoggerFactory.getLogger;

/**
 * Implementation of a rest client
 */
public class NexusRestImpl implements NexusRest {
    private static final Logger log = getLogger(NexusRestImpl.class);
    private final Bucket bucket = Buckets.newDefaultBucket();
    private final HttpClient http;
    private final ObjectMapper objectMapper;
    private final String host;
    private final Map<String, String> header;
    private final ScheduledExecutorService executorService;
    private final V1 v1;

    /**
     * Create a new nexus rest client
     *
     * @param http            http client
     * @param executorService executor service
     * @param header
     */
    public NexusRestImpl(HttpClient http, ScheduledExecutorService executorService, String host, Map<String, String> header) {
        this.http = http;
        this.executorService = executorService;
        this.host = host;
        this.header = header;
        this.objectMapper = Mapper.create(this);
        this.v1 = new V1(this);
    }

    /**
     * Get the http client
     *
     * @return http client
     */
    public HttpClient http() {
        return http;
    }

    /**
     * Get a pre configured uri builder
     *
     * @return new uri builder
     */
    public URIBuilder uri() {
        return new URIBuilder().setScheme("https").setHost(host).appendPathSegments("service", "rest");
    }

    /**
     * Get the object mapper
     *
     * @return object mapper
     */
    public ObjectMapper objectMapper() {
        return objectMapper;
    }

    /**
     * Send an asynchronous request to the uri and map the result body
     *
     * @param uri    uri
     * @param result result class
     * @param <T>    result type
     * @return future with result
     */
    public <T> CompletableFuture<T> getAsyncAndMap(URIBuilder uri, Class<T> result) {
        try {
            return getAsyncAndMap(uri.build(), result);
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Send a request and map the result body
     *
     * @param uri    uri
     * @param result result class
     * @param <T>    result type
     * @return result
     */
    public <T> T getAndMap(URIBuilder uri, Class<T> result) {
        try {
            return getAndMap(uri.build(), result);
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }

    private HttpRequest.Builder request(URI uri) {
        HttpRequest.Builder builder = HttpRequest.newBuilder(uri);
        for (var entry : header.entrySet()) {
            builder.header(entry.getKey(), entry.getValue());
        }
        return builder;
    }

    /**
     * Send an asynchronous request to the uri and map the result body
     *
     * @param uri    uri
     * @param result result class
     * @param <T>    result type
     * @return future with result
     */
    public <T> CompletableFuture<T> getAsyncAndMap(URI uri, Class<T> result) {
        HttpRequest request = request(uri).GET().build();
        return getAsyncAndMap(request, result);
    }

    /**
     * Send a request and map the result body
     *
     * @param uri    uri
     * @param result result class
     * @param <T>    result type
     * @return result
     */
    public <T> T getAndMap(URI uri, Class<T> result) {
        HttpRequest request = request(uri).GET().build();
        return getAndMap(request, result);
    }

    /**
     * Send an asynchronous request and map the result body
     *
     * @param request request
     * @param result  result class
     * @param <T>     result type
     * @return future with result
     */
    public <T> CompletableFuture<T> getAsyncAndMap(HttpRequest request, Class<T> result) {
        return dispatchAsync(() -> getAndMapInternal(request, result));
    }

    /**
     * Send a request and map the result body
     *
     * @param request request
     * @param result  result class
     * @param <T>     result type
     * @return result
     */
    public <T> T getAndMap(HttpRequest request, Class<T> result) {
        return dispatch(() -> getAndMapInternal(request, result));
    }

    public <T> T dispatch(Callable<T> callable) {
        try {
            bucket.asBlocking().consume(1);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        try {
            return callable.call();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    public <T> CompletableFuture<T> dispatchAsync(Callable<T> callable) {
        return bucket.asScheduler().consume(1, executorService)
                .thenApplyAsync(v -> {
                    try {
                        return callable.call();
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                }, executorService);
    }

    private <T> T getAndMapInternal(HttpRequest request, Class<T> result) {
        try {
            log.trace("Requesting {}", request.uri());
            HttpResponse<String> response = http().send(request, HttpResponse.BodyHandlers.ofString());
            log.trace("Received\n{}", response.body());
            handleStatusCode(response);
            return objectMapper().readValue(response.body(), result);
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public byte[] getBytes(URI uri) {
        return dispatch(() -> getBytesInternal(request(uri).GET().build()));
    }

    public CompletableFuture<byte[]> getAsyncBytes(URI uri) {
        return dispatchAsync(() -> getBytesInternal(request(uri).GET().build()));
    }

    private byte[] getBytesInternal(HttpRequest request) {
        try {
            log.trace("Requesting {}", request.uri());
            HttpResponse<byte[]> response = http().send(request, HttpResponse.BodyHandlers.ofByteArray());
            log.trace("Received\n{}", response.body());
            handleStatusCode(response);
            return response.body();
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public Path getToFile(URI uri, @Nullable Path path) {
        return dispatch(() -> getToFileInternal(request(uri).GET().build(), path));
    }

    public CompletableFuture<Path> getAsyncToFile(URI uri, @Nullable Path path) {
        return dispatchAsync(() -> getToFileInternal(request(uri).GET().build(), path));
    }

    private Path getToFileInternal(HttpRequest request, @Nullable Path path) {
        try {
            path = path == null ? Files.createTempFile("nexus", "file") : path;
            log.trace("Requesting {}", request.uri());
            HttpResponse<Path> response = http().send(request, HttpResponse.BodyHandlers.ofFile(path));
            log.trace("Received\n{}", response.body());
            handleStatusCode(response);
            return response.body();
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    private <T> void handleStatusCode(HttpResponse<T> response) {
        if (response.statusCode() < 200 || response.statusCode() >= 300) {
            T body = response.body();
            if (body instanceof String b) throw new RuntimeException(b);
            if (body instanceof byte[] b) throw new RuntimeException(new String(b));
            throw new RuntimeException(String.valueOf(body));
        }
    }

    @Override
    public V1 v1() {
        return v1;
    }

    public <T> CompletableFuture<T> deleteAsync(URI uri) {
        return dispatchAsync(() -> delete(uri));
    }

    public <T> T delete(URI uri) {
        try {
            HttpRequest request = request(uri).DELETE().build();
            log.trace("Requesting {}", request.uri());
            HttpResponse<String> response = http().send(request, HttpResponse.BodyHandlers.ofString());
            log.trace("Received\n{}", response.body());
            handleStatusCode(response);
            return null;
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
