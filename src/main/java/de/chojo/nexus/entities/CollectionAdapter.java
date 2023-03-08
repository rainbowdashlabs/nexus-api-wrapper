package de.chojo.nexus.entities;

import org.jetbrains.annotations.NotNull;

import java.util.Collection;
import java.util.Iterator;

public interface CollectionAdapter<T> extends Collection<T> {
    Collection<T> collection();

    @Override
    default int size() {
        return collection().size();
    }

    @Override
    default boolean isEmpty() {
        return collection().isEmpty();
    }

    @Override
    default boolean contains(Object o) {
        return collection().contains(o);
    }

    @NotNull
    @Override
    default Iterator<T> iterator() {
        return collection().iterator();
    }

    @NotNull
    @Override
    default Object[] toArray() {
        return collection().toArray();
    }

    @NotNull
    @Override
    default <T> T[] toArray(@NotNull T[] a) {
        return collection().toArray(a);
    }

    @Override
    default boolean add(T componentXO) {
        throw new UnsupportedOperationException();
    }

    @Override
    default boolean remove(Object o) {
        throw new UnsupportedOperationException();
    }

    @Override
    default boolean containsAll(@NotNull Collection<?> c) {
        return collection().containsAll(c);
    }

    @Override
    default boolean addAll(@NotNull Collection<? extends T> c) {
        throw new UnsupportedOperationException();
    }

    @Override
    default boolean removeAll(@NotNull Collection<?> c) {
        throw new UnsupportedOperationException();
    }

    @Override
    default boolean retainAll(@NotNull Collection<?> c) {
        throw new UnsupportedOperationException();
    }

    @Override
    default void clear() {
        throw new UnsupportedOperationException();
    }
}
