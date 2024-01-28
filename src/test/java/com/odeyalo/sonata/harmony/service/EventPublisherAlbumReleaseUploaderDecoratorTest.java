package com.odeyalo.sonata.harmony.service;

import com.odeyalo.sonata.harmony.model.*;
import com.odeyalo.sonata.harmony.service.album.TrackUploadTarget;
import com.odeyalo.sonata.harmony.service.album.TrackUploadTargetContainer;
import com.odeyalo.sonata.harmony.service.album.UploadAlbumReleaseInfo;
import com.odeyalo.sonata.harmony.service.event.impl.BasicAlbumInfoUploadedEventPublisher;
import com.odeyalo.sonata.harmony.support.converter.external.BasicAlbumInfoUploadedPayloadConverter;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;
import org.springframework.http.codec.multipart.FilePart;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import testing.faker.AlbumReleaseFaker;
import testing.faker.ImageFaker;
import testing.spring.MapStructBeansBootstrapConfiguration;
import testing.spring.web.FilePartStub;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
@Import(MapStructBeansBootstrapConfiguration.class)
class EventPublisherAlbumReleaseUploaderDecoratorTest {

    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    @Autowired
    BasicAlbumInfoUploadedPayloadConverter converter;

    @Test
    void shouldPublishEventOnSuccess() {
        Image image = ImageFaker.create().get();
        AlbumRelease albumRelease = AlbumReleaseFaker.create().id(10L).images(ImageContainer.one(image)).get();
        BasicAlbumInfoUploadedEventPublisher publisher = Mockito.mock(BasicAlbumInfoUploadedEventPublisher.class);

        when(publisher.publishEvent(any())).thenReturn(Mono.empty());

        EventPublisherAlbumReleaseUploaderDecorator testable = new EventPublisherAlbumReleaseUploaderDecorator(
                (info, tracks, coverImage) -> Mono.just(albumRelease),
                publisher,
                converter
        );

        UploadAlbumReleaseInfo releaseInfo = createUploadAlbumReleaseInfo();
        Mono<FilePart> albumCoverFile = prepareAlbumCoverFile();
        Flux<FilePart> trackFiles = prepareTrackFiles();

        testable.uploadAlbumRelease(releaseInfo, trackFiles, albumCoverFile).block();

        verify(publisher, times(1)).publishEvent(any());
    }


    @NotNull
    private static Mono<FilePart> prepareAlbumCoverFile() {
        return Mono.just(new FilePartStub(Flux.empty()));
    }

    @NotNull
    private static Flux<FilePart> prepareTrackFiles() {
        return Flux.just(new FilePartStub(Flux.empty(), 2000, "track.mp3"));
    }

    @NotNull
    private static UploadAlbumReleaseInfo createUploadAlbumReleaseInfo() {
        ArtistContainer artists = ArtistContainer.solo(Artist.of("booones", "BONES"));

        TrackUploadTargetContainer tracks = TrackUploadTargetContainer.builder()
                .item(TrackUploadTarget.builder()
                        .trackName("dudeness")
                        .isExplicit(true)
                        .hasLyrics(true)
                        .fileId("track.mp3")
                        .artists(artists)
                        .discNumber(1)
                        .index(0)
                        .build())
                .build();

        return UploadAlbumReleaseInfo.builder()
                .albumName("something")
                .totalTracksCount(1)
                .albumType(AlbumType.SINGLE)
                .tracks(tracks)
                .releaseDate(ReleaseDate.onlyYear(2020))
                .artists(artists)
                .build();
    }
}