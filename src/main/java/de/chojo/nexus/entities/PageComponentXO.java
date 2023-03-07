package de.chojo.nexus.entities;

import java.util.List;

public record PageComponentXO(List<ComponentXO> items, String continuationToken) {
}
