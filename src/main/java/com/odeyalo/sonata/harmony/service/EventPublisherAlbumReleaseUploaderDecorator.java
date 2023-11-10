package com.odeyalo.sonata.harmony.service;

import com.odeyalo.sonata.harmony.model.AlbumRelease;
import com.odeyalo.sonata.harmony.service.album.UploadAlbumReleaseInfo;
import com.odeyalo.sonata.harmony.service.event.impl.AlbumUploadingFullyFinishedEventPublisher;
import com.odeyalo.sonata.harmony.support.converter.external.UploadedAlbumInfoDtoConverter;
import com.odeyalo.sonata.suite.brokers.events.album.AlbumUploadingFullyFinishedEvent;
import com.odeyalo.sonata.suite.brokers.events.album.data.AlbumFullyUploadedInfo;
import org.jetbrains.annotations.NotNull;
import org.springframework.context.annotation.Primary;
import org.springframework.http.codec.multipart.FilePart;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
@Primary
public class EventPublisherAlbumReleaseUploaderDecorator implements AlbumReleaseUploader {
    private final AlbumReleaseUploader delegate;
    private final AlbumUploadingFullyFinishedEventPublisher eventPublisher;
    private final UploadedAlbumInfoDtoConverter uploadedAlbumInfoDtoConverter;

    public EventPublisherAlbumReleaseUploaderDecorator(AlbumReleaseUploader delegate,
                                                       AlbumUploadingFullyFinishedEventPublisher eventPublisher,
                                                       UploadedAlbumInfoDtoConverter uploadedAlbumInfoDtoConverter) {
        this.delegate = delegate;
        this.eventPublisher = eventPublisher;
        this.uploadedAlbumInfoDtoConverter = uploadedAlbumInfoDtoConverter;
    }

    @Override
    @NotNull
    public Mono<AlbumRelease> uploadAlbumRelease(@NotNull UploadAlbumReleaseInfo info,
                                                 @NotNull Flux<FilePart> tracks,
                                                 @NotNull Mono<FilePart> coverImage) {
        return delegate.uploadAlbumRelease(info, tracks, coverImage)
                .flatMap(albumRelease -> sendEvent(albumRelease).thenReturn(albumRelease));
    }

    private Mono<Void> sendEvent(AlbumRelease albumRelease) {
        AlbumFullyUploadedInfo albumFullyUploadedInfo = createAlbumFullyUploadedInfo(albumRelease);
        return eventPublisher.publishEvent(new AlbumUploadingFullyFinishedEvent(albumFullyUploadedInfo));
    }

    private AlbumFullyUploadedInfo createAlbumFullyUploadedInfo(AlbumRelease albumRelease) {
        return AlbumFullyUploadedInfo.builder().albumInfo(
                uploadedAlbumInfoDtoConverter.toUploadedAlbumInfoDto(albumRelease)
        ).build();
    }
}
