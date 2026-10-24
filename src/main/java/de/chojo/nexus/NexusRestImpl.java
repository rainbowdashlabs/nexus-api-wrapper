/*
 *     SPDX-License-Identifier: LGPL-3.0-or-later
 *
 *     Copyright (C) RainbowDashLabs and Contributor
 */

package de.chojo.nexus;

import de.chojo.nexus.requests.Buckets;
import de.chojo.nexus.requests.Mapper;
import de.chojo.nexus.routes.v1.V1;
import io.github.bucket4j.Bucket;
import org.apache.hc.core5.net.URIBuilder;
import org.jetbrains.annotations.Nullable;
import org.slf4j.Logger;
import tools.jackson.databind.ObjectMapper;
import tools.jackson.databind.type.CollectionType;

import java.io.IOException;
import java.io.InputStream;
import java.io.UncheckedIOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ScheduledExecutorService;
import java.util.function.Supplier;

import static org.slf4j.LoggerFactory.getLogger;

/**
 * Implementation of a rest client.
 *
 * <p>Every request waits for the shared rate limit first. A status outside 200 to 299 ends in a {@link NexusException},
 * an I/O error in an {@link UncheckedIOException}. Asynchronous requests complete their future exceptionally with the
 * same exceptions, wrapped in a {@link java.util.concurrent.CompletionException}.
 */
public class NexusRestImpl implements NexusRest {
    private static final Logger log = getLogger(NexusRestImpl.class);
    private final Bucket bucket = Buckets.newDefaultBucket();
    private final HttpClient http;
    private final ObjectMapper objectMapper;
    private final URI baseUrl;
    private final Map<String, String> header;
    private final ScheduledExecutorService executorService;
    private final V1 v1;

    /**
     * Create a new nexus rest client
     *
     * @param http            http client
     * @param executorService executor service for asynchronous requests
     * @param baseUrl         base url of the nexus, without {@code /service/rest}
     * @param header          headers sent with every request
     */
    public NexusRestImpl(
            HttpClient http, ScheduledExecutorService executorService, URI baseUrl, Map<String, String> header) {
        this.http = http;
        this.executorService = executorService;
        this.baseUrl = withoutTrailingSlash(baseUrl);
        this.header = Map.copyOf(header);
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
        return new URIBuilder(baseUrl).appendPathSegments("service", "rest");
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
        return getAsyncAndMap(build(uri), result);
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
        return getAndMap(build(uri), result);
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
        return dispatchAsync(() -> getAndMapInternal(uri, result));
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
        return dispatch(() -> getAndMapInternal(uri, result));
    }

    /**
     * Send an asynchronous request to the uri and map the result body to a list
     *
     * @param uri    uri
     * @param result element class
     * @param <T>    element type
     * @return future with result
     */
    public <T> CompletableFuture<List<T>> getAsyncAndMapList(URI uri, Class<T> result) {
        return dispatchAsync(() -> getAndMapListInternal(uri, result));
    }

    /**
     * Send a request and map the result body to a list
     *
     * @param uri    uri
     * @param result element class
     * @param <T>    element type
     * @return result
     */
    public <T> List<T> getAndMapList(URI uri, Class<T> result) {
        return dispatch(() -> getAndMapListInternal(uri, result));
    }

    /**
     * Send a request and return the body as bytes
     *
     * @param uri uri
     * @return body
     */
    public byte[] getBytes(URI uri) {
        return dispatch(() -> getBytesInternal(uri));
    }

    /**
     * Send an asynchronous request and return the body as bytes
     *
     * @param uri uri
     * @return future with body
     */
    public CompletableFuture<byte[]> getAsyncBytes(URI uri) {
        return dispatchAsync(() -> getBytesInternal(uri));
    }

    /**
     * Send a request and return the body as stream. The caller closes the stream.
     *
     * @param uri uri
     * @return body
     */
    public InputStream getStream(URI uri) {
        return dispatch(() -> getStreamInternal(uri));
    }

    /**
     * Send an asynchronous request and return the body as stream. The caller closes the stream.
     *
     * @param uri uri
     * @return future with body
     */
    public CompletableFuture<InputStream> getAsyncStream(URI uri) {
        return dispatchAsync(() -> getStreamInternal(uri));
    }

    /**
     * Send a request and write the body to a file
     *
     * @param uri  uri
     * @param path file to write, or null for a new temporary file
     * @return the written file
     */
    public Path getToFile(URI uri, @Nullable Path path) {
        return dispatch(() -> getToFileInternal(uri, path));
    }

    /**
     * Send an asynchronous request and write the body to a file
     *
     * @param uri  uri
     * @param path file to write, or null for a new temporary file
     * @return future with the written file
     */
    public CompletableFuture<Path> getAsyncToFile(URI uri, @Nullable Path path) {
        return dispatchAsync(() -> getToFileInternal(uri, path));
    }

