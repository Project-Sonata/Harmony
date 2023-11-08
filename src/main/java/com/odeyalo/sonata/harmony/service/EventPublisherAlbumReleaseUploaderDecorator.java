package com.odeyalo.sonata.harmony.service;

import com.odeyalo.sonata.harmony.model.AlbumRelease;
import com.odeyalo.sonata.harmony.service.album.UploadAlbumReleaseInfo;
import com.odeyalo.sonata.harmony.support.converter.external.UploadedAlbumInfoDtoConverter;
import com.odeyalo.sonata.suite.brokers.events.album.data.AlbumFullyUploadedInfo;
import com.odeyalo.sonata.suite.brokers.events.album.data.UploadedAlbumInfoDto;
import org.jetbrains.annotations.NotNull;
import org.springframework.context.annotation.Primary;
import org.springframework.http.codec.multipart.FilePart;
import org.springframework.kafka.core.reactive.ReactiveKafkaProducerTemplate;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
@Primary
public class EventPublisherAlbumReleaseUploaderDecorator implements AlbumReleaseUploader {
    private final AlbumReleaseUploader delegate;
    private final ReactiveKafkaProducerTemplate<String, AlbumFullyUploadedInfo> sender;
    private final UploadedAlbumInfoDtoConverter uploadedAlbumInfoDtoConverter;

    public EventPublisherAlbumReleaseUploaderDecorator(AlbumReleaseUploader delegate,
                                                       ReactiveKafkaProducerTemplate<String, AlbumFullyUploadedInfo> sender,
                                                       UploadedAlbumInfoDtoConverter uploadedAlbumInfoDtoConverter) {
        this.delegate = delegate;
        this.sender = sender;
        this.uploadedAlbumInfoDtoConverter = uploadedAlbumInfoDtoConverter;
    }

    @Override
    @NotNull
    public Mono<AlbumRelease> uploadAlbumRelease(@NotNull UploadAlbumReleaseInfo info,
                                                 @NotNull Flux<FilePart> tracks,
                                                 @NotNull Mono<FilePart> coverImage) {
        return delegate.uploadAlbumRelease(info, tracks, coverImage)
                .flatMap(albumRelease -> sender.send("hello-world", createValue(albumRelease))
                        .doOnNext((result) -> System.out.println("Sent the message to message broker: " + result))
                        .thenReturn(albumRelease));
    }

    private AlbumFullyUploadedInfo createValue(AlbumRelease albumRelease) {
        return AlbumFullyUploadedInfo.builder().albumInfo(
                uploadedAlbumInfoDtoConverter.toUploadedAlbumInfoDto(albumRelease)
        ).build();
    }
}
