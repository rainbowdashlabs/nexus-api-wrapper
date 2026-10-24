/*
 *     SPDX-License-Identifier: LGPL-3.0-or-later
 *
 *     Copyright (C) RainbowDashLabs and Contributor
 */

/**
 * Client for the REST API of Sonatype Nexus Repository 3.
 */
module de.chojo.nexus {
    requires transitive java.net.http;
    requires transitive tools.jackson.databind;
    requires transitive io.github.bucket4j.core;
    requires transitive org.apache.httpcomponents.core5.httpcore5;
    requires org.slf4j;
    requires static transitive org.jetbrains.annotations;

    exports de.chojo.nexus;
    exports de.chojo.nexus.entities;
    exports de.chojo.nexus.requests;
    exports de.chojo.nexus.requests.v1.assets;
    exports de.chojo.nexus.requests.v1.repositories;
    exports de.chojo.nexus.requests.v1.search;
    exports de.chojo.nexus.requests.v1.search.assets;
    exports de.chojo.nexus.routes.v1;
    exports de.chojo.nexus.routes.v1.assets;
    exports de.chojo.nexus.routes.v1.repositories;
    exports de.chojo.nexus.routes.v1.search;
    exports de.chojo.nexus.routes.v1.search.assets;

    opens de.chojo.nexus.entities to
            tools.jackson.databind;
}
