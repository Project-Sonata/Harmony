package com.odeyalo.sonata.harmony.repository.r2dbc;

import com.odeyalo.sonata.harmony.entity.SimplifiedAlbumEntity;
import com.odeyalo.sonata.harmony.repository.SimplifiedAlbumRepository;
import com.odeyalo.sonata.harmony.repository.r2dbc.delegate.R2dbcSimplifiedAlbumRepositoryDelegate;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Collection;

@Component
public class R2dbcSimplifiedAlbumRepository implements SimplifiedAlbumRepository {
    private final R2dbcSimplifiedAlbumRepositoryDelegate delegate;

    public R2dbcSimplifiedAlbumRepository(R2dbcSimplifiedAlbumRepositoryDelegate delegate) {
        this.delegate = delegate;
    }

    @Override
    public Mono<SimplifiedAlbumEntity> save(SimplifiedAlbumEntity simplifiedAlbumEntity) {
        return delegate.save(simplifiedAlbumEntity);
    }

    @Override
    public Flux<SimplifiedAlbumEntity> saveAll(Flux<SimplifiedAlbumEntity> flux) {
        return delegate.saveAll(flux);
    }

    @Override
    public Flux<SimplifiedAlbumEntity> saveAll(Collection<SimplifiedAlbumEntity> flux) {
        return delegate.saveAll(flux);
    }

    @Override
    public Mono<SimplifiedAlbumEntity> findById(Long id) {
        return delegate.findById(id);
    }

    @Override
    public Flux<SimplifiedAlbumEntity> findAll() {
        return delegate.findAll();
    }

    @Override
    public Flux<SimplifiedAlbumEntity> findAllById(Flux<Long> ids) {
        return delegate.findAllById(ids);
    }

    @Override
    public Flux<SimplifiedAlbumEntity> findAllById(Collection<Long> ids) {
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
