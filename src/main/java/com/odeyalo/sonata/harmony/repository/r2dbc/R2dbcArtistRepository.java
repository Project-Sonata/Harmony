package com.odeyalo.sonata.harmony.repository.r2dbc;

import com.odeyalo.sonata.harmony.entity.ArtistEntity;
import com.odeyalo.sonata.harmony.repository.ArtistRepository;
import com.odeyalo.sonata.harmony.repository.r2dbc.delegate.R2dbcArtistRepositoryDelegate;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Collection;

@Component
public class R2dbcArtistRepository implements ArtistRepository {
    private final R2dbcArtistRepositoryDelegate delegate;

    public R2dbcArtistRepository(R2dbcArtistRepositoryDelegate delegate) {
        this.delegate = delegate;
    }

    @Override
    public Mono<ArtistEntity> findBySonataId(String sonataId) {
        return delegate.findBySonataId(sonataId);
    }

    @Override
    public Mono<ArtistEntity> save(ArtistEntity artistEntity) {
        return delegate.save(artistEntity);
    }

    @Override
    public Flux<ArtistEntity> saveAll(Flux<ArtistEntity> entities) {
        return delegate.saveAll(entities);
    }

    @Override
    public Flux<ArtistEntity> saveAll(Collection<ArtistEntity> entities) {
        return delegate.saveAll(entities);
    }

    @Override
    public Mono<ArtistEntity> findById(Long id) {
        return delegate.findById(id);
    }

    @Override
    public Flux<ArtistEntity> findAll() {
        return delegate.findAll();
    }

    @Override
    public Flux<ArtistEntity> findAllById(Flux<Long> ids) {
        return delegate.findAllById(ids);
    }

    @Override
    public Flux<ArtistEntity> findAllById(Collection<Long> ids) {
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
