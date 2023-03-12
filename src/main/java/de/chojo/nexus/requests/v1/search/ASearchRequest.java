/*
 *     SPDX-License-Identifier: AGPL-3.0-only
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

    public Self dockerImageTag(String imageName) {
        parameter("docker.imageTag", imageName);
        return (Self) this;
    }

    public Self dockerLayerId(String imageName) {
        parameter("docker.layerId", imageName);
        return (Self) this;
    }

    public Self dockerContentDigest(String imageName) {
        parameter("docker.contentDigest", imageName);
        return (Self) this;
    }

    public Self mavenGroupId(String imageName) {
        parameter("maven.groupId", imageName);
        return (Self) this;
    }

    public Self mavenArtifactId(String imageName) {
        parameter("maven.artifactId", imageName);
        return (Self) this;
    }

    public Self mavenBaseVersion(String imageName) {
        parameter("maven.baseVersion", imageName);
        return (Self) this;
    }

    public Self mavenExtension(String imageName) {
        parameter("maven.extension", imageName);
        return (Self) this;
    }

    public Self mavenClassifier(String imageName) {
        parameter("maven.classifier", imageName);
        return (Self) this;
    }

    public Self gavec(String imageName) {
        parameter("gavec", imageName);
        return (Self) this;
    }

    public Self npmScope(String imageName) {
        parameter("npm.scope", imageName);
        return (Self) this;
    }

    public Self npmAuthor(String imageName) {
        parameter("npm.author", imageName);
        return (Self) this;
    }

    public Self npmDescription(String imageName) {
        parameter("npm.description", imageName);
        return (Self) this;
    }

    public Self npmKeywords(String imageName) {
        parameter("npm.keywords", imageName);
        return (Self) this;
    }

    public Self npmLicense(String imageName) {
        parameter("npm.license", imageName);
        return (Self) this;
    }

    public Self npmTaggedIs(String imageName) {
        parameter("npm.tagged_is", imageName);
        return (Self) this;
    }

    public Self npmTaggedNot(String imageName) {
        parameter("npm.tagged_not", imageName);
        return (Self) this;
    }

    public Self nugetId(String imageName) {
        parameter("nuget.id", imageName);
        return (Self) this;
    }

    public Self nugetTags(String imageName) {
        parameter("nuget.tags", imageName);
        return (Self) this;
    }

    public Self nugetTitle(String imageName) {
        parameter("nuget.title", imageName);
        return (Self) this;
    }

    public Self nugetAuthors(String imageName) {
        parameter("nuget.authors", imageName);
        return (Self) this;
    }

    public Self nugetDescription(String imageName) {
        parameter("nuget.description", imageName);
        return (Self) this;
    }

    public Self nugetSummary(String imageName) {
        parameter("nuget.summary", imageName);
        return (Self) this;
    }

    public Self p2PluginName(String imageName) {
        parameter("p2.pluginName", imageName);
        return (Self) this;
    }

    public Self pypiClassifiers(String imageName) {
        parameter("pypi.classifiers", imageName);
        return (Self) this;
    }

    public Self pypiDescription(String imageName) {
        parameter("pypi.description", imageName);
        return (Self) this;
    }

    public Self pypiKeywords(String imageName) {
        parameter("pypi.keywords", imageName);
        return (Self) this;
    }

    public Self pypiSummary(String imageName) {
        parameter("pypi.summary", imageName);
        return (Self) this;
    }

    public Self rubygemsDescription(String imageName) {
        parameter("rubygems.description", imageName);
        return (Self) this;
    }

    public Self rubygemsPlatform(String imageName) {
        parameter("rubygems.platform", imageName);
        return (Self) this;
    }

    public Self rubygemsSummary(String imageName) {
        parameter("rubygems.summary", imageName);
        return (Self) this;
    }

    public Self yumArchitecture(String imageName) {
        parameter("yum.architecture", imageName);
        return (Self) this;
    }

    public Self yumName(String imageName) {
        parameter("yum.name", imageName);
        return (Self) this;
    }
}
