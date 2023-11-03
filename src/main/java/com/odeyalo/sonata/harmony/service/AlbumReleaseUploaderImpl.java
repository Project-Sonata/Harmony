package com.odeyalo.sonata.harmony.service;

import com.odeyalo.sonata.harmony.entity.AlbumCoverImageContainerEntity;
import com.odeyalo.sonata.harmony.entity.AlbumReleaseEntity;
import com.odeyalo.sonata.harmony.entity.ArtistContainerEntity;
import com.odeyalo.sonata.harmony.entity.TrackContainerEntity;
import com.odeyalo.sonata.harmony.model.*;
import com.odeyalo.sonata.harmony.repository.AlbumReleaseRepository;
import com.odeyalo.sonata.harmony.service.album.UploadAlbumReleaseInfo;
import com.odeyalo.sonata.harmony.service.album.stage.AlbumReleaseUploadingStage;
import com.odeyalo.sonata.harmony.support.converter.ArtistContainerConverter;
import com.odeyalo.sonata.harmony.support.converter.ArtistContainerEntityConverter;
import com.odeyalo.sonata.harmony.support.converter.ImageContainerConverter;
import com.odeyalo.sonata.harmony.support.converter.TrackConverter;
import org.jetbrains.annotations.NotNull;
import org.springframework.http.codec.multipart.FilePart;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

public class AlbumReleaseUploaderImpl implements AlbumReleaseUploader {
    private final AlbumReleaseRepository albumRepository;
    private final List<AlbumReleaseUploadingStage> steps;
    private final ArtistContainerEntityConverter artistContainerEntityConverter;
    private final TrackConverter trackConverter;
    private final ImageContainerConverter imageContainerConverter;
    private final ArtistContainerConverter artistContainerConverter;

    public AlbumReleaseUploaderImpl(AlbumReleaseRepository albumRepository,
                                    List<AlbumReleaseUploadingStage> steps,
                                    ArtistContainerEntityConverter artistContainerEntityConverter,
                                    TrackConverter trackConverter,
                                    ImageContainerConverter imageContainerConverter,
                                    ArtistContainerConverter artistContainerConverter) {
        this.steps = steps;
        this.albumRepository = albumRepository;
        this.artistContainerEntityConverter = artistContainerEntityConverter;
        this.trackConverter = trackConverter;
        this.imageContainerConverter = imageContainerConverter;
        this.artistContainerConverter = artistContainerConverter;
    }

    @Override
    @NotNull
    public Mono<AlbumRelease> uploadAlbumRelease(@NotNull UploadAlbumReleaseInfo info,
                                                 @NotNull Flux<FilePart> tracks,
                                                 @NotNull Mono<FilePart> coverImage) {

        List<Track> convertedTracks = info.getTracks().stream().map(trackConverter::toTrack).toList();

        AlbumReleaseEntity.AlbumReleaseEntityBuilder<?, ?> initialBuilder = toAlbumReleaseEntityBuilder(info);
        initialBuilder.tracks(TrackContainerEntity.empty());

        return Flux.fromIterable(steps)
                .flatMap(stage -> stage.processAlbumUpload(info, initialBuilder, tracks, coverImage))
                .reduce((prevProcessed, updatedBuilder) -> updatedBuilder)
                .flatMap(builder -> albumRepository.save(builder.build())
                        .map(saved -> buildAlbumRelease(convertedTracks, saved)));
    }

    private AlbumRelease buildAlbumRelease(List<Track> convertedTracks, AlbumReleaseEntity saved) {
        ArtistContainer artists = artistContainerConverter.toArtistContainer(saved.getArtists());
        ImageContainer images = imageContainerConverter.toImageContainer(saved.getImages());

        return AlbumRelease.builder()
                .id(saved.getId())
                .name(saved.getAlbumName())
                .totalTracksCount(saved.getTotalTracksCount())
                .albumType(saved.getAlbumType())
                .artists(artists)
                .releaseDate(saved.getReleaseDate())
                .tracks(TrackContainer.fromCollection(convertedTracks))
                .images(images)
                .build();
    }

    private AlbumReleaseEntity.AlbumReleaseEntityBuilder<?, ?> toAlbumReleaseEntityBuilder(@NotNull UploadAlbumReleaseInfo info) {
        ArtistContainerEntity artists = artistContainerEntityConverter.toArtistContainerEntity(info.getArtists());

        return AlbumReleaseEntity.builder()
                .albumName(info.getAlbumName())
                .releaseDate(info.getReleaseDate())
                .totalTracksCount(info.getTotalTracksCount())
                .releaseDate(info.getReleaseDate())
                .albumType(info.getAlbumType())
                .artists(artists)
                .images(AlbumCoverImageContainerEntity.empty())
                .tracks(TrackContainerEntity.empty());
    }
}
