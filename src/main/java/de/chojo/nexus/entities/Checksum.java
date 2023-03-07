package de.chojo.nexus.entities;

public record Checksum(String sha1, String sha256, String sha512, String md5) {
}
