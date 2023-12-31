package com.odeyalo.sonata.harmony.repository;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Collection;

public interface BasicRepository<T, ID> extends RemoveCapable<T, ID> {

    Mono<T> save(T t);

    Flux<T> saveAll(Flux<T> flux);

    Flux<T> saveAll(Collection<T> flux);

    Mono<T> findById(ID id);

    Flux<T> findAll();

    Flux<T> findAllById(Flux<ID> ids);

    Flux<T> findAllById(Collection<ID> ids);
}
