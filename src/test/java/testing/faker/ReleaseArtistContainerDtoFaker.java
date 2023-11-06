package testing.faker;

import com.github.javafaker.Faker;
import com.odeyalo.sonata.harmony.dto.ReleaseArtistContainerDto;
import com.odeyalo.sonata.harmony.dto.ReleaseArtistContainerDto.ReleaseArtistContainerDtoBuilder;
import com.odeyalo.sonata.harmony.dto.ReleaseArtistDto;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;

import java.util.Collection;

@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ReleaseArtistContainerDtoFaker {
    ReleaseArtistContainerDtoBuilder builder = ReleaseArtistContainerDto.builder();

    Faker faker = Faker.instance();

    public ReleaseArtistContainerDtoFaker() {
        int artistCount = faker.random().nextInt(0, 3);

        for (int i = 0; i < artistCount; i++) {
            ReleaseArtistDto artistDto = ReleaseArtistDtoFaker.create().get();

            builder.artist(artistDto);
        }
    }

    public static ReleaseArtistContainerDtoFaker randomReleaseArtistsDto() {
        return new ReleaseArtistContainerDtoFaker();
    }


    public ReleaseArtistContainerDto get() {
        return builder.build();
    }

    public ReleaseArtistContainerDtoFaker artist(ReleaseArtistDto artist) {
        builder.artist(artist);
        return this;
    }

    public ReleaseArtistContainerDtoFaker artists(Collection<? extends ReleaseArtistDto> artists) {
        builder.artists(artists);
        return this;
    }

    public ReleaseArtistContainerDtoFaker clearArtists() {
        builder.clearArtists();
        return this;
    }
}
