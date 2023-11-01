package com.odeyalo.sonata.harmony.repository.r2dbc;

import com.odeyalo.sonata.harmony.entity.AlbumCoverImageEntity;
import com.odeyalo.sonata.harmony.repository.AlbumCoverImageRepository;
import com.odeyalo.sonata.harmony.repository.r2dbc.delegate.R2dbcAlbumCoverImageRepositoryDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Collection;

@Component
public class R2dbcAlbumCoverImageRepository implements AlbumCoverImageRepository {
    private final R2dbcAlbumCoverImageRepositoryDelegate delegate;

    @Autowired
    public R2dbcAlbumCoverImageRepository(R2dbcAlbumCoverImageRepositoryDelegate delegate) {
        this.delegate = delegate;
    }

    @Override
    public Mono<AlbumCoverImageEntity> findByAlbumId(Long albumId) {
        return delegate.findByAlbumId(albumId);
    }

    @Override
    public Mono<AlbumCoverImageEntity> save(AlbumCoverImageEntity albumCoverImageEntity) {
        return delegate.save(albumCoverImageEntity);
    }

    @Override
    public Flux<AlbumCoverImageEntity> saveAll(Flux<AlbumCoverImageEntity> flux) {
        return delegate.saveAll(flux);
    }

    @Override
    public Flux<AlbumCoverImageEntity> saveAll(Collection<AlbumCoverImageEntity> flux) {
        return delegate.saveAll(flux);
    }

    @Override
    public Mono<AlbumCoverImageEntity> findById(Long id) {
        return delegate.findById(id);
    }

    @Override
    public Flux<AlbumCoverImageEntity> findAll() {
        return delegate.findAll();
    }

    @Override
    public Flux<AlbumCoverImageEntity> findAllById(Flux<Long> ids) {
        return delegate.findAllById(ids);
    }

    @Override
    public Flux<AlbumCoverImageEntity> findAllById(Collection<Long> ids) {
        return delegate.findAllById(ids);
    }

    @Override
    public Mono<Void> deleteById(Long id) {
        return delegate.deleteById(id);
    }

    @Override
    public Mono<Void> deleteAll() {
        return delegate.deleteAll();
    }
}
