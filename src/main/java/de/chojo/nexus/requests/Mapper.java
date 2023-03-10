/*
 *     SPDX-License-Identifier: AGPL-3.0-only
 *
 *     Copyright (C) RainbowDashLabs and Contributor
 */

package de.chojo.nexus.requests;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.InjectableValues;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import de.chojo.nexus.NexusRest;
import de.chojo.nexus.NexusRestImpl;

/**
 * Class providing preconfigured {@link ObjectMapper}
 */
public final class Mapper {
    private Mapper() {
        throw new UnsupportedOperationException("This is a utility class.");
    }

    /**
     * A mapper able to map items and everything used in rest response
     *
     * @return object mapper
     */
    public static ObjectMapper create(NexusRestImpl nexusRest) {
        SimpleModule module = new SimpleModule();
        InjectableValues.Std iv = new InjectableValues.Std();
        iv.addValue(NexusRest.class, nexusRest);
        //iv.addValue(NexusRestImpl.class, nexusRest);
        return JsonMapper.builder()
                .enable(MapperFeature.ACCEPT_CASE_INSENSITIVE_ENUMS)
                .configure(DeserializationFeature.ACCEPT_EMPTY_ARRAY_AS_NULL_OBJECT, true)
                .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
                .build()
                .setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.NONE)
                .setInjectableValues(iv)
                .registerModule(module)
                .registerModule(new JavaTimeModule());
    }
}
