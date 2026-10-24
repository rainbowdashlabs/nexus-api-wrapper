/*
 *     SPDX-License-Identifier: LGPL-3.0-or-later
 *
 *     Copyright (C) RainbowDashLabs and Contributor
 */

package de.chojo.nexus.entities;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public record ComponentXO(
        @JsonProperty String id,
        @JsonProperty String repository,
        @JsonProperty String format,
        @JsonProperty String group,
        @JsonProperty String name,
        @JsonProperty String version,
        @JsonProperty List<AssetXO> assets) {}
