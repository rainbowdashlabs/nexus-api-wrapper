/*
 *     SPDX-License-Identifier: LGPL-3.0-or-later
 *
 *     Copyright (C) RainbowDashLabs and Contributor
 */

package de.chojo.nexus;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.io.InputStream;
import java.io.UncheckedIOException;
import java.net.InetSocketAddress;
import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * A local stand-in for Nexus that answers with JSON recorded from Nexus Repository 3.96.1.
 *
 * <p>It serves the REST API under {@code <contextPath>/service/rest} and files under {@code <contextPath>/repository}.
 * The asset id {@code missing} answers like an unknown id on a real Nexus, the asset id {@code broken} points to a
 * file that fails to download.
 */
final class FakeNexus implements AutoCloseable {
    static final byte[] JAR = "jar bytes".getBytes(StandardCharsets.UTF_8);

    private final HttpServer server;
    private final String contextPath;
    private final List<Request> requests = new CopyOnWriteArrayList<>();

    /**
     * A request the server received.
     *
     * @param method        HTTP method
     * @param path          path without query
     * @param query         raw query, or null
     * @param authorization the authorization header, or null
     */
    record Request(String method, String path, String query, String authorization) {}

    private FakeNexus(String contextPath) throws IOException {
        this.contextPath = contextPath;
        server = HttpServer.create(new InetSocketAddress("localhost", 0), 0);
        server.createContext("/", this::handle);
        server.start();
    }

    /**
     * Start a server at the root path.
     *
     * @return server
     */
    static FakeNexus start() throws IOException {
        return new FakeNexus("");
    }

    /**
     * Start a server below a context path.
     *
     * @param contextPath context path like {@code /nexus}
     * @return server
     */
    static FakeNexus start(String contextPath) throws IOException {
        return new FakeNexus(contextPath);
    }

    /**
     * The base url of this Nexus, including the context path.
     *
     * @return base url
     */
    URI baseUrl() {
        return URI.create("http://localhost:" + server.getAddress().getPort() + contextPath);
    }

    /**
     * A client for this server.
     *
     * @return client
     */
    NexusRest client() {
        return NexusRest.builder(baseUrl()).build();
    }

    /**
     * The requests received so far.
     *
     * @return requests
     */
    List<Request> requests() {
        return requests;
    }

    /**
     * The last request received for a path below {@code /service/rest}.
     *
     * @param path path below {@code /service/rest}, like {@code /v1/search}
     * @return request
     */
    Request last(String path) {
        return requests.reversed().stream()
                .filter(request -> request.path().equals(contextPath + "/service/rest" + path))
                .findFirst()
                .orElseThrow();
    }

    @Override
    public void close() {
        server.stop(0);
    }

    private void handle(HttpExchange exchange) throws IOException {
        URI uri = exchange.getRequestURI();
        requests.add(new Request(
                exchange.getRequestMethod(),
                uri.getPath(),
                uri.getRawQuery(),
                exchange.getRequestHeaders().getFirst("Authorization")));
        String path = uri.getPath().substring(contextPath.length());
        String query = uri.getRawQuery() == null ? "" : uri.getRawQuery();
        try (exchange) {
            switch (path) {
                case "/service/rest/v1/repositories" -> json(exchange, 200, "repositories.json");
                case "/service/rest/v1/search" -> json(exchange, 200, "search-components.json");
                case "/service/rest/v1/search/assets", "/service/rest/v1/assets" ->
                    json(
                            exchange,
                            200,
                            query.contains("continuationToken=2")
                                    ? "search-assets-page-2.json"
                                    : "search-assets-page-1.json");
                case "/service/rest/v1/search/assets/download" -> {
                    exchange.getResponseHeaders()
                            .add(
                                    "Location",
                                    baseUrl()
                                            + "/repository/maven-releases/de/chojo/nexus-api-wrapper/1.0.5/nexus-api-wrapper-1.0.5.jar");
                    exchange.sendResponseHeaders(302, -1);
                }
                case "/service/rest/v1/assets/missing" -> json(exchange, 422, "error-422.json");
                case "/service/rest/v1/assets/broken" ->
                    send(
                            exchange,
                            200,
                            fixture("asset.json").replace("/repository/maven-releases/", "/repository/broken/"));
                default -> other(exchange, path);
            }
        }
    }

    private void other(HttpExchange exchange, String path) throws IOException {
        if (path.startsWith("/service/rest/v1/assets/")) {
            if (exchange.getRequestMethod().equals("DELETE")) {
                exchange.sendResponseHeaders(204, -1);
                return;
            }
            json(exchange, 200, "asset.json");
            return;
        }
        if (path.startsWith("/repository/broken/")) {
            send(exchange, 500, "storage unavailable");
            return;
        }
        if (path.startsWith("/repository/")) {
            exchange.sendResponseHeaders(200, JAR.length);
            exchange.getResponseBody().write(JAR);
            return;
        }
        send(exchange, 404, "not found");
    }

    private void json(HttpExchange exchange, int status, String fixture) throws IOException {
        exchange.getResponseHeaders().add("Content-Type", "application/json");
        send(exchange, status, fixture(fixture));
    }

    private static void send(HttpExchange exchange, int status, String body) throws IOException {
        byte[] bytes = body.getBytes(StandardCharsets.UTF_8);
        exchange.sendResponseHeaders(status, bytes.length);
        exchange.getResponseBody().write(bytes);
    }

    private String fixture(String name) {
        try (InputStream in = FakeNexus.class.getResourceAsStream("/fixtures/" + name)) {
            if (in == null) throw new IllegalArgumentException("Unknown fixture " + name);
            return new String(in.readAllBytes(), StandardCharsets.UTF_8)
                    .replace("{{base}}", baseUrl().toString());
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }
}
