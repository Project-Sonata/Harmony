package com.odeyalo.sonata.harmony.repository.memory;

import com.odeyalo.sonata.harmony.entity.AlbumReleaseEntity;
import com.odeyalo.sonata.harmony.repository.AlbumReleaseRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

/**
 * In memory implementation of AlbumReleaseRepository
 *
 * Used for tests and local development
 */
public class InMemoryAlbumReleaseRepository implements AlbumReleaseRepository {
    private final Map<Long, AlbumReleaseEntity> albumReleases = new HashMap<>();
    private final AtomicLong idGenerator = new AtomicLong();

    @Override
    public Mono<AlbumReleaseEntity> save(AlbumReleaseEntity albumReleaseEntity) {
        if (albumReleaseEntity.getId() == null) {
            albumReleaseEntity.setId(idGenerator.incrementAndGet());
        }
        albumReleases.put(albumReleaseEntity.getId(), albumReleaseEntity);
        return Mono.just(albumReleaseEntity);
    }

    @Override
    public Flux<AlbumReleaseEntity> saveAll(Flux<AlbumReleaseEntity> flux) {
        return flux.doOnNext(albumReleaseEntity -> albumReleases.put(albumReleaseEntity.getId(), albumReleaseEntity));
    }

    @Override
    public Flux<AlbumReleaseEntity> saveAll(Collection<AlbumReleaseEntity> collection) {
        return Flux.fromIterable(collection)
                .flatMap(this::save);
    }

    @Override
    public Mono<AlbumReleaseEntity> findById(Long id) {
        AlbumReleaseEntity albumReleaseEntity = albumReleases.get(id);
        return Mono.justOrEmpty(albumReleaseEntity);
    }

    @Override
    public Flux<AlbumReleaseEntity> findAll() {
        return Flux.fromIterable(albumReleases.values());
    }

    @Override
    public Flux<AlbumReleaseEntity> findAllById(Flux<Long> ids) {
        return ids.flatMap(this::findById);
    }

    @Override
    public Flux<AlbumReleaseEntity> findAllById(Collection<Long> ids) {
        return Flux.fromIterable(ids).flatMap(this::findById);
    }

    @Override
    public Mono<Void> deleteById(Long id) {
        albumReleases.remove(id);
        return Mono.empty();
    }

    @Override
    public Mono<Void> deleteAll() {
        albumReleases.clear();
        return Mono.empty();
    }
}

