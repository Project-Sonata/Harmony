package com.odeyalo.sonata.harmony.support.converter.external;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.odeyalo.sonata.harmony.model.AlbumRelease;
import com.odeyalo.sonata.suite.brokers.events.album.data.UploadedAlbumInfoDto;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import testing.faker.AlbumReleaseFaker;
import testing.spring.MapStructBeansBootstrapConfiguration;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@Import(MapStructBeansBootstrapConfiguration.class)
class UploadedAlbumInfoDtoConverterTest {

    @Autowired
    UploadedAlbumInfoDtoConverter testable;
    ObjectMapper objectMapper = new ObjectMapper();
    @Test
    void toUploadedAlbumInfoDto() throws JsonProcessingException {
        var albumRelease = AlbumReleaseFaker.create().get();

        UploadedAlbumInfoDto result = testable.toUploadedAlbumInfoDto(albumRelease);

        assertThat(result).isNotNull();

        String json = objectMapper.writeValueAsString(result);

        System.out.println(json);

        assertThat(result.getName()).isEqualTo(albumRelease.getName());
        assertThat(result.getAlbumType().name()).isEqualTo(albumRelease.getAlbumType().name());

        assertThat(result.getArtists()).hasSize(albumRelease.getArtists().size());
        assertThat(result.getTracks()).hasSize(albumRelease.getTracks().size());
    }
}