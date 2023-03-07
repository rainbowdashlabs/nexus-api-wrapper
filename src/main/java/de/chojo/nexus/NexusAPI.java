/*
 *     SPDX-License-Identifier: AGPL-3.0-only
 *
 *     Copyright (C) RainbowDashLabs and Contributor
 */

package de.chojo.nexus;

import java.net.http.HttpClient;

public class NexusAPI {
    HttpClient client = HttpClient.newBuilder().build();
}
