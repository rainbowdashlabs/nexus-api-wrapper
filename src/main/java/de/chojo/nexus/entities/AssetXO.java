/*
 *     SPDX-License-Identifier: AGPL-3.0-only
 *
 *     Copyright (C) RainbowDashLabs and Contributor
 */

package de.chojo.nexus.entities;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDateTime;

public record AssetXO(@JsonProperty String downloadUrl,
                      @JsonProperty String path,
                      @JsonProperty String id,
                      @JsonProperty String Repository,
                      @JsonProperty String format,
                      @JsonProperty String contentType,
                      @JsonProperty String lastModified,
                      @JsonProperty String lastDownloaded,
                      @JsonProperty Checksum checksum,
                      @JsonProperty String uploader,
                      @JsonProperty String uploaderIp,
                      @JsonProperty int fileSize) {
}
