package com.odeyalo.sonata.harmony.repository;

import reactor.core.publisher.Mono;

/**
 * Represent that entity can be deleted
 * @param <T> - entity to delete
 * @param <ID> - id of the entity
 */
public interface RemoveCapable<T, ID> {

    Mono<Void> deleteById(ID id);

    Mono<Void> deleteAll();
}