    /**
     * Send a delete request
     *
     * @param uri uri
     * @return null
     */
    public Void delete(URI uri) {
        return dispatch(() -> deleteInternal(uri));
    }

    /**
     * Send an asynchronous delete request
     *
     * @param uri uri
     * @return future completed with null
     */
    public CompletableFuture<Void> deleteAsync(URI uri) {
        return dispatchAsync(() -> deleteInternal(uri));
    }

    /**
     * Run a call on the current thread once the rate limit allows it.
     *
     * @param call call
     * @param <T>  result type
     * @return result of the call
     */
    public <T> T dispatch(Supplier<T> call) {
        try {
            bucket.asBlocking().consume(1);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new IllegalStateException("Interrupted while waiting for the rate limit", e);
        }
        return call.get();
    }

    /**
     * Run a call on the executor once the rate limit allows it.
     *
     * @param call call
     * @param <T>  result type
     * @return future with the result of the call
     */
    public <T> CompletableFuture<T> dispatchAsync(Supplier<T> call) {
        return bucket.asScheduler().consume(1, executorService).thenApplyAsync(v -> call.get(), executorService);
    }

    @Override
    public V1 v1() {
        return v1;
    }

    private <T> T getAndMapInternal(URI uri, Class<T> result) {
        return objectMapper.readValue(getStringInternal(uri), result);
    }

    private <T> List<T> getAndMapListInternal(URI uri, Class<T> result) {
        CollectionType type = objectMapper.getTypeFactory().constructCollectionType(ArrayList.class, result);
        return objectMapper.readValue(getStringInternal(uri), type);
    }

    private String getStringInternal(URI uri) {
        HttpResponse<String> response = send(request(uri).GET().build(), HttpResponse.BodyHandlers.ofString());
        log.trace("Received\n{}", response.body());
        if (failed(response)) throw new NexusException(uri, response.statusCode(), response.body());
        return response.body();
    }

    private byte[] getBytesInternal(URI uri) {
        HttpResponse<byte[]> response = send(request(uri).GET().build(), HttpResponse.BodyHandlers.ofByteArray());
        if (failed(response)) {
            throw new NexusException(uri, response.statusCode(), new String(response.body(), StandardCharsets.UTF_8));
        }
        return response.body();
    }

    private InputStream getStreamInternal(URI uri) {
        HttpResponse<InputStream> response =
                send(request(uri).GET().build(), HttpResponse.BodyHandlers.ofInputStream());
        if (failed(response)) {
            try (InputStream body = response.body()) {
                throw new NexusException(
                        uri, response.statusCode(), new String(body.readAllBytes(), StandardCharsets.UTF_8));
            } catch (IOException e) {
                throw new UncheckedIOException(e);
            }
        }
        return response.body();
    }

    private Path getToFileInternal(URI uri, @Nullable Path path) {
        try {
            Path target = path == null ? Files.createTempFile("nexus", "file") : path;
            HttpResponse<Path> response = send(request(uri).GET().build(), HttpResponse.BodyHandlers.ofFile(target));
            if (failed(response)) {
                String body = new String(Files.readAllBytes(target), StandardCharsets.UTF_8);
                Files.deleteIfExists(target);
                throw new NexusException(uri, response.statusCode(), body);
            }
            return response.body();
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    private Void deleteInternal(URI uri) {
        sendWithoutBody(request(uri).DELETE().build());
        return null;
    }

    private void sendWithoutBody(HttpRequest request) {
        HttpResponse<String> response = send(request, HttpResponse.BodyHandlers.ofString());
        if (failed(response)) throw new NexusException(request.uri(), response.statusCode(), response.body());
    }

    private <T> HttpResponse<T> send(HttpRequest request, HttpResponse.BodyHandler<T> handler) {
        log.trace("Requesting {} {}", request.method(), request.uri());
        try {
            return http.send(request, handler);
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new IllegalStateException("Interrupted while waiting for Nexus", e);
        }
    }

    private HttpRequest.Builder request(URI uri) {
        HttpRequest.Builder builder = HttpRequest.newBuilder(uri);
        header.forEach(builder::header);
        return builder;
    }

    private static boolean failed(HttpResponse<?> response) {
        return response.statusCode() < 200 || response.statusCode() >= 300;
    }

    private static URI withoutTrailingSlash(URI uri) {
        String path = uri.getPath();
        if (path == null || !path.endsWith("/")) return uri;
        return uri.resolve(path.substring(0, path.length() - 1));
    }

    private static URI build(URIBuilder uri) {
        try {
            return uri.build();
        } catch (URISyntaxException e) {
            throw new IllegalArgumentException(e);
        }
    }
}
