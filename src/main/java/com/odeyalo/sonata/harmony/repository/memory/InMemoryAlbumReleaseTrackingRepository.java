package com.odeyalo.sonata.harmony.repository.memory;

import com.odeyalo.sonata.harmony.entity.AlbumReleaseTrackingEntity;
import com.odeyalo.sonata.harmony.repository.AlbumReleaseTrackingRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.*;
import java.util.concurrent.atomic.AtomicLong;

public class InMemoryAlbumReleaseTrackingRepository implements AlbumReleaseTrackingRepository {
    private final Map<Long, AlbumReleaseTrackingEntity> store = new HashMap<>();
    private final AtomicLong idGenerator = new AtomicLong();

    @Override
    public Mono<AlbumReleaseTrackingEntity> save(AlbumReleaseTrackingEntity entity) {
        if ( entity.getId() == null ) {
            entity.setId(idGenerator.incrementAndGet());
        }
        store.put(entity.getId(), entity);
        return Mono.just(entity);
    }

    @Override
    public Flux<AlbumReleaseTrackingEntity> saveAll(Flux<AlbumReleaseTrackingEntity> flux) {
        return flux.flatMap(this::save);
    }

    @Override
    public Flux<AlbumReleaseTrackingEntity> saveAll(Collection<AlbumReleaseTrackingEntity> flux) {
        return Flux.fromIterable(flux).flatMap(this::save);
    }

    @Override
    public Mono<AlbumReleaseTrackingEntity> findById(Long id) {
        return Mono.justOrEmpty(store.get(id));
    }

    @Override
    public Flux<AlbumReleaseTrackingEntity> findAll() {
        return Flux.fromIterable(store.values());
    }

    @Override
    public Flux<AlbumReleaseTrackingEntity> findAllById(Flux<Long> ids) {
        return ids.flatMap(this::findById);
    }

    @Override
    public Flux<AlbumReleaseTrackingEntity> findAllById(Collection<Long> ids) {
        return Flux.fromIterable(ids).flatMap(this::findById);
    }

    @Override
    public Mono<Void> deleteById(Long id) {

        store.remove(id);

        return Mono.empty();
    }

    @Override
    public Mono<Void> deleteAll() {
        store.clear();
        return Mono.empty();
    }

    @Override
    public Mono<AlbumReleaseTrackingEntity> findByTrackingId(String trackingId) {
        return Flux.fromIterable(store.values())
                .filter(r -> Objects.equals(r.getTrackingId(), trackingId))
                .next();
    }
}
