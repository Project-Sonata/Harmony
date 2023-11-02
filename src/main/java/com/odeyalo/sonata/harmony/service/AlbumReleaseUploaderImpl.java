package com.odeyalo.sonata.harmony.service;

import com.odeyalo.sonata.harmony.entity.*;
import com.odeyalo.sonata.harmony.model.*;
import com.odeyalo.sonata.harmony.repository.AlbumReleaseRepository;
import com.odeyalo.sonata.harmony.service.album.TrackUploadTarget;
import com.odeyalo.sonata.harmony.service.album.UploadAlbumReleaseInfo;
import com.odeyalo.sonata.harmony.service.album.support.AlbumCoverImageUploader;
import com.odeyalo.sonata.harmony.service.album.support.MockAlbumCoverImageUploader;
import org.jetbrains.annotations.NotNull;
import org.springframework.http.codec.multipart.FilePart;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

public class AlbumReleaseUploaderImpl implements AlbumReleaseUploader {
    private final AlbumReleaseRepository albumRepository;
    private final AlbumCoverImageUploader albumCoverImageUploader;

    public AlbumReleaseUploaderImpl(AlbumReleaseRepository albumRepository, AlbumCoverImageUploader albumCoverImageUploader) {

        this.albumRepository = albumRepository;
        this.albumCoverImageUploader = albumCoverImageUploader;
    }


    @Override
    @NotNull
    public Mono<AlbumRelease> uploadAlbumRelease(@NotNull UploadAlbumReleaseInfo info,
                                                 @NotNull Flux<FilePart> tracks,
                                                 @NotNull Mono<FilePart> coverImage) {


        List<Track> convertedTracks = info.getTracks().stream().map(AlbumReleaseUploaderImpl::convertToTrack).toList();
        ArtistContainerEntity artists = ArtistContainerEntity.multiple(info.getArtists()
                .stream().map(artist -> ArtistEntity.builder().sonataId(artist.getSonataId())
                        .name(artist.getName())
                        .build()).toList());

        List<SimplifiedTrackEntity> trackObjects = info.getTracks().stream()
                .map(trackUploadTarget -> toSimplifiedTrack(artists, trackUploadTarget))
                .toList();

        AlbumReleaseEntity releaseEntity = AlbumReleaseEntity.builder()
                .albumName(info.getAlbumName())
                .releaseDate(info.getReleaseDate())
                .totalTracksCount(info.getTotalTracksCount())
                .releaseDatePrecision("YEAR")
                .albumType(info.getAlbumType())
                .artists(artists)
                .images(AlbumCoverImageContainerEntity.empty())
                .tracks(TrackContainerEntity.multiple(trackObjects))
                .build();

        return albumRepository.save(releaseEntity)
                .flatMap(saved -> Mono.just(AlbumRelease.builder()
                        .id(saved.getId())
                        .name(saved.getAlbumName())
                        .totalTracksCount(saved.getTotalTracksCount())
                        .albumType(saved.getAlbumType())
                        .artists(info.getArtists())
                        .releaseDate(saved.getReleaseDate())
                        .tracks(TrackContainer.fromCollection(convertedTracks))
                        .images(ImageContainer.one(Image.urlOnly("https://cdn.sonata.com/i/image")))
                        .build()));
    }

    private static SimplifiedTrackEntity toSimplifiedTrack(ArtistContainerEntity artists, TrackUploadTarget trackUploadTarget) {
        return SimplifiedTrackEntity.builder()
                .name(trackUploadTarget.getTrackName())
                .artists(artists)
                .durationMs(trackUploadTarget.getDurationMs())
                .discNumber(trackUploadTarget.getDiscNumber())
                .index(trackUploadTarget.getIndex())
                .explicit(trackUploadTarget.isExplicit())
                .hasLyrics(trackUploadTarget.hasLyrics())
                .build();
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
