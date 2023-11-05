package com.odeyalo.sonata.harmony.support.converter;

import com.odeyalo.sonata.harmony.dto.ReleaseArtistContainerDto;
import com.odeyalo.sonata.harmony.dto.ReleaseArtistDto;
import com.odeyalo.sonata.harmony.entity.ArtistContainerEntity;
import com.odeyalo.sonata.harmony.entity.ArtistEntity;
import com.odeyalo.sonata.harmony.model.Artist;
import com.odeyalo.sonata.harmony.model.ArtistContainer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import testing.faker.ArtistEntityFaker;
import testing.faker.ReleaseArtistDtoFaker;
import testing.spring.MapStructBeansBootstrapConfiguration;

import java.util.List;
import java.util.Objects;

import static com.odeyalo.sonata.harmony.dto.ReleaseArtistContainerDto.multiple;
import static com.odeyalo.sonata.harmony.dto.ReleaseArtistContainerDto.solo;
import static java.util.Collections.emptyList;
import static java.util.Collections.singletonList;
import static java.util.List.of;
import static testing.asserts.ArtistContainerAssert.assertThat;

@ExtendWith(SpringExtension.class)
@Import(MapStructBeansBootstrapConfiguration.class)
class ArtistContainerConverterTest {

    @Autowired
    ArtistContainerConverter testable;

    @Test
    void shouldConvertSoloArtistFromDto() {
        ReleaseArtistDto artist = ReleaseArtistDtoFaker.create().get();
        ReleaseArtistContainerDto container = solo(artist);

        ArtistContainer result = testable.toArtistContainer(container);

        assertThat(result).hasSize(1);

        assertThat(result).first().matches(actual -> Objects.equals(artist.getId(), actual.getSonataId()));
    }

    @Test
    void shouldConvertMultipleArtistFromDto() {
        ReleaseArtistContainerDto container = prepareContainerDtoWithMultipleArtists();

        ArtistContainer result = testable.toArtistContainer(container);

        assertThat(result).hasSize(2);

        List<Artist> expected = container.getArtists().stream().map(artist -> sonataIdOnly(artist.getId())).toList();

        assertThat(result).containsAll(expected);
    }

    @Test
    void shouldConvertToEmptyFromArtistDto() {
        ReleaseArtistContainerDto empty = ReleaseArtistContainerDto.empty();

        ArtistContainer result = testable.toArtistContainer(empty);

        assertThat(result).isEmpty();
    }

    @Test
    void shouldConvertSoloArtistFromEntity() {
        ArtistEntity firstEntity = ArtistEntityFaker.create().get();
        ArtistContainerEntity container = ArtistContainerEntity.solo(firstEntity);

        ArtistContainer result = testable.toArtistContainer(container);

        assertThat(result).hasSize(1);

        assertThat(result).first()
                .hasSonataId(firstEntity.getSonataId())
                .hasName(firstEntity.getName());
    }

    @Test
    void shouldConvertMultipleArtistFromEntity() {
        ArtistContainerEntity container = getContainerWithMultipleArtistEntity();

        List<Artist> expected = container.stream().map(artist -> Artist.of(artist.getSonataId(), artist.getName())).toList();

        ArtistContainer result = testable.toArtistContainer(container);

        assertThat(result).containsAll(expected);
    }

    @Test
    void shouldConvertToEmptyFromArtistEntity() {
        ArtistContainerEntity empty = ArtistContainerEntity.empty();

        ArtistContainer result = testable.toArtistContainer(empty);

        assertThat(result).isEmpty();
    }

    @Test
    void shouldConvertSoloArtistFromEntityCollection() {
        ArtistEntity firstEntity = ArtistEntityFaker.create().get();
        List<ArtistEntity> container = singletonList(firstEntity);

        ArtistContainer result = testable.toArtistContainer(container);

        assertThat(result).hasSize(1);

        assertThat(result).first()
                .hasSonataId(firstEntity.getSonataId())
                .hasName(firstEntity.getName());
    }

    @Test
    void shouldConvertMultipleArtistFromEntityCollection() {
        List<ArtistEntity> artistEntities = of(ArtistEntityFaker.create().get(), ArtistEntityFaker.create().get());

        List<Artist> expected = artistEntities.stream()
                .map(artist -> Artist.of(artist.getSonataId(), artist.getName())).toList();

        ArtistContainer result = testable.toArtistContainer(artistEntities);

        assertThat(result).containsAll(expected);
    }

    @Test
    void shouldReturnEmptyFromEntityCollection() {
        List<ArtistEntity> empty = emptyList();

        ArtistContainer result = testable.toArtistContainer(empty);

        assertThat(result).isEmpty();
    }

    private static ArtistContainerEntity getContainerWithMultipleArtistEntity() {
        ArtistEntity firstEntity = ArtistEntityFaker.create().get();
        ArtistEntity secondEntity = ArtistEntityFaker.create().get();
        return ArtistContainerEntity.multiple(firstEntity, secondEntity);
    }


    private static Artist sonataIdOnly(String sonataId) {
        return Artist.builder().sonataId(sonataId).build();
    }

    private static ReleaseArtistContainerDto prepareContainerDtoWithMultipleArtists() {
        ReleaseArtistDto artist1 = ReleaseArtistDtoFaker.create().get();
        ReleaseArtistDto artist2 = ReleaseArtistDtoFaker.create().get();
        return multiple(artist1, artist2);
    }
}