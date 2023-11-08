package com.odeyalo.sonata.harmony.support.converter.external;

import com.odeyalo.sonata.suite.brokers.events.album.data.ArtistDto;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import testing.faker.ArtistFaker;
import testing.spring.MapStructBeansBootstrapConfiguration;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@Import(MapStructBeansBootstrapConfiguration.class)
class ArtistDtoConverterTest {
    @Autowired
    ArtistDtoConverter testable;

    @Test
    void toArtistDto() {
        var artist = ArtistFaker.create().get();
        ArtistDto result = testable.toArtistDto(artist);

        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(artist.getSonataId());
        assertThat(result.getName()).isEqualTo(artist.getName());
    }
}