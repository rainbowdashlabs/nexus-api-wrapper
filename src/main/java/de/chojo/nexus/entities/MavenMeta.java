/*
 *     SPDX-License-Identifier: LGPL-3.0-or-later
 *
 *     Copyright (C) RainbowDashLabs and Contributor
 */

package de.chojo.nexus.entities;

public record MavenMeta(String extension, String groupId, String classifier, String artifactId, String version) {}
