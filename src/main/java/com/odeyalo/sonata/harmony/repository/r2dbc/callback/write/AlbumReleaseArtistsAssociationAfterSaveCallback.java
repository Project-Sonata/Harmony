package com.odeyalo.sonata.harmony.repository.r2dbc.callback.write;

import com.odeyalo.sonata.harmony.entity.AlbumArtistEntity;
import com.odeyalo.sonata.harmony.entity.ArtistEntity;
import com.odeyalo.sonata.harmony.entity.SimplifiedAlbumEntity;
import com.odeyalo.sonata.harmony.repository.ArtistRepository;
import com.odeyalo.sonata.harmony.repository.r2dbc.R2dbcAlbumArtistsRepository;
import org.jetbrains.annotations.NotNull;
import org.reactivestreams.Publisher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.r2dbc.mapping.OutboundRow;
import org.springframework.data.r2dbc.mapping.event.AfterSaveCallback;
import org.springframework.data.relational.core.sql.SqlIdentifier;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import static com.odeyalo.sonata.harmony.entity.AlbumArtistEntity.pairOf;

@Component
public class AlbumReleaseArtistsAssociationAfterSaveCallback implements AfterSaveCallback<SimplifiedAlbumEntity> {
    private final R2dbcAlbumArtistsRepository albumArtistsRepository;
    private final ArtistRepository artistRepository;

    @Autowired
    public AlbumReleaseArtistsAssociationAfterSaveCallback(@Lazy R2dbcAlbumArtistsRepository albumArtistsRepository,
                                                           @Lazy ArtistRepository artistRepository) {

        this.albumArtistsRepository = albumArtistsRepository;
        this.artistRepository = artistRepository;
    }

    @Override
    @NotNull
    public Publisher<SimplifiedAlbumEntity> onAfterSave(@NotNull SimplifiedAlbumEntity entity,
                                                     @NotNull OutboundRow outboundRow,
                                                     @NotNull SqlIdentifier table) {

        Long albumId = entity.getId();

        Flux<AlbumArtistEntity> albumArtists = Flux.fromIterable(entity.getArtists())
                .flatMap(this::getOrSaveArtist)
                .map(artist -> pairOf(albumId, artist.getId()));


        return albumArtistsRepository.saveAll(albumArtists)
                .then(Mono.just(entity));
    }

    @NotNull
    private Mono<ArtistEntity> getOrSaveArtist(ArtistEntity artistEntity) {
        return artistRepository.findBySonataId(artistEntity.getSonataId())
                .switchIfEmpty(artistRepository.save(artistEntity));
    }
}
