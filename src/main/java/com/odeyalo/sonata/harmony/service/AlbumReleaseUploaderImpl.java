package com.odeyalo.sonata.harmony.service;

import com.odeyalo.sonata.harmony.entity.*;
import com.odeyalo.sonata.harmony.model.*;
import com.odeyalo.sonata.harmony.repository.AlbumReleaseRepository;
import com.odeyalo.sonata.harmony.service.album.TrackUploadTarget;
import com.odeyalo.sonata.harmony.service.album.UploadAlbumReleaseInfo;
import com.odeyalo.sonata.harmony.service.album.stage.AlbumReleaseUploadingStage;
import com.odeyalo.sonata.harmony.support.converter.ArtistContainerEntityConverter;
import org.jetbrains.annotations.NotNull;
import org.springframework.http.codec.multipart.FilePart;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

public class AlbumReleaseUploaderImpl implements AlbumReleaseUploader {
    private final AlbumReleaseRepository albumRepository;
    private final List<AlbumReleaseUploadingStage> steps;
    private final ArtistContainerEntityConverter artistContainerEntityConverter;

    public AlbumReleaseUploaderImpl(AlbumReleaseRepository albumRepository,
                                    List<AlbumReleaseUploadingStage> steps,
                                    ArtistContainerEntityConverter artistContainerEntityConverter) {
        this.steps = steps;
        this.albumRepository = albumRepository;
        this.artistContainerEntityConverter = artistContainerEntityConverter;
    }

    @Override
    @NotNull
    public Mono<AlbumRelease> uploadAlbumRelease(@NotNull UploadAlbumReleaseInfo info,
                                                 @NotNull Flux<FilePart> tracks,
                                                 @NotNull Mono<FilePart> coverImage) {
        List<Track> convertedTracks = info.getTracks().stream().map(AlbumReleaseUploaderImpl::convertToTrack).toList();


        AlbumReleaseEntity.AlbumReleaseEntityBuilder<?, ?> initialBuilder = toAlbumReleaseEntityBuilder(info);
        initialBuilder.tracks(TrackContainerEntity.empty());

        return Flux.fromIterable(steps)
                .flatMap(stage -> stage.processAlbumUpload(info, initialBuilder, tracks, coverImage))
                .reduce((prevProcessed, updatedBuilder) -> updatedBuilder)
                .flatMap(builder -> albumRepository.save(builder.build())
                        .map(saved -> buildAlbumRelease(info, convertedTracks, saved)));
    }

    private static AlbumRelease buildAlbumRelease(@NotNull UploadAlbumReleaseInfo info, List<Track> convertedTracks, AlbumReleaseEntity saved) {
        return AlbumRelease.builder()
                .id(saved.getId())
                .name(saved.getAlbumName())
                .totalTracksCount(saved.getTotalTracksCount())
                .albumType(saved.getAlbumType())
                .artists(info.getArtists())
                .releaseDate(saved.getReleaseDate())
                .tracks(TrackContainer.fromCollection(convertedTracks))
                .images(ImageContainer.fromCollection(
                        saved.getImages().getItems().stream().map(entity -> Image.urlOnly(entity.getUrl())).toList()
                ))
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

    private static Track convertToTrack(TrackUploadTarget target) {
        return Track.builder()
                .trackName(target.getTrackName())
                .discNumber(target.getDiscNumber())
                .index(target.getIndex())
                .durationMs(target.getDurationMs())
                .hasLyrics(target.hasLyrics())
                .isExplicit(target.isExplicit())
                .artists(target.getArtists())
                .build();
    }
}
