/*
 *     SPDX-License-Identifier: LGPL-3.0-or-later
 *
 *     Copyright (C) RainbowDashLabs and Contributor
 */

package de.chojo.nexus;

import java.net.http.HttpClient;

public class NexusAPI {
    HttpClient client = HttpClient.newBuilder().build();
}
