/*
 *     SPDX-License-Identifier: LGPL-3.0-or-later
 *
 *     Copyright (C) RainbowDashLabs and Contributor
 */

package de.chojo.nexus.entities;

/**
 *
 * @param name A unique identifier for this repository
 * @param format Component format held in this repository
 * @param type Controls if deployments of and updates to artifacts are allowed
 * @param url URL to the repository
 * @param online Whether this repository accepts incoming requests
 */
public record AbstractApiRepository(String name, String format, RepositoryType type, String url, boolean online) {}
