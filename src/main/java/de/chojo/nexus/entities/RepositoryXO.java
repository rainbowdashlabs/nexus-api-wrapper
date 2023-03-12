/*
 *     SPDX-License-Identifier: AGPL-3.0-only
 *
 *     Copyright (C) RainbowDashLabs and Contributor
 */

package de.chojo.nexus.entities;

import com.fasterxml.jackson.annotation.JacksonInject;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import de.chojo.nexus.NexusRest;

import java.util.Map;
import java.util.Objects;

public final class RepositoryXO {
    @JacksonInject
    private NexusRest nexusRest;
    private final String name;
    private final String format;
    private final RepositoryType type;
    private final String url;
    private final Map<String, Object> attributes;

    @JsonCreator
    public RepositoryXO(@JsonProperty("name") String name,
                        @JsonProperty("format") String format,
                        @JsonProperty("type") RepositoryType type,
                        @JsonProperty("url") String url,
                        @JsonProperty("attributes") Map<String, Object> attributes) {
        this.name = name;
        this.format = format;
        this.type = type;
        this.url = url;
        this.attributes = attributes;
    }

    public String name() {
        return name;
    }

    public String format() {
        return format;
    }

    public RepositoryType type() {
        return type;
    }

    public String url() {
        return url;
    }

    public Map<String, Object> attributes() {
        return attributes;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (obj == null || obj.getClass() != this.getClass()) return false;
        var that = (RepositoryXO) obj;
        return Objects.equals(this.name, that.name) &&
               Objects.equals(this.format, that.format) &&
               Objects.equals(this.type, that.type) &&
               Objects.equals(this.url, that.url) &&
               Objects.equals(this.attributes, that.attributes);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, format, type, url, attributes);
    }

    @Override
    public String toString() {
        return "RepositoryXO[" +
               "name=" + name + ", " +
               "format=" + format + ", " +
               "type=" + type + ", " +
               "url=" + url + ", " +
               "attributes=" + attributes + ']';
    }

}
