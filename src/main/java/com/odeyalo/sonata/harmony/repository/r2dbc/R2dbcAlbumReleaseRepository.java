package com.odeyalo.sonata.harmony.repository.r2dbc;

import com.odeyalo.sonata.harmony.entity.AlbumReleaseEntity;
import com.odeyalo.sonata.harmony.repository.AlbumReleaseRepository;
import com.odeyalo.sonata.harmony.repository.r2dbc.delegate.R2dbcAlbumReleaseRepositoryDelegate;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Collection;

@Component
public class R2dbcAlbumReleaseRepository implements AlbumReleaseRepository {
    private final R2dbcAlbumReleaseRepositoryDelegate delegate;

    public R2dbcAlbumReleaseRepository(R2dbcAlbumReleaseRepositoryDelegate delegate) {
        this.delegate = delegate;
    }


    @Override
    public Mono<AlbumReleaseEntity> save(AlbumReleaseEntity albumReleaseEntity) {
        return delegate.save(albumReleaseEntity);
    }

    @Override
    public Flux<AlbumReleaseEntity> saveAll(Flux<AlbumReleaseEntity> entities) {
        return delegate.saveAll(entities);
    }

    @Override
    public Flux<AlbumReleaseEntity> saveAll(Collection<AlbumReleaseEntity> entities) {
        return delegate.saveAll(entities);
    }

    @Override
    public Mono<AlbumReleaseEntity> findById(Long id) {
        return delegate.findById(id);
    }

    @Override
    public Flux<AlbumReleaseEntity> findAll() {
        return delegate.findAll();
    }

    @Override
    public Flux<AlbumReleaseEntity> findAllById(Flux<Long> ids) {
        return delegate.findAllById(ids);
    }

    @Override
    public Flux<AlbumReleaseEntity> findAllById(Collection<Long> ids) {
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
    @NotNull
    public Mono<AlbumReleaseEntity> updateAlbumDuration(@NotNull Long id, @Nullable Long duration) {
        return delegate.updateDuration(id, duration);
    }
}
