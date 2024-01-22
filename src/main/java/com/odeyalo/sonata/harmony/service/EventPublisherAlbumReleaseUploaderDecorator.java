package com.odeyalo.sonata.harmony.service;

import com.odeyalo.sonata.harmony.model.AlbumRelease;
import com.odeyalo.sonata.harmony.service.album.UploadAlbumReleaseInfo;
import com.odeyalo.sonata.harmony.service.event.impl.BasicAlbumInfoUploadedEventPublisher;
import com.odeyalo.sonata.harmony.support.converter.external.BasicAlbumInfoUploadedPayloadConverter;
import com.odeyalo.sonata.suite.brokers.events.album.BasicAlbumInfoUploadedEvent;
import com.odeyalo.sonata.suite.brokers.events.album.data.BasicAlbumInfoUploadedPayload;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.context.annotation.Primary;
import org.springframework.http.codec.multipart.FilePart;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
@Primary
@RequiredArgsConstructor
public class EventPublisherAlbumReleaseUploaderDecorator implements AlbumReleaseUploader {
    private final AlbumReleaseUploader delegate;
    private final BasicAlbumInfoUploadedEventPublisher eventPublisher;
    private final BasicAlbumInfoUploadedPayloadConverter basicAlbumInfoUploadedPayloadConverter;

    @Override
    @NotNull
    public Mono<AlbumRelease> uploadAlbumRelease(@NotNull UploadAlbumReleaseInfo info,
                                                 @NotNull Flux<FilePart> tracks,
                                                 @NotNull Mono<FilePart> coverImage) {
        return delegate.uploadAlbumRelease(info, tracks, coverImage)
                .flatMap(albumRelease -> sendEvent(albumRelease).thenReturn(albumRelease));
    }

    private Mono<Void> sendEvent(AlbumRelease albumRelease) {
        BasicAlbumInfoUploadedPayload payload = basicAlbumInfoUploadedPayloadConverter.toBasicAlbumInfoUploadedPayload(albumRelease);

        return eventPublisher.publishEvent(new BasicAlbumInfoUploadedEvent(payload));
    }
}