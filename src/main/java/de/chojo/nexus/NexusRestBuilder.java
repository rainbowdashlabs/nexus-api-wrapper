/*
 *     SPDX-License-Identifier: AGPL-3.0-only
 *
 *     Copyright (C) RainbowDashLabs and Contributor
 */

package de.chojo.nexus;

import javax.annotation.CheckReturnValue;
import java.net.Authenticator;
import java.net.http.HttpClient;
import java.time.Duration;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

/**
 * Class to build a nexus rest client
 */
public class NexusRestBuilder {
    private HttpClient http;
    private final HttpClient.Builder httpBuilder = HttpClient.newBuilder()
            .version(HttpClient.Version.HTTP_2)
            .followRedirects(HttpClient.Redirect.NORMAL)
            .connectTimeout(Duration.ofSeconds(60));
    private ScheduledExecutorService executorService = Executors.newScheduledThreadPool(2);
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
     * Set the authenticator for the underlying {@link HttpClient} builder.
     *
     * @param username username
     * @param password password
     * @return builder
     */
    @CheckReturnValue
    public NexusRestBuilder setPasswordAuth(String username, String password) {
        String valueToEncode = "%s:%s".formatted(username, password);
        return header("Authorization", "Basic " + Base64.getEncoder().encodeToString(valueToEncode.getBytes()));
    }

    public NexusRestBuilder header(String key, String value) {
        header.put(key, value);
        return this;
    }

    /**
     * Set the executor service used for asynchronous requests.
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
        return new NexusRestImpl(http != null ? http : httpBuilder.build(), executorService, host, header);
    }
}
