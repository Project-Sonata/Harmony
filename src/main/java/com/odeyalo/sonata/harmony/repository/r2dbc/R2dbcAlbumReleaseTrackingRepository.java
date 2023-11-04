package com.odeyalo.sonata.harmony.repository.r2dbc;

import com.odeyalo.sonata.harmony.entity.AlbumReleaseTrackingEntity;
import com.odeyalo.sonata.harmony.repository.AlbumReleaseTrackingRepository;
import com.odeyalo.sonata.harmony.repository.r2dbc.delegate.R2dbcAlbumReleaseTrackingRepositoryDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Collection;

@Component
public class R2dbcAlbumReleaseTrackingRepository implements AlbumReleaseTrackingRepository {
    private final R2dbcAlbumReleaseTrackingRepositoryDelegate delegate;

    @Autowired
    public R2dbcAlbumReleaseTrackingRepository(R2dbcAlbumReleaseTrackingRepositoryDelegate delegate) {
        this.delegate = delegate;
    }

    @Override
    public Mono<AlbumReleaseTrackingEntity> save(AlbumReleaseTrackingEntity albumReleaseTrackingEntity) {
        return delegate.save(albumReleaseTrackingEntity);
    }

    @Override
    public Flux<AlbumReleaseTrackingEntity> saveAll(Flux<AlbumReleaseTrackingEntity> flux) {
        return delegate.saveAll(flux);
    }

    @Override
    public Flux<AlbumReleaseTrackingEntity> saveAll(Collection<AlbumReleaseTrackingEntity> flux) {
        return delegate.saveAll(flux);
    }

    @Override
    public Mono<AlbumReleaseTrackingEntity> findById(String id) {
        return delegate.findById(id);
    }

    @Override
    public Flux<AlbumReleaseTrackingEntity> findAll() {
        return delegate.findAll();
    }

    @Override
    public Flux<AlbumReleaseTrackingEntity> findAllById(Flux<String> ids) {
        return delegate.findAllById(ids);
    }

    @Override
    public Flux<AlbumReleaseTrackingEntity> findAllById(Collection<String> ids) {
        return delegate.findAllById(ids);
    }

    @Override
    public Mono<Void> deleteById(String id) {
        return delegate.deleteById(id);
    }

    @Override
    public Mono<Void> deleteAll() {
        return delegate.deleteAll();
    }
}
