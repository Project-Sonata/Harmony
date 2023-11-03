package com.odeyalo.sonata.harmony.service;

import com.odeyalo.sonata.harmony.entity.*;
import com.odeyalo.sonata.harmony.model.*;
import com.odeyalo.sonata.harmony.repository.AlbumReleaseRepository;
import com.odeyalo.sonata.harmony.service.album.TrackUploadTarget;
import com.odeyalo.sonata.harmony.service.album.UploadAlbumReleaseInfo;
import com.odeyalo.sonata.harmony.service.album.support.AlbumCoverImageUploader;
import com.odeyalo.sonata.harmony.service.album.support.AlbumTracksUploader;
import com.odeyalo.sonata.harmony.service.upload.FileUploadTarget;
import org.jetbrains.annotations.NotNull;
import org.springframework.http.codec.multipart.FilePart;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

public class AlbumReleaseUploaderImpl implements AlbumReleaseUploader {
    private final AlbumReleaseRepository albumRepository;
    private final AlbumCoverImageUploader albumCoverImageUploader;
    private final AlbumTracksUploader albumTracksUploader;

    // validation
    // Upload image stage
    // upload tracks stage

    public AlbumReleaseUploaderImpl(AlbumReleaseRepository albumRepository, AlbumCoverImageUploader albumCoverImageUploader,
                                    AlbumTracksUploader albumTracksUploader) {

        this.albumRepository = albumRepository;
        this.albumCoverImageUploader = albumCoverImageUploader;
        this.albumTracksUploader = albumTracksUploader;
    }


    @Override
    @NotNull
    public Mono<AlbumRelease> uploadAlbumRelease(@NotNull UploadAlbumReleaseInfo info,
                                                 @NotNull Flux<FilePart> tracks,
                                                 @NotNull Mono<FilePart> coverImage) {

        List<Track> convertedTracks = info.getTracks().stream().map(AlbumReleaseUploaderImpl::convertToTrack).toList();

        ArtistContainerEntity artists = toArtistContainer(info);

        return albumTracksUploader.uploadTracks(tracks.map(f -> FileUploadTarget.of(f.filename(), f)))
                .map(uploadedFile -> {
                    TrackUploadTarget target = info.getTracks().findByTrackFileId(uploadedFile.getFileId()).get();
                    return toSimplifiedTrack(artists, target, uploadedFile.getFileUrl().getUrl());
                })
                .collectList()
                .map(trackEntities -> toAlbumReleaseEntity(info, artists, trackEntities))
                .flatMap(albumRepository::save)
                .map(saved -> buildAlbumRelease(info, convertedTracks, saved));


    }

    private static ArtistContainerEntity toArtistContainer(@NotNull UploadAlbumReleaseInfo info) {
        return ArtistContainerEntity.multiple(info.getArtists().stream().map(AlbumReleaseUploaderImpl::convertToArtistEntity)
                .toList());
    }

    private static ArtistEntity convertToArtistEntity(Artist artist) {
        return ArtistEntity.builder().sonataId(artist.getSonataId())
                .name(artist.getName())
                .build();
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
                .images(ImageContainer.one(Image.urlOnly("https://cdn.sonata.com/i/image")))
                .build();
    }

    private static AlbumReleaseEntity toAlbumReleaseEntity(@NotNull UploadAlbumReleaseInfo info, ArtistContainerEntity artists, List<SimplifiedTrackEntity> trackObjects) {
        return AlbumReleaseEntity.builder()
                .albumName(info.getAlbumName())
                .releaseDate(info.getReleaseDate())
                .totalTracksCount(info.getTotalTracksCount())
                .releaseDatePrecision("YEAR")
                .albumType(info.getAlbumType())
                .artists(artists)
                .images(AlbumCoverImageContainerEntity.empty())
                .tracks(TrackContainerEntity.multiple(trackObjects))
                .build();
    }

    private static SimplifiedTrackEntity toSimplifiedTrack(ArtistContainerEntity artists, TrackUploadTarget trackUploadTarget, String url) {
        return SimplifiedTrackEntity.builder()
                .name(trackUploadTarget.getTrackName())
                .artists(artists)
                .durationMs(trackUploadTarget.getDurationMs())
                .discNumber(trackUploadTarget.getDiscNumber())
                .index(trackUploadTarget.getIndex())
                .explicit(trackUploadTarget.isExplicit())
                .hasLyrics(trackUploadTarget.hasLyrics())
                .trackUrl(url)
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
