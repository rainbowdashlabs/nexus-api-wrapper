/*
 *     SPDX-License-Identifier: AGPL-3.0-only
 *
 *     Copyright (C) RainbowDashLabs and Contributor
 */

package de.chojo.nexus.entities;

public record MavenMeta(String extension, String groupId, String classifier, String artifactId, String version) {

}
