/*
 *     SPDX-License-Identifier: LGPL-3.0-or-later
 *
 *     Copyright (C) RainbowDashLabs and Contributor
 */

package de.chojo.nexus;

import org.jetbrains.annotations.CheckReturnValue;

import java.net.Authenticator;
import java.net.http.HttpClient;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Class to build a nexus rest client
 */
public class NexusRestBuilder {
    private HttpClient http;
    private final HttpClient.Builder httpBuilder = HttpClient.newBuilder()
            .version(HttpClient.Version.HTTP_2)
            .followRedirects(HttpClient.Redirect.NORMAL)
            .connectTimeout(Duration.ofSeconds(60));
    private ScheduledExecutorService executorService;
    private final Map<String, String> header = new HashMap<>();
    private final String host;

    public NexusRestBuilder(String host) {
        this.host = host;
    }

    /**
     * Set the http client used to send requests
     *
     * @param http client
     * @return builder
     */
    @CheckReturnValue
    public NexusRestBuilder setHttp(HttpClient http) {
        this.http = http;
        return this;
    }

    /**
     * Set the authenticator for the underlying {@link HttpClient} builder.
     *
     * @param authenticator authenticator
     * @return builder
     */
    @CheckReturnValue
    public NexusRestBuilder setAuth(Authenticator authenticator) {
        this.httpBuilder.authenticator(authenticator);
        return this;
    }

    /**
     * Log in with HTTP Basic authentication. The credentials are sent with every request.
     *
     * @param username username
     * @param password password
     * @return builder
     */
    @CheckReturnValue
    public NexusRestBuilder setPasswordAuth(String username, String password) {
        String valueToEncode = "%s:%s".formatted(username, password);
        return header(
                "Authorization",
                "Basic " + Base64.getEncoder().encodeToString(valueToEncode.getBytes(StandardCharsets.UTF_8)));
    }

    /**
     * Add a header that is sent with every request.
     *
     * @param key   header name
     * @param value header value
     * @return builder
     */
    @CheckReturnValue
    public NexusRestBuilder header(String key, String value) {
        header.put(key, value);
        return this;
    }

    /**
     * Set the executor service used for asynchronous requests. Without one, the client uses a pool of two daemon
     * threads, which does not keep the JVM running.
     *
     * @param executorService executor service
     * @return builder
     */
    @CheckReturnValue
    public NexusRestBuilder setExecutorService(ScheduledExecutorService executorService) {
        this.executorService = executorService;
        return this;
    }

    /**
     * Build the api. The instance is ready to use.
     *
     * @return api instance
     */
    @CheckReturnValue
    public NexusRest build() {
        return new NexusRestImpl(
                http != null ? http : httpBuilder.build(),
                executorService != null ? executorService : defaultExecutor(),
                host,
                header);
    }

    private static ScheduledExecutorService defaultExecutor() {
        AtomicInteger count = new AtomicInteger();
        return Executors.newScheduledThreadPool(2, runnable -> {
            Thread thread = new Thread(runnable, "nexus-api-wrapper-" + count.incrementAndGet());
            thread.setDaemon(true);
            return thread;
        });
    }
}
