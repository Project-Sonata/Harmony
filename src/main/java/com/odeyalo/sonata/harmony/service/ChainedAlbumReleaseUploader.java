package com.odeyalo.sonata.harmony.service;

import com.odeyalo.sonata.harmony.entity.AlbumCoverImageContainerEntity;
import com.odeyalo.sonata.harmony.entity.AlbumReleaseEntity;
import com.odeyalo.sonata.harmony.entity.AlbumReleaseEntity.AlbumReleaseEntityBuilder;
import com.odeyalo.sonata.harmony.entity.ArtistContainerEntity;
import com.odeyalo.sonata.harmony.entity.TrackContainerEntity;
import com.odeyalo.sonata.harmony.model.*;
import com.odeyalo.sonata.harmony.repository.AlbumReleaseRepository;
import com.odeyalo.sonata.harmony.service.album.UploadAlbumReleaseInfo;
import com.odeyalo.sonata.harmony.service.album.stage.AlbumReleaseUploadingStage;
import com.odeyalo.sonata.harmony.support.converter.*;
import org.jetbrains.annotations.NotNull;
import org.springframework.http.codec.multipart.FilePart;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

@Component
public class ChainedAlbumReleaseUploader implements AlbumReleaseUploader {
    private final AlbumReleaseRepository albumRepository;
    private final List<AlbumReleaseUploadingStage> stages;
    private final ArtistContainerEntityConverter artistContainerEntityConverter;
    private final AlbumReleaseConverter albumReleaseConverter;

    public ChainedAlbumReleaseUploader(AlbumReleaseRepository albumRepository,
                                       List<AlbumReleaseUploadingStage> stages,
                                       ArtistContainerEntityConverter artistContainerEntityConverter,
                                       AlbumReleaseConverter albumReleaseConverter) {
        this.stages = stages;
        this.albumRepository = albumRepository;
        this.artistContainerEntityConverter = artistContainerEntityConverter;
        this.albumReleaseConverter = albumReleaseConverter;
    }

    @Override
    @NotNull
    public Mono<AlbumRelease> uploadAlbumRelease(@NotNull UploadAlbumReleaseInfo info,
                                                 @NotNull Flux<FilePart> tracks,
                                                 @NotNull Mono<FilePart> coverImage) {

        AlbumReleaseEntityBuilder<?, ?> initialBuilder = createInitialBuilder(info);

        return Flux.fromIterable(stages)
                .flatMap(stage -> stage.processAlbumUpload(info, initialBuilder, tracks, coverImage))
                .reduce((prevProcessed, updatedBuilder) -> updatedBuilder)
                .flatMap(this::saveAndConvert);
    }

    @NotNull
    private Mono<AlbumRelease> saveAndConvert(AlbumReleaseEntityBuilder<?, ?> builder) {
        return albumRepository.save(builder.build())
                .map(albumReleaseConverter::toAlbumRelease);
    }

    private AlbumReleaseEntityBuilder<?, ?> createInitialBuilder(@NotNull UploadAlbumReleaseInfo info) {
        ArtistContainerEntity artists = artistContainerEntityConverter.toArtistContainerEntity(info.getArtists());

        return AlbumReleaseEntity.builder()
                .albumName(info.getAlbumName())
                .releaseDate(info.getReleaseDate())
                .totalTracksCount(info.getTotalTracksCount())
                .releaseDate(info.getReleaseDate())
                .albumType(info.getAlbumType())
                .artists(artists)
                .durationMs(10000L) // TODO: CHANGE ME
                .images(AlbumCoverImageContainerEntity.empty())
                .tracks(TrackContainerEntity.empty());
    }
}
