package com.odeyalo.sonata.harmony.repository.r2dbc;

import com.odeyalo.sonata.harmony.entity.TrackEntity;
import com.odeyalo.sonata.harmony.repository.TrackRepository;
import com.odeyalo.sonata.harmony.repository.r2dbc.delegate.R2dbcTrackRepositoryDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Collection;

@Component
public class R2dbcTrackRepository implements TrackRepository {
    private final R2dbcTrackRepositoryDelegate delegate;

    @Autowired
    public R2dbcTrackRepository(R2dbcTrackRepositoryDelegate delegate) {
        this.delegate = delegate;
    }

    @Override
    public Mono<TrackEntity> save(TrackEntity trackEntity) {
        return delegate.save(trackEntity);
    }

    @Override
    public Flux<TrackEntity> saveAll(Flux<TrackEntity> entities) {
        return delegate.saveAll(entities);
    }

    @Override
    public Flux<TrackEntity> saveAll(Collection<TrackEntity> entities) {
        return delegate.saveAll(entities);
    }

    @Override
    public Mono<TrackEntity> findById(Long id) {
        return delegate.findById(id);
    }

    @Override
    public Flux<TrackEntity> findAll() {
        return delegate.findAll();
    }

    @Override
    public Flux<TrackEntity> findAllById(Flux<Long> ids) {
        return delegate.findAll();
    }

    @Override
    public Flux<TrackEntity> findAllById(Collection<Long> ids) {
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
    public Flux<TrackEntity> findAllByAlbumId(Long id) {
        return delegate.findAllByAlbumId(id);
    }
}
