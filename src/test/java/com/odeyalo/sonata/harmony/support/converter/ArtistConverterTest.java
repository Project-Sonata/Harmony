package com.odeyalo.sonata.harmony.support.converter;

import com.odeyalo.sonata.harmony.dto.ReleaseArtistDto;
import com.odeyalo.sonata.harmony.entity.ArtistEntity;
import com.odeyalo.sonata.harmony.model.Artist;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import testing.spring.MapStructBeansBootstrapConfiguration;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@Import(MapStructBeansBootstrapConfiguration.class)
class ArtistConverterTest {

    @Autowired
    ArtistConverter artistConverter;

    @Test
    void fromArtistEntityToArtist() {
        var artist = getArtistEntity();

        Artist result = artistConverter.toArtist(artist);

        assertThat(result).isNotNull();
        assertThat(result.getName()).isEqualTo(artist.getName());
        assertThat(result.getSonataId()).isEqualTo(artist.getSonataId());
    }

    @Test
    void releaseArtistDtoToArtist() {
        var artistDto = ReleaseArtistDto.of("iLoveMiku");

        Artist artist = artistConverter.toArtist(artistDto);

        assertThat(artist).isNotNull();
        assertThat(artist.getName()).isNull();
        assertThat(artist.getSonataId()).isEqualTo("iLoveMiku");
    }

    private static ArtistEntity getArtistEntity() {
        return ArtistEntity.builder()
                .sonataId("id-of-the-artist")
                .name("BONES")
                .build();
    }
}