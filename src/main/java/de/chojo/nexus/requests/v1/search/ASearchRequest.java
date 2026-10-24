/*
 *     SPDX-License-Identifier: LGPL-3.0-or-later
 *
 *     Copyright (C) RainbowDashLabs and Contributor
 */

package de.chojo.nexus.requests.v1.search;

import de.chojo.nexus.NexusRestImpl;
import de.chojo.nexus.requests.RequestBuilder;

import java.util.Locale;

public abstract class ASearchRequest<Self extends ASearchRequest<?, T>, T> extends RequestBuilder<T> {
    public ASearchRequest(NexusRestImpl rest) {
        super(rest);
    }

    public Self continuationToken(String token) {
        parameter("continuationToken", token);
        return (Self) this;
    }

    public Self sort(Sort sort) {
        parameter("sort", sort.name().toLowerCase(Locale.ROOT));
        return (Self) this;
    }

    public Self direction(Direction direction) {
        parameter("direction", direction.name().toLowerCase(Locale.ROOT));
        return (Self) this;
    }

    public Self timeout(int timeout) {
        parameter("timeout", timeout);
        return (Self) this;
    }

    public Self query(String query) {
        parameter("q", query);
        return (Self) this;
    }

    public Self repository(String repository) {
        parameter("repository", repository);
        return (Self) this;
    }

    public Self format(String format) {
        parameter("format", format);
        return (Self) this;
    }

    public Self group(String group) {
        parameter("group", group);
        return (Self) this;
    }

    public Self name(String name) {
        parameter("name", name);
        return (Self) this;
    }

    public Self version(String version) {
        parameter("version", version);
        return (Self) this;
    }

    public Self prerelease(String prerelease) {
        parameter("prerelease", prerelease);
        return (Self) this;
    }

    public Self md5(String md5) {
        parameter("md5", md5);
        return (Self) this;
    }

    public Self sha1(String sha1) {
        parameter("sha1", sha1);
        return (Self) this;
    }

    public Self sha256(String sha256) {
        parameter("sha256", sha256);
        return (Self) this;
    }

    public Self sha512(String sha512) {
        parameter("sha512", sha512);
        return (Self) this;
    }

    public Self conanBaseVersion(String baseVersion) {
        parameter("conan.baseVersion", baseVersion);
        return (Self) this;
    }

    public Self conanChannel(String channel) {
        parameter("conan.channel", channel);
        return (Self) this;
    }

    public Self conanRevision(String revision) {
        parameter("conan.revision", revision);
        return (Self) this;
    }

    public Self dockerImageName(String imageName) {
        parameter("docker.imageName", imageName);
        return (Self) this;
    }

    public Self dockerImageTag(String imageTag) {
        parameter("docker.imageTag", imageTag);
        return (Self) this;
    }

    public Self dockerLayerId(String layerId) {
        parameter("docker.layerId", layerId);
        return (Self) this;
    }

    public Self dockerContentDigest(String contentDigest) {
        parameter("docker.contentDigest", contentDigest);
        return (Self) this;
    }

    public Self mavenGroupId(String groupId) {
        parameter("maven.groupId", groupId);
        return (Self) this;
    }

    public Self mavenArtifactId(String artifactId) {
        parameter("maven.artifactId", artifactId);
        return (Self) this;
    }

    public Self mavenBaseVersion(String baseVersion) {
        parameter("maven.baseVersion", baseVersion);
        return (Self) this;
    }

    public Self mavenExtension(String extension) {
        parameter("maven.extension", extension);
        return (Self) this;
    }

    public Self mavenClassifier(String classifier) {
        parameter("maven.classifier", classifier);
        return (Self) this;
    }

    public Self gavec(String gavec) {
        parameter("gavec", gavec);
        return (Self) this;
    }

    public Self npmScope(String scope) {
        parameter("npm.scope", scope);
        return (Self) this;
    }

    public Self npmAuthor(String author) {
        parameter("npm.author", author);
        return (Self) this;
    }

    public Self npmDescription(String description) {
        parameter("npm.description", description);
        return (Self) this;
    }

    public Self npmKeywords(String keywords) {
        parameter("npm.keywords", keywords);
        return (Self) this;
    }

    public Self npmLicense(String license) {
        parameter("npm.license", license);
        return (Self) this;
    }

    public Self npmTaggedIs(String taggedIs) {
        parameter("npm.tagged_is", taggedIs);
        return (Self) this;
    }

    public Self npmTaggedNot(String taggedNot) {
        parameter("npm.tagged_not", taggedNot);
        return (Self) this;
    }

    public Self nugetId(String id) {
        parameter("nuget.id", id);
        return (Self) this;
    }

    public Self nugetTags(String tags) {
        parameter("nuget.tags", tags);
        return (Self) this;
    }

    public Self nugetTitle(String title) {
        parameter("nuget.title", title);
        return (Self) this;
    }

    public Self nugetAuthors(String authors) {
        parameter("nuget.authors", authors);
        return (Self) this;
    }

    public Self nugetDescription(String description) {
        parameter("nuget.description", description);
        return (Self) this;
    }

    public Self nugetSummary(String summary) {
        parameter("nuget.summary", summary);
        return (Self) this;
    }

    public Self p2PluginName(String pluginName) {
        parameter("p2.pluginName", pluginName);
        return (Self) this;
    }

    public Self pypiClassifiers(String classifiers) {
        parameter("pypi.classifiers", classifiers);
        return (Self) this;
    }

    public Self pypiDescription(String description) {
        parameter("pypi.description", description);
        return (Self) this;
    }

    public Self pypiKeywords(String keywords) {
        parameter("pypi.keywords", keywords);
        return (Self) this;
    }

    public Self pypiSummary(String summary) {
        parameter("pypi.summary", summary);
        return (Self) this;
    }

    public Self rubygemsDescription(String description) {
        parameter("rubygems.description", description);
        return (Self) this;
    }

    public Self rubygemsPlatform(String platform) {
        parameter("rubygems.platform", platform);
        return (Self) this;
    }

    public Self rubygemsSummary(String summary) {
        parameter("rubygems.summary", summary);
        return (Self) this;
    }

    public Self yumArchitecture(String architecture) {
        parameter("yum.architecture", architecture);
        return (Self) this;
    }

    public Self yumName(String name) {
        parameter("yum.name", name);
        return (Self) this;
    }
}
