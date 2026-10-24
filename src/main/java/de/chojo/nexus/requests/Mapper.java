/*
 *     SPDX-License-Identifier: LGPL-3.0-or-later
 *
 *     Copyright (C) RainbowDashLabs and Contributor
 */

package de.chojo.nexus.requests;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import de.chojo.nexus.NexusRest;
import de.chojo.nexus.NexusRestImpl;
import tools.jackson.databind.DeserializationFeature;
import tools.jackson.databind.InjectableValues;
import tools.jackson.databind.MapperFeature;
import tools.jackson.databind.ObjectMapper;
import tools.jackson.databind.json.JsonMapper;

/**
 * Class providing preconfigured {@link ObjectMapper}
 */
public final class Mapper {
    private Mapper() {
        throw new UnsupportedOperationException("This is a utility class.");
    }

    /**
     * A mapper able to map items and everything used in rest response. Entities that need the client to send follow-up
     * requests get it injected as {@link NexusRest}.
     *
     * @param nexusRest client injected into entities
     * @return object mapper
     */
    public static ObjectMapper create(NexusRestImpl nexusRest) {
        InjectableValues.Std injectables = new InjectableValues.Std();
        injectables.addValue(NexusRest.class, nexusRest);
        return JsonMapper.builder()
                .enable(MapperFeature.ACCEPT_CASE_INSENSITIVE_ENUMS)
                .enable(DeserializationFeature.ACCEPT_EMPTY_ARRAY_AS_NULL_OBJECT)
                .disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES)
                .changeDefaultVisibility(visibility -> visibility.withFieldVisibility(JsonAutoDetect.Visibility.NONE))
                .injectableValues(injectables)
                .build();
    }
}
