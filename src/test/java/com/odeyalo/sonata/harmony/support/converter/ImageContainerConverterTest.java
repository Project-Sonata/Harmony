package com.odeyalo.sonata.harmony.support.converter;

import com.odeyalo.sonata.harmony.entity.AlbumCoverImageContainerEntity;
import com.odeyalo.sonata.harmony.entity.AlbumCoverImageEntity;
import com.odeyalo.sonata.harmony.model.ImageContainer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import testing.spring.MapStructBeansBootstrapConfiguration;

import java.util.Objects;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@Import(MapStructBeansBootstrapConfiguration.class)
class ImageContainerConverterTest {

    @Autowired
    ImageContainerConverter testable;

    @Test
    void toImageContainer() {
        AlbumCoverImageEntity image = createImageEntity();

        ImageContainer result = testable.toImageContainer(AlbumCoverImageContainerEntity.single(image));

        assertThat(result).hasSize(1);

        assertThat(result).first().matches(actual -> Objects.equals(actual.getHeight(), image.getHeight()));
        assertThat(result).first().matches(actual -> Objects.equals(actual.getWidth(), image.getWidth()));
        assertThat(result).first().matches(actual -> Objects.equals(actual.getUrl(), image.getUrl()));
    }

    private static AlbumCoverImageEntity createImageEntity() {
        return AlbumCoverImageEntity.builder()
                .height(300)
                .width(300)
                .url("https://cdn.sonata.com/i/image")
                .build();
    }
}