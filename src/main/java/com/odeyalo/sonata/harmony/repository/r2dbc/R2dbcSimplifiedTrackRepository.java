package com.odeyalo.sonata.harmony.repository.r2dbc;

import com.odeyalo.sonata.harmony.entity.SimplifiedTrackEntity;
import com.odeyalo.sonata.harmony.entity.TrackEntity;
import com.odeyalo.sonata.harmony.repository.SimplifiedTrackRepository;
import com.odeyalo.sonata.harmony.repository.r2dbc.delegate.R2dbcSimplifiedTrackRepositoryDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Collection;

@Component
public class R2dbcSimplifiedTrackRepository implements SimplifiedTrackRepository {
    private final R2dbcSimplifiedTrackRepositoryDelegate delegate;

    @Autowired
    public R2dbcSimplifiedTrackRepository(R2dbcSimplifiedTrackRepositoryDelegate delegate) {
        this.delegate = delegate;
    }

    @Override
    public Mono<SimplifiedTrackEntity> save(SimplifiedTrackEntity simplifiedTrackEntity) {
        return delegate.save(simplifiedTrackEntity);
    }

    @Override
    public Flux<SimplifiedTrackEntity> saveAll(Flux<SimplifiedTrackEntity> flux) {
        return delegate.saveAll(flux);
    }

    @Override
    public Flux<SimplifiedTrackEntity> saveAll(Collection<SimplifiedTrackEntity> flux) {
        return delegate.saveAll(flux);
    }

    @Override
    public Mono<SimplifiedTrackEntity> findById(Long id) {
        return delegate.findById(id);
    }

    @Override
    public Flux<SimplifiedTrackEntity> findAll() {
        return delegate.findAll();
    }

    @Override
    public Flux<SimplifiedTrackEntity> findAllById(Flux<Long> ids) {
        return delegate.findAllById(ids);
    }

    @Override
    public Flux<SimplifiedTrackEntity> findAllById(Collection<Long> ids) {
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

    @Override
    public Flux<SimplifiedTrackEntity> findAllByAlbumId(Long id) {
        return delegate.findAllByAlbumId(id);
    }
}
