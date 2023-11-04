package com.odeyalo.sonata.harmony.repository.memory;

import com.odeyalo.sonata.harmony.entity.AlbumReleaseTrackingEntity;
import com.odeyalo.sonata.harmony.repository.AlbumReleaseTrackingRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class InMemoryAlbumReleaseTrackingRepository implements AlbumReleaseTrackingRepository {
    private final Map<String, AlbumReleaseTrackingEntity> store = new HashMap<>();

    @Override
    public Mono<AlbumReleaseTrackingEntity> save(AlbumReleaseTrackingEntity entity) {
        if ( entity.getTrackingId() == null ) {
            entity.setTrackingId(UUID.randomUUID().toString());
        }
        store.put(entity.getTrackingId(), entity);
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
    public Mono<AlbumReleaseTrackingEntity> findById(String id) {
        return Mono.justOrEmpty(store.get(id));
    }

    @Override
    public Flux<AlbumReleaseTrackingEntity> findAll() {
        return Flux.fromIterable(store.values());
    }

    @Override
    public Flux<AlbumReleaseTrackingEntity> findAllById(Flux<String> ids) {
        return ids.flatMap(this::findById);
    }

    @Override
    public Flux<AlbumReleaseTrackingEntity> findAllById(Collection<String> ids) {
        return Flux.fromIterable(ids).flatMap(this::findById);
    }

    @Override
    public Mono<Void> deleteById(String id) {

        store.remove(id);

        return Mono.empty();
    }

    @Override
    public Mono<Void> deleteAll() {
        store.clear();
        return Mono.empty();
    }
}
