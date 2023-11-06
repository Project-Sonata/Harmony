package com.odeyalo.sonata.harmony.api.rest;

import com.odeyalo.sonata.harmony.entity.TrackArtistEntity;
import com.odeyalo.sonata.harmony.repository.*;
import com.odeyalo.sonata.harmony.repository.r2dbc.R2dbcAlbumArtistsRepository;
import com.odeyalo.sonata.harmony.repository.r2dbc.R2dbcTrackArtistRepository;
import com.odeyalo.sonata.harmony.service.album.tracking.AlbumReleaseUploadTrackingService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/qa")
@RequiredArgsConstructor
public class QaController {
    private final R2dbcAlbumArtistsRepository albumArtistsRepository;
    private final AlbumCoverImageRepository albumCoverImageRepository;
    private final AlbumReleaseTrackingRepository trackingRepository;
    private final AlbumReleaseRepository albumReleaseRepository;
    private final ArtistRepository artistRepository;
    private final R2dbcTrackArtistRepository trackArtistRepository;
    private final TrackRepository trackRepository;

    @DeleteMapping("/clear")
    public Mono<Void> clearEverything() {
        return albumArtistsRepository.deleteAll()
                .then(albumCoverImageRepository.deleteAll())
                .then(trackingRepository.deleteAll())
                .then(trackArtistRepository.deleteAll())
                .then(trackArtistRepository.deleteAll())
                .then(albumReleaseRepository.deleteAll())
                .then(trackRepository.deleteAll());
    }
}
