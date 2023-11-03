package com.odeyalo.sonata.harmony.support.converter;

import com.odeyalo.sonata.harmony.entity.ArtistEntity;
import com.odeyalo.sonata.harmony.model.Artist;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import testing.spring.MapStructBeansBootstrapConfiguration;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;


@ExtendWith(SpringExtension.class)
@Import(MapStructBeansBootstrapConfiguration.class)
class ArtistEntityConverterTest {

    @Autowired
    ArtistEntityConverter testable;

    @Test
    void toArtistEntity() {
        Artist artist = Artist.of("hello", "bones");

        ArtistEntity result = testable.toArtistEntity(artist);

        assertThat(result.getSonataId()).isEqualTo("hello");
        assertThat(result.getName()).isEqualTo("bones");
    }
}