package testing.faker;

import com.github.javafaker.Faker;
import com.odeyalo.sonata.harmony.dto.ReleaseArtistContainerDto;
import com.odeyalo.sonata.harmony.dto.TrackDto;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;

@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class TrackDtoFaker {
    TrackDto.TrackDtoBuilder builder = TrackDto.builder();

    Faker faker = Faker.instance();

    public TrackDtoFaker() {
        trackName(faker.funnyName().name())
                .index(1)
                .discNumber(1)
                .durationMs(faker.random().nextLong())
                .hasLyrics(faker.random().nextBoolean())
                .isExplicit(faker.random().nextBoolean());
    }

    public static TrackDtoFaker create() {
        return new TrackDtoFaker();
    }

    public TrackDtoFaker trackName(String trackName) {
        builder.trackName(trackName);
        return this;
    }

    public TrackDtoFaker durationMs(long durationMs) {
        builder.durationMs(durationMs);
        return this;
    }

    public TrackDtoFaker isExplicit(boolean isExplicit) {
        builder.isExplicit(isExplicit);
        return this;
    }

    public TrackDtoFaker hasLyrics(boolean hasLyrics) {
        builder.hasLyrics(hasLyrics);
        return this;
    }

    public TrackDtoFaker discNumber(int discNumber) {
        builder.discNumber(discNumber);
        return this;
    }

    public TrackDtoFaker index(int index) {
        builder.index(index);
        return this;
    }

    public TrackDtoFaker artists(ReleaseArtistContainerDto artists) {
        builder.artists(artists);
        return this;
    }

    public TrackDtoFaker fileId(String fileId) {
        builder.fileId(fileId);
        return this;
    }

    public TrackDto get() {
        return builder.build();
    }
}
