package com.odeyalo.sonata.harmony.api.rest;

import com.odeyalo.sonata.harmony.api.rest.UploadAlbumReleaseEndpointTest.Config;
import com.odeyalo.sonata.harmony.dto.*;
import com.odeyalo.sonata.harmony.service.upload.DelegatingFileUploader;
import com.odeyalo.sonata.harmony.service.upload.FileUploader;
import com.odeyalo.sonata.harmony.service.upload.FileUrl;
import com.odeyalo.sonata.harmony.service.upload.MockFileUploaderDelegate;
import com.odeyalo.sonata.harmony.support.kafka.ReactiveKafkaProducer;
import com.odeyalo.sonata.harmony.support.kafka.TemplateDelegateReactiveKafkaProducer;
import com.odeyalo.sonata.suite.brokers.events.SonataEvent;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.MediaType;
import org.springframework.http.client.MultipartBodyBuilder;
import org.springframework.kafka.core.reactive.ReactiveKafkaProducerTemplate;
import org.springframework.messaging.Message;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.function.BodyInserters;
import reactor.core.publisher.Mono;
import reactor.kafka.sender.SenderResult;
import testing.faker.ReleaseArtistDtoFaker;
import testing.faker.TrackDtoFaker;
import testing.faker.UploadAlbumReleaseRequestFaker;
import testing.spring.config.AutoconfigureQaEnvironment;
import testing.qa.QaOperations;

import static com.odeyalo.sonata.harmony.dto.ReleaseArtistContainerDto.solo;
import static com.odeyalo.sonata.harmony.dto.TrackContainerDto.single;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;

@SpringBootTest
@TestInstance(Lifecycle.PER_CLASS)
@AutoConfigureWebTestClient
@ActiveProfiles("test")
@Import(Config.class)
@AutoconfigureQaEnvironment
public class UploadAlbumReleaseEndpointTest {

    @Autowired
    WebTestClient webTestClient;

    @Autowired
    QaOperations qaOperations;

    static final String ALBUM_COVER_PATH = "./img/album-cover.png";
    static final String TRACK_1_NAME = "test.mp3";
    static final String TRACK_1_PATH = "./music/" + TRACK_1_NAME;

    @AfterEach
    void tearDown() {
        qaOperations.clearEverything();
    }

    @Nested
    @TestInstance(Lifecycle.PER_CLASS)
    class ValidRequestTests {

        @Test
        void shouldReturnAcceptedStatus() {
            WebTestClient.ResponseSpec responseSpec = sendRequestWithValidData();

            responseSpec.expectStatus().isAccepted();
        }

        @Test
        void shouldReturnTrackingIdInBody() {
            WebTestClient.ResponseSpec responseSpec = sendRequestWithValidData();

            var responseBody = responseSpec.expectBody(AlbumReleaseUploadAcceptedResponse.class).returnResult().getResponseBody();

            assertThat(responseBody).isNotNull();

            String trackingId = responseBody.getTrackingId();

            webTestClient.get().uri("/tracking/{trackingId}", trackingId)
                    .exchange()
                    .expectStatus().isOk();
        }

        @NotNull
        private WebTestClient.ResponseSpec sendRequestWithValidData() {
            MultipartBodyBuilder builder = prepareValidRequestBody();

            return sendUploadAlbumRelease(builder);
        }

        @NotNull
        private static MultipartBodyBuilder prepareValidRequestBody() {
            UploadAlbumReleaseRequest requestBody = prepareValidCustomizableRequestBody().build();

            return prepareRequest(requestBody);
        }
    }

    @Nested
    @TestInstance(Lifecycle.PER_CLASS)
    class InvalidRequestBodyPayloadTests {

        @Test
        void shouldReturnBadRequestIfNameIsNull() {
            UploadAlbumReleaseRequest requestBody = prepareValidCustomizableRequestBody()
                    .albumName(null).build();

            MultipartBodyBuilder builder = prepareRequest(requestBody);

            WebTestClient.ResponseSpec responseSpec = sendUploadAlbumRelease(builder);

            responseSpec.expectStatus().isBadRequest();
        }

        @Test
        void shouldReturnBadRequestIfAlbumTypeIsNull() {
            UploadAlbumReleaseRequest requestBody = prepareValidCustomizableRequestBody()
                    .albumType(null).build();

            MultipartBodyBuilder builder = prepareRequest(requestBody);

            WebTestClient.ResponseSpec responseSpec = sendUploadAlbumRelease(builder);

            responseSpec.expectStatus().isBadRequest();
        }

        @Test
        void shouldReturnBadRequestIfPerformersAreNull() {
            UploadAlbumReleaseRequest requestBody = prepareValidCustomizableRequestBody()
                    .performers(null).build();

            MultipartBodyBuilder builder = prepareRequest(requestBody);

            WebTestClient.ResponseSpec responseSpec = sendUploadAlbumRelease(builder);

            responseSpec.expectStatus().isBadRequest();
        }

        @Test
        void shouldReturnBadRequestIfTracksAreNull() {
            UploadAlbumReleaseRequest requestBody = prepareValidCustomizableRequestBody()
                    .tracks(null).build();

            MultipartBodyBuilder builder = prepareRequest(requestBody);

            WebTestClient.ResponseSpec responseSpec = sendUploadAlbumRelease(builder);

            responseSpec.expectStatus().isBadRequest();
        }
    }

    private static UploadAlbumReleaseRequest.UploadAlbumReleaseRequestBuilder prepareValidCustomizableRequestBody() {
        ReleaseArtistContainerDto artistContainer = solo(ReleaseArtistDtoFaker.create().get());
        TrackDto track = TrackDtoFaker.create().artists(artistContainer).fileId(TRACK_1_NAME).get();

        TrackContainerDto container = single(track);

        return UploadAlbumReleaseRequestFaker.create().performers(artistContainer)
                .tracks(container).get().toBuilder();
    }

    @NotNull
    private static MultipartBodyBuilder prepareRequest(UploadAlbumReleaseRequest requestBody) {
        MultipartBodyBuilder builder = new MultipartBodyBuilder();

        builder.part("body", requestBody, MediaType.APPLICATION_JSON);

        ClassPathResource albumCover = new ClassPathResource(ALBUM_COVER_PATH);

        ClassPathResource track1 = new ClassPathResource(TRACK_1_PATH);

        builder.part("cover", BodyInserters.fromResource(albumCover))
                .filename("hoshino.png");

        builder.part("tracks", BodyInserters.fromResource(track1))
                .filename(TRACK_1_NAME);

        return builder;
    }

    @NotNull
    private WebTestClient.ResponseSpec sendUploadAlbumRelease(MultipartBodyBuilder builder) {
        return webTestClient.post()
                .uri("/release/upload/album")
                .body(BodyInserters.fromMultipartData(builder.build()))
                .exchange();
    }


    @TestConfiguration
    static class Config {
        @Bean
        public FileUploader fileUploader() {
            return new DelegatingFileUploader(new MockFileUploaderDelegate(
                    FileUrl.urlOnly("https://cdn.sonata.com/i/image")
            ));
        }

        @Bean
        @Primary
        public ReactiveKafkaProducer<?, ?> testingReactiveKafkaProducer() {
            ReactiveKafkaProducerTemplate<?, SonataEvent> producerTemplate = Mockito.mock(ReactiveKafkaProducerTemplate.class);
            Mockito.when(producerTemplate.send(any(), (SonataEvent) any())).thenReturn(Mono.empty());

            return new TemplateDelegateReactiveKafkaProducer<>(
                    producerTemplate
            );
        }

    }
}
