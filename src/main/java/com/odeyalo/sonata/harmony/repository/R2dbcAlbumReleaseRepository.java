package com.odeyalo.sonata.harmony.repository;

import com.odeyalo.sonata.harmony.entity.AlbumReleaseEntity;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Collection;

public class R2dbcAlbumReleaseRepository implements AlbumReleaseRepository {

    @Override
    public Mono<AlbumReleaseEntity> save(AlbumReleaseEntity albumReleaseEntity) {
        return null;
    }

    @Override
    public Flux<AlbumReleaseEntity> saveAll(Flux<AlbumReleaseEntity> flux) {
        return null;
    }

    @Override
    public Flux<AlbumReleaseEntity> saveAll(Collection<AlbumReleaseEntity> flux) {
        return null;
    }

    @Override
    public Mono<AlbumReleaseEntity> findById(Long id) {
        return null;
    }

    @Override
    public Flux<AlbumReleaseEntity> findAll() {
        return null;
    }

    @Override
    public Flux<AlbumReleaseEntity> findAllById(Flux<Long> ids) {
        return null;
    }

    @Override
    public Flux<AlbumReleaseEntity> findAllById(Collection<Long> ids) {
        return null;
    }

    @Override
    public Mono<Void> deleteById(Long aLong) {
        return null;
    }

    @Override
    public Mono<Void> deleteAll() {
        return null;
    }
}
