package com.odeyalo.sonata.harmony.support.converter;

import com.odeyalo.sonata.harmony.entity.ArtistContainerEntity;
import com.odeyalo.sonata.harmony.model.Artist;
import com.odeyalo.sonata.harmony.model.ArtistContainer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import testing.spring.MapStructBeansBootstrapConfiguration;

import java.util.Objects;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@Import(MapStructBeansBootstrapConfiguration.class)
class ArtistContainerEntityConverterTest {

    @Autowired
    ArtistContainerEntityConverter testable;

    @Test
    void toArtistContainerEntity() {
        ArtistContainer param = ArtistContainer.solo(Artist.of("hello", "bones"));

        ArtistContainerEntity actual = testable.toArtistContainerEntity(param);

        assertThat(actual).hasSize(1);

        assertThat(actual).first().matches(entity -> Objects.equals(entity.getSonataId(), "hello"));
        assertThat(actual).first().matches(entity -> Objects.equals(entity.getName(), "bones"));
    }
}