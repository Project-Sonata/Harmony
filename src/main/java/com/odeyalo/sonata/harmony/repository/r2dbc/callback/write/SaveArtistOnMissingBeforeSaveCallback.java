package com.odeyalo.sonata.harmony.repository.r2dbc.callback.write;

import com.odeyalo.sonata.harmony.entity.ArtistContainerEntity;
import com.odeyalo.sonata.harmony.entity.ArtistContainerHolder;
import com.odeyalo.sonata.harmony.entity.ArtistEntity;
import com.odeyalo.sonata.harmony.repository.ArtistRepository;
import org.jetbrains.annotations.NotNull;
import org.reactivestreams.Publisher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.r2dbc.mapping.OutboundRow;
import org.springframework.data.r2dbc.mapping.event.BeforeSaveCallback;
import org.springframework.data.relational.core.sql.SqlIdentifier;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

/**
 * BeforeSaveCallback that saves the artists before saving the original entity.
 * Uses ArtistContainerHolder as input to re-use the code for different entities
 */
@Component
public class SaveArtistOnMissingBeforeSaveCallback implements BeforeSaveCallback<ArtistContainerHolder> {
    private final ArtistRepository artistRepository;

    @Autowired
    public SaveArtistOnMissingBeforeSaveCallback(@Lazy ArtistRepository artistRepository) {
        this.artistRepository = artistRepository;
    }

    @Override
    @NotNull
    public Publisher<ArtistContainerHolder> onBeforeSave(@NotNull ArtistContainerHolder entity,
                                                         @NotNull OutboundRow row,
                                                         @NotNull SqlIdentifier table) {
        return Flux.fromIterable(entity.getArtists())
                .flatMap(this::getOrSaveArtistEntity)
                .collectList()
                .map(artists -> updateArtistsContainerHolder(entity, artists));
    }

    @NotNull
    private Mono<ArtistEntity> getOrSaveArtistEntity(@NotNull ArtistEntity entity) {
        return artistRepository
                .findBySonataId(entity.getSonataId())
                .switchIfEmpty(artistRepository.save(entity));
    }

    @NotNull
    private ArtistContainerHolder updateArtistsContainerHolder(@NotNull ArtistContainerHolder artistContainerParent,
                                                               @NotNull List<ArtistEntity> savedArtistEntities) {

        artistContainerParent.setArtists(ArtistContainerEntity.multiple(savedArtistEntities));

        return artistContainerParent;
    }
}
