package com.odeyalo.sonata.harmony.service;

import com.odeyalo.sonata.harmony.model.AlbumRelease;
import com.odeyalo.sonata.harmony.model.Track;
import com.odeyalo.sonata.harmony.model.TrackContainer;
import com.odeyalo.sonata.harmony.service.album.TrackUploadTarget;
import com.odeyalo.sonata.harmony.service.album.UploadAlbumReleaseInfo;
import org.jetbrains.annotations.NotNull;
import org.springframework.http.codec.multipart.FilePart;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

public class AlbumReleaseUploaderImpl implements AlbumReleaseUploader {

    @Override
    @NotNull
    public Mono<AlbumRelease> uploadAlbumRelease(@NotNull UploadAlbumReleaseInfo info,
                                                 @NotNull Flux<FilePart> tracks,
                                                 @NotNull Mono<FilePart> coverImage) {


        List<Track> convertedTracks = info.getTracks().stream().map(AlbumReleaseUploaderImpl::convertToTrack).toList();

        return Mono.just(AlbumRelease.builder()
                .id("hello")
                .name(info.getAlbumName())
                .totalTracksCount(info.getTotalTracksCount())
                .albumType(info.getAlbumType())
                .artists(info.getArtists())
                .tracks(TrackContainer.fromCollection(convertedTracks))
                .build());
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
