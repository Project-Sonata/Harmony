package com.odeyalo.sonata.harmony.support.converter.external;

import com.odeyalo.sonata.harmony.model.Artist;
import com.odeyalo.sonata.harmony.model.ArtistContainer;
import com.odeyalo.sonata.harmony.model.Track;
import com.odeyalo.sonata.suite.brokers.events.album.data.SimplifiedTrackDto;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import testing.faker.ArtistFaker;
import testing.faker.TrackFaker;
import testing.spring.MapStructBeansBootstrapConfiguration;

import java.util.Objects;

import static com.odeyalo.sonata.harmony.model.ArtistContainer.solo;
import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@Import(MapStructBeansBootstrapConfiguration.class)
class SimplifiedTrackDtoConverterTest {

    @Autowired
    SimplifiedTrackDtoConverter testable;

    @Test
    void toSimplifiedTrackDto() {
        Artist artist = ArtistFaker.create().get();
        var track = TrackFaker.create().artists(solo(artist)).get();
        SimplifiedTrackDto result = testable.toSimplifiedTrackDto(track);

        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(track.getId());
        assertThat(result.getName()).isEqualTo(track.getTrackName());
        assertThat(result.getDurationMs()).isEqualTo(track.getDurationMs());

        assertThat(result.getArtists()).hasSize(1);

        assertThat(result.getArtists()).first().matches(actual ->
                Objects.equals(artist.getName(), actual.getName()) &&
                        Objects.equals(artist.getSonataId(), actual.getId()));
    }
}