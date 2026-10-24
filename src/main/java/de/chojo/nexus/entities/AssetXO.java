/*
 *     SPDX-License-Identifier: LGPL-3.0-or-later
 *
 *     Copyright (C) RainbowDashLabs and Contributor
 */

package de.chojo.nexus.entities;

import com.fasterxml.jackson.annotation.JacksonInject;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import de.chojo.nexus.NexusRest;
import de.chojo.nexus.requests.v1.assets.DownloadRequest;
import de.chojo.nexus.requests.v1.assets.DownloadStreamRequest;
import de.chojo.nexus.requests.v1.assets.DownloadToFileRequest;
import org.jetbrains.annotations.Nullable;

import java.time.OffsetDateTime;
import java.util.Objects;

/**
 * A single file in a repository.
 */
public final class AssetXO {
    @JacksonInject
    private NexusRest nexusRest;

    @JsonProperty
    private final String downloadUrl;

    @JsonProperty
    private final String path;

    @JsonProperty
    private final String id;

    @JsonProperty
    private final String repository;

    @JsonProperty
    private final String format;

    @JsonProperty
    private final String contentType;

    @JsonProperty
    private final OffsetDateTime lastModified;

    @JsonProperty
    private final @Nullable OffsetDateTime lastDownloaded;

    @JsonProperty
    private final Checksum checksum;

    @JsonProperty
    private final String uploader;

    @JsonProperty
    private final String uploaderIp;

    @JsonProperty
    private final long fileSize;

    @JsonProperty
    private final MavenMeta maven2;

    @JsonCreator
    public AssetXO(
            @JsonProperty("downloadUrl") String downloadUrl,
            @JsonProperty("path") String path,
            @JsonProperty("id") String id,
            @JsonProperty("repository") String repository,
            @JsonProperty("format") String format,
            @JsonProperty("contentType") String contentType,
            @JsonProperty("lastModified") OffsetDateTime lastModified,
            @JsonProperty("lastDownloaded") OffsetDateTime lastDownloaded,
            @JsonProperty("checksum") Checksum checksum,
            @JsonProperty("uploader") String uploader,
            @JsonProperty("uploaderIp") String uploaderIp,
            @JsonProperty("fileSize") long fileSize,
            @JsonProperty("maven2") MavenMeta maven2) {
        this.downloadUrl = downloadUrl;
        this.path = path;
        this.id = id;
        this.repository = repository;
        this.format = format;
        this.contentType = contentType;
        this.lastModified = lastModified;
        this.lastDownloaded = lastDownloaded;
        this.checksum = checksum;
        this.uploader = uploader;
        this.uploaderIp = uploaderIp;
        this.fileSize = fileSize;
        this.maven2 = maven2;
    }

    @JacksonInject
    public NexusRest nexusRest() {
        return nexusRest;
    }

    @JsonProperty
    public String downloadUrl() {
        return downloadUrl;
    }

    @JsonProperty
    public String path() {
        return path;
    }

    @JsonProperty
    public String id() {
        return id;
    }

    /**
     * The name of the repository that holds the asset.
     *
     * @return repository name
     */
    @JsonProperty
    public String repository() {
        return repository;
    }

    @JsonProperty
    public String format() {
        return format;
    }

    @JsonProperty
    public String contentType() {
        return contentType;
    }

    @JsonProperty
    public OffsetDateTime lastModified() {
        return lastModified;
    }

    @JsonProperty
    public @Nullable OffsetDateTime lastDownloaded() {
        return lastDownloaded;
    }

    @JsonProperty
    public Checksum checksum() {
        return checksum;
    }

    @JsonProperty
    public String uploader() {
        return uploader;
    }

    @JsonProperty
    public String uploaderIp() {
        return uploaderIp;
    }

    /**
     * The size of the file in bytes.
     *
     * @return size in bytes
     */
    @JsonProperty
    public long fileSize() {
        return fileSize;
    }

    @JsonProperty
    public MavenMeta maven2() {
        return maven2;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (obj == null || obj.getClass() != this.getClass()) return false;
        var that = (AssetXO) obj;
        return Objects.equals(this.nexusRest, that.nexusRest)
                && Objects.equals(this.downloadUrl, that.downloadUrl)
                && Objects.equals(this.path, that.path)
                && Objects.equals(this.id, that.id)
                && Objects.equals(this.repository, that.repository)
                && Objects.equals(this.format, that.format)
                && Objects.equals(this.contentType, that.contentType)
                && Objects.equals(this.lastModified, that.lastModified)
                && Objects.equals(this.lastDownloaded, that.lastDownloaded)
                && Objects.equals(this.checksum, that.checksum)
                && Objects.equals(this.uploader, that.uploader)
                && Objects.equals(this.uploaderIp, that.uploaderIp)
                && this.fileSize == that.fileSize
                && Objects.equals(this.maven2, that.maven2);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
                nexusRest,
                downloadUrl,
                path,
                id,
                repository,
                format,
                contentType,
                lastModified,
                lastDownloaded,
                checksum,
                uploader,
                uploaderIp,
                fileSize,
                maven2);
    }

    @Override
    public String toString() {
        return "AssetXO[" + "nexusRest="
                + nexusRest + ", " + "downloadUrl="
                + downloadUrl + ", " + "path="
                + path + ", " + "id="
                + id + ", " + "repository="
                + repository + ", " + "format="
                + format + ", " + "contentType="
                + contentType + ", " + "lastModified="
                + lastModified + ", " + "lastDownloaded="
                + lastDownloaded + ", " + "checksum="
                + checksum + ", " + "uploader="
                + uploader + ", " + "uploaderIp="
                + uploaderIp + ", " + "fileSize="
                + fileSize + ", " + "maven2="
                + maven2 + ']';
    }

    public DownloadRequest download() {
        return nexusRest.v1().assets().download(id);
    }

    public DownloadToFileRequest downloadToFile() {
        return nexusRest.v1().assets().downloadToFile(id);
    }

    public DownloadStreamRequest downloadStream() {
        return nexusRest.v1().assets().downloadStream(id);
    }
}
