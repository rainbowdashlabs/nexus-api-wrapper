/*
 *     SPDX-License-Identifier: AGPL-3.0-only
 *
 *     Copyright (C) RainbowDashLabs and Contributor
 */

package de.chojo.nexus.entities;

import org.jetbrains.annotations.NotNull;

import java.util.Collection;
import java.util.Iterator;
import java.util.stream.Stream;

public interface CollectionAdapter<T> extends Iterable<T>, CollectionProvider<T> {
    Collection<T> collection();

    default int size() {
        return collection().size();
    }

    default boolean isEmpty() {
        return collection().isEmpty();
    }

    default boolean contains(Object o) {
        return collection().contains(o);
    }

    @NotNull
    default Iterator<T> iterator() {
        return collection().iterator();
    }

    @NotNull
    default Object[] toArray() {
        return collection().toArray();
    }

    @NotNull
    default <T> T[] toArray(@NotNull T[] a) {
        return collection().toArray(a);
    }

    default Stream<T> stream() {
        return collection().stream();
    }
    default Stream<T> parallelStream() {
        return collection().parallelStream();
    }
}
